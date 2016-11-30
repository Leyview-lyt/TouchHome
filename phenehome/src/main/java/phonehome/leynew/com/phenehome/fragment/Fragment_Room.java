package phonehome.leynew.com.phenehome.fragment;

import android.app.ActionBar;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import phonehome.leynew.com.phenehome.MainActivity;
import phonehome.leynew.com.phenehome.R;
import phonehome.leynew.com.phenehome.adapter.AreaGVAdapter;
import phonehome.leynew.com.phenehome.damain.Images;
import phonehome.leynew.com.phenehome.damain.Room;
import phonehome.leynew.com.phenehome.db.ImagesDatabase;
import phonehome.leynew.com.phenehome.db.RoomDataBase;
import phonehome.leynew.com.phenehome.dialog.AddRoomDialog;
import phonehome.leynew.com.phenehome.dialog.DeleteRoomDialog;
import phonehome.leynew.com.phenehome.dialog.UpdateRoomDialog;

/**
 */
public class Fragment_Room extends Fragment implements View.OnClickListener, AdapterView.OnItemClickListener {


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
     * Integer为ID，String为Path
     */
    private Map<Integer, String> images = new HashMap<Integer, String>();
    /**
     * 当前的区域数据
     */
    private int type ;//GridView显示的类型,默认是4: 2代表修改,3代表删除,4代表正常.
    private List<Room> currentRooms = new ArrayList<Room>();
    private AreaGVAdapter adapter_Current, adapter_Normal, adapter_Update, adapter_Delete;
    private GridView gridView_Area_Griview ;
    public static Room ROOM;
    public static int IMG;
    public static String TEXT;


    private Handler handlerDialogClose = new Handler(){
        public void handleMessage(Message msg){
            super.handleMessage(msg);
            currentRooms.clear();
            List<Room> list = getCurrentData();
            for (Room r : list) {
                currentRooms.add(r);
            }
            adapter_Current.notifyDataSetChanged();
        }
    };


    @Override
    public void onClick(View view) {
        int id = view.getId();
        if (id == R.id.id_top_Menu) {
            initPopWindow();
        }

    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        type = getArguments().getInt("type");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mainActivity = (MainActivity) getActivity();
        View view = inflater.inflate(R.layout.fragment_area_view, container, false);
        top_Menu = (Button) view.findViewById(R.id.id_top_Menu);
        top_Menu.setOnClickListener(this);
        top_Back = (Button) view.findViewById(R.id.id_top_back);
        top_Back.setVisibility(View.GONE);
        top_Title = (TextView) view.findViewById(R.id.id_top_title);
        top_Title.setText(getResources().getString(R.string.area));


        //获取griview所需数据，（Area当前的数据）
        currentRooms = getCurrentData();
        gridView_Area_Griview = (GridView) view.findViewById(R.id.id_gridView);
        //生成所需Adapter
        // TODO: 2016/11/2/002 根据条件选择Adapter
//        adapter_Normal = new AreaGVAdapter(this.getActivity(), currentRooms, images, false, false);
//        adapter_Current = adapter_Normal;
//        gridView_Area_Griview.setAdapter(adapter_Normal);
        creatAdapter(type,currentRooms);
        gridView_Area_Griview.setOnItemClickListener(new ItemClick(this));


        return view;
    }

    public void creatAdapter(int type, List<Room> rooms){
        if (type == 2) {//更新
            adapter_Current = new AreaGVAdapter(this.getActivity(), rooms, images, true, false);
        }else if (type == 3){//删除
            adapter_Current = new AreaGVAdapter(this.getActivity(), rooms, images, false, true);
        }else if (type == 4){//正常
            adapter_Current = new AreaGVAdapter(this.getActivity(), rooms, images, false, false);
        }
        gridView_Area_Griview.setAdapter(adapter_Current);
    }




    private List<Room> getCurrentData() {
        List<Room> rooms = new ArrayList<Room>();
        //图片数据库
        ImagesDatabase idb = new ImagesDatabase(this.getActivity());
        List<Images> list = idb.findRoomImages(); //查询了11张图片？不是应该当前拥有的图片
        for (Images img : list) {
            images.put(img.get_id(), img.getPath());
        }
        idb.close();
        RoomDataBase rdb = new RoomDataBase(this.getActivity());
        rooms = rdb.findRoom();//查询所有本机区域
        rdb.close();
        return rooms;
    }

