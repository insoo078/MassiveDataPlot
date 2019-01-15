
public class Point {
	private String groupName;
	private double x;
	private double y;
	private double z;
	
	public Point(String groupName, double x, double y, double z) {
		this.groupName = groupName;
		this.x = x;
		this.y = y;
		this.z = z;
	}
	
	public String getGroupName() {
		return groupName;
	}
	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}
	public double getX() {
		return x;
	}
	public void setX(double x) {
		this.x = x;
	}
	public double getY() {
		return y;
	}
	public void setY(double y) {
		this.y = y;
	}
	public double getZ() {
		return z;
	}
	public void setZ(double z) {
		this.z = z;
	}

}
