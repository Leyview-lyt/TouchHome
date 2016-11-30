package phonehome.leynew.com.phenehome.control;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import phonehome.leynew.com.phenehome.R;
import phonehome.leynew.com.phenehome.util.Util;


public class DeleteThreeCustomTipDialog extends Dialog {

	private TextView title,tip;
	private Button confirm,cancel;
	private ThreeColourCustom tcc;
	private Handler handler;
	
	public DeleteThreeCustomTipDialog(Context context, ThreeColourCustom tcc, Handler handler) {
		super(context);
		// TODO Auto-generated constructor stub
		this.tcc = tcc;
		this.handler = handler;
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.tip_dialog);
		System.out.println("32 DeleteThreeCustomTipDialog");
		initView();
		setViewListener();
		title.setText(getContext().getString(R.string.tip_title));
		tip.setText(getContext().getString(R.string.tip_del_custom));
		
		
	}

	private void setViewListener() {
		// TODO Auto-generated method stub
		confirm.setOnClickListener(confirmClickListener);
		cancel.setOnClickListener(cancelClickListener);
		
	}

	private void initView() {
		// TODO Auto-generated method stub
		title = (TextView) findViewById(R.id.head_title);
		tip = (TextView) findViewById(R.id.tip_tip);
		confirm = (Button) findViewById(R.id.tip_btn_confirm);
		cancel = (Button) findViewById(R.id.tip_btn_cancel);
		
	}
	
	private View.OnClickListener confirmClickListener = new View.OnClickListener() {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			ThreeCustomDataBase db = new ThreeCustomDataBase(getContext());
			db.delete(tcc);
			db.close();
			Util.sendMessage(handler, null);
			DeleteThreeCustomTipDialog.this.dismiss();
		}
	};

	private View.OnClickListener cancelClickListener = new View.OnClickListener() {
		
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			DeleteThreeCustomTipDialog.this.dismiss();
		}
	};

}
