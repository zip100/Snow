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


    Integer screenHeight, screenWidth;

    private final String imgUrl = "http://codelife.jios.org:807/?action=stream";
    private final String ipAddress = "172.19.21.128";
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
        //SocketThread.startSocket(ipAddress, port);

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
        if (view.getId() == R.id.web) {
            Log.d("touch-img", String.valueOf(event.getX()) + "-" + String.valueOf(event.getY()));
            //SocketThread.send(String.valueOf(event.getX()) + "-" + String.valueOf(event.getY()));
        }


        if (view.getId() == R.id.left) {

            Float a = event.getY() / screenHeight;

            Log.d("touch-left", a.toString());
        }

        if (view.getId() == R.id.right) {

            Float a = event.getY() / screenHeight;

            Log.d("touch-right", a.toString());
        }

        //SocketThread.send(String.valueOf(event.getX()) + "-" +String.valueOf(event.getY()));
//
//        if (event.getAction() == MotionEvent.ACTION_DOWN) {
//            x1 = event.getX();
//            y1 = event.getY();
//        }
//
//        if (event.getAction() == MotionEvent.ACTION_UP) {
//            String line = "";
//            x2 = event.getX();
//            y2 = event.getY();
//            if (y1 - y2 > 50) {
//                line = "向上滑" + String.valueOf(y1 - y2);
//            } else if (y2 - y1 > 50) {
//                line = "向下滑" + String.valueOf(y2 - y1);
//            } else if (x1 - x2 > 50) {
//                line = "向左滑" + String.valueOf(x1 - x2);
//            } else if (x2 - x1 > 50) {
//                line = "向右滑" + String.valueOf(x2 - x1);
//            }
//            SocketThread.send(line);
//        }
//

        return true;
    }

    private void initImg(String url) {
        WebView web = (WebView) findViewById(R.id.web);
        web.loadUrl(url);
        web.setWebViewClient(new WebViewClient());
    }
}
