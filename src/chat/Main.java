package chat;

public class Main
{
   public static void main(String[] args) throws Exception
   {
      if (args.length < 2)
      {
         System.out.println("Except 2 arguments: host and port.");
         return;
      }

      int hostPort = Integer.parseInt(args[1]);
      ConnectionManager connectionHandler = new ConnectionManager(args[0], hostPort);
      new MainFrame(connectionHandler);
   }
}
