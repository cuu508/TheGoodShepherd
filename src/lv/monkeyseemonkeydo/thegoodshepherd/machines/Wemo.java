package lv.monkeyseemonkeydo.thegoodshepherd.machines;

import lv.monkeyseemonkeydo.thegoodshepherd.actions.StartCooldown;
import lv.monkeyseemonkeydo.thegoodshepherd.actions.SwitchWemoOff;
import lv.monkeyseemonkeydo.thegoodshepherd.actions.SwitchWemoOn;
import android.content.Context;

import com.github.oxo42.stateless4j.StateMachine;
import com.github.oxo42.stateless4j.StateMachineConfig;

public class Wemo {
	public enum State {
		ReachableOff, ReachableOn, ReachableCooldown, UnreachableWantOff, UnreachableWantOn, UnreachableCooldown
	}

	public enum Trigger {
		WifiConnected, WifiDisconnected, PeopleArrived, PeopleLeft, CooldownPassed
	}

	private StateMachine<State, Trigger> machine;

	public Wemo(State initialState, Context context) {
		StateMachineConfig<State, Trigger> wemoConfig = new StateMachineConfig<>();

		// @formatter:off
		wemoConfig.configure(State.ReachableOff)
		    .onEntry(new SwitchWemoOff())
			.permit(Trigger.PeopleArrived, State.ReachableOn)
			.permit(Trigger.WifiDisconnected, State.UnreachableWantOff);

		wemoConfig.configure(State.ReachableOn)
    		.onEntry(new SwitchWemoOn())
    		.permit(Trigger.PeopleLeft, State.ReachableCooldown)
    		.permit(Trigger.WifiDisconnected, State.UnreachableWantOn);

		wemoConfig.configure(State.ReachableCooldown)
    		.onEntry(new StartCooldown(context))
    		.permit(Trigger.CooldownPassed, State.ReachableOff)
    		.permit(Trigger.WifiDisconnected, State.UnreachableCooldown);

		wemoConfig.configure(State.UnreachableWantOff)
			.permit(Trigger.PeopleArrived, State.UnreachableWantOn)
			.permit(Trigger.WifiConnected, State.ReachableOff);

		wemoConfig.configure(State.UnreachableWantOn)
			.permit(Trigger.PeopleLeft, State.UnreachableCooldown)
			.permit(Trigger.WifiConnected, State.ReachableOn);

		wemoConfig.configure(State.UnreachableCooldown)
			.permit(Trigger.CooldownPassed, State.UnreachableWantOff)
			.permit(Trigger.WifiConnected, State.ReachableCooldown);
		// @formatter:on

		machine = new StateMachine<>(initialState, wemoConfig);
	}

	public void fire(Trigger trigger) {
		machine.fire(trigger);
	}


}
