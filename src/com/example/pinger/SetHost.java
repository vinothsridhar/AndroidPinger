package com.example.pinger;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class SetHost extends Activity {

	Button ping;
	EditText host,timeout;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_set_host);
		
		ping=(Button)this.findViewById(R.id.button1);
		host=(EditText)this.findViewById(R.id.hname);
		timeout=(EditText)this.findViewById(R.id.timeout);
		
		host.requestFocus();
		
		Intent i=new Intent(getApplicationContext(),pingerService.class);
		getApplicationContext().stopService(i);
		
		if(Constants.timer!=null)
		{
		Constants.timer.cancel();
		Constants.timer=null;
		}
		
		ping.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
			// TODO Auto-generated method stub
				
				if(!host.getText().toString().equals(""))
				{
				Constants.HOSTNAME=host.getText().toString();
				try
				{
				Constants.TIMEOUT=Integer.valueOf(timeout.getText().toString());
				}
				catch(Exception e)
				{
					Constants.TIMEOUT=1;
				}
				Intent i=new Intent(getApplicationContext(),startPinger.class);
				getApplicationContext().startService(i);
				finish();
				}
				else
					Toast.makeText(getApplicationContext(), "Enter host name.", Toast.LENGTH_LONG).show();
			}
		});
	}
}
