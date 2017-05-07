package arif.dxball;

import android.app.Activity;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class ScoreActivity extends Activity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.score);
		
		Typeface font=Typeface.createFromAsset(getAssets(), "font/font1.ttf");
		TextView tv=(TextView)findViewById(R.id.score_textview);
		TextView tv2=(TextView)findViewById(R.id.score_label_textview);
		
		tv.setTypeface(font);
		tv2.setTypeface(font);
		
		SharedPreferences settings=
				getSharedPreferences(DXBallActivity.PREFERENCES_FILE_NAME,0);
		Integer score=settings.getInt("score", 0);
		tv.setText(String.valueOf(score));
	}
	
	public void onClick(View v){
		SharedPreferences settings=
				getSharedPreferences(DXBallActivity.PREFERENCES_FILE_NAME,0);
		SharedPreferences.Editor editor=settings.edit();
		editor.putInt("score", 0);
		TextView tv=(TextView)findViewById(R.id.score_textview);
		tv.setText("0");
		editor.commit();
	}

}
