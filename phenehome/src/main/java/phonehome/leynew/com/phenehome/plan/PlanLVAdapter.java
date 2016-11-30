package phonehome.leynew.com.phenehome.plan;


import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;



import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import phonehome.leynew.com.phenehome.R;
import phonehome.leynew.com.phenehome.control.SringToHexString;
import phonehome.leynew.com.phenehome.control.UnionSendInfoClass;
import phonehome.leynew.com.phenehome.damain.Device;
import phonehome.leynew.com.phenehome.db.DeviceDataBase;
import phonehome.leynew.com.phenehome.util.LeyNew;
import phonehome.leynew.com.phenehome.util.Util;

public class PlanLVAdapter extends BaseAdapter {

    private Context context;
    private List<Plan> list = new ArrayList<Plan>();
    private Handler handler;//
    private boolean isEdit = false;//
    private boolean isOnOff = false;

    public PlanLVAdapter(Context context, List<Plan> list, Handler handler) {
        super();
        this.context = context;
        this.list = list;
        this.handler = handler;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        System.out.println("11 PlanLVAdapter");
        if (!isEdit && convertView != null) {
            return convertView;
        }
        convertView = LayoutInflater.from(context).inflate(R.layout.plan_item,
                null);

        boolean isEveryday = true;//
        boolean isNoRepeat = true;//

        TextView txt_plan = (TextView) convertView
                .findViewById(R.id.plan_item_txt_plan);
        final Button onoff = (Button) convertView //
                .findViewById(R.id.plan_item_btn_on);
        Button btn_edit = (Button) convertView //
                .findViewById(R.id.plan_item_btn_edit);
        TextView txt_time = (TextView) convertView //
                .findViewById(R.id.plan_item_txt_time);
        LinearLayout ll_date = (LinearLayout) convertView
                .findViewById(R.id.plan_item_ll_day);

        //
        boolean dates[] = Plan.getDayIsck(list.get(position).getP_date());
        for (int i = 0; i < dates.length; i++) {
            if (dates[i]) {
                TextView tv = new TextView(context);
                tv.setTextSize(14);
                if (i == 0)
                    tv.setText(context.getString(R.string.mon));
                else if (i == 1)
                    tv.setText(context.getString(R.string.tue));
                else if (i == 2)
                    tv.setText(context.getString(R.string.wed));
                else if (i == 3)
                    tv.setText(context.getString(R.string.thu));
                else if (i == 4)
                    tv.setText(context.getString(R.string.fri));
                else if (i == 5)
                    tv.setText(context.getString(R.string.sat));
                else if (i == 6)
                    tv.setText(context.getString(R.string.sun));
                ll_date.addView(tv);
                isNoRepeat = false;
            } else {
                isEveryday = false;
            }
        }
        if (isEveryday) {//
            ll_date.removeAllViews();
            TextView tv = new TextView(context);
            tv.setTextSize(18);
            tv.setText(context.getString(R.string.everyday));
            ll_date.addView(tv);
        } else if (isNoRepeat) {
            ll_date.removeAllViews();
            TextView tv = new TextView(context);
            tv.setTextSize(18);
            tv.setMaxLines(1);
            tv.setText(context.getString(R.string.norepeat));
            ll_date.addView(tv);
        }
        if (list.get(position).isP_status()) {
            onoff.setBackgroundResource(R.drawable.plan_on);
        } else {
            onoff.setBackgroundResource(R.drawable.plan_off);
        }
        Log.i("=========时间为: ",list.get(position).getP_time() );
        txt_time.setText(list.get(position).getP_time());
        txt_plan.setText(context.getString(R.string.plan) + "" + (position + 1));
        onoff.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                isOnOff = !isOnOff;
                if (isOnOff) {
                    onoff.setBackgroundResource(R.drawable.plan_on);
                    openPlan(position + 1, true);
                } else {
                    onoff.setBackgroundResource(R.drawable.plan_off);
                    openPlan(position + 1, false);
                }
                list.get(position).setP_status(isOnOff);
                System.out.println("22222222"
                        + list.get(position).isP_status());
                //
                PlanDataBase db = new PlanDataBase(context);
                //
                db.updateStatus(list.get(position).get_id(), isOnOff);
                db.close();
            }
        });

        btn_edit.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                Message msg = new Message();
                Bundle data = new Bundle();
                data.putString("result", "edit");
                data.putSerializable("Plan", list.get(position));
                msg.setData(data);
                handler.sendMessage(msg);
            }
        });

        //
        ll_date.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                EditPlanDateDialog dialog = new EditPlanDateDialog(context,
                        list.get(position), handler2);
                dialog.show();
            }
        });

        txt_time.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                EditPlanDateDialog dialog = new EditPlanDateDialog(context,
                        list.get(position), handler2);
                dialog.show();
            }
        });
        return convertView;
    }


    private void openPlan(final int pid, final boolean isOpen) {

        final Plan plan = list.get(pid - 1); //
        String str[] = plan.getP_time().split(":");
        final String h = str[0]; //
        final String m = str[1];

        PlanDataBase db = new PlanDataBase(context);
        System.out.println("00000000" + pid);
        final List<PlanDeviceStore> list = db.findAllStore(pid);
        System.out.println("00000000000" + list.size());
        db.close();
        for (int i = 0; i < list.size(); i++) {
            final int time = i * 300;
            final int index = i;
            System.out.println(" " + index + "-------------------");
            new Thread() {
                public void run() {
                    // try {
                    // Thread.sleep(time);
                    // } catch (InterruptedException e){
                    // e.printStackTrace();
                    // return;
                    // }
                    Log.i("=======", "进入发送指令线程");
                    PlanDeviceStore pds = list.get(index);
                    DeviceDataBase ddb = new DeviceDataBase(context);
                    Device device = ddb.findDeviceById(pds.getD_id());


                    /**
                     * 自定义
                     */
                    if (pds.getPds_custom() != 0) {
                        Log.i("=======", "进入发送指令线程1:  " + "开关:" + isOpen + "时间:" + h + ":" + m);

                        Util.sendCommand(LeyNew.SETTIMING,
                                new String[]{(isOpen ? 1 : 0) + "",
                                        pid - 1 + "",
                                        h,
                                        m,
                                        plan.getP_date() + "",
                                        "200",
                                        "200",
                                        pds.getPds_custom() + 79 + "",
                                        "0",
                                        "0",
                                        "0"
                                },
                                device.getLamp().getL_sequence());
                    }

                    /**
                     * 模式选择
                     */
                    else if (pds.getPds_mode() != 0) {

                        String onOff = "00";
                        if (plan.isP_status())
                            onOff = "01";
                        // WF 323
                        if ("WF323".equals(device.getLamp().getL_type())) {
                            String sendBefor = LeyNew.SETTM
                                    + "00"
                                    + device.getLamp().getL_sequence()
                                    + "02"
                                    + Util.integer2HexString(pid - 1)
                                    + ""
                                    + onOff
                                    + SringToHexString.getHexString(h)
                                    + SringToHexString.getHexString(m)
                                    + Util.integer2HexString(plan.getP_date())
                                    + Util.integer2HexString(pds
                                    .getPds_brightness())
                                    + ""
                                    + Util.integer2HexString(pds.getPds_speed())
                                    + ""
                                    + Util.integer2HexString(pds.getPds_mode())
                                    + "64" + LeyNew.END;
                            Log.i("=======", "进入发送指令线程2: ");

                            UnionSendInfoClass.sendSpecialOrder(Util
                                    .HexString2Bytes(sendBefor));
                        }
                        else {
                            Log.i("=======", "进入发送指令线程3:   ");
                            Util.sendCommand(
                                    LeyNew.SETTIMING,
                                    new String[]{
                                            (isOpen ? 1 : 0) + "",
                                            pid - 1 + "",
                                            h,
                                            m,
                                            plan.getP_date() + "",
                                            pds.getPds_brightness2() + 128 + "",
                                            pds.getPds_speed() + "",
                                            pds.getPds_mode() + "",
                                            "0"},
                                    device.getLamp().getL_sequence());
                        }
                    }


                    /**
                     * 颜色选择
                     */
                    else {
                        Log.i("=======", "进入发送指令线程4:  Rgb   ");

                        if (device.getLamp().getL_type().equals("Zigbee")) {//
                            String mark = "02"; //
                            String timer = LeyNew.SETTM + LeyNew.FLAG
                                    + device.getLamp().getL_sequence() + mark
                                    + index + LeyNew.OPEN
                                    + h
                                    + m
                                    + plan.getP_date() //
                                    //
                                    + pds.getPds_brightness()
                                    + pds.getPds_red() + pds.getPds_green()
                                    + pds.getPds_blue() + LeyNew.END;
                            byte[] timming = Util.HexString2Bytes(timer);
                            Util.sendCommand(timming, null, null);
                            Log.i("=======", "进入发送指令线程5:  Zigbee   ");
                        }
                        else if (device.getLamp().getL_type().equals("WF322") || device.getLamp().getL_type().equals("WF326")) {
                            Log.i("=======", "进入发送指令线程6:  WF322  WF326  ");                                                        //
                            try {
                                // Settm
                                int wf32Bri = pds.getPds_brightness(); //
                                float wfRed = (float) (254 * (pds
                                        .getPds_colour_warm() * 0.01) * (wf32Bri * 0.01));
                                float wfGreen = (float) (254 * (pds
                                        .getPds_colour_cool() * 0.01) * (wf32Bri * 0.01));
                                int endRed = (int) (wfRed);
                                int endGreen = (int) (wfGreen);
                                System.out.println("000" + endRed);
                                System.out.println("00000" + endGreen);
                                String onOff = "00"; //0
                                if (plan.isP_status())
                                    onOff = "01";
                                else
                                    onOff = "00";
                                //
                                String beforSend = LeyNew.SETTM
                                        + "00"
                                        + device.getLamp().getL_sequence()
                                        + "02"
                                        + Util.integer2HexString(pid - 1)
                                        + ""
                                        + onOff
                                        + ""
                                        + SringToHexString.getHexString(h)
                                        + SringToHexString.getHexString(m)
                                        + Util.integer2HexString(plan
                                        .getP_date())
                                        + Util.integer2HexString(wf32Bri)
                                        + Util.integer2HexString(endRed)
                                        + Util.integer2HexString(endGreen)
                                        + Util.integer2HexString(endGreen)
                                        + LeyNew.END;
                                byte[] myEndSendByte = Util.HexString2Bytes(beforSend);
                                UnionSendInfoClass.sendSpecialOrder(myEndSendByte);
                            } catch (Exception e) {
                            }
                        }
                        else if (null != device.getLamp().getL_type() && device.getLamp().getL_type().equals("WF321")) {
                            Log.i("=======", "进入发送指令线程7:  WF321    ");
                            Calendar calendar = Calendar.getInstance();
                            int hour = calendar.get(Calendar.HOUR_OF_DAY);
                            int minute = calendar.get(Calendar.MINUTE);
                            String onOff = "00";
                            /**
                             * String sendBefor = LeyNew.SETTM + "00" +
                             * device.getLamp().getL_sequence() + "02" +
                             * Util.integer2HexString(pid - 1) + "" + onOff +
                             * SringToHexString.getHexString(h) +
                             * SringToHexString.getHexString(m) +
                             * Util.integer2HexString(plan.getP_date()) +
                             * Util.integer2HexString(pds.getPds_brightness()) +
                             * "" + Util.integer2HexString(pds.getPds_speed()) +
                             * "" + Util.integer2HexString(pds.getPds_mode())
                             * +"64" + LeyNew.END;
                             *
                             */
                            if (plan.isP_status())
                                onOff = "01";
                            int brightNess = pds.getPds_brightness();
                            int redValue = (int) (brightNess * 2.54);
                            String beforSend = LeyNew.SETTM + "00"
                                    + device.getLamp().getL_sequence() + "02"
                                    + Util.integer2HexString(pid - 1) + ""
                                    + onOff + ""
                                    + SringToHexString.getHexString(h + "")
                                    + ""
                                    + SringToHexString.getHexString(m + "")
                                    + ""
                                    + Util.integer2HexString(plan.getP_date())
                                    + "" + Util.integer2HexString(brightNess)
                                    + "" + Util.integer2HexString(redValue)
                                    + "" + Util.integer2HexString(redValue)
                                    + "" + Util.integer2HexString(redValue)
                                    + LeyNew.END;
                            byte[] endSendBytes = Util.HexString2Bytes(beforSend);
                            UnionSendInfoClass.sendSpecialOrder(endSendBytes);

                        }
                        else if ("WF323".equals(device.getLamp().getL_type())) {
                            //
                            String onOff = "00";
                            if (plan.isP_status())
                                onOff = "01";
                            String sendBefor = LeyNew.SETTM
                                    + "00"
                                    + device.getLamp().getL_sequence()
                                    + "02"
                                    + Util.integer2HexString(pid - 1)
                                    + ""
                                    + onOff
                                    + SringToHexString.getHexString(h)
                                    + SringToHexString.getHexString(m)
                                    + Util.integer2HexString(plan.getP_date())
                                    + Util.integer2HexString(pds
                                    .getPds_brightness())
                                    + ""
                                    + Util.integer2HexString(pds.getPds_red())
                                    + ""
                                    + Util.integer2HexString(pds.getPds_green())
                                    + ""
                                    + Util.integer2HexString(pds.getPds_blue())
                                    + LeyNew.END;
                            byte[] endSendBytes = Util
                                    .HexString2Bytes(sendBefor);
                            UnionSendInfoClass.sendSpecialOrder(endSendBytes);
                        }
                        else {

                            // TODO: 2016/11/29/029 指令????
                            Log.i("=======", "进入发送指令线程8:  红  "+pds.getPds_red()+"  绿"+
                                    (pds.getPds_green())+"  蓝"+
                                    (pds.getPds_blue() )+"  color"+
                                    (pds.getPds_colour())+"  亮1: "+
                                    (pds.getPds_brightness())+" warm"+
                                    (pds.getPds_colour_warm())+" 亮2: "+
                                    (pds.getPds_brightness2()));

                                Util.sendCommand(
                                        LeyNew.SETTIMING,
                                        new String[]{
                                                (isOpen ? 1 : 0) + "",
                                                pid - 1 + "",
                                                h,
                                                m,
                                                plan.getP_date() + "",
                                                pds.getPds_brightness()+""//亮度???
                                                ,
                                                pds.getPds_colour()>0?  (int) (pds.getPds_red()*(pds.getPds_colour()/100.0)) + "" ://????B模式下
                                                (int) (pds.getPds_red()*(pds.getPds_brightness()/100.0)) + ""
                                                ,
                                                pds.getPds_colour()>0? "0" ://????B模式下
                                                (int) (pds.getPds_green()*(pds.getPds_brightness()/100.0)) + ""

                                                ,
                                                (int) (pds.getPds_blue()*(pds.getPds_brightness()/100.0)) + ""
                                                ,
                                                "0"
                                        },
                                        device.getLamp().getL_sequence());
                        }
                    }
                    ddb.close();
                }
            }.start();
        }
    }


    public int getWeek(int y, int m, int d) {
        Calendar c = Calendar.getInstance(Locale.CHINA);
        c.set(Calendar.YEAR, y);
        c.set(Calendar.MONTH, m - 1);
        c.set(Calendar.DATE, d);
        return c.get(Calendar.DAY_OF_WEEK) - 1;
    }


    private Handler handler2 = new Handler() {
        public void handleMessage(Message msg) {
            // TODO Auto-generated method stub
            super.handleMessage(msg);
            Bundle data = msg.getData();
            String edit = data.getString("isEdit");
            if (edit.equals("true")) {
                Plan p = (Plan) data.getSerializable("Plan");
                list.remove(p.get_id() - 1);
                list.add(p.get_id() - 1, p);
                isEdit = true;
                PlanLVAdapter.this.notifyDataSetChanged();
            }
        }
    };
}
