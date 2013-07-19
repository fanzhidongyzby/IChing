package upc.iching;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.widget.TextView;

public class MoreDetail extends Activity 
{
	public void onCreate(Bundle savedInstanceState) 
    {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.main);
        //获得Intent
		Intent intent=this.getIntent();
		//从Intent中获得Bundle
		Bundle b=intent.getBundleExtra("data");		
		//从Bundle中获得属性，添加到List
		String fileName=b.getString("fileName");
		if(fileName.equals("help"))
			this.setTitle("帮助");
		else
			this.setTitle(new Gua64(fileName).getName());
		this.load(fileName);

    }
	
	public boolean onTouchEvent(MotionEvent e)
    {   
		this.finish();
		return false;
    }

	public boolean onCreateOptionsMenu(Menu menu)
    {
    	MenuInflater inflater =this.getMenuInflater();
    	inflater.inflate(R.menu.menu, menu);
    	return true;
    }
    
    public boolean onOptionsItemSelected(MenuItem item)
    {
    	int itemId=item.getItemId();
    	switch(itemId)
    	{
    	case R.id.backword:
    		this.finish();
    		break;
    	case R.id.exit:
    		this.finish();
    		Home.close();
    		break;
    	}
    	return true;
    }
    private void load(String fileName)
    {

    	FileInputStream  fis;
		try {
			   fis = this.openFileInput(fileName+".txt");
			   BufferedReader br = new BufferedReader(new InputStreamReader(fis,
			     "GB2312"));

			   StringBuffer sb = new StringBuffer();
			   String data = "";

			   while ((data = br.readLine()) != null) {
			        sb.append(data+"\n");   
			   }   
			   String result = sb.toString();

			   TextView t=(TextView)this.findViewById(R.id.ViewMain);
			   t.setBackgroundColor(Color.argb(100,162,162,255));
		       t.setText(result);
		       fis.close();
		} catch (Exception  e) {
			// TODO Auto-generated catch block
			TextView t=(TextView)this.findViewById(R.id.ViewMain);
			this.setTitle("警告");
			t.setBackgroundColor(Color.argb(100,162,162,255));
		    t.setText("\n\n          程序文件丢失!          \n\n");
		}
    }
}
