package lv.monkeyseemonkeydo.thegoodshepherd;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;

import net.mafro.android.wakeonlan.MagicPacket;

import org.apache.commons.io.IOUtils;

import android.content.res.Resources;
import android.util.Log;

import com.trilead.ssh2.ChannelCondition;
import com.trilead.ssh2.Connection;
import com.trilead.ssh2.Session;

public class Util {

	private static char[] getPrivateKey(Resources res) throws IOException {
		InputStream is = res.openRawResource(R.raw.private_key);
		char[] key = IOUtils.toCharArray(is);
		is.close();

		return key;
	}

	public static void runSudo(Resources res, String command) {
		Connection conn = new Connection(Consts.IP);
		try {
			conn.setTCPNoDelay(true);
			conn.connect();
			conn.authenticateWithPublicKey(Consts.USER, getPrivateKey(res), null);
			final Session session = conn.openSession();

			// -S makes sudo read password from stdin
			session.execCommand("sudo -S " + command);

			OutputStream os = session.getStdin();
			PrintWriter pw = new PrintWriter(os);
			pw.write(Consts.PASSWORD + "\n");
			pw.close();
			os.close();

			session.waitForCondition(ChannelCondition.EXIT_SIGNAL, 0);

			InputStream is = session.getStdout();
			String stdout = IOUtils.toString(is);
			is.close();

			is = session.getStderr();
			String stderr = IOUtils.toString(is);
			is.close();

			session.close();
			conn.close();

			Log.d("TGS", "Response: " + stdout.toString());
			Log.d("TGS", "Err: " + stderr.toString());
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	public static void sendWakeOnLanPacket() {
		try {
			MagicPacket.send(Consts.MAC, Consts.IP);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
}
