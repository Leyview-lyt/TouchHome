package phonehome.leynew.com.phenehome.fragment1;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import phonehome.leynew.com.phenehome.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class Fragment_Plan extends Fragment {


    public Fragment_Plan() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.plan,container,false);
        return view;
    }

}
