package zip100.snow;

import android.os.Handler;
import android.os.Message;
import android.os.StrictMode;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.ConnectException;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.Socket;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.Enumeration;
import java.util.Timer;
import java.util.logging.LogRecord;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


public class ConnectActivity extends AppCompatActivity {

    private static String IpAddress = "172.19.21.128";
    private static int Port = 81;
    Socket socket = null;

    public static final int EXTCEPTION = 1;

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == EXTCEPTION) {
                Bundle data = msg.getData();
                AlertDialog.Builder builder = new AlertDialog.Builder(ConnectActivity.this);
                builder.setMessage(data.getString("msg"));
                builder.setTitle("提示");
                builder.show();
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_connect);

        StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder().detectDiskReads().detectDiskWrites().detectNetwork().penaltyLog().build());


        findViewById(R.id.button_connect).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            // 创建socket对象，指定服务器端地址和端口号
                            socket = new Socket(IpAddress, Port);
                            // 填充信息
                            Log.d("Socket", "msg=");


                            // 获取 Client 端的输出流
                            PrintWriter out = new PrintWriter(new BufferedWriter(
                                    new OutputStreamWriter(socket.getOutputStream())), true);

                            while (true) {
                                Log.d("erro", "loop");
                                try {
                                    Thread.sleep(500);

                                    // 填充信息
                                    //out.println("guoi ahhaah");
                                    out.write("ahhaah");
                                    out.flush();
                                } catch (InterruptedException e) {
                                    Log.d("erro", e.getMessage());
                                    // 关闭
                                    socket.close();
                                }
                            }

                        } catch (UnknownHostException e1) {
                            e1.printStackTrace();
                        } catch (ConnectException e) {
                            Message msg = new Message();
                            msg.what = EXTCEPTION;

                            Bundle data = new Bundle();
                            data.putString("msg", e.getMessage());

                            msg.setData(data);

                            handler.sendMessage(msg);

                        } catch (IOException e1) {
                            e1.printStackTrace();
                        }
                    }
                }).start();
            }
        });
    }
}
