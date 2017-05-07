package arif.dxball;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.util.Log;
import android.util.Pair;

public class Bar extends Brick {
	private Boolean first;
	
	Bar() {
		first=true;
		setType("bar");
	}
	
	/*@Override
	public Status Collision(Drawable d) {
		Status res=super.Collision(d);
		if(res!=Status.TOP || res!=Status.INSIDE_TOP) return res;
		//Log.d("Debug", ""+d.getX()+" "+d.getY()+" Bar: "+this.getX()+" "+this.getY());
		
		if(d.getX()<=this.getX()+this.getWidth()/3) return Status.BAR_TOP_LEFT;
		else if(d.getX()>=this.getX()+2*this.getWidth()/3) return Status.BAR_TOP_RIGHT;
		else return Status.BAR_TOP_MIDDLE;
	}*/
	
	public Status BarAngle(Drawable d) {
		if(d.getX()<=this.getX()+this.getWidth()/3) return Status.BAR_TOP_LEFT;
		else if(d.getX()>=this.getX()+2*this.getWidth()/3) return Status.BAR_TOP_RIGHT;
		else return Status.BAR_TOP_MIDDLE;
	}
	
	public void onMove(Canvas canvas, Pair<Float,Float>TouchPoint) {
		double x=this.getX();
		if(TouchPoint.first<canvas.getWidth()/2) this.setX(x-15);
		else this.setX(x+15);
		//Log.d("Info", "Values: "+this.getX());
	}
	
	public void reset(){
		first=true;
	}
	
	@Override
	public void Draw(Canvas canvas) {
		if(first) {
			setX((canvas.getWidth()/2)-(getWidth()/2));
			setY(canvas.getHeight()-getHeight());
			first=false;
		}
		if(getX()<0) setX(0);
		else if(getX()+getWidth()>canvas.getWidth())setX(canvas.getWidth()-getWidth());
		//canvas.drawRect((float)getX(), (float)getY(), (float)(getX()+getWidth()), (float)(getY()+getHeight()), getPaint());
		//if(bitmap!=null) canvas.drawBitmap(Bitmap.createScaledBitmap(bitmap, this.getWidth(), this.getHeight(),false),(float) getX(),(float)getY(),getPaint());
		if(bitmap!=null) canvas.drawBitmap(bitmap, (float) getX(),(float)getY(),getPaint());
	}
}
