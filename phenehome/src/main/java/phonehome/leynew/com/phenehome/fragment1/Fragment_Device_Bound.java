package phonehome.leynew.com.phenehome.fragment1;


import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import phonehome.leynew.com.phenehome.MainActivity;
import phonehome.leynew.com.phenehome.R;
import phonehome.leynew.com.phenehome.adapter.DeviceBoundLVAdapter;
import phonehome.leynew.com.phenehome.command.HandlerConstantByDxy;
import phonehome.leynew.com.phenehome.command.SingleChildDeviceType;
import phonehome.leynew.com.phenehome.damain.Device;
import phonehome.leynew.com.phenehome.damain.Images;
import phonehome.leynew.com.phenehome.damain.Lamp;
import phonehome.leynew.com.phenehome.db.DeviceDataBase;
import phonehome.leynew.com.phenehome.db.LampDataBase;
import phonehome.leynew.com.phenehome.util.LeyNew;
import phonehome.leynew.com.phenehome.util.Util;

/**
 * A simple {@link Fragment} subclass.
 */
public class Fragment_Device_Bound extends Fragment implements View.OnClickListener, AdapterView.OnItemClickListener {
    MainActivity mainActivity = null;

    /**
     * 顶部菜单
     */
    private Button top_Menu;
    /**
     * 顶部返回
     */
    private Button top_Back;
    /**
     * 顶部标题
     */
    private TextView top_Title;


    //保存数据
    private int type;
    private ListView listView;
    private List<Images> list = new ArrayList<Images>();
    private List<Lamp> lamps = new ArrayList<Lamp>(); // 这个是用来表示搜出来的设备
    private DeviceBoundLVAdapter lvAdapter;
    private Device initDevice; // 初始设备，用于修改设备
    private Device device = Fragment_Device_Add.D ;
    List<String> returnResult = new ArrayList<String>();

    //进度条
    private ProgressDialog progressDialog;
    private Button save;

