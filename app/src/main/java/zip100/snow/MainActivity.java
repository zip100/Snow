package zip100.snow;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, View.OnTouchListener {

    float x1 = 0;
    float x2 = 0;
    float y1 = 0;
    float y2 = 0;

    public float left, right = 0;
    public float offset = 20;


    Integer screenHeight, screenWidth;

    private final String imgUrl = "http://codelife.jios.org:807/?action=stream";
    private final String ipAddress = "192.168.199.137";
    private final Integer port = 81;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);//去掉信息栏
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);


        findViewById(R.id.web).setOnTouchListener(this);
        findViewById(R.id.left).setOnTouchListener(this);
        findViewById(R.id.right).setOnTouchListener(this);
        SocketThread.startSocket(ipAddress, port);

        initImg(imgUrl);

        WindowManager vm = (WindowManager) getApplicationContext().getSystemService(Context.WINDOW_SERVICE);
        screenHeight = vm.getDefaultDisplay().getHeight();
        screenWidth = vm.getDefaultDisplay().getWidth();
    }

    @Override
    public void onClick(View view) {
    }

    @Override
    public boolean onTouch(View view, MotionEvent event) {
        if (view.getId() == R.id.left || view.getId() == R.id.right) {
            float rate = event.getY() / screenHeight * 255;
            Integer a = (int) rate;

            if ((view.getId() == R.id.left && (a - left > offset || a - left < -offset))) {
                left = a;
                SocketThread.send("Left:" + a.toString());
            }

            if (view.getId() == R.id.right && (a - right > offset || a - right < -offset)) {
                right = a;
                SocketThread.send("Right:" + a.toString());
            }
            if (event.getAction() == MotionEvent.ACTION_UP) {
                SocketThread.send("Up...");
            }
        }

        if (view.getId() == R.id.web) {
            if (event.getAction() == MotionEvent.ACTION_DOWN) {
                x1 = event.getX();
                y1 = event.getY();
            }

            if (event.getAction() == MotionEvent.ACTION_UP) {
                String line = "";
                x2 = event.getX();
                y2 = event.getY();
                if (y1 - y2 > 50) {
                    line = "向上滑" + String.valueOf(y1 - y2);
                } else if (y2 - y1 > 50) {
                    line = "向下滑" + String.valueOf(y2 - y1);
                } else if (x1 - x2 > 50) {
                    line = "向左滑" + String.valueOf(x1 - x2);
                } else if (x2 - x1 > 50) {
                    line = "向右滑" + String.valueOf(x2 - x1);
                }
                SocketThread.send(line);
            }
        }

        return true;
    }

    private void initImg(String url) {
        WebView web = (WebView) findViewById(R.id.web);
        web.loadUrl(url);
        web.setWebViewClient(new WebViewClient());
    }
}
