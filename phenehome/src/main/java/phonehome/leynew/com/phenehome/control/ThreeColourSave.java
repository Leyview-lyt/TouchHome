package phonehome.leynew.com.phenehome.control;

import java.io.Serializable;


public class ThreeColourSave implements Serializable {

	private static final long serialVersionUID = 1L;

	private int _id;
	private int t_id;//计划
	private String tc_hold = "0.0";//保持时间
	private String tc_shade = "0.0";//渐变时间
	private int r=255;
	private int g=255;
	private int b=255;
	private int diaphaneity=100;//透明度
	private int status;


	public int get_id(){
		return _id;
	}
	public void set_id(int _id){
		this._id = _id;
	}
	public int getT_id(){
		return t_id;
	}
	public void setT_id(int t_id){
		this.t_id = t_id;
	}
	public String getTc_hold(){
		return tc_hold;
	}
	public void setTc_hold(String tc_hold){
		this.tc_hold = tc_hold;
	}
	public String getTc_shade(){
		return tc_shade;
	}
	public void setTc_shade(String tc_shade){
		this.tc_shade = tc_shade;
	}
	
	public int getR(){
		return r;
	}
	public void setR(int r){
		this.r = r;
	}
	public int getG(){
		return g;
	}
	public void setG(int g){
		this.g = g;
	}
	public int getB(){
		return b;
	}
	public void setB(int b){
		this.b = b;
	}
	public int getDiaphaneity(){
		return diaphaneity;
	}
	public void setDiaphaneity(int diaphaneity){
		this.diaphaneity = diaphaneity;
	}
	public int getStatus(){
		return status;
	}
	public void setStatus(int status){
		this.status = status;
	}
}
