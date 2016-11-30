package phonehome.leynew.com.phenehome.plan;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;
import android.widget.TimePicker;

import java.util.Calendar;

import phonehome.leynew.com.phenehome.R;
import phonehome.leynew.com.phenehome.util.Util;


public class EditPlanDateDialog extends Dialog implements
		View.OnClickListener {
	private TimePicker tp;
	private TextView title;
	private Context context;
	private Plan plan;
	private Button btn_mon, btn_tue, btn_wed, btn_thu, btn_fri, btn_sat,
			btn_sun;//
	private Button btn_save, btn_cancel;//
	private boolean[] dates;
	private Handler handler;//

	public EditPlanDateDialog(Context context, Plan plan, Handler handler) {
		super(context);
		this.context = context;
		this.plan = plan;
		this.handler = handler;
	}

	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.plan_item_edit_date);
		System.out.println("33 EditPlanDateDialog");
		initView();
		setViewListener();

		tp.setIs24HourView(true);
		String str[] = (plan.getP_time()).split(":");
		tp.setCurrentHour(Integer.parseInt(str[0]));
		tp.setCurrentMinute(Integer.parseInt(str[1]));
		// title.setText(context.getString(R.string.tip_setting_date));

		dates = Plan.getDayIsck(plan.getP_date());
		if (dates[0])
			btn_mon.setBackgroundResource(R.drawable.textfield_pressed);
		else
			btn_mon.setBackgroundResource(R.drawable.textfield_default);
		if (dates[1])
			btn_tue.setBackgroundResource(R.drawable.textfield_pressed);
		else
			btn_tue.setBackgroundResource(R.drawable.textfield_default);
		if (dates[2])
			btn_wed.setBackgroundResource(R.drawable.textfield_pressed);
		else
			btn_wed.setBackgroundResource(R.drawable.textfield_default);
		if (dates[3])
			btn_thu.setBackgroundResource(R.drawable.textfield_pressed);
		else
			btn_thu.setBackgroundResource(R.drawable.textfield_default);
		if (dates[4])
			btn_fri.setBackgroundResource(R.drawable.textfield_pressed);
		else
			btn_fri.setBackgroundResource(R.drawable.textfield_default);
		if (dates[5])
			btn_sat.setBackgroundResource(R.drawable.textfield_pressed);
		else
			btn_sat.setBackgroundResource(R.drawable.textfield_default);
		if (dates[6])
			btn_sun.setBackgroundResource(R.drawable.textfield_pressed);
		else
			btn_sun.setBackgroundResource(R.drawable.textfield_default);

	}

	private void setViewListener() {
		// TODO Auto-generated method stub
		btn_mon.setOnClickListener(this);
		btn_tue.setOnClickListener(this);
		btn_wed.setOnClickListener(this);
		btn_thu.setOnClickListener(this);
		btn_fri.setOnClickListener(this);
		btn_sat.setOnClickListener(this);
		btn_sun.setOnClickListener(this);
		btn_save.setOnClickListener(this);
		btn_cancel.setOnClickListener(this);
	}

	private void initView() {
		// TODO Auto-generated method stub
		tp = (TimePicker) findViewById(R.id.timePicker1);
		// title = (TextView) findViewById(R.id.head_title);
		btn_mon = (Button) findViewById(R.id.edit_date_mon);
		btn_tue = (Button) findViewById(R.id.edit_date_tue);
		btn_wed = (Button) findViewById(R.id.edit_date_wed);
		btn_thu = (Button) findViewById(R.id.edit_date_thu);
		btn_fri = (Button) findViewById(R.id.edit_date_fri);
		btn_sat = (Button) findViewById(R.id.edit_date_sat);
		btn_sun = (Button) findViewById(R.id.edit_date_sun);
		btn_save = (Button) findViewById(R.id.edit_date_save);
		btn_cancel = (Button) findViewById(R.id.edit_date_cancel);
	}

	public void onClick(View v) {

		switch (v.getId()) {
		case R.id.edit_date_mon:
			if (dates[0])
				btn_mon.setBackgroundResource(R.drawable.textfield_default);
			else
				btn_mon.setBackgroundResource(R.drawable.textfield_pressed);
			dates[0] = !dates[0]; //
			break;
		case R.id.edit_date_tue:
			if (dates[1])
				btn_tue.setBackgroundResource(R.drawable.textfield_default);
			else
				btn_tue.setBackgroundResource(R.drawable.textfield_pressed);
			dates[1] = !dates[1];
			break;
		case R.id.edit_date_wed:
			if (dates[2])
				btn_wed.setBackgroundResource(R.drawable.textfield_default);
			else
				btn_wed.setBackgroundResource(R.drawable.textfield_pressed);
			dates[2] = !dates[2];
			break;
		case R.id.edit_date_thu:
			if (dates[3])
				btn_thu.setBackgroundResource(R.drawable.textfield_default);
			else
				btn_thu.setBackgroundResource(R.drawable.textfield_pressed);
			dates[3] = !dates[3];
			break;
		case R.id.edit_date_fri:
			if (dates[4])
				btn_fri.setBackgroundResource(R.drawable.textfield_default);
			else
				btn_fri.setBackgroundResource(R.drawable.textfield_pressed);
			dates[4] = !dates[4];
			break;
		case R.id.edit_date_sat:
			if (dates[5])
				btn_sat.setBackgroundResource(R.drawable.textfield_default);
			else
				btn_sat.setBackgroundResource(R.drawable.textfield_pressed);
			dates[5] = !dates[5];
			break;
		case R.id.edit_date_sun:
			if (dates[6])
				btn_sun.setBackgroundResource(R.drawable.textfield_default);
			else
				btn_sun.setBackgroundResource(R.drawable.textfield_pressed);
			dates[6] = !dates[6];
			break;
		case R.id.edit_date_save:
			String hour = tp.getCurrentHour() < 10 ? "0" + tp.getCurrentHour()
					: tp.getCurrentHour() + "";
			String minute = tp.getCurrentMinute() < 10 ? "0"
					+ tp.getCurrentMinute() : tp.getCurrentMinute() + "";

			int date = Plan.getInteger(dates);
			plan.setP_date(date);
			plan.setP_time(hour + ":" + minute);
			PlanDataBase db = new PlanDataBase(context);
			db.updateDateTime(plan);
			db.close();

			Bundle data = new Bundle();
			data.putString("isEdit", "true");
			data.putSerializable("Plan", plan);
			Util.sendMessage(handler, data);
			this.dismiss();
			break;
		case R.id.edit_date_cancel:
			this.dismiss();
			break;

		default:
			break;
		}

	}

}
