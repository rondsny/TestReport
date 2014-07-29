package ron.bean;

/*
 * 该类用于存储问题的基本信息
 * */
public class BeanIssue {

	private String id;
	private String title;
	private String status;
	private int level;
	private String category;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public int getLevel() {
		return level;
	}
	public void setLevel(int level) {
		this.level = level;
	}
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	
	

	public BeanIssue() {
	}
	
	public BeanIssue(String id, String title, String status, int level,
			String category) {
		super();
		this.id = id;
		this.title = title;
		this.status = status;
		this.level = level;
		this.category = category;
	}
	
	
	@Override
	public String toString() {
		return "BeanIssue [id=" + id + ", title=" + title + ", status="
				+ status + ", level=" + level + ", category=" + category + "]";
	}
	
}
