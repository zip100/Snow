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

    public static PrintWriter out;
    private static SocketThread instance;
    private Handler handler = new Handler();

    public void start() {
        Log.d("Socket", "startSocket click");
        new Thread(new Runnable() {
            @Override
            public void run() {

                Log.d("Socket", "startSocket run");
                Looper.prepare();
                handler = new Handler(){
                    @Override
                    public void handleMessage(Message msg) {
                        Log.d("Socket", "get message..");
                        try {
                            out.write("heheda");
                            out.flush();
                        }catch (Exception e){
                            e.printStackTrace();
                        }
                    }
                };

                try {
                    Socket socket = new Socket("172.19.21.128", 81);
                    socket.setSoTimeout(13000);
                    // 填充信息
                    Log.d("Socket", "created..");

                    out = new PrintWriter(new BufferedWriter(
                            new OutputStreamWriter(socket.getOutputStream())), true);
                    while (true) {
                    }
                } catch (UnknownHostException e1) {
                    e1.printStackTrace();
                } catch (ConnectException e1) {
                    e1.printStackTrace();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }

                Log.d("Socket", "End Socket");
            }
        }).start();
    }


    public static void startSocket(String IpAddress, Integer Port) {
        instance = new SocketThread();
        instance.start();
    }

    public static void send(String line){
        instance.out.write(line);
        instance.out.flush();
    }

}
