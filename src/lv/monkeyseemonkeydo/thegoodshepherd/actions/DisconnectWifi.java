package lv.monkeyseemonkeydo.thegoodshepherd.actions;

import android.content.Context;
import android.net.wifi.WifiManager;

import com.github.oxo42.stateless4j.delegates.Action;

public class DisconnectWifi implements Action {
	private Context context;

	public DisconnectWifi(Context context) {
		this.context = context;
	}

	@Override
	public void doIt() {
		WifiManager wifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
		wifiManager.setWifiEnabled(false);
	}

}
