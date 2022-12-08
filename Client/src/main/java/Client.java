import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.Socket;
import java.util.function.Consumer;

// Client class handles networking with Server
public class Client extends Thread {
	Socket socketClient;  // Socket
	ObjectOutputStream out;
	ObjectInputStream in;
	String IP;
	int Port;
	
	// two methods to update GUI
	Consumer<Serializable> changeScene;
	Consumer<Serializable> showInfo;
	
	// constructor
	Client(Consumer<Serializable> changeScene, Consumer<Serializable> showInfo, String IP, int Port) {
		this.changeScene = changeScene;
		this.showInfo = showInfo;
		this.IP = IP;
		this.Port = Port;
	}
	
	// attempts to connect with the server and initialize the streams
	public void run() {
		try {
			socketClient = new Socket(IP, Port);
			out = new ObjectOutputStream(socketClient.getOutputStream());
			in = new ObjectInputStream(socketClient.getInputStream());
			socketClient.setTcpNoDelay(true);
			changeScene.accept("Success");  // changes the scene after connection
		} catch (Exception e) {
			changeScene.accept("Failure");
		}
		while (true) {
			try {
				BaccaratInfo info = (BaccaratInfo) in.readObject();  // receives the finished gameinfo from server
				showInfo.accept(info);  // displays all the info in the GUI
			} catch (Exception e) {}
		}
	}

	// method used to send info to the Server
	public void send(BaccaratInfo data) throws Exception {
		System.out.println(data.Bet);
		out.writeObject(data);
	}
}
