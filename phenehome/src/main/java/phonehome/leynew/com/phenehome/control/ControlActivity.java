package phonehome.leynew.com.phenehome.control;


import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import phonehome.leynew.com.phenehome.R;
import phonehome.leynew.com.phenehome.damain.Device;
import phonehome.leynew.com.phenehome.damain.Lamp;
import phonehome.leynew.com.phenehome.db.DataBaseHelper;
import phonehome.leynew.com.phenehome.db.LampDataBase;
import phonehome.leynew.com.phenehome.fragment.Fragment_Device;


@TargetApi(Build.VERSION_CODES.HONEYCOMB)
@SuppressLint({"NewApi", "HandlerLeak"})
public class ControlActivity extends Activity implements OnClickListener {
    private Context context;
    private Button back;
    private TextView title;
    private Device device;

    DataBaseHelper dbhelp;

    /*********************************************/
    private ImageButton left, right;
    private MyViewPager vp;
    private List<View> views = new ArrayList<View>();

    @Override
    public void onClick(View view) {
        left.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v) {
                vp.setCurrentItem(vp.getCurrentItem()-1);
            }
        });

        right.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v) {
                Log.i("=======","点击");
                vp.setCurrentItem(vp.getCurrentItem()+1);
            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.setContentView(R.layout.control_layout);
        device = Fragment_Device.DEVICE;
        context = this;
        back = (Button) findViewById(R.id.dev);
        title = (TextView) findViewById(R.id.ctitle);
        title.setText(device.getD_name());
        back.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                finish();
            }
        });

        vp = (MyViewPager) findViewById(R.id.plan_home_vp);
        init();
        left = (ImageButton) findViewById(R.id.plan_vp_left);
        right = (ImageButton) findViewById(R.id.plan_vp_right);
        left.setOnClickListener(this);
        right.setOnClickListener(this);
        left.performClick();//???小bug,第一次点击事件无效
    }

/********************************************************/
    private void init(){
            if (device != null && device.getL_id() != 0) {
                LampDataBase ldb = new LampDataBase(context);
                final Lamp lamp = ldb.findLampById(device.getL_id());
                ldb.close();
                new Thread() {
                    public void run() {
                        if (lamp.getL_type().equals("WF500")) {
                            lamp.setL_sequence(lamp.getL_sequence().split("-")[0]);
                        }
                        //获取返回信息
//                        List<String[]> list = Util.sendCommandForResult("readstatus =", null, lamp.getL_sequence());
//                        if (list.size() == 0) {
//                            Util.sendMessage(isSuccessHandler, null);
//                        }
                    }

                    ;
                }.start();
                device.setLamp(lamp);

                if (lamp.getL_type().equals("WF400C")
                        || lamp.getL_type().equals("WF500A")
                        || lamp.getL_type().equals("WF500B")
                        || lamp.getL_type().equals("TM111")
                        || lamp.getL_type().equals("WF510")
                        || lamp.getL_type().equals("TM113")
                        || lamp.getL_type().equals("WF321")
                        || lamp.getL_type().equals("NB-1000")
                        ) {
                    // $(R.id.plan_vp_left).setVisibility(View.GONE);
                    // $(R.id.plan_vp_right).setVisibility(View.GONE);
                    View view = LayoutInflater.from(context).inflate(R.layout.homochromy, null);
                    views.add(view);
                    WF400CAdapter c = new WF400CAdapter(context, views, device);
                    vp.setAdapter(c);

                } else if (lamp.getL_type().equals("WF400B") || lamp.getL_type().equals("WF322")) {
                    View view = LayoutInflater.from(context).inflate(
                            R.layout.two_tone, null);
                    View view2 = LayoutInflater.from(context).inflate(
                            R.layout.two_tone_custom, null);
                    views.add(view);
                    views.add(view2);
                    WF400BAdapter b = new WF400BAdapter(context, views, device);
                    vp.setAdapter(b);

                } else if (lamp.getL_type().equals("WF400A") || lamp.getL_type().equals("Zigbee") || lamp.getL_type().equals("WF323")) {
                    View view = LayoutInflater.from(context).inflate(
                            R.layout.three_colour, null);
                    View view2 = LayoutInflater.from(context).inflate(
                            R.layout.three_colour_model, null);
                    View view3 = LayoutInflater.from(context).inflate(
                            R.layout.three_colour_custom, null);
                    views.add(view);
                    views.add(view2);
                    views.add(view3);
                    // TODO: 2016/12/1/001 ???? 
                    WF400AAdapter a = new WF400AAdapter(context, views, device,null);
                    vp.setOffscreenPageLimit(2);
                    vp.setAdapter(a);
                }

            } else {
                views = new ArrayList<View>();
                WF400CAdapter c = new WF400CAdapter(context, views, device);
                vp.setAdapter(c);
            }
        }




//    private Handler isSuccessHandler = new Handler() {
//        public void handleMessage(Message msg) {
//            super.handleMessage(msg);
//            TimerTask task = new TimerTask() {
//                public void run() {
//                    Util.sendMessage(readStatusHandler, null);
//                }
//            };
//            Timer timer = new Timer(true);
//            timer.schedule(task, 3000);
//        }
//
//        ;
//    };
//    private Handler readStatusHandler = new Handler() {
//        public void handleMessage(Message msg) {
//            super.handleMessage(msg);
//        }
//    };


}
