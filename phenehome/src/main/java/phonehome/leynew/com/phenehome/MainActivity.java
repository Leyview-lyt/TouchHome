package phonehome.leynew.com.phenehome;


import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;

import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import java.util.ArrayList;
import java.util.List;

import phonehome.leynew.com.phenehome.damain.Room;
import phonehome.leynew.com.phenehome.fragment.Fragment_Room_Add;
import phonehome.leynew.com.phenehome.fragment.Fragment_Room;
import phonehome.leynew.com.phenehome.fragment.Fragment_Me;
import phonehome.leynew.com.phenehome.fragment.Fragment_Plan;
import phonehome.leynew.com.phenehome.view.MyRadioButton1;

public class MainActivity extends FragmentActivity implements RadioGroup.OnCheckedChangeListener {

    private static final String TAG = "POPALL";
    // Lindex为了防止重复点击相同按钮导致重复执行而设置的判断变量
    private static int Lindex = 100;

    private Fragment fragment;
    private FragmentManager fragmentManager;
    private FragmentTransaction transaction;
    List<Fragment> fragments = new ArrayList<Fragment>();


    private Fragment_Room mFragment_Area;
    private Fragment_Room_Add mFragment_Add_Room;


    private Fragment_Me mFragment_Me;


    private Fragment_Plan mFragment_Plan;


    private RadioGroup mRadioGroup;
    private MyRadioButton1 mRadioButton0;
    private MyRadioButton1 mRadioButton1;
    private MyRadioButton1 mRadioButton2;

    private Button mbutton;
    String menu[] = null;
    private PopupWindow mPop;
    private ListView listView;
    private LinearLayout layout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main1);
        // 实例化FragmentManager类

        fragmentManager = getSupportFragmentManager();
//        fragment = fragmentManager.findFragmentById(R.id.id_Top);
//        transaction = fragmentManager.beginTransaction();
//        transaction.add(R.id.id_Top, fragment).commit();

        // 第一次启动时选中第0个tab，也就是默认选中左侧栏中的终端按钮。
        setTabSelection(0);
        mRadioGroup = (RadioGroup) findViewById(R.id.id_Area_Group);
        mRadioButton0 = (MyRadioButton1) findViewById(R.id.id_Area_0);
        mRadioButton1 = (MyRadioButton1) findViewById(R.id.id_Area_1);
        mRadioButton2 = (MyRadioButton1) findViewById(R.id.id_Area_2);
        mRadioGroup.setOnCheckedChangeListener(this);
    }


    public void setTabSelection(int index) {
        //如果当前fragment不为空,让所有fragment弹出
        List<Fragment> list = fragmentManager.getFragments();
        if (list != null) {
            for (int i = 0; i < list.size(); i++) {
                Log.i("==================方法后", fragmentManager.getFragments().size() + "");
                fragmentManager.popBackStackImmediate();
                //fragmentManager.beginTransaction().hide(list.get(i));
            }
        }
        // 开启一个Fragment事务
        FragmentTransaction transaction = fragmentManager.beginTransaction();

        switch (index) {
            case 0:
                // 初始化ClientFragment
                mFragment_Area = new Fragment_Room();
                Bundle bundle = new Bundle();
                bundle.putInt("type",4);
                mFragment_Area.setArguments(bundle);
                transaction.replace(R.id.id_Top, mFragment_Area);
                transaction.commit();
                break;
            case 1:
                // 初始化Fragment_Plan
                mFragment_Plan = new Fragment_Plan();
                transaction.replace(R.id.id_Top, mFragment_Plan);
                transaction.commit();
                break;
            case 2:
                // 初始化Fragment_Me
                mFragment_Me = new Fragment_Me();
                transaction.replace(R.id.id_Top, mFragment_Me);
                transaction.commit();
                break;
        }
    }


    /**
     * 隐藏要调用的frament,显示要用的fragment
     *
     * @param from
     * @param to
     */
    public void switchContent(Fragment from, Fragment to, boolean isAddtoBackStack,int type) {
        Bundle bundle = new Bundle();
        bundle.putInt("type",type);
        to.setArguments(bundle);
        if (from != to) {
            FragmentTransaction transaction = fragmentManager.beginTransaction();
            transaction.remove(from);

//            int count =fragmentManager.getBackStackEntryCount();
//            if (count>0) {
//                for (int i = 0; i < count; i++) {
//                    FragmentManager.BackStackEntry entry = fragmentManager.getBackStackEntryAt(i);
//                    Log.i("===================名字是:", entry.getName() + ""+entry.getId());
//                }
//            }

            if (isAddtoBackStack) {//是否加入返回栈
                transaction.replace(R.id.id_Top, to);
                transaction.addToBackStack(null);
                transaction.commit();
            }else {
                fragmentManager.popBackStackImmediate();
                transaction.replace(R.id.id_Top, to);
                transaction.commit();

            }
            //to.getLoaderManager().hasRunningLoaders();
//            if (isAddtoBackStack) {//是否加入返回栈
//                Log.i("=====================前", fragmentManager.getBackStackEntryCount() + "");
//                fragments.add(to);//
//                transaction.hide(from).add(R.id.id_Top, to, TAG).addToBackStack(null).show(to).commit();
//
//            } else {
//                Log.i("=====================返回", fragmentManager.getBackStackEntryCount() + "");
//                transaction.hide(from).add(R.id.id_Top, to, TAG).show(to).commit();
//
//            }

        }

    }


    @Override
    public void onCheckedChanged(RadioGroup radioGroup, int i) {
        switch (i) {

            case R.id.id_Area_0:
                setTabSelection(0);
                break;
            case R.id.id_Area_1:
                setTabSelection(1);
                break;
            case R.id.id_Area_2:
                setTabSelection(2);
                break;
        }


    }


    public void popFragments() {
        fragmentManager.popBackStackImmediate();
    }

/*****************************新增**********************************/











    /********************* 隐藏输入法  *********************/

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        // TODO Auto-generated method stub
        if (ev.getAction() == MotionEvent.ACTION_DOWN) {
            View view = getCurrentFocus();
            if (isHideInput(view, ev)) {
                HideSoftInput(view.getWindowToken());
            }
        }
        return super.dispatchTouchEvent(ev);
    }

    // 判定是否需要隐藏
    private boolean isHideInput(View v, MotionEvent ev) {
        if (v != null && (v instanceof EditText)) {
            int[] l = { 0, 0 };
            v.getLocationInWindow(l);
            int left = l[0], top = l[1], bottom = top + v.getHeight(), right = left
                    + v.getWidth();
            if (ev.getX() > left && ev.getX() < right && ev.getY() > top
                    && ev.getY() < bottom) {
                return false;
            } else {
                return true;
            }
        }
        return false;
    }

    // 隐藏软键盘
    private void HideSoftInput(IBinder token) {
        if (token != null) {
            InputMethodManager manager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            manager.hideSoftInputFromWindow(token,
                    InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }


}
