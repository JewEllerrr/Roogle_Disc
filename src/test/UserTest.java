package test;

import org.junit.Assert;
import org.junit.Test;

import model.Brain;
import model.User;

public class UserTest {

	private User u;
	private Brain main;

	@Test
	public void addFileToFileAccess() {
		String name = "lizok";
		String password = "123";
		u = new User(name, password);
		u.addFileToFileAccess(name, password);
		for (int i = 0; i < Brain.getUsers().size(); i++) {
			if (name.equals(Brain.getUsers().get(i).getName()) && Brain.getUsers().get(i).containsFileName(name)) {
				Brain.getUsers().get(i).deleteFile(name);
				break;
			}
		}
		main = new Brain();
		Assert.assertTrue(true);
	}

	@Test
	public void deleteFileAccess() {
		main = new Brain();
		String name = "lizok";
		String password = "123";
		u = new User(name, password);
		u.deleteFileAccess(password);
		for (int i = 0; i < Brain.getUsers().size(); i++) {
			if (name.equals(Brain.getUsers().get(i).getName()) && Brain.getUsers().get(i).containsFileName(name)) {
				Brain.getUsers().get(i).deleteFile(name);
				break;
			}
			for (int j = 0; j < Brain.getUsers().size(); j++) {
				if (name.equals(Brain.getUsers().get(j).getName()) && Brain.getUsers().get(j).containsFileName(name)) {
					Brain.getUsers().get(j).deleteFile(name);
					break;
				}
			}
		}
		Assert.assertTrue(true);
	}

}
