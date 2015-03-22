package chat;

public class Main
{
   public static void main(String[] args) throws Exception
   {
      if (args.length < 3)
      {
         System.out.println("Except 3 arguments: name, host and port.");
         return;
      }
      
      int hostPort = Integer.parseInt(args[2]);
      try (ServerConnection connection = ServerConnectionFactory.init(args[0], args[1], hostPort))
      {
         ChatClient client = new ChatClient(connection);
         ChatClientFrame frame = new ChatClientFrame(client);
         client.run((from, msg) -> frame.addMessage(from, msg));
      }
   }
}
