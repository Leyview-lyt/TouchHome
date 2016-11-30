package phonehome.leynew.com.phenehome.control;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;

import phonehome.leynew.com.phenehome.damain.Device;


@SuppressLint({ "NewApi", "ValidFragment" })
public class ThreeDialogFragment extends DialogFragment{
	private Device device;
	private Context context;
	private boolean isUpdate = false;//
	private ThreeColourCustom tcc;
	private Handler handler;

	public ThreeDialogFragment(Context context, Device device, ThreeColourCustom tcc, Handler handler){
		super();
		this.context = context;
		this.device = device;
		if(tcc!=null){
			this.tcc = tcc;
			isUpdate = true;
		}else{
			isUpdate = false;
		}
		this.handler = handler;
	}
	@SuppressLint("NewApi")
	public void onCreate(Bundle savedInstanceState){
		System.out.println("ThreeDialogFragment==============>onCreate");
		super.onCreate(savedInstanceState);
	}

	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState){
		if (isUpdate){
			return new AddThreeCustomDialog(context, device, tcc, handler);
		}else{
			return new AddThreeCustomDialog(context, device, null, handler);
		}
	}

	@SuppressLint("NewApi")
	public void onActivityCreated(Bundle savedInstanceState){
		super.onActivityCreated(savedInstanceState);
	}
}
