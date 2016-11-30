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
import java.util.List;

import phonehome.leynew.com.phenehome.R;
import phonehome.leynew.com.phenehome.adapter.AreaAddGVAdapter;
import phonehome.leynew.com.phenehome.damain.Device;
import phonehome.leynew.com.phenehome.damain.Images;
import phonehome.leynew.com.phenehome.damain.Room;
import phonehome.leynew.com.phenehome.db.DeviceDataBase;
import phonehome.leynew.com.phenehome.db.ImagesDatabase;
import phonehome.leynew.com.phenehome.fragment.Fragment_Room;
import phonehome.leynew.com.phenehome.util.Util;

public class AddDeviceDialog extends Dialog {

	private Context context;
	private Handler handler;
	private Button btn_save,btn_back;
	private TextView title;
	private ImageView pic;
	private EditText et_name;
	private GridView gv;
	private AreaAddGVAdapter gvAdapter;
	private List<Images> list = new ArrayList<Images>();
//	private Map<Integer, String> images = new HashMap<Integer, String>();//
	private int resid;
	private Device device = new Device();//
	private Room room;
	public static Device D;
	private Device initDevice; //
	private String initName;
	private int initLampID;
	
	public AddDeviceDialog(Context context, Handler handler, Room room){
		super(context);
		this.context = context;
		this.handler = handler;
		this.room = room;
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.add_room);
		D = this.device;
		initView();
		getData();         //
		click();
		pic.setBackgroundResource(Integer.parseInt(list.get(0).getPath()));
		Images image = new Images();
		image.set_id(list.get(0).get_id());
		image.setPath(list.get(0).getPath());
		device.setImage(image);
		resid = Integer.parseInt(list.get(0).getPath());//设置默认保存
		gvAdapter = new AreaAddGVAdapter(context, list);
		gv.setAdapter(gvAdapter);
		gv.setOnItemClickListener(new OnItemClickListener(){
			
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub
				pic.setBackgroundResource(Integer.parseInt(list.get(arg2).getPath()));
				Images image = new Images();
				image.set_id(list.get(arg2).get_id());
				image.setPath(list.get(arg2).getPath());
				device.setImage(image);
				resid = Integer.parseInt(list.get(arg2).getPath());
				
			}
		});
	}
	
	private void click() {
		
		btn_back.setOnClickListener(new View.OnClickListener(){
			
			public void onClick(View v) {
				// TODO Auto-generated method stub
				AddDeviceDialog.this.dismiss();
			}

			
		});
		
		btn_save.setOnClickListener(new View.OnClickListener(){
			@Override
			public void onClick(View v){
				// TODO Auto-generated method stub
			//	Device device = new Device();
				String name = et_name.getText().toString().trim();
				if (name.equals("") || name == null){
					Toast.makeText(context, context.getString(R.string.tip_floorName_empty),Toast.LENGTH_LONG).show();
					return;
				}
				DeviceDataBase db = new DeviceDataBase(context);
				device.setD_name(name);
				device.setD_path(resid);
				device.setR_id(Fragment_Room.ROOM.get_id());
				db.addDevice(device);
				db.close();
				BindDeviceDialog bindDeviceDialog = new BindDeviceDialog(context,handler ,name);
				bindDeviceDialog.show();
				AddDeviceDialog.this.dismiss();
			}
		}); 
	}

	private void initView() {
		// TODO Auto-generated method stub
		title = (TextView) findViewById(R.id.dialog_title);
		title.setText("");
		btn_save = (Button) findViewById(R.id.add_floor_save);
		btn_save.setText(context.getString(R.string.next));
		btn_back = (Button) findViewById(R.id.back);
		pic = (ImageView) findViewById(R.id.add_floor_pic);
		et_name = (EditText) findViewById(R.id.add_floor_name);
		gv = (GridView) findViewById(R.id.add_floor_gv);
	}
	
	public  void getData() {
		ImagesDatabase idb = new ImagesDatabase(getContext());
		list.clear();
		for (Images img  : idb.findDeviceImages()) {
			list.add(img);
		}
		idb.close();
	}
	
	protected void onStop() {
		// TODO Auto-generated method stub
		super.onStop();
		Util.sendMessage(handler, null);
	}

}

