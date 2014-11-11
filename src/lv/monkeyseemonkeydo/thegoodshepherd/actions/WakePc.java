package lv.monkeyseemonkeydo.thegoodshepherd.actions;

import java.io.IOException;

import lv.monkeyseemonkeydo.thegoodshepherd.Consts;
import net.mafro.android.wakeonlan.MagicPacket;

import com.github.oxo42.stateless4j.delegates.Action;

public class WakePc implements Action {

	@Override
	public void doIt() {
		try {
			MagicPacket.send(Consts.MAC, Consts.IP);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

}
