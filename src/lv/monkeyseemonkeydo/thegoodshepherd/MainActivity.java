package lv.monkeyseemonkeydo.thegoodshepherd;

import android.app.Activity;
import android.os.Bundle;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();


		// Let's test our SSH thingy
		(new Thread() {

			@Override
			public void run() {
				//Util.runSudo(getResources(), "uname -a");
				Util.sendWakeOnLanPacket();
			}

		}).start();

	}
}
