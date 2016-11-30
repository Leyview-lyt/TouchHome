package phonehome.leynew.com.phenehome.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.List;

import phonehome.leynew.com.phenehome.damain.ChildDeviceType;

public class ChildDeviceTypeDataBase extends DataBaseHelper {

	SQLiteDatabase sd;
	Context context;
	
	public ChildDeviceTypeDataBase(Context context) {
		super(context);
		this.context = context;
	}
	
//	public boolean saveChildDriverType(ChildDeviceType childDriver){
//		boolean flag = false;
//		sd = getWritableDatabase();
//		//
//		ContentValues values = new ContentValues();
//		values.put("driver_id", childDriver.getDriverId());
//		values.put("lamp_id", childDriver.getLampId());
//		values.put("child_driver_type", childDriver.getChildDriverType());
//		values.put("child_driver_sequence", childDriver.getChildDriverSequence());
//		Long result = sd.insert("childDriverType", null, values);
//		if(result>-1){
//			flag = true;
//		}
//		sd.close();
//		return flag;
//	}
//
//
//	//
//	public String findChildType(int id ){
//		String childType = null;
//		sd = getReadableDatabase();
//		String [] row = {"child_driver_type"};
//		String [] conditon = {String.valueOf(id)};
//		Cursor  cursor = sd.query("childDriverType", row, "driver_id = ?", conditon, null, null, null);
//		if(cursor.getCount()>0)
//		{
//			int  nameColumnIndex =  cursor.getColumnIndex("child_driver_type");
//			while(cursor.moveToNext()){
//				childType = cursor.getString(nameColumnIndex);
//			}
//		}
//		cursor.close();
//		sd.close();
//		return  childType;
//	}
//
//
//	//
//	public boolean  findChildDriver(String sequence){
//		boolean flag = false;
//		sd = getReadableDatabase();
//		String [] row = {"driver_name","sequence","child_driver_type"};
//		String [] condition = {sequence};
//		Cursor cursor = sd.query("childDriverType", row , "sequence=?", condition, null,null, null);
//		if(0<cursor.getCount()){
//			flag = true;
//		}
//		return flag;
//	}
//
//	//
//	public boolean searchContainDriver(String drivername){
//		sd = getReadableDatabase();
//		boolean flag = false;
//		String [] row = {"driverPicName"};
//		String [] condition = {drivername};
//		Cursor cursor = sd.query("childDriverType", row, "driverPicName = ?", condition, null, null, null);
//		if(cursor.getCount()>0){
//			flag = true;
//		}
//		return flag;
//	}
	
	

	public void deleteChildDiveceByRoom(List<Integer > device_id){
		if(device_id.size() >0)
		{
			sd = getReadableDatabase();
			for(int  i=0; i<device_id.size();i++){
				String [ ] arg = new String[]{device_id.get(i).toString()}; 
				sd.delete("childDriverType", "driver_id=?", arg);
			}
			sd.close();
		}
	}
	

	public void deleteChildDeviceByDeviceId(Integer d_id){
		if( 0 != d_id){
			sd = getReadableDatabase();
			String [] arg = new String[]{d_id.toString()};
			sd.delete("childDriverType", "driver_id=?", arg);
			sd.close();
		}
	}
	
	
}
