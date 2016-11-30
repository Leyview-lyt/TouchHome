package phonehome.leynew.com.phenehome.control;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.Button;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;


import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import phonehome.leynew.com.phenehome.R;
import phonehome.leynew.com.phenehome.damain.Device;
import phonehome.leynew.com.phenehome.util.LeyNew;
import phonehome.leynew.com.phenehome.util.Util;

public class WF400BAdapter extends PagerAdapter implements OnClickListener,
        OnTouchListener, VerticalSeekBar.OnSeekBarChangeListener,
        SeekBar.OnSeekBarChangeListener {
    private List<View> list = new ArrayList<View>();
    private Context context;
    private View view;
    private Device device;
    private Timer timer;
    private Timer timer2;
    private Timer timerdxy;


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
    private LinearLayout main;


    private String childDeviceType;
    private String doubleRedValue;
    private String doubleGreenValue;

    static int redValue = 0;
    static int greenValue = 0;
    static int brightnessProcessValue = 0;


    static Long sendInfoTime = 0L;
    static Long currentTime = 0L;

    static int check = 0;
    SingleSend322Info send322Target = null;

    public WF400BAdapter(Context context, List<View> list, Device device) {
        super();
        this.context = context;
        this.list = list;
        this.device = device;
        timerdxy = new Timer(false);
    }


    public int getCount() {
        return list.size();
    }

    public boolean isViewFromObject(View arg0, Object arg1) {
        return arg0 == arg1;
    }

    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView(list.get(position));
        //
        SendInfoList.wf322BrightnessFlag = false;
        SendInfoList.wf322CoodAndWarFlag = false;
        SendInfoList.wf322CoodFlag = false;
        SendInfoList.wf322SecondBrightnessFlag = false;
        SendInfoList.wf322WarmFlag = false;
    }

    public Object instantiateItem(ViewGroup container, int position) {
        container.addView(list.get(position));
        //
        if (position == 0) {
            view = list.get(0);
            initView1();
            setViewListener1();
            //
            two_txt_brs.setText("0%");
            two_txt_warm.setText("0%");
            two_sb_brs.setProgress(0);
            two_sb_warm.setProgress(0);
        }
        //
        else {
            view = list.get(1);
            initView2();
            setViewListener2();
            two_custom_sb_brs.setProgress(0);
            two_custom_sb_warm.setProgress(0);
            two_custom_sb_cool.setProgress(0);
            two_custom_txt_color.setText("0_0_0");
            two_custom_txt_cool.setText("0%");
            two_custom_txt_brs.setText("0%");
            two_custom_txt_warm.setText("0%");
            two_custom_rl.setBackgroundColor(Color.rgb(0, 0, 0));
            getTccsData();
            twoGvAdapter = new TwoGvAdapter(context, tccs);
            two_custom_gv.setAdapter(twoGvAdapter);
        }
        return list.get(position);
    }

    private Object $(int id) {
        return view.findViewById(id);
    }


    private void getTccsData() {
        tccs.clear();
        TwoCustomDataBase db = new TwoCustomDataBase(context);
        tccs = db.findAllTCC(device.get_id());
        db.close();
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
        two_sb_brs.setOnSeekBarChangeListener(this);
        two_sb_warm.setOnSeekBarChangeListener(this);
        two_sb_brs.setOnTouchListener(new BrightnessAndColor());
        two_sb_warm.setOnTouchListener(new BrightnessAndColor());
    }

    //
    private class BrightnessAndColor implements OnTouchListener {
        public boolean onTouch(View v, MotionEvent event) {
            switch (v.getId()) {
                case R.id.two_sb_brs:
                    if (1 == event.getAction()) {
                        int brightness = two_sb_brs.getProgress();
                        String toHex = Util.integer2HexString(brightness);
                        String flag = "00";
                        String brightnesValue = LeyNew.SETBN + flag + device.getLamp().getL_sequence() + toHex + LeyNew.END;
                        final byte[] brightnessByte = Util.HexString2Bytes(brightnesValue);
                        new Thread() {
                            public void run() {
                                try {
                                    Thread.currentThread().sleep(300);
                                    Util.sendCommandForZigbeeResult(brightnessByte);
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }
                            }
                        }.start();
                    }
                    break;
                case R.id.two_sb_warm:
                    if (1 == event.getAction()) {
                        int colorWarm = two_sb_warm.getProgress();
                        String fistrColor = Util.integer2HexString(colorWarm);
                        String bright = LeyNew.SETCH + LeyNew.FLAG + device.getLamp().getL_sequence() + "02" + fistrColor + fistrColor + "00" + LeyNew.END;
                        final byte[] sendData = Util.HexString2Bytes(bright);
                        new Thread() {
                            public void run() {
                                try {
                                    Thread.currentThread().sleep(300);
                                    Util.sendCommandForZigbeeResult(sendData);
                                } catch (Exception e) {
                                }
                            }
                        }.start();
                    }
                    break;
                default:
                    break;
            }
            return false;
        }
    }

    private void setViewListener2() {
        two_custom_sb_cool.setOnSeekBarChangeListener(this);
        two_custom_sb_warm.setOnSeekBarChangeListener(this);
        two_custom_sb_brs.setOnSeekBarChangeListener(this);
        two_custom_rl.setOnClickListener(this);
        two_custom_gv.setOnItemLongClickListener(itemLongClickListener);
        two_custom_gv.setOnItemClickListener(itemClickListener);
    }

    private void initView1() {
        System.out.println("24 WF400BAdapter");
        main = (LinearLayout) $(R.id.two_tone);
        ViewPager.LayoutParams params = (ViewPager.LayoutParams) main.getLayoutParams();
        System.out.println(params.height + " =params.height " + params.width + " =params.width");
        main.setLayoutParams(params);
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

    private OnItemLongClickListener itemLongClickListener = new OnItemLongClickListener() {
        public boolean onItemLongClick(AdapterView<?> parent, View view,
                                       int position, long id) {
            TwoCustomDataBase db = new TwoCustomDataBase(context);
            db.delete(tccs.get(position));
            db.close();
            tccs.remove(position);
            twoGvAdapter.notifyDataSetChanged();
            return true;
        }
    };

    private OnItemClickListener itemClickListener = new OnItemClickListener() {
        public void onItemClick(AdapterView<?> parent, View view, int position,
                                long id) {
            two_custom_sb_cool.setProgress(tccs.get(position).getT_cool());
            two_custom_sb_warm.setProgress(tccs.get(position).getT_warm());
            two_custom_sb_brs.setProgress(tccs.get(position).getT_brightness());
            // TODO: 2016/11/29/029 发送指令,更新信息
        }
    };


    public void onProgressChanged(VerticalSeekBar VerticalSeekBar,
                                  final int progress, boolean fromUser) {
        String deviceType = device.getLamp().getL_type();
        switch (VerticalSeekBar.getId()) {
            case R.id.two_sb_brs:
                two_txt_brs.setText(progress > 100 ? "100%" : progress + "%");
                two_sb_brs.setProgress(progress > 100 ? 100 : progress);
                if (deviceType.equals("WF322") || deviceType.equals("WF326")) {
                    brightnessProcessValue = progress;
                    String brightness = Util.integer2HexString(progress);
                    String flag = "00";
                    String brightnesValue = LeyNew.SETBN + flag + device.getLamp().getL_sequence() + brightness + LeyNew.END;
                    final byte[] brightnessByte = Util.HexString2Bytes(brightnesValue);
                    UnionSendInfoClass.sendCommonOrder(brightnessByte);
                } else {
                    Util.sendCommand(LeyNew.SETBRIGHTNESS, new String[]{progress + "", "254"},
                            device.getLamp().getL_sequence());
                }
                break;
            case R.id.two_sb_warm:
                two_txt_warm.setText(progress > 100 ? "100%" : progress + "%");
                two_sb_warm.setProgress(progress > 100 ? 100 : progress);
                if (deviceType.equals("WF322") || deviceType.equals("WF326")) {
                    String flag = "02";
                    final int value = progress;
                    String firstColor = Util.integer2HexString(value);
                    if (100 == progress) {
                        firstColor = Util.integer2HexString(value - 1);
                    }
                    String bright = LeyNew.SETCH + LeyNew.FLAG + device.getLamp().getL_sequence() + "02" + firstColor + firstColor + "00" + LeyNew.END;
                    final byte[] controlDoubleColor = Util.HexString2Bytes(bright);
                    UnionSendInfoClass.sendCommonOrder(controlDoubleColor);
                } else {
                    Util.sendCommand(LeyNew.SETCOLORTEM, new String[]{progress + ""},
                            device.getLamp().getL_sequence());
                }
                break;
            default:
                break;
        }
    }

    @Override
    public void onStartTrackingTouch(VerticalSeekBar verticalSeekBar) {
    }

    public void onStopTrackingTouch(VerticalSeekBar verticalSeekBar) {
        String deviceType = device.getLamp().getL_type();
        switch (verticalSeekBar.getId()) {
            case R.id.two_sb_brs:
                if (null != deviceType || deviceType.equals("WF322") || deviceType.equals("WF326")) {
                    final int value = two_sb_brs.getProgress();
                    final String flag = "00";
                    final String brightness = Util.integer2HexString(value);
                    String brightnesValue = LeyNew.SETBN + flag + device.getLamp().getL_sequence() + brightness + LeyNew.END;
                    final byte[] sendBrightnessByteData = Util.HexString2Bytes(brightnesValue);
                    UnionSendInfoClass.sendSpecialOrder(sendBrightnessByteData);
                }
                break;
            case R.id.two_sb_warm:
                if (null != deviceType || deviceType.equals("WF322") || deviceType.equals("WF326")) {
                    int warmColor = two_sb_warm.getProgress();
                    if (100 == warmColor) {
                        warmColor = 99;
                    }
                    final String warmValue = Util.integer2HexString(warmColor);
                    final byte[] sendWarmData = Util.HexString2Bytes(LeyNew.SETCH + LeyNew.FLAG + device.getLamp().getL_sequence() + "02" + warmValue + warmValue + "00" + LeyNew.END);
                    UnionSendInfoClass.sendSpecialOrder(sendWarmData);
                }
                break;
            default:
                break;
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
        return false;
    }

    public boolean judgeContainVariable(String var) {
        boolean judgeFlag = false;
        if (null != device.getLamp().getL_type() && device.getLamp().getL_type().equals(var)) {
            judgeFlag = true;
        }
        return judgeFlag;
    }


    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.two_btn_brs_open:
                if (this.judgeContainVariable("WF322") || this.judgeContainVariable("WF326")) {
                    String sendInfo = LeyNew.SETON + LeyNew.FLAG + device.getLamp().getL_sequence() + "01" + LeyNew.END;
                    final byte[] sendData = Util.HexString2Bytes(sendInfo);
                    TimerTask open_task = new TimerTask() {
                        public void run() {
                            new Thread() {
                                public void run() {
                                    Util.sendCommandForZigbeeResult(sendData);
                                }
                            }.start();
                        }
                    };
                    Timer myTimer = new Timer(true);
                    myTimer.schedule(open_task, 100);
                } else {
                    two_txt_brs.setText("100%");
                    two_sb_brs.setProgress(100);
                }
                break;
            case R.id.two_btn_brs_close:
                if (this.judgeContainVariable("WF322") || this.judgeContainVariable("WF326")) {
                    String sendInfo = LeyNew.SETON + LeyNew.FLAG + device.getLamp().getL_sequence() + "00" + LeyNew.END;
                    final byte[] sendData = Util.HexString2Bytes(sendInfo);
                    new Thread() {
                        public void run() {
                            Util.sendCommandForZigbeeResult(sendData);
                        }
                    }.start();
                } else {
                    two_txt_brs.setText("0%");
                    two_sb_brs.setProgress(0);
                }
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

            case R.id.two_custom_rl:
                TwoCustomDataBase db = new TwoCustomDataBase(context);
                TwoColourCustom tcc = new TwoColourCustom();
                tcc.setD_id(device.get_id());
                tcc.setT_brightness(two_custom_sb_brs.getProgress());
                tcc.setT_cool(two_custom_sb_cool.getProgress());
                tcc.setT_warm(two_custom_sb_warm.getProgress());
                db.save(tcc);
                tccs.clear();
                List<TwoColourCustom> list = db.findAllTCC(device.get_id());
                for (TwoColourCustom tc : list) {
                    tccs.add(tc);
                }
                db.close();
                twoGvAdapter.notifyDataSetChanged();
                break;
        }
    }

    private Handler timeHandler = new Handler() {
        public void handleMessage(Message msg) {
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
        switch (seekBar.getId()) {
            case R.id.two_custom_sb_cool: //
                two_custom_txt_cool.setText(progress > 100 ? "100%" : progress + "%");

                two_custom_txt_color.setText(
                        two_custom_sb_cool.getProgress() + "_"
                        + two_custom_sb_warm.getProgress() + "_"
                        + two_custom_sb_brs.getProgress());

                two_custom_rl.setBackgroundColor(Color.rgb(
                        (int) (two_custom_sb_brs.getProgress() * (two_custom_sb_warm.getProgress() * 0.0255)),
                        (int) (two_custom_sb_brs.getProgress() * (two_custom_sb_warm.getProgress() * 0.0255)),
                        (int) (two_custom_sb_brs.getProgress() * (two_custom_sb_cool.getProgress() * 0.0255))));
                if (this.judgeContainVariable("WF322") || this.judgeContainVariable("WF326")) {
                    int blueValue = (int) (two_custom_sb_cool.getProgress() * two_custom_sb_brs.getProgress() * 0.0255);
                    int redValue = (int) (two_custom_sb_warm.getProgress() * two_custom_sb_brs.getProgress() * 0.0255);
                    if (255 == blueValue) {
                        blueValue = 254;
                    }
                    if (255 == redValue) {
                        redValue = 254;
                    }
                    int coolColor = two_custom_sb_cool.getProgress();
                    if (100 == coolColor) {
                        coolColor = 99;
                    }
                    String coolValue = Util.integer2HexString(coolColor);
                    currentTime = System.currentTimeMillis();
                    final byte[] sendValue = Util.HexString2Bytes(LeyNew.SETCH + "00" + device.getLamp().getL_sequence() + "02" + coolValue + coolValue + "01" + LeyNew.END);
                    UnionSendInfoClass.sendCommonOrder(sendValue);
                } else {
                    Util.sendCommand(LeyNew.SETCOLORTEM, new String[]{two_custom_sb_brs.getProgress() * two_custom_sb_cool.getProgress() * 0.0255 + "",
                            two_custom_sb_brs.getProgress() * two_custom_sb_warm.getProgress() * 0.0255 + ""}, device.getLamp().getL_sequence());
                }
                break;
            case R.id.two_custom_sb_warm: //
                two_custom_txt_warm.setText(progress > 100 ? "100%" : progress + "%");
                two_custom_txt_color.setText(two_custom_sb_cool.getProgress() + "_" +
                        two_custom_sb_warm.getProgress() + "_" + two_custom_sb_brs.getProgress());
                two_custom_rl.setBackgroundColor(Color.rgb(
                        (int) (two_custom_sb_brs.getProgress() * (two_custom_sb_warm.getProgress() * 0.0255)),
                        (int) (two_custom_sb_brs.getProgress() * (two_custom_sb_warm.getProgress() * 0.0255)),
                        (int) (two_custom_sb_brs.getProgress() * (two_custom_sb_cool.getProgress() * 0.0255))));
                if (this.judgeContainVariable("WF322") || this.judgeContainVariable("WF326")) {
                    int blueValue = (int) (two_custom_sb_cool.getProgress() * two_custom_sb_brs.getProgress() * 0.0255);
                    int redValue = (int) (two_custom_sb_warm.getProgress() * two_custom_sb_brs.getProgress() * 0.0255);
                    if (255 == blueValue) {
                        blueValue = 254;
                    }
                    if (255 == redValue) {
                        redValue = 254;
                    }
                    int warmColor = two_custom_sb_warm.getProgress();
                    if (warmColor == 100) {
                        warmColor = 99;
                    }
                    String warmValue = Util.integer2HexString(warmColor);
                    final byte[] sendValue = Util.HexString2Bytes(LeyNew.SETCH + "00" + device.getLamp().getL_sequence() + "02" + warmValue + warmValue + "02" + LeyNew.END);
                    UnionSendInfoClass.sendCommonOrder(sendValue);
                } else {
                    Util.sendCommand(LeyNew.SETCOLORTEM, new String[]{two_custom_sb_brs.getProgress() * two_custom_sb_cool.getProgress() * 0.0255 + "",
                            two_custom_sb_brs.getProgress() * two_custom_sb_warm.getProgress() * 0.0255 + ""}, device.getLamp().getL_sequence());
                }
                break;
            case R.id.two_custom_sb_brs://
                two_custom_txt_brs.setText(progress > 100 ? "100%" : progress + "%");
                two_custom_txt_color.setText(two_custom_sb_cool.getProgress() + "_" +
                        two_custom_sb_warm.getProgress() + "_" + two_custom_sb_brs.getProgress());
                two_custom_rl.setBackgroundColor(Color.rgb(
                        (int) (two_custom_sb_brs.getProgress() * (two_custom_sb_warm.getProgress() * 0.0255)),
                        (int) (two_custom_sb_brs.getProgress() * (two_custom_sb_warm.getProgress() * 0.0255)),
                        (int) (two_custom_sb_brs.getProgress() * (two_custom_sb_cool.getProgress() * 0.0255))));
                if (this.judgeContainVariable("WF322") || this.judgeContainVariable("WF326")) {
                    int blueValue = (int) (two_custom_sb_cool.getProgress() * two_custom_sb_brs.getProgress() * 0.0255);
                    int redValue = (int) (two_custom_sb_warm.getProgress() * two_custom_sb_brs.getProgress() * 0.0255);
                    if (255 == blueValue) {
                        blueValue = 254;
                    }
                    if (255 == redValue) {
                        redValue = 254;
                    }
                    int brightness = two_custom_sb_brs.getProgress();
                    if (100 == brightness) {
                        brightness = 99;
                    }
                    String sendValue = Util.integer2HexString(brightness);
                    String brightnesValue = LeyNew.SETBN + "00" + device.getLamp().getL_sequence() + sendValue + LeyNew.END;
                    final byte[] info = Util.HexString2Bytes(brightnesValue);
                    UnionSendInfoClass.sendCommonOrder(info);
                } else {
                    Util.sendCommand(LeyNew.SETCOLORTEM, new String[]{two_custom_sb_brs.getProgress() * two_custom_sb_cool.getProgress() * 0.0255 + "",
                            two_custom_sb_brs.getProgress() * two_custom_sb_warm.getProgress() * 0.0255 + ""}, device.getLamp().getL_sequence());
                }
                break;
            default:
                break;
        }
    }

    public void onStartTrackingTouch(SeekBar seekBar) {
    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {
        switch (seekBar.getId()) {
            case R.id.two_custom_sb_brs:
                int brs_value = two_custom_sb_brs.getProgress();
                String brs_str = Util.integer2HexString(brs_value);
                String brightnesValue = LeyNew.SETBN + "00" + device.getLamp().getL_sequence() + brs_str + LeyNew.END;
                final byte[] brightByte = Util.HexString2Bytes(brightnesValue);
                UnionSendInfoClass.sendSpecialOrder(brightByte);
                break;
            case R.id.two_custom_sb_cool:
                String sendValue = Util.integer2HexString(two_custom_sb_cool.getProgress());
                final byte[] sendData = Util.HexString2Bytes(LeyNew.SETCH + "00" + device.getLamp().getL_sequence() + "02" + sendValue + sendValue + "01" + LeyNew.END);
                UnionSendInfoClass.sendSpecialOrder(sendData);
                break;
            case R.id.two_custom_sb_warm:
                String sb_warm = Util.integer2HexString(two_custom_sb_warm.getProgress());
                final byte[] sendValuem = Util.HexString2Bytes(LeyNew.SETCH + "00" + device.getLamp().getL_sequence() + "02" + sb_warm + sb_warm + "02" + LeyNew.END);
                UnionSendInfoClass.sendSpecialOrder(sendValuem);
                break;
            default:
                break;
        }

    }
}
