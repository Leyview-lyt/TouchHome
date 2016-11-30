package phonehome.leynew.com.phenehome.db;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import phonehome.leynew.com.phenehome.damain.Lamp;


public class LampDataBase extends DataBaseHelper {
	
	private SQLiteDatabase sd;

	public LampDataBase(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}
	
//	public List<Lamp> findAllLamp(){
//		List<Lamp> list = new ArrayList<Lamp>();
//		sd = getReadableDatabase();
//		Cursor c  = sd.rawQuery("select * from Lamp", null);
//		while(c.moveToNext()){
//			list.add(resultLamp(c));
//		}
//		c.close();
//		sd.close();
//		return list;
//	}

	public Lamp resultLamp(Cursor c){
		Lamp l = new Lamp();
		l.set_id(c.getInt(c.getColumnIndex("_id")));
		l.setD_id(c.getInt(c.getColumnIndex("d_id")));
		l.setL_address(c.getString(c.getColumnIndex("l_address")));
		l.setL_sequence(c.getString(c.getColumnIndex("l_sequence")));
//		l.setL_status(c.getString(c.getColumnIndex("l_status")).equals("ON")?true:false);
		l.setL_type(c.getString(c.getColumnIndex("l_type")));
		return l;
	}

	/**
	 * controlActivity
	 * @param id
	 * @return
     */
	public Lamp findLampById(int id){
		String sql = "select * from Lamp where _id=?";
		sd = getReadableDatabase();
		Cursor c = sd.rawQuery(sql, new String[]{id+""});
		Lamp lamp = new Lamp();
		if (c.moveToNext()) {
			lamp = resultLamp(c);
		}
		c.close();
		sd.close();
		return lamp;
	}
	
	public Lamp findLamp(String sequence){
		String sql = "select * from Lamp where l_sequence=?";
		sd = getReadableDatabase();
		Cursor c = sd.rawQuery(sql, new String[]{sequence});
		Lamp lamp = null;
		if (c.moveToNext()) {
			lamp = resultLamp(c);
		}
		c.close();
		sd.close();
		return lamp;
	}
	
	public Lamp save(Lamp lamp){
		String sql = "insert into Lamp(l_sequence,l_type) values(?,?)";
		sd = getWritableDatabase();
		sd.execSQL(sql, new String[]{lamp.getL_sequence(),lamp.getL_type()});
		sd.close();
		return findLamp(lamp.getL_sequence());
	}

	/**
	 * 更新设备信息
	 * @param sequence
	 * @param type
     */
	public void updateType(String sequence, String type){
		String sql = "update Lamp set l_type=? where l_sequence=?";
		sd = getWritableDatabase();
		sd.execSQL(sql, new String[]{type,sequence});
		sd.close();
	}
	
}
