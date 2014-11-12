package lv.monkeyseemonkeydo.thegoodshepherd;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class WifiReceiver extends BroadcastReceiver {

	public static boolean isConnected(Context context) {
    	ConnectivityManager conMan = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = conMan.getActiveNetworkInfo();
    	return netInfo != null && netInfo.getType() == ConnectivityManager.TYPE_WIFI;
	}

	@Override
    public void onReceive(Context context, Intent intent) {
        Intent newIntent = new Intent(context, TriggerDispatcher.class);
        newIntent.setAction(isConnected(context) ? TriggerDispatcher.HANDLE_CONNECTED : TriggerDispatcher.HANDLE_DISCONNECTED);
        context.startService(newIntent);
	}
};