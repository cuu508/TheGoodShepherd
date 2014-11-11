package lv.monkeyseemonkeydo.thegoodshepherd.machines;

import lv.monkeyseemonkeydo.thegoodshepherd.actions.ShutdownPc;
import lv.monkeyseemonkeydo.thegoodshepherd.actions.WakePc;
import android.content.res.Resources;

import com.github.oxo42.stateless4j.StateMachine;
import com.github.oxo42.stateless4j.StateMachineConfig;

public class Pc {
	public enum State {
		WantOffUnplugged, IsOff, WantOnUnplugged, IsOn
	}

	public enum Trigger {
		GotPluggedIn, GotUnplugged, PeopleArrived, PeopleLeft
	}

	private StateMachine<State, Trigger> machine;

	public Pc(State initialState, Resources resources) {
		StateMachineConfig<State, Trigger> pcConfig = new StateMachineConfig<>();

		// @formatter:off
		pcConfig.configure(State.WantOffUnplugged)
			.permit(Trigger.GotPluggedIn, State.IsOn)
			.permit(Trigger.PeopleArrived, State.WantOnUnplugged);

		pcConfig.configure(State.IsOff)
			.onEntry(new ShutdownPc(resources))
			.permit(Trigger.GotUnplugged, State.WantOffUnplugged)
			.permit(Trigger.PeopleArrived, State.IsOn);

		pcConfig.configure(State.WantOnUnplugged)
			.permit(Trigger.GotPluggedIn, State.IsOn)
			.permit(Trigger.PeopleLeft, State.WantOffUnplugged);

		pcConfig.configure(State.IsOn)
			.onEntry(new WakePc())
			.permit(Trigger.GotUnplugged, State.WantOnUnplugged)
			.permit(Trigger.PeopleLeft, State.IsOff);

		// @formatter:on

		machine = new StateMachine<>(initialState, pcConfig);
	}

	public void fire(Trigger trigger) {
		machine.fire(trigger);
	}


}
