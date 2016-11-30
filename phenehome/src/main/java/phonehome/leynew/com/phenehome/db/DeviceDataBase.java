package phonehome.leynew.com.phenehome.db;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;


import java.util.ArrayList;
import java.util.List;

import phonehome.leynew.com.phenehome.damain.Device;
import phonehome.leynew.com.phenehome.damain.Images;
import phonehome.leynew.com.phenehome.damain.Lamp;
import phonehome.leynew.com.phenehome.damain.Room;

public class DeviceDataBase extends DataBaseHelper {

	private SQLiteDatabase sd;
	private Context context;
	
	public DeviceDataBase(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
		this.context = context;
	}
	

	public void addDevice(Device device){
		String sql = "insert into Device(d_name,d_path,r_id,l_id) values(?,?,?,?)";
		sd = this.getWritableDatabase();
		sd.execSQL(sql, new Object[]{device.getD_name(), device.getImage().get_id(),
				device.getR_id(), device.getL_id()});
		sd.close();
	}

	public List<Device> findDevice(Room room){
		List<Device> list = new ArrayList<Device>();
		String sql = "select * from Device as d,Images as i where d.d_path=i._id and d.r_id=?";
		sd = getReadableDatabase();
		Cursor c = sd.rawQuery(sql, new String[]{room.get_id()+""});
		while(c.moveToNext()){
			Device d = resultDevice(c);
//			LampDataBase db = new LampDataBase(context);
//			Lamp lamp = db.findLampById(d.get_id());
//			d.setLamp(lamp);

			list.add(d);
		}
		c.close();
		sd.close();
		return list;
	}
	
/*	public List<Device> findDevice(Room room) {
		sd = getReadableDatabase();
		List<Device> devices = new ArrayList<Device>();

		//String sFloor = "select * from Floor";
		String sDevice = "select * from Device where r_id=? ";
	
			Cursor cr = sd.rawQuery(sDevice, new String[] {room.get_id() + ""});
			while (cr.moveToNext()) {
				Device d = new Device();
				d.set_id(cr.getInt(cr.getColumnIndex("_id")));
				d.setD_name(cr.getString(cr.getColumnIndex("d_name")));
				d.setD_path(cr.getInt(cr.getColumnIndex("d_path")));
				d.setR_id(cr.getInt(cr.getColumnIndex("r_id")));
				d.setL_id(cr.getInt(cr.getColumnIndex("l_id")));
				
				devices.add(d);
			}
			cr.close();
			sd.close();
			return devices;
	
	}  */
	

	public Device resultDevice(Cursor c){
		Device d = new Device();
		d.set_id(c.getInt(0));
		d.setD_name(c.getString(c.getColumnIndex("d_name")));
		d.setL_id(c.getString(c.getColumnIndex("l_id"))==null  || 
				c.getString(c.getColumnIndex("l_id")).equals("")
				? 0:Integer.parseInt(c.getString(c.getColumnIndex("l_id"))));
		d.setR_id(c.getInt(c.getColumnIndex("r_id")));
		Images image=new Images();
		image.set_id(c.getInt(5));
		image.setPath(c.getString(c.getColumnIndex("path")));
		d.setImage(image);
		return d;
	}
	

	public void deleteDevice(Device device){
		sd = getWritableDatabase();
		int did = device.get_id();
		sd.delete("Device", "_id=?", new String[]{did+""});
		

		sd.delete("Lamp", "d_id="+did, null);

		String sql = "select _id from ThreeColourCustom where d_id=?";
		List<Integer> list = new ArrayList<Integer>();
		Cursor c = sd.rawQuery(sql , new String[]{""+did});
		while(c.moveToNext()){
			list.add(c.getInt(0));
		}
		c.close();
		if (list.size()>0) {
			StringBuffer sb = new StringBuffer("t_id in (");
			String []term = new String[list.size()];
			for (int i = 0; i < list.size(); i++) {
				if (i<list.size()-1) 
					sb.append("?,");
				else
					sb.append("?)");
				term[i] = list.get(i)+"";
			}

			sd.delete("ThreeColourSave", sb.toString(), term);
			sd.delete("ThreeColourCustom", "d_id="+did, null);
		}
		//
		sd.delete("TwoColourCustom", "d_id="+did, null);
		//
		String sql2 = "select _id from SceneDevice where d_id=?";
		Cursor c2 = sd.rawQuery(sql2, new String[]{""+did});
		int sdid = 0;
		if (c2.moveToNext()) {
			sdid = c2.getInt(0);
		}
		c2.close();
		if (sdid>0) {
			sd.delete("SceneDevice", "d_id="+did, null);
			sd.delete("SceneDeviceStore", "sd_id="+sdid, null);
		}
		//ɾ���ƻ�����
		sd.delete("PlanDeviceStore", "d_id="+did, null);
		sd.close();
	}
	

	public void updateDevice(Device device){
		sd = getWritableDatabase();

		String sql = "update Device set d_name=?,d_path=?,r_id=?,l_id=? where _id=?";
		sd.execSQL(sql, new Object[]{device.getD_name(),device.getImage().get_id(),device.getR_id(),
				device.getL_id(),device.get_id()});
		sd.close();
	}
	

	public int getDeviceID(Device device){
		sd = getReadableDatabase();
		String sql = "select _id from Device where d_name=? and r_id=? ";
		Cursor c = sd.rawQuery(sql, new String[]{device.getD_name(),device.getR_id()+""});
		int id = 0;
		if (c.moveToNext()) {
			id = c.getInt(0);
		}
		c.close();
		sd.close();
		return id;
	}

	public Device findDeviceById(int did){
		String sql = "select * from Device where _id="+did;
		sd = getReadableDatabase();
		Device device = new Device();
		Cursor c = sd.rawQuery(sql, null);
		if (c.moveToNext()) {
			device.set_id(c.getInt(c.getColumnIndex("_id")));
			device.setD_name(c.getString(c.getColumnIndex("d_name")));
			device.setL_id(c.getInt(c.getColumnIndex("l_id")));
			device.setR_id(c.getInt(c.getColumnIndex("r_id")));
		}
		c.close();
		sd.close();
		LampDataBase db = new LampDataBase(context);
		Lamp lamp = db.findLampById(device.getL_id());
		db.close();
		device.setLamp(lamp);
		return device;
	}
	

	public boolean detectionName(String name,int roomid){
		boolean b = false;
		sd = getReadableDatabase();
		String sql = "select _id from Device where d_name=? and r_id=?";
		Cursor c = sd.rawQuery(sql, new String[]{name,roomid+""});
		if (c.moveToNext()) {
			b = true;
		}
		c.close();
		sd.close();
		return b;
	}
	

	public void bindingLamp(int deviceID,int lampID){
		sd = getWritableDatabase();
		String sql = "update Device set l_id=? where _id=?";
		String sql2 = "update Lamp set d_id=? where _id=?";
		sd.execSQL(sql, new String[]{lampID+"", deviceID+""});
		sd.execSQL(sql2, new String[]{deviceID+"", lampID+""});
		sd.close();
	}
	
	public void removeLamp(int deviceID,int lampID){
		sd = getWritableDatabase();
		String sql = "update Device set l_id=? where _id=?";
		sd.execSQL(sql, new String[]{0+"", deviceID+""});
		sd.delete("Lamp", "_id="+lampID, null);
		sd.close();
	}

}
