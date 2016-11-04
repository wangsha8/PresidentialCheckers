package shaojun.presidentialcheckers;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class StartActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
//        initializeControls();
    }

//    public void initializeControls()
//    {
//        final Intent intent = new Intent(this, CheckersActivity.class);
//        final Button friendButton = (Button) findViewById(R.id.Friend);
//        friendButton.setOnClickListener(new View.OnClickListener() {
//            public void onClick(View v) {
//                startActivity(Intent intent);
//            }
//        });
//    }
    public void setupCheckers(View view)
    {
        Intent intent = new Intent(this,CheckersActivity.class);
        startActivity(intent);
    }
}
