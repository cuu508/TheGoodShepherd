package lv.monkeyseemonkeydo.thegoodshepherd;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class WifiReceiver extends BroadcastReceiver {

	@Override
    public void onReceive(Context context, Intent intent) {
    	ConnectivityManager conMan = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = conMan.getActiveNetworkInfo();
    	boolean isConnected = (netInfo != null && netInfo.getType() == ConnectivityManager.TYPE_WIFI);

        Intent newIntent = new Intent(context, ProxyService.class);
        newIntent.setAction(isConnected ? ProxyService.HANDLE_CONNECTED : ProxyService.HANDLE_DISCONNECTED);
        context.startService(newIntent);
	}
};