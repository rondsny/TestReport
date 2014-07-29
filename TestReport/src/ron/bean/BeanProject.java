package ron.bean;

import java.util.List;

public class BeanProject {
	private String md5;
	private String project;
	private String name;
	private List<BeanReport> reports;
	private boolean isDel;
	
	public String getMd5() {
		return md5;
	}
	public void setMd5(String md5) {
		this.md5 = md5;
	}
	public String getProject() {
		return project;
	}
	public void setProject(String project) {
		this.project = project;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public List<BeanReport> getReports() {
		return reports;
	}
	public void setReports(List<BeanReport> reports) {
		this.reports = reports;
	}
	public boolean isDel() {
		return isDel;
	}
	public void setDel(boolean isDel) {
		this.isDel = isDel;
	}
	public BeanProject(){}
	public BeanProject(String md5, String project, String name,
			List<BeanReport> reports, boolean isDel) {
		super();
		this.md5 = md5;
		this.project = project;
		this.name = name;
		this.reports = reports;
		this.isDel = isDel;
	}
	@Override
	public String toString() {
		return "BeanProject [project=" + project + ", name=" + name
				+ ", reports=" + reports + ", isDel=" + isDel + "]";
	}
}
