package phonehome.leynew.com.phenehome.db;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;


import java.util.ArrayList;
import java.util.List;

import phonehome.leynew.com.phenehome.damain.Device;
import phonehome.leynew.com.phenehome.damain.Room;

public class RoomDataBase extends DataBaseHelper {

	private SQLiteDatabase sd;
	private Context context;

	public RoomDataBase(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
		this.context = context;
	}

	public Room save(Room room) {
		sd = getWritableDatabase();
		String sql = "insert into Room(r_name,r_path,f_id) values(?,?,?)";
		sd.execSQL(sql, new String[] { room.getR_name(), room.getR_path() + "",
				room.getF_id() + "" });
		sd.close();
		room.set_id(findRoomIdByName(room.getF_id(), room.getR_name()));
		Log.i("=====================",""+findRoomIdByName(room.getF_id(), room.getR_name()));
		return room;
	}

	public int findRoomIdByName(int f_id, String name) {
		sd = getReadableDatabase();
		Cursor c = sd.rawQuery(
				"select _id from Room where r_name=? and f_id=?", new String[] {
						name, f_id + "" });
		int id = 0;
		if (c.moveToNext()) {
			id = c.getInt(0);
		}
		c.close();
		sd.close();
		return id;
	}
//
//	public boolean isUse(int f_id, String name) {
//		return findRoomIdByName(f_id, name) == 0;
//	}
//
	public void update(Room room) {
		sd = getWritableDatabase();
		String sql = "update Room set r_name=?,r_path=? where _id=?";
		sd.execSQL(sql, new String[] { room.getR_name(), room.getR_path() + "",
				room.get_id() + "" });
		sd.close();
	}

	public void delete(int rid) {
		sd = getWritableDatabase();
		String sql = "select _id from Device where r_id=" + rid;
		List<Integer> list = new ArrayList<Integer>();
		Cursor c = sd.rawQuery(sql, null);
		while (c.moveToNext()) {
			list.add(c.getInt(0));
		}
		c.close();
		sd.delete("Room", "_id=" + rid, null);
		sd.close();
		if (list.size() > 0) {
			DeviceDataBase db = new DeviceDataBase(context);
			for (int i = 0; i < list.size(); i++) {
				Device device = new Device();
				device.set_id(list.get(i));
				db.deleteDevice(device);
			}
			db.close();
			ChildDeviceTypeDataBase delete = new ChildDeviceTypeDataBase(
					context);
			delete.deleteChildDiveceByRoom(list);
		}
	}

	/**
	 * 查询所有的区域？？？
	 * @return
     */
	public List<Room> findRoom() {
		sd = getReadableDatabase();
		List<Room> rooms = new ArrayList<Room>();
		// String sFloor = "select * from Floor";
		String sRoom = "select * from Room ";

		Cursor cr = sd.rawQuery(sRoom, null);
		while (cr.moveToNext()) {
			Room r = new Room();
			r.set_id(cr.getInt(cr.getColumnIndex("_id"))); //
			r.setR_name(cr.getString(cr.getColumnIndex("r_name")));
			r.setR_path(cr.getInt(cr.getColumnIndex("r_path")));
			rooms.add(r);
		}
		cr.close();
		sd.close();
		return rooms;
	}
}
