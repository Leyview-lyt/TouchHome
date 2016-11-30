package phonehome.leynew.com.phenehome.control;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.PopupWindow;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;


import java.util.List;

import phonehome.leynew.com.phenehome.R;
import phonehome.leynew.com.phenehome.damain.Device;
import phonehome.leynew.com.phenehome.util.LeyNew;
import phonehome.leynew.com.phenehome.util.Util;
import phonehome.leynew.com.phenehome.view.MyRelativeLayout;

public class AddThreeCustomRgbDialog extends Dialog implements
        OnSeekBarChangeListener {

    private Device device;
    private Context context;
    private ThreeColourCustom tcc = new ThreeColourCustom();
    private List<ThreeColourSave> tcss = null;
    private View currentView;
    private int currentItem;
    private int j;
    private Button back;

    private TextView Tred, Tgreen, Tblue;
    private MyRelativeLayout color;
    private PopupWindow popupWindow = null;
    private View view = null;
    private Bitmap resBitmap = null;
    private SoundPool spool;
    private int x = 0, y = 0, radius = 0;
    private int pixel, p_off_width, p_off_height;
    private int red = 0, green = 0, blue = 0;
    private int P_width, P_height;
    private int hit;

    private TextView add_txt_speed, add_txt_hold, add_txt_diaphaneity;
    private SeekBar add_vsb_speed, add_vsb_hold, add_vsb_diaphaneity;

    private int tempRed;
    private int tempGreen;
    private int tempBlue;

    private Handler handler;
    private boolean isUpdate = false;//
    private String initName = "";//
    private boolean isChange = true;

    public AddThreeCustomRgbDialog(Context context, Device device,
                                   List<ThreeColourSave> tcss, Handler handler, int j, View currentView, int currentItem) {
        super(context);
        this.context = context;
        this.device = device;
        this.j = j;
        this.currentView = currentView;
        this.currentItem = currentItem;
        this.tcss = tcss;
        this.handler = handler;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.setContentView(R.layout.add_2);
        System.out.println(" RGB Dialog");
        //	initSepan(j);
        findID();
        setListener();
    }

    private void findID() {
        // TODO Auto-generated method stub
        Tred = (TextView) $(R.id.add_red);
        Tred.setText(red + "");
        Tgreen = (TextView) $(R.id.add_green);
        Tgreen.setText(green + "");
        Tblue = (TextView) $(R.id.add_blue);
        Tblue.setText(blue + "");
        color = (MyRelativeLayout) $(R.id.add_color);
//		String[] str = Util.getDisplayMetrics(context);
//		P_width = Integer.parseInt(str[1]);
//		P_height = Integer.parseInt(str[2]);
//		p_off_width = P_width * 17 / 320 + 180;
//		p_off_height = P_height * 95 / 480 - 30;
//		System.out.println("P_width=" + P_width + "P_height=" + P_height);
//		setPopupWindows();
        spool = new SoundPool(1, AudioManager.STREAM_MUSIC, 100);

        add_txt_speed = (TextView) $(R.id.add_txt_speed);
        add_txt_hold = (TextView) $(R.id.add_txt_hold);
        add_vsb_speed = (SeekBar) $(R.id.add_vsb_speed);
        add_vsb_speed.setOnSeekBarChangeListener(this);
        add_vsb_hold = (SeekBar) $(R.id.add_vsb_hold);
        add_vsb_hold.setOnSeekBarChangeListener(this);
        add_txt_diaphaneity = (TextView) $(R.id.add_txt_diaphaneity);
        add_vsb_diaphaneity = (SeekBar) $(R.id.add_vsb_diaphaneity);
        add_vsb_diaphaneity.setOnSeekBarChangeListener(this);
        back = (Button) $(R.id.fanhui);
        back.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {
                AddThreeCustomRgbDialog.this.dismiss();
            }
        });

    }

    private Object $(int id) {
        return findViewById(id);
    }

