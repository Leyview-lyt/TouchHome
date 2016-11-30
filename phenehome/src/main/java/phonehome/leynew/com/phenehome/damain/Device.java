package phonehome.leynew.com.phenehome.damain;

import java.io.Serializable;

public class Device implements Serializable {
	
	
	private static final long serialVersionUID = -4342078073849153056L;
	private int _id;
	private String d_name;
	private int d_path;
	private int r_id;
	private int l_id;
	private Images image;
	private Lamp lamp;
	public int get_id() {
		return _id;
	}
	public void set_id(int _id) {
		this._id = _id;
	}
	public String getD_name() {
		return d_name;
	}
	public void setD_name(String d_name) {
		this.d_name = d_name;
	}
	
	public int getR_id() {
		return r_id;
	}
	public void setR_id(int r_id) {
		this.r_id = r_id;
	}
	public int getL_id() {
		return l_id;
	}
	public void setL_id(int l_id) {
		this.l_id = l_id;
	}
	public Images getImage() {
		return image;
	}
	public void setImage(Images image) {
		this.image = image;
	}
	public Lamp getLamp() {
		return lamp;
	}
	public void setLamp(Lamp lamp) {
		this.lamp = lamp;
	}

	public int getD_path() {
		return d_path;
	}
	public void setD_path(int d_path) {
		this.d_path = d_path;
	}

}
