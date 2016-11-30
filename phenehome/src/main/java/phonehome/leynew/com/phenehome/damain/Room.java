package phonehome.leynew.com.phenehome.damain;



import java.io.Serializable;

public class Room implements Serializable {
	
	private static final long serialVersionUID = -2369115584479198516L;
	private int _id;
	private String r_name;//
	private int r_path;//
	private int f_id;//
	private Images img;//

//	private List<Device> devices = new ArrayList<Device>();
	public int get_id() {
		return _id;
	}
	public void set_id(int _id) {
		this._id = _id;
	}
	public String getR_name() {
		return r_name;
	}
	public void setR_name(String r_name) {
		this.r_name = r_name;
	}
	public int getR_path() {
		return r_path;
	}
	public void setR_path(int r_path) {
		this.r_path = r_path;
	}
	public int getF_id() {
		return f_id;
	}
	public void setF_id(int f_id) {
		this.f_id = f_id;
	}
//	public List<Device> getDevices() {
//		return devices;
//	}
//	public void setDevices(List<Device> devices) {
//		this.devices = devices;
//	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	public Images getImg() {
		return img;
	}
	public void setImg(Images img) {
		this.img = img;
	}
	

}
