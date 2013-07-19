package upc.iching;

import android.graphics.Canvas;
import android.graphics.Paint;

public class Gua64Graph extends Graphic 
{
	private Gua64[] gua64sCir=new Gua64[64];
	private Gua64[] gua64sSqu=new Gua64[64];
	public Gua64Graph()
	{
		//初始化卦64
		for(int i=0;i<64;i++)
		{
			StringBuffer s=new StringBuffer(Gua64.indexToType(i)).reverse();
			gua64sCir[i]=new Gua64(s.toString());
			gua64sSqu[i]=new Gua64(s.toString());
			gua64sSqu[i].setAside(true);
		}		
	}
	
	public Gua64 getGua64CirByIndex(int index)
	{
		return gua64sCir[index];
	}
	public Gua64 getGua64SquByIndex(int index)
	{
		return gua64sSqu[index];
	}
	
	public void draw(Canvas c,Paint p)
	{
		super.draw(c, p);
		c.save();
		c.save();
		c.rotate(angle);
		for(int i=0;i<32;i++)
		{
			c.save();
			c.translate(0, -350);
			c.rotate(-5.625f*i-180,0,350);
			//gua64s[i].setAside(true);
			gua64sCir[i].draw(c, p);
			c.restore();
		}	
		for(int i=32;i<64;i++)
		{
			c.save();
			c.translate(0, -350);
			c.rotate(-5.625f*(63-i),0,350);
			//gua64s[i].setAside(true);
			gua64sCir[i].draw(c, p);
			c.restore();
		}
		c.restore();
		/////////////////////
		c.save();			
		for(int i=0;i<8;i++)
			for(int j=0;j<8;j++)
			{
				c.save();
				c.translate(-210, -210);
				c.translate(j*60, i*60);				
				gua64sSqu[8*i+j].draw(c, p);
				c.restore();
			}
		c.restore();
		c.restore();
	}
	
	public String onGraphic(float x,float y)
	{
		for(int i=0;i<64;i++)
		{
			if(!gua64sCir[i].onGraphic(x,y).equals(""))
				return String.valueOf(i);//返回索引
			if(!gua64sSqu[i].onGraphic(x,y).equals(""))
				return String.valueOf(i);//返回索引
		}
		return "";
	}
	
	public void onTouch(int t,int index)
	{
		switch(t)
    	{
    	case 0://down
    		gua64sCir[index].onTouch(0);
    		gua64sCir[63-index].onTouch(0);
    		gua64sSqu[index].onTouch(0);
    		gua64sSqu[63-index].onTouch(0);
    		Home.playMusic(R.raw.put);
    		break;
    	case 2://up
    		gua64sCir[index].onTouch(2);
    		gua64sCir[63-index].onTouch(2);
    		gua64sSqu[index].onTouch(2);
    		gua64sSqu[63-index].onTouch(2);
    		break;
    	}
	}

	private float angle=0;
	public void rotate(float i) 
	{
		// TODO Auto-generated method stub
		angle=i;
	}
	
}
