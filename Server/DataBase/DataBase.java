package DataBase;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.logging.Logger;

import org.h2.tools.DeleteDbFiles;

import WindChuckers_Main.WindChuckers;

/**
 * 
 * @author L.Weber
 *
 */
public class DataBase {

	private static Logger logger = Logger.getLogger("");

	private static DataBase h2;

	private static final String DB_DRIVER = "org.h2.Driver";
	private static final String DB_CONNECTION = "jdbc:h2:~/WindChuckers";
	private static final String DB_USER = "";
	private static final String DB_PASSWORD = "";

	// only for test the DB and show the tables
//	public static void main(String[] args) throws Exception {
//		deleteDB();
//		deletePlayer(2);
//		createDB();
//		updatePreparedStatementWithId(1);
//	}
//		ArrayList<String> searchList = selectPlayer();
//		for(int i = 0; i < searchList.size(); i++){
//			System.out.println(searchList.get(i));
//		}
//		insertPlayer(1,"lukas","weber",1);
//		isTheEntryThere(1);
//		update("UPDATE PLAYER SET WINS = 2 WHERE PLAYERID = 1");
//		System.out.println(selectWithName("weber"));
//		update("UPDATE PLAYER SET WINS =0 WHERE PLAYERID = 1");
//		insertFriend(2,1);
//		
//		ArrayList<String> friendsList = selectFriends();
//		for(int i = 0; i < friendsList.size(); i++){
//		System.out.println(friendsList.get(i));
//		}	
//		
//		System.out.println(selectLastIdFriends());
//		
//		boolean request = selectFriendsRequest(1,2);
//		System.out.println(request);
		
//		boolean request = areBothRequestHere(1,2);
//		System.out.println(request);
//	}

	/**
	public DataBase() {
	}
	/**
	 * Factory method for returning the singleton database
	 * @param DataBase
	 * @return The singleton database
	 */
	public static DataBase getDB() {
		if (h2 == null)
			h2 = new DataBase();
			if(!checkIsDBThere()){
				deleteDB();
			}
		return h2;
	}
	
