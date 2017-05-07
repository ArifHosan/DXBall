package arif.dxball;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.os.Vibrator;
import android.text.TextPaint;
import android.util.Log;
import android.util.Pair;
import android.view.MotionEvent;
import android.view.View;

public class GameCanvas extends View{
	private Boolean isTouched;
	private Pair<Float, Float>TouchPoint;
	Paint textPaint;
	//ArrayList<Drawable>objects;
	//ArrayList<Drawable>balls;
	Context context;
	Boolean first;
	Thread t;
	Stage stage;
	Vibrator vibrator;
	
	public GameCanvas(Context context) {
		super(context);
		isTouched=false;
		GameActivity.notTouched=true;
		GameActivity.gameOver=false;
		this.context=context;
		first=true;
		textPaint=new Paint();
		textPaint.setStyle(Paint.Style.FILL);
		textPaint.setColor(Color.RED);
		textPaint.setTextSize(48);
		stage=new Stage(this.context);
		vibrator=(Vibrator)context.getSystemService(Context.VIBRATOR_SERVICE);
		/*t=new Thread(new Runnable() {
			public void run() {
				while(true) {
					invalidate();
					try{
						Thread.sleep(30);
					}
					catch(Exception e) {}
				}
			}
		});
		
		t.run();*/
		stage.Load();
	}
	
	public void onDraw(Canvas canvas) {
		ArrayList<Drawable>removeList=new ArrayList<Drawable>();
		if(stage.objectList.size()==1) {
			stage.Load();
			if(stage.objectList.size()<=1) {
				GameActivity.gameOver=true;
			}
			else {
				GameActivity.notTouched=true;
			}
		}
		canvas.drawRGB(255, 255, 255);
		if(GameActivity.gameOver){
			canvas.drawText("Game Over", canvas.getWidth()/2-100, canvas.getHeight()/2, textPaint);
			canvas.drawText("Score: "+stage.score, canvas.getWidth()/2-100, canvas.getHeight()/2+50, textPaint);
			return;
		}
		for (Drawable b : stage.balls) {
			((Ball) b).onMove(canvas);
			if(((Ball) b).isLost(canvas)) {
				stage.lifeCount--;
				if(DXBallActivity.VIBRATE_STATUS)
					vibrator.vibrate(500);
				((Ball) b).reset();
				for (Drawable d : stage.objectList) if(d.getType().equals("bar")) ((Bar) d).reset();
				GameActivity.notTouched=true;
				if(stage.lifeCount<=0) {
					GameActivity.gameOver=true;
				}
			}
			for(Drawable d:stage.objectList) {
				Status res=d.Collision(b);
				if(res==Status.NONE) continue;
				/*if(res==Status.BAR_TOP_LEFT) {
					((Ball) b).changeAngle(10);
				}
				else if(res==Status.BAR_TOP_RIGHT) {
					((Ball) b).changeAngle(-10);
				}
				else if(res==Status.TOP) {
					((Ball) b).changeDy();
				}
				else if(res==Status.SIDE) {
					((Ball) b).changeDx();
				}*/
				/*else if(res==Status.CORNER) {
					((Ball) b).changeDy();
					((Ball) b).changeDx();
				} */
				//else if(res==Status.INSIDE_TOP || res==Status.INSIDE_SIDE || res==Status.INSIDE_CORNER) {
				else {
					((Ball) b).HandleError(res, d);
					if(d.getType().equals("bar")) {
						Status ret=((Bar) d).BarAngle(b);
						if(ret==Status.BAR_TOP_LEFT) {
							((Ball) b).changeAngle(10);
						}
						else if(ret==Status.BAR_TOP_RIGHT) {
							((Ball) b).changeAngle(-10);
						}
					}
				}
				if(d.getType().equals("brick")) {
					removeList.add(d);
					stage.score+=20;
				}	
			}
			b.Draw(canvas);
		}
		stage.objectList.removeAll(removeList);
		removeList.clear();
		for (Drawable d : stage.objectList) {
			if(d.getType().equals("bar") && isTouched) ((Bar) d).onMove(canvas,TouchPoint);
			d.Draw(canvas);
		}
		stage.DrawLife(canvas);
		stage.drawScore(canvas);
		
		if(GameActivity.notTouched) {
			canvas.drawText("Stage "+(stage.StageCount-1), canvas.getWidth()/2-100, canvas.getHeight()/2, textPaint);
		}
		invalidate();
		//t.run();
	}
	
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		if(GameActivity.notTouched) {
			GameActivity.notTouched=false;
			return false;
		}
		if(GameActivity.gameOver) {
			SharedPreferences settings=
					context.getSharedPreferences(DXBallActivity.PREFERENCES_FILE_NAME,0);
			SharedPreferences.Editor editor=settings.edit();
			Log.d("score",""+stage.score);
			editor.putInt("score", stage.score);
			editor.commit();
			((Activity)context).finish();
			return false;
		}
		if(event.getAction()==MotionEvent.ACTION_DOWN) {
			isTouched=true;
			//Log.d("Info", "Values: "+event.getX()+" "+event.getY());
			TouchPoint=Pair.create(event.getX(), event.getY());
			return true;
		}
		else if(event.getAction()==MotionEvent.ACTION_UP) {
			isTouched=false;
			//Log.d("Info", "Touch Up Occurred!!");
		}
		return false;
		//return super.onTouchEvent(event);
	}
}
