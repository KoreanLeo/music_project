package Model;

public class MemberTable {
	private String userid;
	private String name;
	private String age;
	private String userNick;
	private String gender;
	private String genre;
	private String blacklist;
	private String blcause;
	
	public MemberTable(String userid, String name, String age, String userNick, String gender, String genre,
			String blacklist, String blcause) {
		super();
		this.userid = userid;
		this.name = name;
		this.age = age;
		this.userNick = userNick;
		this.gender = gender;
		this.genre = genre;
		this.blacklist = blacklist;
		this.blcause = blcause;
	}

	public String getUserid() {
		return userid;
	}

	public void setUserid(String userid) {
		this.userid = userid;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAge() {
		return age;
	}

	public void setAge(String age) {
		this.age = age;
	}

	public String getUserNick() {
		return userNick;
	}

	public void setUserNick(String userNick) {
		this.userNick = userNick;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getGenre() {
		return genre;
	}

	public void setGenre(String genre) {
		this.genre = genre;
	}

	public String getBlacklist() {
		return blacklist;
	}

	public void setBlacklist(String blacklist) {
		this.blacklist = blacklist;
	}

	public String getBlcause() {
		return blcause;
	}

	public void setBlcause(String blcause) {
		this.blcause = blcause;
	}
}
