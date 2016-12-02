package phonehome.leynew.com.phenehome.plan;

/**
 * WF400B
 * 
 */

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;



import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import phonehome.leynew.com.phenehome.R;
import phonehome.leynew.com.phenehome.control.TwoColourCustom;
import phonehome.leynew.com.phenehome.control.TwoCustomDataBase;
import phonehome.leynew.com.phenehome.control.TwoGvAdapter;
import phonehome.leynew.com.phenehome.control.UnionSendInfoClass;
import phonehome.leynew.com.phenehome.control.VerticalSeekBar;
import phonehome.leynew.com.phenehome.damain.Device;
import phonehome.leynew.com.phenehome.util.LeyNew;
import phonehome.leynew.com.phenehome.util.Util;

public class PlanWF400BAdapter extends PagerAdapter implements OnClickListener,
		OnTouchListener, VerticalSeekBar.OnSeekBarChangeListener,
		SeekBar.OnSeekBarChangeListener {
	private List<View> list = new ArrayList<View>();
	private Context context;
	private View view;
	private Device device;
	private Timer timer;
	private Timer timer2;


	private VerticalSeekBar two_sb_brs, two_sb_warm;
	private TextView two_txt_brs, two_txt_warm;
	private Button two_btn_brs_open, two_btn_brs_close, two_btn_brs_up,
			two_btn_brs_down;
	private Button two_btn_warm_open, two_btn_warm_close, two_btn_warm_up,
			two_btn_warm_down;


	private SeekBar two_custom_sb_cool, two_custom_sb_warm, two_custom_sb_brs;
	private TextView two_custom_txt_cool, two_custom_txt_warm,
			two_custom_txt_brs;
	private LinearLayout two_custom_rl;
	private TextView two_custom_txt_color;
	private GridView two_custom_gv;
	private List<TwoColourCustom> tccs = new ArrayList<TwoColourCustom>();
	private TwoGvAdapter twoGvAdapter;
	private Handler isUpdateHandler;
	private PlanDeviceStore pds;
	private int tempPosition = 0;
	private ViewPager vp;
	private LinearLayout main;
	public PlanWF400BAdapter(Context context, List<View> list, Device device,
			Handler isUpdateHandler, PlanDeviceStore pds, ViewPager vp){
		super();
		this.context = context;
		this.list = list;
		this.device = device;
		this.isUpdateHandler = isUpdateHandler;
		this.pds = pds;
		this.vp = vp;
	}

	public int getCount(){
		return list.size();
	}

	public boolean isViewFromObject(View arg0, Object arg1){
		return arg0 == arg1;
	}

	public void destroyItem(ViewGroup container, int position, Object object){
		container.removeView(list.get(position));
	}

	public Object instantiateItem(ViewGroup container, int position){
		container.addView(list.get(position));
		if (position == 0) {
			view = list.get(0);
			initView1();
			setViewListener();
			setViewListener1();

			two_txt_brs.setText(pds.getPds_brightness() + "%");
			two_txt_warm.setText(pds.getPds_colour() + "%");
			// two_txt_warm.setVisibility(View.GONE);
			two_sb_brs.setProgress(pds.getPds_brightness());
			two_sb_warm.setProgress(pds.getPds_colour());
		} else{
			view = list.get(1);
			initView2();
			setViewListener2();
			two_custom_sb_brs.setProgress(pds.getPds_brightness2());
			two_custom_sb_warm.setProgress(pds.getPds_colour_warm());
			two_custom_sb_cool.setProgress(pds.getPds_colour_cool());
			two_custom_txt_color
					.setText(pds.getPds_colour_cool() + "_"
							+ pds.getPds_colour_warm() + "_"
							+ pds.getPds_brightness2());
			two_custom_txt_cool.setText(pds.getPds_colour_cool() + "%");
			two_custom_txt_brs.setText(pds.getPds_brightness2() + "%");
			two_custom_txt_warm.setText(pds.getPds_colour_warm() + "%");
			two_custom_rl.setBackgroundColor(Color.rgb(
					(int) (two_custom_sb_cool.getProgress() * 2.5),
					(int) (two_custom_sb_warm.getProgress() * 2.5),
					(int) (two_custom_sb_brs.getProgress() * 2.5)));
			getTccsData();
			twoGvAdapter = new TwoGvAdapter(context, tccs);
			two_custom_gv.setAdapter(twoGvAdapter);
		}
		return list.get(position);
	}

	private Object $(int id){
		return view.findViewById(id);
	}

	private void getTccsData(){
		tccs.clear();
		TwoCustomDataBase db = new TwoCustomDataBase(context);
		tccs = db.findAllTCC(device.get_id());
		db.close();
	}


	private void updated() {
		Bundle data = new Bundle(); //
		PlanDeviceStore pds = new PlanDeviceStore();
		pds.set_id(this.pds.get_id());
		if (tempPosition == 0) {//页面1
			if (device.getLamp().getL_type().equals("WF322") || device.getLamp().getL_type().equals("WF326")) {
				//
				int collWarm = two_sb_warm.getProgress();
				int brightNess = two_sb_brs.getProgress();
				if(100 == collWarm)
					collWarm = 99 ;
				if(100 == brightNess)
					brightNess = 99 ;
				pds.setPds_brightness(brightNess);
				pds.setPds_colour(collWarm);

				pds.setPds_colour_cool(99 - collWarm);
				pds.setPds_colour_warm(collWarm);
			}
			else {
				pds.setPds_brightness(two_sb_brs.getProgress());
				pds.setPds_colour(two_sb_warm.getProgress());

				pds.setPds_brightness2(0);
				pds.setPds_colour_cool(0);
				pds.setPds_colour_warm(0);
				two_custom_sb_brs.setProgress(0);
				two_custom_sb_warm.setProgress(0);
				two_custom_sb_cool.setProgress(0);
				two_custom_txt_color.setText("0_0_0");
				two_custom_txt_cool.setText("0%");
				two_custom_txt_brs.setText("0%");
				two_custom_txt_warm.setText("0%");
				two_custom_rl.setBackgroundColor(Color.rgb(0, 0, 0));
			}

		}
		else if (tempPosition == 1){
			if (device.getLamp().getL_type().equals("WF322")
					|| device.getLamp().getL_type().equals("WF326")){
				pds.setPds_brightness(two_custom_sb_brs.getProgress());
				pds.setPds_colour(two_custom_sb_warm.getProgress());
				pds.setPds_brightness2(two_custom_sb_brs.getProgress());
				pds.setPds_colour_cool(two_custom_sb_cool.getProgress());
				pds.setPds_colour_warm(two_custom_sb_warm.getProgress());
			} else{
				pds.setPds_brightness(0);
				pds.setPds_colour(0);
				pds.setPds_brightness2(two_custom_sb_brs.getProgress());
				pds.setPds_colour_cool(two_custom_sb_cool.getProgress());
				pds.setPds_colour_warm(two_custom_sb_warm.getProgress());

				two_txt_brs.setText("0%");
				two_txt_warm.setText("0%");
				// two_txt_warm.setVisibility(View.GONE);
				two_sb_brs.setProgress(0);
				two_sb_warm.setProgress(0);
			}
		}
		data.putSerializable("pds", pds);
		Util.sendMessage(isUpdateHandler, data);
	}

	private void setViewListener() {
		vp.setOnPageChangeListener(pageChangeListener);
	}

	private void setViewListener1() {
		two_btn_brs_open.setOnClickListener(this);
		two_btn_brs_close.setOnClickListener(this);
		two_btn_brs_up.setOnClickListener(this);
		two_btn_brs_down.setOnClickListener(this);
		two_btn_brs_up.setOnTouchListener(this);
		two_btn_brs_down.setOnTouchListener(this);
		two_btn_warm_open.setOnClickListener(this);
		two_btn_warm_close.setOnClickListener(this);
		two_btn_warm_up.setOnClickListener(this);
		two_btn_warm_down.setOnClickListener(this);
		two_btn_warm_up.setOnTouchListener(this);
		two_btn_warm_down.setOnTouchListener(this);
		two_sb_brs.setOnSeekBarChangeListener((VerticalSeekBar.OnSeekBarChangeListener) this);
		two_sb_warm.setOnSeekBarChangeListener((VerticalSeekBar.OnSeekBarChangeListener) this);
	}

	private void setViewListener2(){
		two_custom_sb_cool.setOnSeekBarChangeListener(this);
		two_custom_sb_warm.setOnSeekBarChangeListener(this);
		two_custom_sb_brs.setOnSeekBarChangeListener(this);
		two_custom_gv.setOnItemClickListener(itemClickListener);
	}

	private void initView1(){
		// TODO Auto-generated method stub
		System.out.println("14 PlanWF400BAdapter");
		main = (LinearLayout) $(R.id.two_tone);
		int a = 685;
		int b = 300;
		FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(a, b);
		two_btn_brs_close = (Button) $(R.id.two_btn_brs_close);
		two_btn_brs_down = (Button) $(R.id.two_btn_brs_down);
		two_btn_brs_open = (Button) $(R.id.two_btn_brs_open);
		two_btn_brs_up = (Button) $(R.id.two_btn_brs_up);
		two_btn_warm_close = (Button) $(R.id.two_btn_warm_close);
		two_btn_warm_open = (Button) $(R.id.two_btn_warm_open);
		two_btn_warm_down = (Button) $(R.id.two_btn_warm_down);
		two_btn_warm_up = (Button) $(R.id.two_btn_warm_up);
		two_sb_brs = (VerticalSeekBar) $(R.id.two_sb_brs);
		two_sb_warm = (VerticalSeekBar) $(R.id.two_sb_warm);
		two_txt_brs = (TextView) $(R.id.two_txt_brs);
		two_txt_warm = (TextView) $(R.id.two_txt_warm);
	}

	private void initView2() {
		two_custom_gv = (GridView) $(R.id.two_custom_gv);
		two_custom_rl = (LinearLayout) $(R.id.two_custom_rl);
		two_custom_sb_brs = (SeekBar) $(R.id.two_custom_sb_brs);
		two_custom_sb_cool = (SeekBar) $(R.id.two_custom_sb_cool);
		two_custom_sb_warm = (SeekBar) $(R.id.two_custom_sb_warm);
		two_custom_txt_brs = (TextView) $(R.id.two_custom_txt_brs);
		two_custom_txt_color = (TextView) $(R.id.two_custom_txt_color);
		two_custom_txt_cool = (TextView) $(R.id.two_custom_txt_cool);
		two_custom_txt_warm = (TextView) $(R.id.two_custom_txt_warm);
	}

	private OnItemClickListener itemClickListener = new OnItemClickListener() {
		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id) {
			two_custom_sb_cool.setProgress(tccs.get(position).getT_cool());
			two_custom_sb_warm.setProgress(tccs.get(position).getT_warm());
			two_custom_sb_brs.setProgress(tccs.get(position).getT_brightness());
		}
	};

	@Override
	public void onProgressChanged(VerticalSeekBar VerticalSeekBar,
			int progress, boolean fromUser) {

		if ("WF322".equals(device.getLamp().getL_type()) || "WF326".equals(device.getLamp().getL_type())){
			switch (VerticalSeekBar.getId()) {
			case R.id.two_sb_brs://
				two_txt_brs.setText(100 < progress ? "100%" : progress + "%");
				int sb_brs_one = two_sb_brs.getProgress();
				if (100 == sb_brs_one)
					sb_brs_one = 99;
				String brightness = Util.integer2HexString(sb_brs_one);
				String flag = "00";
				String brightnesValue = LeyNew.SETBN + flag
						+ device.getLamp().getL_sequence() + brightness
						+ LeyNew.END;
				UnionSendInfoClass.sendCommonOrder(Util
						.HexString2Bytes(brightnesValue));
				if (sb_brs_one != pds.getPds_brightness()) {
					updated();
				}
				break;

			case R.id.two_sb_warm: //
				two_txt_warm.setText(progress > 100 ? "100%" : progress + "%");
				two_sb_warm.setProgress(progress > 100 ? 100 : progress);
				int sb_warm_one = two_sb_warm.getProgress();
				if (100 == sb_warm_one)
					sb_warm_one = 99;
				final String warmValue = Util.integer2HexString(sb_warm_one);
				final byte[] sendWarmData = Util.HexString2Bytes(LeyNew.SETCH
						+ LeyNew.FLAG + device.getLamp().getL_sequence() + "02"
						+ warmValue + warmValue + "00" + LeyNew.END);
				UnionSendInfoClass.sendCommonOrder(sendWarmData);
				if (sb_warm_one != pds.getPds_colour_warm()){
					updated();
				}
				break;
			default:
				break;
			}
		}
		else {
			switch (VerticalSeekBar.getId()) {
			case R.id.two_sb_brs:
				two_txt_brs.setText(progress > 100 ? "100%" : progress + "%");
				two_sb_brs.setProgress(progress > 100 ? 100 : progress);
				if (two_sb_brs.getProgress() != pds.getPds_brightness()) {
					updated();
				}
				Util.sendCommand(LeyNew.SETBRIGHTNESS, new String[] {
						progress + "", "254" }, device.getLamp()
						.getL_sequence());
				break;
			case R.id.two_sb_warm:
				two_txt_warm.setText(progress > 100 ? "100%" : progress + "%");
				two_sb_warm.setProgress(progress > 100 ? 100 : progress);
				if (two_sb_warm.getProgress() != pds.getPds_colour()) {
					updated();
				}
				Util.sendCommand(LeyNew.SETCOLORTEM, new String[] { progress + "" },
						device.getLamp().getL_sequence());
				break;
			default:
				break;
			}
		}
	}

	@Override
	public void onStartTrackingTouch(VerticalSeekBar verticalSeekBar) {
		// TODO Auto-generated method stub
	}

	@Override
	public void onStopTrackingTouch(VerticalSeekBar verticalSeekBar) {
		// TODO Auto-generated method stub
		String productType = device.getLamp().getL_type();
		if ("WF322".equals(productType) || "WF326".equals(productType)) {
			switch (verticalSeekBar.getId()) {
			//
			case R.id.two_sb_brs:
				int brightNess = two_sb_brs.getProgress();
				if (100 == brightNess)
					brightNess = 99;
				String brightness = Util.integer2HexString(brightNess);
				String flag = "00";
				String brightnesValue = LeyNew.SETBN + flag
						+ device.getLamp().getL_sequence() + brightness
						+ LeyNew.END;
				UnionSendInfoClass.sendSpecialOrder(Util
						.HexString2Bytes(brightnesValue));
				if (brightNess != pds.getPds_brightness()) {
					updated();
				}

				break;
			case R.id.two_sb_warm:
				int colorWarm = two_sb_warm.getProgress();
				if (100 == colorWarm)
					colorWarm = 99;
				final String warmValue = Util.integer2HexString(colorWarm);
				final byte[] sendWarmData = Util.HexString2Bytes(LeyNew.SETCH
						+ LeyNew.FLAG + device.getLamp().getL_sequence() + "02"
						+ warmValue + warmValue + "00" + LeyNew.END);
				UnionSendInfoClass.sendSpecialOrder(sendWarmData);
				if (colorWarm != pds.getPds_colour())
					updated();
				break;

			default:
				break;
			}
		}
	}

	public boolean onTouch(View v, MotionEvent event) {
		switch (v.getId()) {
		case R.id.two_btn_brs_up:
			int code3 = event.getAction();
			if (code3 == 0) {
				TimerTask task = new TimerTask() {
					public void run() {
						Message message = new Message();
						Bundle data = new Bundle();
						data.putString("type", "two");
						data.putString("isupdown", "brs_up");
						message.setData(data);
						timeHandler.sendMessage(message);
					}
				};
				timer = new Timer(true);
				timer.schedule(task, 300, 100);//
			}
			if (code3 == 1 || code3 == 3) {
				if (timer != null) {
					two_btn_brs_down.setEnabled(true);
					timer.cancel();
				}
			}
			break;

		case R.id.two_btn_brs_down:
			int code4 = event.getAction();//
			if (code4 == 0) {
				TimerTask task = new TimerTask() {
					public void run() {
						Message message = new Message();
						Bundle data = new Bundle();
						data.putString("type", "two");
						data.putString("isupdown", "brs_down");
						message.setData(data);
						timeHandler.sendMessage(message);
					}
				};
				timer = new Timer(true);
				timer.schedule(task, 300, 100);//
			}
			if (code4 == 1 || code4 == 3) {
				if (timer != null) {
					two_btn_brs_up.setEnabled(true);
					timer.cancel();
				}
			}
			break;

		case R.id.two_btn_warm_up:
			int code5 = event.getAction();//
			if (code5 == 0) {
				TimerTask task = new TimerTask() {
					public void run() {
						Message message = new Message();
						Bundle data = new Bundle();
						data.putString("type", "two");
						data.putString("isupdown", "warm_up");
						message.setData(data);
						timeHandler.sendMessage(message);
					}
				};
				timer2 = new Timer(true);
				timer2.schedule(task, 300, 100);//
			}
			if (code5 == 1 || code5 == 3) {
				if (timer2 != null) {
					two_btn_warm_down.setEnabled(true);
					timer2.cancel();
				}
			}
			break;
		case R.id.two_btn_warm_down:
			int code6 = event.getAction();//
			if (code6 == 0) {

				TimerTask task = new TimerTask() {
					public void run() {
						Message message = new Message();
						Bundle data = new Bundle();
						data.putString("type", "two");
						data.putString("isupdown", "warm_down");
						message.setData(data);
						timeHandler.sendMessage(message);
					}
				};
				timer2 = new Timer(true);
				timer2.schedule(task, 300, 100);//
			}
			if (code6 == 1 || code6 == 3) {
				if (timer2 != null) {
					two_btn_warm_up.setEnabled(true);
					timer2.cancel();
				}
			}
			break;
		default:
			break;
		}
		updated();
		return false;
	}

	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.two_btn_brs_open:
			two_txt_brs.setText("100%");
			two_sb_brs.setProgress(100);
			break;
		case R.id.two_btn_brs_close:
			two_txt_brs.setText("0%");
			two_sb_brs.setProgress(0);
			break;
		case R.id.two_btn_warm_open:
			two_txt_warm.setText("100%");
			two_sb_warm.setProgress(100);
			break;
		case R.id.two_btn_warm_close:
			two_txt_warm.setText("0%");
			two_sb_warm.setProgress(0);
			break;
		case R.id.two_btn_brs_up:
			int num3 = two_sb_brs.getProgress() + 1;
			two_txt_brs.setText(num3 > 100 ? "100%" : num3 + "%");
			two_sb_brs.setProgress(num3 > 100 ? 100 : num3);
			break;
		case R.id.two_btn_brs_down:
			int num4 = two_sb_brs.getProgress() - 1;
			two_txt_brs.setText(num4 < 0 ? "0%" : num4 + "%");
			two_sb_brs.setProgress(num4 < 0 ? 0 : num4);
			break;
		case R.id.two_btn_warm_up:
			int num5 = two_sb_warm.getProgress() + 1;
			two_txt_warm.setText(num5 > 100 ? "100%" : num5 + "%");
			two_sb_warm.setProgress(num5 > 100 ? 100 : num5);
			break;
		case R.id.two_btn_warm_down:
			int num6 = two_sb_warm.getProgress() - 1;
			two_txt_warm.setText(num6 < 0 ? "0%" : num6 + "%");
			two_sb_warm.setProgress(num6 < 0 ? 0 : num6);
			break;
		}
		updated();
	}


	private Handler timeHandler = new Handler() {

		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			super.handleMessage(msg);
			Bundle data = msg.getData();
			String type = data.getString("type");
			String isupdown = data.getString("isupdown");
			if (type.equals("two")) {
				if (isupdown.equals("brs_up")) {
					two_btn_brs_down.setEnabled(false);
					int num = two_sb_brs.getProgress() + 1;
					two_txt_brs.setText(num > 100 ? "100%" : num + "%");
					two_sb_brs.setProgress(num > 100 ? 100 : num);
				} else if (isupdown.equals("brs_down")) {
					two_btn_brs_up.setEnabled(false);
					int num2 = two_sb_brs.getProgress() - 1;
					two_txt_brs.setText(num2 < 0 ? "0%" : num2 + "%");
					two_sb_brs.setProgress(num2 < 0 ? 0 : num2);
				} else if (isupdown.equals("warm_up")) {
					two_btn_warm_down.setEnabled(false);
					int num = two_sb_warm.getProgress() + 1;
					two_txt_warm.setText(num > 100 ? "100%" : num + "%");
					two_sb_warm.setProgress(num > 100 ? 100 : num);
				} else if (isupdown.equals("warm_down")) {
					two_btn_warm_up.setEnabled(false);
					int num2 = two_sb_warm.getProgress() - 1;
					two_txt_warm.setText(num2 < 0 ? "0%" : num2 + "%");
					two_sb_warm.setProgress(num2 < 0 ? 0 : num2);
				}
			}
		}
	};


	public void onProgressChanged(SeekBar seekBar, int progress,
			boolean fromUser) {
		// TODO Auto-generated method stub
		switch (seekBar.getId()) {
		case R.id.two_custom_sb_cool:
			two_custom_txt_cool.setText(progress > 100 ? "100%" : progress
					+ "%");

			two_custom_txt_color.setText(two_custom_sb_cool.getProgress() + "_"
					+ two_custom_sb_warm.getProgress() + "_"
					+ two_custom_sb_brs.getProgress());

			two_custom_rl.setBackgroundColor(Color.rgb(
							(int) (two_custom_sb_brs.getProgress() * (two_custom_sb_warm.getProgress() * 0.0255)),
							(int) (two_custom_sb_brs.getProgress() * (two_custom_sb_warm.getProgress() * 0.0255)),
							(int) (two_custom_sb_brs.getProgress() * (two_custom_sb_cool.getProgress() * 0.0255))));
			
			if(null != device.getLamp().getL_type() && "WF322".equals(device.getLamp().getL_type())){
				int blueValue = (int)(two_custom_sb_cool.getProgress() * two_custom_sb_brs.getProgress() * 0.0255);
				int redValue  = (int)(two_custom_sb_warm.getProgress() * two_custom_sb_brs.getProgress() * 0.0255);
				if(255 == blueValue){
					blueValue = 254;}
				if(255 == redValue){
					redValue = 254;}
				int coolColor = two_custom_sb_cool.getProgress();
				if(100 == coolColor){
					coolColor = 99;}
				String coolValue = Util.integer2HexString(coolColor);
					final byte [] sendValue = Util.HexString2Bytes(LeyNew.SETCH + "00" + device.getLamp().getL_sequence() + "02" + coolValue + coolValue +"01"+ LeyNew.END);
					UnionSendInfoClass.sendCommonOrder(sendValue);
					if (two_custom_sb_cool.getProgress() != pds.getPds_colour_cool()){
						updated();
						break;
					}
			}else{
				Util.sendCommand(LeyNew.SETCOLORTEM,
						new String[] {
								two_custom_sb_brs.getProgress()
										* two_custom_sb_cool.getProgress() * 0.0255
										+ "",
								two_custom_sb_brs.getProgress()
										* two_custom_sb_warm.getProgress() * 0.0255
										+ "" }, device.getLamp().getL_sequence());
			}
			if (two_custom_sb_cool.getProgress() != pds.getPds_colour_cool())
				updated();
			break;
		case R.id.two_custom_sb_warm:
			two_custom_txt_warm.setText(progress > 100 ? "100%" : progress
					+ "%");
			two_custom_txt_color.setText(two_custom_sb_cool.getProgress() + "_"
					+ two_custom_sb_warm.getProgress() + "_"
					+ two_custom_sb_brs.getProgress());
			two_custom_rl.setBackgroundColor(Color.rgb(
							(int) (two_custom_sb_brs.getProgress() * (two_custom_sb_warm.getProgress() * 0.0255)),
							(int) (two_custom_sb_brs.getProgress() * (two_custom_sb_warm.getProgress() * 0.0255)),
							(int) (two_custom_sb_brs.getProgress() * (two_custom_sb_cool.getProgress() * 0.0255))));
			
			if(null != device.getLamp().getL_sequence() && "WF322".equals(device.getLamp().getL_type())){
				int blueValue = (int)(two_custom_sb_cool.getProgress() * two_custom_sb_brs.getProgress() * 0.0255);
				int redValue  = (int)(two_custom_sb_warm.getProgress() * two_custom_sb_brs.getProgress() * 0.0255);
				if(255 == blueValue){
					blueValue = 254;}
				if(255 == redValue){
					redValue = 254;}
				int warmColor  = two_custom_sb_warm.getProgress();
				if(warmColor == 100){
					warmColor = 99;}
				String warmValue = Util.integer2HexString(warmColor);
				final byte [] sendValue = Util.HexString2Bytes(LeyNew.SETCH + "00" + device.getLamp().getL_sequence() + "02" + warmValue + warmValue +"02"+ LeyNew.END);
				UnionSendInfoClass.sendCommonOrder(sendValue);
			}else{
				Util.sendCommand(LeyNew.SETCOLORTEM,
						new String[] {
								two_custom_sb_brs.getProgress()
										* two_custom_sb_cool.getProgress() * 0.0255
										+ "",
								two_custom_sb_brs.getProgress()
										* two_custom_sb_warm.getProgress() * 0.0255
										+ "" }, device.getLamp().getL_sequence());
			}
			
			if (two_custom_sb_warm.getProgress() != pds.getPds_colour_warm())
				updated();
			break;
		case R.id.two_custom_sb_brs:
			two_custom_txt_brs
					.setText(progress > 100 ? "100%" : progress + "%");
			two_custom_txt_color.setText(two_custom_sb_cool.getProgress() + "_"
					+ two_custom_sb_warm.getProgress() + "_"
					+ two_custom_sb_brs.getProgress());
			two_custom_rl
					.setBackgroundColor(Color.rgb(
							(int) (two_custom_sb_brs.getProgress() * (two_custom_sb_warm
									.getProgress() * 0.0255)),
							(int) (two_custom_sb_brs.getProgress() * (two_custom_sb_warm
									.getProgress() * 0.0255)),
							(int) (two_custom_sb_brs.getProgress() * (two_custom_sb_cool
									.getProgress() * 0.0255))));
			if(null != device.getLamp().getL_type() && "WF322".equals(device.getLamp().getL_type())){
				int blueValue = (int)(two_custom_sb_cool.getProgress() * two_custom_sb_brs.getProgress() * 0.0255);
				int redValue  = (int)(two_custom_sb_warm.getProgress() * two_custom_sb_brs.getProgress() * 0.0255);
				if(255 == blueValue){
					blueValue = 254;}
				if(255 == redValue){
					redValue = 254;}
				int brightness = two_custom_sb_brs.getProgress();
				if(100 == brightness){
					brightness = 99;
				}
				String sendValue = Util.integer2HexString(brightness);
				String brightnesValue = LeyNew.SETBN + "00" + device.getLamp().getL_sequence() + sendValue + LeyNew.END; 
				final byte [] info = Util.HexString2Bytes(brightnesValue);
				UnionSendInfoClass.sendCommonOrder(info);
				if(two_custom_sb_brs.getProgress() != pds.getPds_brightness()){
					updated();
					break;
				}
			}else{
				Util.sendCommand(LeyNew.SETCOLORTEM,
				new String[] {
								two_custom_sb_brs.getProgress()
								* two_custom_sb_cool.getProgress() * 0.0255
								+ "",two_custom_sb_brs.getProgress()
								* two_custom_sb_warm.getProgress() * 0.0255
								+ "" }, device.getLamp().getL_sequence());
				Util.sendCommand(LeyNew.SETBRIGHTNESS, new String[] {
						two_custom_sb_brs.getProgress() + "", "254" }, device
						.getLamp().getL_sequence());
			}
			if (two_custom_sb_brs.getProgress() != pds.getPds_brightness2())
				updated();
			break;
		default:
			break;
		}
	}

	public void onStartTrackingTouch(SeekBar seekBar) {
	}



	public void onStopTrackingTouch(SeekBar seekBar) {
		if (device.getLamp().getL_type().equals("WF322")){
			switch (seekBar.getId()) {
			case R.id.two_custom_sb_brs: //

				break;

			case R.id.two_custom_sb_warm: //

				break;

			case R.id.two_custom_sb_cool: //

				break;

			default:
				break;
			}
		}
	}

		private OnPageChangeListener pageChangeListener = new OnPageChangeListener() {
		public void onPageSelected(int arg0) {
			System.out.println("0000000000" + arg0);
			tempPosition = arg0;
		}

		public void onPageScrolled(int arg0, float arg1, int arg2) {
		}

		public void onPageScrollStateChanged(int arg0) {
		}
	};
}
