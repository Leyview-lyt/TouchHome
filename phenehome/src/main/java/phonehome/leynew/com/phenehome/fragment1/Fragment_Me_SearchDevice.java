package phonehome.leynew.com.phenehome.fragment1;


import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import phonehome.leynew.com.phenehome.MainActivity;
import phonehome.leynew.com.phenehome.R;
import phonehome.leynew.com.phenehome.adapter.SettingDeviceGVAdapter;
import phonehome.leynew.com.phenehome.damain.Lamp;
import phonehome.leynew.com.phenehome.db.LampDataBase;
import phonehome.leynew.com.phenehome.util.LeyNew;
import phonehome.leynew.com.phenehome.util.Util;
import phonehome.leynew.com.phenehome.view.MyRadioButton;

/**
 * A simple {@link Fragment} subclass.
 */
public class Fragment_Me_SearchDevice extends Fragment implements View.OnClickListener, RadioGroup.OnCheckedChangeListener {

    MainActivity mainActivity = null;


    private ProgressBar progressBar;

    private int tempPosition = -1;//记录点击的位置
    private List<Lamp> list = new ArrayList<Lamp>();
    private GridView gridView;
    private SettingDeviceGVAdapter adapter;


    private String accountStr, passwordStr;//记录账号,密码
    private EditText account;//账号输入框
    private EditText password;//密码输入框


    private String authStr, encryStr;//记录认证方式,和加密方式
    private RadioButton wpaRbt, wpa2Rbt, tkipRbt, aesRbt;
    private RadioGroup auth, encry;

    private View wf;
    private RadioGroup wfRadioGroup;
    private MyRadioButton wf400a;
    private MyRadioButton wf400b;
    private MyRadioButton wf400c;
    private String type = null;

    private ImageButton refreshButton;
    private Button sentButton;


    public Fragment_Me_SearchDevice() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mainActivity = (MainActivity) getActivity();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.me_searchdivece, container, false);
        View in = mainActivity.findViewById(R.id.id_Area_Tottom);
        in.setVisibility(View.GONE);


        progressBar = (ProgressBar) view.findViewById(R.id.progressBar);
        refreshButton = (ImageButton) view.findViewById(R.id.bt_refresh);
        refreshButton.setOnClickListener(this);
        gridView = (GridView) view.findViewById(R.id.gridView);
        gridView.setStretchMode(GridView.NO_STRETCH);
        adapter = new SettingDeviceGVAdapter(getActivity(), list, handlerAdapter);
        gridView.setAdapter(adapter);

        account = (EditText) view.findViewById(R.id.account);
        password = (EditText) view.findViewById(R.id.password);

        wpaRbt = (RadioButton) view.findViewById(R.id.wpapsk);
        wpa2Rbt = (RadioButton) view.findViewById(R.id.wpa2psk);
        tkipRbt = (RadioButton) view.findViewById(R.id.tkip);
        aesRbt = (RadioButton) view.findViewById(R.id.aes);
        auth = (RadioGroup) view.findViewById(R.id.auth);
        encry = (RadioGroup) view.findViewById(R.id.encry);
        auth.setOnCheckedChangeListener(this);
        encry.setOnCheckedChangeListener(this);


        wfRadioGroup = (RadioGroup) view.findViewById(R.id.wfRadioGroup);
        wf400a = (MyRadioButton) view.findViewById(R.id.wf400a);
        wf400b = (MyRadioButton) view.findViewById(R.id.wf400b);
        wf400c = (MyRadioButton) view.findViewById(R.id.wf400c);
        wfRadioGroup.setOnCheckedChangeListener(this);
//        wf400a.setVisibility(View.GONE);
//        wf400b.setVisibility(View.GONE);
//        wf400c.setVisibility(View.GONE);
        wf = view.findViewById(R.id.wf);
//        wf.setVisibility(View.GONE);

        sentButton = (Button) view.findViewById(R.id.bt_send);
        sentButton.setOnClickListener(this);

