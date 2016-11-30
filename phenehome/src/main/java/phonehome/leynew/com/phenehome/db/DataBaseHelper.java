package phonehome.leynew.com.phenehome.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import phonehome.leynew.com.phenehome.R;


public class DataBaseHelper extends SQLiteOpenHelper {
	private static final String DB_NAME = "led.db";
	private static final int VERSION = 5;

	public DataBaseHelper(Context context) {
		super(context, DB_NAME, null, VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		// TODO Auto-generated method stub
		System.out.println("创建数据库");
		// sqlite查询路径data/data/com.leynew.touchhome/databases
		// 创建图片表
		String cImages = "create table Images (_id integer primary key autoincrement,"
				+ "path varchar(300))";
		// 创建楼层
		String cFloor = "create table Floor (_id integer primary key autoincrement,"
				+ "f_name varchar(50))";
		// 创建房间
		String cRoom = "create table Room (_id integer primary key autoincrement,"
				+ "r_name varchar(50),r_path integer,f_id integer)";
		// 创建灯光设备
		String cDevice = "create table Device (_id integer primary key autoincrement,"
				+ "d_name varchar(50),d_path integer,r_id integer,l_id integer)";
		// 创建灯光控制器
		String cLamp = "create table Lamp (_id integer primary key autoincrement,"
				+ "d_id integer,l_sequence varchar(50),l_address varchar(50),l_type varchar(50),"
				+ "l_status varchar(50))";
		// 创建场景
		String cScene = "create table Scene (_id integer primary key autoincrement,"
				+ "s_name varchar(50),r_id integer,s_path integer)";
		// 创建场景设备
		String cSceneDevice = "create table SceneDevice (_id integer primary key autoincrement,"
				+ "s_id integer,d_id integer)";
		// 创建场景设备属性
		String cSceneDeviceStore = "create table SceneDeviceStore (_id integer primary key autoincrement,"
				+ "sd_id integer,sds_status varchar(50),sds_brightness integer,sds_colour integer"
				+ ",sds_brightness2 integer,sds_colour_cool integer,sds_colour_warm integer,"
				+ "sds_mode integer,sds_speed integer,sds_red integer,sds_green integer,sds_blue integer,"
				+ "sds_custom integer)";
		// 创建计划
		String cPlan = "create table Plan (_id integer primary key autoincrement,"
				+ "p_date integer,p_time varchar(50),p_status varchar(50))";

		// 创建计划设备属性
		String cPlanDeviceStore = "create table PlanDeviceStore (_id integer primary key autoincrement,"
				+ "p_id integer,d_id integer,pds_status varchar(50),pds_brightness integer,pds_colour integer,"
				+ "pds_brightness2 integer,pds_colour_cool integer,pds_colour_warm integer,"
				+ "pds_mode integer,pds_speed integer,pds_red integer,pds_green integer,pds_blue integer,"
				+ "pds_custom integer)";
		// 创建三色设备属性存储表
		String cThreeColourCustom = "create table ThreeColourCustom (_id integer primary key autoincrement,"
				+ "d_id integer,t_name varchar(50),t_custom integer)";
		// 创建三色设备16宫格颜色存储表
		String cThreeColourSave = "create table ThreeColourSave (_id integer primary key autoincrement,"
				+ "t_id integer,tc_hold varchar(50),tc_shade varchar(50),r integer,g integer,b integer,"
				+ "diaphaneity integer,status integer)";
		// 创建双色设备属性存储表
		String cTwoColourCustom = "create table TwoColourCustom (_id integer primary key autoincrement,"
				+ "d_id integer,t_cool integer,t_warm integer,t_brightness integer)";

		// 创建子设备类型表
		// String childDriverType =
		// "create table childDriverType( _id integer primary key  autoincrement, driver_name varchar(50) , "
		// +" sequence varchar(50), child_driver_type varchar(50), driverPicName varchar(50));";
		String childDriverType = "create table childDriverType(id  integer primary key  autoincrement,   driver_id  integer,    lamp_id integer, "
				+ "child_driver_type varchar(50),  child_driver_sequence  varchar(50) );";
		db.execSQL(cImages);
		db.execSQL(cFloor);
		db.execSQL(cRoom);
		db.execSQL(cDevice);
		db.execSQL(cLamp);
		db.execSQL(cScene);
		db.execSQL(cSceneDevice);
		db.execSQL(cSceneDeviceStore);
		db.execSQL(cPlan);
		db.execSQL(cPlanDeviceStore);
		db.execSQL(cThreeColourCustom);
		db.execSQL(cThreeColourSave);
		db.execSQL(cTwoColourCustom);
		db.execSQL(childDriverType);
		insertImages(db);
		// 创建5个计划
		String plan5 = "insert into Plan(p_date,p_time,p_status) values(?,?,?)";
		for (int i = 1; i <= 5; i++) {
			db.execSQL(plan5, new String[] { "0", "00:00", "false" });
		}
	}

	private void insertImages(SQLiteDatabase db) {
		// 添加图标，系统图标存储drawable id
		String sql = "insert into Images(path) values(?)";
		db.execSQL(sql, new String[] { R.drawable.d1 + "" });
		db.execSQL(sql, new String[] { R.drawable.d2 + "" });
		db.execSQL(sql, new String[] { R.drawable.d3 + "" });
		db.execSQL(sql, new String[] { R.drawable.d4 + "" });
		db.execSQL(sql, new String[] { R.drawable.d5 + "" });
		db.execSQL(sql, new String[] { R.drawable.d6 + "" });
		db.execSQL(sql, new String[] { R.drawable.d7 + "" });
		db.execSQL(sql, new String[] { R.drawable.d8 + "" });
		db.execSQL(sql, new String[] { R.drawable.d9 + "" });
		db.execSQL(sql, new String[] { R.drawable.d10 + "" });
		db.execSQL(sql, new String[] { R.drawable.d11 + "" });
		db.execSQL(sql, new String[] { R.drawable.d12 + "" });
		db.execSQL(sql, new String[] { R.drawable.d13 + "" });
		db.execSQL(sql, new String[] { R.drawable.d14 + "" });
		db.execSQL(sql, new String[] { R.drawable.d15 + "" });
		db.execSQL(sql, new String[] { R.drawable.d16 + "" });
		db.execSQL(sql, new String[] { R.drawable.d17 + "" });
		db.execSQL(sql, new String[] { R.drawable.d18 + "" });
		db.execSQL(sql, new String[] { R.drawable.d19 + "" });
		db.execSQL(sql, new String[] { R.drawable.d20 + "" });
		db.execSQL(sql, new String[] { R.drawable.d21 + "" });
		db.execSQL(sql, new String[] { R.drawable.d22 + "" });
		db.execSQL(sql, new String[] { R.drawable.d23 + "" });
		db.execSQL(sql, new String[] { R.drawable.d24 + "" });
		db.execSQL(sql, new String[] { R.drawable.d25 + "" });
		db.execSQL(sql, new String[] { R.drawable.d26 + "" });
		db.execSQL(sql, new String[] { R.drawable.d27 + "" });
		db.execSQL(sql, new String[] { R.drawable.d28 + "" });
		db.execSQL(sql, new String[] { R.drawable.d29 + "" });
		db.execSQL(sql, new String[] { R.drawable.d30 + "" });
		db.execSQL(sql, new String[] { R.drawable.d31 + "" });
		db.execSQL(sql, new String[] { R.drawable.d32 + "" });
		db.execSQL(sql, new String[] { R.drawable.d33 + "" });
		db.execSQL(sql, new String[] { R.drawable.d34 + "" });
		db.execSQL(sql, new String[] { R.drawable.r1 + "" });
		db.execSQL(sql, new String[] { R.drawable.r2 + "" });
		db.execSQL(sql, new String[] { R.drawable.r3 + "" });
		db.execSQL(sql, new String[] { R.drawable.r4 + "" });
		db.execSQL(sql, new String[] { R.drawable.r5 + "" });
		db.execSQL(sql, new String[] { R.drawable.r6 + "" });
		db.execSQL(sql, new String[] { R.drawable.r7 + "" });
		db.execSQL(sql, new String[] { R.drawable.r8 + "" });
		db.execSQL(sql, new String[] { R.drawable.r9 + "" });
		db.execSQL(sql, new String[] { R.drawable.r10 + "" });
		db.execSQL(sql, new String[] { R.drawable.r11 + "" });// 45
		db.execSQL(sql, new String[] { R.drawable.s1 + "" });
		db.execSQL(sql, new String[] { R.drawable.s2 + "" });
		db.execSQL(sql, new String[] { R.drawable.s3 + "" });
		db.execSQL(sql, new String[] { R.drawable.s4 + "" });
		db.execSQL(sql, new String[] { R.drawable.s5 + "" });
		db.execSQL(sql, new String[] { R.drawable.s6 + "" });
		db.execSQL(sql, new String[] { R.drawable.s7 + "" });
		db.execSQL(sql, new String[] { R.drawable.s8 + "" });
		db.execSQL(sql, new String[] { R.drawable.s9 + "" });
		db.execSQL(sql, new String[] { R.drawable.s10 + "" });
		db.execSQL(sql, new String[] { R.drawable.s11 + "" });
		db.execSQL(sql, new String[] { R.drawable.s12 + "" });
		db.execSQL(sql, new String[] { R.drawable.s13 + "" });// 58
		// 蛋疼
	}

	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub
	}
}
