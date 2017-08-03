package Server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.logging.Logger;


import AI.Board;
import AI.Kamisado;
import AI.Move;
import AI.NewRound;
import AI.PlayerType;
import AI.Tile;
import DataBase.DataBase;
import Login.LoginModel;
import Message.Message;
import Message.Message.MessageType;
import Message.Message.Value;

/**
 * 
 * @author L.Weber
 *
 */
public class ServerThreadForClient extends Thread {
	private volatile boolean runner = true;
	private final Logger logger = Logger.getLogger("");
	private Socket clientSocket;
	private Kamisado kamisado;
	private Board board = Board.getBoard();
	private int name;
	private static Tile lastInComingTile;

	private static boolean wantAI = false;
	private static boolean wantDoubleAI = false;

	DataBase h2 = DataBase.getDB();

	public ServerThreadForClient(Socket clientSocket) {
		this.clientSocket = clientSocket;
		this.kamisado = Kamisado.getKamisado();
	}

	/**
	 * Process messages
	 */
	@Override
	public void run() {
		while (runner) {
			Message.MessageType lastMessageType = null;
			logger.info("Request from client " + clientSocket.getInetAddress().toString() + " for server "
					+ clientSocket.getLocalAddress().toString());
			try {
				Message message = Message.receive(clientSocket);
				getMessageType(message);

			} catch (Exception e) {
				logger.severe(e.getLocalizedMessage());
				logger.severe(e.toString());
			}
		}
	}

