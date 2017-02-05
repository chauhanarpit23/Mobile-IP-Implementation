import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.text.StyledEditorKit.BoldAction;

public class MobileNode extends TimerTask {
	static DatagramSocket HomeAgentSocket;
	static int mobileNodePort;
	static InetAddress mobileAddress;
	static InetAddress homeAgentAddress;
	static int homeAgentPort;

	static InetAddress faAddress1;
	static InetAddress faAddress2;
	static InetAddress faAddress;
	static int faPort;
	static int faPort1;
	static int faPort2;

	public void run() {
		if (faPort == faPort1) {
			faAddress = faAddress2;
			faPort = faPort2;
		} else {
			faAddress = faAddress1;
			faPort = faPort1;
		}
		byte[] senddata = Integer.toString(faPort).getBytes();
		DatagramPacket sendPacket = new DatagramPacket(senddata,
				senddata.length, homeAgentAddress, homeAgentPort);
		try {
			HomeAgentSocket.send(sendPacket);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public static void recsend() throws IOException

	{
				byte[] receiveData = new byte[1024];
		byte[] sendData = new byte[1024];
		DatagramPacket reciver = new DatagramPacket(receiveData,
				receiveData.length);
		faAddress = faAddress1;
		faPort = faPort1;
		boolean exce = false;
		while (true) {
			HomeAgentSocket.receive(reciver);
			if (!exce) {
				Timer timer = new Timer();
				timer.schedule(new MobileNode(), 5000, 5000);
				exce = true;
			}
			byte[] recieve_data = reciver.getData();
			String s = new String(recieve_data, 0, reciver.getLength());
			System.out.println("Sequence Number = "+s+" Time = "+new SimpleDateFormat("HH:mm:ss").format(Calendar.getInstance().getTime())+ " Forwarded to "+ reciver.getAddress().getHostAddress()+"/"+reciver.getPort()+"Accepted");
		}
	}

	public static void main(String args[]) throws Exception {
		if(args.length==0)
		 {
			 System.out.print("Usage MobileNode port ForeignAgent1 ForeignAgentPort1 ForeignAgent2 ForeignAgentPort2 HomeAgent HomeAgentPort");
			 System.exit(0);
		 	}

		mobileNodePort = Integer.parseInt(args[0]);

		HomeAgentSocket = new DatagramSocket(mobileNodePort);
		
		faAddress1 = InetAddress.getByName(args[1]);
		homeAgentAddress = InetAddress.getByName(args[5]);
		faAddress2 = InetAddress.getByName(args[3]);
		faPort1 = Integer.parseInt(args[2]);
		faPort2 = Integer.parseInt(args[4]);
		homeAgentPort = Integer.parseInt(args[6]);

		MobileNode.recsend();
	}
}
