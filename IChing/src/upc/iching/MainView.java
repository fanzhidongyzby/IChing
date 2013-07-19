package upc.iching;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RadialGradient;
import android.graphics.Shader;
import android.graphics.drawable.BitmapDrawable;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

/*
 * SurfaceView 开发框架
 */
public class MainView extends SurfaceView implements SurfaceHolder.Callback,Runnable
{

	private SurfaceHolder surfaceHolder;	
	
	//渲染对象
	private Bitmap bgImg=((BitmapDrawable)this.getResources()
			.getDrawable(R.drawable.bg)).getBitmap(); 
	Bitmap btnBgImg0=((BitmapDrawable)this.getResources()
			.getDrawable(R.drawable.xiantian)).getBitmap();
	Bitmap btnBgImg1=((BitmapDrawable)this.getResources()
			.getDrawable(R.drawable.houtian)).getBitmap();
	Bitmap btnGua8BgImg=((BitmapDrawable)this.getResources()
			.getDrawable(R.drawable.gua8)).getBitmap();
	Bitmap btnGua64BgImg=((BitmapDrawable)this.getResources()
			.getDrawable(R.drawable.gua64)).getBitmap();
	Bitmap btnMusicImg0=((BitmapDrawable)this.getResources()
			.getDrawable(R.drawable.misicon)).getBitmap();
	Bitmap btnMusicImg1=((BitmapDrawable)this.getResources()
			.getDrawable(R.drawable.musicoff)).getBitmap();
	Bitmap btnCloseImg=((BitmapDrawable)this.getResources()
			.getDrawable(R.drawable.close)).getBitmap();
	Bitmap logo=((BitmapDrawable)this.getResources()
			.getDrawable(R.drawable.sign)).getBitmap();
	Bitmap logo2=((BitmapDrawable)this.getResources()
			.getDrawable(R.drawable.logo2)).getBitmap();
	Bitmap back=((BitmapDrawable)this.getResources()
			.getDrawable(R.drawable.back)).getBitmap();
	Bitmap left=((BitmapDrawable)this.getResources()
			.getDrawable(R.drawable.left)).getBitmap();
	Bitmap right=((BitmapDrawable)this.getResources()
			.getDrawable(R.drawable.right)).getBitmap();
	Bitmap add=((BitmapDrawable)this.getResources()
			.getDrawable(R.drawable.add)).getBitmap();
	Bitmap sub=((BitmapDrawable)this.getResources()
			.getDrawable(R.drawable.sub)).getBitmap();
	Bitmap helper=((BitmapDrawable)this.getResources()
			.getDrawable(R.drawable.helper)).getBitmap();
	//Bitmap 渲染
	private  Shader bgImgShader=null;
	/*private  Shader logoImgShader=null;
	private  Shader logo2ImgShader=null;*/
/*	//线性渐变渲染
	public  static Shader linearGradient=null;
	//混合渲染
	public  static Shader composeShader=null;
	//唤醒渐变渲染
	public  static Shader radialGradient=null;	
	//梯度渲染
	public  static Shader sweepGradient=null;
	/////////
*/	
	private  void initShaders()
	{
		bgImgShader=new BitmapShader(bgImg,Shader.TileMode.MIRROR,
				Shader.TileMode.MIRROR);
		/*logoImgShader=new BitmapShader(logo,Shader.TileMode.MIRROR,
				Shader.TileMode.MIRROR);
		logo2ImgShader=new BitmapShader(logo2,Shader.TileMode.MIRROR,
				Shader.TileMode.MIRROR);*/
		/*btnLogo.setLogo(true);
		btnLogo2.setLogo(true);*/
		/*linearGradient=new LinearGradient(0,0,100,100,
				new int[]{Color.RED,Color.GREEN,Color.BLUE,Color.WHITE},
				null,Shader.TileMode.REPEAT);
		composeShader=new ComposeShader(bitmapShader,linearGradient,PorterDuff.Mode.DARKEN);
		radialGradient=new RadialGradient(50,200,50,
				new int[]{Color.GREEN,Color.GRAY,Color.BLUE},
				//new int[]{Color.RED,Color.GREEN,Color.BLUE,Color.WHITE},
				null,Shader.TileMode.REPEAT);
		sweepGradient=new SweepGradient(30,30,
				new int[]{Color.RED,Color.GREEN,Color.BLUE,Color.WHITE},
				null);*/
	}
	
