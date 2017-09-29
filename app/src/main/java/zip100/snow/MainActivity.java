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
    public float offset = 30;
    public Integer defaultValue = 0;

    private final byte EVENT_LEFT_SPEED = 1;
    private final byte EVENT_RIGHR_SPEED = 2;
    private final byte EVENT_START_MOVE = 3;
    private final byte EVENT_END_MOVE = 4;
    private final byte EVENT_LEFT_SPEED_END = 5;
    private final byte EVENT_RIGHR_SPEED_END = 6;
    private final byte EVENT_END_MOVING = 7;


    Integer screenHeight, screenWidth;

    private final String imgUrl = "http://192.168.199.121:8080/?action=stream";
    private final String ipAddress = "192.168.199.121";
    private final Integer port = 1500;

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


                Integer dd = (int) ((10 * event.getY() / 1080) + 10);
                SocketThread.send(dd, 0, 0, 0, EVENT_LEFT_SPEED);
            }

            if (view.getId() == R.id.right && (a - right > offset || a - right < -offset)) {
                //right = a;
                //SocketThread.send(0, 356 - a, 0, 0, EVENT_RIGHR_SPEED);
                Integer dd = (int) ((10 * event.getY() / 1080) + 10);
                SocketThread.send(0, dd, 0, 0, EVENT_LEFT_SPEED);
            }

            if (event.getAction() == MotionEvent.ACTION_UP) {
                if (view.getId() == R.id.left) {
                    SocketThread.send(15, 0, 0, 0, EVENT_LEFT_SPEED_END);
                }
                if (view.getId() == R.id.right) {
                    SocketThread.send(0, 15, 0, 0, EVENT_RIGHR_SPEED_END);
                }
            }

        }

        if (view.getId() == R.id.web) {
            if (event.getAction() == MotionEvent.ACTION_DOWN) {
                x1 = event.getX();
                y1 = event.getY();
                SocketThread.send(0, 0, (int) x1, (int) y1, EVENT_START_MOVE);
            }


            Float a = event.getX();
            if (event.getAction() == MotionEvent.ACTION_MOVE && x1 > 0) {
                Float mx = event.getX() - x1;
                Float my = event.getY() - y1;
                Log.d("move", mx.toString());
                SocketThread.send(0, 0, (int) event.getX(), (int) event.getY(), EVENT_END_MOVING);
            }


            if (event.getAction() == MotionEvent.ACTION_UP) {
                String line = "";
                x2 = event.getX();
                y2 = event.getY();

                SocketThread.send(0, 0, (int) x2, (int) y2, EVENT_END_MOVE);
                x1 = y1 = 0;

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
