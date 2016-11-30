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
import phonehome.leynew.com.phenehome.damain.Images;
import phonehome.leynew.com.phenehome.damain.Room;
import phonehome.leynew.com.phenehome.db.ImagesDatabase;
import phonehome.leynew.com.phenehome.db.RoomDataBase;


public class Fragment_Room_Add extends Fragment implements View.OnClickListener, AdapterView.OnItemClickListener {

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
    private List<Images> allRoomData = new ArrayList<Images>();
    private Map<Integer, String> images = new HashMap<Integer, String>();
    private AreaAddGVAdapter areaAddGVAdapter;
    private GridView gridView ;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mainActivity = (MainActivity) getActivity();
        View view = inflater.inflate(R.layout.fragment_area_add_view,container,false);
        top_Menu = (Button) view.findViewById(R.id.id_top_Menu);
        top_Menu.setText("保存");
        top_Menu.setOnClickListener(this);
        top_Back = (Button) view.findViewById(R.id.id_top_back);
        top_Back.setOnClickListener(this);
        top_Title = (TextView) view.findViewById(R.id.id_top_title);
        top_Title.setText("添加区域");

        editName = (EditText) view.findViewById(R.id.id_editName);
        addRoom_Image = (ImageView) view.findViewById(R.id.id_addRoom_Image);


        //获取数据
        allRoomData = getData();
        resid =  Integer.parseInt(allRoomData.get(0).getPath());
        addRoom_Image.setBackgroundResource(Integer.parseInt(allRoomData.get(0).getPath()));//设置默认
        areaAddGVAdapter = new AreaAddGVAdapter(this.getActivity(), allRoomData);
        gridView = (GridView) view.findViewById(R.id.id_GridView);
        gridView.setAdapter(areaAddGVAdapter);
        gridView.setOnItemClickListener(this);
        return view;
    }

    private List<Images> getData() {
        ImagesDatabase idb = new ImagesDatabase(this.getActivity());
        allRoomData = idb.findRoomImages();//查询所有可加区域图片
        for (Images img : allRoomData) {
            images.put(img.get_id(), img.getPath());
        }
        idb.close();
        idb.close();
        return allRoomData;
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        if (id == R.id.id_top_Menu) {
            Room room = new Room();
            String name = editName.getText().toString().trim();//获得输入框的数据
            System.out.println(""+name);
            if (name.equals("") || name == null) {
                Toast.makeText(this.getActivity(), this.getActivity().getString(R.string.tip_floorName_empty), Toast.LENGTH_LONG).show();
                return;
            }
            RoomDataBase db = new RoomDataBase(this.getActivity());
            room.setR_name(name);
            room.setR_path(resid);
            room.setF_id(1);
            db.save(room);
            db.close();
            Log.i("=====================","保存成功");
        }

        mainActivity.switchContent(this, new Fragment_Room(),false,4);

    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        //设置选择的图片(路径跟ID同名)
        addRoom_Image.setBackgroundResource(Integer.parseInt(allRoomData.get(i).getPath()));
        resid = Integer.parseInt(allRoomData.get(i).getPath());

    }
}
