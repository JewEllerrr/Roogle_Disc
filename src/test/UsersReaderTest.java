package test;

import java.io.IOException;
import java.util.ArrayList;

import org.junit.Assert;
import org.junit.Test;

import model.Brain;
import model.User;
import model.UsersReader;

public class UsersReaderTest {
	
	private ArrayList<User> users;
	private UsersReader ur;
	private Brain main;	
	
	@Test
	public void readFile() throws IOException {
		main = new Brain();	
		String liza = "dsfsdg";
		
		ur = new UsersReader("C://Users//lizfr//eclipse-workspace//RoogleDisc//UserBase",
				"C://Users//lizfr//eclipse-workspace//RoogleDisc//UserFileAccess");	
		users = ur.readFile();
		
		Assert.assertTrue(true);
	}
}
