package phonehome.leynew.com.phenehome.damain;

public class ChildDeviceType {
	
	private  int   id;
	private  int  driverId;
	private  int  lampId;
	private  String  childDriverType;
	private  String  childDriverSequence;
	
	public ChildDeviceType(){
	}

	public ChildDeviceType(int id){
		this.id = id;
	}
	
	public ChildDeviceType(int id, int driverId){
		this.id = id;
		this.driverId = driverId;
	}
	
	public ChildDeviceType(int id, int driverId, int lampId){
		this.id = id;
		this.driverId = driverId;
		this.lampId = lampId;
	}
	public ChildDeviceType(String childDriverType){
		this.childDriverType = childDriverType;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getDriverId() {
		return driverId;
	}

	public void setDriverId(int driverId) {
		this.driverId = driverId;
	}

	public int getLampId() {
		return lampId;
	}

	public void setLampId(int lampId) {
		this.lampId = lampId;
	}

	public String getChildDriverType() {
		return childDriverType;
	}

	public void setChildDriverType(String childDriverType) {
		this.childDriverType = childDriverType;
	}

	public String getChildDriverSequence() {
		return childDriverSequence;
	}

	public void setChildDriverSequence(String childDriverSequence) {
		this.childDriverSequence = childDriverSequence;
	}
	
}
