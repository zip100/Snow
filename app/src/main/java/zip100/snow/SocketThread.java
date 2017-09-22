package zip100.snow;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.ConnectException;
import java.net.Socket;
import java.net.UnknownHostException;

/**
 * Created by mike on 2017/9/21.
 */

public class SocketThread {

    public static PrintWriter out = null;
    private static SocketThread instance;

    public void start(final String IpAddress, final Integer Port) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Log.d("SocketThread", "Created Socket");
                    Socket socket = new Socket(IpAddress, Port);
                    socket.setSoTimeout(13000);

                    out = new PrintWriter(new BufferedWriter(
                            new OutputStreamWriter(socket.getOutputStream())), true);
                    while (true) {
                    }
                } catch (Exception e) {
                    Log.d("SocketThread", e.getMessage());
                }
            }
        }).start();
    }

    public static void startSocket(String IpAddress, Integer Port) {
        Log.d("SocketThread", "startSocket");
        instance = new SocketThread();
        instance.start(IpAddress, Port);
    }

    public static void send(String line) {
        Log.d("SocketThread", "send");

        if (null == instance.out) {
            return;
        }

        instance.out.write(line);
        instance.out.flush();
    }

}
