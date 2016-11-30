package phonehome.leynew.com.phenehome.plan;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import phonehome.leynew.com.phenehome.R;
import phonehome.leynew.com.phenehome.adapter.AreaGVAdapter;
import phonehome.leynew.com.phenehome.damain.Device;
import phonehome.leynew.com.phenehome.damain.Images;
import phonehome.leynew.com.phenehome.damain.Room;
import phonehome.leynew.com.phenehome.db.ImagesDatabase;
import phonehome.leynew.com.phenehome.db.RoomDataBase;



public class PlanEditDialog extends Dialog  {

	private Context context;
	private Plan plan;
	private Button  close;
	private TextView head_title;
	private AreaGVAdapter adapter ;                //
	private List<Room> rooms = new ArrayList<Room>();
	private GridView gv;                             
	private Map<Integer, String> images = new HashMap<Integer, String>();
	//
	private Device device;//
	public static Room P_R;
	
	
	public PlanEditDialog(Context context, Plan plan){
		super(context);
		this.context = context;
		this.plan = plan;
	}

	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		this.setContentView(R.layout.plan_home);
		initView();	
		rooms = getData();
		adapter = new AreaGVAdapter(context, rooms, images, false, false);
		gv.setAdapter(adapter);
		gv.setOnItemClickListener(new ItemClick());
		head_title.setText(context.getString(R.string.plan) + ""
				+ plan.get_id());
	}

	

	private void initView() {
		// TODO Auto-generated method stub
		close = (Button) $(R.id.close_plan);
		head_title = (TextView) $(R.id.head_title);
		gv = (GridView) $(R.id.gridView3);
		close.setOnClickListener(new View.OnClickListener() {
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				PlanEditDialog.this.dismiss();
			}
		});
	}

	private View $(int id) {
		return findViewById(id);
	}


		private List<Room> getData(){
			// TODO Auto-generated method stub
			List<Room> rooms = new ArrayList<Room>();
			ImagesDatabase idb = new ImagesDatabase(context);
			List<Images> list = idb.findRoomImages();
			for (Images img : list){
				images.put(img.get_id(), img.getPath());
			}

			idb.close();

			RoomDataBase rdb = new RoomDataBase(context);
			rooms = rdb.findRoom();
			rdb.close();
			return rooms;
		}
		
		private class ItemClick implements OnItemClickListener{
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				P_R = rooms.get(arg2);
				Toast.makeText(context, "Dialog", Toast.LENGTH_LONG).show();
				PlanEditDialog.this.dismiss();
				PlanEditDeviceDialog dialog = new PlanEditDeviceDialog(context, plan);
				dialog.show();
			}
			
		}
	
	

}
