package shaojun.presidentialcheckers.View;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import java.util.List;

import shaojun.presidentialcheckers.Controller.RulesEngine;
import shaojun.presidentialcheckers.Database.GameStat;
import shaojun.presidentialcheckers.Database.GameStatDataSource;
import shaojun.presidentialcheckers.Model.Opponent;
import shaojun.presidentialcheckers.Model.Player;
import shaojun.presidentialcheckers.R;

public class AnnounceActivity extends AppCompatActivity {
    private GameStatDataSource datasource;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_announce);
        TextView announcement = (TextView)findViewById(R.id.announcement);
        TextView hillarymsg = (TextView)findViewById(R.id.hillarymsg);
        TextView trumpmsg = (TextView)findViewById(R.id.trumpmsg);
//        SharedPreferences sharedPref = this.getSharedPreferences("userpreference", Context.MODE_PRIVATE);
        boolean trump=RulesEngine.trump;
        boolean robot=RulesEngine.robot;

        datasource = new GameStatDataSource(this);
        datasource.open();

        datasource.updateNumberOfWinning(trump);
        if ((trump && RulesEngine.winner.getClass()== Player.class)||(!trump && RulesEngine.winner.getClass()== Opponent.class))
        {announcement.setText("TRUMP IS THE WINNER!");}
        else
        {announcement.setText("HILLARY IS THE WINNER!");}
        List<GameStat> gss =datasource.getAllGameStats();
        for (GameStat gs:gss)
        {
            if(gs.id==1)
            {
                trumpmsg.setText("Trump won "+String.valueOf(gs.numberOfWinning)+" times");
            }
            else if (gs.id==2)
            {
                hillarymsg.setText("Hillary won "+String.valueOf(gs.numberOfWinning)+" times");
            }
        }
    }

    @Override
    protected void onResume() {
        datasource.open();
        super.onResume();
    }

    @Override
    protected void onPause() {
        datasource.close();
        super.onPause();
    }

    public void restart(View view)
    {
        startActivity(new Intent(AnnounceActivity.this, StartActivity.class));
    }

    public void sendText(View view)
    {
        String message ="";
        List<GameStat> gss =datasource.getAllGameStats();
        for (GameStat gs:gss)
        {
            if(gs.id==1)
            {
                message = message + "Trump won "+String.valueOf(gs.numberOfWinning)+" times. ";
            }
            else if (gs.id==2)
            {
                message=message + "Hillary won "+String.valueOf(gs.numberOfWinning)+" times. ";
            }
        }
        Intent sendIntent = new Intent(Intent.ACTION_VIEW);
        message = message+"You can download the game at https://github.com/wangsha8/PresidentialCheckers";
        sendIntent.putExtra("sms_body", message);
        sendIntent.setType("vnd.android-dir/mms-sms");
        startActivity(sendIntent);
    }
}
