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

    public static Handler socketHandler = null;
    private static PrintWriter out;

    public static void startSocket(final String IpAddress, final Integer Port) {
        Log.d("Socket", "startSocket click");
        new Thread(new Runnable() {
            @Override
            public void run() {

                Log.d("Socket", "startSocket run");

                Looper.prepare();

                socketHandler = new Handler() {
                    @Override
                    public void handleMessage(Message msg) {//3、定义处理消息的方法
                        Log.d("Test", "CustomThread Get Message");
                        out.write(msg.getData().getString("message"));
                    }
                };

                try {
                    Socket socket = new Socket(IpAddress, Port);
                    socket.setSoTimeout(13000);
                    // 填充信息
                    Log.d("Socket", "created..");

                    out = new PrintWriter(new BufferedWriter(
                            new OutputStreamWriter(socket.getOutputStream())), true);

                    send("hellow");

                    while (true) {
                    }

                } catch (UnknownHostException e1) {
                    e1.printStackTrace();
                } catch (ConnectException e1) {
                    e1.printStackTrace();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        }).start();
    }

    public static void send(String content) {

        if (socketHandler == null) {
            Log.d("send", "no socketHandler");
            return;
        }

        Message message = new Message();

        Bundle bundle = new Bundle();
        bundle.putString("message", content);

        message.setData(bundle);

        socketHandler.sendMessage(message);
    }
}
