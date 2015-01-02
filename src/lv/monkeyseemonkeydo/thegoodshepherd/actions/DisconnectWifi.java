package lv.monkeyseemonkeydo.thegoodshepherd.actions;

import android.content.Context;
import android.net.wifi.WifiManager;

public class DisconnectWifi implements Action {
	private Context context;

	public DisconnectWifi(Context context) {
		this.context = context;
	}

	@Override
	public boolean doIt() {
		WifiManager wifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
		wifiManager.setWifiEnabled(false);
		return true;
	}

}
