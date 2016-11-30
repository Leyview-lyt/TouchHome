package phonehome.leynew.com.phenehome.plan;


import android.annotation.SuppressLint;
import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;



@SuppressLint({"NewApi", "ValidFragment"})
public class PlanEditDialogFragment extends DialogFragment {

	private Plan plan;



	@SuppressLint({"NewApi", "ValidFragment"})
	public PlanEditDialogFragment(Plan plan) {
		this.plan = plan;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		System.out.println("PlanEditDialogFragment==============>onCreate");
		super.onCreate(savedInstanceState);
	}

	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		return new PlanEditDialog(getActivity(), plan);
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);
	}
	
	

}
