import java.io.*;
import java.net.*;

public class UDPClient implements Runnable {
	// kaikkee UDP client juttui tanne sit
	public static void main (String[] arga)throws Exception{
		
		DatagramSocket ds = new DatagramSocket();
		
		byte[] b = new byte[40000];
		
		InetAddress ia = InetAddress.getLocalHost();
		DatagramPacket dp =  new DatagramPacket(b,b.length,ia,3126);
		ds.send(dp);
		
		byte[] b1 = new byte[50000];
		
		DatagramPacket dp1 = new DatagramPacket(b1, b1.length);
		ds.receive(dp1);
		
		String str = new String(dp1.getData());
		System.out.println("results" + str);
	}
}