//	private void setPopupWindows() {
//			resBitmap = ((BitmapDrawable) color.getBackground()).getBitmap();
//			view = LayoutInflater.from(getContext()).inflate(
//					R.layout.three_colour_popupwindows, null);
//			popupWindow = new PopupWindow(view, 20, 20);
//			popupWindow.setAnimationStyle(android.R.style.Animation_Toast);
//			popupWindow.setTouchable(false);
//		}

    private void setListener() {
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
                    red = (int) (tempRed * (add_vsb_diaphaneity.getProgress() / 100.0));
                    green = (int) (tempGreen * (add_vsb_diaphaneity.getProgress() / 100.0));
                    blue = (int) (tempBlue * (add_vsb_diaphaneity.getProgress() / 100.0));
                    Tred.setText(red + "");
                    Tgreen.setText(green + "");
                    Tblue.setText(blue + "");
                    if (currentView != null) {
                        ThreeColourSave t = tcss.get(currentItem);
                        t.setR(tempRed);
                        t.setG(tempGreen);
                        t.setB(tempBlue);
                        t.setStatus(1);
                        currentView.setBackgroundColor(Color.rgb(red,
                                green, blue));
                        if ("WF323".equals(device.getLamp().getL_type())) {
                            if (255 == red)
                                red = 254;
                            if (255 == green)
                                green = 254;
                            if (255 == blue)
                                green = 254;
                            String ido = "03";
                            String currentColor = LeyNew.SETCH + LeyNew.FLAG + device.getLamp().getL_sequence() + ido + Util.integer2HexString(red) + "" + Util.integer2HexString(green) + "" + Util.integer2HexString(blue) + LeyNew.END;
                            UnionSendInfoClass.sendCommonOrder(Util.HexString2Bytes(currentColor));

                        } else {
                            Util.sendCommand("setrgb", new String[]{red + "",
                                    green + "", blue + ""}, device.getLamp()
                                    .getL_sequence());
                        }
                    }
//						} else {
//							return true;
//						}
                } else if (action == MotionEvent.ACTION_UP) {
                    if ("WF323".equals(device.getLamp().getL_type())) {
                        int currentRedColor = Integer.parseInt(Tred.getText().toString());
                        int currentGreenColor = Integer.parseInt(Tgreen.getText().toString());
                        int currentBlueColor = Integer.parseInt(Tblue.getText().toString());
                        if (255 == currentRedColor) {
                            currentRedColor = 254;
                        }
                        if (255 == currentGreenColor) {
                            currentGreenColor = 254;
                        }
                        if (255 == currentBlueColor) {
                            currentBlueColor = 254;
                        }
                        String ido = "03";
                        String currentColor = LeyNew.SETCH + LeyNew.FLAG + device.getLamp().getL_sequence() + ido + Util.integer2HexString(currentRedColor) + "" + Util.integer2HexString(currentGreenColor) + "" + Util.integer2HexString(currentBlueColor) + LeyNew.END;
                        UnionSendInfoClass.sendSpecialOrder(Util.HexString2Bytes(currentColor));
                    }
                    spool.stop(hit);
                }
                return false;
            }
        });
    }


    @Override
    public void onProgressChanged(SeekBar seekBar,
                                  int progress, boolean fromUser) {
        // TODO Auto-generated method stub
        switch (seekBar.getId()) {
            case R.id.add_vsb_hold:
                float num = (float) ((progress * (1.0)) / 10);
                add_txt_hold.setText(num + "s");
                if (currentView != null) {
                    tcss.get(currentItem).setTc_hold(num + "");
                    tcss.get(currentItem).setStatus(1);
                }
                break;
            case R.id.add_vsb_speed:
                float num2 = (float) ((progress * (1.0)) / 10);
                add_txt_speed.setText(num2 + "s");
                if (currentView != null) {
                    tcss.get(currentItem).setTc_shade(num2 + "");
                    tcss.get(currentItem).setStatus(1);
                }
                break;
            case R.id.add_vsb_diaphaneity:
                add_txt_diaphaneity.setText(progress + "%");
                red = (int) (tempRed * (progress / 100.0));
                green = (int) (tempGreen * (progress / 100.0));
                blue = (int) (tempBlue * (progress / 100.0));
                Tred.setText(red + "");
                Tgreen.setText(green + "");
                Tblue.setText(blue + "");
                if (currentView != null && isChange) {
                    tcss.get(currentItem).setDiaphaneity(progress);
                    tcss.get(currentItem).setStatus(1);
                    currentView.setBackgroundColor(Color.rgb(red, green, blue));
                    Util.sendCommand("setrgb", new String[]{red + "", green + "",
                            blue + "","1","1","1"}, device.getLamp().getL_sequence());
                }
                isChange = true;
                break;
            default:
                break;
        }
    }

    public void onStartTrackingTouch(SeekBar seekBar) {
    }

    public void onStopTrackingTouch(SeekBar seekBar) {

    }

    private void initSepan(int j) {
        ThreeColourSave t = tcss.get(j);
        tempRed = t.getR();
        tempGreen = t.getG();
        tempBlue = t.getB();

        int hold = (int) (Float.parseFloat(t.getTc_hold()) * 10);
        int speed = (int) (Float.parseFloat(t.getTc_shade()) * 10);
        if (hold == 0 && speed == 0) {
            hold = add_vsb_hold.getProgress();
            speed = add_vsb_speed.getProgress();
            tcss.get(currentItem).setTc_hold(hold * 1.0 / 10 + "");
            tcss.get(currentItem).setTc_shade(speed * 1.0 / 10 + "");
            tcss.get(currentItem).setStatus(0);
        }

        add_vsb_hold.setProgress(hold);
        add_vsb_speed.setProgress(speed);
        isChange = false;
        add_vsb_diaphaneity.setProgress(t.getDiaphaneity());
        red = (int) (tempRed * (add_vsb_diaphaneity.getProgress() / 100.0));
        green = (int) (tempGreen * (add_vsb_diaphaneity.getProgress() / 100.0));
        blue = (int) (tempBlue * (add_vsb_diaphaneity.getProgress() / 100.0));
        Tred.setText(red + "");
        Tgreen.setText(green + "");
        Tblue.setText(blue + "");

    }
}
