package phonehome.leynew.com.phenehome.db;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import phonehome.leynew.com.phenehome.damain.Images;
import phonehome.leynew.com.phenehome.util.FinalClass;

public class ImagesDatabase extends DataBaseHelper {

	private SQLiteDatabase sd;
	
	public ImagesDatabase(Context context){
		super(context);
	}
	//DEVICE_IMAGES_SIZE = 34;
	//ROOM_IMAGES_SIZE = 11;
	//String sql = "select * from images where _id>="+star+" and _id<="+end;
	public List<Images> findRoomImages(){
		int star = FinalClass.DEVICE_IMAGES_SIZE + 1;
		int end = FinalClass.DEVICE_IMAGES_SIZE + FinalClass.ROOM_IMAGES_SIZE;
		return findImages(star, end);
	} 
	
	public List<Images> findDeviceImages(){
		int star = 1;
		int end = FinalClass.DEVICE_IMAGES_SIZE;
		return findImages(star, end);
	}
//
//	public List<Images> findSceneImages(){
//		int star = FinalClass.DEVICE_IMAGES_SIZE + FinalClass.ROOM_IMAGES_SIZE + 1;
//		int end = FinalClass.IMAGES_SIZE;
//		return findImages(star, end);
//	}
	
	private List<Images> findImages(int star, int end){
		List<Images> list = new ArrayList<Images>();
		sd = getReadableDatabase();
		
		String sql = "select * from images where _id>="+star+" and _id<="+end;
		Cursor c = sd.rawQuery(sql, null);
		while(c.moveToNext()){
			Images img = new Images();
			img.set_id(c.getInt(0));
			img.setPath(c.getString(1));
			list.add(img);
		}
		c.close();
		sd.close();
		return list;
	} 
	
//	public List<Images> findAllImages(){
//		List<Images> list = new ArrayList<Images>();
//		sd = getReadableDatabase();
//		String sImages = "select * from Images";
//		Cursor c = sd.rawQuery(sImages, null);
//		while(c.moveToNext()){
//			Images img = new Images();
//			img.set_id(c.getInt(c.getColumnIndex("_id")));
//			img.setPath(c.getString(c.getColumnIndex("path")));
//			list.add(img);
//		}
//		c.close();
//		sd.close();
//		return list;
//	}
//
//	public Map<Integer, String> findImagesMap(){
//		Map<Integer, String> images = new HashMap<Integer, String>();//
//		sd = getReadableDatabase();
//		String sImages = "select * from Images";
//		Cursor c = sd.rawQuery(sImages, null);
//		while (c.moveToNext()){
//			Images img = new Images();
//			img.set_id(c.getInt(c.getColumnIndex("_id")));
//			img.setPath(c.getString(c.getColumnIndex("path")));
//			images.put(img.get_id(), img.getPath());
//		}
//		System.out.println(images.size());
//		c.close();
//		sd.close();
//		return images;
//	}

}
