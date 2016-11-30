package phonehome.leynew.com.phenehome.control;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;



import java.util.ArrayList;
import java.util.List;

import phonehome.leynew.com.phenehome.db.DataBaseHelper;
import phonehome.leynew.com.phenehome.util.FinalClass;

public class ThreeCustomDataBase extends DataBaseHelper {
	
	private SQLiteDatabase sd;

	public ThreeCustomDataBase(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}
	
	public ThreeColourCustom save(ThreeColourCustom tcc){
		int index = findInsertableCustom(tcc.getD_id());
		sd = getWritableDatabase();
		String sql = "insert into ThreeColourCustom(d_id,t_name,t_custom) values(?,?,?)";
		String sql2 = "insert into ThreeColourSave(t_id,tc_hold,tc_shade,r,g,b,diaphaneity,status) values(?,?,?,?,?,?,?,?)";
		if (tcc.getList().size()!=16){
			new Exception("000000000000");
		}
		tcc.setT_custom(index);
		sd.execSQL(sql, new String[]{tcc.getD_id()+"",tcc.getT_name(),index+""});
		sd.close();
		int id = findThreeColourCustomId(tcc);
		if(id!=-1)
			tcc.set_id(id);
		 else 
			new Exception("111111111");
		sd = getWritableDatabase();
		for (int i = 0; i < tcc.getList().size(); i++){
			ThreeColourSave tcs = tcc.getList().get(i);
			sd.execSQL(sql2, new Object[]{tcc.get_id(),tcs.getTc_hold(),tcs.getTc_shade(),
		    tcs.getR(),tcs.getG(),tcs.getB(),tcs.getDiaphaneity(),tcs.getStatus()});
		}
		sd.close();
		return tcc;
	}
	public int findInsertableCustom(int d_id){
		int num = 0;
		int temp[] = new int[FinalClass.CUSTOM_SIZE];
		String sql = "select t_custom from ThreeColourCustom where d_id="+d_id+" order by t_custom";
		sd = getReadableDatabase();
		Cursor c = sd.rawQuery(sql, null);
		while(c.moveToNext()){
			if (c.getInt(0)<= FinalClass.CUSTOM_SIZE){
				temp[c.getInt(0)-1] = 1;
			}
		}
		c.close();
		sd.close();
		for (int i = 0; i < temp.length; i++) {
			if (temp[i]==0) {
				num = i + 1;
				break;
			}
		}
		return num;
	}
	
	public int findThreeColourCustomId(ThreeColourCustom tcc){
		String sql = "select _id from ThreeColourCustom where d_id=? and t_name=?";
		sd = getReadableDatabase();
		Cursor c = sd.rawQuery(sql, new String[]{tcc.getD_id()+"",tcc.getT_name()});
		int id = -1;
		if (c.moveToNext()) {
			id = c.getInt(0);
		}
		c.close();
		sd.close();
		return id;
	}
	
	public boolean isUseName(String name,int d_id){
		String sql = "select _id from ThreeColourCustom where t_name=? and d_id=?";
		sd = getReadableDatabase();
		Cursor c = sd.rawQuery(sql, new String []{name,d_id+""});
		if (c.moveToNext()) {
			return false;
		}
		c.close();
		sd.close();
		return true;
	}
	
	public List<ThreeColourCustom> findTCC(int d_id){
		List<ThreeColourCustom> list = new ArrayList<ThreeColourCustom>();
		sd = getReadableDatabase();
		String sql = "select * from ThreeColourCustom where d_id=? order by _id desc";
		Cursor c = sd.rawQuery(sql, new String[]{d_id+""});
		while (c.moveToNext()) {
			ThreeColourCustom tcc = new ThreeColourCustom();
			tcc.set_id(c.getInt(c.getColumnIndex("_id")));
			tcc.setD_id(c.getInt(c.getColumnIndex("d_id")));
			tcc.setT_name(c.getString(c.getColumnIndex("t_name")));
			tcc.setT_custom(c.getInt(c.getColumnIndex("t_custom")));
			list.add(tcc);
		}
		c.close();
		if (list.size()>0) {
			List<ThreeColourSave> tcss = new ArrayList<ThreeColourSave>();
			StringBuffer sb = new StringBuffer("select * from ThreeColourSave where t_id in (");
			String str[] = new String[list.size()];
			for (int i = 0; i < list.size(); i++) {
				if (i!=list.size()-1) {
					sb.append("?,");
				} else {
					sb.append("?)");
				}
				str[i] = list.get(i).get_id()+"";
			}
			System.out.println(sb.toString());
			Cursor s = sd.rawQuery(sb.toString(), str);
			while(s.moveToNext()){
				ThreeColourSave tcs = new ThreeColourSave();
				tcs.set_id(s.getInt(0));
				tcs.setT_id(s.getInt(1));
				tcs.setTc_hold(s.getString(2));
				tcs.setTc_shade(s.getString(3));
				tcs.setR(s.getInt(4));
				tcs.setG(s.getInt(5));
				tcs.setB(s.getInt(6));
				tcs.setDiaphaneity(s.getInt(7));
				tcs.setStatus(s.getInt(8));
				tcss.add(tcs);
			}
			s.close();
			for (int i = 0; i < list.size(); i++) {
				List<ThreeColourSave> subTcss = new ArrayList<ThreeColourSave>();
				for (int j = 0; j < tcss.size(); j++) {
					if (tcss.get(j).getT_id() == list.get(i).get_id()) {
						subTcss.add(tcss.get(j));
					}
				}
				list.get(i).setList(subTcss);
			}
		}
		sd.close();
		return list;
	}
	
	public void delete(ThreeColourCustom tcc){
		sd = getWritableDatabase();
		sd.delete("ThreeColourSave", "t_id="+tcc.get_id(), null);
		sd.delete("ThreeColourCustom", "_id="+tcc.get_id(), null);
		sd.close();
	}
	
	public void update(ThreeColourCustom tcc){
		sd = getWritableDatabase();
		String sql = "update ThreeColourCustom set t_name=? where _id=?";
		String sql2 = "update ThreeColourSave set tc_hold=?,tc_shade=?,r=?,g=?,b=?," +
				"diaphaneity=?,status=? where _id=?";
		sd.execSQL(sql, new String[]{tcc.getT_name(),tcc.get_id()+""});
		for (int i = 0; i < tcc.getList().size(); i++) {
			ThreeColourSave t = tcc.getList().get(i);
			sd.execSQL(sql2, new String[]{t.getTc_hold(),t.getTc_shade(),t.getR()+"",t.getG()+""
					,t.getB()+"",t.getDiaphaneity()+"",+t.getStatus()+"",t.get_id()+""});
		}
		sd.close();
	}
	
	public int getTccCount(int d_id){
		String sql = "select count(_id) from ThreeColourCustom where d_id=?";
		Cursor c = sd.rawQuery(sql, new String[]{d_id+""});
		int num = 0;
		if (c.moveToNext()) {
			num = c.getInt(0);
		}
		c.close();
		return num;
	}
	

}
