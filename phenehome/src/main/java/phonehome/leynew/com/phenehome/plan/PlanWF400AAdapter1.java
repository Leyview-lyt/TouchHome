package phonehome.leynew.com.phenehome.plan;

/**
 * WF400A
 */

import android.annotation.SuppressLint;
import android.app.FragmentManager;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SeekBar;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import phonehome.leynew.com.phenehome.R;
import phonehome.leynew.com.phenehome.control.ThreeColourCustom;
import phonehome.leynew.com.phenehome.control.ThreeCustomDataBase;
import phonehome.leynew.com.phenehome.control.ThreeLvAdapter;
import phonehome.leynew.com.phenehome.control.UnionSendInfoClass;
import phonehome.leynew.com.phenehome.control.WheelView;
import phonehome.leynew.com.phenehome.damain.Device;
import phonehome.leynew.com.phenehome.util.FinalClass;
import phonehome.leynew.com.phenehome.util.LeyNew;
import phonehome.leynew.com.phenehome.util.Util;
import phonehome.leynew.com.phenehome.view.MyRelativeLayout;

@SuppressLint("NewApi")
public class PlanWF400AAdapter1 extends PagerAdapter implements
        SeekBar.OnSeekBarChangeListener {

    private Context context;
    private List<View> list = new ArrayList<View>();
    private Device device;
    private View convertView;


    private TextView Tred, Tgreen, Tblue;
    private TextView color_txt;
    private SeekBar bar;
    private MyRelativeLayout color;

    private LayoutInflater layoutInflater = null;
    private View mView = null;


    private int red = 255, green = 255, blue = 255;
    private int tempRed = 255;
    private int tempGreen = 255;
    private int tempBlue = 255;
    private int P_width, P_height;
    private int hit;
    private ImageView color_img;

    private SeekBar sb_brightness;
    private SeekBar sb_speed;
    private TextView txt_brightness;
    private TextView txt_speed;
    private WheelView wv_model;
    private FinalClass fc;
    private int modelNum = 56;

    private ListView custom_lv;
    private Button custom_btn;
    private FragmentManager manager;
    private ThreeLvAdapter adapter;
    private List<ThreeColourCustom> tccs = new ArrayList<ThreeColourCustom>();

    private Handler isUpdateHandler;
    private PlanDeviceStore pds;
    private int tempPosition = 0;
    private ViewPager vp;
    private int tempItem;

    public PlanWF400AAdapter1(Context context, List<View> list, Device device,
                              FragmentManager manager,
                              Handler isUpdateHandler, PlanDeviceStore pds, ViewPager vp) {
        super();
        this.context = context;
        this.list = list;
        this.device = device;

        this.manager = manager;
        this.isUpdateHandler = isUpdateHandler;
        this.pds = pds;
        this.vp = vp;
    }

    public int getCount() {
        return list.size();
    }

    public boolean isViewFromObject(View arg0, Object arg1) {
        return arg0 == arg1;
    }

    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView(list.get(position));
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        container.addView(list.get(position));
        if (position == 0) {//0
            convertView = list.get(0);
            sePan();
        } else if (position == 1) {// 0
            convertView = list.get(1);
            model();
        } else {// 0
            convertView = list.get(2);
            custom();
        }
        return list.get(position);
    }

    private Object $(int id) {
        return convertView.findViewById(id);
    }


    private void updated() {
        Bundle data = new Bundle();
        PlanDeviceStore pds = new PlanDeviceStore();
        pds.set_id(this.pds.get_id());
        if (tempPosition == 0) {
            if (device.getLamp().getL_type().equals("WF323")) {
                pds.setPds_red(red);
                pds.setPds_green(green);
                pds.setPds_blue(blue);
                int firstBrightness = bar.getProgress();
                if (100 == firstBrightness)
                    firstBrightness = 99;
                pds.setPds_brightness(firstBrightness);
                pds.setPds_mode(0);
            } else {
                pds.setPds_red(tempRed);
                pds.setPds_green(tempGreen);
                pds.setPds_blue(tempBlue);
                pds.setPds_brightness(bar.getProgress());
                sb_brightness.setProgress(0);
                sb_speed.setProgress(0);
                // TODO: 2016/11/26/026
                wv_model.setSeletion(0);
//                wv_model.setCurrentItem(0);
            }
        } else if (tempPosition == 1) {
            if (device.getLamp().getL_type().equals("WF323")) {
                // TODO: 2016/11/26/026
                pds.setPds_mode(fc.model_values[wv_model.getSeletedIndex()]);
//                pds.setPds_mode(fc.model_values[wv_model.getCurrentItem()]);
                //
                // fc.model_values[wv_model.getCurrentItem()]);

                int secondBritnessvalue = sb_brightness.getProgress();
                int flashSpeed = sb_speed.getProgress();
                if (100 == secondBritnessvalue)
                    secondBritnessvalue = 99;
                if (100 == flashSpeed)
                    flashSpeed = 99;
                pds.setPds_brightness(secondBritnessvalue + 128);
                pds.setPds_speed(sb_speed.getProgress());
                pds.setPds_brightness2(secondBritnessvalue);
            } else {
                // TODO: 2016/11/26/026
                pds.setPds_mode(fc.model_values[wv_model.getSeletedIndex()]);
//                pds.setPds_mode(fc.model_values[wv_model.getCurrentItem()]);
                pds.setPds_brightness2(sb_brightness.getProgress());
                pds.setPds_brightness(100);
                pds.setPds_speed(sb_speed.getProgress());
                tempRed = 255;
                tempGreen = 255;
                tempBlue = 255;
                bar.setProgress(100);
                Tred.setText("255");
                Tgreen.setText("255");
                Tblue.setText("255");
                color_img.setBackgroundColor(Color.rgb(255, 255, 255));
            }
        } else if (tempPosition == 2) {
            //
            if (device.getLamp().getL_type().equals("WF323")) {

            } else {
                pds.setPds_custom(tccs.get(tempItem).getT_custom());
                pds.setPds_brightness(100);
                sb_brightness.setProgress(0);
                sb_speed.setProgress(0);
                // TODO: 2016/11/26/026
                wv_model.setSeletion(0);
//                wv_model.setCurrentItem(0);
                tempRed = 255;
                tempGreen = 255;
                tempBlue = 255;
                bar.setProgress(100);
                Tred.setText("255");
                Tgreen.setText("255");
                Tblue.setText("255");
                color_img.setBackgroundColor(Color.rgb(255, 255, 255));
            }
        }
        data.putSerializable("pds", pds);
        Util.sendMessage(isUpdateHandler, data);
    }

    private void setViewListener() {
        vp.setOnPageChangeListener(pageChangeListener);
    }


    private void custom() {
        initViewCustom();
        setCustomViewListener();
        ThreeCustomDataBase db = new ThreeCustomDataBase(context);
        tccs = db.findTCC(device.get_id());
        db.close();
        if (pds.getPds_custom() != 0) {
            for (int i = 0; i < tccs.size(); i++) {
                if (tccs.get(i).getT_custom() == pds.getPds_custom()) {
                    tccs.get(i).setChecked(true);
                    break;
                }
            }
        }

        adapter = new ThreeLvAdapter(context, tccs, handler, device, manager,
                false);
        custom_lv.setAdapter(adapter);
        if (manager == null) {
            custom_btn.setVisibility(View.GONE);
        }
    }

    private void setCustomViewListener() {
        custom_lv.setOnItemClickListener(itemClickListener); //
    }

    private void initViewCustom() {
        custom_lv = (ListView) $(R.id.three_custom_lv);
        custom_btn = (Button) $(R.id.three_custom_btn_addnew);
    }

    private OnItemClickListener itemClickListener = new OnItemClickListener() {
        public void onItemClick(AdapterView<?> parent, View view, int position,
                                long id) {
            System.out.println("000" + position);
            for (int i = 0; i < tccs.size(); i++) {
                if (i == position)
                    tccs.get(i).setChecked(true);
                else
                    tccs.get(i).setChecked(false);
            }
            adapter.notifyDataSetChanged();
            tempItem = position;
            updated();
            System.out.println("0000" + tccs.get(position).getT_custom());
            if (device.getLamp().getL_type().equals("Zigbee")
                    || device.getLamp().getL_type().equals("WF323")) {
                String mark = "02";
                String modNum = Util.integer2HexString(position);
                String sendCustom = LeyNew.SETMD + LeyNew.FLAG
                        + device.getLamp().getL_sequence() + mark + modNum;
                byte[] cust = Util.HexString2Bytes(sendCustom);
                Util.sendCommand(cust, null, null);
            } else {
                Util.sendCommand(LeyNew.CALL_7SAE,
                        new String[]{(tccs.get(position).getT_custom() - 1)
                                + ""}, device.getLamp().getL_sequence());
            }
        }
    };

    private Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            ThreeCustomDataBase db = new ThreeCustomDataBase(context);
            tccs.clear();
            for (ThreeColourCustom tcc : db.findTCC(device.get_id())) {
                tccs.add(tcc);
            }
            db.close();
            adapter.notifyDataSetChanged();
        }
    };


    private void model() {
        initViewModel();
        fc = new FinalClass(context);
        // TODO: 2016/11/28/028
        List<String> list = Arrays.asList(fc.models);
        int index = 0;
        for (int i = 0; i < fc.model_values.length; i++) {
            if (fc.model_values[i] == pds.getPds_mode()) {       //
                index = i;
                break;
            }
        }
        // TODO: 2016/11/26/026
        wv_model.setOffset(1);
        wv_model.setItems(list);
        wv_model.setSeletion(index);
        setModelViewListener();
    }


    private void initViewModel() {
        sb_brightness = (SeekBar) $(R.id.three_model_sb_brightness);
        sb_speed = (SeekBar) $(R.id.three_model_sb_speed);
        txt_brightness = (TextView) $(R.id.three_model_txt_brightness);
        txt_speed = (TextView) $(R.id.three_model_txt_speed);
        wv_model = (WheelView) $(R.id.three_model_wv_model);
        sb_brightness.setProgress(pds.getPds_brightness2());
        sb_speed.setProgress(pds.getPds_speed());
        txt_brightness.setText(pds.getPds_brightness2() + "%");
        txt_speed.setText(pds.getPds_speed() + "%");
    }

    /**
     *
     */
    private void setModelViewListener() {
        sb_brightness.setOnSeekBarChangeListener(this);
        sb_speed.setOnSeekBarChangeListener(this);
        // TODO: 2016/11/26/026
        wv_model.setOnWheelViewListener(wheelScrollListener);
//        wv_model.addScrollingListener(wheelScrollListener);
    }

    // TODO: 2016/11/26/026
    private WheelView.OnWheelViewListener wheelScrollListener = new WheelView.OnWheelViewListener() {
        @Override
        public void onSelected(int selectedIndex, String item) {
            Log.i("===========" ," selectedIndex"+ selectedIndex);
            updated();
            FinalClass fc = new FinalClass(context);
            modelNum = fc.model_values[selectedIndex]; //
            //
            System.out.println("000000000" + modelNum);
            if (null != device.getLamp().getL_type()
                    && device.getLamp().getL_type().equals("WF323")) {
                String modeFlag = "01";
                String currentModelNum = Util.integer2HexString(modelNum); //
                String sendInfo = LeyNew.SETMD + LeyNew.FLAG
                        + device.getLamp().getL_sequence() + modeFlag
                        + currentModelNum + LeyNew.END;
                byte[] finalModelByte = Util.HexString2Bytes(sendInfo);
                UnionSendInfoClass.sendSpecialOrder(finalModelByte);
            } else if (device.getLamp().getL_type().equals("Zigbee")) {
                String slog = "01";
                String number = Util.integer2HexString(modelNum);
                String s = LeyNew.SETMD + LeyNew.FLAG
                        + device.getLamp().getL_sequence() + slog + number
                        + LeyNew.END;
                System.out.println("---HELL--->>" + s);
                byte[] model = Util.HexString2Bytes(s);
                Util.sendCommand(model, null, null);
            } else {
                Util.sendCommand(
                        "setmode",
                        new String[]{modelNum + "",
                                sb_brightness.getProgress() + "",
                                sb_speed.getProgress() + ""}, device.getLamp()
                                .getL_sequence());
            }
        }
    };


    private void sePan() {
        // TODO Auto-generated method stub
        initViewSePan();
        setSePanViewListener();
//        setViewListener();
    }


    private void initViewSePan() {

        bar = (SeekBar) $(R.id.color_vsb);
        bar.setProgress(pds.getPds_brightness());
        color_txt = (TextView) $(R.id.color_txt);
        color_txt.setText(pds.getPds_brightness() + "%");


        tempRed = pds.getPds_red();
        tempGreen = pds.getPds_green();
        tempBlue = pds.getPds_blue();
        red = (int) (tempRed * (bar.getProgress() / 100.0));
        green = (int) (tempGreen * (bar.getProgress() / 100.0));
        blue = (int) (tempBlue * (bar.getProgress() / 100.0));
        Tred = (TextView) $(R.id.red);
        Tgreen = (TextView) $(R.id.green);
        Tblue = (TextView) $(R.id.blue);
        Tred.setText(red + "");
        Tgreen.setText(green + "");
        Tblue.setText(blue + "");
        color = (MyRelativeLayout) $(R.id.color);
        color_img = (ImageView) $(R.id.color_img);
        color_img.setBackgroundColor(Color.rgb(red, green, blue));


    }


    private void setSePanViewListener() {
        color.setOnTouchListener(new View.OnTouchListener() {
            public boolean onTouch(View v, MotionEvent event) {
                int clors[] = color.getColor();
                Log.i("=====", "" + clors[0] + " " + clors[1] + " " + clors[2]);
                int action = event.getAction();
                // TODO: 2016/11/22/022
                if (action == MotionEvent.ACTION_DOWN || action == MotionEvent.ACTION_MOVE) {
                    red = clors[0];
                    green = clors[1];
                    blue = clors[2];
                    tempRed = clors[0];
                    tempGreen = clors[1];
                    tempBlue = clors[2];
                    red = (int) (tempRed * (bar.getProgress() / 100.0));
                    green = (int) (tempGreen * (bar.getProgress() / 100.0));
                    blue = (int) (tempBlue * (bar.getProgress() / 100.0));
                    Tred.setText(red + "");
                    Tgreen.setText(green + "");
                    Tblue.setText(blue + "");
                    color_img.setBackgroundColor(Color.rgb(red, green, blue));

                    if (null != device.getLamp().getL_type() && device.getLamp().getL_type().equals("WF323")) {
                        if (255 == red) {
                            red = 254;
                        }
                        if (255 == green) {
                            green = 254;
                        }
                        if (255 == blue) {
                            blue = 254;
                        }
                        String ido = "03";
                        String colorMian = LeyNew.SETCH + LeyNew.FLAG
                                + device.getLamp().getL_sequence() + ido
                                + Util.integer2HexString(red)
                                + Util.integer2HexString(green)
                                + Util.integer2HexString(blue) + LeyNew.END;
                        byte[] finalSendInfo = Util
                                .HexString2Bytes(colorMian);
                        UnionSendInfoClass.sendCommonOrder(finalSendInfo);
                    } else {
                        new Thread() {
                            public void run() {
                                if (device.getLamp().getL_type()
                                        .equals("Zigbee")) {
                                    String ido = "01";
                                    String sepan = LeyNew.SETCH
                                            + LeyNew.FLAG
                                            + device.getLamp()
                                            .getL_sequence() + ido
                                            + Util.integer2HexString(red)
                                            + Util.integer2HexString(green)
                                            + Util.integer2HexString(blue);
                                    byte[] sep = Util
                                            .HexString2Bytes(sepan);
                                    Util.sendCommand(sep, null, null);
                                } else {
                                    Util.sendCommand(
                                            "setrgb",
                                            new String[]{red + "",
                                                    green + "", blue + ""},
                                            device.getLamp()
                                                    .getL_sequence());
                                }
                            }
                        }.start();
                    }
                } else if (action == MotionEvent.ACTION_UP) {
                    if (null != device.getLamp().getL_type()
                            && device.getLamp().getL_type().equals("WF323")) {
                        if (255 == red)
                            red = 254;
                        if (255 == green)
                            green = 254;
                        if (255 == blue)
                            blue = 254;
                        String redLast = Util.integer2HexString(red);
                        String greenLast = Util.integer2HexString(green);
                        String blueLast = Util.integer2HexString(blue);
                        String ido = "03";
                        String colorMian = LeyNew.SETCH + LeyNew.FLAG
                                + device.getLamp().getL_sequence() + ido
                                + redLast + greenLast + blueLast + LeyNew.END;
                        byte[] finalSendInfo = Util.HexString2Bytes(colorMian);
                        UnionSendInfoClass.sendSpecialOrder(finalSendInfo);
                    }
                }
                updated();
                return false;
            }
        });



        /*************************         ****************************/
        bar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            public void onStopTrackingTouch(SeekBar seekBar) {
                if (null != device.getLamp().getL_type() && "WF323".equals(device.getLamp().getL_type())) {
                    String ido = "03";
                    int redFirst = Integer.parseInt(Tred.getText().toString());
                    int greenFirst = Integer.parseInt(Tgreen.getText()
                            .toString());
                    int blueFirst = Integer
                            .parseInt(Tblue.getText().toString());
                    if (255 == redFirst) {
                        red = 254;
                        redFirst = 254;
                    }
                    if (255 == greenFirst) {
                        green = 254;
                        greenFirst = 254;
                    }
                    if (255 == blueFirst) {
                        blue = 254;
                        blueFirst = 254;
                    }
                    String sendColor = LeyNew.SETCH + LeyNew.FLAG
                            + device.getLamp().getL_sequence() + ido
                            + Util.integer2HexString(redFirst)
                            + Util.integer2HexString(greenFirst)
                            + Util.integer2HexString(blueFirst)
                            + Util.integer2HexString(blueFirst) + LeyNew.END;
                    byte[] sendBytes = Util.HexString2Bytes(sendColor);
                    UnionSendInfoClass.sendSpecialOrder(sendBytes);
                    updated();
                    return;
                }
            }

            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

                if (null != device.getLamp().getL_type() && "WF323".equals(device.getLamp().getL_type())) {
                    red = (int) (tempRed * (bar.getProgress() / 100.0));
                    green = (int) (tempGreen * (bar.getProgress() / 100.0));
                    blue = (int) (tempBlue * (bar.getProgress() / 100.0));
                    Tred.setText(red + "");
                    Tgreen.setText(green + "");
                    Tblue.setText(blue + "");
                    color_txt.setText(progress + "%");
                    color_img.setBackgroundColor(Color.rgb(red, green, blue));
                    if (255 == red)
                        red = 254;
                    if (255 == green)
                        green = 254;
                    if (255 == blue)
                        blue = 254;
                    String firstRed = Util.integer2HexString(red);
                    String firstGreen = Util.integer2HexString(green);
                    String firstBlue = Util.integer2HexString(blue);
                    String sendBefor = LeyNew.SETCH + LeyNew.FLAG + device.getLamp().getL_sequence() + "03" + firstRed + firstGreen + firstBlue + LeyNew.END;
                    byte[] sendBytes = Util.HexString2Bytes(sendBefor);
                    UnionSendInfoClass.sendCommonOrder(sendBytes);
                    if (progress != pds.getPds_brightness()) {
                        updated();
                    }
                }
                else if (device.getLamp().getL_type().equals("Zigbee")) {
                    if (progress != pds.getPds_brightness()) {
                        updated();
                    }
                    new Thread() {
                        public void run() {
                            if (device.getLamp().getL_type().equals("Zigbee")) {
                                String brt = Util.integer2HexString(bar.getProgress());
                                String gflag = "00";
                                String setbtr = LeyNew.SETBN + gflag + device.getLamp().getL_sequence() + brt + LeyNew.END;
                                System.out.println("wolegequ" + setbtr);
                                byte[] tiaoguang = Util.HexString2Bytes(setbtr);
                                Util.sendCommand(tiaoguang, null, null);
                            }
                        }
                    }.start();
                }else {
                    red = (int) (tempRed * (bar.getProgress() / 100.0));
                    green = (int) (tempGreen * (bar.getProgress() / 100.0));
                    blue = (int) (tempBlue * (bar.getProgress() / 100.0));
                    Tred.setText(red + "");
                    Tgreen.setText(green + "");
                    Tblue.setText(blue + "");
                    color_txt.setText(progress + "%");
                    color_img.setBackgroundColor(Color.rgb(red, green, blue));
                    Util.sendCommand(LeyNew.SETRGB, new String[]{red + "", green + "", blue + ""}, device.getLamp().getL_sequence());
                }
            }
        });
    }














    /*************************         ****************************/
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        switch (seekBar.getId()) {
            case R.id.three_model_sb_brightness:
                txt_brightness.setText(progress + "%");

                if (null != device.getLamp().getL_type()
                        && device.getLamp().getL_type().equals("WF323")) {

                    int brightnessValue = progress;
                    if (100 == brightnessValue)
                        brightnessValue = 99;
                    String sbtr = Util.integer2HexString(brightnessValue);
                    String gFlag = "00";
                    String sendBefor = LeyNew.SETBN + gFlag
                            + device.getLamp().getL_sequence() + sbtr + LeyNew.END;
                    byte[] finalSendValue = Util.HexString2Bytes(sendBefor);
                    UnionSendInfoClass.sendCommonOrder(finalSendValue);
                    if (sb_brightness.getProgress() != pds.getPds_brightness2()) {
                        updated();
                        break;
                    }
                }
                if (sb_brightness.getProgress() != pds.getPds_brightness2()) {
                    updated();
                }
                if (device.getLamp().getL_type().equals("Zigbee")) {//
                    String brt = Util.integer2HexString(progress);
                    String gflag = "00";
                    String setbtr = LeyNew.SETBN + gflag
                            + device.getLamp().getL_sequence() + brt + LeyNew.END;

                    byte[] tiaoguang = Util.HexString2Bytes(setbtr);
                    Util.sendCommand(tiaoguang, null, null);
                }
                break;
            case R.id.three_model_sb_speed:
                txt_speed.setText(progress + "%");
                if (null != device.getLamp().getL_type()
                        && device.getLamp().getL_type().equals("WF323")) {
                    String sp = Util.integer2HexString(progress);
                    String gflag = "00";
                    String setSpeed = LeyNew.SETSP + gflag
                            + device.getLamp().getL_sequence() + sp + LeyNew.END;
                    byte[] speed = Util.HexString2Bytes(setSpeed);
                    UnionSendInfoClass.sendCommonOrder(speed);
                    if (pds.getPds_speed() != progress) {
                        updated();
                        break;
                    }
                }
                if (sb_speed.getProgress() != pds.getPds_brightness2()) {
                    updated();
                }
                if (device.getLamp().getL_type().equals("Zigbee")) {//
                    String sp = Util.integer2HexString(progress);
                    String gflag = "00";
                    String setSpeed = LeyNew.SETSP + gflag
                            + device.getLamp().getL_sequence() + sp + LeyNew.END;
                    byte[] speed = Util.HexString2Bytes(setSpeed);
                    Util.sendCommand(speed, null, null);
                }
                break;
            default:
                break;
        }

        if (!device.getLamp().getL_type().equals("Zigbee")) {
            Util.sendCommand("setmode", new String[]{modelNum + "",
                    sb_brightness.getProgress() + "",
                    sb_speed.getProgress() + ""}, device.getLamp()
                    .getL_sequence());
        }
    }

    public void onStartTrackingTouch(SeekBar seekBar) {
    }

    public void onStopTrackingTouch(SeekBar seekBar) {
        switch (seekBar.getId()) {
            case R.id.three_model_sb_brightness:
                if (null != device.getLamp().getL_type() && "WF323".equals(device.getLamp().getL_type())) {
                    int currentValue = sb_brightness.getProgress();
                    if (100 == currentValue)
                        currentValue = 99;
                    String btr = Util.integer2HexString(currentValue);
                    String flag = "00";
                    String sendBefor = LeyNew.SETBN + flag + device.getLamp().getL_sequence() + btr + LeyNew.END;
                    byte[] finalSend = Util.HexString2Bytes(sendBefor);
                    UnionSendInfoClass.sendSpecialOrder(finalSend);
                    updated();
                }
                break;
            case R.id.three_model_sb_speed:

                if ("WF323".equals(device.getLamp().getL_type())) {
                    int currentSpeedValue = sb_speed.getProgress();
                    if (100 == currentSpeedValue)
                        currentSpeedValue = 99;
                    String sp = Util.integer2HexString(currentSpeedValue);
                    String flag = "00";
                    String speedSendBefore = LeyNew.SETBN + flag + device.getLamp().getL_sequence() + sp + LeyNew.END;
                    byte[] finalSpeedByte = Util.HexString2Bytes(speedSendBefore);
                    UnionSendInfoClass.sendSpecialOrder(finalSpeedByte);
                    if (pds.getPds_speed() != currentSpeedValue) {
                        updated();
                    }
                }
                break;
            default:
                break;
        }

    }

    /**
     * vp
     */
    private OnPageChangeListener pageChangeListener = new OnPageChangeListener() {
        public void onPageSelected(int arg0) {
            tempPosition = arg0;
        }

        public void onPageScrolled(int arg0, float arg1, int arg2) {
        }

        public void onPageScrollStateChanged(int arg0) {

        }
    };
}
