package com.janardanrimal.jpt;

import gnu.io.CommPortIdentifier;
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.EventQueue;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.util.Enumeration;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.UIManager.LookAndFeelInfo;

public class configureWindow extends JFrame {
   private JComboBox Combo1;
   private JButton Button1;
   CommPortIdentifier portId;
   CommPortIdentifier port;
   Enumeration portList;

   public configureWindow() {
      this.initComponents();
      this.setLocationRelativeTo((Component)null);
      this.FillCombo();
   }

   public void close() {
      WindowEvent WINDOW_CLOSING = new WindowEvent(this, 201);
      Toolkit.getDefaultToolkit().getSystemEventQueue().postEvent(WINDOW_CLOSING);
   }

   private void initComponents() {
      this.Combo1 = new JComboBox();
      this.Button1 = new JButton("");
      this.setLayout(new BorderLayout(20, 20));
      this.setDefaultCloseOperation(3);
      this.setTitle("PreConfigure");
      this.setResizable(false);
      this.setSize(500, 500);
      this.Button1.setText("OK");
      this.add(this.Combo1, "North");
      this.add(this.Button1, "South");
      this.Button1.addActionListener(new ActionListener() {
         public void actionPerformed(ActionEvent evt) {
            configureWindow.this.butt_loginActionPerformed(evt);
         }
      });
   }

   private void butt_loginActionPerformed(ActionEvent evt) {
      try {
         MainClass.portSelected = this.Combo1.getSelectedItem().toString();
         this.setVisible(false);
         MainWindow mainWindow = new MainWindow();
         mainWindow.setExtendedState(6);
         System.out.println("Connecting to.... " + this.Combo1.getSelectedItem().toString());
         mainWindow.pack();
         MainWindow.start();
      } catch (Exception var3) {
         JOptionPane.showMessageDialog((Component)null, var3);
      }

   }

   private void FillCombo() {
      try {
         this.Combo1.removeAllItems();
         this.portId = null;
         this.portList = CommPortIdentifier.getPortIdentifiers();

         while(this.portList.hasMoreElements()) {
            this.portId = (CommPortIdentifier)this.portList.nextElement();
            if (this.portId.getPortType() == 1) {
               this.Combo1.addItem(this.portId.getName());
               this.port = this.portId;
            }
         }
      } catch (Exception var2) {
         JOptionPane.showMessageDialog((Component)null, "Error: Can't Identify CommPorts.");
      }

      this.Combo1.setSelectedIndex(0);
   }

   public static void start() {
      try {
         LookAndFeelInfo[] var0 = UIManager.getInstalledLookAndFeels();
         int var1 = var0.length;

         for(int var2 = 0; var2 < var1; ++var2) {
            LookAndFeelInfo info = var0[var2];
            if ("Nimbus".equals(info.getName())) {
               UIManager.setLookAndFeel(info.getClassName());
               break;
            }
         }
      } catch (ClassNotFoundException var4) {
         Logger.getLogger(configureWindow.class.getName()).log(Level.SEVERE, (String)null, var4);
      } catch (InstantiationException var5) {
         Logger.getLogger(configureWindow.class.getName()).log(Level.SEVERE, (String)null, var5);
      } catch (IllegalAccessException var6) {
         Logger.getLogger(configureWindow.class.getName()).log(Level.SEVERE, (String)null, var6);
      } catch (UnsupportedLookAndFeelException var7) {
         Logger.getLogger(configureWindow.class.getName()).log(Level.SEVERE, (String)null, var7);
      }

      EventQueue.invokeLater(new Runnable() {
         public void run() {
            (new configureWindow()).setVisible(true);
         }
      });
   }
}
