package arif.dxball;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.Log;

public abstract class Drawable {
	
	private double x,y;
	private Paint paint;
	private String type;
	public Bitmap bitmap;
	public abstract void Draw(Canvas canvas);
	public abstract Status Collision(Drawable d);
	public double getX() {
		return this.x;
	}
	public void setX(double x) {
		//Log.d("info", ""+x+" "+this.x);
		this.x = x;
	}
	public double getY() {
		return y;
	}
	public void setY(double y) {
		this.y = y;
	}
	public Paint getPaint() {
		return paint;
	}
	public void setPaint(Paint paint) {
		this.paint = paint;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
}
