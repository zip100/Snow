package zip100.snow;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, View.OnTouchListener {

    float x1 = 0;
    float x2 = 0;
    float y1 = 0;
    float y2 = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.activify_main).setOnTouchListener(this);
        SocketThread.startSocket("192.168.199.137", 81);


        WebView web = (WebView)findViewById(R.id.web);
        web.loadUrl("http://codelife.jios.org:807/?action=stream");
        web.setWebViewClient(new WebViewClient());
    }

    @Override
    public void onClick(View view) {
    }

    @Override
    public boolean onTouch(View var1, MotionEvent event) {
        SocketThread.send(String.valueOf(event.getX()) + "-" +String.valueOf(event.getY()));
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
}
