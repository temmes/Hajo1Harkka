import java.io.*;
import java.net.*;

public class Summaaja extends Thread {
private int portti;
private ServerSocket ss;
private Socket soketti;
private ObjectInputStream input;
private int summa1;
private int summa2;
private int apu;
private InputStream is;

public Summaaja(int portti){
	this.portti = portti;
}
public int getPortti() {
	return portti;
}

public int getSumma1() {
	return summa1;
}

public int getSumma2() {
	return summa2;
}

public boolean onkoYhteys(){
	if (soketti.isConnected()){return true;}else{return false;}
}
public void run(){
	
	try {
		ss = new ServerSocket(portti);
		soketti = ss.accept();
		is = soketti.getInputStream();
		input = new ObjectInputStream(is);
	} catch (IOException e) {
		System.exit(0);
	}
	do{
		try {
			apu = input.readInt();
		
		if(apu == 0){
			soketti.close();
			ss.close();
			input.close();
			break;
		}
		summa1++;
		summa2+= apu;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}while(true);
	
}


}
