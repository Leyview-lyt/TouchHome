package phonehome.leynew.com.phenehome.adapter;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import phonehome.leynew.com.phenehome.R;
import phonehome.leynew.com.phenehome.damain.Device;
import phonehome.leynew.com.phenehome.damain.Lamp;


public class DeviceBoundLVAdapter extends BaseAdapter {

	private Context context;
	private List<Lamp> lamps = new ArrayList<Lamp>();
	private Device initDevice;
	private ImageButton imgbazhang;

	public DeviceBoundLVAdapter(Context context, List<Lamp> lamps, Device initDevice){
		super();
		this.context = context;
		this.lamps = lamps;
		if (initDevice!=null){
			this.initDevice = initDevice;
		}
	}
	
	public int getCount(){
		return lamps.size();
	}

	public Object getItem(int position){
		return lamps.get(position);
	}

	public long getItemId(int position){
		return position;
	}

	public View getView(int position, View convertView, ViewGroup parent){
		convertView = LayoutInflater.from(context).inflate(R.layout.fragment_device_bound_view_list_item, null);
		TextView type = (TextView) convertView.findViewById(R.id.lv_item_type);
		TextView sequence = (TextView) convertView.findViewById(R.id.lv_item_sequence);
		TextView bound = (TextView) convertView.findViewById(R.id.lv_item_bound);
		ImageView img = (ImageView) convertView.findViewById(R.id.lv_item_img);
		Button prohibit = (Button) convertView.findViewById(R.id.lv_item_prohibit);
		imgbazhang = (ImageButton) convertView.findViewById(R.id.bazhang);
		imgbazhang.setVisibility(View.INVISIBLE);
		final Lamp lamp = lamps.get(position);
		type.setText(lamp.getL_type());
		sequence.setText(lamp.getL_sequence());
		if (initDevice!=null && initDevice.getL_id()==lamp.get_id()){
			System.out.println("initDevice!=null && initDevice.getL_id()==lamp.get_id()"+ initDevice.getL_id());
			lamp.setCheck(true);
		}
		if (lamp.isCheck()){
			img.setVisibility(View.VISIBLE);
		}else
			img.setVisibility(View.GONE);
		if (lamp.getD_id()==0){

		}else {
			bound.setVisibility(View.VISIBLE);
			bound.setTextColor(context.getResources().getColor(R.color.magenta));
			type.setTextColor(context.getResources().getColor(R.color.magenta));
			sequence.setTextColor(context.getResources().getColor(R.color.magenta));
			prohibit.setVisibility(View.VISIBLE);
		}
		return convertView;
	}
}
