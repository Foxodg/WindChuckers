package Login;

import java.util.ArrayList;

import abstractClasses.Model;
import commonClasses.ServiceLocator;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.value.ObservableStringValue;
import javafx.concurrent.Task;

public class LoginModel extends Model {
	private ServiceLocator serviceLocator;
	private String realPassword;
	private static String surname;
	private static int wins;
	private static String username;
	private static boolean withoutServer = false;
	private static boolean forcePlayer;

	
	public LoginModel(){
		super();
	}

	public boolean passwordCheck(String text) {
		if(text.equals(realPassword)){
			return true;
		} else {
			return false;
		}
		
	}
	
	public void setPassword(String password) {
		this.realPassword = password;
	}
	
	public static void setSurname(String name){
		surname = name;
	}
	
	public static String getSurname(){
		return surname;
	}
	
	public static void setWins(int win){
		wins = win;
	}
	
	public static int getWins(){
		return wins;
	}
	
	public static void setUserName(String user) {
		username = user;
	}
	
	public static String getUserName() {
		return username;
	}
	
	public static boolean getWithoutServer() {
		return withoutServer;
	}
	
	public static void setWithoutServer(boolean without) {
		withoutServer = without;
	}
	
	public static void setForcePlayer(boolean force) {
		forcePlayer = force;
	}
	
	public static boolean getForcePlayer() {
		return forcePlayer;
	}
}


