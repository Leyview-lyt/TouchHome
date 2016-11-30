package phonehome.leynew.com.phenehome.adapter;


import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;



import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import phonehome.leynew.com.phenehome.R;
import phonehome.leynew.com.phenehome.damain.Device;

public class DeviceGridAdapter extends BaseAdapter {
	
	private LayoutInflater layoutInflater;
	private List<Device> devices = new ArrayList<Device>();
	private Map<Integer, String> images = new HashMap<Integer, String>();
	private ImageView update;
	private ImageView delete;
	private boolean isShow ;
	private boolean isOpen;
	
	public DeviceGridAdapter(Context context, List<Device> devices,
							 boolean isShow, boolean isOpen) {
		this.isShow = isShow ;
		this.isOpen = isOpen;
		this.devices = devices;
		
		layoutInflater = LayoutInflater.from(context);
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return devices.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return devices.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		Log.i("getView", "position");
		if(convertView==null){
			convertView = layoutInflater.inflate(R.layout.fragment_area_view_grid_item, null);
		}
		Device device = devices.get(position);
	//	LinearLayout gritem = (LinearLayout) convertView.findViewById(R.id.add_grid_item);
		ImageView img = (ImageView) convertView.findViewById(R.id.gridview_item_img);
		update = (ImageView) convertView.findViewById(R.id.update_room);
		delete = (ImageView) convertView.findViewById(R.id.delete_room);
		TextView name = (TextView) convertView.findViewById(R.id.room_name);
		img.setBackgroundResource(Integer.parseInt(device.getImage().getPath()));
		name.setText(device.getD_name());
		if(isShow==true){
			update.setVisibility(View.VISIBLE);
		}
		if(isOpen==true){
			delete.setVisibility(View.VISIBLE);
		}
		System.out.println("getView");
		return convertView;
	}

}
