package phonehome.leynew.com.phenehome.dialog;


import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.Window;
import android.widget.Button;

import phonehome.leynew.com.phenehome.R;
import phonehome.leynew.com.phenehome.damain.Device;
import phonehome.leynew.com.phenehome.db.ChildDeviceTypeDataBase;
import phonehome.leynew.com.phenehome.db.DeviceDataBase;
import phonehome.leynew.com.phenehome.fragment.Fragment_Device;
import phonehome.leynew.com.phenehome.util.Util;


public class DeleteDeviceDialog extends Dialog {
	
	private Context context;
	private Handler handler;
	private Button confirm;
	private Button cancel;
	
	public DeleteDeviceDialog(Context context, Handler handler) {
		super(context);
		this.context = context;
		this.handler = handler;
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.delete_room_dialog);
		confirm = (Button) findViewById(R.id.confirm);
		cancel = (Button) findViewById(R.id.cancel);
		cancel.setOnClickListener(new View.OnClickListener() {
			
			public void onClick(View v) {
				// TODO Auto-generated method stub
				DeleteDeviceDialog.this.dismiss();
			}

		});
		
		confirm.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				// TODO Auto-generated method stub

				Device device = Fragment_Device.DEVICE;
				DeviceDataBase db = new DeviceDataBase(context);
				db.deleteDevice(device);
				db.close();
				db.close();
				DeleteDeviceDialog.this.dismiss();
				ChildDeviceTypeDataBase deleteChild = new ChildDeviceTypeDataBase(context);
				deleteChild.deleteChildDeviceByDeviceId(device.get_id());
			}
		});
	}
	
	protected void onStop() {
		// TODO Auto-generated method stub
		super.onStop();
		Util.sendMessage(handler, null);
	}

}
