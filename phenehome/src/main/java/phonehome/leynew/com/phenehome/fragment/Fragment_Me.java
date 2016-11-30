package phonehome.leynew.com.phenehome.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.Nullable;
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


import phonehome.leynew.com.phenehome.MainActivity;
import phonehome.leynew.com.phenehome.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class Fragment_Me extends Fragment implements AdapterView.OnItemClickListener {

    MainActivity mainActivity = null;


    /**
     * 顶部
     */
    private Button top_Menu;
    private Button top_Back;
    private TextView top_Title;



    private ListView listView;


    public Fragment_Me() {
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
        View view = inflater.inflate(R.layout.me,container,false);

        top_Menu = (Button) view.findViewById(R.id.id_top_Menu);
        top_Menu.setVisibility(View.GONE);
        top_Back = (Button) view.findViewById(R.id.id_top_back);
        top_Back.setVisibility(View.GONE);
        top_Title = (TextView) view.findViewById(R.id.id_top_title);
        top_Title.setText(getResources().getString(R.string.me));


        View in = mainActivity.findViewById(R.id.id_Area_Tottom);
        in.setVisibility(View.VISIBLE);

        listView = (ListView) view.findViewById(R.id.id_me_menu);
        String menu[] = getResources().getStringArray(R.array.sd);
        listView.setAdapter(new ArrayAdapter<String>(getContext(),
                R.layout.me_dialog_item, R.id.me_dialog_item, menu));
        listView.setOnItemClickListener(this);
        return view;
    }


    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

        switch (i) {
            case 0 :
                mainActivity.switchContent(this,new Fragment_Me_SearchDevice(),true,4);
                break;


            case 1 :
//                mainActivity.switchContent(this,new Fragment_Me_Product(),true,4);
                startActivity(new Intent(Settings.ACTION_WIFI_SETTINGS));// 进入wifi连接界面
                break;


            case 2 :
                mainActivity.switchContent(this,new Fragment_Me_VerSion(),true,4);
                break;
        }
    }


}
