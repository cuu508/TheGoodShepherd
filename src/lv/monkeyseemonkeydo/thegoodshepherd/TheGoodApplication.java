package lv.monkeyseemonkeydo.thegoodshepherd;

import lv.monkeyseemonkeydo.thegoodshepherd.machines.Pc;
import lv.monkeyseemonkeydo.thegoodshepherd.machines.Wemo;
import lv.monkeyseemonkeydo.thegoodshepherd.machines.Wifi;
import android.app.AlarmManager;
import android.app.Application;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

public class TheGoodApplication extends Application {

	public Pc pc;
	public Wemo wemo;
	public Wifi wifi;

	@Override
	public void onCreate() {
		super.onCreate();

		// Determine initial state:
		boolean wifiConnected = WifiReceiver.isConnected(this);
		boolean charging = PowerConnectionReceiver.isCharging(this, null);

		Wifi.State initialWifiState = Wifi.State.Connected;
		if (!wifiConnected)
			initialWifiState = charging ? Wifi.State.Connecting : Wifi.State.Disconnected;

		// For Wemo, take the conservative approach and

		Wemo.State initialWemoState = Wemo.State.UnreachableWantOff;
		if (!wifiConnected && charging)
			initialWemoState = Wemo.State.UnreachableWantOn;

		// If Wifi is on, we will want to determine actual wemo state now


		pc = new Pc(Pc.State.IsOff, this);
		wemo = new Wemo(Wemo.State.ReachableOff, this);
		wifi = new Wifi(initialWifiState, this);

		// Schedule periodic battery checks:
		Intent intent = new Intent(this, BatteryCheckReceiver.class);
		PendingIntent pi = PendingIntent.getBroadcast(this, 0, intent, 0);

		AlarmManager mgr = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
		// I'm not sure if I need to do this, but just in case...
		mgr.cancel(pi);
		mgr.setRepeating(AlarmManager.RTC_WAKEUP, System.currentTimeMillis(),
				AlarmManager.INTERVAL_HALF_DAY, pi);
	}

}
