package test;

import java.util.ArrayList;

import org.junit.Assert;
import org.junit.Test;

import model.Brain;

public class BrainTest {
	
	private Brain main;
	
	@Test
	public void isItCorrectPassword() {
		main = new Brain();	
		String name = "liza";
		String password = "123";
		boolean result = main.isItCorrectPassword(name, password);
		Assert.assertTrue(true);
		for (int i = 0; i < Brain.getUsers().size(); i++) {
			if (name.equals(Brain.getUsers().get(i).getName())
					&& Brain.getUsers().get(i).containsFileName(password)) {
				Brain.getUsers().get(i).deleteFile(password);
				break;
			}
		}
		
	}
	
	@Test
	public void removeNotOnlineUsers() {
		main = new Brain();	
		String user  = "liza";
		ArrayList<String> tmp = main.getOlusers();
		main.removeNotOnlineUsers(user);
		tmp = main.getOlusers();
	}

}
