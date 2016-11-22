import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;


public class UDPClient implements Runnable  {
	//kaikkee UDP client juttui tanne sit
	
	private final int portti;
	
	public UDPClient (int portti) {
		
		this.portti = portti;
	}
	
	@Override
	
	public void run(){
		try(DatagramSocket clientSocket = new DatagramSocket(portti)){
			byte[] buffer = new byte[50000];
			clientSocket.setSoTimeout(3000);
			while (true){
				DatagramPacket datagramPacket = new DatagramPacket(buffer, 0, buffer.length);
				clientSocket.receive(datagramPacket);
				
				String vastaanotettuViesti = new String(datagramPacket.getData());
			}

		} catch (SocketException e){
			e.printStackTrace();
		} catch (IOException e){
			System.out.println("Timeout, Clienti suljetaan");
			e.printStackTrace();
		}
	}
}
