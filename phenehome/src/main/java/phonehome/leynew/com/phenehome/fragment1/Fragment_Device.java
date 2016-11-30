package phonehome.leynew.com.phenehome.fragment1;


import android.app.ActionBar;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import phonehome.leynew.com.phenehome.MainActivity;
import phonehome.leynew.com.phenehome.R;
import phonehome.leynew.com.phenehome.adapter.DeviceGridAdapter;
import phonehome.leynew.com.phenehome.damain.Device;
import phonehome.leynew.com.phenehome.damain.Room;
import phonehome.leynew.com.phenehome.db.ChildDeviceTypeDataBase;
import phonehome.leynew.com.phenehome.db.DeviceDataBase;

/**
 * A simple {@link Fragment} subclass.
 */
public class Fragment_Device extends Fragment implements View.OnClickListener, AdapterView.OnItemClickListener {

    MainActivity mainActivity = null;

    /**
     * 顶部菜单
     */
    private Button top_Menu;
    private PopupWindow mPop;
    private LinearLayout layout;
    private ListView listView;
    /**
     * 顶部返回
     */
    private Button top_Back;
    /**
     * 顶部标题
     */
    private TextView top_Title;


    /**
     * 中部
     */
    private int type ;//GridView显示的类型,默认是4: 2代表修改,3代表删除,4代表正常.
    private GridView gridView;
    private Room room = Fragment_Room.ROOM;
    private DeviceGridAdapter adapter_Current ,adapter_Normol,adapter_Delete,adapter_Update;
    private List<Device> devices = new ArrayList<Device>();
    public  static Device DEVICE;
    public	static int DIMG;
    public 	static String DTEXT;

    public Fragment_Device() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        mainActivity = (MainActivity) getActivity();
        type = getArguments().getInt("type");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_device_view,container,false);
        top_Menu = (Button) view.findViewById(R.id.id_top_Menu);
        top_Menu.setOnClickListener(this);
        //Menu菜单点击后
        String menu[] = getResources().getStringArray(R.array.sb); //
        layout = (LinearLayout) LayoutInflater.from(this.getActivity()).inflate(
                R.layout.area_dialog, null);
        listView = (ListView) layout.findViewById(R.id.area_dialog);
        listView.setAdapter(new ArrayAdapter<String>(this.getActivity(),
                R.layout.area_dialog_item, R.id.area_dialog_item, menu));
        listView.setOnItemClickListener(this);

        top_Back = (Button) view.findViewById(R.id.id_top_back);
        top_Back.setOnClickListener(this);
        top_Title = (TextView) view.findViewById(R.id.id_top_title);
        top_Title.setText("设备");

        /**
         * 中部
         */
        gridView = (GridView) view.findViewById(R.id.id_Area_GridView);
        devices = getData();
