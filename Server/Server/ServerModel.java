package Server;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Random;
import java.util.logging.Logger;

import Message.Message;
import Message.Message.MessageType;
import Message.Message.Value;

import javafx.concurrent.Task;

public class ServerModel {
    private Integer port;
    private final Logger logger = Logger.getLogger("");
    private ArrayList<Socket> clientSockets = new ArrayList<Socket>();
	private static ArrayList<Integer> hashPlayers = new ArrayList<Integer>();
	private static ServerModel serverModel;
	private static Integer randomStart;
	private static Hashtable<Long, String> users = new Hashtable<Long, String>();
	private static ArrayList<Hashtable> tempList = new ArrayList<Hashtable>();
	private static ArrayList<Hashtable> binomList = new ArrayList<Hashtable>();
    
	/**
	 * Factory method for returning the singleton board
	 * 
	 * @param mainClass
	 *            The main class of this program
	 * @return ServerModel
	 * @author L.Weber
	 */
	public static ServerModel getServerModel() {
		if (serverModel == null)
			serverModel = new ServerModel();
		return serverModel;
	}
    
    final Task<Void> serverTask = new Task<Void>() {
        @Override
        protected Void call() throws Exception {
            ServerSocket listener = null;
            try {
                listener = new ServerSocket(port, 10, null);
                logger.info("Listening on port " + port);

                while (true) {
                    // The "accept" method waits for a request, then creates a socket
                    // connected to the requesting client
                    Socket clientSocket = listener.accept();
                    
                    ServerThreadForClient client = new ServerThreadForClient(clientSocket);
                    client.setUserName(clientSocket.hashCode());
                    clientSockets.add(clientSocket);
                    client.start();
                    client.sendMessageBackToClient(new Message(MessageType.Hash,client.getUserName()));
                    logger.info("Send the client-name back: " + client.getUserName());
                }
            } catch (Exception e) {
                System.err.println(e);
            } finally {
                if (listener != null) listener.close();
            }
            return null;
        }
    };
    
    /**
     * For the Random start the game
     * Must be an Singleton, there is only one start-point
     * @author L.Weber
     */
    public static Integer buildRandomStart() {
    	if(randomStart == null) {
        	Random rand = new Random();
    		randomStart = rand.nextInt(2)+1;
    		return randomStart;
    	} else {
    		return randomStart;
    	}

    }
    
    /**
     * Called by the controller, to start the serverSocket task
     */
    public void startServerSocket(Integer port) {
        this.port = port;
        new Thread(serverTask).start();
    }
    
	public ArrayList<Socket> getSockets(){
		return this.clientSockets;
	}
	
	public static ArrayList<Integer> getHashList(){
		return hashPlayers;
	}
	
	public void setUsers(long l, String userName) {
		users.put(l, userName);
	}
	
	public Hashtable getUsers() {
		return users;
	}
	
	/**
	 * For get the HashCode with a name
	 * @param name
	 * @return hashCode
	 * @author L.Weber
	 */
	public long getHashCodeWithName(String name) {
		long hash = 0;
		Enumeration e = (Enumeration) users.keys();
		while (e.hasMoreElements()) {
			long key = (long) e.nextElement();
			if(name.equalsIgnoreCase(users.get(key))) {
				hash = key;
				return hash;
			}
			
		}
		return hash;
	}
	
	/**
	 * For generate Binom Sockets with two hashcodes
	 * @param hash1
	 * @param hash2
	 * @return Hashtable Binom
	 * @author L.Weber
	 */
	public void generateBinomSocket (long hash1, long hash2) {
		Hashtable<Socket,Socket> binom = new Hashtable<Socket, Socket>();
		
		for (int i = 0; i < clientSockets.size(); i++) {
			long clientSocketHash1 = clientSockets.get(i).hashCode();
			if(hash1 == clientSocketHash1) {
				for(int j = 0; j < clientSockets.size(); j++) {
					long clientSocketHash2 = clientSockets.get(j).hashCode();
					if(hash2 == clientSocketHash2) {
						binom.put(clientSockets.get(i), clientSockets.get(j));
						tempList.add(binom);
					}
				}
			}
		}
	}
	
	/**
	 * Check and add to the binom list if both player press play
	 * @param hash1
	 * @param hash2
	 * @return
	 */
	public boolean addToBinomList (long hash1, long hash2) {
		for(int i = 0; i < tempList.size(); i++) {
			Enumeration e = (Enumeration) tempList.get(i).keys();
			while (e.hasMoreElements()) {
				long key = (long) e.nextElement().hashCode();
				if(key == hash1 || key == hash2) {
					binomList.add(tempList.get(i));
					return true;
				}
			}
		}
		return false;
	}
	
	public static ArrayList<Hashtable> getTempList() {
		return tempList;
	}
}
