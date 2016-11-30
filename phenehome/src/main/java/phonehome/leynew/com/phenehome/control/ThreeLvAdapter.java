package phonehome.leynew.com.phenehome.control;

import android.annotation.SuppressLint;
import android.app.FragmentManager;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;


import java.util.ArrayList;
import java.util.List;

import phonehome.leynew.com.phenehome.R;
import phonehome.leynew.com.phenehome.damain.Device;


@SuppressLint("NewApi")
public class ThreeLvAdapter extends BaseAdapter{

	private Context context;
	private List<ThreeColourCustom> list = new ArrayList<ThreeColourCustom>();    //
	private Handler handler;
	private FragmentManager manager;
	private Device device;
	private boolean isAddNew;
	
	public ThreeLvAdapter(Context context, List<ThreeColourCustom> list, Handler handler,
			Device device, FragmentManager manager, boolean isAddNew){
		super();
		this.context = context;
		this.list = list;
		this.handler = handler;
		this.device = device;
		this.manager = manager;
		this.isAddNew = isAddNew;
	}

	public int getCount(){
		return list.size();
	}

	public Object getItem(int position){
		return list.get(position);
	}

	public long getItemId(int position){
		return position;
	}

	//
	public static int dp2px(Context context, float dpValue){
		final float scale = context.getResources().getDisplayMetrics().density;
		return (int)(dpValue * scale + 0.5f);
	}
	
