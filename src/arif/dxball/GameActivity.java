package arif.dxball;

import android.app.Activity;
import android.graphics.Point;
import android.os.Bundle;
import android.view.Display;
import android.view.Window;
import android.view.WindowManager;

public class GameActivity extends Activity {
	public static Boolean notTouched,gameOver;
	public static Point displaySize;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		notTouched=true;
		gameOver=false;
		requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        displaySize=new Point();
		Display display=getWindowManager().getDefaultDisplay();
		displaySize.x=display.getWidth();
		displaySize.y=display.getHeight();
        setContentView(new GameCanvas(this));
	}
	
}