	/**
	 * Check is the DB there? If its there return true, when not return false
	 * @return is there = true, is not = false
	 */
	public static boolean checkIsDBThere(){
		Connection conn = getDBConnection();
		ResultSet rset = null;
		try {
			rset = conn.getMetaData().getTables(null, null, "PLAYER", null);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		try {
			if(rset.next()){
				return true;
			} else {
				return false;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
		
	}

	/*********************************************************************************************
	 * Select Statements
	 * @author L.Weber
	 *********************************************************************************************/
	
	/**
	 * becomes a whole statement for the Table 
	 * 
	 * @param statement
	 * @return gives id back 
	 * @throws SQLException
	 */
	public static int selectWithName(String surname) throws SQLException {
		Connection connection = getDBConnection();
		PreparedStatement selectPreparedStatement = null;
		String SelectQuery =  ("SELECT * FROM PLAYER WHERE SURNAME = ?");
		int answer = 0;

		try {
			connection.setAutoCommit(false);

			selectPreparedStatement = connection.prepareStatement(SelectQuery);
			selectPreparedStatement.setString(1, surname);
			ResultSet rs = selectPreparedStatement.executeQuery();
			logger.info("H2 Database select through PreparedStatement");
			while (rs.next()) {
				logger.info("Select:" + SelectQuery);
				answer = rs.getInt("playerid");
			}
			selectPreparedStatement.close();

			connection.commit();
		} catch (SQLException e) {
			logger.warning("Exception Message " + e.getLocalizedMessage());
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			connection.close();
		}
		return answer;
	}
	
	/**
	 * becomes a whole statement for the Table Player
	 * 
	 * @param statement
	 * @return gives all back in the Table Player
	 * @throws SQLException
	 */
	public static ArrayList<String> selectPlayer() throws SQLException {
		Connection connection = getDBConnection();
		PreparedStatement selectPreparedStatement = null;
		String SelectQuery = "select * from PLAYER";
		ArrayList<String> answerList = new ArrayList<String>();

		try {
			connection.setAutoCommit(false);

			selectPreparedStatement = connection.prepareStatement(SelectQuery);
			ResultSet rs = selectPreparedStatement.executeQuery();
			logger.info("H2 Database selectPlayer through PreparedStatement");
			while (rs.next()) {
				logger.info("Select:" + SelectQuery);
				answerList.add(rs.getString("playerid").toString());
				answerList.add(rs.getString("username"));
				answerList.add(rs.getString("prename"));
				answerList.add(rs.getString("surname"));
				answerList.add(rs.getString("password"));
				answerList.add(rs.getString("wins").toString());
			}
			selectPreparedStatement.close();

			connection.commit();
		} catch (SQLException e) {
			logger.warning("Exception Message " + e.getLocalizedMessage());
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			connection.close();
		}
		return answerList;
	}
	
	/**
	 * becomes a whole statement for the Table Friends
	 * 
	 * @param statement
	 * @return gives all back in the Table Friends
	 * @throws SQLException
	 */
	public static ArrayList<String> selectFriends() throws SQLException {
		Connection connection = getDBConnection();
		PreparedStatement selectPreparedStatement = null;
		String SelectQuery = "select * from FRIENDSPOINTS";
		ArrayList<String> answerList = new ArrayList<String>();

		try {
			connection.setAutoCommit(false);

			selectPreparedStatement = connection.prepareStatement(SelectQuery);
			ResultSet rs = selectPreparedStatement.executeQuery();
			logger.info("H2 Database selectfriends through PreparedStatement");
			while (rs.next()) {
				logger.info("Select:" + SelectQuery);
				answerList.add(rs.getString("id").toString());
				answerList.add(rs.getString("friendId1").toString());
				answerList.add(rs.getString("friendId2").toString());
				answerList.add(rs.getString("request").toString());
			}
			selectPreparedStatement.close();

			connection.commit();
		} catch (SQLException e) {
			logger.warning("Exception Message " + e.getLocalizedMessage());
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			connection.close();
		}
		return answerList;
	}
	
	/**
	 * becomes a whole statement for the Table Tower
	 * 
	 * @param statement
	 * @return gives all back in the Table Tower
	 * @throws SQLException
	 */
	public static ArrayList<String> selectTower() throws SQLException {
		Connection connection = getDBConnection();
		PreparedStatement selectPreparedStatement = null;
		String SelectQuery = "select * from TOWER";
		ArrayList<String> answerList = new ArrayList<String>();

		try {
			connection.setAutoCommit(false);

			selectPreparedStatement = connection.prepareStatement(SelectQuery);
			ResultSet rs = selectPreparedStatement.executeQuery();
			logger.info("H2 Database selectTower through PreparedStatement");
			while (rs.next()) {
				logger.info("Select:" + SelectQuery);
				answerList.add(rs.getString("id").toString());
				answerList.add(rs.getString("normalTower"));
				answerList.add(rs.getString("summoTower"));
				answerList.add(rs.getString("playerid").toString());
			}
			selectPreparedStatement.close();

			connection.commit();
		} catch (SQLException e) {
			logger.warning("Exception Message " + e.getLocalizedMessage());
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			connection.close();
		}
		return answerList;
	}
	
	/**
	 * becomes a whole statement for the Table Points
	 * 
	 * @param statement
	 * @return gives all back in the Table Points
	 * @throws SQLException
	 */
	public static ArrayList<Integer> selectPoints() throws SQLException {
		Connection connection = getDBConnection();
		PreparedStatement selectPreparedStatement = null;
		String SelectQuery = "select * from POINTS";
		ArrayList<Integer> answerList = new ArrayList<Integer>();

		try {
			connection.setAutoCommit(false);

			selectPreparedStatement = connection.prepareStatement(SelectQuery);
			ResultSet rs = selectPreparedStatement.executeQuery();
			logger.info("H2 Database selectPoints through PreparedStatement");
			while (rs.next()) {
				logger.info("Select:" + SelectQuery);
				answerList.add(rs.getInt("id"));
				answerList.add(rs.getInt("points"));
				answerList.add(rs.getInt("playerid"));
			}
			selectPreparedStatement.close();

			connection.commit();
		} catch (SQLException e) {
			logger.warning("Exception Message " + e.getLocalizedMessage());
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			connection.close();
		}
		return answerList;
	}
	
	/**
	 * check is there an line in the DB
	 * 
	 * @param id
	 *            - the generate id from the model
	 * @return - yes or no
	 * @throws SQLException
	 */
	public static boolean isTheEntryThere(int id) throws SQLException {
		Connection connection = getDBConnection();
		PreparedStatement selectPreparedStatement = null;
		String SelectQuery = ("SELECT * FROM PLAYER WHERE ID = ?");

		try {
			connection.setAutoCommit(false);

			selectPreparedStatement = connection.prepareStatement(SelectQuery);
			selectPreparedStatement.setInt(1, id);
			ResultSet rs = selectPreparedStatement.executeQuery();
			String entry = null;
			while (rs.next()) {
				entry = rs.getString("prename");
			}
			if (entry != null) {
				logger.info("EntryThere");
				return true;
			}

			selectPreparedStatement.close();
			connection.commit();

		} catch (SQLException e) {
			logger.warning("Exception Message " + e.getLocalizedMessage());
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			connection.close();
		}
		logger.info("EntryNotThere");
		return false;
	}
	
	/**
	 * H2 SQL Prepared Statement Select for Wins
	 * 
	 * @param id
	 * @return send only the points
	 * @throws SQLException
	 */
	public static int selectPreparedStatementPoints(int id) throws SQLException {
		Connection connection = getDBConnection();
		PreparedStatement selectPreparedStatement = null;
		String SelectQuery = ("SELECT * FROM PLAYER WHERE ID = ?");
		int answer = 0;

		try {
			connection.setAutoCommit(false);

			selectPreparedStatement = connection.prepareStatement(SelectQuery);
			selectPreparedStatement.setInt(1, id);
			ResultSet rs = selectPreparedStatement.executeQuery();
			logger.info("H2 Database inserted through PreparedStatement");
			while (rs.next()) {
				logger.info(
						"Id " + rs.getInt("id") + " Name " + rs.getString("prename") + " " + rs.getString("surname") + "Points " + rs.getInt("win"));
				answer = rs.getInt("win");
			}
			selectPreparedStatement.close();

			connection.commit();

		} catch (SQLException e) {
			logger.warning("Exception Message " + e.getLocalizedMessage());
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			connection.close();
		}
		return answer;
	}
	
	
	/**
	 * For check are both friendrequest here?
	 * @param id
	 * @param friendId
	 * @return
	 * @throws SQLException
	 */
	public static boolean selectFriendsRequest(int id, int friendId) throws SQLException {
		Connection connection = getDBConnection();
		PreparedStatement selectPreparedStatement = null;
		String SelectQuery = ("SELECT * FROM FRIENDSPOINTS WHERE friendId1 = ? AND friendId2 = ?");
		int answer = 0;

		try {
			connection.setAutoCommit(false);

			selectPreparedStatement = connection.prepareStatement(SelectQuery);
			selectPreparedStatement.setInt(1, id);
			selectPreparedStatement.setInt(2, friendId);
			ResultSet rs = selectPreparedStatement.executeQuery();
			logger.info("H2 Database selected through PreparedStatement");
			int entry = 0;
			while (rs.next()) {
				entry = rs.getInt("friendId1");
			}
			if (entry != 0) {
				logger.info("EntryThere");
				return true;
			}
			
			selectPreparedStatement.close();

			connection.commit();

		} catch (SQLException e) {
			logger.warning("Exception Message " + e.getLocalizedMessage());
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			connection.close();
		}
		return false;
	}
	
	public static int selectLastIdFriends() throws SQLException {
		Connection connection = getDBConnection();
		PreparedStatement selectPreparedStatement = null;
		String SelectQuery = ("SELECT id FROM FRIENDSPOINTS ORDER BY id DESC LIMIT 1");
		int answer = 0;

		try {
			connection.setAutoCommit(false);

			selectPreparedStatement = connection.prepareStatement(SelectQuery);
			ResultSet rs = selectPreparedStatement.executeQuery();
			logger.info("H2 Database selected through PreparedStatement");
			int entry = 0;
			while (rs.next()) {
				entry = rs.getInt("id");
			}
			return entry;

		} catch (SQLException e) {
			logger.warning("Exception Message " + e.getLocalizedMessage());
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			connection.close();
		}
		return 0;
	}
	

	/*********************************************************************************************
	 * Update Statements
	 * @author L.Weber
	 *********************************************************************************************/
	
	/**
	 * update the entry in the DB
	 * 
	 * @param update
	 *            - input the whole SQL-Query
	 * @throws SQLException
	 */
	public static void update(String update) throws SQLException {
		Connection connection = getDBConnection();
		PreparedStatement updatePreparedStatement = null;

		try {
			connection.setAutoCommit(false);

			updatePreparedStatement = connection.prepareStatement(update);
			updatePreparedStatement.executeUpdate();
			connection.commit();

		} catch (SQLException e) {
			logger.warning("Exception Message " + e.getLocalizedMessage());
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			connection.close();
		}
	}
	
	/**
	 * For Update the win with an id
	 * @param win
	 * @param id
	 * @throws SQLException
	 */
	public static void updatePreparedStatementWithId(int win, int id) throws SQLException {
		Connection connection = getDBConnection();
		PreparedStatement updatePreparedStatement = null;
		String UpdateQuery = ("UPDATE PLAYER SET WINS = ? WHERE PLAYERID = ?");

		try {
			connection.setAutoCommit(false);
			updatePreparedStatement = connection.prepareStatement(UpdateQuery);
			updatePreparedStatement.setInt(1, win);
			updatePreparedStatement.setInt(2, id);
			updatePreparedStatement.executeUpdate();
			logger.info("H2 Database updated through PreparedStatement");
		
			updatePreparedStatement.close();

			connection.commit();

		} catch (SQLException e) {
			logger.warning("Exception Message " + e.getLocalizedMessage());
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			connection.close();
		}
	}
	
	/**
	 * Update the PreName
	 * @param value
	 * @param id
	 * @throws SQLException
	 */
	public static void updatePreparedStatementPreName(String value, int id) throws SQLException {
		Connection connection = getDBConnection();
		PreparedStatement updatePreparedStatement = null;
		String UpdateQuery = ("UPDATE PLAYER SET PRENAME = ? WHERE PLAYERID = ?");

		try {
			connection.setAutoCommit(false);
			updatePreparedStatement = connection.prepareStatement(UpdateQuery);
			updatePreparedStatement.setString(1, value);
			updatePreparedStatement.setInt(2, id);
			updatePreparedStatement.executeUpdate();
			logger.info("H2 Database updated through PreparedStatement");
		
			updatePreparedStatement.close();

			connection.commit();

		} catch (SQLException e) {
			logger.warning("Exception Message " + e.getLocalizedMessage());
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			connection.close();
		}
	}
	
	/**
	 * Update the SurName
	 * @param value
	 * @param id
	 * @throws SQLException
	 */
	public static void updatePreparedStatementSurName(String value, int id) throws SQLException {
		Connection connection = getDBConnection();
		PreparedStatement updatePreparedStatement = null;
		String UpdateQuery = ("UPDATE PLAYER SET SURNAME = ? WHERE PLAYERID = ?");

		try {
			connection.setAutoCommit(false);
			updatePreparedStatement = connection.prepareStatement(UpdateQuery);
			updatePreparedStatement.setString(1, value);
			updatePreparedStatement.setInt(2, id);
			updatePreparedStatement.executeUpdate();
			logger.info("H2 Database updated through PreparedStatement");
		
			updatePreparedStatement.close();

			connection.commit();

		} catch (SQLException e) {
			logger.warning("Exception Message " + e.getLocalizedMessage());
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			connection.close();
		}
	}
	
	/**
	 * Update the UserName
	 * @param value
	 * @param id
	 * @throws SQLException
	 */
	public static void updatePreparedStatementUserName(String value, int id) throws SQLException {
		Connection connection = getDBConnection();
		PreparedStatement updatePreparedStatement = null;
		String UpdateQuery = ("UPDATE PLAYER SET USERNAME = ? WHERE PLAYERID = ?");

		try {
			connection.setAutoCommit(false);
			updatePreparedStatement = connection.prepareStatement(UpdateQuery);
			updatePreparedStatement.setString(1, value);
			updatePreparedStatement.setInt(2, id);
			updatePreparedStatement.executeUpdate();
			logger.info("H2 Database updated through PreparedStatement");
		
			updatePreparedStatement.close();

			connection.commit();

		} catch (SQLException e) {
			logger.warning("Exception Message " + e.getLocalizedMessage());
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			connection.close();
		}
	}
	
	/**
	 * Update the Password
	 * @param value
	 * @param id
	 * @throws SQLException
	 */
	public static void updatePreparedStatementPassword(String value, int id) throws SQLException {
		Connection connection = getDBConnection();
		PreparedStatement updatePreparedStatement = null;
		String UpdateQuery = ("UPDATE PLAYER SET PASSWORD = ? WHERE PLAYERID = ?");

		try {
			connection.setAutoCommit(false);
			updatePreparedStatement = connection.prepareStatement(UpdateQuery);
			updatePreparedStatement.setString(1, value);
			updatePreparedStatement.setInt(2, id);
			updatePreparedStatement.executeUpdate();
			logger.info("H2 Database updated through PreparedStatement");
		
			updatePreparedStatement.close();

			connection.commit();

		} catch (SQLException e) {
			logger.warning("Exception Message " + e.getLocalizedMessage());
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			connection.close();
		}
	}
	
	/**
	 * Set the request in Friends true when both will the request
	 * @param id
	 * @throws SQLException
	 */
	public static void updateFriendsRequest(int id) throws SQLException {
		Connection connection = getDBConnection();
		PreparedStatement updatePreparedStatement = null;
		String UpdateQuery = ("UPDATE FRIENDSPOINTS SET request = true WHERE friendId1 = ?");

		try {
			connection.setAutoCommit(false);
			updatePreparedStatement = connection.prepareStatement(UpdateQuery);
			updatePreparedStatement.setInt(1, id);
			updatePreparedStatement.executeUpdate();
			logger.info("H2 Database updated through PreparedStatement");
		
			updatePreparedStatement.close();

			connection.commit();

		} catch (SQLException e) {
			logger.warning("Exception Message " + e.getLocalizedMessage());
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			connection.close();
		}
	}
	
	/*********************************************************************************************
	 * Insert Statements
	 * @author L.Weber
	 *********************************************************************************************/
	
	/**
	 * insert a new line in the DB for the Table Player
	 * 
	 * @param id
	 *            - generate id from the model
	 * @param name
	 *            - name that given
	 * @param points
	 *            - points that given
	 * @throws SQLException
	 */
	public static void insertPlayer(int id,String username, String prename, String surname, String password) throws SQLException {
		Connection connection = getDBConnection();
		PreparedStatement insertPreparedStatement = null;
		String InsertQuery = "INSERT INTO PLAYER" + "(playerid, username, prename, surname, password) values" + "(?,?,?,?,?)";
		int win = 0;
		
		try {
			connection.setAutoCommit(false);

			insertPreparedStatement = connection.prepareStatement(InsertQuery);
			insertPreparedStatement.setInt(1, id);
			insertPreparedStatement.setString(2, username);
			insertPreparedStatement.setString(3, prename);
			insertPreparedStatement.setString(4, surname);
			insertPreparedStatement.setString(5, password);
					

			logger.info("Insert: " + id + " "+ username + " " + prename + " " + surname + " " + "***" + " " + win);
			insertPreparedStatement.executeUpdate();
			insertPreparedStatement.close();

			connection.commit();
		} catch (SQLException e) {
			logger.warning("Exception Message " + e.getLocalizedMessage());
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			connection.close();
		}
		updatePreparedStatementWithId(0, id);
	}
	
	/**
	 * For make new Friends
	 * @param id
	 * @param idfriend
	 * @throws SQLException
	 */
	public static void insertFriend(int id,int idfriend) throws SQLException {
		Connection connection = getDBConnection();
		PreparedStatement insertPreparedStatement = null;
		String InsertQuery = "INSERT INTO FRIENDSPOINTS" + "(id, friendId1, friendId2, request) values" + "(?,?,?,?)";
		
		int uniqueid = (selectLastIdFriends()+1);
		boolean request = false;
		try {
			connection.setAutoCommit(false);

			insertPreparedStatement = connection.prepareStatement(InsertQuery);
			insertPreparedStatement.setInt(1, uniqueid);
			insertPreparedStatement.setInt(2, id);
			insertPreparedStatement.setInt(3, idfriend);
			insertPreparedStatement.setBoolean(4, request);
					
			logger.info("Insert: " + uniqueid + " "+ id + " "+ idfriend + " " + request);
			insertPreparedStatement.executeUpdate();
			insertPreparedStatement.close();

			connection.commit();
		} catch (SQLException e) {
			logger.warning("Exception Message " + e.getLocalizedMessage());
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			connection.close();
		}
		//check is there the same entry with changed numbers
		//if yes: make request to true
		if(areBothRequestHere(id,idfriend)) {
			updateFriendsRequest(id);
			updateFriendsRequest(idfriend);
		} else {
			logger.info("Noth both Player has requested");
		}
	}
	
	/*********************************************************************************************
	 * Prepare Statements
	 * @author L.Weber
	 *********************************************************************************************/
	
	/**
	 * delete the DB and sets up a new table
	 */
	public static void deleteDB() {
		// delete the H2 database named 'test' in the user home directory
		DeleteDbFiles.execute("~", "WindChuckers", true);
		try {
			createDB();
			insertPlayer(1,"admin","admin","admin","admin");
			insertPlayer(2,"testy","prename", "surname", "test");
			insertPlayer(3,"noname", "sowhat", "nonsens", "none");
			
			insertFriend(1,2);
			insertFriend(2,1);
			insertFriend(3,1);
			insertFriend(3,2);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}	
	
	/**
	 * delete the single Entry in the DB
	 * @throws SQLException 
	 */
	public static void deletePlayer(int id) throws SQLException {
		Connection connection = getDBConnection();
		PreparedStatement deletePreparedStatement = null;
		String deleteDBPlayer = ("DELETE FROM PLAYER WHERE PLAYERID = ?");
		
		try {
			connection.setAutoCommit(false);
			
			deletePreparedStatement = connection.prepareStatement(deleteDBPlayer);
			deletePreparedStatement.setInt(1, id);
			logger.info("Delete id " + id);
			deletePreparedStatement.executeUpdate();
			deletePreparedStatement.close();
			connection.commit();
			
		} catch (SQLException e) {
			logger.warning("Exception Message " + e.getLocalizedMessage());
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			insertPlayer(id,"deleted","deleted","deleted","deleted");
			connection.close();
		}
	}
	
	/**
	 * delete the single Entry in the DB for Friends
	 * @throws SQLException 
	 */
	public static void deleteFriend(int friendId) throws SQLException {
		Connection connection = getDBConnection();
		PreparedStatement deletePreparedStatement = null;
		String deleteDBPlayer = ("DELETE FROM FRIENDSPOINTS WHERE friendId2 = ?");
		
		try {
			connection.setAutoCommit(false);
			
			deletePreparedStatement = connection.prepareStatement(deleteDBPlayer);
			deletePreparedStatement.setInt(1, friendId);
			logger.info("Delete Friend with Id: " + friendId);
			deletePreparedStatement.executeUpdate();
			deletePreparedStatement.close();
			connection.commit();
			
		} catch (SQLException e) {
			logger.warning("Exception Message " + e.getLocalizedMessage());
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			connection.close();
		}
	}
	

	/**
	 * generate a new Table
	 * 
	 * @throws SQLException
	 */
	public static void createDB() throws SQLException {
		Connection connection = getDBConnection();
		PreparedStatement createPreparedStatementPLAYER = null;
		PreparedStatement createPreparedStatementFRIENDS = null;
		PreparedStatement createPreparedStatementTOWER = null;
		PreparedStatement createPreparedStatementPOINTS = null;
		PreparedStatement createPreparedStatementFRIENDSPLAYER= null;
		PreparedStatement alterTower=null;
		PreparedStatement alterPoints=null;
		PreparedStatement alterFriendPlayer1=null;
		PreparedStatement alterFriendPlayer2=null;
		
		
		String CreateQueryPlayer = "CREATE TABLE PLAYER("
				+ "playerid int primary key, "
				+ "username varchar(255),"
				+ "prename varchar(255), "
				+ "surname varchar(255), "
				+ "password varchar(255),"
				+ "wins int)";
		String CreateQueryTower = "CREATE TABLE TOWER("
				+ "id int primary key, "
				+ "normalTower int, "
				+ "summoTower int, "
				+ "playerid int)";
		String CreateQueryPoints = "CREATE TABLE POINTS("
				+ "id int primary key, "
				+ "points int, "
				+ "playerid int)";
		String CreateQueryFriendPlayer = "CREATE TABLE FRIENDSPOINTS("
				+ "id int primary key, "
				+ "friendId1 int, "
				+ "friendId2 int, "
				+ "request boolean)";
		
		String AlterTower = "ALTER TABLE TOWER"
				+ " ADD FOREIGN KEY (playerid) REFERENCES PLAYER (playerid)";
		
		String AlterPoints = "ALTER TABLE POINTS"
				+ " ADD FOREIGN KEY (playerId) REFERENCES PLAYER (playerid)";
		
		String AlterFriendPlayer1 = "ALTER TABLE FRIENDSPOINTS"
				+ " ADD FOREIGN KEY (friendId1) REFERENCES PLAYER (playerid)";
		
		String AlterFriendPlayer2 = "ALTER TABLE FRIENDSPOINTS"
				+ " ADD FOREIGN KEY (friendId2) REFERENCES PLAYER (playerid)";

		try {
			connection.setAutoCommit(false);

			createPreparedStatementPLAYER = connection.prepareStatement(CreateQueryPlayer);
			createPreparedStatementPLAYER.executeUpdate();
			createPreparedStatementPLAYER.close();
			createPreparedStatementTOWER = connection.prepareStatement(CreateQueryTower);
			createPreparedStatementTOWER.executeUpdate();
			createPreparedStatementTOWER.close();
			createPreparedStatementPOINTS = connection.prepareStatement(CreateQueryPoints);
			createPreparedStatementPOINTS.executeUpdate();
			createPreparedStatementFRIENDSPLAYER = connection.prepareStatement(CreateQueryFriendPlayer);
			createPreparedStatementFRIENDSPLAYER.executeUpdate();
			createPreparedStatementFRIENDSPLAYER.close();
			alterTower = connection.prepareStatement(AlterTower);
			alterTower.executeUpdate();
			alterTower.close();
			alterPoints = connection.prepareStatement(AlterPoints);
			alterPoints.executeUpdate();
			alterPoints.close();
			alterFriendPlayer1 = connection.prepareStatement(AlterFriendPlayer1);
			alterFriendPlayer1.executeUpdate();
			alterFriendPlayer1.close();
			alterFriendPlayer2 = connection.prepareStatement(AlterFriendPlayer2);
			alterFriendPlayer2.executeUpdate();
			alterFriendPlayer2.close();

			connection.commit();
		} catch (SQLException e) {
			logger.warning("Exception Message " + e.getLocalizedMessage());
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			connection.close();
		}

	}
	
	private static Connection getDBConnection() {
		Connection dbConnection = null;
		try {
			Class.forName(DB_DRIVER);
		} catch (ClassNotFoundException e) {
			logger.warning(e.getMessage());
		}
		try {
			dbConnection = DriverManager.getConnection(DB_CONNECTION, DB_USER, DB_PASSWORD);
			return dbConnection;
		} catch (SQLException e) {
			logger.warning(e.getMessage());
		}
		return dbConnection;
	}
	
	private static boolean areBothRequestHere(int id, int friendId) {
		boolean req1 = false;
		boolean req2 = false;
		try {
			req1 = selectFriendsRequest(id, friendId);
			req2 = selectFriendsRequest(friendId, id);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		if(req1 && req2) {
			return true;
		} else {
			return false;
		}
	}

}