package shaojun.presidentialcheckers.View;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;

import shaojun.presidentialcheckers.Controller.RulesEngine;
import shaojun.presidentialcheckers.R;

/**
 * Created by shaojun on 11/10/16.
 */

public class PopupActivity extends Activity
{
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.popupwindow);
    }

    public void trumpSelected(View view)
    {
//        SharedPreferences sharedPref = this.getSharedPreferences("userpreference", Context.MODE_PRIVATE);
//        sharedPref.edit().putBoolean("trump",true).commit();
        RulesEngine.trump=true;
        //PlayOption.trump=true;
        startActivity(new Intent(PopupActivity.this, CheckersActivity.class));
    }

    public void hillarySelected(View view)
    {
//        SharedPreferences sharedPref = this.getSharedPreferences("userpreference", Context.MODE_PRIVATE);
//        sharedPref.edit().putBoolean("trump",false).commit();
        RulesEngine.trump=false;
        //PlayOption.trump=false;
        startActivity(new Intent(PopupActivity.this, CheckersActivity.class));
    }
}