	public MainView(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
		surfaceHolder=this.getHolder();
		surfaceHolder.addCallback(this);
		this.setFocusable(true);
		this.initShaders();
		this.initArow();
	}
	
	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width,
			int height) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void surfaceCreated(SurfaceHolder holder) {
		// TODO Auto-generated method stub
		//启动线程
		new Thread(this).start();
	}

	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {
		// TODO Auto-generated method stub
		//停止线程
		loop=false;
	}

	public void setLoop(boolean l)
	{
		loop=l;
		if(loop)
			new Thread(this).start();
	}
	//控制循环
	private boolean loop=true;
	@Override	
	public void run() {
		// TODO Auto-generated method stub
		while(loop)
		{
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			synchronized(surfaceHolder)
			{
				draw();
			}
		}
	}

	
	private int angle =0;	
	private int angle64 =0; 
	private int angleClose=0;
	private boolean rotating=true;
	private boolean taijiActivated =true;
	private boolean rotating64=true;
	private boolean squareActivated =true;
	private Paint p=new Paint(Paint.ANTI_ALIAS_FLAG);
	private int viewType=0;
	private int oldViewType=0;
	
	private void draw() {
		// TODO Auto-generated method stub
		//锁定画布
		Canvas c=surfaceHolder.lockCanvas();
		if(surfaceHolder==null||c==null)
		{
			return ;
		}
		//动画参数
		angleClose=(angleClose+5)%360;
		if(rotating&&taijiActivated)
		{
			angle=(angle+1)%360;			
		}
		if(rotating64&&squareActivated)
		{
			angle64=(angle64+1)%360;
		}
		//清屏
		//p.setColor(Color.argb(255, 150, 150, 150));
		//c.drawRect(0, 0, Home.srcWidth, Home.srcHeight, p);
		c.drawColor(Color.argb(255, 0, 0, 0));
		//绘图
		if(viewType==0)
			this.drawGua8View(c);
		else if(viewType==1)
			this.drawGua64View(c);
		else if(viewType==2)
			this.drawGua64DetailView(c);
		//绘制后解锁
		surfaceHolder.unlockCanvasAndPost(c);
	}
	
	private Gua8Gragh gua8Graph_xiantian=new Gua8Gragh("0");
	private Gua8Gragh gua8Graph_houtian=new Gua8Gragh("1");
	private Gua8Gragh gua8Graph=gua8Graph_xiantian;
	private Taiji taiji=new Taiji();
	private CtrlArea ctrlArea=new CtrlArea();
	private ComposeGua64 cGua64=new ComposeGua64();
	private SwitchButton sBtn=new SwitchButton("switch",btnBgImg0,"先天八卦图");
	private SwitchButton sg8vs64Btn=new SwitchButton("s8vs64",btnGua64BgImg,"");
	private SwitchButton btnMusic=new SwitchButton("music",btnMusicImg0,"");
	private SwitchButton btnClose=new SwitchButton("close",btnCloseImg,"");
	private SwitchButton btnHelper=new SwitchButton("helper",helper,"");
	private Gua8 tempGua8=null;
	
	private SwitchButton btnLogo=new SwitchButton("logo",logo,"");
	private SwitchButton btnLogo2=new SwitchButton("logo2",logo2,"");
	
	private Matrix m;//记录适应矩阵
	private float mousex=0;
	private float mousey=0;
	
	private void drawGua8View(Canvas c)
	{
		c.save();
		p.setShader(this.bgImgShader);
		p.setAlpha(255);
		c.drawRect(0, 0, Home.srcWidth, Home.srcHeight, p);	
		p.setShader(null);
		c.translate(Home.xDisp/2, Home.yDisp/2);
		c.scale(Home.scrResize,Home.scrResize);//根据分辨率调整大小
		m=c.getMatrix();	
		//绘制logo
		c.save();
		c.translate(420,255);
		c.scale(3.5f, 3.5f);
		btnLogo.draw(c, p);
		c.restore();
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
		this.drawArow(c, p);
		c.restore();
		//按钮
		c.save();
		c.translate(270, 290);		
		c.scale(0.8f, 0.8f);
		sBtn.draw(c, p);
		c.restore();
		//按钮切换视图
		c.save();
		c.translate(30, 290);
		sg8vs64Btn.draw(c, p);
		c.restore();
		//帮助
		c.save();
		c.translate(350, 30);
		btnHelper.draw(c, p);
		c.restore();
		//按钮music
		c.save();
		c.translate(400, 30);
		btnMusic.draw(c, p);
		c.restore();
		//close按钮
		c.save();
		c.translate(450, 30);
		c.rotate(angleClose);
		btnClose.draw(c, p);
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
	
	private Path p1,p2,p3;
	private void initArow()
	{
		p1=new Path();
		p2=new Path();
		p3=new Path();
		//p1		
		p1.moveTo(-40, -5);
		p1.lineTo(10, -5);
		p1.lineTo(20, 0);
		p1.lineTo(10, 5);
		p1.lineTo(-40, 5);
		p1.close();
		//p2
		p2.moveTo(0, -10);
		p2.lineTo(30, 0);
		p2.lineTo(15, 0);
		p2.lineTo(10, -5);
		p2.close();
		//p3
		p3.moveTo(0, 10);
		p3.lineTo(10, 5);
		p3.lineTo(15, 0);
		p3.lineTo(30, 0);
		p3.close();
	}
	
	private Shader radialGradientArow=new RadialGradient(0,0,100,
			new int[]{Color.GRAY,Color.BLUE},
			//new int[]{Color.RED,Color.GREEN,Color.BLUE,Color.WHITE},
			null,Shader.TileMode.REPEAT);
	private void drawArow(Canvas c,Paint p)
	{
		//p.setColor(Color.argb(200, 0, 0, 0));
		p.setShader(radialGradientArow);
		//draw
		c.drawPath(p1, p);
		c.drawPath(p2, p);
		c.drawPath(p3, p);
		p.setShader(null);
	}
	
	private Gua64Graph gua64Graph=new Gua64Graph();	
	private float gua64PosX=240;
	private float gua64PosY=160;
	private float gua64Size=0.55f;
	private 
	SwitchButton btnAdd=new SwitchButton("add",add,"");
	SwitchButton btnSub=new SwitchButton("sub",sub,"");
	private void drawGua64View(Canvas c)
	{
		c.save();
		p.setShader(this.bgImgShader);
		p.setAlpha(255);
		c.drawRect(0, 0, Home.srcWidth, Home.srcHeight, p);	
		p.setShader(null);
		c.translate(Home.xDisp/2, Home.yDisp/2);
		c.scale(Home.scrResize,Home.scrResize);//根据分辨率调整大小
		m=c.getMatrix();	
/*		p.setColor(Color.GRAY);
		p.setStyle(Paint.Style.STROKE);
		c.drawRect(0, 0, 480, 320, p);
		p.setStyle(Paint.Style.FILL_AND_STROKE);*/
		//////////////		
		//////////////////////////////////////////		
		//64view绘制代码
		c.save();	
		c.translate(gua64PosX, gua64PosY);
		c.scale(gua64Size, gua64Size);
		gua64Graph.rotate(this.angle64);
		gua64Graph.draw(c, p);
		c.restore();
		//////////////
		//按钮切换视图
		c.save();
		c.translate(30, 290);
		sg8vs64Btn.draw(c, p);
		c.restore();
		//帮助
		c.save();
		c.translate(350, 30);
		btnHelper.draw(c, p);
		c.restore();
		//按钮music
		c.save();
		c.translate(400, 30);
		btnMusic.draw(c, p);
		c.restore();
		//close按钮
		c.save();
		c.translate(450, 30);
		c.rotate(angleClose);
		btnClose.draw(c, p);
		c.restore();
		//放大按钮
		c.save();
		c.translate(450, 190);
		c.scale(0.8f, 0.8f);
		btnAdd.draw(c, p);
		c.restore();
		//缩小按钮
		c.save();
		c.translate(450, 260);
		c.scale(0.75f, 0.75f);
		btnSub.draw(c, p);
		c.restore();
		
		///////////////
		c.restore();
	}
	
	private SwitchButton btnHelp=new SwitchButton("help",left,"");
	private int helpPosX=360;
	private int helpPosY=160;
	private boolean direction=false;//向左
	private boolean stop=false;
	private void drawHelp(Canvas c,Paint p)
	{
		c.translate(helpPosX, helpPosY);
		c.scale(2, 2);
		btnHelp.draw(c, p);
		if(helpPosX>=420)//最右
		{
			btnHelp.setBgImg(left);
			direction=!direction;//转向
		}
		else if(helpPosX<=300)//最左
		{
			btnHelp.setBgImg(right);
			direction=!direction;//转向
		}
		if(direction&&!stop)
		{
			helpPosX+=10;
		}
		else if(!stop)
		{
			helpPosX-=10;
		}
	}
	
	private GuaDetail guaDetail=null;
	
	private void drawGua64DetailView(Canvas c)
	{
		c.save();
		p.setShader(this.bgImgShader);
		p.setAlpha(255);
		c.drawRect(0, 0, Home.srcWidth, Home.srcHeight, p);	
		p.setShader(null);
		c.translate(Home.xDisp/2, Home.yDisp/2);
		c.scale(Home.scrResize,Home.scrResize);//根据分辨率调整大小
		m=c.getMatrix();	
		/////////////
		//详细解释绘制代码
		//绘制logo
		c.save();
		c.translate(420,270);
		/*p.setShader(logo2ImgShader);
		c.drawRect(0, 0, logo2.getWidth(), logo2.getHeight(), p);
		p.setShader(null);*/
		c.scale(10, 10);
		btnLogo2.draw(c, p);
		c.restore();
		//help
		c.save();
		this.drawHelp(c, p);
		c.restore();
		//details		
		c.save();
		c.translate(30, 30);
		if(guaDetail!=null)
			guaDetail.draw(c, p);
		c.restore();
		/////////////
		//按钮返回视图
		c.save();
		c.translate(30, 290);
		sg8vs64Btn.draw(c, p);
		c.restore();
		//帮助
		c.save();
		c.translate(350, 30);
		btnHelper.draw(c, p);
		c.restore();
		//按钮music
		c.save();
		c.translate(400, 30);
		btnMusic.draw(c, p);
		c.restore();
		//close按钮
		c.save();
		c.translate(450, 30);
		c.rotate(angleClose);
		btnClose.draw(c, p);
		c.restore();
		///////////////
		c.restore();
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
	
	//鼠标事件处理函数
	private void downGua8View(float x,float y)
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
				Home.playMusic(R.raw.put);
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
		else if(sBtn.onGraphic(x, y).equals("switch"))//切换卦图
		{
			this.hit="switch";
			sBtn.onTouch(0);
		}
		else//空白区
		{
			this.hit="";
		}
	}
	
	private int gua64Index=-1;//记录选中的64卦
	private void downGua64View(float x,float y)
	{
		if(this.btnAdd.onGraphic(x, y).equals("add"))//点击按钮放大
		{
			this.hit="add";
			this.btnAdd.onTouch(0);
		}
		else if(this.btnSub.onGraphic(x, y).equals("sub"))
		{
			this.hit="sub";
			this.btnSub.onTouch(0);
		}
		else
		{			
			String s=this.gua64Graph.onGraphic(x, y);
			if(!s.equals(""))//命中64卦
			{
				hit=s;
				//停止旋转
				this.rotating64=false;//停止64卦旋转
				this.gua64Index=Integer.parseInt(s);
				this.gua64Graph.onTouch(0, gua64Index);
			}
			else
				hit="move";//移动卦图
		}		
	}
	
	private void downDetailView(float x,float y)
	{
		this.guaDetail.onTouch(x,y);
		if(btnHelp.onGraphic(x, y).equals("help"))
		{
			btnHelp.onTouch(0);
			this.hit="help";
			this.stop=true;
		}
	}
	private String hit="";
	private void down(float x,float y)
	{
		if(this.viewType==0)
		{
			this.downGua8View(x, y);
		}
		else if(this.viewType==2)
		{
			this.downDetailView(x, y);
		}	
		
		////////////////
		if(this.sg8vs64Btn.onGraphic(x, y).equals("s8vs64"))//切换视图
		{
			this.hit="s8vs64";
			sg8vs64Btn.onTouch(0);
		}
		else if(this.btnHelper.onGraphic(x, y).equals("helper"))//帮助
		{
			this.hit="helper";
			btnHelper.onTouch(0);
		}
		else if(this.btnMusic.onGraphic(x, y).equals("music"))//切换音乐
		{
			this.hit="music";
			btnMusic.onTouch(0);
		}
		else if(this.btnClose.onGraphic(x, y).equals("close"))//关闭
		{
			this.hit="close";
			btnClose.onTouch(0);
		}
		/////////////////////////////////////最后处理64卦视图
		else if(this.viewType==1)
		{
			this.downGua64View(x, y);
		}
	}
	
	private void moveGua8View(float x,float y)
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
	private void moveGua64View(float x,float y)
	{
		if(hit.equals("move"))//触摸主体区域
		{
			this.gua64PosX+=this.mousex-this.lastx;
			this.gua64PosY+=this.mousey-this.lasty;
		}
	}
	private void moveDetailView(float x,float y)
	{		
	}
	private void move(float x,float y)
	{
		if(this.viewType==0)
		{
			this.moveGua8View(x, y);
		}
		else if(this.viewType==1)
		{
			this.moveGua64View(x, y);
		}
		else if(this.viewType==2)
		{
			this.moveDetailView(x, y);
		}
	}
	
	private void upGua8View(float x,float y)
	{
		rotating=true;
		if(this.hit.equals("taiji")&&this.taiji.onGraphic(x, y).equals("taiji"))
		{
			taijiActivated=!taijiActivated;
		}
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
			//切换视图
			try {
				this.guaDetail=new GuaDetail(cGua64.getType());
				this.viewType=2;
				this.sg8vs64Btn.setBgImg(back);
				this.oldViewType=0;//上一层的视图类型
			} catch (Exception e) {
				// TODO Auto-generated catch block
				cGua64.onTouch(2);
				this.hit="";
				return ;
			}
		}
		cGua64.onTouch(2);
		if(this.hit.equals("switch")&&sBtn.onGraphic(x, y).equals("switch"))
		{
			//切换八卦图
			if(this.gua8Graph.getType().equals("0"))
			{
				this.gua8Graph=this.gua8Graph_houtian;
				sBtn.setLabel("后天八卦图");
				sBtn.setBgImg(this.btnBgImg1);
			}
			else
			{
				this.gua8Graph=this.gua8Graph_xiantian;
				sBtn.setLabel("先天八卦图");
				sBtn.setBgImg(this.btnBgImg0);
			}

		}
		sBtn.onTouch(2);		
	}
	private boolean musicOn=true;
	private void upGua64View(float x,float y)
	{
		if(this.hit.equals("add")&&this.btnAdd.onGraphic(x, y).equals("add"))
		{
			if(this.gua64Size<2)
				this.gua64Size+=0.1;
		}
		this.btnAdd.onTouch(2);
		if(this.hit.equals("sub")&&this.btnSub.onGraphic(x, y).equals("sub"))
		{
			if(this.gua64Size>0.3)
				this.gua64Size-=0.1;
		}
		this.btnSub.onTouch(2);
		if(this.gua64Index!=-1)//已经按下64卦
		{						
			if(!this.gua64Graph.getGua64CirByIndex(gua64Index).onGraphic(x, y).equals("")
					||!this.gua64Graph.getGua64SquByIndex(gua64Index).onGraphic(x, y).equals(""))//有效
			{
				//转向卦64解析
				//转到下一个View
				//切换视图
				try {
					this.guaDetail=new GuaDetail(this.gua64Graph.getGua64SquByIndex(gua64Index).getType());
					this.sg8vs64Btn.setBgImg(back);
					this.viewType=2;
					this.oldViewType=1;//上一层的视图类型
				} catch (Exception e) {
					// TODO Auto-generated catch block
					this.hit="";
					gua64Index=-1;
					this.rotating64=true;
					return ;
				}
			}			
			this.gua64Graph.onTouch(2, gua64Index);
			this.rotating64=true;
			gua64Index=-1;
		}		
	}
	private void upDetailView(float x,float y)
	{
		if(this.hit.equals("help")&&btnHelp.onGraphic(x, y).equals("help"))
		{			
			//转向白话文
			if(direction)//右
			{
				this.btnHelp.setBgImg(left);				
			}
			else
			{
				this.btnHelp.setBgImg(right);
			}
			direction=!direction;
			Home.toMoreDetail(this.guaDetail.getType());
		}
		this.stop=false;
		btnHelp.onTouch(2);
	}
	
	private void up(float x,float y)
	{
		if(this.viewType==0)
		{
			this.upGua8View(x, y);
		}		
		else if(this.viewType==2)
		{
			this.upDetailView(x, y);
		}
		////////
		if(this.hit.equals("s8vs64")&&sg8vs64Btn.onGraphic(x, y).equals("s8vs64"))
		{
			//切换视图
			if(this.viewType==0)
			{
				this.viewType=1;
				this.sg8vs64Btn.setBgImg(btnGua8BgImg);
			}
			else if(this.viewType==1)
			{
				this.viewType=0;
				this.sg8vs64Btn.setBgImg(btnGua64BgImg);
			}
			else if(this.viewType==2)//返回按钮
			{
				this.viewType=this.oldViewType;//还原视图类型
				if(this.oldViewType==0)
				{
					sg8vs64Btn.setBgImg(btnGua64BgImg);
				}
				else if(this.oldViewType==1)
				{
					this.sg8vs64Btn.setBgImg(btnGua8BgImg);
				}
				this.guaDetail=null;
			}
		}
		sg8vs64Btn.onTouch(2);
		if(this.hit.equals("helper")&&btnHelper.onGraphic(x, y).equals("helper"))
		{
			Home.toMoreDetail("help");
		}
		btnHelper.onTouch(2);
		if(this.hit.equals("music")&&btnMusic.onGraphic(x, y).equals("music"))
		{
			if(musicOn)
			{
				musicOn=false;
				btnMusic.setBgImg(this.btnMusicImg1);
			}
			else
			{
				musicOn=true;
				btnMusic.setBgImg(this.btnMusicImg0);
			}
			Home.setCtrlMusic(musicOn);
		}
		btnMusic.onTouch(2);
		if(this.hit.equals("close")&&btnClose.onGraphic(x, y).equals("close"))
		{
			Home.close();
		}
		btnClose.onTouch(2);
		///////////////////////最后处理64卦视图
		if(this.viewType==1)
		{
			this.upGua64View(x, y);
		}
		this.hit="";
	}

}
