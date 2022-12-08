import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.function.Consumer;

// class for handling networking from Server side
public class Server {
	TheServer server;  // instance of TheServer
	int counter;  // used for assigning numbers to clients
	int Port;  // port number to start the server
	private Consumer<Serializable> changeTotalClients;  // method to update totalClients
	private Consumer<Serializable> gameStatus;  // method to update gameStatus
	ArrayList<ClientThread> clients = new ArrayList<ClientThread>();  // stores all the ClientThreads in an ArrayList
	ServerSocket socketServer;  // instance of ServerSocket
	
	// constructor, creates a new TheSever object and starts that thread
	Server(Consumer<Serializable> totalClients, Consumer<Serializable> gameStatus, int Port) {
		this.changeTotalClients = totalClients;
		this.gameStatus = gameStatus;
		server = new TheServer();
		server.start();
		this.Port = Port;
		counter = 1;
	}
	
	// closes down the server by closing down the sockets
	void closeDownServer() {
		// closes all client threads
		for (ClientThread ct : clients) {
			try {
				ct.connection.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		// closes down the server
		try {
			socketServer.close();
		} catch (Exception e) {
			System.out.println("Server Already Closed");
		}
	}
	
	// starts the server and waits for clients to join
	public class TheServer extends Thread {
		public void run() {
			try {
				socketServer = new ServerSocket(Port);  // starts the server
				gameStatus.accept("Server is waiting for a client using Port " + Port);  // new client joined
		    	changeTotalClients.accept("Total Clients " + clients.size());
				while (true) {
					ClientThread c = new ClientThread(socketServer.accept(), counter);  // returns a socket
					clients.add(c);  // adds the clientThread to the arrayList
				    System.out.println("Client Connected!");
					c.start();  // starts a clientThread
					counter++;
					changeTotalClients.accept("Total Clients " + clients.size());
					gameStatus.accept("Client # " + c.clientNum + " Joined" + "\n");
				}
			} catch (Exception e) {
				changeTotalClients.accept("Invalid/Used Port or Server Connection Broken");  // Wrong Port Number
			}
		}
	}
		
	// class for handling connection to each individual client
	class ClientThread extends Thread {
		int clientNum;  // identifies the client
		Socket connection;
		ObjectInputStream in;
		ObjectOutputStream out;
		BaccaratGame g1;  // each client has a unique game
		
		// constructor, uses socket returned by the accept method
		ClientThread(Socket s, int num) {
			this.connection = s;
			this.clientNum = num;
		}
		
		public void run() {
			try {
				in = new ObjectInputStream(connection.getInputStream());
				out = new ObjectOutputStream(connection.getOutputStream());
				connection.setTcpNoDelay(true);
				g1 = new BaccaratGame();
			} catch (Exception e) {
				e.printStackTrace();
				System.out.println("Streams Not Open");
			}
			while(true) {
			    try {
			    	g1.resetGame();  // resets the game for each instance of the while loop
			    	BaccaratInfo info = (BaccaratInfo) in.readObject();  // reads in BaccaratInfo from the client
			    	// sets up all the required fields in the BaccaratGame object
			    	g1.Bet = info.Bet;
					g1.currentBet = info.currentBet;
					g1.evaluateWinnings();  // starts the game
					info.setBaccaratInfoServer(g1.winner, g1.totalWinnings, g1.playerHand, g1.bankerHand, g1.playerTotal, g1.bankerTotal);  // sets up the BaccaratInfo to send it back to the Client
					// updates the GUI
					gameStatus.accept("Client " + clientNum + " started a New Game");
					gameStatus.accept("Client " + clientNum + " betted on " + info.Bet + " for $" + info.currentBet + " and " + info.whoWon
							+ " won. Player Total was " + info.playerTotal + " and Banker Total was " + info.bankerTotal);
					if (info.whoWon.equals(info.Bet)) {
						gameStatus.accept("Client " + clientNum + " won $" + info.currentBet);
					} else {
						gameStatus.accept("Client " + clientNum + " lost $" + info.currentBet);
					}
					out.writeObject(info);  // sends the finished game results to the Client program
			    } catch (Exception e) {  // handles exception when client has left the game
			    	System.out.println("OOOOPPs...Something wrong....closing down!");
					gameStatus.accept("Client # " + clientNum + " Left" + "\n");
			    	clients.remove(this);
			    	changeTotalClients.accept("Total Clients " + clients.size());
			    	break;
			    }
			}
		}
		
	}
}
