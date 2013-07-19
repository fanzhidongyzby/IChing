package upc.iching;



import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;

public class GuaDetail extends Graphic
{
	private Text mainTitle;
	private Text smallTitle;
	private Text guaCi;
	private Text[] yaoNames=new Text[6];
	private Yao[] yaos=new Yao[6];
	private Text[] yaoCis=new Text[6];
	
	private static String[] numText=new String[]
	               {"","һ","��","��","��","��","��","��","��","��"};
	private String cvtNumToText(int num)
	{		
		String s="";
		if(num/10!=0)//��λ��
		{
			if(num/10!=1)
				s+=GuaDetail.numText[num/10];
			s+="ʮ";
		}
		if(num%10!=0)//��λ����0
		{
			s+=GuaDetail.numText[num%10];
		}
		return s;
	}
	
	public GuaDetail(String type) throws Exception
	{
		this.type=type;
		Gua64 gua64=new Gua64(type);
		Gua8 guaUp=gua64.getUp();
		Gua8 guaDown=gua64.getDown();	
		SQLiteDatabase db=Home.openDataBase();
		Cursor cur=db.rawQuery("select * from gua64s where guanum="+gua64.getNum()+";", null);
		cur.moveToFirst();		
		//����������
		this.mainTitle=new Text(gua64.getName());
		mainTitle.setTextColor(Color.BLUE);
		//����С����
		String st="��"+this.cvtNumToText(gua64.getNum())+"��   ";
		if(guaUp.getType().equals(guaDown.getType()))//����
		{
			st+=gua64.getName()+"Ϊ"+guaUp.getObject();
		}
		else
		{
			st+=guaUp.getObject()+guaDown.getObject()+gua64.getName();			
		}
		st+="   "+guaUp.getName()+"��"+guaDown.getName()+"��";
		smallTitle=new Text(st);
		smallTitle.setRowLength(50);		
		smallTitle.setTextColor(Color.RED);
		//�����Դ�
		this.guaCi=new Text(new String(cur.getBlob(1),"gb2312"));
		guaCi.setShowTip(true);
		//��ʼ��س��Ϣ
		for(int i=0;i<6;i++)
		{			
			StringBuffer t=new StringBuffer("");
			String s=type.substring(i, i+1);
			//����س����
			this.yaos[5-i]=new Yao(s);
			//����type����س��
			if(s.equals("1"))
			{
				t.append("��");
			}
			else if(s.equals("0"))
			{
				t.append("��");
			}			
			if(i==0)//��س
			{
				t.append("��");
				t.reverse();
			}
			else if(i==5)//��س
			{
				t.append("��");
				t.reverse();
			}
			else
			{
				t.append(GuaDetail.numText[6-i]);
			}
			this.yaoNames[5-i]=new Text(t.toString());
			//����س��
			this.yaoCis[5-i]=new Text(new String(cur.getBlob(i+2),"gb2312"));
			this.yaoCis[5-i].setShowTip(true);
			this.yaoCis[5-i].setRowLength(13);
		}
		cur.close();
		db.close();
	}
	
	/*private Text mainTitle;
	private Text smallTitle;
	private Text guaCi;
	private Text[] yaoNames=new Text[6];
	private Yao[] yaos=new Yao[6];
	private Text[] yaoCis=new Text[6];*/
	
	public void draw(Canvas c,Paint p)
	{
		super.draw(c, p);		
		c.save();
		//����
		mainTitle.draw(c, p);
		//�Դ�
		c.save();
		c.translate(70, 7);
		c.scale(0.6f, 0.6f);
		smallTitle.draw(c, p);
		c.restore();
		//س
		c.save();
		c.translate(0, 200);
		for(int i=0;i<6;i++)
		{
			c.save();
			c.scale(0.6f, 0.6f);
			this.yaoNames[i].draw(c, p);
			c.restore();
			
			c.save();
			c.translate(115, 8);
			c.scale(4.5f, 2.5f);
			this.yaos[i].draw(c, p);
			c.restore();
			c.translate(0, -30);
		}		
		c.restore();
		//ѡ��س
		if(selectedYaoNum>=1&&selectedYaoNum<=6)
		{
			c.save();
			p.setStyle(Paint.Style.STROKE);
			p.setColor(Color.BLUE);			
			c.translate(115, 58+30*(6-selectedYaoNum));
			c.drawRect(-70, -10, 70, 10, p);
			p.setStyle(Paint.Style.FILL_AND_STROKE);
			c.restore();
		}
		//��ʾ����
		c.save();
		c.translate(tipXPos, tipYPos);
		c.scale(0.55f, 0.55f);
		if(selectedYaoNum==0)//guaci
		{
			this.guaCi.draw(c, p);
		}
		else if(selectedYaoNum>=1&&selectedYaoNum<=6)//س��
		{
			try
			{
				this.yaoCis[6-selectedYaoNum].draw(c, p);
			}
			catch(Exception e)
			{}			
		}
		c.restore();
		/////////////////

		c.restore();
	}
	
	//����ͼ��
	public String onGraphic(float x,float y)
	{		
		if(mainTitle.onGraphic(x, y).equals("text"))
			return "0";
		float xd=0,yd=0;
		for(int i=0;i<6;i++)
		{
			xd=x-115;
			yd=y-58-30*i;
			if(xd>=-70&&xd<=70&&yd>=-10&&yd<=10)
				return String.valueOf(6-i);
		}
		return "7";
	}
	private int selectedYaoNum=-1;
	private float tipXPos=0;
	private float tipYPos=0;
	public void onTouch(float x,float y)
	{
		float pts[]=new float[]{x,y};
		Matrix inv=new Matrix();
		this.matrix.invert(inv);
		inv.mapPoints(pts);
		x=pts[0];
		y=pts[1];//��ԭ����
		
		int hit=Integer.parseInt(this.onGraphic(x, y));
		if(hit==0)
		{
			mainTitle.setShowBound(true);
			this.tipXPos=mainTitle.getW()+24;
			this.tipYPos=7;
			selectedYaoNum=0;			
			Home.playMusic(R.raw.put);
		}
		else if(hit>=1&&hit<=6)
		{
			selectedYaoNum=hit;
			this.tipXPos=185+20;
			this.tipYPos=50+(6-hit)*30;
			mainTitle.setShowBound(false);
			Home.playMusic(R.raw.put);
		}
		else
		{
			mainTitle.setShowBound(false);
			selectedYaoNum=-1;
		}
		
	}
}
