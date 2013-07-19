package upc.iching;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import android.app.Activity;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.Window;
import android.view.WindowManager;



public class Home extends Activity {
	
	public static MainView view=null;
	public static float scrResize=1;//��¼��Ļ��С�仯
	public static float xDisp=0;//�仯��Сƫ��
	public static float yDisp=0;
	public static int srcWidth=480;
	public static int srcHeight=320;
    /** Called when the activity is first created. */
	private static Home home=null;
    @Override
    public void onCreate(Bundle savedInstanceState) 
    {
        super.onCreate(savedInstanceState);
        
        //ȡ��������
	    this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		//ȫ��ģʽ
		this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
		//����
		//this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);		
		
        view=new MainView(this);
        setContentView(view);
        
        DisplayMetrics outMetrics=new DisplayMetrics();
		this.getWindowManager().getDefaultDisplay().getMetrics(outMetrics);	
		Home.srcWidth=outMetrics.widthPixels;
		Home.srcHeight=outMetrics.heightPixels;
		float ws=outMetrics.widthPixels/480f;
		float hs=outMetrics.heightPixels/320f;
		if(ws<hs)
		{
			Home.scrResize=ws;
			yDisp=(hs-ws)*320;
		}
		else
		{
			Home.scrResize=hs;
			xDisp=(ws-hs)*480;
		}
		home=this;
		init();
	}
    private boolean ctrlMusic=true;
    public static void setCtrlMusic(boolean on)
    {
    	home.ctrlMusic=on;
    }
    public static void playMusic(int id)
    {
    	if(!home.ctrlMusic)
    		return;
    	MediaPlayer m=MediaPlayer.create(home, id);
    	m.setLooping(false);
    	try {
			m.prepare();
		} catch (IllegalStateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		m.start();
		m.setOnCompletionListener(new OnCompletionListener()
		{
			public void onCompletion(MediaPlayer mp)
			{
				mp.release();
			}
		});
    }
    private void init()
    {
    	//�ж��ļ��Ƿ����
    	boolean exist=false;
    	try {
			FileInputStream fin=new FileInputStream("data/data/upc.iching/files/help.txt");
			if(fin!=null)
				exist=true;
			else
				exist=false;
				
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			exist=false;
		}
		if(exist)
			return;
    	//�½������ݿ�
		SQLiteDatabase db=this.openOrCreateDatabase("data.db", MODE_PRIVATE, null);
		db.close();
		this.deleteDatabase("data.db");
		try {
			this.openFileOutput("text.txt", MODE_PRIVATE);
			this.deleteFile("text.txt");
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//����raw���ݵ�apkĿ¼
		//����txt
		for(int i=0;i<64;i++)
		{
			int start=R.raw.gua000000;
			this.copyRaw(start+i, "data/data/upc.iching/files/"
					+Gua64.indexToType(i)+".txt");
		}
		//�������ݿ�
		this.copyRaw(R.raw.data, "data/data/upc.iching/databases/data.db");
		//���ư����ļ�
		this.copyRaw(R.raw.help, "data/data/upc.iching/files/help.txt");
    }
    
    private void copyRaw(int id,String desDir)
    {
    	InputStream in = null;
    	OutputStream out = null;
    	BufferedInputStream bin = null;
    	BufferedOutputStream bout = null;
    	try
    	{
    		in = getResources().openRawResource(id);
    		out = new FileOutputStream(desDir);
    		bin = new BufferedInputStream(in);
    		bout = new BufferedOutputStream(out);
    		byte[] b = new byte[1024];
    		int len = bin.read(b);
    		while (len != -1)
    		{
    			bout.write(b, 0, len);
        		len = bin.read(b);
    		}
    		bin.close();
    		in.close();
    		bout.close();
    		out.close();
    	}
    	catch(Exception e)
    	{}
    }

    public boolean onTouchEvent(MotionEvent e)
    {   
    	return view.onTouch(e);
    }

    public static void close()
    {
    	Home.home.finish();
    }   
    
    
    public static SQLiteDatabase openDataBase()
    {
    	return home.openOrCreateDatabase("data.db", MODE_PRIVATE, null);
    }
    
    public static void toMoreDetail(String fileName)
    {
    	//ʵ����Bundle���󣬱�������
    	Bundle b=new Bundle();
		//��Bundle������û����ƺ��û�����
		b.putString("fileName",fileName);
		//ʵ����Intent����ת��RegisterResultActvity
		Intent intent=new Intent(home,MoreDetail.class);
		//��Bundle��ӵ�Intent
		intent.putExtra("data",b);
		//����Activity
		home.startActivity(intent);
		//������ǰActivity
		//home.finish();
    }
    
}