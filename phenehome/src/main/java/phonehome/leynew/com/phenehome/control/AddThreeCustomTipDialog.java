package phonehome.leynew.com.phenehome.control;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import phonehome.leynew.com.phenehome.R;


public class AddThreeCustomTipDialog extends Dialog {

	private Context context;
	private TextView tip;
	private Button confirm;

	public AddThreeCustomTipDialog(Context context) {
		super(context);
		this.context = context;
		}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		this.setContentView(R.layout.tip_dialog_one_btn);
		System.out.println("29 AddThreeCustomTipDialog");
		initView();
		setViewListener();
	//	title.setText(context.getString(R.string.tip_title));
		tip.setText(context.getString(R.string.tip_custom_overproof));
		
		
	}

	private void setViewListener() {
		// TODO Auto-generated method stub
		confirm.setOnClickListener(confirmClickListener);
		
	}

	private void initView() {
		// TODO Auto-generated method stub
	//	title = (TextView) findViewById(R.id.head_title);
		tip = (TextView) findViewById(R.id.tip_tip);
		confirm = (Button) findViewById(R.id.tip_btn_confirm);
		
	}
	
	private View.OnClickListener confirmClickListener = new View.OnClickListener() {
		
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			AddThreeCustomTipDialog.this.dismiss();
		}
	};
	
	

}
