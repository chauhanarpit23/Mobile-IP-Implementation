import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.text.SimpleDateFormat;
import java.util.Calendar;


public class ForeignAgent {
	 public static void main(String args[]) throws Exception 
	    {
		 if(args.length==0)
		 {
			 System.out.print("Usage ForeignAgent port MobileNode MobileNodePort");
			 System.exit(0);
		 	}
		  int foreignAgentPort = Integer.parseInt(args[0]);
		  int mobileNodePort  = Integer.parseInt(args[2]);
		  
	      DatagramSocket HomeAgentSocket = new DatagramSocket(foreignAgentPort);
	      InetAddress mobileAddress = InetAddress.getByName(args[1]);

	      byte[] receiveData = new byte[1024]; 
	      byte[] sendData  = new byte[1024];
	      DatagramPacket reciver = new DatagramPacket(receiveData, receiveData.length);
	      while(true) 
	        {

	    	  HomeAgentSocket.receive(reciver);
	    	  byte [] recieve_data = reciver.getData();
	    	  String s = new String(recieve_data,0,reciver.getLength());
	    	  System.out.println("Sequence Number = "+s+" Time = "+new SimpleDateFormat("HH:mm:ss").format(Calendar.getInstance().getTime())+ " Forwarded to "+ mobileAddress.getHostAddress()+"/"+mobileNodePort);
	    	  DatagramPacket sendPacket = new DatagramPacket(s.getBytes(), s.getBytes().length, mobileAddress, mobileNodePort);
		      HomeAgentSocket.send(sendPacket);
	        }
	      
	    } 
}
