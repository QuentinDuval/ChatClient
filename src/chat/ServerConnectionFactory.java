package chat;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ServerConnectionFactory
{
   public static ServerConnection init(String name, String host, int port)
   {
      try
      {
         Socket socket = new Socket(host, port);
         try
         {
            BufferedReader input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter output = new PrintWriter(socket.getOutputStream(), true);
            return new ServerConnection(socket, name, input, output);   
         }
         catch (IOException e)
         {
            socket.close();
            e.printStackTrace();
            return null;
         }
      }
      catch (Exception e)
      {
         e.printStackTrace();
         return null;
      }
   }
}
