package com.example.cliente;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.Menu;
import android.widget.Button;

import com.rabbitmq.client.AMQP.Connection;
import com.rabbitmq.client.impl.AMQImpl.Channel;

@SuppressLint("NewApi")
public class ClientActivity extends Activity {
	
    public String mServer;
    public String mExchange;

    protected Channel mModel = null;
    protected Connection  mConnection;

    protected boolean Running ;

    protected  String MyExchangeType ;
	
	Button button;

	
	@SuppressLint("NewApi")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	      if (android.os.Build.VERSION.SDK_INT > 9) {
	          StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
	          StrictMode.setThreadPolicy(policy);
	        }
		Intent intent = new Intent(this, ServiceSMS.class);
		startService(intent);

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.client, menu);
		return true;
	}

}
