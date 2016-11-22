package shaojun.presidentialcheckers.View;

import android.content.Intent;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.view.MotionEvent;

import java.util.Random;

import shaojun.presidentialcheckers.Controller.CheckersPlayController;
import shaojun.presidentialcheckers.Controller.RulesEngine;
import shaojun.presidentialcheckers.R;

public class CheckersActivity extends AppCompatActivity
{
    private int[] trumpAudio = new int[5];
    private int[] hillaryAudio = new int[5];
    {
        trumpAudio[0]=R.raw.trump1;
        trumpAudio[1]=R.raw.trump2;
        trumpAudio[2]=R.raw.trump3;
        trumpAudio[3]=R.raw.trump4;
        trumpAudio[4]=R.raw.trump5;
        hillaryAudio[0]=R.raw.hillary1;
        hillaryAudio[1]=R.raw.hillary2;
        hillaryAudio[2]=R.raw.hillary3;
        hillaryAudio[3]=R.raw.hillary4;
        hillaryAudio[4]=R.raw.hillary5;

    }

    private MediaPlayer mPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkers);

        RulesEngine.checkersActivity=this;

        final CheckersPlayController view = (CheckersPlayController)findViewById(R.id.board);
        view.hillaryscore=(TextView)findViewById(R.id.hillaryscore);
        view.trumpscore=(TextView)findViewById(R.id.trumpscore);
        view.hillaryhead=(ImageView)findViewById(R.id.hillaryhead);
        view.trumphead=(ImageView)findViewById(R.id.trumphead);
        //SharedPreferences sharedPref = this.getSharedPreferences("userpreference", Context.MODE_PRIVATE);
        view.setOnTouchListener(new View.OnTouchListener(){
            @Override
            public boolean onTouch(View iview, MotionEvent event) {
                view.handleTouch(event);
                //RulesEngine.winner=new Player();
                if(RulesEngine.winner!=null)
                {startActivity(new Intent(CheckersActivity.this, AnnounceActivity.class));}
                return false;
            }
        });
    }

    public void playHillary()
    {
        Random ran = new Random();
        int n = ran.nextInt(5);
        stopPlaying();
        mPlayer = MediaPlayer.create(CheckersActivity.this, hillaryAudio[n]);
        mPlayer.start();
    }

    public void playTrump()
    {
        Random ran = new Random();
        int n = ran.nextInt(5);
        stopPlaying();
        mPlayer = MediaPlayer.create(CheckersActivity.this, trumpAudio[n]);
        mPlayer.start();
    }

    private void stopPlaying() {
        if (mPlayer != null)
        {
            mPlayer.stop();
            mPlayer.release();
            mPlayer = null;
        }
    }
}
