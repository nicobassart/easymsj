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
    
    
    
	// ***************************************
	// Activity methods
	// ***************************************
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		// Initiate the request to the protected service
		final Button submitButton = (Button) findViewById(R.id.submit);
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
	        //super.onCreate(savedInstanceState);
	        setContentView(R.layout.activity_main);
	 
	        //The output TextView we'll use to display messages
	        mOutput =  (TextView) findViewById(R.id.output);
		}

		@Override
		protected Message doInBackground(Void... params) {
			mOutput =  (TextView) findViewById(R.id.output);
			String message = null;
			ConnectionFactory connectionFactory = new ConnectionFactory();
			connectionFactory.setHost("turtle.rmq.cloudamqp.com");
			//connectionFactory.setHost("lemur.cloudamqp.com");
			//connectionFactory.setPassword("X42BZqqOcWm33xzpookIFZbuqKdb_aiz" );
			connectionFactory.setPassword("V-2pWPQFVq-1xvJoKBIIcQxtD8086r20" );
			//connectionFactory.setUsername("agprifpq");
			connectionFactory.setUsername("ynmemqdl");
			connectionFactory.setPort(5672);
			//connectionFactory.setVirtualHost("agprifpq");
			connectionFactory.setVirtualHost("ynmemqdl");
			try {
	    	   mConnection = connectionFactory.newConnection();
	    	   mModel = mConnection.createChannel();
	    	   mModel.queueBind("myQueue", "myExchange", "foo.*");
	    	   

	    	    QueueingConsumer consumer = new QueueingConsumer(mModel);
	    	    mModel.basicConsume("myQueue", true, consumer);

	    	   int enviados = 0; 
	    	   while (true) {
	    		      QueueingConsumer.Delivery delivery;
	    		      delivery = consumer.nextDelivery();
	    		      LongString telefono = (LongString)delivery.getProperties().getHeaders().get("telefono");
	    		      message = new String(delivery.getBody());
	    		      SmsManager sms = SmsManager.getDefault();
	    		       sms.sendTextMessage(telefono.toString(), null, message, null, null);
	    		      
	    		       enviados+=1;
	    		       final int enviadosFinal = enviados;
	    		       runOnUiThread(new Runnable() {
	    	               @Override
	    	               public void run() {
	    	            	   mOutput =  (TextView) findViewById(R.id.output);
	    	       	           mOutput.setText("CLIENTE ACTIVO \n Enviados: " + enviadosFinal);
	    	               }
	    	            });
	    		       
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
	
}
