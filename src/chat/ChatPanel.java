package chat;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class ChatPanel extends JPanel implements ActionListener
{
   private static final long serialVersionUID = -6034752164463994298L;
   private JTextArea m_received = new JTextArea(20, 30);
   private JTextField m_toSend = new JTextField(30);
   private IConnectionManager m_connectionManager;
   
   ChatPanel(IConnectionManager listener)
   {
      m_connectionManager = listener;
      m_received.setEditable(false);
      m_received.setLineWrap(true);
      m_toSend.addActionListener(this);
      initLayout();
   }
   
   public void disableChat(boolean disabled)
   {
      m_received.setEnabled(!disabled);
      m_toSend.setEnabled(!disabled);
   }
   
   void addMessage(String from, String message)
   {
      m_received.append("[" + from + "]:\n");
      m_received.append(message + "\n");
   }
   
   void notifyConnection(String name)
   {
      m_received.append("[" + name + "] connected.\n");
   }
   
   void notifyDisconnection(String name)
   {
      m_received.append("[" + name + "] disconnected.\n");
   }
   
   @Override
   public void actionPerformed(ActionEvent e)
   {
      if (e.getSource() == m_toSend)
      {
         m_connectionManager.onTextTyped(m_toSend.getText());
         m_toSend.setText("");
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
      JScrollPane scrollPane = new JScrollPane(m_received, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
      add(scrollPane, c);
      
      c.gridy = 1;
      add(m_toSend, c);
   }
}
