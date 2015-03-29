package chat;

import java.io.IOException;

public class ConnectionManager implements IConnectionManager
{
   private ServerConnection m_connection = null;
   private final String m_host;
   private final int m_port;
   
   ConnectionManager(String host, int port)
   {
      m_host = host;
      m_port = port;
   }
   
   void close()
   {
      logOff();
   }
   
   public void listen(ServerConnection.MessageHandler handler)
   {
      new Thread()
      {
         @Override
         public void run()
         {
            while (null != m_connection)
            {
               try
               {
                  m_connection.nonBlockingRead(handler);
               }
               catch (IOException e)
               {
                  logOff();
               }
            }
         }
      }.start();
   }

   @Override
   synchronized public boolean loginAttempt(String from)
   {
      m_connection = ServerConnectionFactory.init(from, m_host, m_port);
      return null != m_connection;
   }
   
   @Override
   synchronized public void logOff()
   {
      if (null == m_connection)
         return;
         
      try { m_connection.close(); }
      catch (Exception e) { e.printStackTrace(); }
      m_connection = null;
   }

   @Override
   synchronized public void onTextTyped(String text)
   {
      if (null != m_connection)
      {
         m_connection.tellAll(text);
      }
   }
}
