package phonehome.leynew.com.phenehome.fragment;


import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import phonehome.leynew.com.phenehome.R;
import phonehome.leynew.com.phenehome.plan.Plan;
import phonehome.leynew.com.phenehome.plan.PlanDataBase;
import phonehome.leynew.com.phenehome.plan.PlanEditDialogFragment;
import phonehome.leynew.com.phenehome.plan.PlanLVAdapter;
import phonehome.leynew.com.phenehome.util.Util;

/**
 * A simple {@link Fragment} subclass.
 */
public class Fragment_Plan extends Fragment {

    private ListView lv;
    private List<Plan> list = new ArrayList<Plan>();
    private Context context;

    private PlanLVAdapter adapter;
    private Plan resultPlan;
    private PlanEditDialogFragment pedf;
    //	private ImageButton area;
//	private ImageButton more;
    LinearLayout id_tab_area;
    LinearLayout id_tab_setting;
    /**
     * 顶部
     */
    private Button top_Menu;
    private Button top_Back;
    private TextView top_Title;

    public Fragment_Plan() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.plan_layout,container,false);
        top_Menu = (Button) view.findViewById(R.id.id_top_Menu);
        top_Menu.setVisibility(View.GONE);
        top_Back = (Button) view.findViewById(R.id.id_top_back);
        top_Back.setVisibility(View.GONE);
        top_Title = (TextView) view.findViewById(R.id.id_top_title);
        top_Title.setText(getResources().getString(R.string.plan));


        context = getActivity();
        initView(view);
        setViewListener();
        adapter = new PlanLVAdapter(context, list, handler);
        lv.setAdapter(adapter);
        return  view;
    }

    private Handler handlerData = new Handler(){
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            adapter = new PlanLVAdapter(context, list, handler);
            lv.setAdapter(adapter);
        }
    };

    @Override
    public void onResume() {
        super.onResume();
        if (list.size()!=0) {
            list.clear();
        }
        new Thread(){
            @Override
            public void run(){
                getPlanData();
                Util.sendMessage(handlerData, null);
                super.run();
            }
        }.start();
    }





    private void getPlanData(){
        PlanDataBase db = new PlanDataBase(context);
        list = db.findAllPlan();
        System.out.println("   getPlanData       ");
        db.close();
    }


    private void setViewListener(){
        // TODO Auto-generated method stub
    }

    private void initView(View view){
        // TODO Auto-generated method stub
        //plan_head = (LinearLayout) findViewById(R.id.plan_head);

        //plan_content = (LinearLayout) findViewById(R.id.plan_content);
//		area = (ImageButton) findViewById(R.id.area);
//		more = (ImageButton) findViewById(R.id.more);
//        id_tab_area = (LinearLayout)view.findViewById(R.id.id_tab_area);
//        id_tab_setting = (LinearLayout) view.findViewById(R.id.id_tab_setting);

//        id_tab_area.setOnClickListener(new View.OnClickListener(){
//            @Override
//            public void onClick(View v){
//                // TODO Auto-generated method stub
//                Intent intent = new Intent();
//                intent.setClass(PlanActivity.this, MainActivity.class);
//                startActivity(intent);
//            }
//        });
//
//        id_tab_setting.setOnClickListener(new View.OnClickListener() {
//            public void onClick(View v) {
//                // TODO Auto-generated method stub
//                Intent  intent = new Intent();
//                intent.setClass(PlanActivity.this, SettingActivity.class);
//                startActivity(intent);
//            }
//        });
        lv = (ListView) view.findViewById(R.id.plan_lv);
    }

//    public Object $(int id) {
//        return findViewById(id);
//    }

    private Handler handler = new Handler(){
        @SuppressLint("NewApi")
        public void handleMessage(Message msg) {
            // TODO Auto-generated method stub
            super.handleMessage(msg);
            Bundle data = msg.getData();
            String str = data.getString("result");
            if (str.equals("edit")) {
                resultPlan = (Plan) data.getSerializable("Plan");
                System.out.println("handler"+resultPlan.get_id());
                pedf = new PlanEditDialogFragment(resultPlan);
                pedf.show(getActivity().getFragmentManager(), "edit");
            }
        }
    };

    public void onClick(View v) {
        // TODO Auto-generated method stub
    }

}
