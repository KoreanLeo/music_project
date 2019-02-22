package Model;

public class RcSinger {
	private String singer;
	private String title;
	private String genre;
	private String userid;
	
	public RcSinger(String singer, String title, String genre, String userid) {
		super();
		this.singer = singer;
		this.title = title;
		this.genre = genre;
		this.userid = userid;
	}

	public String getSinger() {
		return singer;
	}

	public void setSinger(String singer) {
		this.singer = singer;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getGenre() {
		return genre;
	}

	public void setGenre(String genre) {
		this.genre = genre;
	}

	public String getUserid() {
		return userid;
	}

	public void setUserid(String userid) {
		this.userid = userid;
	}
}
