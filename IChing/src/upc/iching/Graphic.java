package upc.iching;

import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;

//所有图形的基类：图形的位置默认在(0,0)处，通过TSR转换
public class Graphic 
{
	//属性
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
	//方法
	//绘制图形
	public void draw(Canvas c,Paint p)
	{
		this.setMatrix(c.getMatrix());
	}
	//命中图形
	public String onGraphic(float x,float y)
	{		
		return "";
	}
}
