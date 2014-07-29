package ron.bean;

public class BeanVersion {
	
	private String version;
	
	private int open;
	private int currentCount;
	private int sumCount;
	
	public String getVersion() {
		return version;
	}
	public void setVersion(String version) {
		this.version = version;
	}
	public int getOpen() {
		return open;
	}
	public void setOpen(int open) {
		this.open = open;
	}
	public int getCurrentCount() {
		return currentCount;
	}
	public void setCurrentCount(int currentCount) {
		this.currentCount = currentCount;
	}
	public int getSumCount() {
		return sumCount;
	}
	public void setSumCount(int sumCount) {
		this.sumCount = sumCount;
	}
	
	public BeanVersion() {
	}
	
	public BeanVersion(String version, int open, int currentCount, int sumCount) {
		super();
		this.version = version;
		this.open = open;
		this.currentCount = currentCount;
		this.sumCount = sumCount;
	}
	@Override
	public String toString() {
		return "BeanVersion [version=" + version + ", open=" + open
				+ ", currentCount=" + currentCount + ", sumCount=" + sumCount
				+ "]";
	}
	

}
