package com.janardanrimal.jpt;

import gnu.io.CommPortIdentifier;
import gnu.io.PortInUseException;
import gnu.io.SerialPort;
import gnu.io.UnsupportedCommOperationException;
import java.awt.Component;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Enumeration;
import javax.swing.JOptionPane;

public class ReadWriteData {
   static Enumeration portList;
   static CommPortIdentifier portId;
   static SerialPort serialPort;
   static String messageString = "Hello world";
   static OutputStream outputStream;
   static boolean outputBufferEmptyFlag = false;

   public static void writemain(String cpi, String c) {
      boolean portFound = false;
      String defaultPort = cpi;
      portList = CommPortIdentifier.getPortIdentifiers();

      while(true) {
         do {
            do {
               if (!portList.hasMoreElements()) {
                  if (!portFound) {
                     System.out.println("port " + defaultPort + " not found.");
                  }

                  return;
               }

               portId = (CommPortIdentifier)portList.nextElement();
            } while(portId.getPortType() != 1);
         } while(!portId.getName().equals(defaultPort));

         System.out.println("Found port " + defaultPort);
         portFound = true;

         try {
            serialPort = (SerialPort)portId.open("SimpleWrite", 2000);
         } catch (PortInUseException var10) {
            JOptionPane.showMessageDialog((Component)null, "Port in use.");
            continue;
         }

         try {
            outputStream = serialPort.getOutputStream();
         } catch (IOException var9) {
         }

         try {
            serialPort.setSerialPortParams(9600, 8, 1, 2);
         } catch (UnsupportedCommOperationException var8) {
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
            outputStream.write(c.getBytes());
         } catch (IOException var6) {
         }

         try {
            Thread.sleep(2000L);
         } catch (Exception var5) {
         }

         serialPort.close();
      }
   }
}
