package chat;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class LoginPanel extends JPanel implements ActionListener
{
   private static final long serialVersionUID = 7372280796733817482L;
   private JTextField m_name = new JTextField(20);
   private JButton m_login = new JButton("Login");
   private JButton m_logoff = new JButton("Logoff");
   private IConnectionManager m_handler;
   
   public LoginPanel(IConnectionManager handler)
   {
      m_handler = handler;
      initLayout();
      m_login.addActionListener(this);
      m_logoff.addActionListener(this);
   }
   
   public void notifyLoggedIn()
   {
      m_login.setVisible(false);
      m_logoff.setVisible(true);
   }
   
   public void notifyLoggedOff()
   {
      m_login.setVisible(true);
      m_logoff.setVisible(false);
   }
   
   @Override
   public void actionPerformed(ActionEvent e)
   {
      if (e.getSource() == m_login)
      {
         m_handler.loginAttempt(m_name.getText());
      }
      else if (e.getSource() == m_logoff)
      {
         m_handler.logOff();
      }
   }
   
   private void initLayout()
   {
      setLayout(new GridBagLayout());
      GridBagConstraints c = new GridBagConstraints();
      c.fill = GridBagConstraints.BOTH;
      c.gridwidth = 1;
      c.weightx = 1;
      c.insets = new Insets(1, 1, 1, 1);
      c.gridy = 0;
      
      c.gridwidth = 1;
      c.gridx = 0;
      add(new JLabel("User name:"), c);
      
      c.gridx = 1;
      add(m_name, c);
      
      c.gridx = 2;
      add(m_login, c);
      add(m_logoff, c);
   }
}
