package lv.monkeyseemonkeydo.thegoodshepherd;

import lv.monkeyseemonkeydo.thegoodshepherd.actions.ShutdownPc;
import lv.monkeyseemonkeydo.thegoodshepherd.actions.WakePc;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;

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

	}

	public void onWakeClick(View v) {
		(new Thread() {
			@Override
			public void run() {
				// TODO Auto-generated method stub
				(new WakePc()).doIt();
			}
			
		}).start();
	}
	
	public void onShutdownClick(View v) {
		(new Thread() {
			@Override
			public void run() {
				// TODO Auto-generated method stub
				(new ShutdownPc(MainActivity.this)).doIt();
			}
			
		}).start();
	}
	

}
