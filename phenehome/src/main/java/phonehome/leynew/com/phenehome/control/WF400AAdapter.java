package phonehome.leynew.com.phenehome.control;

import android.annotation.SuppressLint;
import android.app.FragmentManager;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.PagerAdapter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;


import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import phonehome.leynew.com.phenehome.R;

import phonehome.leynew.com.phenehome.damain.Device;
import phonehome.leynew.com.phenehome.util.FinalClass;
import phonehome.leynew.com.phenehome.util.LeyNew;
import phonehome.leynew.com.phenehome.util.Util;
import phonehome.leynew.com.phenehome.view.MyRelativeLayout;


@SuppressLint("NewApi")
public class WF400AAdapter extends PagerAdapter implements
        OnSeekBarChangeListener {
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
    private int x = 0, y = 0, radius = 0;
    private int red = 255, green = 255, blue = 255;
    private int tempRed = 255;
    private int tempGreen = 255;
    private int tempBlue = 255;
    private int P_width, P_height;
    private WheelView wv_model;
    private int hit;
    private ImageView color_img;

    private SeekBar sb_brightness;
    private SeekBar sb_speed;
    private TextView txt_brightness;
    private TextView txt_speed;

    private int modelNum = 56;


    private ListView custom_lv;
    private Button custom_btn;
    @SuppressLint("NewApi")
    private FragmentManager manager;
    private ThreeLvAdapter adapter;
    private List<ThreeColourCustom> tccs = new ArrayList<ThreeColourCustom>();
    private boolean isAddNew = false;//


    private long sendTime = 0L;
    static long lastSendTime = 0L;

    public WF400AAdapter(Context context, List<View> list, Device device, FragmentManager manager) {
        super();
        this.context = context;
        this.list = list;
        this.device = device;

        this.manager = manager;
    }


    public static int px2dp(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }


    public static int dp2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }


    public static int pxToSp(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (pxValue / scale + 0.5f);
    }


    public static int spTopx(Context context, float spValue) {
        final float scale = context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (spValue * scale + 0.5f);
    }


    public int getCount() {
        return list.size();
    }

    public boolean isViewFromObject(View arg0, Object arg1) {
        return arg0 == arg1;
    }

    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView(list.get(position));
        SendInfoList.wf323AdjustBrighFlag = false;
    }

    public Object instantiateItem(ViewGroup container, int position) {
        container.addView(list.get(position));
        if (position == 0) {//
            convertView = list.get(0);
            sePan();
        } else if (position == 1) {//
            convertView = list.get(1);
            model();
        } else {                    //
            convertView = list.get(2);
            custom();
        }
        return list.get(position);
    }

    private Object $(int id) {
        return convertView.findViewById(id);
    }


    private void custom() {
        initViewCustom();
        setCustomViewListener();
        tccs = new ArrayList<ThreeColourCustom>();

        adapter = new ThreeLvAdapter(context, tccs, handler, device, manager,
                isAddNew);
        custom_lv.setAdapter(adapter);
        new Thread() {
            public void run() {
                ThreeCustomDataBase db = new ThreeCustomDataBase(context);
                tccs = db.findTCC(device.get_id());     //
                db.close();
                Util.sendMessage(h, null);
            }

            ;
        }.start();
    }

    private Handler h = new Handler() {
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            adapter = new ThreeLvAdapter(context, tccs, handler, device, manager, isAddNew);
            custom_lv.setAdapter(adapter);
        }
    };

    private void setCustomViewListener() {
        custom_btn.setOnClickListener(clickListener);
        custom_lv.setOnItemClickListener(itemClickListener);
    }

    private void initViewCustom() {
        custom_lv = (ListView) $(R.id.three_custom_lv);
        custom_btn = (Button) $(R.id.three_custom_btn_addnew);
    }


    private OnItemClickListener itemClickListener = new OnItemClickListener() {
        public void onItemClick(AdapterView<?> parent, View view, int position,
                                long id) {
            System.out.println(":" + tccs.get(position).getT_custom());
            if (device.getLamp().getL_type().equals("Zigbee") || device.getLamp().getL_type().equals("WF323")) {
                if (device.getLamp().getL_type().equals("WF323")) {
                    String sendBefore = LeyNew.SETMD + LeyNew.FLAG + device.getLamp().getL_sequence() + "02" + Util.integer2HexString(position) + LeyNew.END;
                    UnionSendInfoClass.sendSpecialOrder(Util.HexString2Bytes(sendBefore));
                } else {
                    String mark = "02";
                    String modNum = Util.integer2HexString(position);
                    String sendCustom = LeyNew.SETMD + LeyNew.FLAG + device.getLamp().getL_sequence() + mark + modNum;
                    byte[] cust = Util.HexString2Bytes(sendCustom);
                    Util.sendCommand(cust, null, null);
                }
            } else {
//                Util.sendCommand(
//                        LeyNew.CALL_7SAE,
//                        new String[]{(tccs.get(position).getT_custom() - 1) + ""},
//                        device.getLamp().getL_sequence());
                Log.i("==========", "发送");
                Util.sendCommand(
                        LeyNew.CALL_7SAE,
                        new String[]{
                                "1",
                                "1_0_50_255_1.00_1.2_1.5",
                                "1_0_255_34_1.00_1.0_1.1",
                                "1_255_18_0_1.00_0.8_0.8",
                                "0",
                                "0",
                        //更新到github
                        },
                        device.getLamp().getL_sequence());

            }
        }
    };

    @SuppressLint("NewApi")
    private OnClickListener clickListener = new OnClickListener() {
        public void onClick(View v) {
            Log.i("======", "" + tccs.size());
            if (tccs.size() < FinalClass.CUSTOM_SIZE) {
                ThreeDialogFragment dialog = new ThreeDialogFragment(context, device, null, handler);
                dialog.show(manager, "addNew");
            } else {
                AddThreeCustomTipDialog dialog = new AddThreeCustomTipDialog(context);
                dialog.show();
            }
        }
    };

    private Handler handler = new Handler() {    //���������ɫֵ�Ǵ洢�����ݿ���    ����иı��ʱ���������Ӧ����
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            isAddNew = true;
            ThreeCustomDataBase db = new ThreeCustomDataBase(context);
            tccs.clear();
            for (ThreeColourCustom tcc : db.findTCC(device.get_id())) {
                tccs.add(tcc);
            }
            db.close();
            adapter.notifyDataSetChanged();
            isAddNew = false;
        }
    };


    private void model() {
        initViewModel();
        setModelViewListener();
        FinalClass fc = new FinalClass(context);
        // TODO: 2016/11/28/028
        List<String> list = Arrays.asList(fc.models);
        wv_model.setOffset(2);
        wv_model.setSeletion(0);
        wv_model.setItems(list);

    }


    private void initViewModel() {
        sb_brightness = (SeekBar) $(R.id.three_model_sb_brightness);
        sb_speed = (SeekBar) $(R.id.three_model_sb_speed);
        txt_brightness = (TextView) $(R.id.three_model_txt_brightness);
        txt_speed = (TextView) $(R.id.three_model_txt_speed);
        wv_model = (WheelView) $(R.id.three_model_wv_model);
        sb_brightness.setProgress(0);
        sb_speed.setProgress(0);
        txt_brightness.setText("0%");
        txt_speed.setText(" 0");
    }


    private void setModelViewListener() {
        sb_brightness.setOnSeekBarChangeListener(this);
        sb_speed.setOnSeekBarChangeListener(this);
        wv_model.setOnWheelViewListener(wheelScrollListener);
    }

    private WheelView.OnWheelViewListener wheelScrollListener = new WheelView.OnWheelViewListener() {

        public void onSelected(int selectedIndex, String item) {
            // TODO Auto-generated method stub
            FinalClass fc = new FinalClass(context);
            modelNum = fc.model_values[selectedIndex];

            if (device.getLamp().getL_type().equals("Zigbee")) {
                String slog = "01";
                String number = Util.integer2HexString(modelNum);
                String s = LeyNew.SETMD + LeyNew.FLAG + device.getLamp().getL_sequence() + slog + number + LeyNew.END;
                byte[] model = Util.HexString2Bytes(s);
                Util.sendCommand(model, null, null);
            } else if (device.getLamp().getL_type().equals("WF323")) {
                String slog = "01";
                String number = Util.integer2HexString(modelNum);
                String s = LeyNew.SETMD + LeyNew.FLAG + device.getLamp().getL_sequence() + slog + number + LeyNew.END;
                byte[] model = Util.HexString2Bytes(s);
                UnionSendInfoClass.sendSpecialOrder(model);
            } else {
                Util.sendCommand(LeyNew.SETMODE, new String[]{modelNum + "",
                        sb_brightness.getProgress() + "",
                        sb_speed.getProgress() + ""}, device.getLamp()
                        .getL_sequence());
            }
        }
    };


    private void sePan() {
        initViewSePan();
        setSePanViewListener();
    }


    private void initViewSePan() {
        layoutInflater = LayoutInflater.from(context);
        mView = layoutInflater.inflate(R.layout.three_colour, null);
        Tred = (TextView) $(R.id.red);
        Tred.setText(red + "");
        Tgreen = (TextView) $(R.id.green);
        Tgreen.setText(green + "");
        Tblue = (TextView) $(R.id.blue);
        Tblue.setText(blue + "");
        color_txt = (TextView) $(R.id.color_txt);
        bar = (SeekBar) $(R.id.color_vsb);
        color = (MyRelativeLayout) $(R.id.color);
        String[] str = Util.getDisplayMetrics(context);
        P_width = Integer.parseInt(str[1]);
        P_height = Integer.parseInt(str[2]);

        color_img = (ImageView) $(R.id.color_img);
    }


    private void setSePanViewListener() {
        color.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent event) {
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
                    if (null != device.getLamp().getL_sequence() || device.getLamp().getL_type().equals("WF323")) {
                        if (255 == red) {
                            red = 254;
                        }
                        if (255 == green) {
                            green = 254;
                        }
                        if (255 == blue) {
                            blue = 254;
                        }
                    }
                    Tred.setText(red + "");
                    Tgreen.setText(green + "");
                    Tblue.setText(blue + "");
                    color_img.setBackgroundColor(Color.rgb(red, green, blue));
                    if (device.getLamp().getL_type().equals("Zigbee")) {
                        new Thread() {
                            public void run() {
                                if (device.getLamp().getL_type().equals("Zigbee")) {
                                    String ido = "01";
                                    String sepan = LeyNew.SETRGB + LeyNew.FLAG + device.getLamp().getL_sequence() + ido + Util.integer2HexString(red) + Util.integer2HexString(green) + Util.integer2HexString(blue);
                                    byte[] sep = Util.HexString2Bytes(sepan);
                                    Util.sendCommand(sep, null, null);
                                }
                            }

                            ;
                        }.start();
                    }
                    if (null != device.getLamp().getL_type() && device.getLamp().getL_type().equals("WF323")) {
                        Long currentTime = System.currentTimeMillis();
                        String ido = "03";
                        String colorMian = LeyNew.SETCH + LeyNew.FLAG + device.getLamp().getL_sequence() + ido + Util.integer2HexString(red) + Util.integer2HexString(green) + Util.integer2HexString(blue) + LeyNew.END;
                        byte[] sendInfo = Util.HexString2Bytes(colorMian);
                        UnionSendInfoClass.sendCommonOrder(sendInfo);
                    } else {
                        new Thread() {
                            public void run() {
                                Util.sendCommand(LeyNew.SETRGB, new String[]{
                                                red + "", green + "", blue + ""},
                                        device.getLamp().getL_sequence());
                            }
                        }.start();
                    }
                } else if (action == MotionEvent.ACTION_UP) {
                    if (null != device.getLamp().getL_type() && device.getLamp().getL_type().equals("WF323")) {
                        String ido = "03";
                        String red_last = Util.integer2HexString(Integer.parseInt((Tred.getText().toString())));
                        String green_last = Util.integer2HexString(Integer.parseInt((Tgreen.getText().toString())));
                        String blue_last = Util.integer2HexString(Integer.parseInt(Tblue.getText().toString()));
                        String colorMian = LeyNew.SETCH + LeyNew.FLAG + device.getLamp().getL_sequence() + ido + red_last + green_last + blue_last + LeyNew.END;
                        byte[] colorByte = Util.HexString2Bytes(colorMian);
                        UnionSendInfoClass.sendSpecialOrder(colorByte);
                    }
                }
                return false;
            }
        });
        bar.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {
            public void onStopTrackingTouch(SeekBar seekBar) {
                if (null != device.getLamp().getL_type() && device.getLamp().getL_type().equals("WF323")) {
                    String ido = "03";
                    int redFirst = Integer.parseInt(Tred.getText().toString());
                    int greenFirst = Integer.parseInt(Tgreen.getText().toString());
                    int blueFirst = Integer.parseInt(Tblue.getText().toString());
                    if (255 == redFirst) {
                        redFirst = 254;
                    }
                    if (255 == greenFirst) {
                        greenFirst = 254;
                    }
                    if (255 == blueFirst) {
                        blueFirst = 254;
                    }
                    String redSend = Util.integer2HexString(redFirst);
                    String greenSend = Util.integer2HexString(greenFirst);
                    String blueSend = Util.integer2HexString(blueFirst);
                    String sendColor = LeyNew.SETCH + LeyNew.FLAG + device.getLamp().getL_sequence() + ido + redSend + greenSend + blueSend + LeyNew.END;
                    final byte[] sendByte = Util.HexString2Bytes(sendColor);
                    UnionSendInfoClass.sendSpecialOrder(sendByte);
                }
            }

            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            public void onProgressChanged(SeekBar seekBar, final int progress, boolean fromUser) {
                if (null != device.getLamp().getL_type() && device.getLamp().getL_type().equals("WF323")) {
                    red = (int) ((tempRed * bar.getProgress()) / 100.0);
                    green = (int) ((tempGreen * bar.getProgress()) / 100.0);
                    blue = (int) ((tempBlue * bar.getProgress()) / 100.0);
                    Tred.setText(red + "");
                    Tgreen.setText(green + "");
                    Tblue.setText(blue + "");
                    color_txt.setText(progress + "%");
                    color_img.setBackgroundColor(Color.rgb(red, green, blue));
                } else if (device.getLamp().getL_type().equals("Zigbee")) {
                    color_txt.setText(progress + "%");
                    Tred.setEnabled(true);
                    Tgreen.setEnabled(true);
                    Tblue.setEnabled(true);
                    color_img.setEnabled(true);
                } else {
                    red = (int) (tempRed * (bar.getProgress() / 100.0));
                    green = (int) (tempGreen * (bar.getProgress() / 100.0));
                    blue = (int) (tempBlue * (bar.getProgress() / 100.0));
                    Tred.setText(red + "");
                    Tgreen.setText(green + "");
                    Tblue.setText(blue + "");
                    color_txt.setText(progress + "%");
                    color_img.setBackgroundColor(Color.rgb(red, green, blue));
                }
                if (device.getLamp().getL_type().equals("Zigbee")) {
                    new Thread() {
                        public void run() {
                            String brt = Util.integer2HexString(progress);
                            String gflag = "00";
                            String setbtr = LeyNew.SETBN + gflag + device.getLamp().getL_sequence() + brt + LeyNew.END;
                            System.out.println("wolegequ" + setbtr);
                            byte[] tiaoguang = Util.HexString2Bytes(setbtr);
                            Util.sendCommand(tiaoguang, null, null);
                        }
                    }.start();
                } else if (null != device.getLamp().getL_type() && device.getLamp().getL_type().equals("WF323")) {
                    String ido = "03";
                    if (255 == red) {
                        red = 254;
                    }
                    if (255 == green) {
                        green = 254;
                    }
                    if (255 == blue) {
                        blue = 254;
                    }
                    String redVarable = Util.integer2HexString(red);
                    String greenVarable = Util.integer2HexString(green);
                    String blueVarable = Util.integer2HexString(blue);
                    String colorMian = LeyNew.SETCH + LeyNew.FLAG + device.getLamp().getL_sequence() + ido + redVarable + greenVarable + blueVarable + LeyNew.END;
                    final byte[] sendInfo = Util.HexString2Bytes(colorMian);
                    UnionSendInfoClass.sendCommonOrder(sendInfo);
                } else {
                    new Thread() {
                        public void run() {
                            Util.sendCommand(LeyNew.SETRGB, new String[]{red + "",
                                    green + "", blue + ""}, device.getLamp()
                                    .getL_sequence());
                        }
                    }.start();
                }
            }
        });
    }



    public void onProgressChanged(SeekBar seekBar, int progress,
                                  boolean fromUser) {
        switch (seekBar.getId()) {
            case R.id.three_model_sb_brightness:
                txt_brightness.setText(progress + "%");
                if (device.getLamp().getL_type().equals("Zigbee")) {//
                    String brt = Util.integer2HexString(progress);
                    String gflag = "00";
                    String setbtr = LeyNew.SETBN + gflag + device.getLamp().getL_sequence() + brt + LeyNew.END;
                    System.out.println("wolegequ" + setbtr);
                    byte[] tiaoguang = Util.HexString2Bytes(setbtr);
                    Util.sendCommand(tiaoguang, null, null);
                }
                if (device.getLamp().getL_type().equals("WF323")) {
                    String brt = Util.integer2HexString(progress);
                    String setbn = LeyNew.SETBN + "00" + device.getLamp().getL_sequence() + brt + LeyNew.END;
                    byte[] tiaoguang = Util.HexString2Bytes(setbn);
                    UnionSendInfoClass.sendCommonOrder(tiaoguang);
                }
                break;
            case R.id.three_model_sb_speed:
                txt_speed.setText(progress + "%");
                if (device.getLamp().getL_type().equals("Zigbee")) {

                    String sp = Util.integer2HexString(progress);
                    String gflag = "00";
                    String setSpeed = LeyNew.SETSP + gflag + device.getLamp().getL_sequence() + sp + LeyNew.END;
                    byte[] speed = Util.HexString2Bytes(setSpeed);
                    Util.sendCommand(speed, null, null);
                }
                if (device.getLamp().getL_type().equals("WF323")) {
                    String sp = Util.integer2HexString(progress);
                    String gflag = "00";
                    String setSpeed = LeyNew.SETSP + gflag + device.getLamp().getL_sequence() + sp + LeyNew.END;
                    byte[] speed = Util.HexString2Bytes(setSpeed);
                    UnionSendInfoClass.sendCommonOrder(speed);
                }
                break;
            default:
                break;
        }
        if (!device.getLamp().getL_type().equals("Zigbee") && !device.getLamp().getL_type().equals("WF323")) {
            Util.sendCommand(LeyNew.SETMODE,
                    new String[]{modelNum + "", sb_brightness.getProgress() + "",
                            sb_speed.getProgress() + ""}, device.getLamp()
                            .getL_sequence());
        }
    }

    public void onStartTrackingTouch(SeekBar seekBar) {
    }

    //
    public void onStopTrackingTouch(SeekBar seekBar) {
        switch (seekBar.getId()) {
            case R.id.three_model_sb_brightness:
                if (device.getLamp().getL_type().equals("WF323")) {
                    System.out.println("" + seekBar.getProgress());
                    String brt = Util.integer2HexString(seekBar.getProgress());
                    String setbn = LeyNew.SETBN + "00" + device.getLamp().getL_sequence() + brt + LeyNew.END;
                    final byte[] setBnByte = Util.HexString2Bytes(setbn);
                    //
                    UnionSendInfoClass.sendSpecialOrder(setBnByte);
                }
                break;
            case R.id.three_model_sb_speed:
                if (device.getLamp().getL_type().equals("WF323")) {
                    int currentSpeedValue = sb_speed.getProgress();
                    if (100 == currentSpeedValue)
                        currentSpeedValue = 99;
                    String sp = Util.integer2HexString(currentSpeedValue);
                    String flag = "00";
                    String speedSendBefore = LeyNew.SETBN + flag + device.getLamp().getL_sequence() + sp + LeyNew.END;
                    byte[] finalSpeedByte = Util.HexString2Bytes(speedSendBefore);
                    UnionSendInfoClass.sendSpecialOrder(finalSpeedByte);
                }
                break;
            default:
                break;
        }
    }
}
