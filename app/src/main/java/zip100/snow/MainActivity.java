package zip100.snow;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, View.OnTouchListener {

    float x1 = 0;
    float x2 = 0;
    float y1 = 0;
    float y2 = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.button_connect).setOnClickListener(this);
        findViewById(R.id.activify_main).setOnTouchListener(this);
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.button_connect) {
            Intent intent = new Intent();
            intent.setClass(this, ConnectActivity.class);
            startActivity(intent);
        }
    }

    @Override
    public boolean onTouch(View var1, MotionEvent event) {


        Log.d("touch", "X:" + String.valueOf(event.getX()) + "  Y:" + String.valueOf(event.getY()));


        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            x1 = event.getX();
            y1 = event.getY();
        }

        if (event.getAction() == MotionEvent.ACTION_UP) {
            x2 = event.getX();
            y2 = event.getY();
            if (y1 - y2 > 50) {
                Log.d("touch", "向上滑" + String.valueOf(y1 - y2));
            } else if (y2 - y1 > 50) {
                Log.d("touch", "向下滑" + String.valueOf(y2 - y1));
            } else if (x1 - x2 > 50) {
                Log.d("touch", "向左滑" + String.valueOf(x1 - x2));
            } else if (x2 - x1 > 50) {
                Log.d("touch", "向右滑" + String.valueOf(x2 - x1));
            }
        }

        return true;
    }
}
