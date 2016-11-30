package phonehome.leynew.com.phenehome.control;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import phonehome.leynew.com.phenehome.db.DataBaseHelper;

public class TwoCustomDataBase extends DataBaseHelper {

	SQLiteDatabase sd;
	
	public TwoCustomDataBase(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}
	
	public List<TwoColourCustom> findAllTCC(int d_id){
		List<TwoColourCustom> list = new ArrayList<TwoColourCustom>();
		String sql = "select * from TwoColourCustom where d_id=? order by _id desc";
		sd = getReadableDatabase();
		Cursor c = sd.rawQuery(sql, new String[]{d_id+""});
		while(c.moveToNext()){
			TwoColourCustom tcc = new TwoColourCustom();
			tcc.set_id(c.getInt(c.getColumnIndex("_id")));
			tcc.setD_id(c.getInt(c.getColumnIndex("d_id")));
			tcc.setT_brightness(c.getInt(c.getColumnIndex("t_brightness")));
			tcc.setT_cool(c.getInt(c.getColumnIndex("t_cool")));
			tcc.setT_warm(c.getInt(c.getColumnIndex("t_warm")));
			list.add(tcc);
		}
		c.close();
		sd.close();
		return list;
	}
	
	public void save(TwoColourCustom tcc){
		String sql = "insert into TwoColourCustom(d_id,t_cool,t_warm,t_brightness) values(?,?,?,?)";
		sd = getWritableDatabase();
		sd.execSQL(sql, new Integer[]{tcc.getD_id(),tcc.getT_cool(),tcc.getT_warm(),tcc.getT_brightness()});
		sd.close();
	}
	
	public void delete(TwoColourCustom tcc){
		sd = getWritableDatabase();
		sd.delete("TwoColourCustom", "_id="+tcc.get_id(), null);
		sd.close();
	}

}
