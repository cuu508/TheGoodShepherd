package lv.monkeyseemonkeydo.thegoodshepherd;

import android.app.AlarmManager;
import android.app.Application;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

public class TheGoodApplication extends Application {

	@Override
	public void onCreate() {
		super.onCreate();

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
