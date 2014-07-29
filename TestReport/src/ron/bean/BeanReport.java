package ron.bean;

public class BeanReport {
	private String md5;
	private String version;
	private String from;
	private String tos;
	private String title;
	private String url;
	private long createTime;
	private boolean isDel;
	
	public String getMd5() {
		return md5;
	}
	public void setMd5(String md5) {
		this.md5 = md5;
	}
	public String getVersion() {
		return version;
	}
	public void setVersion(String version) {
		this.version = version;
	}
	public String getFrom() {
		return from;
	}
	public void setFrom(String from) {
		this.from = from;
	}
	public String getTos() {
		return tos;
	}
	public void setTos(String tos) {
		this.tos = tos;
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
	public long getCreateTime() {
		return createTime;
	}
	public void setCreateTime(long createTime) {
		this.createTime = createTime;
	}
	public boolean isDel() {
		return isDel;
	}
	public void setDel(boolean isDel) {
		this.isDel = isDel;
	}
	public BeanReport(){}
	public BeanReport(String md5, String version, String from, String tos,
			String title, String url, long createTime, boolean isDel) {
		super();
		this.md5 = md5;
		this.version = version;
		this.from = from;
		this.tos = tos;
		this.title = title;
		this.url = url;
		this.createTime = createTime;
		this.isDel = isDel;
	}
	@Override
	public String toString() {
		return "BeanReport [version=" + version + ", from=" + from + ", tos="
				+ tos + ", title=" + title + ", url=" + url + ", createTime="
				+ createTime + ", isDel=" + isDel + "]";
	}
}
