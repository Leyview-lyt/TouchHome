package phonehome.leynew.com.phenehome.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import phonehome.leynew.com.phenehome.R;
import phonehome.leynew.com.phenehome.adapter.AreaAddGVAdapter;
import phonehome.leynew.com.phenehome.adapter.AreaGVAdapter;
import phonehome.leynew.com.phenehome.damain.Images;
import phonehome.leynew.com.phenehome.damain.Room;
import phonehome.leynew.com.phenehome.db.ImagesDatabase;
import phonehome.leynew.com.phenehome.db.RoomDataBase;
import phonehome.leynew.com.phenehome.util.Util;

public class AddRoomDialog extends Dialog {
	
	private Context context;
	private Handler handler;
	private Button btn_save,btn_back;
	private ImageView pic;
	private EditText et_name;
	private GridView gv;
	private AreaAddGVAdapter gvAdapter;
	private List<Images> list = new ArrayList<Images>();
	private Map<Integer, String> images = new HashMap<Integer, String>();//
	private int resid;
	private String initRoomName = "";
	private TextView title;
	
	public AddRoomDialog(Context context,  Handler handler){
		super(context);
		this.context = context;
		this.handler = handler;
	}
	
	
	protected void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.add_room);
		initView();
		getData();
		click();
		resid = Integer.parseInt(list.get(0).getPath());
		pic.setBackgroundResource(Integer.parseInt(list.get(0).getPath()));
		gvAdapter = new AreaAddGVAdapter(context, list);
		gv.setAdapter(gvAdapter);
		gv.setOnItemClickListener(new OnItemClickListener(){

			

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub
				pic.setBackgroundResource(Integer.parseInt(list.get(arg2).getPath()));
				resid = Integer.parseInt(list.get(arg2).getPath());
			}
		});
	}
	
	private void click(){
		btn_back.setOnClickListener(new View.OnClickListener(){
			
			@Override
			public void onClick(View v) {
				AddRoomDialog.this.dismiss();
			}
		});
		
		btn_save.setOnClickListener(new View.OnClickListener() {
			
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Room room = new Room();
				String name = et_name.getText().toString().trim();
				System.out.println(""+name);
				if (name.equals("") || name == null) {
					Toast.makeText(context, context.getString(R.string.tip_floorName_empty), Toast.LENGTH_LONG).show();
					return;
				}
				System.out.println("");
				RoomDataBase db = new RoomDataBase(context);
				room.setR_name(name);
				room.setR_path(resid);
				room.setF_id(1);
				db.save(room);
				db.close();
				AddRoomDialog.this.dismiss();
			}
		});
	}

	private void initView() {
		title = (TextView) findViewById(R.id.dialog_title);
		title.setText("");
		btn_save = (Button) findViewById(R.id.add_floor_save);
		btn_back = (Button) findViewById(R.id.back);
		pic = (ImageView) findViewById(R.id.add_floor_pic);
		et_name = (EditText) findViewById(R.id.add_floor_name);
		gv = (GridView) findViewById(R.id.add_floor_gv);
	}
	
	private void getData() {
		ImagesDatabase idb = new ImagesDatabase(context);
		List<Images> list = idb.findRoomImages();
		for (Images img : list) {
			images.put(img.get_id(), img.getPath());
		}
		idb.close();
		idb.close();
		this.list = list;
	}
	
	
	@Override
	protected void onStop() {
		// TODO Auto-generated method stub
		super.onStop();
		Util.sendMessage(handler, null);
	}
}
