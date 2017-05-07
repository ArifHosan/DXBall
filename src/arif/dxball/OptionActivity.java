package arif.dxball;

import android.app.Activity;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class OptionActivity extends Activity {
	Button sound_on,sound_off,vibrate_on,vibrate_off;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.options);
		Typeface font=Typeface.createFromAsset(getAssets(), "font/font1.ttf");
		
		TextView tv2=(TextView)findViewById(R.id.vibrate_textview);
		 vibrate_on=(Button) findViewById(R.id.vibration_on_button);
		 vibrate_off=(Button) findViewById(R.id.vibration_off_button);
		
		tv2.setTypeface(font);
		vibrate_off.setTypeface(font);
		vibrate_on.setTypeface(font);
				
		if(DXBallActivity.VIBRATE_STATUS==true) vibrate_on.setEnabled(false);
		else vibrate_off.setEnabled(false);
	}
	
	public void onClick(View v) {
		SharedPreferences settings=
				getSharedPreferences(DXBallActivity.PREFERENCES_FILE_NAME,0);
		SharedPreferences.Editor editor=settings.edit();
		
		Button btn=(Button)v;
		if(btn.getId()==R.id.vibration_on_button) {
			DXBallActivity.VIBRATE_STATUS=true;
			editor.putString("vibrate", "true");
			editor.commit();
			vibrate_on.setEnabled(false);
			vibrate_off.setEnabled(true);
		}
		else if(btn.getId()==R.id.vibration_off_button) {
			DXBallActivity.VIBRATE_STATUS=false;
			Toast.makeText(this, "Vibration: "+DXBallActivity.VIBRATE_STATUS, Toast.LENGTH_SHORT).show();
			vibrate_on.setEnabled(true);
			vibrate_off.setEnabled(false);
			editor.putString("vibrate", "false");
			editor.commit();
		}
	}
	
}
