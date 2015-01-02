package lv.monkeyseemonkeydo.thegoodshepherd;

import lv.monkeyseemonkeydo.thegoodshepherd.actions.ShutdownPc;
import lv.monkeyseemonkeydo.thegoodshepherd.actions.WakePc;
import android.app.IntentService;
import android.content.Intent;

public class TriggerDispatcher extends IntentService {
	public static String HANDLE_CHARGING = "shepherd.CHARGING";
	public static String HANDLE_DISCHARGING = "shepherd.CHARGING";
	public static String HANDLE_CONNECTED = "shepherd.CONNECTED";
	public static String HANDLE_DISCONNECTED = "shepherd.DISCONNECTED";
	public static String HANDLE_COOLDOWN = "shepherd.COODLOWN";
	public static String HANDLE_LOW_BATTERY = "shepherd.LOW_BATTERY";

    public TriggerDispatcher() {
        super("ProxyService");
    }

	@Override
    protected void onHandleIntent(Intent intent) {
        TheGoodApplication app = (TheGoodApplication) getApplication();

        // If we're not connected to Wifi then do nothing for now-
        if (!WifiReceiver.isConnected(app))
    		return;
        
        if (HANDLE_CHARGING.equals(intent.getAction())) {
        	(new WakePc()).doIt();
        }

        if (HANDLE_DISCHARGING.equals(intent.getAction())) {
        	(new ShutdownPc(app)).doIt();        	
        }

    }
}