	public View getView(final int position, View convertView, ViewGroup parent){
		// TODO Auto-generated method stub
		System.out.println("21 ThreeLvAdapter");
		convertView = LayoutInflater.from(context).inflate(R.layout.three_colour_custom_item, null);
		ThreeColourCustom tcc = list.get(position);
		if(tcc.isChecked()){
			((RelativeLayout)convertView.findViewById(R.id.three_addcustom_item_rl))
			.setBackgroundColor(Color.rgb(211, 211, 211));
		}else{
			((RelativeLayout)convertView.findViewById(R.id.three_addcustom_item_rl))
			.setBackgroundColor(Color.alpha(0));
		}
		TextView name = (TextView) convertView.findViewById(R.id.three_custom_item_txt_name);
		final ImageButton edit = (ImageButton)convertView.findViewById(R.id.three_custom_item_imgbtn_edit);
		if (manager==null){
			edit.setVisibility(View.GONE);
		}
		//
		ImageView r1c1, r1c2, r1c3, r1c4;
		ImageView r2c1, r2c2, r2c3, r2c4;
		ImageView r3c1, r3c2, r3c3, r3c4;
		ImageView r4c1, r4c2, r4c3, r4c4;

		r1c1 = (ImageView) convertView.findViewById(R.id.three_addcustom_ll_row1_column1);
		r1c2 = (ImageView) convertView.findViewById(R.id.three_addcustom_ll_row1_column2);
		r1c3 = (ImageView) convertView.findViewById(R.id.three_addcustom_ll_row1_column3);
		r1c4 = (ImageView) convertView.findViewById(R.id.three_addcustom_ll_row1_column4);
		r2c1 = (ImageView) convertView.findViewById(R.id.three_addcustom_ll_row2_column1);
		r2c2 = (ImageView) convertView.findViewById(R.id.three_addcustom_ll_row2_column2);
		r2c3 = (ImageView) convertView.findViewById(R.id.three_addcustom_ll_row2_column3);
		r2c4 = (ImageView) convertView.findViewById(R.id.three_addcustom_ll_row2_column4);
		r3c1 = (ImageView) convertView.findViewById(R.id.three_addcustom_ll_row3_column1);
		r3c2 = (ImageView) convertView.findViewById(R.id.three_addcustom_ll_row3_column2);
		r3c3 = (ImageView) convertView.findViewById(R.id.three_addcustom_ll_row3_column3);
		r3c4 = (ImageView) convertView.findViewById(R.id.three_addcustom_ll_row3_column4);
		r4c1 = (ImageView) convertView.findViewById(R.id.three_addcustom_ll_row4_column1);
		r4c2 = (ImageView) convertView.findViewById(R.id.three_addcustom_ll_row4_column2);
		r4c3 = (ImageView) convertView.findViewById(R.id.three_addcustom_ll_row4_column3);
		r4c4 = (ImageView) convertView.findViewById(R.id.three_addcustom_ll_row4_column4);
		name.setText(tcc.getT_name());
		List<ThreeColourSave> tcss = tcc.getList();
		if(tcss.get(0).getStatus()==1)
			r1c1.setBackgroundColor(Color.rgb(resultR(0, tcss), resultG(0, tcss), resultB(0, tcss)));
		if(tcss.get(1).getStatus()==1)
			r1c2.setBackgroundColor(Color.rgb(resultR(1, tcss), resultG(1, tcss), resultB(1, tcss)));
		if(tcss.get(2).getStatus()==1)
			r1c3.setBackgroundColor(Color.rgb(resultR(2, tcss), resultG(2, tcss), resultB(2, tcss)));
		if(tcss.get(3).getStatus()==1)
			r1c4.setBackgroundColor(Color.rgb(resultR(3, tcss), resultG(3, tcss), resultB(3, tcss)));
		if(tcss.get(4).getStatus()==1)
			r2c1.setBackgroundColor(Color.rgb(resultR(4, tcss), resultG(4, tcss), resultB(4, tcss)));
		if(tcss.get(5).getStatus()==1)
			r2c2.setBackgroundColor(Color.rgb(resultR(5, tcss), resultG(5, tcss), resultB(5, tcss)));
		if(tcss.get(6).getStatus()==1)
			r2c3.setBackgroundColor(Color.rgb(resultR(6, tcss), resultG(6, tcss), resultB(6, tcss)));
		if(tcss.get(7).getStatus()==1)
			r2c4.setBackgroundColor(Color.rgb(resultR(7, tcss), resultG(7, tcss), resultB(7, tcss)));
		if(tcss.get(8).getStatus()==1)
			r3c1.setBackgroundColor(Color.rgb(resultR(8, tcss), resultG(8, tcss), resultB(8, tcss)));
		if(tcss.get(9).getStatus()==1)
			r3c2.setBackgroundColor(Color.rgb(resultR(9, tcss), resultG(9, tcss), resultB(9, tcss)));
		if(tcss.get(10).getStatus()==1)
			r3c3.setBackgroundColor(Color.rgb(resultR(10, tcss), resultG(10, tcss), resultB(10, tcss)));
		if(tcss.get(11).getStatus()==1)
			r3c4.setBackgroundColor(Color.rgb(resultR(11, tcss), resultG(11, tcss), resultB(11, tcss)));
		if(tcss.get(12).getStatus()==1)
			r4c1.setBackgroundColor(Color.rgb(resultR(12, tcss), resultG(12, tcss), resultB(12, tcss)));
		if(tcss.get(13).getStatus()==1)
			r4c2.setBackgroundColor(Color.rgb(resultR(13, tcss), resultG(13, tcss), resultB(13, tcss)));
		if(tcss.get(14).getStatus()==1)
			r4c3.setBackgroundColor(Color.rgb(resultR(14, tcss), resultG(14, tcss), resultB(14, tcss)));
		if(tcss.get(15).getStatus()==1)
			r4c4.setBackgroundColor(Color.rgb(resultR(15, tcss), resultG(15, tcss), resultB(15, tcss)));
		
		//
		edit.setOnClickListener(new OnClickListener(){
			@SuppressWarnings("deprecation")
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				View view = LayoutInflater.from(context).inflate(R.layout.three_colour_custom_popup, null);
				final PopupWindow pw = new PopupWindow(view, dp2px(context, 74), dp2px(context, 64));
				//
				pw.setFocusable(true);
				//
				pw.setOutsideTouchable(true);
				//
				pw.setBackgroundDrawable(new BitmapDrawable());
				pw.setAnimationStyle(android.R.style.Animation_Toast);
				if (!pw.isShowing()) {
					pw.showAsDropDown(edit,-110,-140);
				}
				Button edit = (Button) view.findViewById(R.id.custom_popup_edit);
				Button delete = (Button) view.findViewById(R.id.custom_popup_delete);
				edit.setOnClickListener(new OnClickListener() {
					public void onClick(View v){
						pw.dismiss();
						System.out.println("");
						ThreeDialogFragment dialog = new ThreeDialogFragment(context, device, list.get(position), handler);
						dialog.show(manager, "addNew");
					}
				});
				delete.setOnClickListener(new OnClickListener(){
					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						pw.dismiss();
						DeleteThreeCustomTipDialog dialog = new DeleteThreeCustomTipDialog(
								context, list.get(position), handler);
						dialog.show();
					}
				});
			}
		});
		return convertView;
	}
	

	private int resultR(int current,List<ThreeColourSave> list){
		return (int) (list.get(current).getR()*(list.get(current).getDiaphaneity()/100.0));
	}
	

	private int resultG(int current,List<ThreeColourSave> list){
		return (int) (list.get(current).getG()*(list.get(current).getDiaphaneity()/100.0));
	}
	

	private int resultB(int current,List<ThreeColourSave> list){
		return (int) (list.get(current).getB()*(list.get(current).getDiaphaneity()/100.0));
	}
	
}
