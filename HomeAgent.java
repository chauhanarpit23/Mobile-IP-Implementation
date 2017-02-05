import java.io.*;
import java.net.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class HomeAgent {
	public static void main(String args[]) throws Exception {
		if (args.length == 0) {
			System.out.print("Usage HomeAgent port ForeignAgent1 ForeignAgentPort1 ForeignAgent2 ForeignAgentPort2 DataSource");
			System.exit(0);
		}
		int homeagentport = Integer.parseInt(args[0]);
		int foreignagent1port = Integer.parseInt(args[2]);
		int foreignagent2port = Integer.parseInt(args[4]);
		;
		int foreignagent = foreignagent1port;
		DatagramSocket HomeAgentSocket = new DatagramSocket(homeagentport);
		InetAddress foreign1Address = InetAddress.getByName(args[1]);
		InetAddress foreign2Address = InetAddress.getByName(args[3]);
		InetAddress datasourceAddress = InetAddress.getByName(args[5]);
		InetAddress foreignAddress = InetAddress.getByName(args[1]);
		byte[] receiveData = new byte[1024];
		byte[] sendData = new byte[1024];
		DatagramPacket reciver = new DatagramPacket(receiveData,
				receiveData.length);
		while (true) {

			HomeAgentSocket.receive(reciver);
			byte[] recieve_data = reciver.getData();
			String s = new String(recieve_data, 0, reciver.getLength());
			System.out.println("Sequence Number:" + reciver.getAddress().getHostName() + datasourceAddress.getHostName());
			if (reciver.getAddress().getHostName()
					.contains(datasourceAddress.getHostName())) {
				System.out.println("Sequence Number: "+s+" Time: "+new SimpleDateFormat("HH:mm:ss").format(Calendar.getInstance().getTime())+ " Forwarded to ="+ foreignAddress.getHostAddress()+"/"+foreignagent);
				
				DatagramPacket sendPacket = new DatagramPacket(s.getBytes(),
						s.getBytes().length, foreignAddress, foreignagent);
				HomeAgentSocket.send(sendPacket);

			} else {
				int port = Integer.parseInt(s);
				if (port == foreignagent1port) {
					foreignAddress = foreign1Address;
					foreignagent = foreignagent1port;
				} else {
					foreignAddress = foreign2Address;
					foreignagent = foreignagent2port;
				}
				System.out.println("Registration packet received.  "+ " Time = "+new SimpleDateFormat("HH:mm:ss").format(Calendar.getInstance().getTime())+" Changing care-of address to "+reciver.getAddress().getHostAddress() + " : " + foreignAddress.getHostAddress() + "/" + foreignagent);

			}

		}

	}
}
