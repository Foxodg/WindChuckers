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
	
	public void setPassword(String password) {
		this.realPassword = password;
	}
}


