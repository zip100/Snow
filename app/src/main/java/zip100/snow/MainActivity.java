package zip100.snow;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.button_connect).setOnClickListener(this);
    }

    @Override
    public void onClick(View view){
        if(view.getId() == R.id.button_connect){
            Intent intent = new Intent();
            intent.setClass(this,ConnectActivity.class);
            startActivity(intent);
        }
    }
}
