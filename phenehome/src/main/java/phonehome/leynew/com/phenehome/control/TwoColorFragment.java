package phonehome.leynew.com.phenehome.control;

import java.util.ArrayList;
import java.util.List;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Fragment;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;

import phonehome.leynew.com.phenehome.R;
import phonehome.leynew.com.phenehome.damain.Device;
import phonehome.leynew.com.phenehome.fragment.Fragment_Device;

@SuppressLint("NewApi")
public class TwoColorFragment extends Fragment implements OnClickListener {

	private View view;
	private Context context;
	private Device device = Fragment_Device.DEVICE;

	private List<View> list = new ArrayList<View>();
	private MyViewPager vp;

	@SuppressLint("NewApi")
	@Override
	public void onCreate(Bundle savedInstanceState){
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		System.out.println("TwoColorFragment==============>onCreate");
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		System.out.println("TwoColorFragment==============>onCreateView");
		view = inflater.inflate(R.layout.two_tone_main, container, false);
		return view;
	}

	@TargetApi(Build.VERSION_CODES.HONEYCOMB)
	@SuppressLint("NewApi")
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);
		context = view.getContext();
		initView();
		setViewListener();
	//	device = (Device) getArguments().getSerializable("device");
		getPagerData();
		WF400BAdapter adapter = new WF400BAdapter(context, list, device);
		vp.setAdapter(adapter);
		vp.setCurrentItem(0);
		$(R.id.vp2_left).setVisibility(View.GONE);
		$(R.id.vp2_right).setVisibility(View.VISIBLE);
	}

	private View $(int id){
		return view.findViewById(id);
	}

	private void getPagerData() {
		LayoutInflater inflater = LayoutInflater.from(context);
		View v1 = inflater.inflate(R.layout.two_tone, null);
		View v2 = inflater.inflate(R.layout.two_tone_custom, null);
		list.add(v1);
		list.add(v2);
	}

	private void setViewListener() {
		// TODO Auto-generated method stub
		$(R.id.vp2_left).setOnClickListener(this);
		$(R.id.vp2_right).setOnClickListener(this);
	}

	private void initView() {
		// TODO Auto-generated method stub
		vp = (MyViewPager) $(R.id.vp2);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.vp2_left:
			vp.setCurrentItem(0);
			$(R.id.vp2_left).setVisibility(View.GONE);
			$(R.id.vp2_right).setVisibility(View.VISIBLE);
			break;
		case R.id.vp2_right:
			vp.setCurrentItem(1);
			$(R.id.vp2_left).setVisibility(View.VISIBLE);
			$(R.id.vp2_right).setVisibility(View.GONE);
			break;

		default:
			break;
		}
		
	}

}
