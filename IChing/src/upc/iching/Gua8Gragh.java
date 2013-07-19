package upc.iching;

import android.graphics.Canvas;
import android.graphics.Paint;

public class Gua8Gragh extends Graphic{
	
	private static String[] XIANTIAN_GUA_8_SEQUENCE=new String[]
	                 {"111","110","010","100","000","001","101","011"};
	private static String[] HOUTIAN_GUA_8_SEQUENCE=new String[]
	                 {"101","000","011","111","010","100","001","110"};
	
	private Gua8[] gua8s=new Gua8[8];

	public Gua8Gragh(String type)
	{
		// TODO Auto-generated constructor stub
		this.type=type;
		if(type.equals("0"))//先天八卦图
		{			
			for(int i=0;i<8;i++)
			{
				gua8s[i]=new Gua8(Gua8Gragh.XIANTIAN_GUA_8_SEQUENCE[i]);	
			}
		}
		else//后天八卦图
		{
			for(int i=0;i<8;i++)
			{
				gua8s[i]=new Gua8(Gua8Gragh.HOUTIAN_GUA_8_SEQUENCE[i]);	
			}
		}
	}
	
	public void draw(Canvas c,Paint p)
	{
		super.draw(c, p);
		c.save();
		for(int i=0;i<8;i++)
		{
			c.save();
			c.translate(0, -90);
			c.rotate(45*i,0,90);
			gua8s[i].draw(c, p);
			c.restore();
		}
		c.restore();
	}
	
	public String onGraphic(float x,float y)
	{
		for(int i=0;i<8;i++)
		{
			if(!gua8s[i].onGraphic(x,y).equals(""))
				return gua8s[i].getType();
		}
		return "";
	}	
}
