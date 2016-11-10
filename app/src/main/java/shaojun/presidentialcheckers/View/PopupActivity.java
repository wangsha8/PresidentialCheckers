package shaojun.presidentialcheckers.View;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import shaojun.presidentialcheckers.Model.PlayOption;
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
        PlayOption.trump=true;
        startActivity(new Intent(PopupActivity.this, CheckersActivity.class));
    }

    public void hillarySelected(View view)
    {
        PlayOption.trump=false;
        startActivity(new Intent(PopupActivity.this, CheckersActivity.class));
    }
}
