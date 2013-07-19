package upc.iching;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;


public class CtrlArea extends Graphic
{
	public String onGraphic(float x,float y)
	{
		float pts[]=new float[]{x,y};
		Matrix inv=new Matrix();
		this.matrix.invert(inv);
		inv.mapPoints(pts);
		x=pts[0];
		y=pts[1];//»¹Ô­×ø±ê
		
		if(Math.sqrt(x*x+y*y)<=50)
		{
			return "ctrlArea";
		}
		
		return "";
			
	}
	
	public void draw(Canvas c,Paint p)
	{
		super.draw(c, p);
		c.save();
		p.setColor(Color.RED);
		p.setStyle(Paint.Style.STROKE);
		c.drawCircle(0, 0, 50, p);
		p.setStyle(Paint.Style.FILL_AND_STROKE);
		c.restore();
	}
	
}
