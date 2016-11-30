package phonehome.leynew.com.phenehome.control;

import java.io.Serializable;

public class TwoColourCustom implements Serializable {
	
	private static final long serialVersionUID = 1L;
	private int _id;
	private int d_id;//
	private int t_cool;//
	private int t_warm;//
	private int t_brightness;//
	public int get_id() {
		return _id;
	}
	public void set_id(int _id) {
		this._id = _id;
	}
	public int getD_id() {
		return d_id;
	}
	public void setD_id(int d_id) {
		this.d_id = d_id;
	}
	public int getT_cool() {
		return t_cool;
	}
	public void setT_cool(int t_cool) {
		this.t_cool = t_cool;
	}
	public int getT_warm() {
		return t_warm;
	}
	public void setT_warm(int t_warm) {
		this.t_warm = t_warm;
	}
	public int getT_brightness() {
		return t_brightness;
	}
	public void setT_brightness(int t_brightness) {
		this.t_brightness = t_brightness;
	}

}
