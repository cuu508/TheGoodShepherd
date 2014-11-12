package lv.monkeyseemonkeydo.thegoodshepherd;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.BatteryManager;

public class BatteryCheckReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Intent batteryIntent = context.registerReceiver(null, new IntentFilter(Intent.ACTION_BATTERY_CHANGED));
        int level = batteryIntent.getIntExtra(BatteryManager.EXTRA_LEVEL, -1);
        int scale = batteryIntent.getIntExtra(BatteryManager.EXTRA_SCALE, -1);

        if(level == -1 || scale == -1)
        	throw new RuntimeException("Battery level is " + level + " and scale is " + scale);

        if ((100 * level) / scale < 50) {
        	Intent newIntent = new Intent(context, TriggerDispatcher.class);
        	newIntent.setAction(TriggerDispatcher.HANDLE_LOW_BATTERY);
        	context.startService(newIntent);
        }
    }
}