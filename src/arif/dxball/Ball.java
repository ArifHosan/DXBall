package arif.dxball;

import java.util.ArrayList;
import java.util.List;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.util.Log;

public class Ball extends Drawable {
	private double radius;
	private Boolean first;
	private double dx,dy;
	private int angle;
	private double speed;
	Ball() {
		first=true;
		setType("ball");
		setDx(1); setDy(-1);
		speed=20;
		angle=45;
	}
	
	public void reset() {
		first=true;
		setDx(1); setDy(-1);
		angle=45;
	}
	
	@Override
	public void Draw(Canvas canvas) {
		//Log.d(" ",""+angle);
		if(first) {
			setX(canvas.getWidth()/2);
			setY(canvas.getHeight()-this.getRadius()-51);
			first=false;
		}
		if(bitmap!=null) canvas.drawBitmap(bitmap, (float) (getX()-this.getRadius()),(float)(getY()-this.getRadius()),getPaint());
		//if(bitmap!=null) canvas.drawBitmap(Bitmap.createScaledBitmap(bitmap, (int)(this.getRadius()*2), (int)(this.getRadius()*2),false),(float) (getX()-this.getRadius()),(float)(getY()-this.getRadius()),getPaint());
		//canvas.drawCircle((float)getX(), (float)getY(), (float)getRadius(), getPaint());
	}

	
	@Override
	public Status Collision(Drawable d) {
		return Status.NONE;
	}

	public double getRadius() {
		return radius;
	}

	public void setRadius(double radius) {
		this.radius = radius;
	}

	public void onMove(Canvas canvas) {
		if(GameActivity.notTouched) return;
		this.setX(this.getX()+getDx()*speed);
		this.setY(this.getY()+getDy()*speed);
		
		//if(this.getY()+this.getRadius()>=canvas.getHeight())
		
		if(this.getX()<this.getRadius()) {
			this.setX(this.radius);
			setDx(getDx() * -1);
		}
		else if(this.getX()+this.getRadius()>canvas.getWidth()) {
			this.setX(canvas.getWidth()-this.getRadius());
			setDx(getDx() * -1);
		}
		if(this.getY()<this.getRadius()) {
			this.setY(this.radius);
			setDy(getDy() * -1);
		}
		/*else if(this.getY()+this.getRadius()>=canvas.getHeight()) {
			this.setY(canvas.getHeight()-this.getRadius());
			setDy(getDy() * -1);
		}*/
	}

	public Boolean isLost(Canvas canvas) {
		return this.getY()+this.getRadius()>=canvas.getHeight();
	}
	
	public double shouldMove(ArrayList<Drawable>objects) {
		double dist=100000;
		for (Drawable d : objects) {
			dist=Math.min(dist, ((Brick) d).getDistance(this));
		}
		//Log.d("onMove of Ball", " "+dist);
		return dist;
	}
	
	public void HandleError(Status status,Drawable d) {
		double c=this.getY()- this.getX()*this.getDy();
		if(status==Status.BRICK_LEFT) {
			double yy=this.getDy()*(d.getX()-this.radius)+c;
			this.setY(yy);
			this.setX((d.getX()-this.radius));
			this.changeDx();
		}
		else if(status==Status.BRICK_RIGHT){
			double yy=this.getDy()*(d.getX()+((Brick) d).getWidth()+this.radius)+c;
			this.setY(yy);
			this.setX((d.getX()+((Brick) d).getWidth()+this.radius));
			this.changeDx();
		}
		else if(status==Status.BRICK_TOP) {
			double xx=(d.getY()-this.getRadius())-c;
			xx/=this.getDy();
			this.setX(xx);
			this.setY((d.getY()-this.getRadius())-1);
			this.changeDy();
		}
		else if(status==Status.BRICK_BOTTOM){
			double xx=(d.getY()+((Brick) d).getHeight()+this.getRadius())-c;
			xx/=this.getDy();
			this.setX(xx);
			this.setY((d.getY()+((Brick) d).getHeight()+this.getRadius())+1);
			this.changeDy();
		}
		
		
		/*if(status==Status.INSIDE_SIDE) {
			if(dx>0) {
				double yy=this.getDy()*(d.getX()-this.radius)+c;
				this.setY(yy);
				this.setX((d.getX()-this.radius));
				this.changeDx();
			}
			else {
				double yy=this.getDy()*(d.getX()+((Brick) d).getWidth()+this.radius)+c;
				this.setY(yy);
				this.setX((d.getX()+((Brick) d).getWidth()+this.radius));
				this.changeDx();
			}
		}
		else if(status==Status.INSIDE_TOP) {
			if(dy<0) {
				double xx=(d.getY()+((Brick) d).getHeight()+this.getRadius())-c;
				xx/=this.getDy();
				this.setX(xx);
				this.setY((d.getY()+((Brick) d).getHeight()+this.getRadius())+1);
				this.changeDy();
			}
			else {
				double xx=(d.getY()-this.getRadius())-c;
				xx/=this.getDy();
				this.setX(xx);
				this.setY((d.getY()-this.getRadius())-1);
				this.changeDy();
			}
		}
		else if(status==Status.INSIDE_CORNER) {
			if(dx==-1) {
				this.setX(d.getX()+((Brick) d).getWidth());
				this.setY(d.getY());
			}
			else if(dx==1) {
				this.setX(d.getX());
				this.setY(d.getY());
			}
			this.changeDx();
			this.changeDy();
		}*/
	}
	
	public double getDx() {
		return dx;
	}

	public void setDx(double dx) {
		this.dx = dx;
	}

	public double getDy() {
		return dy;
	}

	public double setDy(double dy) {
		this.dy = dy;
		return dy;
	}
	
	public void changeDx() {
		this.dx*=-1;
	}
	public void changeDy() {
		this.dy*=-1;
	}
	
	
	public void changeAngle(int th) {
		Log.d("Changing Angle"," "+this.angle+" "+th);
		if(this.angle+th>80 || this.angle+th<25) return;
		this.angle+=th;
		//this.dy=Math.tan(Math.toRadians(this.angle));
		if(this.dy<0)this.dy=Math.tan(Math.toRadians(this.angle))*-1;
		else this.dy=Math.tan(Math.toRadians(this.angle));
	}
}
