package model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class User {

	private String name;
	private String password;
	private ArrayList<String> myFiles;
	private HashMap<String, ArrayList<String>> fileAccess;

	public User(String name, String password) {
		this.name = name;
		this.password = password;
		this.myFiles = new ArrayList<String>();
		this.fileAccess = new HashMap<String, ArrayList<String>>();
	}

	public ArrayList<String> getFiles() {
		return myFiles;
	}

	public void addFile(String fileName) {
		this.myFiles.add(fileName);
	}

	public HashMap<String, ArrayList<String>> getFilesAccess() {
		return fileAccess;
	}

	public void addFileToFileAccess(String fromUser, String fileName) {
		ArrayList<String> anotlist = new ArrayList<String>();
		for (Map.Entry<String, ArrayList<String>> entry : getFilesAccess().entrySet()) {
			if (entry.getKey().equals(fromUser)) {
				for (int i = 0; i < entry.getValue().size(); i++) {
					if (!entry.getValue().get(i).equals(fileName))
						anotlist.add(entry.getValue().get(i));
				}

			}
		}
		anotlist.add(fileName);
		this.fileAccess.remove(fromUser);
		this.fileAccess.put(fromUser, anotlist);

	}

	public void setName(String name) {
		this.name = name;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getName() {
		return name;
	}

	public String getPassword() {
		return password;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(name).append(" ").append(password).append("\r\n");
		return sb.toString();
	}

	public boolean containsFileName(String fileName) {
		if (myFiles.contains(fileName))
			return true;
		return false;
	}

	public synchronized void deleteFile(String fileName) {
		myFiles.removeIf(note -> note.equals(fileName));
	}

	public boolean containsAccessFile(String fileName) {
		if (this.fileAccess.containsValue(fileName))
			return true;
		return false;
	}

	public synchronized void deleteFileAccess(String fileName) {
		for (Map.Entry<String, ArrayList<String>> entry : fileAccess.entrySet()) {
			ArrayList<String> anotlist = new ArrayList<String>();
			for (int i = 0; i < entry.getValue().size(); i++) {
				if (!entry.getValue().get(i).equals(fileName))
					anotlist.add(entry.getValue().get(i));
			}
			this.fileAccess.replace(entry.getKey(), anotlist);
		}
	}

}
