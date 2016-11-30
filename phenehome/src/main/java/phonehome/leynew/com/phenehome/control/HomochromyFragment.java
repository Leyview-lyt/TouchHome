package phonehome.leynew.com.phenehome.control;

import java.util.ArrayList;
import java.util.List;

import android.annotation.SuppressLint;
import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import phonehome.leynew.com.phenehome.R;
import phonehome.leynew.com.phenehome.damain.Device;
import phonehome.leynew.com.phenehome.fragment.Fragment_Device;

@SuppressLint("NewApi")
public class HomochromyFragment extends Fragment{
	private View view;

	private Context context;
	private Device device = Fragment_Device.DEVICE;
	
	private List<View> list = new ArrayList<View>();
	private ViewPager vp;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		System.out.println("HomochromyFragment==============>onCreate");
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		System.out.println("HomochromyFragment==============>onCreateView");
		view = inflater.inflate(R.layout.homochromy_main, container ,false);
		return view;
	}
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);
		System.out.println("HomochromyFragment==============>onActivityCreated");
		context = view.getContext();
		initView();
		setViewListener();
		//device = (Device) getArguments().getSerializable("device");
		
		getPagerData();
		WF400CAdapter adapter = new WF400CAdapter(context, list, device);
		vp.setAdapter(adapter);
		
	}
	
	private Object $(int id){
		return view.findViewById(id);
	}

	
	private void setViewListener() {
		// TODO Auto-generated method stub
	}
	
	private void getPagerData(){
		LayoutInflater inflater = LayoutInflater.from(view.getContext());
		View v1 = inflater.inflate(R.layout.homochromy, null);
		list.add(v1);
	}

	private void initView() {
		// TODO Auto-generated method stub
		vp = (ViewPager) $(R.id.vp);
		
	}

	
}
