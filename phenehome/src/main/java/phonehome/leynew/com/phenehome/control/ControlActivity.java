package phonehome.leynew.com.phenehome.control;


import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.PopupWindow;
import android.widget.TextView;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import phonehome.leynew.com.phenehome.R;
import phonehome.leynew.com.phenehome.damain.Device;
import phonehome.leynew.com.phenehome.damain.Lamp;
import phonehome.leynew.com.phenehome.db.DataBaseHelper;
import phonehome.leynew.com.phenehome.db.LampDataBase;
import phonehome.leynew.com.phenehome.fragment.Fragment_Device;
import phonehome.leynew.com.phenehome.util.Util;

@TargetApi(Build.VERSION_CODES.HONEYCOMB)
@SuppressLint({ "NewApi", "HandlerLeak" })
public class ControlActivity extends Activity{
	private Context context;
	private Button dev;
	private TextView title;
	private Device device;
	private PopupWindow popupWindow;
	DataBaseHelper dbhelp;

	@Override
	protected void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		this.setContentView(R.layout.control_layout);
		device = Fragment_Device.DEVICE;
		context = this;
		dev = (Button) findViewById(R.id.dev);
		title = (TextView) findViewById(R.id.ctitle);
		title.setText(device.getD_name());
		dev.setOnClickListener(new OnClickListener(){
			public void onClick(View v){
				finish();
			}
		});
//		createPopupWindow();
		Util.sendMessage(controlHandler, null);
	}
	
	
	private Handler controlHandler = new Handler(){
		public void handleMessage(Message msg){
			super.handleMessage(msg);
			LampDataBase ldb = new LampDataBase(context);
			final Lamp lamp = ldb.findLampById(device.getL_id());
			ldb.close();
			FragmentTransaction transaction = getFragmentManager()
					.beginTransaction();
			Fragment fragment = null;
			new Thread() {
				public void run() {
					if (lamp.getL_type().equals("WF500")) {
						lamp.setL_sequence(lamp.getL_sequence().split(
								"-")[0]);
					}
					List<String[]> list = Util.sendCommandForResult(
							"readstatus =", null, lamp.getL_sequence());
					if (list.size() == 0){
						Util.sendMessage(isSuccessHandler, null);
					}
				};
			}.start();
			device.setLamp(lamp);

			if (lamp.getL_type().equals("WF400C")
					|| lamp.getL_type().equals("WF500A")
					|| lamp.getL_type().equals("WF500B")
					|| lamp.getL_type().equals("TM111")
					|| lamp.getL_type().equals("WF510")
					|| lamp.getL_type().equals("TM113")
					|| lamp.getL_type().equals("WF321")
					|| lamp.getL_type().equals("WF325")
					|| lamp.getL_type().equals("NB-1000")
					) {
				fragment = new HomochromyFragment();
				transaction.replace(R.id.home_ll_console, fragment,
						"one").commit();
			} else if (lamp.getL_type().equals("WF400B") || lamp.getL_type().equals("WF320")
					|| lamp.getL_type().equals("WF322")  || lamp.getL_type().equals("WF326")
					) {
				fragment = new TwoColorFragment();
				transaction.replace(R.id.home_ll_console, fragment,
						"two").commit();
			} else if (lamp.getL_type().equals("WF400A")||lamp.getL_type().equals("Zigbee")
					|| lamp.getL_type().equals("WF323")
					) {
				if(lamp.getL_type().equals("WF400A") || lamp.getL_type().equals("WF323")){
					fragment = new ThreeColorFragment(popupWindow);
					transaction.replace(R.id.home_ll_console, fragment,"three").commit();
				}else{
					fragment = new ThreeColorFragment(popupWindow);
					transaction.replace(R.id.home_ll_console, fragment,"three").commit();
				}
			}
		}
	};
	
	private Handler isSuccessHandler = new Handler() {
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			TimerTask task = new TimerTask() {
				public void run() {
					Util.sendMessage(readStatusHandler, null);
				}
			};
			Timer timer = new Timer(true);
			timer.schedule(task, 3000);
		};
	};
	private Handler readStatusHandler = new Handler(){
		public void handleMessage(Message msg){
			super.handleMessage(msg);
		}
	};
	
//	private void createPopupWindow() {
//		View view = LayoutInflater.from(context).inflate(
//		R.layout.three_colour_popupwindows, null);
//		popupWindow = new PopupWindow(view, 30, 30);
//		popupWindow.setAnimationStyle(android.R.style.Animation_Toast);
//		popupWindow.setTouchable(false);
//	}
	
	protected void onPause() {
		super.onPause();
//		System.out.println("onPause");
//		if (popupWindow.isShowing()) {
//			popupWindow.dismiss();
//		}
	} 

}
