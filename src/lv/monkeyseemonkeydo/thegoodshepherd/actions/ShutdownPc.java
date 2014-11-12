package lv.monkeyseemonkeydo.thegoodshepherd.actions;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;

import lv.monkeyseemonkeydo.thegoodshepherd.Consts;
import lv.monkeyseemonkeydo.thegoodshepherd.R;

import org.apache.commons.io.IOUtils;

import android.content.Context;
import android.util.Log;

import com.github.oxo42.stateless4j.delegates.Action;
import com.trilead.ssh2.ChannelCondition;
import com.trilead.ssh2.Connection;
import com.trilead.ssh2.Session;

public class ShutdownPc implements Action {

	private char[] privateKey;

	public ShutdownPc(Context context) {
		try {
			InputStream is = context.getResources().openRawResource(R.raw.private_key);
			privateKey = IOUtils.toCharArray(is);
			is.close();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * Opens SSH shell on target computer and executed "sudo shutdown -h now" over there.
	 */
	@Override
	public void doIt() {
		Connection conn = new Connection(Consts.IP);
		try {
			conn.setTCPNoDelay(true);
			conn.connect();
			conn.authenticateWithPublicKey(Consts.USER, privateKey, null);
			final Session session = conn.openSession();

			// -S makes sudo read password from stdin
			session.execCommand("sudo -S shutdown -h now ");

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
}
