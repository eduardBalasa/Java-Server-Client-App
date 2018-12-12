package controller;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerMain {

	private static DBConnection dbConnection;
	public ServerMain() {
	}

	public static void main(String[] args) {
		try {
			dbConnection = new DBConnection();
			@SuppressWarnings("resource")
			ServerSocket server = new ServerSocket(9091);
			System.out.println("Start server");
			while (true) {
				try {
					Socket socket = server.accept();
					new Thread(new ClientWorker(socket, dbConnection)).start();
				} catch (Exception ex) {
					ex.printStackTrace();
				}
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	
	/*cerinta pe partea Server-ului:
	 * - se conecteaza la DB si in functie de actiunea trimisa de client: update, insert, select, delete, clear 
	 * - rezolva cererea 
	 * - intoarce raspunsul clientului impachetat in format XML/ (optinal JSON)
	 * 
	 * - server-ul este indepedent the structura din informatia
	 */
}
