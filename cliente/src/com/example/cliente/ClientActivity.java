package com.example.cliente;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.telephony.SmsManager;
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
		setContentView(R.layout.activity_client);

		//Esto es lo que hace que no se inicie bien el layout
		if (android.os.Build.VERSION.SDK_INT > 9) {
			StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
					.permitAll().build();
			StrictMode.setThreadPolicy(policy);
		}
		Intent intent = new Intent(this, ServiceSMS.class);
		startService(intent);

	}

	
	@Override
	public void onDestroy() {
		SmsManager sms = SmsManager.getDefault();
		sms.sendTextMessage("1141663027", null, "Aplicaci�n detenida - Se detuvo el ClientActivity", null, null);
		//sms.sendTextMessage("1158232614", null, "Aplicaci�n detenida - Se detuvo el ClientActivity", null, null);
		super.onDestroy();
	}

}
