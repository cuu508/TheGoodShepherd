package lv.monkeyseemonkeydo.thegoodshepherd.actions;

import java.util.Calendar;

import lv.monkeyseemonkeydo.thegoodshepherd.TriggerDispatcher;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

public class StartCooldown implements Action {

	private Context context;

	public StartCooldown(Context context) {
		this.context = context;
	}

	@Override
	public boolean doIt() {
        Intent intent = new Intent(context, TriggerDispatcher.class);
        intent.setAction(TriggerDispatcher.HANDLE_COOLDOWN);
		PendingIntent pi = PendingIntent.getService(context, 0, intent, 0);

		// If there is previous cooldown "in flight", cancel it:
		AlarmManager mgr = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
		mgr.cancel(pi);

		// And schedule this one in 5 minutes:
		Calendar time = Calendar.getInstance();
		time.setTimeInMillis(System.currentTimeMillis());
		time.add(Calendar.MINUTE, 5);
		mgr.set(AlarmManager.RTC_WAKEUP, time.getTimeInMillis(), pi);
		
		return true;
	}

}
