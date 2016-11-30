package phonehome.leynew.com.phenehome.fragment;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import phonehome.leynew.com.phenehome.MainActivity;
import phonehome.leynew.com.phenehome.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class Fragment_Me_Product extends Fragment {

    MainActivity mainActivity = null;

    /**
     * 顶部
     */
    private Button top_Menu;
    private Button top_Back;
    private TextView top_Title;



    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mainActivity = (MainActivity) getActivity();
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.me_product,container,false);
        top_Menu = (Button) view.findViewById(R.id.id_top_Menu);
        top_Menu.setVisibility(View.GONE);
        top_Back = (Button) view.findViewById(R.id.id_top_back);
        top_Back.setVisibility(View.GONE);
        top_Title = (TextView) view.findViewById(R.id.id_top_title);
        top_Title.setText("我的");

        View in = mainActivity.findViewById(R.id.id_Area_Tottom);
        in.setVisibility(View.GONE);

        return view;
    }

}
