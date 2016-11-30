package phonehome.leynew.com.phenehome.control;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ThreeColourCustom implements Serializable{
	
	private static final long serialVersionUID = 1L;
	private int _id;
	private int d_id;//
	private String t_name;//
	private int t_custom;//
	private boolean isChecked;//
	private List<ThreeColourSave> list = new ArrayList<ThreeColourSave>();
	public int get_id(){
		return _id;
	}
	public void set_id(int _id){
		this._id = _id;
	}
	public int getD_id(){
		return d_id;
	}
	public void setD_id(int d_id){
		this.d_id = d_id;
	}
	public String getT_name(){
		return t_name;
	}
	public void setT_name(String t_name){
		this.t_name = t_name;
	}
	public List<ThreeColourSave> getList(){
		return list;
	}
	public void setList(List<ThreeColourSave> list){
		this.list = list;
	}
	public int getT_custom() {
		return t_custom;
	}
	public void setT_custom(int t_custom){
		this.t_custom = t_custom;
	}
	public boolean isChecked() {
		return isChecked;
	}
	public void setChecked(boolean isChecked){
		this.isChecked = isChecked;
	}
}
