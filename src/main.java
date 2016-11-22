/*import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class main {
	
	public void main (String[] args){
		
		int portti = 3126;
		UdpUnicastServer serveri = new UdpUnicastServer(portti);
		UdpUnicastClient clientti = new UdpUnicastClient(portti);
		
		ExecutorService executorService = Executors.newFixedThreadPool(2);
		executorService.submit(clientti);
		executorService.submit(serveri);
	}
	

}
*/