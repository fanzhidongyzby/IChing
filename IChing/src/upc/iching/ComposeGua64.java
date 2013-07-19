package upc.iching;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.RadialGradient;
import android.graphics.Shader;

public class ComposeGua64 extends Graphic
{
	private Gua8 guaUp=null;
	private Gua8 guaDown=null;
	private String selectShow="11";//全部显示
	private String name="";
	
	public String getName() {
		return name;
	}

	private float xScale=1;
	private float yScale=1;
	
	public void setSelectShow(String selectShow) {
		this.selectShow = selectShow;
	}


	public void setGua8(String guaPos , Gua8 gua) 
	{
		gua.showName(false);
		if(guaPos.equals("guaUp"))
		{
			guaUp=gua;
		}
		else if(guaPos.equals("guaDown"))
		{
			guaDown=gua;
		}
		String t=guaUp.getType()+guaDown.getType();
		int index=0;
		for(int i=0;i<6;i++)
		{
			String s=t.substring(i, i+1);
			if(s.equals("1"))
			{
				index+=Math.pow(2, 5-i);
			}
		}
		this.name=Gua64.GUA_64_NAME[index];
		this.type=guaUp.getType()+guaDown.getType();
		Home.playMusic(R.raw.put);
	}
	

	public ComposeGua64()
	{
		this.type="010101";
		guaUp=new Gua8("010");
		guaDown=new Gua8("101");
		this.name="既济";
		guaUp.showName(false);
		guaDown.showName(false);
	}
	
	private Shader radialGradient=new RadialGradient(15,15,50,
			new int[]{Color.GRAY,Color.BLUE},
			null,Shader.TileMode.REPEAT);
	
	public void draw(Canvas c,Paint p)
	{
		p.setShader(radialGradient);
		p.setTextSize(20);
		c.save();
		if(this.getName().length()==1)
		{
			c.translate(-10, 55);
		}
		else if(this.getName().length()==2)
		{
			c.translate(-20, 55);
		}
		c.drawText(this.getName(), 0, 0, p);
		p.setShader(null);
		c.restore();
		
		c.save();
		c.scale(xScale, yScale);
		super.draw(c, p);		
		p.setStyle(Paint.Style.STROKE);
		p.setColor(Color.BLUE);
		c.drawRect(-20, -32.5f, 20, 32.5f, p);
		p.setStyle(Paint.Style.FILL_AND_STROKE);
		c.translate(0, -15);
		if(this.selectShow.startsWith("1"))
		{
			guaUp.draw(c, p);
		}
		c.translate(0, 30);
		if(this.selectShow.endsWith("1"))
		{
			guaDown.draw(c, p);
		}		
		c.restore();
	}
	
	public String onGraphic(float x,float y)
	{		
		float pts[]=new float[]{x,y};
		Matrix inv=new Matrix();
		this.matrix.invert(inv);
		inv.mapPoints(pts);
		x=pts[0];
		y=pts[1];//还原坐标
		
		if(x>=-15&&x<=15&&y>=-27.5&&y<0)
		{
			return "guaUp";
		}
		else if(x>=-15&&x<=15&&y>0&&y<=27.5)
		{
			return "guaDown";
		}
		else
		{
			return "";
		}
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
