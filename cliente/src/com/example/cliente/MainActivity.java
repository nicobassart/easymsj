/*
 * Copyright 2010-2013 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.cliente;


import java.io.ByteArrayOutputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.Random;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.AsyncTask;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.LongString;
import com.rabbitmq.client.QueueingConsumer;

public class MainActivity extends AbstractAsyncActivity {

	protected static final String TAG = MainActivity.class.getSimpleName();
	
	
    protected Channel mModel = null;
    protected Connection  mConnection;
    private String mQueue;
    private QueueingConsumer MySubscription;
    private String prueba;
    
    String SENT = "SMS_SENT";
    String DELIVERED = "SMS_DELIVERED";

    List<Intent> intents = new ArrayList<Intent>();
    
	// ***************************************
	// Activity methods
	// ***************************************
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		// Initiate the request to the protected service
		Button submitButton = (Button) findViewById(R.id.submit);
		submitButton.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				new FetchSecuredResourceTask().execute();
			}
		});
		
		
	}
	
	// ***************************************
	// Private methods
	// ***************************************
	private void displayResponse(Message response) {
		Toast.makeText(this, response.getText(), Toast.LENGTH_LONG).show();
	}

	// ***************************************
	// Private classes
	// ***************************************
	private class FetchSecuredResourceTask extends AsyncTask<Void, Void, Message> {
	    private MessageConsumer mConsumer;
	    private TextView mOutput;

	    
		@Override
		protected void onPreExecute() {
	 
	        ((Button) findViewById(R.id.submit)).setVisibility(View.GONE);
		}

		@Override
		protected Message doInBackground(Void... params) {
			
			mOutput =  (TextView) findViewById(R.id.output);
			String message = null;
			ConnectionFactory connectionFactory = new ConnectionFactory();
			connectionFactory.setHost("turtle.rmq.cloudamqp.com");
			connectionFactory.setPassword("V-2pWPQFVq-1xvJoKBIIcQxtD8086r20" );
			connectionFactory.setUsername("ynmemqdl");
			connectionFactory.setPort(5672);
			connectionFactory.setVirtualHost("ynmemqdl");
			try {
	    	   mConnection = connectionFactory.newConnection();
	    	   mModel = mConnection.createChannel();
	    	   mModel.queueBind("myQueue", "myExchange", "foo.*");
	    	   

	    	    QueueingConsumer consumer = new QueueingConsumer(mModel);
	    	    mModel.basicConsume("myQueue", true, consumer);
	    	    
	    	    runOnUiThread(new Runnable() {
	                 @Override
	                 public void run() {
	                	 ((TextView) findViewById(R.id.mensajeConexion)).setText("CONECTADO...");	
	                 }
	              });
	    	    
	    	   while (true) {
	    		      QueueingConsumer.Delivery delivery;
	    		      delivery = consumer.nextDelivery();
	    		      runOnUiThread(new Runnable() {
	    	                 @Override
	    	                 public void run() {
	    	                	 Integer i = Integer.valueOf(((TextView) findViewById(R.id.mensajesDesencolados)).getText().toString()) + 1;
	    	                	((TextView) findViewById(R.id.mensajesDesencolados)).setText(i.toString());	
	    	                 }
	    	              });
	    		      LongString telefono = (LongString)delivery.getProperties().getHeaders().get("telefono");
	    		      message = new String(delivery.getBody());
	    		      sendSMS(telefono.toString(), message);
	    	   }
			} catch (Exception e1) {
	    	   return new Message(1, "ERROR: \n", e1.toString());
			}
			
		}

		@Override
		protected void onPostExecute(Message message) {
	        //The output TextView we'll use to display messages
	        mOutput =  (TextView) findViewById(R.id.output);
	        mOutput.setText(message.getSubject() + message.getText());
			dismissProgressDialog();
			displayResponse(message);
		}

	}
	
	private void sendSMS(String phoneNumber, String message)
	{        
		Random rand = new Random();

		int  n = rand.nextInt(50000000);
		
	    PendingIntent sentPI = PendingIntent.getBroadcast(getApplicationContext(), 0,
	            new Intent(SENT+n), 0);

	    PendingIntent deliveredPI = PendingIntent.getBroadcast(getApplicationContext(), 0,
	            new Intent(DELIVERED+n), 0);

	    final String fPhoneNumber = phoneNumber;
	    final String fMessage = message;
	        
	    //---when the SMS has been sent---
	    registerReceiver(new BroadcastReceiver(){
	        @Override
	        public void onReceive(Context arg0, Intent arg1) {
	            switch (getResultCode())
	            {
	                case Activity.RESULT_OK:
	            	       runOnUiThread(new Runnable() {
	                        @Override
	                        public void run() {
	                        	 Integer i = Integer.valueOf(((TextView) findViewById(R.id.mensajesEnviados)).getText().toString()) + 1;
	                        	 TextView mOutput =  (TextView) findViewById(R.id.mensajesEnviados);
	                	         mOutput.setText(i.toString());
	                        }
	                     });
	                    break;
	                    default:
	                    	 runOnUiThread(new Runnable() {
	 	                        @Override
	 	                        public void run() {
	 	                        	 Integer i = Integer.valueOf(((TextView) findViewById(R.id.mensajesNoEnviados)).getText().toString()) + 1;
	 	                        	 TextView mOutput =  (TextView) findViewById(R.id.mensajesNoEnviados);
	 	                	         mOutput.setText(i.toString());
	 	                        }
	 	                     });
	                    	 
	                    	 publishMessage(fPhoneNumber, fMessage);
	                    	 
	                    	break;
	            }
	        }
	    }, new IntentFilter(SENT+n));

	    //---when the SMS has been delivered---
	    registerReceiver(new BroadcastReceiver(){
	        @Override
	        public void onReceive(Context arg0, Intent arg1) {
	            switch (getResultCode())
	            {
	                case Activity.RESULT_OK:
	            	       runOnUiThread(new Runnable() {
	                        @Override
	                        public void run() {
	                        	 Integer i = Integer.valueOf(((TextView) findViewById(R.id.mensajesEntregados)).getText().toString()) + 1;
	                        	 TextView mOutput =  (TextView) findViewById(R.id.mensajesEntregados);
	                	         mOutput.setText(i.toString());
	                        }
	                     });
	                    break;
	                default:
                   	 runOnUiThread(new Runnable() {
	                        @Override
	                        public void run() {
	                        	 Integer i = Integer.valueOf(((TextView) findViewById(R.id.mensajesNoEnviados)).getText().toString()) + 1;
	                        	 TextView mOutput =  (TextView) findViewById(R.id.mensajesNoEnviados);
	                	         mOutput.setText(i.toString());
	                        }
	                     });
                   	   
                   	 publishMessage(fPhoneNumber, fMessage);
                   	 
                   	break;                  
	            }
	        }
	    }, new IntentFilter(DELIVERED+n));        

	    SmsManager sms = SmsManager.getDefault();
	    sms.sendTextMessage(phoneNumber, null, message, sentPI, deliveredPI);  
	   
	}
	
	void publishMessage(String telefono, String message) {
		Properties msg = new Properties();
		msg.put("telefono", telefono);
		msg.put("body", message);

		try {
			ByteArrayOutputStream bos = new ByteArrayOutputStream();
			ObjectOutput out = null;
			out = new ObjectOutputStream(bos);
			out.writeObject(msg);
			byte[] msgBytes = bos.toByteArray();

			mModel.basicPublish("", "errorSms", null, msgBytes);
			
//          Para tomar el valor de la cola
//  		bis = new ByteArrayInputStream(delivery.getBody());
//          ois = new ObjectInputStream(bis);
//          msg2 = (Properties)ois.readObject();
	    	    
		} catch (Exception e) {
			System.out.println(e);
		}
	}

	
}
