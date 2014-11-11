package lv.monkeyseemonkeydo.thegoodshepherd.machines;

import lv.monkeyseemonkeydo.thegoodshepherd.actions.ConnectWifi;
import lv.monkeyseemonkeydo.thegoodshepherd.actions.DisconnectWifi;
import android.content.Context;

import com.github.oxo42.stateless4j.StateMachine;
import com.github.oxo42.stateless4j.StateMachineConfig;

public class Wifi {
	public enum State {
		Disconnected, Connecting, Connected, ConnectedInactive
	}

	public enum Trigger {
		PeopleArrived, GotConnected, GotDisconnected, PeopleLeft, BatteryLow
	}

	private StateMachine<State, Trigger> machine;

	public Wifi(State initialState, Context context) {
		StateMachineConfig<State, Trigger> wifiConfig = new StateMachineConfig<>();

		// @formatter:off
		wifiConfig.configure(State.Disconnected)
			.onEntry(new DisconnectWifi(context))
		    .permit(Trigger.PeopleArrived, State.Connecting);

		wifiConfig.configure(State.Connecting)
			.onEntry(new ConnectWifi(context))
			.permit(Trigger.GotConnected, State.Connected)
			.permit(Trigger.GotDisconnected, State.Disconnected);

		wifiConfig.configure(State.Connected)
			.permit(Trigger.PeopleLeft, State.ConnectedInactive)
			.permit(Trigger.GotDisconnected, State.Disconnected);

		wifiConfig.configure(State.ConnectedInactive)
			.permit(Trigger.BatteryLow, State.Disconnected)
			.permit(Trigger.GotDisconnected, State.Disconnected);

		// @formatter:on

		machine =  new StateMachine<>(initialState, wifiConfig);
	}

	public void fire(Trigger trigger) {
		machine.fire(trigger);
	}

}
