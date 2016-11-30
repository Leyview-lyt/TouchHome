package phonehome.leynew.com.phenehome.damain;

import java.io.Serializable;

public class Images implements Serializable {

	private static final long serialVersionUID = 7104123099350480086L;
	private int _id;
	private String path;
	public int get_id() {
		return _id;
	}
	public void set_id(int _id) {
		this._id = _id;
	}
	public String getPath() {
		return path;
	}
	public void setPath(String path) {
		this.path = path;
	}
	
}
