package phonehome.leynew.com.phenehome.plan;

import java.io.Serializable;
import java.math.BigInteger;

public class Plan implements Serializable {

	private static final long serialVersionUID = 3895490865008938936L;
	private int _id;
	private int p_date;//
	private String p_time;//
	private boolean p_status;//
	public int get_id() {
		return _id;
	}
	public void set_id(int _id) {
		this._id = _id;
	}
	public int getP_date() {
		return p_date;
	}
	public void setP_date(int p_date) {
		this.p_date = p_date;
	}
	public String getP_time() {
		return p_time;
	}
	public void setP_time(String p_time) {
		this.p_time = p_time;
	}
	public boolean isP_status() {
		return p_status;
	}
	public void setP_status(boolean p_status) {
		this.p_status = p_status;
	}
	
	public static boolean[] getDayIsck(int t){
		boolean[] b = new boolean[7];
		char c[] = Integer.toBinaryString(t).toCharArray();
		int num = 0;
		if (c.length<b.length) {
			num = b.length - c.length;
		}
		for (int i = num; i < b.length; i++) {
			if ((c[i-num]+"").equals("1")) 
				b[i] = true;
			else 
				b[i] = false;
		}
		return b;
	}
	
	public static int getInteger(boolean[] b){
		String str = "";
		for (int i = 0; i < b.length; i++){
			if(b[i])
				str = str + "1";
			else
				str = str + "0";
		}
		return Integer.parseInt(new BigInteger(str, 2).toString(10));
	}
	
}
