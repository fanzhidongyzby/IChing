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
 * SurfaceView �������
 */
public class MainView extends SurfaceView implements SurfaceHolder.Callback,Runnable
{

	private SurfaceHolder surfaceHolder;	
	
	//��Ⱦ����
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
	//Bitmap ��Ⱦ
	private  Shader bgImgShader=null;
	/*private  Shader logoImgShader=null;
	private  Shader logo2ImgShader=null;*/
/*	//���Խ�����Ⱦ
	public  static Shader linearGradient=null;
	//�����Ⱦ
	public  static Shader composeShader=null;
	//���ѽ�����Ⱦ
	public  static Shader radialGradient=null;	
	//�ݶ���Ⱦ
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
		//�����߳�
		new Thread(this).start();
	}

	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {
		// TODO Auto-generated method stub
		//ֹͣ�߳�
		loop=false;
	}

	public void setLoop(boolean l)
	{
		loop=l;
		if(loop)
			new Thread(this).start();
	}
	//����ѭ��
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
		//��������
		Canvas c=surfaceHolder.lockCanvas();
		if(surfaceHolder==null||c==null)
		{
			return ;
		}
		//��������
		angleClose=(angleClose+5)%360;
		if(rotating&&taijiActivated)
		{
			angle=(angle+1)%360;			
		}
		if(rotating64&&squareActivated)
		{
			angle64=(angle64+1)%360;
		}
		//����
		//p.setColor(Color.argb(255, 150, 150, 150));
		//c.drawRect(0, 0, Home.srcWidth, Home.srcHeight, p);
		c.drawColor(Color.argb(255, 0, 0, 0));
		//��ͼ
		if(viewType==0)
			this.drawGua8View(c);
		else if(viewType==1)
			this.drawGua64View(c);
		else if(viewType==2)
			this.drawGua64DetailView(c);
		//���ƺ����
		surfaceHolder.unlockCanvasAndPost(c);
	}
	
	private Gua8Gragh gua8Graph_xiantian=new Gua8Gragh("0");
	private Gua8Gragh gua8Graph_houtian=new Gua8Gragh("1");
	private Gua8Gragh gua8Graph=gua8Graph_xiantian;
	private Taiji taiji=new Taiji();
	private CtrlArea ctrlArea=new CtrlArea();
	private ComposeGua64 cGua64=new ComposeGua64();
	private SwitchButton sBtn=new SwitchButton("switch",btnBgImg0,"�������ͼ");
	private SwitchButton sg8vs64Btn=new SwitchButton("s8vs64",btnGua64BgImg,"");
	private SwitchButton btnMusic=new SwitchButton("music",btnMusicImg0,"");
	private SwitchButton btnClose=new SwitchButton("close",btnCloseImg,"");
	private SwitchButton btnHelper=new SwitchButton("helper",helper,"");
	private Gua8 tempGua8=null;
	
	private SwitchButton btnLogo=new SwitchButton("logo",logo,"");
	private SwitchButton btnLogo2=new SwitchButton("logo2",logo2,"");
	
	private Matrix m;//��¼��Ӧ����
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
		c.scale(Home.scrResize,Home.scrResize);//���ݷֱ��ʵ�����С
		m=c.getMatrix();	
		//����logo
		c.save();
		c.translate(420,255);
		c.scale(3.5f, 3.5f);
		btnLogo.draw(c, p);
		c.restore();
		//����ͼ
		c.save();
		c.translate(150, 140);
		c.scale(0.9f,0.9f);
		c.rotate(angle,0,0);
		gua8Graph.draw(c, p);
		c.restore();
		//̫��ͼ
		c.save();
		c.translate(150, 140);
		c.scale(1.1f,1.1f);	
		c.rotate(-angle,0,0);
		taiji.draw(c, p);
		c.restore();
		//��Ӧ����
		c.save();
		c.translate(150, 140);
		c.scale(2.5f, 2.5f);
		ctrlArea.draw(c, p);
		c.restore();
		//�ϳ�64��
		c.save();
		c.translate(420, 140);
		c.scale(1.3f, 1.3f);
		cGua64.draw(c, p);
		c.restore();
		//��ͷ
		c.save();
		c.translate(340, 140);
		this.drawArow(c, p);
		c.restore();
		//��ť
		c.save();
		c.translate(270, 290);		
		c.scale(0.8f, 0.8f);
		sBtn.draw(c, p);
		c.restore();
		//��ť�л���ͼ
		c.save();
		c.translate(30, 290);
		sg8vs64Btn.draw(c, p);
		c.restore();
		//����
		c.save();
		c.translate(350, 30);
		btnHelper.draw(c, p);
		c.restore();
		//��ťmusic
		c.save();
		c.translate(400, 30);
		btnMusic.draw(c, p);
		c.restore();
		//close��ť
		c.save();
		c.translate(450, 30);
		c.rotate(angleClose);
		btnClose.draw(c, p);
		c.restore();		
		//�϶�ͼ��		
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
		c.scale(Home.scrResize,Home.scrResize);//���ݷֱ��ʵ�����С
		m=c.getMatrix();	
/*		p.setColor(Color.GRAY);
		p.setStyle(Paint.Style.STROKE);
		c.drawRect(0, 0, 480, 320, p);
		p.setStyle(Paint.Style.FILL_AND_STROKE);*/
		//////////////		
		//////////////////////////////////////////		
		//64view���ƴ���
		c.save();	
		c.translate(gua64PosX, gua64PosY);
		c.scale(gua64Size, gua64Size);
		gua64Graph.rotate(this.angle64);
		gua64Graph.draw(c, p);
		c.restore();
		//////////////
		//��ť�л���ͼ
		c.save();
		c.translate(30, 290);
		sg8vs64Btn.draw(c, p);
		c.restore();
		//����
		c.save();
		c.translate(350, 30);
		btnHelper.draw(c, p);
		c.restore();
		//��ťmusic
		c.save();
		c.translate(400, 30);
		btnMusic.draw(c, p);
		c.restore();
		//close��ť
		c.save();
		c.translate(450, 30);
		c.rotate(angleClose);
		btnClose.draw(c, p);
		c.restore();
		//�Ŵ�ť
		c.save();
		c.translate(450, 190);
		c.scale(0.8f, 0.8f);
		btnAdd.draw(c, p);
		c.restore();
		//��С��ť
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
	private boolean direction=false;//����
	private boolean stop=false;
	private void drawHelp(Canvas c,Paint p)
	{
		c.translate(helpPosX, helpPosY);
		c.scale(2, 2);
		btnHelp.draw(c, p);
		if(helpPosX>=420)//����
		{
			btnHelp.setBgImg(left);
			direction=!direction;//ת��
		}
		else if(helpPosX<=300)//����
		{
			btnHelp.setBgImg(right);
			direction=!direction;//ת��
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
		c.scale(Home.scrResize,Home.scrResize);//���ݷֱ��ʵ�����С
		m=c.getMatrix();	
		/////////////
		//��ϸ���ͻ��ƴ���
		//����logo
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
		//��ť������ͼ
		c.save();
		c.translate(30, 290);
		sg8vs64Btn.draw(c, p);
		c.restore();
		//����
		c.save();
		c.translate(350, 30);
		btnHelper.draw(c, p);
		c.restore();
		//��ťmusic
		c.save();
		c.translate(400, 30);
		btnMusic.draw(c, p);
		c.restore();
		//close��ť
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
		mousey=pts[1];//��ԭ����320*480����ϵ
		//�����Ӧ�������		
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
		//��¼�ϴε�λ��
		lastx=mousex;
		lasty=mousey;
		return true;
	}
	
	//����¼�������
	private void downGua8View(float x,float y)
	{
		if(ctrlArea.onGraphic(x, y).equals("ctrlArea"))//����������
		{			
			rotating=false;//ֹͣ��ת
			if(taiji.onGraphic(x, y).equals("taiji"))//����̫��
			{
				this.hit="taiji";
				taiji.onTouch(0);
			}
			else if(!gua8Graph.onGraphic(x, y).equals(""))//��������
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
		else if(!cGua64.onGraphic(x, y).equals(""))//ѡ��64��
		{
			this.hit="cGua64";
			cGua64.onTouch(0);			
		}
		else if(sBtn.onGraphic(x, y).equals("switch"))//�л���ͼ
		{
			this.hit="switch";
			sBtn.onTouch(0);
		}
		else//�հ���
		{
			this.hit="";
		}
	}
	
	private int gua64Index=-1;//��¼ѡ�е�64��
	private void downGua64View(float x,float y)
	{
		if(this.btnAdd.onGraphic(x, y).equals("add"))//�����ť�Ŵ�
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
			if(!s.equals(""))//����64��
			{
				hit=s;
				//ֹͣ��ת
				this.rotating64=false;//ֹͣ64����ת
				this.gua64Index=Integer.parseInt(s);
				this.gua64Graph.onTouch(0, gua64Index);
			}
			else
				hit="move";//�ƶ���ͼ
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
		if(this.sg8vs64Btn.onGraphic(x, y).equals("s8vs64"))//�л���ͼ
		{
			this.hit="s8vs64";
			sg8vs64Btn.onTouch(0);
		}
		else if(this.btnHelper.onGraphic(x, y).equals("helper"))//����
		{
			this.hit="helper";
			btnHelper.onTouch(0);
		}
		else if(this.btnMusic.onGraphic(x, y).equals("music"))//�л�����
		{
			this.hit="music";
			btnMusic.onTouch(0);
		}
		else if(this.btnClose.onGraphic(x, y).equals("close"))//�ر�
		{
			this.hit="close";
			btnClose.onTouch(0);
		}
		/////////////////////////////////////�����64����ͼ
		else if(this.viewType==1)
		{
			this.downGua64View(x, y);
		}
	}
	
	private void moveGua8View(float x,float y)
	{
		if(this.hit.equals("ctrlArea"))//����������
		{
			if(ctrlArea.onGraphic(x, y).equals("ctrlArea"))//��Чת��
			{
				//��ת����
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
			//����Ƿ񵽴�ָ������
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
		else//�հ���
		{}
	}
	private void moveGua64View(float x,float y)
	{
		if(hit.equals("move"))//������������
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
		taiji.onTouch(2);//�ָ�̫��
		//��ָ�����򣨺ϳɣ�
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
			//ת����һ��View
			//�л���ͼ
			try {
				this.guaDetail=new GuaDetail(cGua64.getType());
				this.viewType=2;
				this.sg8vs64Btn.setBgImg(back);
				this.oldViewType=0;//��һ�����ͼ����
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
			//�л�����ͼ
			if(this.gua8Graph.getType().equals("0"))
			{
				this.gua8Graph=this.gua8Graph_houtian;
				sBtn.setLabel("�������ͼ");
				sBtn.setBgImg(this.btnBgImg1);
			}
			else
			{
				this.gua8Graph=this.gua8Graph_xiantian;
				sBtn.setLabel("�������ͼ");
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
		if(this.gua64Index!=-1)//�Ѿ�����64��
		{						
			if(!this.gua64Graph.getGua64CirByIndex(gua64Index).onGraphic(x, y).equals("")
					||!this.gua64Graph.getGua64SquByIndex(gua64Index).onGraphic(x, y).equals(""))//��Ч
			{
				//ת����64����
				//ת����һ��View
				//�л���ͼ
				try {
					this.guaDetail=new GuaDetail(this.gua64Graph.getGua64SquByIndex(gua64Index).getType());
					this.sg8vs64Btn.setBgImg(back);
					this.viewType=2;
					this.oldViewType=1;//��һ�����ͼ����
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
			//ת��׻���
			if(direction)//��
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
			//�л���ͼ
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
			else if(this.viewType==2)//���ذ�ť
			{
				this.viewType=this.oldViewType;//��ԭ��ͼ����
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
		///////////////////////�����64����ͼ
		if(this.viewType==1)
		{
			this.upGua64View(x, y);
		}
		this.hit="";
	}

}