//        getData();//开启线程获取数据
        return view;
    }


    /**
     * 开启线程获取数据
     */
    private void getData() {
        refreshButton.setVisibility(View.GONE);
        progressBar.setVisibility(View.VISIBLE);
//        btn_gone.setVisibility(View.VISIBLE);

        new Thread() {
            public void run() {
                try {
//                    Thread.sleep(500);
                    //发送指令,获取可连接的设备
                    List<String[]> strs = Util.sendCommandForResult(LeyNew.SEARCH, null, null);
                    for (String[] str : strs) {
                        for (int i = 0; i < str.length; i++) {
                            System.out.println("" + i + "" + str[i]);
                            System.out.println(str[i].length());
                            if (str[i].length() == 16) {
                                String sequence = str[i];
                                LampDataBase db = new LampDataBase(getActivity());
                                Lamp lamp = db.findLamp(sequence);//根据序列号查找设备
                                if (lamp == null) {
                                    lamp = new Lamp();
                                }
                                lamp.setL_sequence(sequence);
                                lamp.setL_type("Zigbee");
                                list.add(lamp);
                            }
                        }
                        if (str.length == 6) {
                            String sequence = str[1];
                            String type = str[4];
                            if (sequence.length() == 12) {
                                if (type.equals("WF400A") || type.equals("WF400B")
                                        || type.equals("WF400C")) {
                                    System.out.println("");
                                    LampDataBase db = new LampDataBase(getActivity());
                                    Lamp lamp = db.findLamp(sequence);
                                    if (lamp == null) {
                                        lamp = new Lamp();
//										lamp.setL_sequence(sequence);
//										lamp.setL_type(type);
                                    } else if (!lamp.getL_type().equals(type)) {
                                        db.updateType(sequence, type);
                                    }
                                    lamp.setL_sequence(sequence);
                                    lamp.setL_type(type);
                                    list.add(lamp);
                                } else if (type.equals("WF500") ||
                                        type.equals("TM111") ||
                                        type.equals("WF510") ||
                                        type.equals("NB-1000") ||
                                        type.equals("TM113")) {
                                    Lamp lamp = new Lamp();
                                    lamp.setL_sequence(sequence);
                                    lamp.setL_type(type);
                                    list.add(lamp);
                                }
                            }
                        }
                    }
                    Util.sendMessage(handler, null);
                } catch (Exception e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        }.start();
    }

    /**
     * gridview点击后,更新界面
     */
    private Handler handlerAdapter = new Handler() {
        @Override
        public void handleMessage(Message message) {
            // TODO: 2016/11/3/003 根据gridview的选择,更新item和wf图片
//            btn_gone.setVisibility(View.GONE);
            Bundle data = message.getData();
            tempPosition = data.getInt("position");
            Lamp lamp = list.get(tempPosition);
            String type = lamp.getL_type();
            if (type.equals("WF400A") || type.equals("WF400B")
                    || type.equals("WF400C")) {
                wf.setVisibility(View.VISIBLE);
                wf400a.setChecked(true);
            } else {
                wf.setVisibility(View.GONE);
            }
        }
    };

    /**
     * 数据准备完后,更新gridview
     */
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message message) {
            super.handleMessage(message);
            progressBar.setVisibility(View.GONE);
            refreshButton.setVisibility(View.VISIBLE);
            //横向的GridView
            ViewGroup.LayoutParams lp = (LinearLayout.LayoutParams) gridView.getLayoutParams();
            lp.width = list.size() * 200;
            gridView.setLayoutParams(lp);// 设置GirdView布局参数,横向布局的关键
            gridView.setNumColumns(list.size());//设置列数量=列表集合数
            gridView.setColumnWidth(200);
            gridView.setStretchMode(GridView.NO_STRETCH);//图片填充不拉伸
            adapter.notifyDataSetChanged();

        }
    };


    @Override
    public void onClick(View view) {
        int id = view.getId();
        switch (id) {
            case R.id.bt_refresh://刷新
                list.clear();//删除数据
                adapter.notifyDataSetChanged();//通知界面更新
                getData();//重新获取数据
                break;
            case R.id.bt_send://发送
                if (list.size() == 0) {
                    Toast.makeText(getActivity(), getContext().getString(R.string.no_seatch_device), Toast.LENGTH_LONG).show();
                    return;
                }
                accountStr = account.getText().toString().trim();
                passwordStr = password.getText().toString().trim();

                if (accountStr.equals("")) {//
                    // toast(getActivity().getString(R.string.account_number_empty));
                    accountStr = "$";
                }
                if (tempPosition == -1) {
                    Toast.makeText(getActivity(), getContext().getString(R.string.tip_select_device), Toast.LENGTH_LONG).show();
                    return;
                }

                // TODO: 2016/11/3/003 根据选择的radiobutton确定类型
                Log.i("===","认证方式和加密方式为:"+ authStr + "     " + encryStr);
                Log.i("===","账号和密码为:"+ accountStr + "   " + password);
                Log.i("===","wifi类型:"+type);
                String str[] = null;
                if (type != null)
                  str = new String[] { accountStr, passwordStr.equals("") ? "$" : passwordStr, type,
                            authStr, encryStr };
                else
                str = new String[]{accountStr, passwordStr.equals("") ? "$" : passwordStr,
                        authStr, encryStr};
                Util.sendCommand(LeyNew.SETNET, str, list.get(tempPosition)
                        .getL_sequence());
                Toast.makeText(getActivity(), getContext().getString(R.string.send_success), Toast.LENGTH_LONG)
                        .show();
                break;
        }
    }

    @Override
    public void onCheckedChanged(RadioGroup radioGroup, int arg1) {
        int id = radioGroup.getId();
        Log.i("====radioGroup:id = ", id + " ");
        if (id == R.id.auth) {//认证
            if (wpaRbt.getId() == arg1)
                authStr = "WPAPSK";
            if (wpa2Rbt.getId() == arg1) {
                authStr = "WPA2PSK";
            }
        } else if (id == R.id.encry) {//加密
            if (tkipRbt.getId() == arg1)
                encryStr = "TKIP";
            if (aesRbt.getId() == arg1) {
                encryStr = "AES";
            }
        } else if (id == R.id.wfRadioGroup) {//wifi类型
            if (wf400a.getId() == arg1)
                type = "WF400A";
            else if (wf400b.getId() == arg1)
                type = "WF400B";
            else if (wf400c.getId() == arg1)
                type = "WF400C";
        }


    }
}
