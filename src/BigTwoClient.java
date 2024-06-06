import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

/**
 * Implements the NetworkGame interface. It is used to model a Big 
 * Two game client that is responsible for establishing a connection and communicating with
 * the Big Two game server. Below is a detailed description for the BigTwoClient class.

 * @author Winson Sutanto
 *
 */
public class BigTwoClient implements NetworkGame{
	private BigTwo game;//a BigTwo object for the Big Two card game
	private BigTwoGUI gui;//a BigTwoGUI object for the Big Two card game.
	private Socket sock;//a socket connection to the game server.
	private ObjectOutputStream oos;//an ObjectOutputStream for sending messages to the server.
	private int playerID;//an integer specifying the playerID (i.e., index) of the local player
	private String playerName;//a string specifying the name of the local player
	private String serverIP;// a string specifying the IP address of the game server
	private int serverPort;//an integer specifying the TCP port of the game server.
//	private ObjectInputStream ois;//an ObjectInputStream for receiving messages from the server.
	
	/**
	 * 	a constructor for creating a Big Two client
	 * @param game a reference to a BigTwo object associated with this client
	 * @param gui a reference to a BigTwoGUI object associated the BigTwo object
	 */
	public BigTwoClient(BigTwo game, BigTwoGUI gui) {
		this.game = game;
		this.gui = gui;
	}

	/**
	 * a method for getting the playerI
	 * @return index of the local player.
	 */
	@Override
	public int getPlayerID() {
		return this.playerID;
	}

	/**
	 * a method for setting the playerID (i.e., index) of the local player
	 */
	@Override
	public void setPlayerID(int playerID) {
		this.playerID = playerID;
		
	}

	/**
	 * a method for getting the name of the local player
	 * @return name of local player
	 */
	@Override
	public String getPlayerName() {
		return this.playerName;
	}

	/**
	 * a method for setting the name of the local player
	 */
	@Override
	public void setPlayerName(String playerName) {
		this.playerName = playerName;
		
	}

	/**
	 * a method for getting the IP address of the game server.
	 * @return the server IP address
	 */
	@Override
	public String getServerIP() {
		return this.serverIP;
	}

	/**
	 * a method for setting the IP address of the game server.
	 */
	@Override
	public void setServerIP(String serverIP) {
		this.serverIP = serverIP;
		
	}

	/**
	 * a method for getting the TCP port of the game server
	 * @return TCP port of the game server
	 */
	@Override
	public int getServerPort() {
		return this.serverPort;
	}

	/**
	 * a method for setting the TCP port of the game server
	 */
	@Override
	public void setServerPort(int serverPort) {
		this.serverPort = serverPort;
		
	}

	/**
	 * a method for making a socket connection with the game server.
	 * Upon successful connection, you should (i) create an ObjectOutputStream for sending messages to the game server.
	 * (ii) create a new thread for receiving messages from the game server.
	 */
	@Override
	public void connect() {
		try {
			//creates a socket to connect to game server
			this.sock = new Socket("127.0.0.1",2397);
			//Output stream to send message to game server
			this.oos = new ObjectOutputStream(sock.getOutputStream());
//			this.ois = new ObjectInputStream(sock.getInputStream());
			//Run a thread to listen to messages from game server
			Thread readerThread = new Thread(new ServerHandler());
			readerThread.start();
		}catch (IOException ex) {
			ex.printStackTrace();
		}
		
		
	}

	/**
	 * a method for parsing the messages received from the game server.
	 * This method should be called from the thread responsible for receiving messages from the game server.
	 * Based on the message type, different actions will be carried out
	 */
	@Override
	public void parseMessage(GameMessage message) {
		
		switch (message.getType()) {
		case CardGameMessage.PLAYER_LIST:
			this.playerID = message.getPlayerID();
			//updating names in playerlist
			String[] playerList = (String[])message.getData();
			for (int i = 0 ; i < 4 ; i++) {
				game.getPlayerList().get(i).setName(playerList[i]);
			}
			sendMessage(new CardGameMessage(CardGameMessage.JOIN,-1,this.playerName));
			break;
			
		case CardGameMessage.JOIN:
			if (message.getPlayerID() == playerID) {
				sendMessage(new CardGameMessage(CardGameMessage.READY,-1,null));
			}
			game.getPlayerList().get(message.getPlayerID()).setName((String) message.getData());
			break;
			
		case CardGameMessage.FULL:
			gui.printMsg("Server is full, cannot join game");
			//quit from server
			break;
			
		case CardGameMessage.QUIT:
			//setting name of player who left to empty string
			game.getPlayerList().get(message.getPlayerID()).setName("");
			if (!game.endOfGame()) {
				gui.disable();
				sendMessage(new CardGameMessage(CardGameMessage.QUIT,-1,null));
			}
			break;
		case CardGameMessage.READY:
			gui.printMsg(game.getPlayerList().get(message.getPlayerID())+" is ready.");
			break;
			
		case CardGameMessage.START:
			game.start((BigTwoDeck)message.getData());
			break;
			
			
		case CardGameMessage.MOVE:
			int[] cardIdx = (int[]) message.getData();
			game.checkMove(message.getPlayerID(),cardIdx);
			break;
			
			
		case CardGameMessage.MSG:
			String chat = (String) message.getData();
			gui.printChat(chat);
			break;
		}
			
	}

	/**
	 * a method for sending the specified message to the game server.
	 * This method should be called whenever the client wants to commuicate with the game server or other clients. 
	 */
	@Override
	public void sendMessage(GameMessage message) {
		try {
			oos.writeObject(message);
			oos.flush();
		}catch(IOException ex) {
			ex.printStackTrace();
		}
	}
	
	/**
	 * an inner class that implements the Runnable interface.
	 */
	class ServerHandler implements Runnable {
		private ObjectInputStream ois;
		@Override
		public void run() {
			try {
				ois = new ObjectInputStream(sock.getInputStream());
				CardGameMessage msg;
				while(true) {
					msg = (CardGameMessage) ois.readObject();
					if (msg != null) {
						parseMessage(msg);
					}
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

}
