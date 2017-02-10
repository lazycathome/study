package cn.jedisoft.framework.examples;

public class Image {

	private long id = 0;
	private int total = 0;
	private String title = "";
	private String url = "";

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public int getTotal() {
		return total;
	}

	public void setTotal(int total) {
		this.total = total;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

}
