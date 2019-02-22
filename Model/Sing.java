package Model;

public class Sing {
	private String singer;
	private String title;
	private String genre;
	public Sing(String singer, String title, String genre) {
		super();
		this.singer = singer;
		this.title = title;
		this.genre = genre;
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
}
