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
import phonehome.leynew.com.phenehome.damain.Room;

public class AreaGVAdapter extends BaseAdapter {
	
	private LayoutInflater layoutInflater;
	private List<Room> rooms = new ArrayList<Room>();
	private Map<Integer, String> images = new HashMap<Integer, String>();
	private ImageView update;
	private ImageView delete;
	private boolean isShow ;
	private boolean isOpen;
	
	public AreaGVAdapter(Context context, List<Room> rooms, Map<Integer, String> images, boolean isShow, boolean isOpen) {
		this.isShow = isShow ;
		this.isOpen = isOpen;
		this.rooms = rooms;
		this.images = images;
		layoutInflater = LayoutInflater.from(context);
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return rooms.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return rooms.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}


	@Override
	public View getView(int position, View convertView, ViewGroup parent){
		// TODO Auto-generated method stub
		Log.i("getView", "position");
		if(convertView==null){
			convertView = layoutInflater.inflate(R.layout.fragment_area_view_grid_item, null);
		}
		Room room = rooms.get(position);
	//	LinearLayout gritem = (LinearLayout) convertView.findViewById(R.id.add_grid_item);
		ImageView img = (ImageView) convertView.findViewById(R.id.gridview_item_img);
		update = (ImageView) convertView.findViewById(R.id.update_room);
		delete = (ImageView) convertView.findViewById(R.id.delete_room);
		TextView name = (TextView) convertView.findViewById(R.id.room_name);
		img.setBackgroundResource(room.getR_path());
		name.setText(room.getR_name());
		if(isShow==true){
			update.setVisibility(View.VISIBLE);
		}
		if(isOpen==true){
			delete.setVisibility(View.VISIBLE);
		}
		System.out.println("getView");
		return convertView;
	}

	// TODO: 2016/10/31/031 ViesHold没添加进去
	class ViewHold{

	}


}
