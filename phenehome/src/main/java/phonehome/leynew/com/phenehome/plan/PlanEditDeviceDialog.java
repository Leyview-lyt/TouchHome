package phonehome.leynew.com.phenehome.plan;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import phonehome.leynew.com.phenehome.R;
import phonehome.leynew.com.phenehome.adapter.DeviceGridAdapter;
import phonehome.leynew.com.phenehome.control.WF400AAdapter;
import phonehome.leynew.com.phenehome.damain.Device;
import phonehome.leynew.com.phenehome.damain.Images;
import phonehome.leynew.com.phenehome.damain.Lamp;
import phonehome.leynew.com.phenehome.db.DeviceDataBase;
import phonehome.leynew.com.phenehome.db.ImagesDatabase;
import phonehome.leynew.com.phenehome.db.LampDataBase;
import phonehome.leynew.com.phenehome.util.Util;

public class PlanEditDeviceDialog extends Dialog  {

	private Context context;
	private Plan plan;
	private Button save;
	private GridView gv;
	private MyViewPager vp;

	private PlanDeviceStore pds;       					//
	private List<View> views = new ArrayList<View>();
	private boolean is = false;
	private ImageButton left, right;
	private TextView read_status;
	private TextView title_device, title_console;//
	private List<Device> devices = new ArrayList<Device>();
	private Map<Integer, String> images = new HashMap<Integer, String>();
	private DeviceGridAdapter adapter;
	private Device device;

	public PlanEditDeviceDialog(Context context, Plan plan){
		super(context);
		this.context = context;
		this.plan = plan;
	}

	GestureDetector mGestureDetector;
	@Override
	protected void onCreate(Bundle savedInstanceState){
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		this.setContentView(R.layout.plan_device);
		init();

		// TODO: 2016/11/26/026
		mGestureDetector = new GestureDetector(getContext(), myGestureListener);

		devices = getData();
		adapter = new DeviceGridAdapter(context, devices,  false, false);
		gv.setAdapter(adapter);
		gv.setOnItemClickListener(new ItemClick());
		clickView();
	}

	private void init(){
		save = (Button) findViewById(R.id.save_pd);
		gv = (GridView) findViewById(R.id.gridView0);     //
		vp = (MyViewPager) findViewById(R.id.plan_home_vp);
		left = (ImageButton) findViewById(R.id.plan_vp_left);
		right = (ImageButton) findViewById(R.id.plan_vp_right);
		read_status = (TextView) findViewById(R.id.plan_txt_status);     //̬
		title_device = (TextView) findViewById(R.id.title_device);
		title_console = (TextView) findViewById(R.id.title_console);
	}
	
	
	private void clickView() {


		save.setOnClickListener(new View. OnClickListener(){
			@Override
			public void onClick(View v) {
				PlanDataBase db = new PlanDataBase(context);
				if (pds!=null) {
					db.saveStore(pds);
					db.close();
					PlanEditDeviceDialog.this.dismiss();
				}else
					Toast.makeText(context,"请选择后再保存 ",Toast.LENGTH_SHORT).show();

			}
		});
		
		left.setOnClickListener(new View.OnClickListener(){
			public void onClick(View v) {
				vp.setCurrentItem(vp.getCurrentItem()-1);
			}
		});
		
		right.setOnClickListener(new View.OnClickListener(){
			public void onClick(View v) {
				vp.setCurrentItem(vp.getCurrentItem()+1);
			}
		});
	}

	// TODO: 2016/11/26/026 优化

	private static final int FLING_MIN_DISTANCE = 30;   //最小距离
	private static final int FLING_MIN_VELOCITY = 0;  //最小速度
	GestureDetector.SimpleOnGestureListener myGestureListener = new GestureDetector.SimpleOnGestureListener(){
		public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {

			Log.e("<--滑动测试-->", "开始滑动");
			float x = e1.getX()-e2.getX();
			float x2 = e2.getX()-e1.getX();
			if(x>FLING_MIN_DISTANCE&&Math.abs(velocityX)>FLING_MIN_VELOCITY){
				vp.setCurrentItem(vp.getCurrentItem()+1);

			}else if(x2>FLING_MIN_DISTANCE&&Math.abs(velocityX)>FLING_MIN_VELOCITY){
				vp.setCurrentItem(vp.getCurrentItem()-1);
			}

			return false;
		}
	};




	@Override
	public boolean onTouchEvent(MotionEvent event) {

		return  mGestureDetector.onTouchEvent(event);
	}

	private List<Device> getData() {
		// TODO Auto-generated method stub
		List<Device> devices = new ArrayList<Device>();
		ImagesDatabase idb = new ImagesDatabase(context);
		List<Images> list = idb.findDeviceImages();
		for (Images img : list) {
			images.put(img.get_id(), img.getPath());
		}
		idb.close();
		DeviceDataBase ddb = new DeviceDataBase(context);
		devices = ddb.findDevice(PlanEditDialog.P_R);
		ddb.close();
		return devices;

	}


