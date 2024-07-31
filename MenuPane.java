package com.janardanrimal.jpt;

import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class MenuPane extends JPanel {
   private JLabel Label1;
   private JPanel Panel1;
   private JTextField comName;
   private JTextField linkAddress;
   private JButton masterButt1;
   private JButton masterButt2;
   private JButton ackButt;

   public MenuPane() {
      this.initComponents();
   }

   private void initComponents() {
      this.Panel1 = new JPanel();
      this.Label1 = new JLabel();
      this.masterButt1 = new JButton("Master");
      this.masterButt2 = new JButton("Master2");
      this.ackButt = new JButton("Acknowledge");
      this.comName = new JTextField();
      this.linkAddress = new JTextField("771");
      this.comName.setText(MainClass.portSelected);
      this.Label1.setText("COM ");
      this.Label1.setLayout(new FlowLayout());
      this.add(this.Label1);
      this.add(this.comName);
      this.add(this.linkAddress);
      this.add(this.masterButt1);
      this.masterButt1.addActionListener(new ActionListener() {
         public void actionPerformed(ActionEvent evt) {
            MenuPane.this.butt_loginActionPerformed(evt);
         }
      });
      this.add(this.masterButt2);
      this.masterButt2.addActionListener(new ActionListener() {
         public void actionPerformed(ActionEvent evt) {
            MenuPane.this.butt2_loginActionPerformed(evt);
         }
      });
      this.add(this.ackButt);
      this.ackButt.addActionListener(new ActionListener() {
         public void actionPerformed(ActionEvent evt) {
            MenuPane.this.ackbutt_ActionPerformed(evt);
         }
      });
   }

   private void butt_loginActionPerformed(ActionEvent evt) {
      try {
         byte[] data = new byte[]{16, -55, 3, 3, -49, 22};
         PortReadWrite.SerialWriter.writeData(data);
      } catch (Exception var3) {
         JOptionPane.showMessageDialog((Component)null, var3);
      }

   }

   private void butt2_loginActionPerformed(ActionEvent evt) {
      try {
         byte[] data = new byte[]{16, -64, 3, 3, -58, 22};
         PortReadWrite.SerialWriter.writeData(data);
      } catch (Exception var3) {
         JOptionPane.showMessageDialog((Component)null, var3);
      }

   }

   private void ackbutt_ActionPerformed(ActionEvent evt) {
      try {
         byte[] data = new byte[]{-27};
         PortReadWrite.SerialWriter.writeData(data);
      } catch (Exception var3) {
         JOptionPane.showMessageDialog((Component)null, var3);
      }

   }
}
