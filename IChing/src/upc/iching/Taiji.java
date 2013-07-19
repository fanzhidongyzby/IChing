package upc.iching;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.RectF;


public class Taiji extends Graphic 
{
	private float xScale=1;
	private float yScale=1;
	private RectF r=new RectF(-50,-50,50,50);
	public void draw(Canvas c,Paint p)
	{
		c.save();
		c.scale(xScale, yScale);
		super.draw(c, p);				
		p.setColor(Color.BLACK);
		c.drawArc(r, 0, 180, false, p);
		p.setColor(Color.WHITE);
		c.drawArc(r, 0, -180, false, p);		
		c.drawCircle(-25, 0, 25, p);
		p.setColor(Color.BLACK);
		c.drawCircle(25, 0, 25, p);
		c.drawCircle(-25, 0, 5, p);
		p.setColor(Color.WHITE);
		c.drawCircle(25, 0, 5, p);
		c.restore();
	}
	
	public String onGraphic(float x ,float y)
	{
		float pts[]=new float[]{x,y};
		Matrix inv=new Matrix();
		this.matrix.invert(inv);
		inv.mapPoints(pts);
		x=pts[0];
		y=pts[1];//»¹Ô­×ø±ê
		
		if(Math.sqrt(x*x+y*y)<=50)
		{
			return "taiji";
		}
		return "";
	}
	
	public void onTouch(int t)
	{
		switch(t)
    	{
    	case 0://down
    		this.xScale=1.2f;
    		this.yScale=1.2f;
    		Home.playMusic(R.raw.btn);
    		break;
    	case 2://up
    		this.xScale=1;
    		this.yScale=1;
    		break;
    	}
	}
}
