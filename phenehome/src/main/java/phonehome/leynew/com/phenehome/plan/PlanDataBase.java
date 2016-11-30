package phonehome.leynew.com.phenehome.plan;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;



import java.util.ArrayList;
import java.util.List;

import phonehome.leynew.com.phenehome.db.DataBaseHelper;

public class PlanDataBase extends DataBaseHelper {
	SQLiteDatabase sd;

	public PlanDataBase(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}

	public List<Plan> findAllPlan() {
		List<Plan> list = new ArrayList<Plan>();
		String sql = "select * from Plan";
		sd = getReadableDatabase();
		Cursor c = sd.rawQuery(sql, null);
		while (c.moveToNext()) {
			Plan p = new Plan();
			p.set_id(c.getInt(c.getColumnIndex("_id")));
			p.setP_date(c.getInt(c.getColumnIndex("p_date")));
			p.setP_time(c.getString(c.getColumnIndex("p_time")));
			p.setP_status(c.getString(c.getColumnIndex("p_status")).equals("true"));
			list.add(p);
		}
		c.close();
		sd.close();
		return list;
	}

	public void updateStatus(int planID, boolean status) {
		String sql = "update Plan set p_status=? where _id=?";
		sd = getWritableDatabase();
		sd.execSQL(sql, new String[] { status + "", planID + "" });
		sd.close();
	}

	public void updateDateTime(Plan plan) {
		String sql = "update Plan set p_date=?,p_time=? where _id=?";
		sd = getWritableDatabase();
		sd.execSQL(sql, new String[] { plan.getP_date() + "", plan.getP_time(),
				plan.get_id() + "" });
		sd.close();
	}

	public PlanDeviceStore findStore(int pid, int did) {
		sd = getReadableDatabase();
		String sql = "select * from PlanDeviceStore where p_id=? and d_id=?";
		Cursor c = sd.rawQuery(sql, new String[] { pid + "", did + "" });
		PlanDeviceStore p = null;
		if (c.moveToNext()) {
			p = getPlanDeviceStore(c);
		}
		c.close();
		sd.close();
		return p;
	}
	private PlanDeviceStore getPlanDeviceStore(Cursor c){
		PlanDeviceStore p = new PlanDeviceStore();
		p.set_id(c.getInt(c.getColumnIndex("_id")));
		p.setP_id(c.getInt(c.getColumnIndex("p_id")));
		p.setD_id(c.getInt(c.getColumnIndex("d_id")));
		p.setPds_status(c.getString(c.getColumnIndex("pds_status")).equals("ON"));
		p.setPds_brightness(c.getInt(c.getColumnIndex("pds_brightness")));
		p.setPds_colour(c.getInt(c.getColumnIndex("pds_colour")));
		p.setPds_brightness2(c.getInt(c.getColumnIndex("pds_brightness2")));
		p.setPds_colour_cool(c.getInt(c.getColumnIndex("pds_colour_cool")));
		p.setPds_colour_warm(c.getInt(c.getColumnIndex("pds_colour_warm")));
		p.setPds_mode(c.getInt(c.getColumnIndex("pds_mode")));
		p.setPds_speed(c.getInt(c.getColumnIndex("pds_speed")));
		p.setPds_red(c.getInt(c.getColumnIndex("pds_red")));
		p.setPds_green(c.getInt(c.getColumnIndex("pds_green")));
		p.setPds_blue(c.getInt(c.getColumnIndex("pds_blue")));
		p.setPds_custom(c.getInt(c.getColumnIndex("pds_custom")));
		return p;
	}
	
	public List<PlanDeviceStore> findAllStore(int pid){
		sd = getReadableDatabase();
		String sql = "select * from PlanDeviceStore where p_id=?";
		Cursor c = sd.rawQuery(sql, new String[] { pid + ""});
		List<PlanDeviceStore> list = new ArrayList<PlanDeviceStore>();
		while (c.moveToNext()) {
			list.add(getPlanDeviceStore(c));
		}
		c.close();
		sd.close();
		return list;
	}



	public void saveStore(PlanDeviceStore p) {
		sd = getWritableDatabase();
		if (p.get_id() == 0) {
			
			String sql = "insert into PlanDeviceStore(p_id,d_id,pds_status,pds_brightness,"
					+ "pds_colour,pds_brightness2,pds_colour_cool,pds_colour_warm,pds_mode,pds_speed,pds_red,"
					+ "pds_green,pds_blue,pds_custom) values(?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
			sd.execSQL(
					sql,
					new Object[] { p.getP_id(), p.getD_id(), p.isPds_status(),
							p.getPds_brightness(), p.getPds_colour(),p.getPds_brightness2(),
							p.getPds_colour_cool(), p.getPds_colour_warm(),
							p.getPds_mode(), p.getPds_speed(), p.getPds_red(),
							p.getPds_green(), p.getPds_blue(),p.getPds_custom() });

		} else {
			String sql = "update PlanDeviceStore set p_id=?,d_id=?,pds_status=?,pds_brightness=?,"
					+ "pds_colour=?,pds_brightness2=?,pds_colour_cool=?,pds_colour_warm=?,pds_mode=?,pds_speed=?,pds_red=?,"
					+ "pds_green=?,pds_blue=?,pds_custom=? where _id=?";
			sd.execSQL(
					sql,
					new Object[] { p.getP_id(), p.getD_id(), p.isPds_status(),
							p.getPds_brightness(), p.getPds_colour(),p.getPds_brightness2(),
							p.getPds_colour_cool(), p.getPds_colour_warm(),
							p.getPds_mode(), p.getPds_speed(), p.getPds_red(),
							p.getPds_green(), p.getPds_blue(),p.getPds_custom(), p.get_id() });
		}
	}
}
