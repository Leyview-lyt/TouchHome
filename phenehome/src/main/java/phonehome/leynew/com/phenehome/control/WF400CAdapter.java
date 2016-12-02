package phonehome.leynew.com.phenehome.control;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.PagerAdapter;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;


import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import phonehome.leynew.com.phenehome.R;
import phonehome.leynew.com.phenehome.damain.Device;
import phonehome.leynew.com.phenehome.util.LeyNew;
import phonehome.leynew.com.phenehome.util.Util;

public class WF400CAdapter extends PagerAdapter implements OnClickListener,
		OnTouchListener,VerticalSeekBar.OnSeekBarChangeListener {
	private Context context;
	private List<View> list = new ArrayList<View>();
	private View view;
	private Device device;
	private VerticalSeekBar one_sb;
	private TextView one_txt;
	private Button one_btn_open, one_btn_close, one_btn_up, one_btn_down;
	private Timer timer;
	private String childDeviceType;
	static long lastSendInfoTime = 0L;
	static int count = 0;
	private SendInfo sendInfoTarget = null;
	
	public WF400CAdapter(Context context, List<View> list, Device device){
		super();
		this.context = context;
		this.list = list;
		this.device = device;

	}
	
	public int getCount(){
		return list.size();
	}

	public boolean isViewFromObject(View arg0, Object arg1) {
		return arg0==arg1;
	}

	public void destroyItem(ViewGroup container, int position, Object object){
		container.removeView(list.get(position)); 
		SendInfoList.sendInfoFlag = false;
	}
	
	public void executerChildThreadSendInfomation(){
		new Thread(){
			public void run(){
				SendInfoList.sendInfoFlag  = true;
				SendInfoList32X sendInfo = SendInfoList32X.getSendInfoList32XTagert();
				while(SendInfoList.sendInfoFlag){
				sendInfo.sendSingleInfo();
				}
			};
		}.start();
	}

	public Object instantiateItem(ViewGroup container, int position) {
		container.addView(list.get(position));
		if (position==0) {//
			view = list.get(0);
			initView();
			setViewListener();
			//
			one_txt.setText("0%");
			one_sb.setProgress(0);
			if (device.getLamp().getL_type().equals("WF500A")) {
//				if(device.getLamp().getL_status()==false){
//				one_btn_up.setEnabled(false);
//				one_btn_down.setEnabled(false);
//				one_sb.setEnabled(false);
//				}
			}
			if (device.getLamp().getL_type().equals("WF500B")) {
				one_btn_up.setEnabled(false);
				one_btn_down.setEnabled(false);
				one_sb.setEnabled(false);
			}
		}
		return list.get(position);
	}

	private Object $(int id){
		return view.findViewById(id);
	}

	@Override
	public void onClick(View v) {
		String type = device.getLamp().getL_type();
		String sequence = device.getLamp().getL_sequence();
		if(type.equals("WF500A")|| type.equals("WF500B")){
		String str[] = device.getLamp().getL_sequence().split("-");//
		int a=Integer.valueOf(str[1]);
			char d = (char)a;
		switch (v.getId()) {
		case R.id.homochromy_btn_open:
			one_txt.setText("100%");
			one_sb.setProgress(100);
			char c = (char)100;
			if( "WF321".equals(type ) || "WF325".equals(type)){
				String sendInfo = LeyNew.SETON + "00" + device.getLamp().getL_sequence() + "01" + LeyNew.END;
				final byte [] sendData = Util.HexString2Bytes(sendInfo);
				UnionSendInfoClass.sendButtonOrder(sendData);
//				UnionSendInfoClass.sendCommonOrder(sendData);
//				new Thread(){
//					public void run(){
//						Util.sendCommand(sendData, null, null);
//						return ;
//					}
//				}.start();
			}else{
			Util.sendCommand("setbrightness" ,new String[] { String.valueOf(c),String.valueOf(d)},sequence);
			}
			
			break;
			
		case R.id.homochromy_btn_close:
			one_txt.setText("0%");
			one_sb.setProgress(0);
			char c1 = (char)1;
			if(null != type || "WF321".equals(type ) || "WF325".equals(type) ){
				String sendInfo = LeyNew.SETON + "00" + device.getLamp().getL_sequence() + "00" + LeyNew.END;
				final byte [] sendData = Util.HexString2Bytes(sendInfo);
				UnionSendInfoClass.sendButtonOrder(sendData);
			}else{
			Util.sendCommand("setbrightness" ,new String[] { String.valueOf(c1),String.valueOf(d)},sequence);
			}
			break;
		case R.id.homochromy_btn_up:
			int num = one_sb.getProgress() + 1;
			one_txt.setText(num > 100 ? "100%" : num + "%");
			one_sb.setProgress(num > 100 ? 100 : num);
			break;
		case R.id.homochromy_btn_down:
			int num2 = one_sb.getProgress() - 1;
			one_txt.setText(num2 < 0 ? "0%" : num2 + "%");
			one_sb.setProgress(num2 < 0 ? 0 : num2);
			break;
		}
		}else {
		//	String str[] = device.getLamp().getL_sequence().split("-");//�����кŷָ��WF500���кźͲ�ۼ�
			switch (v.getId()) {
			case R.id.homochromy_btn_open:
				char c = (char)100;
				if( "WF321".equals(type ) || "WF325".equals(type)){
					String sendInfo = LeyNew.SETON + "00" + device.getLamp().getL_sequence() + "01" + LeyNew.END;
					final byte [] sendData = Util.HexString2Bytes(sendInfo);
					UnionSendInfoClass.sendButtonOrder(sendData);
				}else{
					one_txt.setText("100%");
					one_sb.setProgress(100);
				}
				break;
			case R.id.homochromy_btn_close:
				if("WF321".equals(type ) || "WF325".equals(type)){
					String sendInfo = LeyNew.SETON +  LeyNew.FLAG + device.getLamp().getL_sequence() + "00"+ LeyNew.END;
					byte [] sendData = Util.HexString2Bytes(sendInfo);
					UnionSendInfoClass.sendButtonOrder(sendData);
//					System.out.println(Util.sendCommand(sendData, null, null));
				}else{
					one_txt.setText("0%");
					one_sb.setProgress(0);	
				}
				break;
			case R.id.homochromy_btn_up:
				int num = one_sb.getProgress() + 1;
				one_txt.setText(num > 100 ? "100%" : num + "%");
				one_sb.setProgress(num > 100 ? 100 : num);
				break;
			case R.id.homochromy_btn_down:
				int num2 = one_sb.getProgress() - 1;
				one_txt.setText(num2 < 0 ? "0%" : num2 + "%");
				one_sb.setProgress(num2 < 0 ? 0 : num2);
				break;
			}
		}
	}
	
	@SuppressLint("NewApi")
	private void setViewListener() {
		// TODO Auto-generated method stub
		one_btn_open.setOnClickListener(this);
		one_btn_close.setOnClickListener(this);
		one_btn_up.setOnClickListener(this);
		one_btn_down.setOnClickListener(this);
		one_btn_down.setOnTouchListener(this);
		one_btn_up.setOnTouchListener(this);
		one_sb.setOnSeekBarChangeListener(this);
	}
	private void initView() {
		one_txt = (TextView) $(R.id.homochromy_txt_brightness);
		one_sb = (VerticalSeekBar) $(R.id.homochromy_sb);
		one_btn_close = (Button) $(R.id.homochromy_btn_close);
		one_btn_open = (Button) $(R.id.homochromy_btn_open);
		one_btn_down = (Button) $(R.id.homochromy_btn_down);
		one_btn_up = (Button) $(R.id.homochromy_btn_up);
	}

	@Override
	public boolean onTouch(View v, MotionEvent event) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.homochromy_btn_up:
			int code = event.getAction();
			System.out.println("code=" + code);
			if (code == 0) {
				TimerTask task = new TimerTask() {
					public void run() {
						Message message = new Message();
						Bundle data = new Bundle();
						data.putString("type", "one");
						data.putString("isupdown", "up");
						message.setData(data);
						timeHandler.sendMessage(message);
					}
				};
				timer = new Timer(true);
				timer.schedule(task, 300, 100);//
			}
			if (code == 1 || code == 3) {
				if (timer != null) {
					one_btn_down.setEnabled(true);//
					timer.cancel();
				}
			}
			break;

		case R.id.homochromy_btn_down:
			int code2 = event.getAction();//
			if (code2 == 0){
				TimerTask task = new TimerTask(){
					public void run() {
						Message message = new Message();
						Bundle data = new Bundle();
						data.putString("type", "one");
						data.putString("isupdown", "down");
						message.setData(data);
						timeHandler.sendMessage(message);
					}
				};
				timer = new Timer(true);
				timer.schedule(task, 300, 100);//
			}
			if (code2 == 1 || code2 == 3){
				if (timer != null){
					one_btn_up.setEnabled(true);//
					timer.cancel();
				}
			}
			break;
		}
		return false;
	}
	
	private Handler timeHandler = new Handler(){
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			Bundle data = msg.getData();
			String type = data.getString("type");
			String isupdown = data.getString("isupdown");
			if (type.equals("one")) {
				if (isupdown.equals("up")) {
					one_btn_down.setEnabled(false);//
					int num = one_sb.getProgress() + 1;
					one_txt.setText(num > 100 ? "100%" : num + "%");
					one_sb.setProgress(num > 100 ? 100 : num);
				} else {
					one_btn_up.setEnabled(false);//���ý���
					int num2 = one_sb.getProgress() - 1;
					one_txt.setText(num2 < 0 ? "0%" : num2 + "%");
					one_sb.setProgress(num2 < 0 ? 0 : num2);
				}
			}
		}
	};

	public void onProgressChanged(VerticalSeekBar VerticalSeekBar,
			int progress, boolean fromUser) {
		switch (VerticalSeekBar.getId()) {
		case R.id.homochromy_sb:
			one_txt.setText(progress > 100 ? "100%" : progress + "%");
			one_sb.setProgress(progress > 100 ? 100 : progress);
			String type = device.getLamp().getL_type();
			String sequence = device.getLamp().getL_sequence();

			if (type.equals("WF400C")||type.equals("TM113")||type.equals("TM111") || type.equals("WF510") || type.equals("NB-1000")) {
				Util.sendCommand("setbrightness", new String[] { progress + "", "254" },sequence);
			} else if (type.equals("WF500A") || type.equals("WF500B")) {
				String str[] = device.getLamp().getL_sequence().split("-");//�����кŷָ��WF500���кźͲ�ۼ�
				char c = (char)progress;
				int a=Integer.valueOf(str[1]);
				char d = (char)a;
				Util.sendCommand("setbrightness" ,new String[] { String.valueOf(c),String.valueOf(d)},str[0]);
			} else if (type.equals("TM111") || type.equals("WF510") || type.equals("TM113") || type.equals("NB-1000")){
				Util.sendCommand("setbrightness", new String[]{progress+"", "254"},sequence);
			} 
			//
			else if(null != type || "WF321".equals(type ) || "WF325".equals(type)){
				final long currentTime = System.currentTimeMillis();
					 int originaSend = progress;
						if(0 == originaSend){
							one_sb.setProgress(1);
							one_txt.setText("1%");
							originaSend = 1;
//							try{
//							Thread.currentThread().sleep(300);
//							}catch(Exception e){
//							}
						}
						String sendOriginaData = Util.integer2HexString(originaSend);
						if(100 == originaSend){
							sendOriginaData = Util.integer2HexString(originaSend - 1);
						}

						String sendData = LeyNew.SETBN + "00" + device.getLamp().getL_sequence() + sendOriginaData + LeyNew.END;



						byte [] sendInfo = Util.HexString2Bytes(sendData);
						UnionSendInfoClass.sendCommonOrder(sendInfo);
					}
			break;
		}
	}

	public void onStartTrackingTouch(VerticalSeekBar VerticalSeekBar){
	}
	

	public void onStopTrackingTouch(VerticalSeekBar VerticalSeekBar){
		final int lastSeekBarValue =  one_sb.getProgress();
		String type = device.getLamp().getL_type();
		if(null != type || "WF321".equals(type ) || "WF325".equals(type)){
			String sendNumberData = Util.integer2HexString(lastSeekBarValue);
			String sendStringData = LeyNew.SETBN + "00" + device.getLamp().getL_sequence() + sendNumberData + LeyNew.END;
			byte [] sendData = Util.HexString2Bytes(sendStringData);
			UnionSendInfoClass.sendSpecialOrder(sendData);
		}
	}
}
