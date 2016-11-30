package phonehome.leynew.com.phenehome.fragment1;



import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import phonehome.leynew.com.phenehome.MainActivity;
import phonehome.leynew.com.phenehome.R;
import phonehome.leynew.com.phenehome.adapter.AreaAddGVAdapter;
import phonehome.leynew.com.phenehome.damain.Device;
import phonehome.leynew.com.phenehome.damain.Images;
import phonehome.leynew.com.phenehome.damain.Room;
import phonehome.leynew.com.phenehome.db.DeviceDataBase;
import phonehome.leynew.com.phenehome.db.ImagesDatabase;

/**
 * A simple {@link Fragment} subclass.
 */
public class Fragment_Device_Update extends Fragment implements View.OnClickListener, AdapterView.OnItemClickListener {


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

    /**
     * 中部
     */
    private EditText editName;
    private ImageView addRoom_Image;


    /**
     * 所有可加区域的数据
     */
    private int resid;//图片ID
//    private Device device = new Device();
    private Device device = Fragment_Device.DEVICE;//使用回显的Device
    private List<Images> allDeviceData = new ArrayList<Images>();
    private Map<Integer, String> images = new HashMap<Integer, String>();
    private AreaAddGVAdapter areaAddGVAdapter;
    private GridView gridView ;
    private  Room room;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mainActivity = (MainActivity) getActivity();
        View view = inflater.inflate(R.layout.fragment_area_add_view,container,false);
        top_Menu = (Button) view.findViewById(R.id.id_top_Menu);
        top_Menu.setText("下一步");
        top_Menu.setOnClickListener(this);
        top_Back = (Button) view.findViewById(R.id.id_top_back);
        top_Back.setOnClickListener(this);
        top_Title = (TextView) view.findViewById(R.id.id_top_title);
        top_Title.setText("添加设备");

        editName = (EditText) view.findViewById(R.id.id_editName);
        editName.setText(Fragment_Device.DTEXT);//设置回显Name
        addRoom_Image = (ImageView) view.findViewById(R.id.id_addRoom_Image);


        //获取数据
        allDeviceData = getData();
//        Images image = new Images();
//        image.set_id(allDeviceData.get(0).get_id());
//        image.setPath(allDeviceData.get(0).getPath());
//        device.setImage(image);
//        addRoom_Image.setBackgroundResource(Integer.parseInt(allDeviceData.get(0).getPath()));//设置默认图片
        addRoom_Image.setBackgroundResource(Integer.parseInt(Fragment_Device.DEVICE.getImage().getPath()));//设置回显图片
        areaAddGVAdapter = new AreaAddGVAdapter(this.getActivity(), allDeviceData);
        gridView = (GridView) view.findViewById(R.id.id_GridView);
        gridView.setAdapter(areaAddGVAdapter);
        gridView.setOnItemClickListener(this);
        return view;
    }

    private List<Images> getData() {
        ImagesDatabase idb = new ImagesDatabase(this.getActivity());
        allDeviceData.clear();
//        for (Images img : idb.findRoomImages()) {
//            allDeviceData.add(img);
//        }
        allDeviceData = idb.findDeviceImages();
        idb.close();
        return allDeviceData;


    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        if (id == R.id.id_top_Menu) {
//            Device device =Fragment_Device.DEVICE;
            String name = editName.getText().toString().trim();
            if (name.equals("") || name == null){
                Toast.makeText(this.getActivity(), this.getActivity().getString(R.string.tip_floorName_empty),Toast.LENGTH_LONG).show();
                return;
            }
            DeviceDataBase db = new DeviceDataBase(this.getActivity());
            device.set_id(device.get_id());
            device.setD_name(name);
            device.setD_path(resid);
            device.setR_id(Fragment_Room.ROOM.get_id());
            db.updateDevice(device);
            if (device.getLamp() != null) {
                db.removeLamp(device.get_id(), device.getL_id());//
            }
            db.close();
            // TODO: 2016/11/1/001 需要绑定设备
            mainActivity.switchContent(this,new Fragment_Device_Bound(),true,2);
            Log.i("=====================","保存成功");
        }else if (id == R.id.id_top_back) {
            mainActivity.popFragments();
//            mainActivity.switchContent(this, new Fragment_Device(),false,2);
        }


    }


    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
        addRoom_Image.setBackgroundResource(Integer.parseInt(allDeviceData.get(position).getPath()));
        Images image = new Images();
        image.set_id(allDeviceData.get(position).get_id());
        image.setPath(allDeviceData.get(position).getPath());
        device.setImage(image);
        resid = Integer.parseInt(allDeviceData.get(position).getPath());


    }
}
