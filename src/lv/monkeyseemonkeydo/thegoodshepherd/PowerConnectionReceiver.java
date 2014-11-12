package lv.monkeyseemonkeydo.thegoodshepherd;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.BatteryManager;

public class PowerConnectionReceiver extends BroadcastReceiver {

	public static boolean isCharging(Context context, Intent statusIntent) {
		if (statusIntent == null)
			statusIntent = context.registerReceiver(null, new IntentFilter(Intent.ACTION_BATTERY_CHANGED));

		int status = statusIntent.getIntExtra(BatteryManager.EXTRA_STATUS, -1);
		return status == BatteryManager.BATTERY_STATUS_CHARGING
				|| status == BatteryManager.BATTERY_STATUS_FULL;
	}

	@Override
	public void onReceive(Context context, Intent intent) {
		boolean isCharging = PowerConnectionReceiver.isCharging(context, intent);

		Intent newIntent = new Intent(context, TriggerDispatcher.class);
		newIntent.setAction(isCharging ? TriggerDispatcher.HANDLE_CHARGING
				: TriggerDispatcher.HANDLE_DISCHARGING);
		context.startService(newIntent);
	}
}