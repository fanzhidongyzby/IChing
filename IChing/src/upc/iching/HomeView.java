package upc.iching;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.view.MotionEvent;
import android.view.View;

/*
 * View 开发框架
 */
public class HomeView extends View implements Runnable {

	
	private Paint p=new Paint(Paint.ANTI_ALIAS_FLAG);
	private Gua8Gragh gua8Graph=new Gua8Gragh("0");
	private Taiji taiji=new Taiji();
	private CtrlArea ctrlArea=new CtrlArea();
	private float angle=0;
	
	private Matrix m;//记录适应矩阵
	private Gua8 tempGua8=null;
	private float mousex=0;
	private float mousey=0;
	
	private ComposeGua64 cGua64=new ComposeGua64();

	
	private Thread timer;
	private boolean rotating=true;
	


	public void setRotating(boolean rotating) {
		this.rotating = rotating;
	} 

	public HomeView(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
		this.setBackgroundColor(Color.argb(200, 255, 255, 255));
		p.setColor(Color.BLACK);  
		timer=new Thread(this);
		timer.start();
	}
	
	
	protected void onDraw(Canvas c)
	{
		c.save();
		c.translate(Home.xDisp/2, Home.yDisp/2);
		c.scale(Home.scrResize,Home.scrResize);//根据分辨率调整大小
		m=c.getMatrix();	
		p.setColor(Color.argb(200, 255, 255, 0));
		c.drawRect(0, 0, 480, 320, p);
		//八卦图
		c.save();
		c.translate(150, 140);
		c.scale(0.9f,0.9f);
		c.rotate(angle,0,0);
		gua8Graph.draw(c, p);
		c.restore();
		//太极图
		c.save();
		c.translate(150, 140);
		c.scale(1.1f,1.1f);	
		c.rotate(-angle,0,0);
		taiji.draw(c, p);
		c.restore();
		//响应区域
		c.save();
		c.translate(150, 140);
		c.scale(2.5f, 2.5f);
		ctrlArea.draw(c, p);
		c.restore();
		//合成64卦
		c.save();
		c.translate(420, 140);
		c.scale(1.3f, 1.3f);
		cGua64.draw(c, p);
		c.restore();
		//箭头
		c.save();
		c.translate(340, 140);
		c.scale(1, 1);
		this.drawArow(c, p);
		c.restore();
		//拖动图形		
		if(tempGua8!=null)
		{
			c.save();
			c.translate(mousex, mousey);
			c.scale(1.3f, 1.3f);
			tempGua8.draw(c, p);
			c.restore();
		}
		/////////////////
		c.restore();
	}
	
	private void drawArow(Canvas c,Paint p)
	{
		p.setColor(Color.argb(200, 0, 0, 0));
		//p1
		Path p1=new Path();
		p1.moveTo(-40, -5);
		p1.lineTo(10, -5);
		p1.lineTo(20, 0);
		p1.lineTo(10, 5);
		p1.lineTo(-40, 5);
		p1.close();
		//p2
		Path p2=new Path();
		p2.moveTo(0, -10);
		p2.lineTo(30, 0);
		p2.lineTo(15, 0);
		p2.lineTo(10, -5);
		p2.close();
		//p3
		Path p3=new Path();
		p3.moveTo(0, 10);
		p3.lineTo(10, 5);
		p3.lineTo(15, 0);
		p3.lineTo(30, 0);
		p3.close();
		//draw
		c.drawPath(p1, p);
		c.drawPath(p2, p);
		c.drawPath(p3, p);
	}
	
	private float lastx=0;
	private float lasty=0;
	public boolean onTouch(MotionEvent e)
	{
		float x=e.getX();
		float y=e.getY();
		float pts[]=new float[]{x,y};
		Matrix inv=new Matrix();
		this.m.invert(inv);
		inv.mapPoints(pts);
		mousex=pts[0];
		mousey=pts[1];//还原坐标320*480坐标系
		//鼠标响应处理代码		
		//Log.e("msg=", "x="+x+",y="+y);
		switch(e.getAction())
		{
		case MotionEvent.ACTION_DOWN:
			down(x,y);
			//Log.e("mag=", "down");
			break;
		case MotionEvent.ACTION_MOVE:
			move(x,y);
			//Log.e("mag=", "move");
			break;
		case MotionEvent.ACTION_UP:
			up(x,y);
			//Log.e("mag=", "up");
			break;
		}
		//记录上次的位置
		lastx=mousex;
		lasty=mousey;
		return true;
	}
	@Override
	public void run() 
	{
		// TODO Auto-generated method stub
		while(true)
		{
			if(rotating)
			{
				angle=(angle+1)%360;
			}
			this.postInvalidate();
			try 
			{
				Thread.sleep(25);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	//鼠标事件处理函数
	private String hit="";
	private void down(float x,float y)
	{
		if(ctrlArea.onGraphic(x, y).equals("ctrlArea"))//触动控制区
		{			
			rotating=false;//停止旋转
			if(taiji.onGraphic(x, y).equals("taiji"))//触动太极
			{
				this.hit="taiji";
				taiji.onTouch(0);				
			}
			else if(!gua8Graph.onGraphic(x, y).equals(""))//触动八卦
			{
				this.hit="gua8";
				String type=gua8Graph.onGraphic(x, y);
				tempGua8=new Gua8(type);
			}
			else
			{
				this.hit="ctrlArea";
			}
		}
		else if(!cGua64.onGraphic(x, y).equals(""))//选择64卦
		{
			this.hit="cGua64";
			cGua64.onTouch(0);
		}
		else//空白区
		{
			this.hit="";
		}
	}
	private void move(float x,float y)
	{
		if(this.hit.equals("ctrlArea"))//触动控制区
		{
			if(ctrlArea.onGraphic(x, y).equals("ctrlArea"))//有效转动
			{
				//旋转八卦
				float beta=(float)Math.atan((mousey-140)/(mousex-150))
				-(float)Math.atan((lasty-140)/(lastx-150));
				double tangle=beta/Math.PI*180;
				if(tangle<-90)
					tangle+=180;
				if(tangle>90)
					tangle-=180;
				angle+=tangle;
			}
			else
			{
				this.hit="";
			}		
		}
		else if(this.hit.equals("gua8"))
		{
			//检测是否到达指定区域
			String guaPos=cGua64.onGraphic(x, y);
			if(guaPos.equals("guaUp"))
			{
				cGua64.setSelectShow("01");
				tempGua8.showName(false);
			}
			else if(guaPos.equals("guaDown"))
			{
				cGua64.setSelectShow("10");
				tempGua8.showName(false);
			}
			else
			{
				cGua64.setSelectShow("11");
				tempGua8.showName(true);
			}
		}
		else//空白区
		{}
	}
	private void up(float x,float y)
	{
		rotating=true;
		taiji.onTouch(2);//恢复太极
		//在指定区域（合成）
		if(this.hit.equals("gua8"))
		{
			String guaPos=cGua64.onGraphic(x, y);
			if(!guaPos.equals(""))
			{
				cGua64.setGua8(guaPos,tempGua8);
				cGua64.setSelectShow("11");
			}
		}		
		tempGua8=null;
		if(this.hit.equals("cGua64")&&!cGua64.onGraphic(x, y).equals(""))
		{
			//转到下一个View
		}
		cGua64.onTouch(2);
	}	
	
}
