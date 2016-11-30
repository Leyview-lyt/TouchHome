package phonehome.leynew.com.phenehome.util;

import android.app.ActionBar;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

import phonehome.leynew.com.phenehome.R;

/**
 * Created by Administrator on 2016/10/29/029.
 */
public class TopViewUtil {
    /**
     * 顶部菜单
     */
    private static Button top_Menu;
    private static PopupWindow mPop;
    private static LinearLayout layout;
    private static ListView listView;

    /**
     * 顶部返回
     */
    private static Button top_Back;


    /**
     * 顶部标题
     */
    private static TextView top_Title;

    public static void setTopName(View view , String title, String menu){

        top_Menu = (Button) view.findViewById(R.id.id_top_Menu);
        top_Menu.setText(menu);
        top_Back = (Button) view.findViewById(R.id.id_top_back);

        top_Title = (TextView) view.findViewById(R.id.id_top_title);
        top_Title.setText(title);
    }




    public void initPopWindow(Fragment fragment){
        String menu[] = fragment.getResources().getStringArray(R.array.sa); //
        layout = (LinearLayout) LayoutInflater.from(fragment.getActivity()).inflate(
                R.layout.area_dialog, null);
        listView = (ListView) layout.findViewById(R.id.area_dialog);
        listView.setAdapter(new ArrayAdapter<String>(fragment.getActivity(),
                R.layout.area_dialog_item, R.id.area_dialog_item, menu));
//        listView.setOnItemClickListener(fragment.getContext());
        if (mPop == null) {
            mPop = new PopupWindow(layout, 200,
                    ActionBar.LayoutParams.WRAP_CONTENT);
            mPop.setBackgroundDrawable(fragment.getResources().getDrawable(
                    R.drawable.reg_tip_win));
            mPop.setAnimationStyle(R.style.Transparent);
        }
        if (mPop.isShowing()) {
            mPop.dismiss();
        }
        mPop.setBackgroundDrawable(fragment.getResources().getDrawable(
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
}