    public Fragment_Device_Bound() {
        // Required empty public constructor
    }
    @Override
    public void onClick(View view) {

        int viewId = view.getId();
        switch (viewId) {
            case R.id.id_top_back :
//                if (type == 4){
//                    mainActivity.switchContent(this,new Fragment_Device_Add(),false,4);
//                }else if (type == 2) {
//                    mainActivity.switchContent(this,new Fragment_Device_Add(),false,2);
//                }else mainActivity.switchContent(this,new Fragment_Device(),false,4);
                mainActivity.popFragments();
                break;

            case R.id.id_top_Menu :
                progressDialog.show();
                lamps.clear();
                if (lvAdapter!=null) {
                    lvAdapter.notifyDataSetChanged();
                }
                new Thread() {
                    public void run() {
                        super.run();
                        try {
                            Thread.sleep(500);
                            searchLamp();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        Message msg = new Message();
                        Bundle b = new Bundle();
                        b.putString("result", "success");
                        msg.setData(b);
                        searchHandler.sendMessage(msg);
                    }
                }.start();

                break;

            case R.id.id_save :
                // TODO: 2016/11/21/021 保存设备
                DeviceDataBase db = new DeviceDataBase(getActivity());
                if (device.getLamp() != null && !device.getLamp().getL_sequence().equals("")) {
                    LampDataBase ldb = new LampDataBase(getActivity());
                    Lamp lamp = ldb.save(device.getLamp());
                    device.setL_id(lamp.get_id());
                    device.setLamp(lamp);
                    ldb.close();
                    int id = db.getDeviceID(device);
                    db.bindingLamp(id, device.getL_id());
                    System.out.println("绑定了灯");
                    db.updateDevice(device);
                    db.close();
                    System.out.println("Lamp的id为：" + lamp.get_id());
                    System.out.println("driver的ID为：" + id);
                    // 我的工作就在这里开始
                    // if(0<returnResult.size()){
                    // ChildDeviceType deviceType = new ChildDeviceType();
                    // deviceType.setDriverId(id);
                    // deviceType.setLampId(lamp.get_id());
                    // deviceType.setChildDriverType(returnResult.get(1));
                    // deviceType.setChildDriverSequence(lamp.getL_sequence());
                    // ChildDeviceTypeDataBase cdb = new
                    // ChildDeviceTypeDataBase(context);
                    // cdb.saveChildDriverType(deviceType);
                    // cdb.close();
                    // }
                }
                // TODO: 2016/11/21/021 回到设备界面
                if (type == 2 || type ==4) {
                    mainActivity.popFragments();
                    mainActivity.popFragments();
                }else mainActivity.popFragments();
//                mainActivity.switchContent(this,new Fragment_Device(),false,4);
                break;
        }
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, final int position, long id) {
        // TODO Auto-generated method stub
        for (int i = 0; i < lamps.size(); i++) {
            lamps.get(i).setCheck(false);
        }
        lamps.get(position).setCheck(true);
        device.setL_id(lamps.get(position).get_id());
        device.setLamp(lamps.get(position));
        lvAdapter.notifyDataSetChanged();
        save.setVisibility(View.VISIBLE);
        // 这里定义返回分机号的类型
        new Thread() {
            public void run() {// 子线程发送测试命令,关，开，关，开
                String off = "00";
                String on = "01";
                try {
                    String lmp_sequence = lamps.get(position)
                            .getL_sequence();
                    String product_name = lamps.get(position)
                            .getL_type();
                    System.out.println("产品名为：" + product_name);
                    returnResult.clear();
                    // 下面开一个方法去查询数据库
                    if (lamps.get(position).getL_type()
                            .equals("Zigbee")) {
                        String fuck = Util.command(LeyNew.SETON,
                                LeyNew.FLAG, lamps.get(position)
                                        .getL_sequence(), off,
                                LeyNew.END);
                        byte[] ok = Util.HexString2Bytes(fuck);
                        Util.sendCommand(ok, null, null);
                        Thread.sleep(1000);
                        String cao = Util.command(LeyNew.SETON,
                                LeyNew.FLAG, lamps.get(position)
                                        .getL_sequence(), on,
                                LeyNew.END);
                        byte[] dan = Util.HexString2Bytes(cao);
                        Util.sendCommand(dan, null, null);
                    } else if (lamps.get(position).getL_type()
                            .equals("WF321")
                            || lamps.get(position).getL_type()
                            .equals("WF322")
                            || lamps.get(position).getL_type()
                            .equals("WF323")
                            || lamps.get(position).getL_type()
                            .equals("WF325")
                            || lamps.get(position).getL_type()
                            .equals("WF326")) {
                        String fuck = Util.command(LeyNew.SETON,
                                LeyNew.FLAG, lamps.get(position)
                                        .getL_sequence(), off,
                                LeyNew.END);
                        byte[] ok = Util.HexString2Bytes(fuck);
                        // 向设备发送 开命令
                        Util.sendCommand(ok, null, null);

                        Thread.sleep(1000);
                        String cao = Util.command(LeyNew.SETON,
                                LeyNew.FLAG, lamps.get(position)
                                        .getL_sequence(), on,
                                LeyNew.END);
                        byte[] dan = Util.HexString2Bytes(cao);
                        // 向设备发送 关 命令
                        Util.sendCommand(dan, null, null);
                    } else {
                        Util.sendCommand(LeyNew.SETONOFF, new String[] {
                                0 + "", "254" }, lamps.get(position)
                                .getL_sequence());
                        Thread.sleep(300);
                        Util.sendCommand(LeyNew.SETONOFF, new String[] {
                                1 + "", "254" }, lamps.get(position)
                                .getL_sequence());
                        Thread.sleep(300);
                        Util.sendCommand(LeyNew.SETONOFF, new String[] {
                                0 + "", "254" }, lamps.get(position)
                                .getL_sequence());
                        Thread.sleep(300);
                        Util.sendCommand(LeyNew.SETONOFF, new String[] {
                                1 + "", "254" }, lamps.get(position)
                                .getL_sequence());
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_device_bound_view,container,false);

        top_Menu = (Button) view.findViewById(R.id.id_top_Menu);
        top_Menu.setOnClickListener(this);
        top_Menu.setText("刷新");
        top_Back = (Button) view.findViewById(R.id.id_top_back);
        top_Back.setOnClickListener(this);
        top_Title = (TextView) view.findViewById(R.id.id_top_title);
        top_Title.setText("绑定设备");

        /************************************************/
        mainActivity = (MainActivity) getActivity();
        type = getArguments().getInt("type");
        getData();
        listView = (ListView) view.findViewById(R.id.id_ListView);
        listView.setOnItemClickListener(this);
        progressDialog = new ProgressDialog(getContext());
        progressDialog.setTitle("请等待");
        progressDialog.setMessage("正在加载......");
        progressDialog.show();

        save = (Button) view.findViewById(R.id.id_save);
        save.setOnClickListener(this);
        save.setVisibility(View.GONE);
        return view;
    }


    private void getData() {
        // 查询数据库所有灯具
        new Thread() {
            public void run() {
                super.run();
                try {
                    Thread.sleep(500);
                    searchLamp();
                } catch (Exception e) {
                }
                Message msg = new Message();
                Bundle b = new Bundle();
                b.putString("result", "success");
                msg.setData(b);
                searchHandler.sendMessage(msg);
            }
        }.start();
    }


    private void searchLamp() {
        lamps.clear();
        Log.i("===========","程序进入到这里的时间");
        // 搜索所有灯具
        List<String[]> list;

            list = Util.sendCommandForResult(LeyNew.SEARCH, null,
                    null);
            Log.i("==========","返回数据大小"+list.size());
//        }
//        while (list!=null);

        Iterator<String[]> it = list.iterator();
        while (it.hasNext()) {
            String[] returnInfo = it.next();
            for (int i = 0; i < returnInfo.length; i++) {
                Log.i("===========","返回数据中的每个String[]值为 ： " + returnInfo[i]);
            }
        }

        Log.i("==========","返回数据大小"+list.size());

        for (String[] str : list) { // 把搜出来的数据放到一个例表中
           Log.i("===========","==========返回灯的数据====================");
            for (int i = 0; i < str.length; i++) {
                if (str[i].length() == 16) {
                    try {
                        String sequence = str[i];
                        String send_info = LeyNew.SETRD + LeyNew.FLAG
                                + sequence + LeyNew.END;
                        Thread.sleep(50);
                        Log.e("==========看下这里输出", send_info);
                        byte[] data = Util.HexString2Bytes(send_info);
                        SingleChildDeviceType deviceType = SingleChildDeviceType
                                .getDeviceType();
                        deviceType.getChildDeviceType(data, updataLampList,
                                sequence);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                // 这个地方的目的是为了处理到最后一个设备时，线程之间处理速度不同，所以在此处让此线程让出时间，将时间交给另外一个线程，只有当另外一个线程处理完毕的时候，
                // 才来这里执行此处后面的代码
                if (i == (str.length - 1)) {
                    try {
                        Thread.currentThread().sleep(500);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
           Log.i("==========","Str.length is " + str.length);
            if (str.length == 6) {
                Log.i("==========","这里执行了吗" + str.length);
                // 将返回回来的序列号和类型取出
                String sequence = str[1];
                String type = str[4];
                // 判断数据是否准确，将数据保存至数据库
                if (sequence.length() == 12) {
                    LampDataBase db = new LampDataBase(getActivity());
                    Lamp lamp = db.findLamp(sequence);
                    if (lamp == null) {
                        lamp = new Lamp();
                    }
                    lamp.setL_sequence(sequence);
                    lamp.setL_type(type);
                    if (type.equals("WF400A") || type.equals("WF400B")
                            || type.equals("WF400C")) {
                        Log.i("==========","加入数据源" + str.length);
                        // 若发现类型被更改，则更新数据库信息
                        if (!lamp.getL_type().equals(type)) {
                            db.updateType(sequence, type);
                        }
                        lamps.add(lamp);
                    } else if (type.equals("WF500")) {
                        // 将WF500拆分成6个灯光设备，前4个设备可调光，设为WF500A类型
                        // 后2个设备不可调光，设为WF500B
                        // 序列号拆分成6个序列号
                        for (int i = 1; i <= 6; i++) {
                            Lamp lamp1 = new Lamp();
                            Log.i("==========","这个循环了吗" + str.length);
                            lamp1.setL_sequence(sequence + "-" + i);
                            if (i <= 4)
                                lamp1.setL_type(type + "A");
                            else
                                lamp1.setL_type(type + "B");
                            lamps.add(lamp1);
                        }
                    } else if (type.equals("TM111") || type.equals("WF510")
                            || type.equals("TM113") || type.equals("NB-1000")) {
                        // 无特殊处理
                        lamps.add(lamp);
                    }
                }
            }
        }
        for (int i = 0; i < lamps.size(); i++) { // 给所有的灯除绑定的灯外添加模拟id，全为负数
            if (lamps.get(i).get_id() == 0) {
                lamps.get(i).set_id(-1 - i);
            }
           Log.i("===","kkkk加入到搜索列表的id:" + lamps.get(i).get_id()
                    + ",类型：" + lamps.get(i).getL_type() + "，序列号："
                    + lamps.get(i).getL_sequence());
        }
        Log.i("===","搜索出：" + lamps.size() + "个灯具");
    }



    // 这个handler是一个用来处理内部类与外部变量不和谐的值传递
    // 主要是用来修改 Lamps 列表中的值

    private Handler updataLampList = new Handler() {
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (HandlerConstantByDxy.receiveType == msg.what) {
                Bundle bundle = msg.getData();
                String mySequen = bundle.getString("sequence");
                String childType = bundle.getString("childType");
                LampDataBase db = new LampDataBase(getActivity());
                Lamp lamp = db.findLamp(mySequen);
                if (null == lamp) {
                    lamp = new Lamp();
                }
                lamp.setL_sequence(mySequen);
                lamp.setL_type(childType);
                lamps.add(lamp);
                System.out.println("Lamps 集合的 Size is " + lamps.size());
            }
        }
    };


    private Handler searchHandler = new Handler() {
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            Bundle data = msg.getData();
            String result = data.getString("result");
            if (result.equals("success")) {
                if (initDevice == null) {
                    lvAdapter = new DeviceBoundLVAdapter(getActivity(), lamps, null);
                } else {
                    lvAdapter = new DeviceBoundLVAdapter(getActivity(), lamps,
                            initDevice);
                    // System.out.println("要修改的灯的id是："+initDevice.get_id()+
                    // "，序列号是："+initDevice.getLamp().getL_sequence());
                }

                listView.setAdapter(lvAdapter);
                progressDialog.dismiss();

//                lvAdapter.notifyDataSetChanged();
//                refresh.setVisibility(View.VISIBLE);
//                refreshing.setVisibility(View.GONE);
            }
        }
    };


}
