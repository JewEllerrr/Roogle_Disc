package model;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class UsersReader {

	private BufferedReader br;
	private String path;
	private String access;
	private String dataPath;

	public UsersReader(String file, String access) {
		this.setPath(file);
		this.setAccess(access);
		this.setDataPath("C://Users//lizfr//eclipse-workspace//RoogleDisc//WebContent//RoogleDiscDownloads//");
	}

	public ArrayList<User> readFile() throws IOException {
		open(getPath());
		String s = "";
		ArrayList<User> res = new ArrayList<User>();
		while ((s = br.readLine()) != null) {
			String[] ss = s.split(" ");
			res.add(new User(ss[0], ss[1]));
			for (int i = 2; i < ss.length; i++) {
				res.get(res.size() - 1).addFile(ss[i]);
			}
		}
		close(getPath());

		open(getAccess());

		User sublist = null;

		while ((s = br.readLine()) != null) {
			String[] ss = s.split(" ");
			for (int i = 0; i < res.size(); i++) {
				sublist = res.get(i);
				if (sublist.getName().equals(ss[0])) {
					for (int j = 1; j < ss.length;) {
						res.get(i).addFileToFileAccess(ss[0 + j], ss[1 + j]);
						j += 2;
					}
				}
			}
		}
		close(getAccess());
		return res;
	}

	public void close(String p) throws IOException {
		br.close();
	}

	public void open(String p) throws FileNotFoundException {
		br = new BufferedReader(new InputStreamReader(new FileInputStream(p)));
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public String getDataPath() {
		return dataPath;
	}

	public void setDataPath(String dataPath) {
		this.dataPath = dataPath;
	}

	public String getAccess() {
		return access;
	}

	public void setAccess(String access) {
		this.access = access;
	}
}
