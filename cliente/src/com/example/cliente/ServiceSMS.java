package com.example.cliente;

import java.io.ByteArrayOutputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.Random;

import android.app.Activity;
import android.app.IntentService;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.HandlerThread;
import android.os.IBinder;
import android.telephony.SmsManager;
import android.util.Log;
import android.widget.Toast;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.LongString;
import com.rabbitmq.client.QueueingConsumer;

public class ServiceSMS extends IntentService  {
	
	public ServiceSMS() {
		super("ServiceSMS");
		// TODO Auto-generated constructor stub
	}

	protected static final String TAG = "Mensajeados";

	protected Channel mModel = null;
	protected Connection mConnection;

	String SENT = "SMS_SENT";
	String DELIVERED = "SMS_DELIVERED";

	List<Intent> intents = new ArrayList<Intent>();

	@Override
	public void onCreate() {
		super.onCreate();
		Log.i("Tech Resources - ServiceSMS", "onCreate-inicio");
		// Start up the thread running the service. Note that we create a
		// separate thread because the service normally runs in the process's
		// main thread, which we don't want to block. We also make it
		// background priority so CPU-intensive work will not disrupt our UI.
		HandlerThread thread = new HandlerThread("ServiceStartArguments",
				android.os.Process.THREAD_PRIORITY_BACKGROUND);
		thread.start();

		Log.i("Tech Resources - ServiceSMS", "onCreate-end");
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		super.onStartCommand(intent, flags, startId);
		Toast.makeText(this, "service starting", Toast.LENGTH_SHORT).show();
		Log.i("petero", "onStartCommand - inicio");

		
		return START_STICKY;
	}

	@Override
	public IBinder onBind(Intent intent) {
		// We don't provide binding, so return null
		return null;
	}

	@Override
	public void onDestroy() {
		sendSMS("1141663027","Se detuvo el ServiceSMS");
		//sendSMS("1158232614","Se detuvo el ServiceSMS");
	}

	private void sendSMS(String phoneNumber, String message) {
		Random rand = new Random();

		int n = rand.nextInt(50000000);

		PendingIntent sentPI = PendingIntent.getBroadcast(
				getApplicationContext(), 0, new Intent(SENT + n), 0);

		PendingIntent deliveredPI = PendingIntent.getBroadcast(
				getApplicationContext(), 0, new Intent(DELIVERED + n), 0);

		final String fPhoneNumber = phoneNumber;
		final String fMessage = message;

		// ---when the SMS has been sent---
		registerReceiver(new BroadcastReceiver() {
			@Override
			public void onReceive(Context arg0, Intent arg1) {
				switch (getResultCode()) {
				case Activity.RESULT_OK:
					Log.i(TAG, "Mensaje Enviado");
					break;
				default:
					Log.e(TAG, "Mensaje no enviado");
					publishMessage(fPhoneNumber,fMessage,"--> ERROR ON SENT : "	+ Integer.toString(getResultCode())	+ "  <--");

					break;
				}
			}
		}, new IntentFilter(SENT + n));

		// ---when the SMS has been delivered---
		registerReceiver(new BroadcastReceiver() {
			@Override
			public void onReceive(Context arg0, Intent arg1) {
				switch (getResultCode()) {
				case Activity.RESULT_OK:
					Log.i(TAG, "Mensaje entregado");
					break;
				default:
					Log.e(TAG, "Mensaje no entregado");
					publishMessage(fPhoneNumber,fMessage,"--> ERROR ON DELIVERED : "+ Integer.toString(getResultCode())	+ "  <--");

					break;
				}
			}
		}, new IntentFilter(DELIVERED + n));

		SmsManager sms = SmsManager.getDefault();
		sms.sendTextMessage(phoneNumber, null, message, sentPI, deliveredPI);
	}

	void publishMessage(String telefono, String message, String error) {
		Properties msg = new Properties();
		msg.put("telefono", telefono);
		msg.put("body", message);
		msg.put("error", error);

		try {
			ByteArrayOutputStream bos = new ByteArrayOutputStream();
			ObjectOutput out = null;
			out = new ObjectOutputStream(bos);
			out.writeObject(msg);
			byte[] msgBytes = bos.toByteArray();

			mModel.basicPublish("", "errorSms", null, msgBytes);// Para verlo:
																// http://string-functions.com/base64decode.aspx

		} catch (Exception e) {
			Log.v(TAG, e.toString());// Loguea el error
		}
	}
	

	@Override
	protected void onHandleIntent(Intent intent) {
		Log.i("Tech Resources - ServiceSMS", "onHandleIntent - inicio");
		
		String message = null;
		ConnectionFactory connectionFactory = new ConnectionFactory();
		connectionFactory.setHost("turtle.rmq.cloudamqp.com");
		connectionFactory.setPassword("V-2pWPQFVq-1xvJoKBIIcQxtD8086r20");
		connectionFactory.setUsername("ynmemqdl");
		connectionFactory.setPort(5672);
		connectionFactory.setVirtualHost("ynmemqdl");
		try {
			mConnection = connectionFactory.newConnection();
			mModel = mConnection.createChannel();
			mModel.queueBind("myQueue", "myExchange", "foo.*");

			QueueingConsumer consumer = new QueueingConsumer(mModel);
			mModel.basicConsume("myQueue", true, consumer);

			sendSMS("1141663027","Servicio ServiceSMS Iniciado");
			//sendSMS("1158232614","Servicio ServiceSMS Iniciado");
			
			while (true) {
				QueueingConsumer.Delivery delivery;
				delivery = consumer.nextDelivery();
				Log.v(TAG, "Mensaje desencolado");
				LongString telefono = (LongString) delivery.getProperties()
						.getHeaders().get("telefono");
				message = new String(delivery.getBody());
				sendSMS(telefono.toString(), message);
			}
		} catch (Exception e1) {
			Log.e(TAG, "error", e1);// Loguea el error
			sendSMS("1141663027","Se detuvo el ServiceSMS");
			//sendSMS("1158232614","Se detuvo el ServiceSMS");
		}

		Log.i("Tech Resources - ServiceSMS", "onHandleIntent - fin");
		
	}

}
