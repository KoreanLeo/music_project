package Model;

public class SingRecommend {
	private String singer;
	private String singTitle;
	private String genre;
	private String userid;
	public SingRecommend(String singer, String singTitle, String genre, String userid) {
		super();
		this.singer = singer;
		this.singTitle = singTitle;
		this.genre = genre;
		this.userid = userid;
	}
	public String getSinger() {
		return singer;
	}
	public void setSinger(String singer) {
		this.singer = singer;
	}
	public String getSingTitle() {
		return singTitle;
	}
	public void setSingTitle(String singTitle) {
		this.singTitle = singTitle;
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
