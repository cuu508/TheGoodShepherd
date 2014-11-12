package lv.monkeyseemonkeydo.thegoodshepherd;

import lv.monkeyseemonkeydo.thegoodshepherd.machines.Pc;
import lv.monkeyseemonkeydo.thegoodshepherd.machines.Wemo;
import lv.monkeyseemonkeydo.thegoodshepherd.machines.Wifi;
import android.app.Application;
import android.content.Context;

public class TheGoodApplication extends Application {

	public Pc pc;
	public Wemo wemo;
	public Wifi wifi;

	@Override
	public void onCreate() {
		super.onCreate();

		pc = new Pc(Pc.State.IsOff, this);
		wemo = new Wemo(Wemo.State.ReachableOff, this);
		wifi = new Wifi(Wifi.State.Connected, this);
	}


}
