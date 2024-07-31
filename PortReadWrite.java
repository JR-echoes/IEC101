package com.janardanrimal.jpt;

import gnu.io.CommPort;
import gnu.io.CommPortIdentifier;
import gnu.io.SerialPort;
import gnu.io.SerialPortEvent;
import gnu.io.SerialPortEventListener;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class PortReadWrite {
   void connect(String portName) throws Exception {
      CommPortIdentifier portIdentifier = CommPortIdentifier.getPortIdentifier(portName);
      if (portIdentifier.isCurrentlyOwned()) {
         System.out.println("Error: Port is currently in use");
      } else {
         CommPort commPort = portIdentifier.open(this.getClass().getName(), 2000);
         if (commPort instanceof SerialPort) {
            SerialPort serialPort = (SerialPort)commPort;
            serialPort.setSerialPortParams(9600, 8, 1, 2);
            InputStream in = serialPort.getInputStream();
            OutputStream out = serialPort.getOutputStream();
            new PortReadWrite.SerialWriter(out);
            serialPort.addEventListener(new PortReadWrite.SerialReader(in));
            serialPort.notifyOnDataAvailable(true);
         } else {
            System.out.println("Error: Only serial ports are handled by this example.");
         }
      }

   }

   public static class SerialWriter implements Runnable {
      static OutputStream out;

      public SerialWriter(OutputStream out) {
         PortReadWrite.SerialWriter.out = out;
      }

      public void run() {
         try {
            boolean var1 = false;

            int c;
            while((c = System.in.read()) > 0) {
               out.write(c);
            }
         } catch (IOException var2) {
            var2.printStackTrace();
            System.exit(-1);
         }

      }

      public static void writeData(byte[] c) {
         try {
            out.write(c);
            String inter = "";
            String sent = "";

            for(int i = 0; i < c.length; ++i) {
               inter = Integer.toHexString(c[i]);
               sent = sent + inter;
               System.out.println(inter);
            }

            MainPane.updateLog("TX: " + sent + "\n");
         } catch (Exception var4) {
            System.out.println("Error sending message");
         }

      }
   }

   public static class SerialReader implements SerialPortEventListener {
      private InputStream in;
      private byte[] buffer = new byte[1024];

      public SerialReader(InputStream in) {
         this.in = in;
      }

      public void serialEvent(SerialPortEvent arg0) {
         String received = "";

         try {
            int data;
            int len;
            String inter;
            for(len = 0; (data = this.in.read()) > -1 && data != 10; this.buffer[len++] = (byte)data) {
               inter = Integer.toHexString(data);
               System.out.println(inter);
               if (inter.equals("16")) {
                  received = received + inter + "\n";
               } else {
                  received = received + inter;
               }
            }

            System.out.println(received);
            inter = new String(this.buffer, 0, len);
            MainPane.updateLog(new String("RX: " + received) + "\t\t" + inter + "\n");
            System.out.println(inter);
         } catch (IOException var6) {
            var6.printStackTrace();
            System.exit(-1);
         }

      }
   }
}
