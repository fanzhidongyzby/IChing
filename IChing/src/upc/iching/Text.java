package upc.iching;

import java.util.ArrayList;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.RectF;


public class Text extends Graphic 
{
	private String text="";
	private ArrayList<String> strList=new ArrayList<String>(2);
	private int rowLength=15;
	public void setRowLength(int rowLength) {
		if(rowLength>50&&rowLength<5)
			return;
		this.rowLength = rowLength;
		init();
	}
	private int length=0;
	
	private Path path=new Path();
	public Text(String t)
	{
		t=t.trim();
		this.text=t;
		this.length=t.length();
		init();
		path.moveTo(-10, 5);
		path.lineTo(-35, 15);
		path.lineTo(-10, 15);
		path.close();
	}
	
	private void init()
	{
		strList.clear();
		if(length>rowLength)//多行显示
		{
			int i=0;
			String s="";
			int l=0;
			while(true)
			{
				if((i+1)*rowLength>length)
					s=text.substring(i*rowLength);
				else
					s=text.substring(i*rowLength, (i+1)*rowLength);
				i++;
				l=s.length();
				if(l==0)//刚好整10
				{
					break;
				}
				else if(l<rowLength)//最后一行
				{
					strList.add(s);
					break;
				}
				else//==rowLength,正常行
				{
					strList.add(s);
				}
			}
		}
	}
	
	private Rect r=new Rect();
	private boolean showTip=false;
	public void setShowTip(boolean showTip) {
		this.showTip = showTip;
	}
	private boolean showBound=false;
	public void setShowBound(boolean showBound) {
		this.showBound = showBound;
	}

	private int textColor=Color.BLACK;
	public void setTextColor(int color)
	{
		textColor=color;
	}
	
	private int w=0,h=0;
	public int getW()
	{
		return w;
	}
	public int getH()
	{
		return h;
	}
	public void draw(Canvas c,Paint p)
	{
		//super.draw(c, p);
		w=0;
		h=0;
		c.save();		
		int l=this.strList.size();
		if(this.length>0&&this.length<=this.rowLength)//单行
		{
			p.getTextBounds(text, 0, text.length(), r);
			w=r.right-r.left;
			h=r.bottom-r.top;
		}
		else
		{
			for(int i=0;i<l;i++)
			{
				p.getTextBounds(strList.get(i), 0, strList.get(i).length(), r);
				h+=r.bottom-r.top+5;
				if(r.right-r.left>w)
				{
					w=r.right-r.left;
				}
			}
		}		
		//绘制气泡
		if(showTip)
		{
			p.setColor(Color.BLACK);
			p.setStyle(Paint.Style.STROKE);
			c.drawRoundRect(new RectF(-10,-10,w+10,h+10), 10, 10, p);
			c.drawPath(path, p);
			p.setColor(Color.argb(200, 255, 255, 255));
			p.setStyle(Paint.Style.FILL_AND_STROKE);
			c.drawRoundRect(new RectF(-10,-10,w+10,h+10), 10, 10, p);
			c.drawPath(path, p);
		}		
		//绘制边界
		if(showBound)
		{
			p.setColor(Color.GREEN);
			p.setStyle(Paint.Style.STROKE);
			c.drawRoundRect(new RectF(-5,-5,w+5,h+5), 10, 10, p);
			p.setStyle(Paint.Style.FILL_AND_STROKE);
		}
		///////
		c.save();
		if(this.length>0&&this.length<=this.rowLength)//单行
		{					
			p.setTextSize(30);
			p.setColor(textColor);
			c.translate(-r.left, -r.top);
			c.drawText(text, 0, 0, p);
		}
		else//多行
		{			
			p.setTextSize(30);
			p.setColor(textColor);			
			//c.translate(-r.left, -r.top);
			for(int i=0;i<l;i++)
			{
				p.getTextBounds(strList.get(i), 0, strList.get(i).length(), r);
				c.translate(-r.left, -r.top+5);
				c.drawText(strList.get(i), 0, 0, p);
				//c.translate(0, 28);
			}		
		}
		c.restore();		
		c.restore();
	}

	//命中图形
	public String onGraphic(float x,float y)
	{		
		if(x>=0&&x<=w&&y>=0&&y<=h)
			return "text";
		return "";
	}
}
