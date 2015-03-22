package chat;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;


/**
 * Handles the connection with the chat server:
 * - Receives messages from other users
 * - Send commands to the server
 */
public class ServerConnection implements AutoCloseable
{
   public interface MessageHandler
   {
      public void onMessage(String from, String message);
      public void onStop();
   }
   
   ServerConnection(Socket socket, String name, BufferedReader input, PrintWriter output)
   {
      m_socket = socket;
      m_name = name;
      m_input = input;
      m_output = output;
      hello();
   }
   
   @Override
   public void close() throws Exception
   {
      System.out.println("Shutting down");
      m_output.println("/shut");
      m_input.close();
      m_output.close();
      m_socket.close();
   }
   
   synchronized private void hello()
   {
      m_output.println("/hello " + m_name);
   }
   
   synchronized public void tell(String user, String text)
   {
      m_output.println("/tell " + user + " " + text);
   }
   
   synchronized public void tellAll(String text)
   {
      m_output.println("/broadcast " + text);
   }
   
   public void nonBlockingRead(MessageHandler handler) throws IOException
   {
      if (m_input.ready())
         blockingRead(handler);
   }
   
   public void blockingRead(MessageHandler handler) throws IOException
   {
      String msg = m_input.readLine();
      if ("/shut" == msg)
      {
         handler.onStop();
      }
      else //if (msg.startsWith("/message"))
      {
         int fstSpace = msg.indexOf(' ');
         int sndSpace = msg.indexOf(' ', fstSpace + 1);
         if (-1 != sndSpace)
         {
            handler.onMessage(msg.substring(fstSpace, sndSpace), msg.substring(sndSpace));
         }
      }
   }
   
   public String getName()
   {
      return m_name;
   }
   
   private final Socket m_socket;
   private final String m_name;
   private final BufferedReader m_input;
   private final PrintWriter m_output;
}
