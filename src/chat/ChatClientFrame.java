package chat;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;


public class ChatClientFrame extends JFrame
{
   ChatClientFrame(ChatClient client)
   {
      super("Chat client");
      m_received.setEditable(false);
      m_received.setLineWrap(true);
      
      JPanel panel = new JPanel();
      GridBagLayout layout = new GridBagLayout();
      panel.setLayout(layout);
      GridBagConstraints c = new GridBagConstraints();
      c.fill = GridBagConstraints.BOTH;
      c.gridwidth = 1;
      
      c.gridy = 0;
      JScrollPane scrollPane = new JScrollPane(m_received, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
      layout.setConstraints(scrollPane, c);
      panel.add(scrollPane);
      
      c.gridy = 1;
      layout.setConstraints(m_toSend, c);
      panel.add(m_toSend);
      
      add(panel);
      
      setVisible(true);
      setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
      addWindowListener(new WindowAdapter() {
         @Override
         public void windowClosing(WindowEvent evt) { client.disconnect(); }
      });
      pack();
      
      m_toSend.addActionListener(e -> {
         String msg = m_toSend.getText();
         m_toSend.setText("");
         if (null != client.getConnection())
            client.getConnection().tellAll(msg);
      });
   }
   
   synchronized void addMessage(String from, String message)
   {
      m_received.append(from + ":\n");
      m_received.append(message + "\n");
   }
   
   private static final long serialVersionUID = 5962184855268202260L;
   private JTextArea m_received = new JTextArea(20, 30);
   private JTextField m_toSend = new JTextField(30);
}
