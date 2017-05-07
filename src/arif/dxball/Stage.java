package arif.dxball;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.provider.OpenableColumns;

public class Stage {
	public int StageCount=1;
	ArrayList<Drawable>objectList,balls;
	Context context;
	int lifeCount=3;
	int score=0;
	Bitmap digitBitmap[];
	
	Stage(Context ctx) {
		objectList=new ArrayList<Drawable>();
		balls=new ArrayList<Drawable>();
		context=ctx;
		digitBitmap=new Bitmap[10];
		Resources r=context.getResources();
		digitBitmap[0]=Bitmap.createScaledBitmap(BitmapFactory.decodeResource(r, R.drawable.numeral0), 50, 50, false);
		digitBitmap[1]=Bitmap.createScaledBitmap(BitmapFactory.decodeResource(r, R.drawable.numeral1), 50, 50, false);
		digitBitmap[2]=Bitmap.createScaledBitmap(BitmapFactory.decodeResource(r, R.drawable.numeral2), 50, 50, false);
		digitBitmap[3]=Bitmap.createScaledBitmap(BitmapFactory.decodeResource(r, R.drawable.numeral3), 50, 50, false);
		digitBitmap[4]=Bitmap.createScaledBitmap(BitmapFactory.decodeResource(r, R.drawable.numeral4), 50, 50, false);
		digitBitmap[5]=Bitmap.createScaledBitmap(BitmapFactory.decodeResource(r, R.drawable.numeral5), 50, 50, false);
		digitBitmap[6]=Bitmap.createScaledBitmap(BitmapFactory.decodeResource(r, R.drawable.numeral6), 50, 50, false);
		digitBitmap[7]=Bitmap.createScaledBitmap(BitmapFactory.decodeResource(r, R.drawable.numeral7), 50, 50, false);
		digitBitmap[8]=Bitmap.createScaledBitmap(BitmapFactory.decodeResource(r, R.drawable.numeral8), 50, 50, false);
		digitBitmap[9]=Bitmap.createScaledBitmap(BitmapFactory.decodeResource(r, R.drawable.numeral9), 50, 50, false);
	}
	
	public void drawScore(Canvas canvas) {
		int x=50,y=30;
		String scoreText=String.valueOf(score);
		while(scoreText.length()<4) scoreText="0"+scoreText;
		for(int i=0;i<scoreText.length();i++) {
			int d=Character.getNumericValue(scoreText.charAt(i));
			canvas.drawBitmap(digitBitmap[d], x, y, new Paint());
			x+=46;			
		}
	}
	
	public void DrawLife(Canvas canvas) {
		Resources r=context.getResources();
		int x=canvas.getWidth()-70 ,y=30;
		for(int i=0;i<lifeCount;i++) {
			Bitmap bit=Bitmap.createScaledBitmap(BitmapFactory.decodeResource(r, R.drawable.life), 80, 80, false);
			Paint paint=new Paint();
			paint.setColor(Color.BLUE);
			canvas.drawBitmap(bit, x, y, paint);
			x-=70;
		}
	}
	
	public void Load() {
		objectList.clear();
		balls.clear();
		Resources r=context.getResources();
		try {
			
			InputStreamReader input=new InputStreamReader(context.getAssets().open("stage"+StageCount));
			StageCount++;
			BufferedReader reader=new BufferedReader(input);
			String line;
			Bar bar=new Bar();
			Ball ball=new Ball();
			while((line=reader.readLine())!=null) {
				String parts[]=line.split(" ");
				if(parts[0].equals("life")) {
					lifeCount=Integer.valueOf(parts[1]);
				}
				else if(parts[0].equals("bar")) {
					ball.setType("bar");
					bar.setPaint(new Paint());
					bar.setHeight(Integer.valueOf(parts[2]));
					bar.setWidth(Integer.valueOf(parts[3]));
					bar.bitmap=Bitmap.createScaledBitmap(BitmapFactory.decodeResource(r, R.drawable.paddle), bar.getWidth(),
							bar.getHeight(), false);
					objectList.add(bar);
				}
				else if(parts[0].equals("ball")) {
					ball.setType("ball");
					ball.setPaint(new Paint());
					ball.setRadius(Double.valueOf(parts[2]));
					ball.bitmap=Bitmap.createScaledBitmap(BitmapFactory.decodeResource(r, R.drawable.ball),
							(int)(2*ball.getRadius()),(int) (2*ball.getRadius()), false);
					balls.add(ball);
				}
				else {
					ArrayList<Integer>brickCounters=new ArrayList<Integer>(); 
					int count=Integer.valueOf(parts[2]);
					int mx=0;
					for(int i=0;i<count;i++) {
						int xx=Integer.valueOf(parts[3+i]);
						brickCounters.add(xx);
						if(xx>mx) mx=xx;
					}
					int wid=GameActivity.displaySize.x/mx;
					int he=(GameActivity.displaySize.y)/(brickCounters.size()+10);
					int inx=5,iny=0;
					for (Integer i : brickCounters) {
						inx=(GameActivity.displaySize.x-i*wid)/2;
						for(int j=0;j<i;j++) {
							Brick brick=new Brick();
							brick.setHeight(he);
							ball.setType("bar");
							brick.setWidth(wid);
							brick.setX(inx);
							brick.setY(iny);
							brick.setPaint(new Paint());
							//brick.getPaint().setColor(Color.BLUE);
							//brick.getPaint().setStyle(Style.FILL_AND_STROKE);
							brick.bitmap=Bitmap.createScaledBitmap(BitmapFactory.decodeResource(r, R.drawable.brick),
									brick.getWidth(),brick.getHeight(), false);
							inx+=wid;
							objectList.add(brick);
						}
						iny+=he;
					}
				}
			}
			
		} catch (FileNotFoundException e) {
			//return null;
		} catch (IOException e) {
			//return null;
		}
	}
	
}
