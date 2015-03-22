package chat;

import java.io.IOException;


public class ChatClient
{
   @FunctionalInterface
   public interface MessageHandler
   {
      public void onMessage(String from, String message);
   }
   
   ChatClient(ServerConnection connection)
   {
      m_connection = connection;
      m_connected = true;
   }
   
   public void run(MessageHandler handler) throws IOException
   {
      ServerConnection.MessageHandler connectionHandler =
            new ServerConnection.MessageHandler()
      {
         @Override
         public void onStop() { disconnect(); }
         
         @Override
         public void onMessage(String from, String message)
         {
            handler.onMessage(from, message);
         }
      };
      
      while (m_connected)
      {
         m_connection.nonBlockingRead(connectionHandler);
      }
   }
   
   public void tellAll(String text)
   {
      m_connection.tellAll(text);
   }
   
   public void disconnect()
   {
      m_connected = false;
   }
   
   public ServerConnection getConnection()
   {
      return m_connection;
   }
   
   private boolean m_connected;
   private final ServerConnection m_connection;
}
