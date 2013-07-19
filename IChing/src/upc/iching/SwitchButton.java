package upc.iching;

import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.RadialGradient;
import android.graphics.Shader;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.OvalShape;

public class SwitchButton extends Graphic 
{
	private float xScale=1;
	private float yScale=1;
	private String label="";	
	private String oldLabel="";
	private String id="";
	
	public void setLabel(String msg) {
		this.label = msg;
	}

	private Shader radialGradient0=new RadialGradient(40,15,100,
			new int[]{Color.GRAY,Color.RED},
			null,Shader.TileMode.REPEAT);
	private Shader radialGradient1=new RadialGradient(40,15,100,
			new int[]{Color.GRAY,Color.BLUE},
			null,Shader.TileMode.REPEAT);
	private ShapeDrawable shape=null;
	private int xSize=0;
	private int ySize=0;
	
	
	public SwitchButton(String id ,Bitmap btnBgImg,String l)
	{
		this.id=id;
		this.oldLabel=this.label=l;
		shape=new ShapeDrawable(new OvalShape());
		this.setBgImg(btnBgImg);
	}
	
	private Bitmap bg=null;
	public Bitmap getBgImg()
	{
		return bg;
	}
	public void setBgImg(Bitmap b)
	{
		bg=b;
		BitmapShader btnBgImgShader=new BitmapShader(bg,Shader.TileMode.MIRROR,
				Shader.TileMode.MIRROR);
		shape.getPaint().setShader(btnBgImgShader);
		xSize=b.getWidth();
		ySize=b.getHeight();
		shape.setBounds(0,0,xSize,ySize);
	}
	
	public void draw(Canvas c,Paint p)
	{
		c.save();		
		c.scale(xScale, yScale);		
		super.draw(c, p);
		c.translate(-25,-25);
		c.scale(50f/this.xSize, 50f/this.ySize);		
		shape.draw(c);
		c.restore();
		if(!label.equals(""))
		{
			c.save();
			c.translate(-195, 6);		
			p.setTextSize(20);
			if(label.equals(this.oldLabel))
				p.setShader(radialGradient0);
			else
				p.setShader(radialGradient1);
			c.drawText(label, 0, 0, p);
			p.setShader(null);
			c.restore();
		}		
	}
	
	public String onGraphic(float x,float y)
	{		
		float pts[]=new float[]{x,y};
		Matrix inv=new Matrix();
		this.matrix.invert(inv);
		inv.mapPoints(pts);
		x=pts[0];
		y=pts[1];//»¹Ô­×ø±ê
		
		if(Math.sqrt((x*x+y*y))<=25)
		{
			return this.id;
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
