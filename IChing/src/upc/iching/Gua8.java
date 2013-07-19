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
	              {"��","��","ˮ","��","ɽ","��","��","��"};//���Զ�Ӧ������
	public static String[] GUA_8_NAME=new String[]
	              {"��","��","��","��","��","��","��","Ǭ"};//���Ե�����
	//����
	//type;//��ʶ8�Ե����ͣ�"000"-"111",Ϊ����
	private Yao[] yao=new Yao[3];
	private String name;//����
	private String object;//����
	private boolean showName;//�Ƿ���ʾ����
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
	
	//����
	public Gua8(String type)
	{
		this.type=type;
		this.showName=true;
		//����type
		String[] tokens=new String[3];
		//��ʼ��س����,�����ţ��ó�����
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
		if(showName)//��������
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
		//��������
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
		y=pts[1];//��ԭ����
		
		if(x>=-15&&x<=15&&y>=-42.5&&y<=12.5)
		{
			return this.type;
		}
		
		return "";
	}
}
