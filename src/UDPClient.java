import java.awt.Point;
import java.io.*;
import java.net.*;
import java.nio.charset.StandardCharsets;

public class UDPClient{
	
	
	public static void main (String[] arga)throws Exception{
			
			// luodaan datagram soketti
			
			DatagramSocket ds = new DatagramSocket();
			
			//yhdistetaan serveriin
			
			byte[] lahetysData = new byte[256];
			byte[] vastaanotettuData = new byte[256];
			
			String viesti = "3126";
			lahetysData = viesti.getBytes();
			
			InetAddress ia = InetAddress.getLocalHost();
			DatagramPacket lahtevaPaketti =  new DatagramPacket(lahetysData,lahetysData.length,ia,3126);
			ds.send(lahtevaPaketti);
			
			try{
			// tehdaan output ja input streamit ja aukaistaan soketti
			
				int portti = WorkDistributor.PORT;
				Socket soketti = new Socket(ia,portti);
				System.out.println("yhteys tehty...");  // TASSA JOTAIN VIKAA, EN TIEDA MIKSI EI HALUA YHDISTYA
				
				InputStream	is = soketti.getInputStream();
				OutputStream os = soketti.getOutputStream();
				
				ObjectOutputStream output = new ObjectOutputStream(os);
				ObjectInputStream input = new ObjectInputStream(is);
				
				//ALLA OLEVA LOOPPI PRUJUIST, EN TIEDA TARVIIKO??
				for (int i=0; i<10; i++) {
					Point p = new Point((int)(20*Math.random()), (int)(20*Math.random()));
					output.writeObject(p); output.flush();
					System.out.println("Writing done ... sleeping..");
					Thread.sleep(2000);
					Point p1 = (Point) input.readObject();
					System.out.println("Put " + p.toString());
					System.out.println("Got " + p1.toString());
					} // for
					output.close(); output.close(); soketti.close();
			}catch(IOException e){System.out.println(e);}
				
			
			// KAIKKI TÄS ALHAAL OLLU KOKEILUU			
			/*DatagramPacket vastaanotettuPaketti = new DatagramPacket(vastaanotettuData, vastaanotettuData.length);
		      ds.receive(vastaanotettuPaketti);
		      
		      new String(vastaanotettuPaketti.getData(),0,vastaanotettuPaketti.getLength(), StandardCharsets.UTF_8);
		      String modifiedSentence = new String(vastaanotettuPaketti.getData());
		      System.out.println("Serveriltä:" + modifiedSentence);
		      ds.close();
		      
			DatagramPacket vastaanotettuPaketti = new DatagramPacket(vastaanotettuData, vastaanotettuData.length);
		      ds.receive(vastaanotettuPaketti);
		      
		      new String(vastaanotettuPaketti.getData(),0,vastaanotettuPaketti.getLength(), StandardCharsets.UTF_8);
		      String modifiedSentence = new String(vastaanotettuPaketti.getData());
		      System.out.println("Serveriltä:" + modifiedSentence);
		      ds.close();
		      */
			
			// tehdaan uusi packet 
	}
}