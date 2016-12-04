import java.io.*;
import java.net.*;

public class UDPClient {

		static Socket soketti;
		static ServerSocket ss;
		static int portti;
		static byte[] lahetysData;
		static String porttinumero;
		static ObjectOutputStream output;
		static ObjectInputStream input;
		static int tiedostovirta = 0;
		static Summaaja[] summatut;
		static Thread[] threads;
		static DatagramPacket lahtevaPaketti;
		static DatagramSocket ds;
		
		public void init() throws IOException{
		
		InetAddress ia = InetAddress.getLocalHost();
		portti = 3126;
		porttinumero = "6000";
		lahetysData = porttinumero.getBytes();
		lahtevaPaketti = new DatagramPacket(lahetysData, lahetysData.length, ia, 3126);
		ds = new DatagramSocket();
		ss = new ServerSocket(6000);
		
		System.out.println("Lahetetaan datagram paketti serverille");
		try{
			System.out.println(lahtevaPaketti);
			ds.send(lahtevaPaketti);
		} catch(IOException x) {x.printStackTrace();}
		try{
			ss.setSoTimeout(10000);
			soketti = ss.accept();
		}catch(SocketTimeoutException x){System.out.println("Ei yhdistetty serveriin.");}
		
		try {
			// tehdaan output ja input streamit ja aukaistaan soketti

			InputStream is = soketti.getInputStream();
			OutputStream os = soketti.getOutputStream();

			output = new ObjectOutputStream(os);
			input = new ObjectInputStream(is);
			output.flush();
			} catch (IOException e) {System.out.println(e);}
		
		try{
			tiedostovirta = input.readInt();
			System.out.println("Saatu tiedosto virta = " + tiedostovirta);
		}catch(SocketTimeoutException x){
			output.writeInt(-1);
			output.flush();
			System.exit(0);
			}
		summausPalvelu();
		

		//kuunellaan serverilta tulevaa tiedostovirtaa
		ss.setSoTimeout(500000);
		while(true){
			try{
				int numero = input.readInt();
				
				if(numero == 0){
					try{
						for(int i = 0; i < threads.length; i++){
							threads[i].interrupt();
						}
						ds.close();
						ss.close();
						soketti.close();
						System.exit(0);
					}catch(Exception e){
						e.printStackTrace();
					}
				}//if
				else if ( numero == 1){
				output.writeInt(summaTulos());
				output.flush();
				}
				else if ( numero == 2){
				output.writeInt(lueMaksimiIndex());	
				}
				else if ( numero == 3){
				output.writeInt(luekokonaisSumma());
				}
			}catch(IOException x){
				x.printStackTrace();
			}
		}//while
		}
		

	private static int lueMaksimiIndex() {
		int suurin = 0;
		int maksimi = 0;
		try{
			Thread.sleep(100);
			for (int i = 0; i < summatut.length; i++){
				suurin = summatut[i].getSumma1();
				maksimi = i+1;
			}
		}catch(InterruptedException e){
			e.printStackTrace();
			System.exit(0);
		}
		return maksimi;
	}


	private static int luekokonaisSumma() {
		int tulos = 0;
		try{
			Thread.sleep(100);
			for(int i = 0; i < summatut.length; i++){
				tulos += summatut[i].getSumma1();
			}
		}catch(InterruptedException e) {
			e.printStackTrace();
			System.exit(0);
		}
		return tulos;
	}


	private static int summaTulos() {
		int tulos = 0;
		try{
			Thread.sleep(100);
			for (int i = 0; i < summatut.length; i++){
				tulos += summatut[i].getSumma1();
			}
		}catch( InterruptedException e){
			e.printStackTrace();
			System.exit(0);
		}
		return tulos;
	}


	private static void summausPalvelu() {
		
		summatut = new Summaaja[tiedostovirta];
		threads = new Thread[tiedostovirta];
		try{
			for(int i = 0; i < tiedostovirta; i++){
				summatut[i] = new Summaaja(600+i);
				threads[i] = summatut[i];
				threads[i].start();
				output.writeInt(summatut[i].getPortti());
				output.flush();
			}
			System.out.println("aloitettiin: " + tiedostovirta + " summatut.");
			}catch(IOException e){
				e.printStackTrace();
				System.exit(0);
			}
		}
}