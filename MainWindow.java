package com.janardanrimal.jpt;

import java.awt.Color;
import java.awt.Component;
import java.awt.EventQueue;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JSplitPane;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.UIManager.LookAndFeelInfo;

public class MainWindow extends JFrame {
   private MenuPane menu;
   private MainPane main;
   private JSplitPane bigPanel;

   public MainWindow() {
      this.initComponents();
      this.setLocationRelativeTo((Component)null);
   }

   private void initComponents() {
      this.bigPanel = new JSplitPane();
      this.main = new MainPane();
      this.menu = new MenuPane();
      this.setDefaultCloseOperation(3);
      this.setTitle("Janardan's Protocol Tester(JPT)");
      this.setResizable(true);
      this.setSize(1000, 1000);
      this.main.setBorder(BorderFactory.createLineBorder(Color.gray));
      this.main.setSize(668, 334);
      this.menu.setBorder(BorderFactory.createLineBorder(Color.gray));
      this.menu.setSize(333, 333);
      this.menu.setBackground(Color.white);
      this.bigPanel.setSize(1000, 1000);
      this.bigPanel.setDividerSize(0);
      this.bigPanel.setDividerLocation(668);
      this.bigPanel.setOrientation(1);
      this.bigPanel.setLeftComponent(this.main);
      this.bigPanel.setRightComponent(this.menu);
      this.add(this.bigPanel);
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
         Logger.getLogger(MainWindow.class.getName()).log(Level.SEVERE, (String)null, var4);
      } catch (InstantiationException var5) {
         Logger.getLogger(MainWindow.class.getName()).log(Level.SEVERE, (String)null, var5);
      } catch (IllegalAccessException var6) {
         Logger.getLogger(MainWindow.class.getName()).log(Level.SEVERE, (String)null, var6);
      } catch (UnsupportedLookAndFeelException var7) {
         Logger.getLogger(MainWindow.class.getName()).log(Level.SEVERE, (String)null, var7);
      }

      EventQueue.invokeLater(new Runnable() {
         public void run() {
            (new MainWindow()).setVisible(true);

            try {
               (new PortReadWrite()).connect(MainClass.portSelected);
            } catch (Exception var2) {
               var2.printStackTrace();
               JOptionPane.showMessageDialog((Component)null, "Error Opening the window!!");
            }

         }
      });
   }
}
