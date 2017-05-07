package arif.dxball;

import android.app.Activity;
import android.app.Application;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class DXBallActivity extends Activity {
	public static Boolean VIBRATE_STATUS;
	public static final String PREFERENCES_FILE_NAME="MyPrefs";
	public static final String GAME_OVER="GAME OVER";
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        VIBRATE_STATUS=true;
        
        Typeface font=Typeface.createFromAsset(getAssets(), "font/font1.ttf");
        
        Button new_game=(Button) findViewById(R.id.new_game_button);
        Button option=(Button) findViewById(R.id.option_button);
        //Button about=(Button) findViewById(R.id.about_button);
        Button high_score=(Button) findViewById(R.id.high_score_button);
        Button exit=(Button) findViewById(R.id.exit_button);
        
        new_game.setTypeface(font);
        option.setTypeface(font);
        //about.setTypeface(font);
        high_score.setTypeface(font);
        exit.setTypeface(font);
        
        SharedPreferences settings=
				getSharedPreferences(DXBallActivity.PREFERENCES_FILE_NAME,0);
        String s2=settings.getString("vibrate", "true");
        if(s2.equals("false")) VIBRATE_STATUS=false;
    }
    
    public void onExit(View v) {
    	finish();
    }
    public void onScore(View v) {
    	Intent i=new Intent(this,ScoreActivity.class);
    	startActivity(i);
    }
    public void onOption(View v) {
    	Intent i=new Intent(this,OptionActivity.class);
    	startActivity(i);
    }
    public void onPlay(View v) {
    	startActivity(new Intent(this,GameActivity.class));
    }
}