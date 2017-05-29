package Login;

import java.util.ArrayList;

import abstractClasses.Model;
import commonClasses.ServiceLocator;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.value.ObservableStringValue;
import javafx.concurrent.Task;

public class LoginModel extends Model {
	ServiceLocator serviceLocator;
	final String realPassword = "test";

	
	public LoginModel(){
		super();
	}

	//TODO real password
	public boolean passwordCheck(String text) {
		if(text.equals(realPassword)){
			return true;
		} else {
			return false;
		}
		
	}

	public boolean checkUserList(String user, String name) {
		if(user.equals(name)){
			return true;
		}
		return false;
	}



}


