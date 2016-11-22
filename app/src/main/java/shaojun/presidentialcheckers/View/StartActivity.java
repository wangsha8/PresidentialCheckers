package shaojun.presidentialcheckers.View;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import shaojun.presidentialcheckers.Controller.RulesEngine;
import shaojun.presidentialcheckers.R;

public class StartActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
        RulesEngine.opponent=null;
        RulesEngine.player=null;
        RulesEngine.tiles=null;
        RulesEngine.winner=null;
    }

    public void againstFriendSelected(View view)
    {
//        SharedPreferences sharedPref = this.getSharedPreferences("userpreference",Context.MODE_PRIVATE);
//        sharedPref.edit().putBoolean("robot",false).commit();
        RulesEngine.robot=false;
        //PlayOption.robot=false;
        startActivity(new Intent(StartActivity.this,PopupActivity.class));
    }

    public void againstRobotSelected(View view)
    {
//        SharedPreferences sharedPref = this.getSharedPreferences("userpreference",Context.MODE_PRIVATE);
//        sharedPref.edit().putBoolean("robot",true).commit();
        RulesEngine.robot=true;
        //PlayOption.robot=true;
        startActivity(new Intent(StartActivity.this,PopupActivity.class));
    }
}
