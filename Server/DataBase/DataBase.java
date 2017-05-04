package DataBase;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Logger;

import org.h2.tools.DeleteDbFiles;

public class DataBase {

	private static Logger logger = Logger.getLogger("");

	private static DataBase h2;

	private static final String DB_DRIVER = "org.h2.Driver";
	private static final String DB_CONNECTION = "jdbc:h2:~/WindChuckers";
	private static final String DB_USER = "";
	private static final String DB_PASSWORD = "";

	// only for test the DB and show the tables
	public static void main(String[] args) throws Exception {
//		deleteDB();
//		createDB();
//		ArrayList<String> answer = selectPlayer();
//		for (String s : answer){
//			System.out.println(s);
//		}
		
//		insertPlayer(1,"lukas","weber",1);
//		isTheEntryThere(1);
//		update("UPDATE PLAYER SET WINS = 2 WHERE PLAYERID = 1");
		System.out.println(select("select * from player where playerid = 1"));
		
	}

	public DataBase() {
	}

	/**
	 * Factory method for returning the singleton board
	 * 
	 * @param mainClass
	 *            The main class of this program
	 * @return The singleton resource locator
	 */
	public static DataBase getDB() {
		if (h2 == null)
			h2 = new DataBase();
		return h2;
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
	public static int select(String input) throws SQLException {
		Connection connection = getDBConnection();
		PreparedStatement selectPreparedStatement = null;
		String SelectQuery = input;
		int answer = 0;

		try {
			connection.setAutoCommit(false);

			selectPreparedStatement = connection.prepareStatement(SelectQuery);
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
				answerList.add(rs.getString("prename"));
				answerList.add(rs.getString("surname"));
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
		String SelectQuery = "select * from FRIENDS";
		ArrayList<String> answerList = new ArrayList<String>();

		try {
			connection.setAutoCommit(false);

			selectPreparedStatement = connection.prepareStatement(SelectQuery);
			ResultSet rs = selectPreparedStatement.executeQuery();
			logger.info("H2 Database selectfriends through PreparedStatement");
			while (rs.next()) {
				logger.info("Select:" + SelectQuery);
				answerList.add(rs.getString("id").toString());
				answerList.add(rs.getString("prename"));
				answerList.add(rs.getString("surname"));
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
	public static void insertPlayer(int id, String prename, String surname, int win) throws SQLException {
		Connection connection = getDBConnection();
		PreparedStatement insertPreparedStatement = null;
		String InsertQuery = "INSERT INTO PLAYER" + "(playerid, prename, surname, wins) values" + "(?,?,?,?)";

		try {
			connection.setAutoCommit(false);

			insertPreparedStatement = connection.prepareStatement(InsertQuery);
			insertPreparedStatement.setInt(1, id);
			insertPreparedStatement.setString(2, prename);
			insertPreparedStatement.setString(3, surname);
			insertPreparedStatement.setInt(4, win);

			logger.info("Insert: " + id + " " + prename + " " + surname + " " + win);
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
			insertPlayer(1, "default", "muster", 00);
		} catch (SQLException e) {
			e.printStackTrace();
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
				+ "prename varchar(255), "
				+ "surname varchar(255), "
				+ "wins int)";
		String CreateQueryFriends = "CREATE TABLE FRIENDS("
				+ "id int primary key, "
				+ "prename varchar(255), "
				+ "surname varchar(255))";
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
				+ " ADD FOREIGN KEY (friendId1) REFERENCES FRIENDS (id)";
		
		String AlterFriendPlayer2 = "ALTER TABLE FRIENDSPOINTS"
				+ " ADD FOREIGN KEY (friendId2) REFERENCES FRIENDS (id)";

		try {
			connection.setAutoCommit(false);

			createPreparedStatementPLAYER = connection.prepareStatement(CreateQueryPlayer);
			createPreparedStatementPLAYER.executeUpdate();
			createPreparedStatementPLAYER.close();
			createPreparedStatementFRIENDS = connection.prepareStatement(CreateQueryFriends);
			createPreparedStatementFRIENDS.executeUpdate();
			createPreparedStatementFRIENDS.close();
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

}