	public void getMessageType(Message message) {
		if (message.getMessageType() == MessageType.ChatMessage) {
			logger.info("Server: " + "Chat-Message: " + message.getChatMessage());
			// For the Chat-Messaging
			sendMessageBackToClient(message);
			
		} else if (message.getMessageType() == MessageType.Coordinate) {			
			if(!this.wantAI && !this.wantDoubleAI) {
				if(message.getPlayer() == 9) {
					Message messagePat = new Message(MessageType.Coordinate,message.getXCoordinate1(),0,0,0, 9);
					sendMessageBackToClient(messagePat);
				}
				//This is a Friends-Game don't need to do anything on the server only response to clients
				logger.info("Server Friends-Game: " + "x-Coordinates1: " + message.getXCoordinate1() + " y-Coordinates1: "
						+ message.getYCoordinate1() + " x-Coordinates2: " + message.getXCoordinate2() + " y-Coordinates2: "
						+ message.getYCoordinate2() + " Value: " + message.getPlayer());
				//send this message back to the client
				sendMessageBackToClient(message);
			}
			
			else if (this.wantAI) {
				//This is a Single-AI-Game make the move on the board here and send back to the clinet
				logger.info("Server Single-AI-Game: " + "x-Coordinates1: " + message.getXCoordinate1() + " y-Coordinates1: "
						+ message.getYCoordinate1() + " x-Coordinates2: " + message.getXCoordinate2() + " y-Coordinates2: "
						+ message.getYCoordinate2() + " Value: " + message.getPlayer());
				//Safe the color from the incoming Tile
				lastInComingTile = board.getTile(message.getXCoordinate2(), message.getYCoordinate2());
				// Safes the coordinates for the hidden-Board on the Server
				if(board.getTile(message.getXCoordinate1(), message.getYCoordinate1()).getTower().getPlayerType() == ServerController.playerConverter(message.getPlayer())) {
					//only when the tower is from the right player
					board.makeMove(new Move(message.getXCoordinate1(), message.getYCoordinate1(), message.getXCoordinate2(),
							message.getYCoordinate2(), true));
					Board board = Board.getBoard();
					if(!board.isWinSituation()) {
						//don't send a move back when the game is finish
						//isWinSituation upgrade also the tower
						Move move = kamisado.setPlayConfiguration(true, 5, message.getPlayer());
						PlayerType playerType = board.getTile(move.getX2(), move.getY2()).getTower().getPlayerType();
						Message messageAI = new Message(MessageType.Coordinate, move.getX1(), move.getY1(), move.getX2(),
								move.getY2(), ServerController.playerConverter(playerType));
						if(lastInComingTile.getColor() == board.getTile(messageAI.getXCoordinate2(), messageAI.getYCoordinate2()).getTower().getColor()) {
							sendMessageBackToClient(messageAI);	
							logger.info("Server Single-AI-Game: Send message back to client: " + "x-Coordinates1: " + messageAI.getXCoordinate1() + " y-Coordinates1: "
							+ messageAI.getYCoordinate1() + " x-Coordinates2: " + messageAI.getXCoordinate2() + " y-Coordinates2: "
							+ messageAI.getYCoordinate2() + " Value: " + messageAI.getPlayer());
							//check now is it a win Situation?
							if(this.board.isWinSituation()) {
								ServerThreadForClient.sendMessageBackToClient(new Message(MessageType.Update,messageAI.getXCoordinate2(),messageAI.getYCoordinate2(), board.getTile(messageAI.getXCoordinate2(), messageAI.getYCoordinate2()).getTower().getGems()+1, messageAI.getPlayer()));
								ServerThreadForClient.sendMessageBackToClient(new Message(MessageType.NewRound, true));
								
								//Now the turn is done - it's time to make the next move
								board.setLastMove(move);
								Board boardNext = Board.getBoard();
								Move moveNext = kamisado.setPlayConfiguration(true, 5, message.getPlayer());
								PlayerType playerTypeNext = boardNext.getTile(moveNext.getX2(), moveNext.getY2()).getTower().getPlayerType();
								Message messageAINext = new Message(MessageType.Coordinate, moveNext.getX1(), moveNext.getY1(), moveNext.getX2(),
										moveNext.getY2(), ServerController.playerConverter(playerTypeNext));
								ServerThreadForClient.sendMessageBackToClient(new Message(MessageType.Coordinate,moveNext.getX1(), moveNext.getY1(), moveNext.getX2(),
										moveNext.getY2(), ServerController.playerConverter(playerTypeNext)));
							}
						}
					} 
				}
			}
			
			else if (this.wantDoubleAI) {
				//This is a Double-AI Game - We don't to need send back anything. Only send from server to client (one-way)
				logger.info("Server Double-AI-Game: " + "x-Coordinates1: " + message.getXCoordinate1() + " y-Coordinates1: "
						+ message.getYCoordinate1() + " x-Coordinates2: " + message.getXCoordinate2() + " y-Coordinates2: "
						+ message.getYCoordinate2() + " Value: " + message.getPlayer());
			}

		} else if (message.getMessageType() == MessageType.Update) {
			if(!this.wantAI && !this.wantDoubleAI) {
				//This is a Single-AI-Game make the move on the board here and send back to the clinet
				logger.info("Server Friend-Game: " + " x-Coordinates: " + message.getXCoordinate2()
				+ " y-Coordinates: " + message.getYCoordinate2() + " Gems: " + message.getGems() + " Player: " + message.getPlayer());	
				sendMessageBackToClient(message);
			}
			else if(this.wantAI) {
				//This is a Single-AI-Game make the move on the board here and don't send back to the clinet
				logger.info("Server Single-AI-Game: " + " x-Coordinates: " + message.getXCoordinate2()
				+ " y-Coordinates: " + message.getYCoordinate2() + " Gems: " + message.getGems() + " Player: " + message.getPlayer());	
				board.upgradeTower(board.getTile(message.getXCoordinate2(), message.getYCoordinate2()).getTower());
			}
			if(this.wantDoubleAI) {
				//This is a Double-AI Game - We don't to need send back anything. Only send from server to client (one-way)
				logger.info("Server Double-AI-Game: " + " x-Coordinates: " + message.getXCoordinate2()
				+ " y-Coordinates: " + message.getYCoordinate2() + " Gems: " + message.getGems() + " Player: " + message.getPlayer());	
			}
		} else if (message.getMessageType() == MessageType.DBMessage) {
			logger.info("Server: " + "DB-Message: ");
			// DB-Message
			if (message.getDB() == 0) {
				// 0 stands for give the hole ArrayList Back
				try {
					sendMessageBackToClient(new Message(MessageType.DBMessageFull, 0, h2.selectPlayer()));
				} catch (SQLException e) {
					e.printStackTrace();
				}
			} else if (message.getDB() == 1) {
				// 1 stands for Insert
				insertPlayer(message.getId(), message.getUserName(), message.getPreName(), message.getLastName(),
						message.getPassword());
				try {
					sendMessageBackToClient(new Message(MessageType.DBMessageFull, 0, h2.selectPlayer()));
				} catch (SQLException e) {
					e.printStackTrace();
				}
			} else if (message.getDB() == 2) {
				// 2 stands for Update
				updatePlayer(message.getId(), message.getUserName(), message.getPreName(), message.getLastName(),
						message.getPassword());
				try {
					sendMessageBackToClient(new Message(MessageType.DBMessageFull, 0, h2.selectPlayer()));
				} catch (SQLException e) {
					e.printStackTrace();
				}
			} else if (message.getDB() == 3) {
				// 3 stands for Delete
				try {
					h2.deletePlayer(message.getId());
					sendMessageBackToClient(new Message(MessageType.DBMessageFull, 0, h2.selectPlayer()));
				} catch (SQLException e) {
					e.printStackTrace();
				}
			} else if (message.getDB() == 4) {
				// 4 stands for all Friends
				try {
					ArrayList<String> friends = new ArrayList<String>();
					friends = h2.selectFriends();
					sendMessageBackToClient(new Message(MessageType.DBMessage, 4, friends));
				} catch (SQLException e) {
					e.printStackTrace();
				}
			} else if (message.getDB() == 5) {
				// 5 Stands for make new Friends
				try {
					h2.insertFriend(message.getId(), message.getFriendId());
				} catch (SQLException e) {
					e.printStackTrace();
				}
				logger.info("New Friend: " + message.getId() + message.getFriendId());
			} else if (message.getDB() == 6) {
				// 6 Stands for delete a Friend Enty
				try {
					h2.deleteFriend(message.getId());
					logger.info("ServerThread: Deleted Friend with Id: " + message.getId());
				} catch (SQLException e) {
					e.printStackTrace();
				}
			} 		
			// Round cap
			else if (message.getDB() == 88) {
				logger.info("Round cap: " + message.getDB());
				sendMessageBackToClient(new Message(MessageType.DBMessage, message.getDB(), message.getId()));
			}
			// Depth
			else if (message.getDB() == 90) {
				logger.info("Depth is: " + message.getId());
				kamisado.setDepth(message.getId());
			}
			// Random-Object for Game-Start
			else if (message.getDB() == 91) {
				ServerModel model = ServerModel.getServerModel();
				int oneOrTwo = model.buildRandomStart();
				sendMessageBackToClient(new Message(MessageType.DBMessage, 91, oneOrTwo));
			}
		} else if (message.getMessageType() == MessageType.WinMessage) {
			logger.info("Server: " + "Win-Message: ");
			try {
				int id = h2.selectWithName(message.getUserName());
				int win = (int) message.getHash();
				h2.updatePreparedStatementWithId(win, id);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		} else if (message.getMessageType() == MessageType.AISingle) {
			logger.info("Server: " + "Paramter-Injection SinglePlayer:\n" + message.getProgressTWO() + "\n"
					+ message.getMovesTWO() + "\n" + message.getBlockTWO() + "\n" + message.getSumoBlockTWO() + "\n"
					+ message.getSumoWinTWO() + "\n" + message.getWinTWO());
			kamisado.setProgressTwo(message.getProgressTWO());
			kamisado.setMovesTwo(message.getMovesTWO());
			kamisado.setBlockTwo(message.getBlockTWO());
			kamisado.setSumoBlockTwo(message.getSumoBlockTWO());
			kamisado.setSumoWinTwo(message.getSumoWinTWO());
			kamisado.setWinTwo(message.getWinTWO());
			this.wantAI = true;
			Board.setWandAI(true);
		} else if (message.getMessageType() == MessageType.AIDouble) {
			logger.info("Server: " + "Paramter-Injection DoublePlayer:\n" + "\nONE: " + message.getProgressONE() + "\n"
					+ message.getMovesONE() + "\n" + message.getBlockONE() + "\n" + message.getSumoBlockONE() + "\n"
					+ message.getSumoWinONE() + "\n" + message.getWinONE() + "\nTWO: " + message.getProgressTWO() + "\n"
					+ message.getMovesTWO() + "\n" + message.getBlockTWO() + "\n" + message.getSumoBlockTWO() + "\n"
					+ message.getSumoWinTWO() + "\n" + message.getWinTWO());

			kamisado.setProgressOne(message.getProgressONE());
			kamisado.setMovesOne(message.getMovesONE());
			kamisado.setBlockOne(message.getBlockONE());
			kamisado.setSumoBlockOne(message.getSumoBlockONE());
			kamisado.setSumoWinOne(message.getSumoWinONE());
			kamisado.setWinOne(message.getWinONE());

			kamisado.setProgressTwo(message.getProgressTWO());
			kamisado.setMovesTwo(message.getMovesTWO());
			kamisado.setBlockTwo(message.getBlockTWO());
			kamisado.setSumoBlockTwo(message.getSumoBlockTWO());
			kamisado.setSumoWinTwo(message.getSumoWinTWO());
			kamisado.setWinTwo(message.getWinTWO());
			this.wantAI = false;
			this.wantDoubleAI = true;
			Board.setDoubleAI(true);
			Board.setWandAI(false);

			if (this.wantDoubleAI) {
				Move move = kamisado.setPlayConfiguration(false, 5, message.getPlayer());
			}
		}
		// the hashcode for unique identifier for the client
		else if (message.getMessageType() == MessageType.Hash) {
			ServerModel.getHashList().add(message.getDB());
		}
		// timecap
		else if (message.getMessageType() == MessageType.Time) {
			sendMessageBackToClient(new Message(MessageType.Time, message.getTime()));
		}
		// NewRound
		else if (message.getMessageType() == MessageType.NewRound) {
			if(!this.wantAI && !this.wantDoubleAI) {
				//For the Friends-Game
				sendMessageBackToClient(message);
			}
			if(this.wantAI) {
				//For the Single AI-Game
				NewRound newRound;
				if (message.getWin() == true) {
					newRound = NewRound.Left;
				} else {
					newRound = NewRound.Right;
				}
//				if(!this.wantAI) {
//					sendMessageBackToClient(message);
//				}
				board.newRound(newRound);
			}
			if(this.wantDoubleAI) {
				//For the Double-AI-Game is nothing to do
			}
		}
		// For send the name
		else if (message.getMessageType() == MessageType.Name) {
			ServerModel model = ServerModel.getServerModel();
			model.setUsers(message.getHash(), message.getUserName());
			logger.info("User added - id: " + message.getHash() + " UserName: " + message.getUserName());
		}
		// For build binom
		else if (message.getMessageType() == MessageType.Binom) {
			ServerModel model = ServerModel.getServerModel();
			long hash1 = model.getHashCodeWithName(message.getUserName());
			long hash2 = model.getHashCodeWithName(message.getFriendName());

			if (!model.addToBinomList(hash1, hash2)) {
				model.generateBinomSocket(hash1, hash2);
			}
			
			//build a new Waiter
			ArrayList<Waiter> waiterList = model.getWaiterList();
			boolean one = false;
			boolean two = false;
			
			if(!waiterList.isEmpty()) {
				for(int i = 0; i < waiterList.size(); i++) {
					if(waiterList.get(i).getHash1() == hash1) {
						if(waiterList.get(i).getHash2() == hash2) {
							logger.info("waiter already exist");
						}
					} else {
						one = true;
					}
				}
				for(int i = 0; i < waiterList.size(); i++) {
					if(waiterList.get(i).getHash2() == hash1) {
						if(waiterList.get(i).getHash1() == hash2) {
							logger.info("waiter already exits");
						}
					} else {
						two = true;
					}
				}
				if(one && two) {
					Waiter waiter = new Waiter(hash1, hash2);
					model.setNewWaiter(waiter);
					
					//send both hash back to the client
					sendMessageBackToClient(new Message(MessageType.Binom, hash1, hash2));
				}
			} else {
				Waiter waiter = new Waiter(hash1, hash2);
				model.setNewWaiter(waiter);
			}
			
		}
		// For send chat-Message to Binom
		else if (message.getMessageType() == MessageType.Capsule) {
			ArrayList<ArrayList<Socket>> capsuleBinom = new ArrayList<ArrayList<Socket>>();
			ServerModel model = ServerModel.getServerModel();
			long hash = message.getHash();
			String chat = message.getUserName();
			Message messageCapsule = new Message(MessageType.ChatMessage, chat);
			
			ArrayList<ArrayList<Socket>> binomList = model.getBinomList();
			
			for(int i = 0; i < binomList.size(); i++) {
				for(int j = 0; j < binomList.get(i).size(); j++) {
					long hash1 = binomList.get(i).get(j).hashCode();
					if(hash1 == hash) {
						capsuleBinom.add(binomList.get(i));
					}
				}
			}
			ArrayList<Socket> capsule = new ArrayList<Socket>();
			for(int i = 0; i < capsuleBinom.size(); i++) {
				capsule.addAll(capsuleBinom.get(i));
			}

			if (!capsule.isEmpty()) {	
				//send the message to the right place
				for (int i = 0; i < capsule.size(); i++) {
					try {
						if(capsule.get(i).isConnected()) {
							messageCapsule.send(capsule.get(i));
						}
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			} else {
				message = new Message(MessageType.ChatMessage, chat);
				sendMessageBackToClient(message);
			}
		}
		// for close the waiter
		else if(message.getMessageType() == MessageType.Waiter) {
				ServerModel model = ServerModel.getServerModel();
				long hash = message.getTime();
				ArrayList<Waiter> waiterList = model.getWaiterList();
				
				for(int i = 0; i < waiterList.size(); i++) {
					//is the ready Player hash1
					if(waiterList.get(i).getHash1() == hash) {
						if(waiterList.get(i).getWaiter1()) {
							logger.info("the waiter1 is now false");
							waiterList.get(i).setWaiter1(false);
						} else {
							//when dont want to be ready
							logger.info("the waiter1 is now true");
							waiterList.get(i).setWaiter1(true);
						}

					}
					//is the ready Player hash2
					else if(waiterList.get(i).getHash2() == hash) {
							if(waiterList.get(i).getWaiter2()) {
								logger.info("the waiter2 is now false");
								waiterList.get(i).setWaiter2(false);
							} else {
								//when dont want to be ready
								logger.info("the waiter2 is now true");
								waiterList.get(i).setWaiter2(true);
							}
					}
				}
				boolean go = false;
				for(int i = 0; i < waiterList.size(); i++) {
					if(!waiterList.get(i).getWaiter1()) {
						if(!waiterList.get(i).getWaiter2()) {
							go = true;
						}
					}
					if(go) {
						logger.info("Both are ok - GO!!!");
						waiterList.get(i).setRunner(false);
						sendMessageBackToClient(new Message(MessageType.Waiter));
					}
				}
			}
		else if(message.getMessageType() == MessageType.End) {
			//end the server
			runner = false;
		}
		else {
			logger.info("Server" + "Error-Message: ");
			// Else must be an Error-Message
		}
	}

	private void insertPlayer(int id, String userName, String preName, String lastName, String password) {
		// TODO Auto-generated method stub
		try {
			h2.insertPlayer(id, userName, preName, lastName, password);
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	public void updatePlayer(int id, String userName, String preName, String lastName, String password) {
		try {
			h2.updatePreparedStatementPreName(preName, id);
			h2.updatePreparedStatementSurName(lastName, id);
			h2.updatePreparedStatementUserName(userName, id);
			h2.updatePreparedStatementPassword(password, id);

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public static void sendMessageBackToClient(Message message) {
		try {
			ServerModel model = ServerModel.getServerModel();
			ArrayList<Socket> sockets = model.getSockets();
			for (Socket socket : sockets) {
				message.send(socket);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public int getUserName() {
		return this.name;
	}

	public void setUserName(int name) {
		this.name = name;
	}
	
	public static boolean getWantDoubleAI() {
		return wantDoubleAI;
	}
}
