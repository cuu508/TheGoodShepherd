package lv.monkeyseemonkeydo.thegoodshepherd;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.BatteryManager;

public class PowerConnectionReceiver extends BroadcastReceiver {

	public static boolean isPlugged(Context context) {
		Intent batteryIntent = context.registerReceiver(null, new IntentFilter(Intent.ACTION_BATTERY_CHANGED));
		int status = batteryIntent.getIntExtra(BatteryManager.EXTRA_PLUGGED, -1);
		return status != 0;
	}

	@Override
	public void onReceive(Context context, Intent intent) {
		boolean plugged = PowerConnectionReceiver.isPlugged(context);
		
		Intent newIntent = new Intent(context, TriggerDispatcher.class);
		newIntent.setAction(plugged ? TriggerDispatcher.HANDLE_CHARGING
				: TriggerDispatcher.HANDLE_DISCHARGING);

		context.startService(newIntent);
	}
}