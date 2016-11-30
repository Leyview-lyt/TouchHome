package phonehome.leynew.com.phenehome.adapter;


import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;


import java.util.ArrayList;
import java.util.List;

import phonehome.leynew.com.phenehome.R;
import phonehome.leynew.com.phenehome.damain.Lamp;
import phonehome.leynew.com.phenehome.util.LeyNew;
import phonehome.leynew.com.phenehome.util.Util;

public class SettingDeviceGVAdapter extends BaseAdapter {

	private Context context;
	private List<Lamp> list = new ArrayList<Lamp>();
	private Handler handler;
	private View tempView;
	private int tempPosition;
	private int status;
	private String tempSequence;
	
	public SettingDeviceGVAdapter(Context context, List<Lamp> list, Handler handler) {
		super();
		this.context = context;
		this.list = list;
		this.handler = handler;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return list.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return list.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
	    System.out.println("20 SettingDeviceGVAdapter");
		ViewHolder holder = null;
		if (convertView == null) {
			holder = new ViewHolder();
			convertView = LayoutInflater.from(context).inflate(R.layout.me_searchdivece_item, null);
			holder.img = (ImageView) convertView.findViewById(R.id.setting_gv_img);
			holder.Img_check = (ImageView) convertView.findViewById(R.id.setting_gv_img_check);
			holder.txt = (TextView) convertView.findViewById(R.id.setting_gv_txt);
			holder.txt_sequence = (TextView) convertView.findViewById(R.id.setting_gv_txt_sequence);
			convertView.setTag(holder);
		}else {
			holder = (ViewHolder) convertView.getTag();
		}
		Lamp lamp = list.get(position);
		holder.txt.setText(lamp.getL_type());
		holder.txt_sequence.setText(lamp.getL_sequence());
		if (lamp.isCheck()) {
			holder.Img_check.setVisibility(View.VISIBLE);
		}else {
			holder.Img_check.setVisibility(View.GONE);
		}
		if (lamp.getL_type().equals("WF400A") ||
				lamp.getL_type().equals("WF400B") ||
				lamp.getL_type().equals("WF400C") ) {
			holder.img.setBackgroundResource(R.drawable.wf400);
		} else if (lamp.getL_type().equals("WF500A") ||
				lamp.getL_type().equals("WF500B") ||
				lamp.getL_type().equals("WF500")) {
			holder.img.setBackgroundResource(R.drawable.wf500);
		} else if (lamp.getL_type().equals("TM111") || lamp.getL_type().equals("TM113")) {
			holder.img.setBackgroundResource(R.drawable.tm111);
		} else if (lamp.getL_type().equals("WF510") || lamp.getL_type().equals("NB-1000")) {
			holder.img.setBackgroundResource(R.drawable.wf510);
		} else if (lamp.getL_type().equals("WF310")) {
			holder.img.setBackgroundResource(R.drawable.wf310);
		}
		holder.img.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {

				if (tempView!=null) {
					if (tempPosition<list.size()) {
						list.get(tempPosition).setCheck(false);
					}
				}
				list.get(position).setCheck(true);
				tempView = v;
				tempPosition = position;
				SettingDeviceGVAdapter.this.notifyDataSetChanged();
				Bundle data = new Bundle();
				data.putInt("position", position);
				Util.sendMessage(handler, data);
				if (status==0) {
					tempSequence = list.get(position).getL_sequence();
				}
				new Thread(){
					public void run() {//
						try {
							status = 1;
							Util.sendCommand(LeyNew.SETONOFF, new String[]{0 + "", "254"},
									tempSequence);
							Thread.sleep(300);
							Util.sendCommand(LeyNew.SETONOFF, new String[]{1 + "", "254"},
									tempSequence);
							Thread.sleep(300);
							Util.sendCommand(LeyNew.SETONOFF, new String[]{0 + "", "254"},
									tempSequence);
							Thread.sleep(300);
							Util.sendCommand(LeyNew.SETONOFF, new String[]{1 + "", "254"},
									tempSequence);
							status = 0;
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					};
				}.start();
			}
		});
		
		return convertView;
	}

	
	
	class ViewHolder{
		ImageView img;
		ImageView Img_check;
		TextView txt;
		TextView txt_sequence;
	}
}