//        adapter_Normol = new DeviceGridAdapter(this.getActivity(), devices, true, false);
//        adapter_Current = adapter_Normol;
//        gridView.setAdapter(adapter_Current);
        creatAdapter(type,devices);

        gridView.setOnItemClickListener(new ItemClick(this));


        return view;
    }

    public void creatAdapter(int type, List<Device> devices){
        if (type == 2) {//更新
            adapter_Current =  new DeviceGridAdapter(this.getActivity(), devices, true, false);
        }else if (type == 3){//删除
            adapter_Current =  new DeviceGridAdapter(this.getActivity(), devices, false, true);
        }else if (type == 4){//正常
            adapter_Current =  new DeviceGridAdapter(this.getActivity(), devices, false, false);
        }
        gridView.setAdapter(adapter_Current);
    }

    private List<Device> getData() {
        List<Device> devices = new ArrayList<Device>();
        DeviceDataBase ddb = new DeviceDataBase(this.getActivity());
        devices = ddb.findDevice(room);
        System.out.println("getData" + devices.size() + "getData");
        ddb.close();
        return devices;
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        if (id == R.id.id_top_Menu) {
            initPopWindow();
        } else if (id == R.id.id_top_back) {
            mainActivity.popFragments();
//            mainActivity.switchContent(this,new Fragment_Room(),false,4 );
        }
    }

    public void initPopWindow() {
        if (mPop == null) {
            mPop = new PopupWindow(layout, 200,
                    ActionBar.LayoutParams.WRAP_CONTENT);
            mPop.setBackgroundDrawable(getResources().getDrawable(
                    R.drawable.reg_tip_win));
            mPop.setAnimationStyle(R.style.Transparent);
        }
        if (mPop.isShowing()) {
            mPop.dismiss();
        }
        mPop.setBackgroundDrawable(getResources().getDrawable(
                R.drawable.reg_tip_win));
        mPop.setFocusable(true);
        mPop.setAnimationStyle(R.style.AppTheme);
        // mPop.showAtLocation(findViewById(R.id.area_menu),
        // Gravity.LEFT, x, y);
        mPop.showAsDropDown(top_Menu, 15, 2);
        mPop.update();
        mPop.setOutsideTouchable(true);
        mPop.setFocusable(true);

    }

    /**
     * 菜单选择
     * @param adapterView
     * @param view
     * @param i
     * @param l
     */
    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        mPop.dismiss();
        devices = getData();//重新获取数据
        if (i!=1) {
            type = i ;
        }
        switch (i) {
            case 1:
                mainActivity.switchContent(this, new Fragment_Device_Add(), true,4);
                break;
            case 2:
//                adapter_Update = new DeviceGridAdapter(this.getActivity(), devices,  true, false);
//                adapter_Current = adapter_Update;
//                gridView.setAdapter(adapter_Current);
                creatAdapter(2,devices);
                adapter_Current.notifyDataSetChanged();
                break;
            case 3:
//                adapter_Delete = new DeviceGridAdapter(this.getActivity(), devices,  false, true);
//                adapter_Current = adapter_Delete;
//                gridView.setAdapter(adapter_Current);
                creatAdapter(3,devices);
                adapter_Current.notifyDataSetChanged();
                break;

            case 4:
//                adapter_Normol = new DeviceGridAdapter(this.getActivity(), devices,  false, false);
//                adapter_Current = adapter_Normol;
//                gridView.setAdapter(adapter_Current);
                creatAdapter(4,devices);
                adapter_Current.notifyDataSetChanged();
                break;
        }
    }

    private class ItemClick implements AdapterView.OnItemClickListener {

        private Fragment fragment;
        public ItemClick(Fragment fragment) {
            this.fragment = fragment;
        }

        public void onItemClick(AdapterView<?> arg0, View view, int position, long arg3){
            System.out.println("ItemClick");
            DEVICE = devices.get(position);
            DIMG = DEVICE.getD_path();
            DTEXT = DEVICE.getD_name();
            System.out.println("ItemClickID"+DEVICE.get_id());
            ImageView img = (ImageView) view.findViewById(R.id.gridview_item_img);
            ImageView update = (ImageView) view.findViewById(R.id.update_room);
            ImageView delete = (ImageView) view.findViewById(R.id.delete_room);
            if(type == 2){
                mainActivity.switchContent(fragment, new Fragment_Device_Update(), true,4);
//                UpdateDeviceDialog dialog = new UpdateDeviceDialog(context, handlerDialogClose);
//                dialog.show();
            }
            else if(type == 3) {
                new AlertDialog.Builder(fragment.getActivity())
                        .setTitle("你确定要删除吗")
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                Device device = DEVICE;
                                DeviceDataBase db = new DeviceDataBase(fragment.getActivity());
                                db.deleteDevice(device);
                                db.close();
                                ChildDeviceTypeDataBase deleteChild = new ChildDeviceTypeDataBase(fragment.getActivity());
                                deleteChild.deleteChildDeviceByDeviceId(device.get_id());
                                deleteChild.close();
                                //通知界面更新
                                creatAdapter(3,getData());
                                adapter_Current.notifyDataSetChanged();
                            }
                        })
                        .setNegativeButton("取消",null)
                        .show();
                
            }
            // TODO: 2016/11/2/002 跳转控制界面和绑定设备
            else if(type == 4) {
                int l_id = DEVICE.getL_id();
                System.out.println("ItemClick" + DEVICE.get_id());
                System.out.println("ItemClick" + DEVICE.getD_name());
                System.out.println("ItemClick" + DEVICE.getR_id());
                System.out.println("ItemClick" + DEVICE.getL_id());
                if (DEVICE.get_id() != -1){
                    // TODO: 2016/11/21/021 回显数据 
                    Fragment_Device_Add.D = devices.get(position);
                    DeviceDataBase db = new DeviceDataBase(getActivity());
                    int d_id = db.getDeviceID(DEVICE);
                    DEVICE.set_id(d_id);
                    db.close();
                    mainActivity.switchContent(fragment, new Fragment_Device_Bound(), true,3);
                }
                // TODO: 2016/11/21/021 控制界面
            }
        }
    }

}
