package com.example.cliente;

import java.io.IOException;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

/**
 * Base class for objects that connect to a RabbitMQ Broker
 */
public abstract class IConnectToRabbitMQ {
      public String mServer;
      public String mExchange;
 
      protected Channel mModel = null;
      protected Connection  mConnection;
 
      protected boolean Running ;
 
      protected  String MyExchangeType ;
 
      /**
       *
       * @param server The server address
       * @param exchange The named exchange
       * @param exchangeType The exchange type name
       */
      public IConnectToRabbitMQ(String server, String exchange, String exchangeType)
      {
          mServer = server;
          mExchange = exchange;
          MyExchangeType = exchangeType;
      }
 
      public void Dispose()
      {
          Running = false;
 
            try {
                if (mConnection!=null)
                    mConnection.close();
                if (mModel != null)
                    mModel.abort();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
 
      }
 
      /**
       * Connect to the broker and create the exchange
       * @return success
       */
      public boolean connectToRabbitMQ()
      {
          if(mModel!= null && mModel.isOpen() )//already declared
              return true;
          try
          {
              ConnectionFactory connectionFactory = new ConnectionFactory();
              connectionFactory.setHost(mServer);
              connectionFactory.setPassword("X42BZqqOcWm33xzpookIFZbuqKdb_aiz" );
              connectionFactory.setUsername("agprifpq");
              connectionFactory.setPort(5672);
              connectionFactory.setVirtualHost("agprifpq");
              mConnection = connectionFactory.newConnection();
              mModel = mConnection.createChannel();
              mModel.exchangeDeclare(mExchange, MyExchangeType, true);
 
              return true;
          }
          catch (Exception e)
          {
       // 	  if (e.getMessage().indexOf("Connection reset by peer") > 0) 
              e.printStackTrace();
              return false;
          }
      }
}