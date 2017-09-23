package zip100.snow;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;

import java.io.BufferedWriter;
import java.io.DataOutputStream;
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

    public static DataOutputStream out = null;
    private static SocketThread instance;

    public void start(final String IpAddress, final Integer Port) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Log.d("SocketThread", "Created Socket");
                    Socket socket = new Socket(IpAddress, Port);
                    socket.setSoTimeout(13000);

                    out = new DataOutputStream(socket.getOutputStream());

                    while (true) {
                        Thread.sleep(1000);
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

    public static void send(Integer left, Integer right, Integer x, Integer y, byte event) {
        Log.d("SocketThread", "send");

        if (null == instance.out) {
            return;
        }
        try {
            instance.out.write(pack(
                    left.byteValue(),
                    right.byteValue(),
                    x,
                    y,
                    event)
            );
            instance.out.flush();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static byte[] pack(byte left, byte right, int x, int y, byte event) {
        return new byte[]{
                left,
                right,

                (byte) ((x >> 24) & 0xFF),
                (byte) ((x >> 16) & 0xFF),
                (byte) ((x >> 8) & 0xFF),
                (byte) (x & 0xFF),

                (byte) ((y >> 24) & 0xFF),
                (byte) ((y >> 16) & 0xFF),
                (byte) ((y >> 8) & 0xFF),
                (byte) (y & 0xFF),

                event,
        };
    }

}
