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
import phonehome.leynew.com.phenehome.db.DeviceDataBase;
import phonehome.leynew.com.phenehome.db.ImagesDatabase;
import phonehome.leynew.com.phenehome.fragment.Fragment_Device;
import phonehome.leynew.com.phenehome.fragment.Fragment_Room;
import phonehome.leynew.com.phenehome.util.Util;

public class UpdateDeviceDialog extends Dialog {
	
	private Context context;
	private Handler handler;
	private List<Images> list = new ArrayList<Images>();
//	private Map<Integer, String> images = new HashMap<Integer, String>();
	private GridView gv;
	private AreaAddGVAdapter gvAdapter;
	private Button btn_save,btn_back;
	private ImageView pic;
	private EditText et_name;
	private int resid;
	private TextView title;
	private Device device = Fragment_Device.DEVICE;
	
	public UpdateDeviceDialog(Context context, Handler handler) {
		super(context);
		this.context = context;
		this.handler = handler;
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		this.setContentView(R.layout.add_room);
		initView();
		list =  getData();
		click();
		
		pic.setBackgroundResource(Integer.parseInt(Fragment_Device.DEVICE.getImage().getPath()));
		et_name.setText(Fragment_Device.DTEXT);
		gvAdapter = new AreaAddGVAdapter(context, list);
		gv.setAdapter(gvAdapter);
		gv.setOnItemClickListener(new OnItemClickListener() {

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
	
	private void initView() {
		// TODO Auto-generated method stub
		title = (TextView) findViewById(R.id.dialog_title);
		title.setText(context.getString(R.string.update));
		btn_save = (Button) findViewById(R.id.add_floor_save);
		btn_save.setText(context.getString(R.string.next));
		btn_back = (Button) findViewById(R.id.back);
		pic = (ImageView) findViewById(R.id.add_floor_pic);
		et_name = (EditText) findViewById(R.id.add_floor_name);
		gv = (GridView) findViewById(R.id.add_floor_gv);
	}
	
	private List<Images> getData() {
		List<Images> images = new ArrayList<Images>();
		ImagesDatabase db = new ImagesDatabase(context);
		images.clear();
		images = db.findDeviceImages();
		
		db.close();
		return images;
		
	}
	
	private void click() {
		
		btn_back.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				UpdateDeviceDialog.this.dismiss();
			}

			
		});
		
		btn_save.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Device	device =Fragment_Device.DEVICE;
				String name = et_name.getText().toString().trim();
				if (name.equals("") || name == null) {
					Toast.makeText(context, context.getString(R.string.tip_floorName_empty), Toast.LENGTH_SHORT).show();
					return;
				}
				DeviceDataBase db = new DeviceDataBase(context);
				device.set_id(device.get_id());
				device.setD_name(name);
				device.setD_path(resid);
				device.setR_id(Fragment_Room.ROOM.get_id());
				db.updateDevice(device);
				if (device.getLamp() != null)
						//&& !device.getLamp().getL_sequence().equals("")) 
						{
					db.removeLamp(device.get_id(), device.getL_id());//
				}
				db.close();
				BindDeviceDialog bindDeviceDialog = new BindDeviceDialog(context,handler,"");
				bindDeviceDialog.show();
				UpdateDeviceDialog.this.dismiss();
			}
		});
		
		
	}
	
	@Override
	protected void onStop() {
		// TODO Auto-generated method stub
		super.onStop();
		Util.sendMessage(handler, null);
	}
	

}
