import java.io.*;
import java.net.*;

class ChessServer {
	private ServerSocket ss;
	private int numPlayers;
	private ServerSideConnection player1;
	private ServerSideConnection player2;
	
	public ChessServer() {
		System.out.println("----Chess Server----");
		numPlayers = 0;
		
		try {
			ss = new ServerSocket(51734);
		}
		catch (IOException ex) {
			System.out.println("IOException from GameServer constructor");
		}
	}
	
	public void acceptConnections() {	//allows clients to connect
		try {
			System.out.println("Waiting for connection");
			while (numPlayers < 2) {	//allows only 2 players
				Socket s = ss.accept();
				numPlayers += 1;
				System.out.println("Player #" + numPlayers + " has connected");		//prints which player has connected
				ServerSideConnection ssc = new ServerSideConnection(s, numPlayers);
				if (numPlayers == 1) {
					player1 = ssc;
				}
				else {
					player2 = ssc;
				}
				Thread t = new Thread(ssc);
				t.start();
			}
			System.out.println("We now have 2 players. No longer accepting connections");
		}
		catch (IOException ex) {
			System.out.println("IOException from acceptConnections()");
		}
	}
	
	private class ServerSideConnection implements Runnable {
		private Socket socket;
		private DataInputStream dataIn;
		private DataOutputStream dataOut;
		private int playerID;
		
		public ServerSideConnection(Socket s, int id) {
			socket = s;
			playerID = id;
			try {
				dataIn = new DataInputStream(socket.getInputStream());
				dataOut = new DataOutputStream(socket.getOutputStream());
			} 
			catch (IOException ex) {
				System.out.println("IOException from run() constructor");
			}
		}
		
		public void run() {
			try {
				dataOut.writeInt(playerID);
				dataOut.flush();
				
				while (true) {
					if (playerID == 1) {
						String player1Board = dataIn.readLine();
						player2.sendBoard(player1Board);
					}
					else {
						String player2Board = dataIn.readLine();
						player1.sendBoard(player2Board);
					}
					//if(player1.checkLoss(Wking,"W","B")) {
						//break;	
					//}
				}
				//player1.closeConnection();
				//player2.closeConnection();
			} 
			catch (IOException ex) {
				System.out.println("IOException from run() SSC");
			}
		}
		
		public void sendBoard(String n) {	//sends boardArray
			try {
				dataOut.writeChars(n);
				dataOut.flush();
			}
			catch (IOException ex) {
				System.out.println("IOException from sendButtonNum() SSC");
			}
		}
		
		public void closeConnection() {
			try {
				socket.close();
				System.out.println("---CONNECTION TERMINATED---");
			}
			catch (IOException ex) {
				System.out.println("IOException on closeConnect() SSC");
			}
		}	
	}
	
	public static void main (String [] args) {
		ChessServer gs = new ChessServer();
		gs.acceptConnections();
	}
}
