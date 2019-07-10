package saki.demo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.*;
import java.util.*;
import android.database.sqlite.*;
import java.io.*;
import android.view.View.*;
import android.view.*;
import android.content.*;
import android.database.*;
import android.util.*;
import saki.demo.Util;
import android.os.*;


public class Test1Activity extends AppCompatActivity {

	private Button button1 = null;
	private EditText edittextbody = null;
	private EditText edittextkey = null;
	private EditText edittextresult = null;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test1);
		Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
		button1 = (Button) findViewById(R.id.mainButton1);
		edittextbody = (EditText) findViewById(R.id.mainEditText1);
		edittextkey = (EditText) findViewById(R.id.mainEditText2);
		edittextresult = (EditText) findViewById(R.id.mainEditText3);

		button1.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v)
				{
					if (String.valueOf(edittextbody.getText()).equals("") != true && String.valueOf(edittextkey.getText()).equals("") != true)
					{
					    Toast.makeText(Test1Activity.this, "Started", Toast.LENGTH_SHORT).show();
					    boolean result = start_decrypt(String.valueOf(edittextbody.getText()), String.valueOf(edittextkey.getText()));
						Toast.makeText(Test1Activity.this, String.valueOf(result), Toast.LENGTH_SHORT).show();

					}
					else
					{
						Toast.makeText(Test1Activity.this, "Must not be empty", Toast.LENGTH_SHORT).show();
					}
				}


			});

    }
	public Handler mHandler = new Handler() {

		@Override
		public void handleMessage(Message msg)
		{
			super.handleMessage(msg);
			switch (msg.what)
			{
				case 0:
					//完成主界面更新,拿到数据
					String data = (String)msg.obj;
					edittextresult.setText(data);
					break;
				default:
					break;
			}
		}

	};

	private boolean start_decrypt(String body, String key)
	{
		byte[] bodys = str_to_byte.str_to_byte(body.replaceAll("\\n",""));
		byte[] keys = str_to_byte.str_to_byte(key);
		if (keys.length!=16){
			return false;
		}
		Crypter y = new Crypter();
		byte[] results = y.decrypt(bodys,keys);
		String result = str_to_byte.byte2HexString(results);
		mHandler.sendEmptyMessage(0);

		//需要数据传递，用下面方法；
		Message msg =new Message();
		//可以是基本类型，可以是对象，可以是List、map等；
		if(result== null){
			msg.obj = "失败";
		}else{
			msg.obj = result;
		}
		mHandler.sendMessage(msg);
		// TODO: Implement this method
		return true;
	}



	
		
    
		
    
}
