package phonehome.leynew.com.phenehome.damain;

import java.io.Serializable;

public class Lamp implements Serializable {
	
	private static final long serialVersionUID = -537328204227183078L;
	private int _id;
	private int d_id;
	private String l_sequence;
	private String l_address;//
	private String l_type;//
	private boolean l_status;//״̬
	private boolean isCheck = false;//
	private boolean l_controll=false;
	
	
	public boolean isL_controll() {
		return l_controll;
	}
	public void setL_controll(boolean l_controll) {
		this.l_controll = l_controll;
	}
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
	public String getL_sequence() {
		return l_sequence;
	}
	public void setL_sequence(String l_sequence) {
		this.l_sequence = l_sequence;
	}
	public String getL_address() {
		return l_address;
	}
	public void setL_address(String l_address) {
		this.l_address = l_address;
	}
	public String getL_type() {
		return l_type;
	}
	public void setL_type(String l_type) {
		this.l_type = l_type;
	}
	public boolean getL_status() {
		return l_status;
	}
	public void setL_status(boolean l_status) {
		this.l_status = l_status;
	}
	public boolean isCheck() {
		return isCheck;
	}
	public void setCheck(boolean isCheck) {
		this.isCheck = isCheck;
	}
	
}
