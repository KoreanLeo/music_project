package Model;

public class MusicRecommend {
	private String singer;
	private String singTitle;
	private String genre;
	private String rcNum;
	
	public MusicRecommend(String singer, String singTitle, String genre, String rcNum) {
		super();
		this.singer = singer;
		this.singTitle = singTitle;
		this.genre = genre;
		this.rcNum = rcNum;
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

	public String getRcNum() {
		return rcNum;
	}

	public void setRcNum(String rcNum) {
		this.rcNum = rcNum;
	}
}
