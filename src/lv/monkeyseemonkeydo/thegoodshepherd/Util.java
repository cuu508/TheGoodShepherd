package lv.monkeyseemonkeydo.thegoodshepherd;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;

import android.util.Log;

import com.trilead.ssh2.ChannelCondition;
import com.trilead.ssh2.Connection;
import com.trilead.ssh2.Session;

public class Util {
	private static final String HOST = "192.168.1.120";
	private static final String USER = "cepe";
	private static final String PASSWORD = "z";

	public static void runSudo(String command) {
		Connection conn = new Connection(HOST);
		try {
			conn.setTCPNoDelay(true);
			conn.connect();
			conn.authenticateWithPassword(USER, PASSWORD);
			final Session session = conn.openSession();

			// -S makes sudo read password from stdin
			session.execCommand("sudo -S " + command);

			OutputStream os = session.getStdin();
			PrintWriter pw = new PrintWriter(os);
			pw.write(PASSWORD + "\n");
			pw.close();
			os.close();

			session.waitForCondition(ChannelCondition.EXIT_SIGNAL, 0);

			InputStream is = session.getStdout();
			BufferedReader r = new BufferedReader(new InputStreamReader(is));
			StringBuilder total = new StringBuilder();
			String line;
			while ((line = r.readLine()) != null)
				total.append(line);

			r.close();
			is.close();

			is = session.getStderr();
			r = new BufferedReader(new InputStreamReader(is));
			StringBuilder err = new StringBuilder();
			while ((line = r.readLine()) != null)
				err.append(line);

			r.close();
			is.close();

			session.close();
			conn.close();

			Log.d("TGS", "Response: " + total.toString());
			Log.d("TGS", "Err: " + err.toString());
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
}