    public void initPopWindow() {
        String menu[] = getResources().getStringArray(R.array.sa); //
        layout = (LinearLayout) LayoutInflater.from(this.getActivity()).inflate(
                R.layout.area_dialog, null);
        listView = (ListView) layout.findViewById(R.id.area_dialog);
        listView.setAdapter(new ArrayAdapter<String>(this.getActivity(),
                R.layout.area_dialog_item, R.id.area_dialog_item, menu));
        listView.setOnItemClickListener(this);
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
     * PopWindow的监听
     * @param adapterView
     * @param view
     * @param i
     * @param l
     */
    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        mPop.dismiss();
        currentRooms = getCurrentData();//重新获取数据
        if (i!=1) {
            type = i ;
        }else type = 4 ;
        switch (i) {
            case 1:
//                mainActivity.switchContent(this, new Fragment_Room_Add(), true,4);
                // TODO: 2016/11/21/021 界面结合
//                adapter = new GridViewAdapter(context, rooms, images,
//                        false, false);
//                gridView.setAdapter(adapter);
                creatAdapter(4,currentRooms);
                AddRoomDialog dialog = new AddRoomDialog(getActivity(), handlerDialogClose);
                dialog.show();
                mPop.dismiss();

                break;
            case 2:
                // TODO: 2016/10/31/031
//                adapter_Update = new AreaGVAdapter(this.getActivity(), currentRooms, images, true, false);
//                adapter_Current = adapter_Update;
//                gridView_Area_Griview.setAdapter(adapter_Update);
                creatAdapter(2,currentRooms);
                adapter_Current.notifyDataSetChanged();
                break;

            case 3:
//                adapter_Delete = new AreaGVAdapter(this.getActivity(), currentRooms, images, false, true);
//                adapter_Current = adapter_Delete;
//                gridView_Area_Griview.setAdapter(adapter_Delete);
                creatAdapter(3,currentRooms);
                adapter_Current.notifyDataSetChanged();
                break;

            case 4:
//                adapter_Normal = new AreaGVAdapter(this.getActivity(), currentRooms, images, false, false);
//                adapter_Current = adapter_Normal;
//                gridView_Area_Griview.setAdapter(adapter_Normal);
                creatAdapter(4,currentRooms);
                adapter_Current.notifyDataSetChanged();
                break;
        }
    }

    public AreaGVAdapter getAdapter_Current() {
        return adapter_Current;
    }

    class ItemClick implements AdapterView.OnItemClickListener {
        private Fragment fragment;
        public ItemClick(Fragment fragment) {
            this.fragment = fragment;
        }





        @Override
        public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
            ROOM = currentRooms.get(position);//点击的Room
            IMG = ROOM.getR_path();//回显图片
            TEXT = ROOM.getR_name();//回显名字
            if (type == 2) {//修改
//                mainActivity.switchContent(fragment, new Fragment_Room_Update(), true,4);
                UpdateRoomDialog dialog = new UpdateRoomDialog(getActivity(),handlerDialogClose);
                dialog.show();
            }
            else if (type == 3) {//删除
                DeleteRoomDialog dialog = new DeleteRoomDialog(getActivity(),handlerDialogClose);
                dialog.show();
//                  new AlertDialog.Builder(fragment.getActivity())
//                        .setTitle("你确定要删除吗")
//                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
//                            @Override
//                            public void onClick(DialogInterface dialogInterface, int i) {
//                                Room room = Fragment_Room.ROOM;
//                                RoomDataBase db = new RoomDataBase(fragment.getActivity());
//                                db.delete(room.get_id());//这里需要把room里面的device一起删掉
//                                db.close();
                                //通知界面更新
//                                currentRooms = getCurrentData();
//                                adapter_Delete = new AreaGVAdapter(fragment.getActivity(), currentRooms, images, false, true);
//                                adapter_Current = adapter_Delete;
//                                gridView_Area_Griview.setAdapter(adapter_Current);
//                                creatAdapter(3,getCurrentData());
//                                adapter_Current.notifyDataSetChanged();
//                            }
//                        })
//                        .setNegativeButton("取消",null)
//                        .show();
            }else{//正常
                Log.i("====================", "" + getParentFragment());
                mainActivity.switchContent(fragment, new Fragment_Device(), true,4);
            }
        }
    }

}
