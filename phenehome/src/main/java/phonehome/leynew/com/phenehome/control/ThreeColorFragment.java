package phonehome.leynew.com.phenehome.control;

import java.util.ArrayList;
import java.util.List;


import android.annotation.SuppressLint;
import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.PopupWindow;

import phonehome.leynew.com.phenehome.R;
import phonehome.leynew.com.phenehome.damain.Device;
import phonehome.leynew.com.phenehome.fragment.Fragment_Device;
import phonehome.leynew.com.phenehome.util.Util;

@SuppressLint({ "NewApi", "ValidFragment" })
public class ThreeColorFragment extends Fragment implements OnClickListener{

	private View view;
	private Context context;
	private Device device = Fragment_Device.DEVICE;
	private List<View> list = new ArrayList<View>();
	private MyViewPager vp;
	private int y = 140;
	private int x;
	
	@SuppressLint("ValidFragment")
	public ThreeColorFragment(PopupWindow popupWindow){
		super();
	}

	@SuppressLint("NewApi")
	@Override
	public void onCreate(Bundle savedInstanceState){
		System.out.println("ThreeColorFragment==============>onCreate");
		super.onCreate(savedInstanceState);
	}

	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState){
		view = inflater.inflate(R.layout.three_colour_main, null);
		return view;
	}

	public void onActivityCreated(Bundle savedInstanceState){
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);
		context = view.getContext();
		initView();
		setViewListener();
		getPagerData();
		int width = Util.getDisplayMetricsWidth(context);
	//	device = (Device) getArguments().getSerializable("device");
		WF400AAdapter adapter = new WF400AAdapter(context, list, device,
				getFragmentManager());
		vp.setAdapter(adapter);
		vp.setCurrentItem(0);
		$(R.id.vp3_left).setVisibility(View.GONE);
		$(R.id.vp3_right).setVisibility(View.VISIBLE);
	}

	private void getPagerData() {
		LayoutInflater inflater = LayoutInflater.from(context);
		View v1 = inflater.inflate(R.layout.three_colour, null);
		View v2 = inflater.inflate(R.layout.three_colour_model, null);
		View v3 = inflater.inflate(R.layout.three_colour_custom, null);
		list.add(v1);
		list.add(v2);
		list.add(v3);
	}

	private void setViewListener() {
		// TODO Auto-generated method stub
		vp.setOnPageChangeListener(pageChangeListener);
		$(R.id.vp3_left).setOnClickListener(this);
		$(R.id.vp3_right).setOnClickListener(this);
	}

	private void initView() {
		// TODO Auto-generated method stub
		vp = (MyViewPager) $(R.id.vp3);
	}

	private View $(int id) {
		return view.findViewById(id);
	}

	private OnPageChangeListener pageChangeListener = new OnPageChangeListener() {
		public void onPageSelected(int arg0) {
			// TODO Auto-generated method stub
			System.out.println("����"+arg0);
		}
		
		@Override
		public void onPageScrolled(int arg0, float arg1, int arg2) {
			// TODO Auto-generated method stub
			//System.out.println("����������"+arg0+","+arg1+","+arg2);
		}
		@Override
		public void onPageScrollStateChanged(int arg0) {
			System.out.println("״̬��"+arg0);
//			if (popupWindow.isShowing()) {
//				popupWindow.dismiss();
//			}
		}
	};
	
	@SuppressLint("NewApi")
	@Override
	public void onStop() {
		// TODO Auto-generated method stub
		super.onStop();
//		if (popupWindow.isShowing()) {
//			popupWindow.dismiss();
//		}
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.vp3_left:
			vp.setCurrentItem(vp.getCurrentItem()-1);
			if (vp.getCurrentItem()==0) {
				$(R.id.vp3_left).setVisibility(View.GONE);
			}
			$(R.id.vp3_right).setVisibility(View.VISIBLE);
			break;
		case R.id.vp3_right:
			vp.setCurrentItem(vp.getCurrentItem()+1);
			if (vp.getCurrentItem()==2) {
				$(R.id.vp3_right).setVisibility(View.GONE);
			}
			$(R.id.vp3_left).setVisibility(View.VISIBLE);
			break;
		default:
			break;
		}
	}
	
}
