package lv.monkeyseemonkeydo.thegoodshepherd;

import lv.monkeyseemonkeydo.thegoodshepherd.actions.ConnectWifi;
import lv.monkeyseemonkeydo.thegoodshepherd.actions.DisconnectWifi;

import com.github.oxo42.stateless4j.StateMachine;
import com.github.oxo42.stateless4j.StateMachineConfig;

public class WifiStateMachine {
	private enum State {
		Disconnected, Connecting, Connected, ConnectedInactive
	}

	private enum Trigger {
		PeopleArrived, GotConnected, PeopleLeft, BatteryLow
	}

	public StateMachine<State, Trigger> getInstance(State initialState) {
		StateMachineConfig<State, Trigger> wifiConfig = new StateMachineConfig<>();

		// @formatter:off
		wifiConfig.configure(State.Disconnected)
			.onEntry(new DisconnectWifi())
		    .permit(Trigger.PeopleArrived, State.Connecting);

		wifiConfig.configure(State.Connecting)
			.onEntry(new ConnectWifi())
			.permit(Trigger.GotConnected, State.Connected);

		wifiConfig.configure(State.Connected)
			.permit(Trigger.PeopleLeft, State.ConnectedInactive);

		wifiConfig.configure(State.ConnectedInactive)
			.permit(Trigger.BatteryLow, State.Disconnected);
		// @formatter:on

		return new StateMachine<>(initialState, wifiConfig);
	}

}
