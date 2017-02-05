import java.io.*;
import java.net.*;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class DataSource 
{
	   public static void main(String args[]) throws Exception
	   {
		   if(args.length==0)
			 {
				 System.out.print("Usage Datasource HomeAgent HomeAgentPort");
				 System.exit(0);
			 	}
		 int homeagentport = Integer.parseInt(args[1]);
	  
	     for(int i=1;i<101;i++)
	     { 
	      DatagramSocket DataSourceSocket = new DatagramSocket();
	      InetAddress homehost = InetAddress.getByName(args[0]);
	      byte[] sendData = new byte[1024];
	      byte[] receiveData = new byte[1024];
	      sendData = Integer.toString(i).getBytes();
	       
	      DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, homehost, homeagentport);
	      
	      DataSourceSocket.send(sendPacket);
	      System.out.println("Sequence Number: "+i+" Time: "+new SimpleDateFormat("HH:mm:ss").format(Calendar.getInstance().getTime())+ " Dest ="+ homehost.getHostAddress()+"/"+homeagentport);
	      Thread.sleep(1000);
	      
	      DataSourceSocket.close();
	     }
	}
	}
