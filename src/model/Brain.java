package model;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;

public class Brain {
	private static ArrayList<User> users = null;
	private static final UsersReader reader = new UsersReader("C://Users//lizfr//eclipse-workspace//RoogleDisc//UserBase",
			"C://Users//lizfr//eclipse-workspace//RoogleDisc//UserFileAccess");
	private static ArrayList<String> olusers = null;

	public Brain() {
		try {
			users = reader.readFile();
			User sublist = null;
			for (int i = 0; i < users.size(); i++) {
				sublist = users.get(i);
				for (Map.Entry<String, ArrayList<String>> pair : sublist.getFilesAccess().entrySet()) {
					String key = pair.getKey();
					for (int j = 0; j < pair.getValue().size(); j++) {
						ArrayList<String> anotherlist = pair.getValue();
						String value = anotherlist.get(j);
					}
				}
			}
			olusers = new ArrayList<String>();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static boolean isItCorrectPassword(String name, String password) {
		for (int i = 0; i < users.size(); i++) {
			User u = users.get(i);
			if (name.equals(u.getName()) && password.equals(u.getPassword())) {
				return true;
			}
		}
		return false;
	}

	public static void reWriteFile() throws IOException {
		String file = reader.getPath();
		BufferedWriter z = new BufferedWriter(new FileWriter(file, false));
		User sublist = null;
		for (int i = 0; i < users.size(); i++) {
			sublist = users.get(i);
			z.write(sublist.getName());
			z.write(" " + sublist.getPassword());
			for (int j = 0; j < sublist.getFiles().size(); j++) {
				z.write(" " + sublist.getFiles().get(j));
			}
			z.write("\n");
		}
		z.close();
	}

	public static void reWriteFile(String fileName) throws IOException {
		String file = reader.getPath();
		BufferedWriter z = new BufferedWriter(new FileWriter(file, false));
		User sublist = null;
		for (int i = 0; i < users.size(); i++) {
			sublist = users.get(i);
			z.write(sublist.getName());
			z.write(" " + sublist.getPassword());
			for (int j = 0; j < sublist.getFiles().size(); j++) {
				z.write(" " + sublist.getFiles().get(j));
			}
			z.write("\n");
		}
		z.close();
		File f = new File(reader.getDataPath() + fileName);
		if (f.delete()) {
			System.out.println("deleted " + fileName);
		} else
			System.out.println("not found");
	}

	public static void reWriteFileAccess() throws IOException {
		String file = reader.getAccess();
		BufferedWriter z = new BufferedWriter(new FileWriter(file, false));
		User sublist = null;
		for (int i = 0; i < users.size(); i++) {
			sublist = users.get(i);
			z.write(sublist.getName());
			for (Map.Entry<String, ArrayList<String>> pair : sublist.getFilesAccess().entrySet()) {
				String key = pair.getKey();
				for (int j = 0; j < pair.getValue().size(); j++) {
					ArrayList<String> anotherlist = pair.getValue();
					String value = anotherlist.get(j);
					z.write(" " + key);
					z.write(" " + value);
				}
			}
			z.write("\n");
		}
		z.close();
	}

	public static void reWriteFileAccess(String name) throws IOException {
		String file = reader.getAccess();
		try {
			FileWriter writer = new FileWriter(file, true);
			BufferedWriter bufferWriter = new BufferedWriter(writer);
			bufferWriter.write(name);
			bufferWriter.close();
		} catch (IOException e) {
			System.out.println(e);
		}
	}

	public static ArrayList<String> getOlusers() {
		return olusers;
	}

	public void setOlusers(ArrayList<String> olusers) {
		Brain.olusers = olusers;
	}

	public static void removeNotOnlineUsers(String user) {
		if (user != null && olusers.contains(user)) {
			olusers.removeIf(note -> note.equals(user));
		}
	}

	public static ArrayList<User> getUsers() {
		return users;
	}

	public static void setUsers(ArrayList<User> users) {
		Brain.users = users;
	}

}
