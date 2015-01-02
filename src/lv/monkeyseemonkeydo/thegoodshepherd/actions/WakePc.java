package lv.monkeyseemonkeydo.thegoodshepherd.actions;

import java.io.IOException;

import lv.monkeyseemonkeydo.thegoodshepherd.Consts;
import net.mafro.android.wakeonlan.MagicPacket;

public class WakePc implements Action {

	@Override
	public boolean doIt() {
		try {
			MagicPacket.send(Consts.MAC, Consts.IP);
			return true;
		} catch (IOException e) {
			return false;
		}
	}

}
