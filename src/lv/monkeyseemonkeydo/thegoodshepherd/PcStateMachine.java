package lv.monkeyseemonkeydo.thegoodshepherd;

import lv.monkeyseemonkeydo.thegoodshepherd.actions.ShutdownPc;
import lv.monkeyseemonkeydo.thegoodshepherd.actions.WakePc;

import com.github.oxo42.stateless4j.StateMachine;
import com.github.oxo42.stateless4j.StateMachineConfig;

public class PcStateMachine {
	private enum State {
		WantOffUnplugged, IsOff, WantOnUnplugged, IsOn
	}

	private enum Trigger {
		GotPluggedIn, GotUnplugged, PeopleArrived, PeopleLeft
	}

	public StateMachine<State, Trigger> getInstance(State initialState) {
		StateMachineConfig<State, Trigger> pcConfig = new StateMachineConfig<>();

		// @formatter:off
		pcConfig.configure(State.WantOffUnplugged)
			.permit(Trigger.GotPluggedIn, State.IsOn)
			.permit(Trigger.PeopleArrived, State.WantOnUnplugged);

		pcConfig.configure(State.IsOff)
			.onEntry(new ShutdownPc())
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

		return new StateMachine<>(initialState, pcConfig);

	}

}
