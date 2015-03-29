package chat;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.UIManager;


public class MainFrame extends JFrame implements IConnectionManager, ServerConnection.MessageHandler
{
   private static final long serialVersionUID = 6694935773416330426L;
   private ConnectionManager m_connectionMananger;
   private LoginPanel m_loginPanel;
   private ChatPanel m_chatPanel;
   
   MainFrame(ConnectionManager connectionHandler) throws Exception
   {
      super("Chat client");
      m_connectionMananger = connectionHandler;
      m_loginPanel = new LoginPanel(this);
      m_chatPanel = new ChatPanel(this);
      m_chatPanel.disableChat(true);
      
      initLayout();
      UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
      pack();
      setVisible(true);
      
      setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
      addWindowListener(new WindowAdapter() {
         @Override
         public void windowClosing(WindowEvent evt) { close(); }
      });
   }
   
   public void close()
   {
      m_connectionMananger.close();
      System.exit(0);
   }
   
   @Override
   public boolean loginAttempt(String from)
   {
      boolean success = m_connectionMananger.loginAttempt(from);
      if (success)
      {
         m_loginPanel.notifyLoggedIn();
         m_chatPanel.disableChat(false);
         m_connectionMananger.listen(this);
      }
      return success;
   }

   @Override
   public void logOff()
   {
      m_loginPanel.notifyLoggedOff();
      m_chatPanel.disableChat(true);
      m_connectionMananger.logOff();
   }

   @Override
   public void onTextTyped(String text)
   {
      m_connectionMananger.onTextTyped(text);
   }
   
   @Override
   public void onMessage(String from, String message)
   {
      m_chatPanel.addMessage(from, message);
   }

   @Override
   public void onStop()
   {
      logOff();
   }
   
   private void initLayout()
   {
      JPanel panel = new JPanel();
      panel.setLayout(new GridBagLayout());
      GridBagConstraints c = new GridBagConstraints();
      c.fill = GridBagConstraints.BOTH;
      c.gridwidth = 1;
      c.weightx = 1;
      c.insets = new Insets(1, 1, 1, 1);
      
      c.gridy = 0;
      panel.add(m_loginPanel, c);
      
      c.gridy = 1;
      panel.add(m_chatPanel, c);
      
      add(panel);
   }
}
