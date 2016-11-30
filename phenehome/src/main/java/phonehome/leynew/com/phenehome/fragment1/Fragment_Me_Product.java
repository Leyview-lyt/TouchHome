package phonehome.leynew.com.phenehome.fragment1;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import phonehome.leynew.com.phenehome.MainActivity;
import phonehome.leynew.com.phenehome.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class Fragment_Me_Product extends Fragment {

    MainActivity mainActivity = null;

    public Fragment_Me_Product() {
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
        View view = inflater.inflate(R.layout.me_product,container,false);
        View in = mainActivity.findViewById(R.id.id_Area_Tottom);
        in.setVisibility(View.GONE);

        return view;
    }

}
