package Model;

public class MemberInfomation {
	private String mbId;
	private String mbPassword;
	private String mbName;
	private String mbAge;
	private String mbNickName;
	private String mbGender;
	private String mbGenre;
	
	public MemberInfomation(String mbId, String mbPassword, String mbName, String mbAge, String mbNickName,
			String mbGender, String mbGenre) {
		super();
		this.mbId = mbId;
		this.mbPassword = mbPassword;
		this.mbName = mbName;
		this.mbAge = mbAge;
		this.mbNickName = mbNickName;
		this.mbGender = mbGender;
		this.mbGenre = mbGenre;
	}

	public String getMbId() {
		return mbId;
	}

	public void setMbId(String mbId) {
		this.mbId = mbId;
	}

	public String getMbPassword() {
		return mbPassword;
	}

	public void setMbPassword(String mbPassword) {
		this.mbPassword = mbPassword;
	}

	public String getMbName() {
		return mbName;
	}

	public void setMbName(String mbName) {
		this.mbName = mbName;
	}

	public String getMbAge() {
		return mbAge;
	}

	public void setMbAge(String mbAge) {
		this.mbAge = mbAge;
	}

	public String getMbNickName() {
		return mbNickName;
	}

	public void setMbNickName(String mbNickName) {
		this.mbNickName = mbNickName;
	}

	public String getMbGender() {
		return mbGender;
	}

	public void setMbGender(String mbGender) {
		this.mbGender = mbGender;
	}

	public String getMbGenre() {
		return mbGenre;
	}

	public void setMbGenre(String mbGenre) {
		this.mbGenre = mbGenre;
	}
}
