package shaojun.presidentialcheckers.View;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import shaojun.presidentialcheckers.Model.PlayOption;
import shaojun.presidentialcheckers.R;

public class StartActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
    }

    public void againstFriendSelected(View view)
    {
        PlayOption.robot=false;
        startActivity(new Intent(StartActivity.this,PopupActivity.class));
    }

    public void againstRobotSelected(View view)
    {
        PlayOption.robot=true;
        startActivity(new Intent(StartActivity.this,PopupActivity.class));
    }
}
