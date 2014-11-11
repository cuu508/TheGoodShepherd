package lv.monkeyseemonkeydo.thegoodshepherd;

import lv.monkeyseemonkeydo.thegoodshepherd.machines.Pc;
import lv.monkeyseemonkeydo.thegoodshepherd.machines.Wemo;
import lv.monkeyseemonkeydo.thegoodshepherd.machines.Wifi;
import android.app.IntentService;
import android.content.Intent;

public class ProxyService extends IntentService {
	public static String HANDLE_CHARGING = "shepherd.CHARGING";
	public static String HANDLE_DISCHARGING = "shepherd.CHARGING";
	public static String HANDLE_CONNECTED = "shepherd.CONNECTED";
	public static String HANDLE_DISCONNECTED = "shepherd.DISCONNECTED";


    public ProxyService() {
        super("ProxyService");
    }

	@Override
    protected void onHandleIntent(Intent intent) {
        TheGoodApplication app = (TheGoodApplication) getApplication();

        if (HANDLE_CHARGING.equals(intent.getAction())) {
        	app.wifi.fire(Wifi.Trigger.PeopleArrived);
        	app.wemo.fire(Wemo.Trigger.PeopleArrived);
        	app.pc.fire(Pc.Trigger.PeopleArrived);
        }

        if (HANDLE_DISCHARGING.equals(intent.getAction())) {
        	app.wifi.fire(Wifi.Trigger.PeopleLeft);
        	app.wemo.fire(Wemo.Trigger.PeopleLeft);
        	app.pc.fire(Pc.Trigger.PeopleLeft);
        }

        if (HANDLE_CONNECTED.equals(intent.getAction())) {
        	app.wifi.fire(Wifi.Trigger.GotConnected);
        	app.wemo.fire(Wemo.Trigger.WifiConnected);
        }

        if (HANDLE_DISCONNECTED.equals(intent.getAction())) {
        	app.wifi.fire(Wifi.Trigger.GotDisconnected);
        	app.wemo.fire(Wemo.Trigger.WifiDisconnected);
        }


    }
}
