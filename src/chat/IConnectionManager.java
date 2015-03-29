package chat;

public interface IConnectionManager
{
   public boolean loginAttempt(String from);
   public void logOff();
   public void onTextTyped(String text);
}
