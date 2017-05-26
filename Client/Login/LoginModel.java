package Login;

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


	public boolean passwordCheck(String text) {
		if(text.equals(realPassword)){
			return true;
		} else {
			return false;
		}
		
	}


}


