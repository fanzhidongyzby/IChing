package upc.iching;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.RadialGradient;
import android.graphics.Shader;

public class Gua8 extends Graphic
{
	public static String[] GUA_8_OBJECT=new String[]
	              {"地","雷","水","泽","山","火","风","天"};//八卦对应的事物
	public static String[] GUA_8_NAME=new String[]
	              {"坤","震","坎","兑","艮","离","巽","乾"};//八卦的名称
	//属性
	//type;//标识8卦的类型："000"-"111",为八卦
	private Yao[] yao=new Yao[3];
	private String name;//卦名
	private String object;//物名
	private boolean showName;//是否显示卦名
	public void showName(boolean show) {
		this.showName = show;
	}
	public String getObject() {
		return object;
	}

	public String getName() {
		return name;
	}

	private Shader radialGradient=new RadialGradient(15,15,50,
			new int[]{Color.GRAY,Color.GREEN},
			null,Shader.TileMode.REPEAT);
	
	//方法
	public Gua8(String type)
	{
		this.type=type;
		this.showName=true;
		//解析type
		String[] tokens=new String[3];
		//初始化爻数组,计算编号，得出卦名
		int num=0;
		for(int i=0;i<3;i++)
		{
			tokens[i]=type.substring(i, i+1);
			yao[i]=new Yao(tokens[i]);
			if(tokens[i].equals("1"))
			{
				num+=Math.pow(2, 2-i);
			}			
		}
		this.name=Gua8.GUA_8_NAME[num];
		this.object=Gua8.GUA_8_OBJECT[num];
	}
	
	public void draw(Canvas c,Paint p)
	{
		super.draw(c, p);
		c.save();
		if(showName)//绘制名称
		{			
			//p.setColor(Color.argb(125, 0, 0, 255));			
			p.setTextSize(30);
			c.save();
			c.translate(-15, -20);
			p.setShader(this.radialGradient);
			c.drawText(this.getName(), 0, 0, p);
			p.setShader(null);	
			c.restore();
		}
		//绘制卦象
		c.translate(0, -10);
		//c.scale(1.3f, 1.3f, 0, 0);
		for(int i=0;i<3;i++)
		{
			if(colorReset)
				yao[i].resetColor(true);
			yao[i].draw(c, p);
			c.translate(0, 10);
		}
		c.restore();
	}
	private boolean colorReset=false;
	public void resetColor(boolean r)
	{
		colorReset=r;
	}
	
	public String onGraphic(float x,float y)
	{
		float pts[]=new float[]{x,y};
		Matrix inv=new Matrix();
		this.matrix.invert(inv);
		inv.mapPoints(pts);
		x=pts[0];
		y=pts[1];//还原坐标
		
		if(x>=-15&&x<=15&&y>=-42.5&&y<=12.5)
		{
			return this.type;
		}
		
		return "";
	}
}
