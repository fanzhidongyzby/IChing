package upc.iching;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.RadialGradient;
import android.graphics.Shader;


//爻类：绘制阴爻和阳爻,位置：(0,0),大小：50*10
public class Yao extends Graphic
{
	//属性
	//private int num;//标识64卦爻的序号：1-6
	
	private Shader radialGradient=new RadialGradient(0,0,30,
			new int[]{Color.argb(255, 255, 128, 128),Color.RED},
			null,Shader.TileMode.REPEAT);
	
	private Shader radialGradientl=new RadialGradient(-8.75f,0,30,
			new int[]{Color.argb(255, 100, 100, 100),Color.BLACK},
			null,Shader.TileMode.REPEAT);
	
	private Shader radialGradientr=new RadialGradient(8.75f,0,30,
			new int[]{Color.argb(255, 100, 100, 100),Color.BLACK},
			null,Shader.TileMode.REPEAT);
	
	//type;//标识爻的类型："0"-阴爻；"1"-阳爻；
	//方法
	public Yao(String type)
	{
		this.type=type;
	}
	public String getType() {
		return type;
	}
	private boolean colorReset=false;
	public void resetColor(boolean r)
	{
		colorReset=r;
	}
	
	public void draw(Canvas c,Paint p)
	{
		super.draw(c, p);
		//阴爻-黑色；阳爻-红色；半透明		
		float w=30,h=5;
		if(type.equals("1"))
		{
			if(colorReset)
				p.setColor(Color.argb(125, 255, 0, 0));
			else
				p.setShader(this.radialGradient);
			c.drawRect(-w/2, -h/2, w/2, h/2, p);
		}
		else if(type.equals("0"))
		{
			if(colorReset)
				p.setColor(Color.argb(125, 0, 0, 0));
			else
				p.setShader(this.radialGradientl);
			c.drawRect(-w/2, -h/2, -h/2, h/2, p);
			if(!colorReset)
				p.setShader(this.radialGradientr);
			c.drawRect(h/2, -h/2, w/2, h/2, p);
		}
		if(!colorReset)
			p.setShader(null);
	}
	public String onGraphic(float x,float y)
	{
		float pts[]=new float[]{x,y};
		Matrix inv=new Matrix();
		this.matrix.invert(inv);
		inv.mapPoints(pts);
		x=pts[0];
		y=pts[1];//还原坐标
		
		if(x>=-25&&x<=25&&y>=-5&&y<=5)
		{
			return this.getType();
		}
		
		return "";
	}
}
