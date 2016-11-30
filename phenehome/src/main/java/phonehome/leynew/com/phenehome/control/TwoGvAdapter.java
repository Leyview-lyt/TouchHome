package phonehome.leynew.com.phenehome.control;


import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;



import java.util.ArrayList;
import java.util.List;

import phonehome.leynew.com.phenehome.R;

public class TwoGvAdapter extends BaseAdapter {

	private Context context;
	private List<TwoColourCustom> list = new ArrayList<TwoColourCustom>();
	
	public TwoGvAdapter(Context context, List<TwoColourCustom> list) {
		super();
		this.context = context;
		this.list = list;
		
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
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		System.out.println("22 TwoGvAdapter");
		convertView = LayoutInflater.from(context).inflate(R.layout.two_tone_custom_item, null);
		LinearLayout two_custom_rl = (LinearLayout) convertView.findViewById(R.id.two_custom_item_rl);
		TextView two_custom_txt_color = (TextView) convertView.findViewById(R.id.two_custom_txt_color);
		TwoColourCustom tcc = list.get(position);
		two_custom_txt_color.setText(tcc.getT_cool()+"_"+tcc.getT_warm()+
				"_"+tcc.getT_brightness());
		
		two_custom_rl.setBackgroundColor(Color.rgb(
				(int)(tcc.getT_cool()*2.5),
				(int)(tcc.getT_warm()*2.5),
				(int)(tcc.getT_brightness()*2.5)));
		
		return convertView;
	}

}
