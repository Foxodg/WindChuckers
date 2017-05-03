package Server;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Logger;

import Message.Message.Value;
import javafx.concurrent.Task;

public class ServerModel {
    private Integer port;
    private final Logger logger = Logger.getLogger("");
    private 
    
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
                    client.start();
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
     * Called by the controller, to start the serverSocket task
     */
    public void startServerSocket(Integer port) {
        this.port = port;
        new Thread(serverTask).start();
    }
}


class Board {
	private static Value[][] board; // singleton
	private static int boardSize = 8;

	public Board() {
	}

	/**
	 * Factory method for returning the singleton board
	 * 
	 * @param mainClass
	 *            The main class of this program
	 * @return The singleton resource locator
	 */
	public static Value[][] getBoard() {
		if (board == null)
			board = new Value[boardSize][boardSize];
		return board;
	}

}