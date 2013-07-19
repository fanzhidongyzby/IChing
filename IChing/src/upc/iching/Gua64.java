package upc.iching;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;

public class Gua64 extends Graphic
{
	public static String[] GUA_64_NAME=new String[]
	               {
					"坤","复","师","临","谦","明夷","升","泰",
					"豫","震","解","归妹","小过","丰","恒","大壮",
					"比","屯","坎","节","蹇","既济","井","需",
					"萃","随","困","兑","咸","革","大过","夬",
					"剥","颐","蒙","损","艮","贲","蛊","大畜",
					"晋","噬磕","未济","睽","旅","离","鼎","大有",
					"观","益","涣","中孚","渐","家人","巽","小畜",
					"否","天妄","讼","履","遁","同人","姤","乾",
	               };//64卦的名称
	public static int[] GUA_64_NUMBER=new int[]
	               {
					2,24,7,19,15,36,46,11,
					16,51,40,54,62,55,32,34,
					8,3,29,60,39,63,48,5,
					45,17,47,58,31,49,28,43,
					23,27,4,41,52,22,18,26,
					35,21,64,38,56,30,50,14,
					20,42,59,61,53,37,57,9,
					12,25,6,10,33,13,44,1
	               };//64卦的卦序
	public static int typeToIndex(String type)
	{
		int index=0;
		for(int i=0;i<6;i++)
		{
			String s=type.substring(i, i+1);
			if(s.equals("1"))
			{
				index+=Math.pow(2, 5-i);
			}
		}
		return index;
	}
	public static String indexToType(int index)
	{
		if(index<0||index>63)
			return "";
		String type="";
		for(int i=5;i>=0;i--)
		{
			double t=Math.pow(2, i);
			if(index>=t)
			{
				type+="1";
				index-=t;
			}
			else
			{
				type+="0";
			}
		}
		return type;
	}
	
	private String name="";
	private int num;
	private Gua8 up;
	private Gua8 down;
	
	private float xScale=1;
	private float yScale=1;
	
	public String getName() {
		return name;
	}

	public int getNum() {
		return num;
	}

	public Gua8 getUp() {
		return up;
	}

	public Gua8 getDown() {
		return down;
	}


	public Gua64(String type)
	{
		this.type=type;
		//解析type
		up=new Gua8(type.substring(0, 3));
		down=new Gua8(type.substring(3, 6));
		up.showName(false);
		down.showName(false);
		up.resetColor(true);
		down.resetColor(true);
		//计算序号
		int index=0;
		for(int i=0;i<6;i++)
		{
			String s=type.substring(i, i+1);
			if(s.equals("1"))
			{
				index+=Math.pow(2, 5-i);
			}
		}
		this.name=Gua64.GUA_64_NAME[index];
		this.num=Gua64.GUA_64_NUMBER[index];
	}
	private boolean isAside=false;
	public void setAside(boolean a)
	{
		isAside=a;
	}
	public void draw(Canvas c,Paint p)
	{
		p.setColor(Color.argb(255, 0, 0, 255));
		p.setTextSize(20);
		c.save();
		c.scale(1, 0.8f);
		if(this.getName().length()==1)
		{
			c.translate(-10, -25);
			if(isAside)
			{
				c.translate(25, 30);
			}
			c.drawText(this.getName(), 0, 0, p);
		}
		else if(this.getName().length()==2)
		{
			c.translate(-10, -45);
			if(isAside)
			{
				c.translate(25, 40);
			}
			c.drawText(this.getName().substring(0, 1), 0, 0, p);
			c.translate(0, 20);
			c.drawText(this.getName().substring(1, 2), 0, 0, p);
		}		
		c.restore();
		
		c.save();
		c.scale(xScale, yScale);
		c.scale(1, 0.5f);
		super.draw(c, p);	
		if(touch)
		{
			p.setColor(Color.WHITE);
			c.drawRect(-15, -27.5f, 15, 27.5f, p);
		}		
		c.translate(0, -15);
		up.draw(c, p);
		c.translate(0, 30);
		down.draw(c, p);
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
		
		if(x>=-15&&x<=15&&y>=-27.5&&y<27.5)
		{
			return this.type;
		}
		else
		{
			return "";
		}
	}
	private boolean touch=false;
	public void onTouch(int t)
	{
		switch(t)
    	{
    	case 0://down
    		touch=true;
    		this.xScale=1.2f;
    		this.yScale=1.2f;
    		break;
    	case 2://up
    		touch=false;
    		this.xScale=1;
    		this.yScale=1;
    		break;
    	}
	}
}
