package com.janardanrimal.jpt;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;

public class MainPane extends JPanel {
   private JLabel Label1;
   private static JTextArea DataLog1;
   private JButton ResetLog;

   public MainPane() {
      this.setSize(334, 334);
      this.initComponents();
   }

   private void initComponents() {
      this.Label1 = new JLabel();
      this.Label1.setText("The main content resides here.");
      this.add(this.Label1);
      DataLog1 = new JTextArea();
      DataLog1.setSize(500, 500);
      DataLog1.setRows(30);
      DataLog1.setColumns(40);
      this.add(DataLog1);
      this.ResetLog = new JButton("Reset");
      this.ResetLog.addActionListener(new ActionListener() {
         public void actionPerformed(ActionEvent evt) {
            MainPane.this.butt_loginActionPerformed(evt);
         }
      });
      this.add(this.ResetLog);
   }

   public static void updateLog(String message) {
      DataLog1.append(message);
   }

   private void butt_loginActionPerformed(ActionEvent evt) {
      try {
         DataLog1.setText("");
      } catch (Exception var3) {
         JOptionPane.showMessageDialog((Component)null, var3);
      }

   }
}
