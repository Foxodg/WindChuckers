package TestClasses;

import static org.junit.Assert.*;

import org.junit.Test;

import Login.LoginModel;

public class LoginModelTest {

	@Test
	public void testPassword() {
		LoginModel model = new LoginModel();
		String password = "test";
		assertEquals("Test the Password", true, model.passwordCheck("test"));
	}

}
