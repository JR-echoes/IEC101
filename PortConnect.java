package com.janardanrimal.jpt;

import gnu.io.CommPortIdentifier;
import gnu.io.PortInUseException;
import gnu.io.SerialPort;
import gnu.io.SerialPortEvent;
import gnu.io.SerialPortEventListener;
import gnu.io.UnsupportedCommOperationException;
import java.awt.Component;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Enumeration;
import java.util.TooManyListenersException;
import javax.swing.JOptionPane;

public class PortConnect implements Runnable, SerialPortEventListener {
   boolean portFound = false;
   static CommPortIdentifier portId;
   static CommPortIdentifier currentPort;
   Enumeration portList;
   int count = 0;
   String receiveBuffer = "";
   boolean readToBuffer = false;
   boolean writeToLog = false;
   byte[] readBuffer;
   int numBytes;
   InputStream inputStream;
   static OutputStream outputStream;
   static SerialPort serialPort;
   BufferedReader bufReader;
   Thread readThread;
   private String port;
   boolean outputBufferEmptyFlag = false;

   public PortConnect() {
   }

   public PortConnect(String portName) {
      this.port = portName;
      this.connect();
   }

   public void connect() {
      this.portList = CommPortIdentifier.getPortIdentifiers();

      while(this.portList.hasMoreElements()) {
         portId = (CommPortIdentifier)this.portList.nextElement();
         if (portId.getPortType() == 1 && portId.getName().toString().equals(this.port)) {
            currentPort = portId;
            System.out.println("Connecting to " + portId.getName());
            this.portFound = true;

            try {
               serialPort = (SerialPort)portId.open("SimpleReadApp", 2000);
               System.out.println("Serial Port Created");
            } catch (PortInUseException var5) {
               serialPort.close();
               JOptionPane.showMessageDialog((Component)null, "Port was in use");
            }

            try {
               this.inputStream = serialPort.getInputStream();
            } catch (IOException var4) {
               System.out.println(var4);
            }

            try {
               serialPort.addEventListener(this);
            } catch (TooManyListenersException var3) {
               System.out.println(var3);
            }

            serialPort.notifyOnDataAvailable(true);

            try {
               serialPort.setSerialPortParams(9600, 8, 1, 2);
            } catch (UnsupportedCommOperationException var2) {
               System.out.println(var2);
            }
         }
      }

      this.readThread = new Thread(this);
      this.readThread.start();
   }

   public void run() {
      try {
         Thread.sleep(20000L);
      } catch (InterruptedException var2) {
         JOptionPane.showMessageDialog((Component)null, var2);
      }

   }

   public static void writeData(String cpi, byte[] c) {
      boolean portFound = false;

      try {
         System.out.println(portId.getName());
         serialPort = (SerialPort)currentPort.open("SimpleWrite", 2000);
         System.out.println("Hello");
      } catch (PortInUseException var9) {
      }

      try {
         outputStream = serialPort.getOutputStream();
      } catch (IOException var8) {
      }

      try {
         serialPort.notifyOnOutputEmpty(true);
      } catch (Exception var7) {
         System.out.println("Error setting event notification");
         System.out.println(var7.toString());
         System.exit(-1);
      }

      System.out.println("Writing \"" + c + "\" to " + serialPort.getName());

      try {
         System.out.println("Before print");
         outputStream.write(c);
         MainPane.updateLog(new String("Sent: ") + c.toString() + "\n");
         System.out.println("After print");
      } catch (IOException var6) {
      }

      try {
         Thread.sleep(2000L);
      } catch (Exception var5) {
      }

   }

   public void serialEvent(SerialPortEvent event) {
      switch(event.getEventType()) {
      case 1:
         int bufferBytes = 1;
         int byteIndex = 0;
         this.readBuffer = new byte[bufferBytes];

         try {
            while(this.inputStream.available() > 0) {
               this.numBytes = this.inputStream.read(this.readBuffer);
               String readString = new String(this.readBuffer);
               System.out.println(readString);
               this.readToBuffer = true;
               if (readString.equals("N") && this.readToBuffer) {
                  this.writeToLog = true;
               }

               if (this.readToBuffer) {
                  this.receiveBuffer = this.receiveBuffer + readString;
                  ++byteIndex;
                  if (byteIndex == 4) {
                     System.out.println(this.receiveBuffer);
                     if (this.receiveBuffer.charAt(0) == this.receiveBuffer.charAt(3)) {
                        System.out.println("The frame is found.");
                        byteIndex = 0;
                     }
                  }
               }
            }

            System.out.println(new String(this.readBuffer));
            if (this.writeToLog) {
               MainPane.updateLog(new String("Received: " + this.receiveBuffer) + "\n");
               this.readToBuffer = false;
               this.writeToLog = false;
               this.receiveBuffer = "";
            }
         } catch (IOException var5) {
            System.out.println(var5);
         }
         break;
      case 2:
      case 3:
      case 4:
      case 5:
      case 6:
      case 7:
      case 8:
      case 9:
      case 10:
         System.out.println("Output Buffer Empty");
      }

   }

   public void insertToDatabase(int str) {
      int abc = str;
      System.out.println(this.count);
      System.out.println(str);

      try {
         Class.forName("com.mysql.jdbc.Driver");
         Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/drinking_water", "root", "");
         Statement stmt = con.createStatement();
         stmt.execute("Update namaste SET P1sensor=" + abc + " WHERE count=" + this.count);
         ++this.count;
         if (this.count == 20) {
            this.count = 0;
         }

         stmt.close();
         con.close();
      } catch (SQLException | ClassNotFoundException var6) {
         System.out.println(var6);
      }

   }
}
