package upc.iching;

import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;

//����ͼ�εĻ��ࣺͼ�ε�λ��Ĭ����(0,0)����ͨ��TSRת��
public class Graphic 
{
	//����
	protected String type="";
	protected Matrix matrix;
	public void setMatrix(Matrix matrix) {
		this.matrix = matrix;
	}
	public Graphic()
	{}
	public String getType() {
		return type;
	}
	//����
	//����ͼ��
	public void draw(Canvas c,Paint p)
	{
		this.setMatrix(c.getMatrix());
	}
	//����ͼ��
	public String onGraphic(float x,float y)
	{		
		return "";
	}
}
