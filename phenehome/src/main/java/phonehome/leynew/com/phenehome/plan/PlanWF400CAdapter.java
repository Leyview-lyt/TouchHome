package phonehome.leynew.com.phenehome.plan;

/**
 * WF400C
 */

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.PagerAdapter;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;


import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import phonehome.leynew.com.phenehome.R;
import phonehome.leynew.com.phenehome.control.UnionSendInfoClass;
import phonehome.leynew.com.phenehome.control.VerticalSeekBar;
import phonehome.leynew.com.phenehome.damain.Device;
import phonehome.leynew.com.phenehome.util.LeyNew;
import phonehome.leynew.com.phenehome.util.Util;

public class PlanWF400CAdapter extends PagerAdapter implements OnClickListener,
        OnTouchListener, VerticalSeekBar.OnSeekBarChangeListener {

    private Context context;
    private List<View> list = new ArrayList<View>();

    private View view;
    private Device device;
    private VerticalSeekBar one_sb;
    private TextView one_txt;
    private Button one_btn_open, one_btn_close, one_btn_up, one_btn_down;
    private Timer timer;
    private Handler isUpdateHandler;
    private PlanDeviceStore pds;

    public PlanWF400CAdapter(Context context, List<View> list, Device device,
                             Handler isUpdateHandler, PlanDeviceStore pds) {
        super();
        this.context = context;
        this.list = list;
        this.device = device;
        this.isUpdateHandler = isUpdateHandler;
        this.pds = pds;
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return list.size();
    }

    @Override
    public boolean isViewFromObject(View arg0, Object arg1) {
        return arg0 == arg1;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        // TODO Auto-generated method stub
        container.removeView(list.get(position));
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        // TODO Auto-generated method stub
        container.addView(list.get(position));
        if (position == 0) {//
            view = list.get(0);
            initView();
            setViewListener();

            one_txt.setText(pds.getPds_brightness() + "%");
            Log.i("========","初始化");
            one_sb.setProgress(pds.getPds_brightness());
            if (device.getLamp().getL_type().equals("WF500B")) {
                one_btn_up.setEnabled(false);
                one_btn_down.setEnabled(false);
                one_sb.setEnabled(false);
            }
        }
        return list.get(position);
    }


    private Object $(int id) {
        return view.findViewById(id);
    }

    @Override
    public void onClick(View v) {
        // TODO Auto-generated method stub
        String type = device.getLamp().getL_type();
        String sequence = device.getLamp().getL_sequence();
        int progress = 0;
        if (type.equals("WF500A") || type.equals("WF500B")) {
            String str[] = device.getLamp().getL_sequence().split("-");//
            int a = Integer.valueOf(str[1]);
            char d = (char) a;
            char c = (char) 0;

            switch (v.getId()) {
                case R.id.homochromy_btn_open:
                    System.out.println("");
                    one_txt.setText("100%");
                    one_sb.setProgress(100);
                    progress = 100;
                    c = (char) 100;
                    //	Util.sendCommand("setbrightness" ,new String[] { String.valueOf(c),String.valueOf(d)},str[0]);
                    break;
                case R.id.homochromy_btn_close:
                    one_txt.setText("0%");
                    progress = 0;
                    one_sb.setProgress(0);
                    c = (char) 1;
                    //	Util.sendCommand("setbrightness" ,new String[] { String.valueOf(c),String.valueOf(d)},str[0]);

                    break;
                case R.id.homochromy_btn_up:
                    int num = one_sb.getProgress() + 1;
                    one_txt.setText(num > 100 ? "100%" : num + "%");
                    one_sb.setProgress(num > 100 ? 100 : num);
                    progress = num;
                    c = (char) progress;
                    break;
                case R.id.homochromy_btn_down:
                    int num2 = one_sb.getProgress() - 1;
                    one_txt.setText(num2 < 0 ? "0%" : num2 + "%");
                    one_sb.setProgress(num2 < 0 ? 0 : num2);
                    progress = num2;
                    c = (char) progress;
                    break;
            }
            Util.sendCommand("setbrightness", new String[]{String.valueOf(c), String.valueOf(d)}, str[0]);
        }
        else {
            switch (v.getId()) {
                case R.id.homochromy_btn_open:
                    one_txt.setText("100%");
                    one_sb.setProgress(100);
                    progress = 100;
                    char c = (char) 100;
                    //	Util.sendCommand("setbrightness" ,new String[] { String.valueOf(c),String.valueOf(d)},str[0]);
                    break;
                case R.id.homochromy_btn_close:
                    one_txt.setText("0%");
                    progress = 0;
                    one_sb.setProgress(0);
                    char c1 = (char) 1;
                    //	Util.sendCommand("setbrightness" ,new String[] { String.valueOf(c),String.valueOf(d)},str[0]);

                    break;
                case R.id.homochromy_btn_up:
                    int num = one_sb.getProgress() + 1;
                    one_txt.setText(num > 100 ? "100%" : num + "%");
                    one_sb.setProgress(num > 100 ? 100 : num);
                    progress = num;
                    c = (char) progress;
                    break;
                case R.id.homochromy_btn_down:
                    int num2 = one_sb.getProgress() - 1;
                    one_txt.setText(num2 < 0 ? "0%" : num2 + "%");
                    one_sb.setProgress(num2 < 0 ? 0 : num2);
                    progress = num2;
                    c = (char) progress;
                    break;

            }

            Util.sendCommand("setbrightness", new String[]{progress + "", "254"}, sequence);
            if (timer != null) {
                one_btn_down.setEnabled(true);//
                one_btn_up.setEnabled(true);//
                timer.cancel();
            }

        }
        updated();
    }


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
        // TODO Auto-generated method stub
        System.out.println("13 PlanWF400CAdapter");
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
                if (code2 == 0) {
                    TimerTask task = new TimerTask() {
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
                if (code2 == 1 || code2 == 3) {
                    if (timer != null) {
                        one_btn_up.setEnabled(true);//
                        timer.cancel();
                    }
                }
                break;
        }
        updated();
        return false;
    }


    private Handler timeHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            // TODO Auto-generated method stub
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
                    one_btn_up.setEnabled(false);//
                    int num2 = one_sb.getProgress() - 1;
                    one_txt.setText(num2 < 0 ? "0%" : num2 + "%");
                    one_sb.setProgress(num2 < 0 ? 0 : num2);
                }
            }
        }
    };

    @Override
    public void onProgressChanged(VerticalSeekBar VerticalSeekBar,
                                  int progress, boolean fromUser) {
        // TODO Auto-generated method stub
        switch (VerticalSeekBar.getId()) {
            case R.id.homochromy_sb:
                one_txt.setText(progress > 100 ? "100%" : progress + "%");
                one_sb.setProgress(progress > 100 ? 100 : progress);
                String type = device.getLamp().getL_type();
                String sequence = device.getLamp().getL_sequence();
                //
                if (type.equals("WF400C")) {
                    Util.sendCommand("setbrightness", new String[]{progress + "", "254"}, sequence);
                } else if (type.equals("WF500A") || type.equals("WF500B")) {
                    String str[] = device.getLamp().getL_sequence().split("-");//
                    char c = (char) progress;
                    int a = Integer.valueOf(str[1]);
                    char d = (char) a;
                    Util.sendCommand("setbrightness", new String[]{String.valueOf(c), String.valueOf(d)}, str[0]);
                    //Util.sendCommand("setbrightness", null,str[0], new int[]{progress, Integer.parseInt(str[1]) });
                } else if (type.equals("TM111") || type.equals("WF510") || type.equals("TM113") || type.equals("NB-1000")) {
                    Util.sendCommand("setbrightness", new String[]{progress + "", "254"}, sequence);
                }

                if (null != type && type.equals("WF321")) {
                    String sendData = LeyNew.SETBN + "00" + device.getLamp().getL_sequence() + Util.integer2HexString(progress) + LeyNew.END;
                    byte[] finalSendByte = Util.HexString2Bytes(sendData);
                    UnionSendInfoClass.sendCommonOrder(finalSendByte);
                }

                if (progress != pds.getPds_brightness()) {
                    updated();
                }
                break;
        }
    }

    @Override
    public void onStartTrackingTouch(VerticalSeekBar VerticalSeekBar) {
    }

    @Override
    public void onStopTrackingTouch(VerticalSeekBar verticalSeekBar) {
// 		TODO Auto-generated method stub
        if (null != device.getLamp().getL_type() && device.getLamp().getL_type().equals("WF321")) {
//		String sendData = LeyNew.SETBN + "00" + device.getLamp().getL_sequence() + sendOriginaData + LeyNew.END;
            switch (verticalSeekBar.getId()) {
                case R.id.homochromy_sb:
                    int process = one_sb.getProgress();
                    String brightnessValue = Util.integer2HexString(process);
                    String sendData = LeyNew.SETBN + "00" + device.getLamp().getL_sequence() + brightnessValue + LeyNew.END; //�˴����������ڻ�ȡ�������仯���һ���ֵ �����䷢�ͳ�ȥ��
                    byte[] finalSendByte = Util.HexString2Bytes(sendData);
                    UnionSendInfoClass.sendSpecialOrder(finalSendByte);
                    updated();
                    break;
                default:
                    break;
            }
        }
    }

    private void updated() {
        Bundle data = new Bundle();
        pds.setPds_brightness(one_sb.getProgress());
        data.putSerializable("pds", pds);
        Util.sendMessage(isUpdateHandler, data);
    }


}
