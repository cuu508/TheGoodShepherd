package lv.monkeyseemonkeydo.thegoodshepherd.actions;

import android.content.Context;
import android.net.wifi.WifiManager;

public class ConnectWifi implements Action {

	private Context context;

	public ConnectWifi(Context context) {
		this.context = context;
	}

	@Override
	public boolean doIt() {
		WifiManager wifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
		wifiManager.setWifiEnabled(true);
		return true;
	}

}
