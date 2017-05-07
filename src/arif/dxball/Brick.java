package arif.dxball;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.util.Log;

public class Brick extends Drawable {
	public static int INF=1000000;
	private int hitCount;
	private int width,height;
	
	Brick() {
		setType("brick");
	}
	
	@Override
	public void Draw(Canvas canvas) {
		if(this.bitmap!=null) 
		//if(bitmap!=null) canvas.drawBitmap(Bitmap.createScaledBitmap(bitmap, this.getWidth(), this.getHeight(),false),(float) getX(),(float)getY(),getPaint());
		if(bitmap!=null) canvas.drawBitmap(bitmap, (float) getX(),(float)getY(),getPaint());
		//canvas.drawRect((float)getX(), (float)getY(), (float)(getX()+getWidth()), (float)(getY()+getHeight()), getPaint());
	}

	@Override
	public Status Collision(Drawable d) {
		Ball b=(Ball)d;
		float dx=(float) Math.abs(b.getX()-this.getX()-this.getWidth()/2);
		float dy=(float) Math.abs(b.getY()-this.getY()-this.getHeight()/2);
		
		float ddx=dx-this.getWidth()/2;
		float ddy=dx-this.getHeight()/2;
		
		//Log.v(getType(), this.getWidth()/2+b.getRadius()+"");
		//Log.v(getType(), this.getHeight()/2+b.getRadius()+"");
		
		if(dx>(this.getWidth()/2+b.getRadius())) return Status.NONE;
		if(dy>(this.getHeight()/2+b.getRadius())) return Status.NONE;
		
		/*if(ddx*ddx+ddy*ddy==b.getRadius()*b.getRadius()) {
			/*double xx=d.getX(),yy=d.getY();
			if(xx<this.getX() && yy<this.getY()) return Status.LEFT_TOP_CORNER;
			if(xx<this.getX() && yy>this.getY()) return Status.LEFT_BOTTOM_CORNER;
			if(xx>this.getX() && yy<this.getY()) return Status.RIGHT_TOP_CORNER;
			if(xx<this.getX() && yy>this.getY()) return Status.RIGHT_BOTTOM_CORNER;
			return Status.CORNER;
		}*/
		/*else if(2*dx==this.getWidth()) {
			return Status.TOP;
		}*/
		//else if(2*dy==this.getHeight()) return Status.SIDE;
		else if(2*dx<this.getWidth()) {
			double top=Math.abs(b.getY()-this.getY());
			double down=Math.abs(b.getY()-this.getY()-this.getHeight());
			if(top<down) return Status.BRICK_TOP;
			else if(top>down) return Status.BRICK_BOTTOM;
			return Status.INSIDE_TOP;
		}
		else if(2*dy<=this.getHeight()) {
			double left=Math.abs(b.getX()-this.getX());
			double right=Math.abs(b.getX()-this.getX()-this.getWidth());
			if(left<right) return Status.BRICK_LEFT;
			else if(left>right) return Status.BRICK_RIGHT;
			return Status.INSIDE_SIDE;
		}
		//else if(ddx*ddx+ddy*ddy<b.getRadius()*b.getRadius()) return Status.INSIDE_CORNER;
		else return Status.NONE;
	}
	
	public double getDistance(Drawable d) {
		Ball b=(Ball)d;
		double centerX=this.getX()+this.getWidth()/2;
		double centerY=this.getY()+this.getHeight()/2;
		double disX=Math.abs(b.getX()-centerX); 
		double disY=Math.abs(b.getY()-centerY);
		//if(disX>(this.getWidth()/2+b.getRadius()) || disY>this.getHeight()/2+b.getRadius()) return Math.min(disX, disY);
		double distance = (centerX-this.getX())*(centerX-this.getX())+(centerY-this.getY())*(centerY-this.getY());
		return Math.sqrt(distance);
		
		
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

}
