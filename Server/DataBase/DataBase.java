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
		// deleteDB();
		// selectPreparedStatement("select * from PLAYER");
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
	 *********************************************************************************************/

	/**
	 * becomes a whole statement for the Table Player
	 * 
	 * @param statement
	 * @return gives all back in the Table Player
	 * @throws SQLException
	 */
	public static ArrayList<String> selectPlayer(String statement) throws SQLException {
		Connection connection = getDBConnection();
		PreparedStatement selectPreparedStatement = null;
		String SelectQuery = statement;
		ArrayList<String> answerList = new ArrayList<String>();

		try {
			connection.setAutoCommit(false);

			selectPreparedStatement = connection.prepareStatement(SelectQuery);
			ResultSet rs = selectPreparedStatement.executeQuery();
			logger.info("H2 Database select through PreparedStatement");
			while (rs.next()) {
				logger.info("Select:" + statement);
				answerList.add(rs.getString("id").toString());
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
	 * check is there an line in the DB
	 * 
	 * @param id
	 *            - the generate id from the model
	 * @return - yes or no
	 * @throws SQLException
	 */
	public boolean isTheEntryThere(int id) throws SQLException {
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
				entry = rs.getString("name");
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
	 *********************************************************************************************/
	
	/**
	 * update the entry in the DB
	 * 
	 * @param update
	 *            - input the whole SQL-Query
	 * @throws SQLException
	 */
	public void updateWithPreparedStatement(String update) throws SQLException {
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
	public static void insertWithPreparedStatement(int id, String prename, String surname, int win) throws SQLException {
		Connection connection = getDBConnection();
		PreparedStatement insertPreparedStatement = null;
		String InsertQuery = "INSERT INTO PLAYER" + "(id, prename, surname, wins) values" + "(?,?,?,?)";

		try {
			connection.setAutoCommit(false);

			insertPreparedStatement = connection.prepareStatement(InsertQuery);
			insertPreparedStatement.setInt(1, id);
			insertPreparedStatement.setString(2, prename);
			insertPreparedStatement.setString(3, surname);
			insertPreparedStatement.setInt(3, win);

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
	 *********************************************************************************************/
	
	/**
	 * delete the DB and sets up a new table
	 */
	public static void deleteDB() {
		// delete the H2 database named 'test' in the user home directory
		DeleteDbFiles.execute("~", "WindChuckers", true);
		try {
			createTableWithPreparedStatement();
			insertWithPreparedStatement(1, "default", "muster", 00);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}	
	
	/**
	 * generate a new Table
	 * 
	 * @throws SQLException
	 */
	public static void createTableWithPreparedStatement() throws SQLException {
		Connection connection = getDBConnection();
		PreparedStatement createPreparedStatement = null;
		String CreateQueryPlayer = "CREATE TABLE PLAYER(id int primary key, prename varchar(255), surname varchar(255), wins int)";
		String CreateQueryFriends = "CREATE TABLE FRIENDS(id int primary key, prename varchar(255), surname varchar(255)";
		String CreateQueryTower = "CREATE TABLE TOWER(id int primary key, normalTower int, summoTower int, playerid int foreign key)";
		String CreateQueryPoints = "CREATE TABLE POINTS(id int primary key, points int, playerId int foreign key)";
		String CreateQueryFriendPlayer = "CREATE TABLE POINTS(id int primary key, friendId1 int foreign key, friendId2 int foreign key, request boolean)";

		try {
			connection.setAutoCommit(false);

			createPreparedStatement = connection.prepareStatement(CreateQueryPlayer);
			createPreparedStatement.executeUpdate();
			createPreparedStatement = connection.prepareStatement(CreateQueryFriends);
			createPreparedStatement.executeUpdate();
			createPreparedStatement = connection.prepareStatement(CreateQueryTower);
			createPreparedStatement.executeUpdate();
			createPreparedStatement = connection.prepareStatement(CreateQueryPoints);
			createPreparedStatement.executeUpdate();
			createPreparedStatement = connection.prepareStatement(CreateQueryFriendPlayer);
			createPreparedStatement.executeUpdate();
			createPreparedStatement.close();

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
