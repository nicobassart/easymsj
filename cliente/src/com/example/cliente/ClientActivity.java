package com.example.cliente;

import java.io.IOException;

import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.AMQP.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;
import com.rabbitmq.client.impl.AMQImpl.Channel;

public class ClientActivity extends Activity {
	
    public String mServer;
    public String mExchange;

    protected Channel mModel = null;
    protected Connection  mConnection;

    protected boolean Running ;

    protected  String MyExchangeType ;
	
	Button button;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_client);

		button = (Button) findViewById(R.id.button1);

		button.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				try {
					//Lanzamos el thread
					//this.lanzarThread();
	
					Toast.makeText(getApplicationContext(), "SMS Sent!",Toast.LENGTH_LONG).show();
				} catch (Exception e) {
					Toast.makeText(getApplicationContext(),	"SMS faild, please try again later!", Toast.LENGTH_LONG).show();
					e.printStackTrace();
				}

			}
			public void lanzarThread() {
				new Thread(new Runnable() {
					
					public void run() {
						try {
							final String url = getString(R.string.base_uri) + "/bandejaDeSalida";
							
							// Create a new RestTemplate instance
							RestTemplate restTemplate = new RestTemplate();

							// Add the String message converter
							restTemplate.getMessageConverters().add(new StringHttpMessageConverter());

							// Make the HTTP GET request, marshaling the response to a String
							String result = restTemplate.getForObject(url, String.class);
							
							Toast.makeText(getApplicationContext(), "SMS Sent!" + result ,
									Toast.LENGTH_LONG).show();
							Thread.sleep(10);
							Thread.currentThread().run();
						} catch (Exception e) {
							System.out.println("Ignorierter Fehler: " + e);
						}
					}
				}).start();
			}
		});

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.client, menu);
		return true;
	}

}
