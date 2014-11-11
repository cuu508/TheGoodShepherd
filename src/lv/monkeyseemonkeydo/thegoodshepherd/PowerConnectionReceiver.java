package lv.monkeyseemonkeydo.thegoodshepherd;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.BatteryManager;

public class PowerConnectionReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        int status = intent.getIntExtra(BatteryManager.EXTRA_STATUS, -1);
        boolean isCharging = status == BatteryManager.BATTERY_STATUS_CHARGING ||
                            status == BatteryManager.BATTERY_STATUS_FULL;

        Intent newIntent = new Intent(context, ProxyService.class);
        newIntent.setAction(isCharging ? ProxyService.HANDLE_CHARGING : ProxyService.HANDLE_DISCHARGING);
        context.startService(newIntent);
    }
}