	private class ItemClick implements OnItemClickListener {
		public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
				long arg3){
			device = devices.get(arg2);
			views = new ArrayList<View>();

			if (device != null && device.getL_id() != 0){
				title_console.setText(device.getD_name());

				LampDataBase ldb = new LampDataBase(context);
				final Lamp lamp = ldb.findLampById(device.getL_id());
				ldb.close();
				new Thread(){
					public void run(){
						if (lamp.getL_type().equals("WF500")) {
							lamp.setL_sequence(lamp.getL_sequence().split("-")[0]);
						}
						
						List<String[]> list = Util.sendCommandForResult(
								"readstatus =", null, lamp.getL_sequence());
						if (list.size() == 0){
							Util.sendMessage(isSuccessHandler, null);
						}
					};
				}.start();
				device.setLamp(lamp);
				Bundle bundle = new Bundle();
				bundle.putSerializable("device", device);
				PlanDataBase db = new PlanDataBase(context);
				PlanDeviceStore pds = db.findStore(plan.get_id(),
						device.get_id());
				if (pds == null) {
					pds = new PlanDeviceStore();
					pds.setPds_brightness(100);
				}
				db.close();

				if (lamp.getL_type().equals("WF400C")
						|| lamp.getL_type().equals("WF500A")
						|| lamp.getL_type().equals("WF500B")
						|| lamp.getL_type().equals("TM111")
						|| lamp.getL_type().equals("WF510")
						|| lamp.getL_type().equals("TM113")
						|| lamp.getL_type().equals("WF321")
						|| lamp.getL_type().equals("NB-1000")
						) {
					// $(R.id.plan_vp_left).setVisibility(View.GONE);
					// $(R.id.plan_vp_right).setVisibility(View.GONE);
					View view = LayoutInflater.from(context).inflate(
							R.layout.homochromy, null);
					views.add(view);
					PlanWF400CAdapter c = new PlanWF400CAdapter(context, views, device, isUpdateHandler, pds);
					vp.setAdapter(c);

				} else if (lamp.getL_type().equals("WF400B") || lamp.getL_type().equals("WF322")){
					View view = LayoutInflater.from(context).inflate(
							R.layout.two_tone, null);
					View view2 = LayoutInflater.from(context).inflate(
							R.layout.two_tone_custom, null);
					views.add(view);
					views.add(view2);
					PlanWF400BAdapter b = new PlanWF400BAdapter(context, views, device, isUpdateHandler, pds, vp);
					vp.setAdapter(b);

				} else if (lamp.getL_type().equals("WF400A")||lamp.getL_type().equals("Zigbee") || lamp.getL_type().equals("WF323")) {
					View view = LayoutInflater.from(context).inflate(
							R.layout.three_colour1, null);
					View view2 = LayoutInflater.from(context).inflate(
							R.layout.three_colour_model, null);
					View view3 = LayoutInflater.from(context).inflate(
							R.layout.three_colour_custom, null);
					views.add(view);
					views.add(view2);
					views.add(view3);
					PlanWF400AAdapter a = new PlanWF400AAdapter(context, views, device,  null, isUpdateHandler, pds, vp);
					vp.setOffscreenPageLimit(2);
					vp.setAdapter(a);
				}

			} else {
			//	$(R.id.plan_vp_left).setVisibility(View.GONE);
			//	$(R.id.plan_vp_right).setVisibility(View.GONE);
			//  if (data.getString("suibian")!=null) {
				title_console.setText(context.getString(R.string.tip_Unbound));
				title_console.setTextColor(Color.RED);
				views = new ArrayList<View>();
				PlanWF400CAdapter c = new PlanWF400CAdapter(context, views,
						device, isUpdateHandler, null);
				vp.setAdapter(c);
				is = true;
				// }else {
				// views.isEmpty()views.size()>0
				System.out.println(views.size() + " =size()");

				if (is) {
					title_console.setText(context.getString(R.string.home_setting));
					title_console.setTextColor(Color.WHITE);
					title_device.setText(context.getString(R.string.home_device));
					is = false;
				}
			}
		}
	}
	
	private Handler isSuccessHandler = new Handler() {
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			read_status.setVisibility(View.VISIBLE);
			TimerTask task = new TimerTask() {
				public void run() {
					Util.sendMessage(readStatusHandler, null);
				}
			};
			Timer timer = new Timer(true);
			timer.schedule(task, 3000);//
		};
	};
	
	private Handler readStatusHandler = new Handler(){
		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			super.handleMessage(msg);
			read_status.setVisibility(View.GONE);
		}
		
	};

	private Handler isUpdateHandler = new Handler() {
		@Override
		public void handleMessage(Message msg){
			// TODO Auto-generated method stub
			super.handleMessage(msg);
			Bundle data = msg.getData();
			pds = (PlanDeviceStore) data.getSerializable("pds");
			pds.setP_id(plan.get_id());   //
			pds.setD_id(device.get_id()); //
		//	save.setVisibility(View.VISIBLE);
		}
	};
	


}
