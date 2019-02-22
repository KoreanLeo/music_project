package Model;

public class BlackList {
	private String blackid;
	private String blackCause;
	
	public BlackList(String blackid, String blackCause) {
		super();
		this.blackid = blackid;
		this.blackCause = blackCause;
	}
	public String getBlackid() {
		return blackid;
	}
	public void setBlackid(String blackid) {
		this.blackid = blackid;
	}
	public String getBlackCause() {
		return blackCause;
	}
	public void setBlackCause(String blackCause) {
		this.blackCause = blackCause;
	}
}
