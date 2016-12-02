package phonehome.leynew.com.phenehome.control;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.media.SoundPool;
import android.os.Bundle;
import android.os.Handler;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnLongClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;



import java.util.ArrayList;
import java.util.List;

import phonehome.leynew.com.phenehome.R;
import phonehome.leynew.com.phenehome.damain.Device;
import phonehome.leynew.com.phenehome.util.LeyNew;
import phonehome.leynew.com.phenehome.util.Util;

public class AddThreeCustomDialog extends Dialog implements
		View.OnClickListener, OnLongClickListener,
		VerticalSeekBar.OnSeekBarChangeListener {
	private Device device;
	private Context context;
	
	private Button save, cancel;
	private EditText name;
	private ImageView r1c1, r1c2, r1c3, r1c4;
	private ImageView r2c1, r2c2, r2c3, r2c4;
	private ImageView r3c1, r3c2, r3c3, r3c4;
	private ImageView r4c1, r4c2, r4c3, r4c4;

	private RelativeLayout wr1c1, wr1c2, wr1c3, wr1c4;
	private RelativeLayout wr2c1, wr2c2, wr2c3, wr2c4;
	private RelativeLayout wr3c1, wr3c2, wr3c3, wr3c4;
	private RelativeLayout wr4c1, wr4c2, wr4c3, wr4c4;
	private View tempView;
	private View currentView;
	private int currentItem;

	private ThreeColourCustom tcc = new ThreeColourCustom();
	private List<ThreeColourSave> tcss = null;  //
	private boolean isUpdate = false;//
	private String initName = "";//
	private boolean isChange = true;

	private TextView Tred, Tgreen, Tblue;
	private RelativeLayout color;
	private PopupWindow popupWindow = null;
	private View view = null;
	private Bitmap resBitmap = null;
	private SoundPool spool;
	private int x = 0, y = 0, radius = 0;
	private int pixel, p_off_width, p_off_height;
	private int red = 0, green = 0, blue = 0;
	private int P_width, P_height;
	private int hit;

	private TextView add_txt_speed, add_txt_hold, add_txt_diaphaneity;
	private VerticalSeekBar add_vsb_speed, add_vsb_hold, add_vsb_diaphaneity;

	private int tempRed;
	private int tempGreen;
	private int tempBlue;

	private Handler handler;

	public AddThreeCustomDialog(Context context, Device device,
			ThreeColourCustom tcc, Handler handler){
		super(context);
		this.context = context;
		this.device = device;
		if (tcc != null){
			this.tcc = tcc;
			tcss = new ArrayList<ThreeColourSave>();
			for(int i = 0; i < tcc.getList().size(); i++){
				tcss.add(tcc.getList().get(i));
			}
			isUpdate = true;
			initName = tcc.getT_name();
		}else{
			isUpdate = false;
			tcss = new ArrayList<ThreeColourSave>();
		}
		this.handler = handler;
	}


	protected void onCreate(Bundle savedInstanceState){
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		this.setContentView(R.layout.three_colour_custom_addcustom);
		System.out.println("28 AddThreeCustomDialog");
		initView();
	//	findID();
	//	head_title.setText(context.getString(R.string.add_new));
		if (isUpdate) {//
			name.setText(tcc.getT_name());
			save.setText(context.getString(R.string.update));
			r1c1.setBackgroundColor(Color.rgb(tcss.get(0).getR(), tcss.get(0)
					.getG(), tcss.get(0).getB()));
			r1c2.setBackgroundColor(Color.rgb(tcss.get(1).getR(), tcss.get(1)
					.getG(), tcss.get(1).getB()));
			r1c3.setBackgroundColor(Color.rgb(tcss.get(2).getR(), tcss.get(2)
					.getG(), tcss.get(2).getB()));
			r1c4.setBackgroundColor(Color.rgb(tcss.get(3).getR(), tcss.get(3)
					.getG(), tcss.get(3).getB()));
			r2c1.setBackgroundColor(Color.rgb(tcss.get(4).getR(), tcss.get(4)
					.getG(), tcss.get(4).getB()));
			r2c2.setBackgroundColor(Color.rgb(tcss.get(5).getR(), tcss.get(5)
					.getG(), tcss.get(5).getB()));
			r2c3.setBackgroundColor(Color.rgb(tcss.get(6).getR(), tcss.get(6)
					.getG(), tcss.get(6).getB()));
			r2c4.setBackgroundColor(Color.rgb(tcss.get(7).getR(), tcss.get(7)
					.getG(), tcss.get(7).getB()));
			r3c1.setBackgroundColor(Color.rgb(tcss.get(8).getR(), tcss.get(8)
					.getG(), tcss.get(8).getB()));
			r3c2.setBackgroundColor(Color.rgb(tcss.get(9).getR(), tcss.get(9)
					.getG(), tcss.get(9).getB()));
			r3c3.setBackgroundColor(Color.rgb(tcss.get(10).getR(), tcss.get(10)
					.getG(), tcss.get(10).getB()));
			r3c4.setBackgroundColor(Color.rgb(tcss.get(11).getR(), tcss.get(11)
					.getG(), tcss.get(11).getB()));
			r4c1.setBackgroundColor(Color.rgb(tcss.get(12).getR(), tcss.get(12)
					.getG(), tcss.get(12).getB()));
			r4c2.setBackgroundColor(Color.rgb(tcss.get(13).getR(), tcss.get(13)
					.getG(), tcss.get(13).getB()));
			r4c3.setBackgroundColor(Color.rgb(tcss.get(14).getR(), tcss.get(14)
					.getG(), tcss.get(14).getB()));
			r4c4.setBackgroundColor(Color.rgb(tcss.get(15).getR(), tcss.get(15)
					.getG(), tcss.get(15).getB()));
		}else{
			save.setText(context.getString(R.string.save));
			for (int i = 0; i < 16; i++){
				ThreeColourSave tcs = new ThreeColourSave();
				tcss.add(tcs);
			}
		}
		// ���ü���
		setViewListener();
	//	setListener();
	}

	private void setViewListener(){
		// TODO Auto-generated method stub
		save.setOnClickListener(this);
		cancel.setOnClickListener(this);
		r1c1.setOnClickListener(this);
		r1c2.setOnClickListener(this);
		r1c3.setOnClickListener(this);
		r1c4.setOnClickListener(this);
		r2c1.setOnClickListener(this);
		r2c2.setOnClickListener(this);
		r2c3.setOnClickListener(this);
		r2c4.setOnClickListener(this);
		r3c1.setOnClickListener(this);
		r3c2.setOnClickListener(this);
		r3c3.setOnClickListener(this);
		r3c4.setOnClickListener(this);
		r4c1.setOnClickListener(this);
		r4c2.setOnClickListener(this);
		r4c3.setOnClickListener(this);
		r4c4.setOnClickListener(this);

		r1c1.setOnLongClickListener(this);
		r1c2.setOnLongClickListener(this);
		r1c3.setOnLongClickListener(this);
		r1c4.setOnLongClickListener(this);
		r2c1.setOnLongClickListener(this);
		r2c2.setOnLongClickListener(this);
		r2c3.setOnLongClickListener(this);
		r2c4.setOnLongClickListener(this);
		r3c1.setOnLongClickListener(this);
		r3c2.setOnLongClickListener(this);
		r3c3.setOnLongClickListener(this);
		r3c4.setOnLongClickListener(this);
		r4c1.setOnLongClickListener(this);
		r4c2.setOnLongClickListener(this);
		r4c3.setOnLongClickListener(this);
		r4c4.setOnLongClickListener(this);

	//	add_vsb_hold.setOnSeekBarChangeListener(this);
	//	add_vsb_speed.setOnSeekBarChangeListener(this);
	//	add_vsb_diaphaneity.setOnSeekBarChangeListener(this);

	}

	private void initView(){
		// TODO Auto-generated method stub
	//	head_title = (TextView) $(R.id.head_title);
		save = (Button) $(R.id.three_addcustom_btn_save);
		cancel = (Button) $(R.id.three_addcustom_btn_cancel);
		name = (EditText) $(R.id.three_addcustom_et_name);
		// 16������ɫ
		r1c1 = (ImageView) $(R.id.three_addcustom_ll_row1_column1);
		r1c2 = (ImageView) $(R.id.three_addcustom_ll_row1_column2);
		r1c3 = (ImageView) $(R.id.three_addcustom_ll_row1_column3);
		r1c4 = (ImageView) $(R.id.three_addcustom_ll_row1_column4);
		r2c1 = (ImageView) $(R.id.three_addcustom_ll_row2_column1);
		r2c2 = (ImageView) $(R.id.three_addcustom_ll_row2_column2);
		r2c3 = (ImageView) $(R.id.three_addcustom_ll_row2_column3);
		r2c4 = (ImageView) $(R.id.three_addcustom_ll_row2_column4);
		r3c1 = (ImageView) $(R.id.three_addcustom_ll_row3_column1);
		r3c2 = (ImageView) $(R.id.three_addcustom_ll_row3_column2);
		r3c3 = (ImageView) $(R.id.three_addcustom_ll_row3_column3);
		r3c4 = (ImageView) $(R.id.three_addcustom_ll_row3_column4);
		r4c1 = (ImageView) $(R.id.three_addcustom_ll_row4_column1);
		r4c2 = (ImageView) $(R.id.three_addcustom_ll_row4_column2);
		r4c3 = (ImageView) $(R.id.three_addcustom_ll_row4_column3);
		r4c4 = (ImageView) $(R.id.three_addcustom_ll_row4_column4);
		// 16����߿�
		wr1c1 = (RelativeLayout) $(R.id.three_addcustom_wai_row1_column1);
		wr1c2 = (RelativeLayout) $(R.id.three_addcustom_wai_row1_column2);
		wr1c3 = (RelativeLayout) $(R.id.three_addcustom_wai_row1_column3);
		wr1c4 = (RelativeLayout) $(R.id.three_addcustom_wai_row1_column4);
		wr2c1 = (RelativeLayout) $(R.id.three_addcustom_wai_row2_column1);
		wr2c2 = (RelativeLayout) $(R.id.three_addcustom_wai_row2_column2);
		wr2c3 = (RelativeLayout) $(R.id.three_addcustom_wai_row2_column3);
		wr2c4 = (RelativeLayout) $(R.id.three_addcustom_wai_row2_column4);
		wr3c1 = (RelativeLayout) $(R.id.three_addcustom_wai_row3_column1);
		wr3c2 = (RelativeLayout) $(R.id.three_addcustom_wai_row3_column2);
		wr3c3 = (RelativeLayout) $(R.id.three_addcustom_wai_row3_column3);
		wr3c4 = (RelativeLayout) $(R.id.three_addcustom_wai_row3_column4);
		wr4c1 = (RelativeLayout) $(R.id.three_addcustom_wai_row4_column1);
		wr4c2 = (RelativeLayout) $(R.id.three_addcustom_wai_row4_column2);
		wr4c3 = (RelativeLayout) $(R.id.three_addcustom_wai_row4_column3);
		wr4c4 = (RelativeLayout) $(R.id.three_addcustom_wai_row4_column4);
	}


	public void onClick(View v){
		// TODO Auto-generated method stub
		if (tempView != null){
			tempView.setBackgroundResource(R.drawable.shape_corner_square);
		}
	//	if (popupWindow.isShowing()){
	//		popupWindow.dismiss();
	//	}
		switch (v.getId()){
		case R.id.three_addcustom_btn_save://
			String n = name.getText().toString().trim();
			if (n.equals("")){//
				Toast.makeText(context,getContext().getString(R.string.tip_name_empty), Toast.LENGTH_LONG).show();
				return;
			}
			//
			ThreeCustomDataBase db = new ThreeCustomDataBase(context);
			if (isUpdate){
				if(!n.equals(initName)){//
					if (!db.isUseName(n, device.get_id())){
						Toast.makeText(context,getContext().getString(R.string.tip_name_isuse),Toast.LENGTH_LONG).show();
						return;
					}
				}
				System.out.println(tcss.size());
				//
				tcc.getList().clear();
				System.out.println(tcss.size());
				for (int i = 0; i < tcss.size(); i++){
					System.out.println("========================="
							+ tcss.get(i).get_id());
					tcc.getList().add(tcss.get(i));
				}
				tcc.setT_name(n);
				tcc.setD_id(device.get_id());
				db.update(tcc);
				Toast.makeText(context,
						context.getString(R.string.tip_update_success), Toast.LENGTH_LONG)
						.show();
			}else{
				if(!db.isUseName(n, device.get_id())){
					Toast.makeText(context,getContext().getString(R.string.tip_name_isuse),Toast.LENGTH_LONG).show();
					return;
				}
				//
				tcc.setList(tcss);
				tcc.setT_name(n);
				tcc.setD_id(device.get_id());
				ThreeColourCustom t = db.save(tcc);  //
				tcc.set_id(t.get_id());
				tcc.setT_custom(t.getT_custom());
				Toast.makeText(context,
						context.getString(R.string.tip_save_success), Toast.LENGTH_LONG).show();
			}
			db.close();
			new Thread() {
				@Override
				public void run() {
					super.run();
					List<ThreeColourSave> tcss = tcc.getList();
					List<int[]> list = new ArrayList<int[]>();
					
					if("WF323".equals(device.getLamp().getL_type())){
						for(int i = 0 ; i<tcss.size(); i++){
							if( 1 == tcss.get(i).getStatus()){
								int num [] = new int [5];
								int maintainTime = (int)(Double.parseDouble(tcss.get(i).getTc_hold()) * 10);
								int gradualy = (int)(Double.parseDouble(tcss.get(i).getTc_shade()) * 10);
								int redValue = tcss.get(i).getR();
								int greenValue = tcss.get(i).getG();
								int blueValue = tcss.get(i).getB();
								if(255 == redValue)redValue = 254;
								if(255 == greenValue) greenValue = 254;
								if(255 == blueValue) blueValue = 254;
								num[0] = maintainTime;
								num[1] = gradualy;
								num[2] = redValue;
								num[3] = greenValue;
								num[4] = blueValue;
								list.add(num);
							}
						}
					}else{
						for (int i = 0; i < tcss.size(); i++){
							if (tcss.get(i).getStatus() == 1){
								int num[] = new int[5];//
								int time1=(int)(Double.parseDouble(tcss.get(i)
										.getTc_hold()) * 10);
								if(time1 == 0 || time1 == 1 || time1 == 12 || time1 == 20)
					                time1 += 2;
								num[0] = time1;
								int time2=(int)(Double.parseDouble(tcss.get(i)
										.getTc_shade()) * 10);
								if(time2 == 0 || time2 == 1 || time2 == 12 || time2 == 20)  
						                time2 += 2;
								num[1] = time2;
								int zhiR=tcss.get(i).getR()/255*100+1;
								int zhiG=tcss.get(i).getG()/255*100+1;
								int zhiB=tcss.get(i).getB()/255*100+1;
								if( zhiR == 1 || zhiR == 12 || zhiR == 20)
									zhiR += 1;
								if( zhiG == 1 || zhiG == 12 || zhiG == 20)
									zhiG += 1;
								if( zhiB == 1 || zhiB == 12 || zhiB == 20)
									zhiB += 1;
								num[2] = zhiR;
								num[3] = zhiG;
								num[4] = zhiB;
								list.add(num);
							}
						}
					}
					if("WF323".equalsIgnoreCase(device.getLamp().getL_type())){//�������ط��� WF323 �Զ���ģʽ�Ļ�������Ҫ�����Լ��Ĵ�ֵ����    ��������Ϊ��ʽ�������֡����
						StringBuffer buffer = new StringBuffer();
						buffer.append(LeyNew.SETEM+ "00" + device.getLamp().getL_sequence() + ""+Util.integer2HexString((tcc.getT_custom()-1))+"" +Util.integer2HexString(list.size()));
						
						for(int i = 0; i < list.size(); i++){
								int [] num = list.get(i);
//								buffer.append(Util.integer2HexString(0) + "");
								buffer.append(Util.integer2HexString(num[0]));
//								buffer.append(Util.integer2HexString(0)+"");
								buffer.append(Util.integer2HexString(num[1]));
								buffer.append(Util.integer2HexString(num[2]));
								buffer.append(Util.integer2HexString(num[3]));
								buffer.append(Util.integer2HexString(num[4]));
							   }
						String sendBefore = buffer.toString().trim() + LeyNew.END;
						UnionSendInfoClass.sendSpecialOrder(Util.HexString2Bytes(sendBefore));
					}else{
						Util.sendCommand(LeyNew.SAVE_8EDT,new String[] { (tcc.getT_custom()-1) + "",list.size() + "" }, device.getLamp().getL_sequence(), list);
					}
				}
			}.start();
			//
			Util.sendMessage(handler, null);
			this.dismiss();
			break;
		case R.id.three_addcustom_btn_cancel://
			this.dismiss();
			break;
		case R.id.three_addcustom_ll_row1_column1:
			wr1c1.setBackgroundResource(R.drawable.shape_corner_square_focus);
			tempView = wr1c1;
			currentView = r1c1;
			currentItem = 0;
			//initSepan(0);
			AddThreeCustomRgbDialog dialog = new AddThreeCustomRgbDialog(context, device, tcss, handler, 0, currentView, currentItem);
			dialog.show();
			break;
		case R.id.three_addcustom_ll_row1_column2:
			wr1c2.setBackgroundResource(R.drawable.shape_corner_square_focus);
			tempView = wr1c2;
			currentView = r1c2;
			currentItem = 1;
		//	initSepan(1);
			AddThreeCustomRgbDialog dialog1 = new AddThreeCustomRgbDialog(context, device, tcss, handler, 1, currentView, currentItem);
			dialog1.show();
			break;
		case R.id.three_addcustom_ll_row1_column3:
			wr1c3.setBackgroundResource(R.drawable.shape_corner_square_focus);
			tempView = wr1c3;
			currentView = r1c3;
			currentItem = 2;
		//	initSepan(2);
			AddThreeCustomRgbDialog dialog2 = new AddThreeCustomRgbDialog(context, device, tcss, handler, 2, currentView, currentItem);
			dialog2.show();
			break;
		case R.id.three_addcustom_ll_row1_column4:
			wr1c4.setBackgroundResource(R.drawable.shape_corner_square_focus);
			tempView = wr1c4;
			currentView = r1c4;
			currentItem = 3;
		//	initSepan(3);
			AddThreeCustomRgbDialog dialog3 = new AddThreeCustomRgbDialog(context, device, tcss, handler, 3, currentView, currentItem);
			dialog3.show();
			break;
		case R.id.three_addcustom_ll_row2_column1:
			wr2c1.setBackgroundResource(R.drawable.shape_corner_square_focus);
			tempView = wr2c1;
			currentView = r2c1;
			currentItem = 4;
		//	initSepan(4);
			AddThreeCustomRgbDialog dialog4 = new AddThreeCustomRgbDialog(context, device, tcss, handler, 4, currentView, currentItem);
			dialog4.show();
			break;
		case R.id.three_addcustom_ll_row2_column2:
			wr2c2.setBackgroundResource(R.drawable.shape_corner_square_focus);
			tempView = wr2c2;
			currentView = r2c2;
			currentItem = 5;
		//	initSepan(5);
			AddThreeCustomRgbDialog dialog5 = new AddThreeCustomRgbDialog(context, device, tcss, handler, 5, currentView, currentItem);
			dialog5.show();
			break;
		case R.id.three_addcustom_ll_row2_column3:
			wr2c3.setBackgroundResource(R.drawable.shape_corner_square_focus);
			tempView = wr2c3;
			currentView = r2c3;
			currentItem = 6;
		//	initSepan(6);
			AddThreeCustomRgbDialog dialog6 = new AddThreeCustomRgbDialog(context, device, tcss, handler, 6, currentView, currentItem);
			dialog6.show();
			break;
		case R.id.three_addcustom_ll_row2_column4:
			wr2c4.setBackgroundResource(R.drawable.shape_corner_square_focus);
			tempView = wr2c4;
			currentView = r2c4;
			currentItem = 7;
		//	initSepan(7);
			AddThreeCustomRgbDialog dialog7 = new AddThreeCustomRgbDialog(context, device, tcss, handler, 7, currentView, currentItem);
			dialog7.show();
			break;
		case R.id.three_addcustom_ll_row3_column1:
			wr3c1.setBackgroundResource(R.drawable.shape_corner_square_focus);
			tempView = wr3c1;
			currentView = r3c1;
			currentItem = 8;
		//	initSepan(8);
			AddThreeCustomRgbDialog dialog8 = new AddThreeCustomRgbDialog(context, device, tcss, handler, 8, currentView, currentItem);
			dialog8.show();
			break;
		case R.id.three_addcustom_ll_row3_column2:
			wr3c2.setBackgroundResource(R.drawable.shape_corner_square_focus);
			tempView = wr3c2;
			currentView = r3c2;
			currentItem = 9;
		//	initSepan(9);
			AddThreeCustomRgbDialog dialog9 = new AddThreeCustomRgbDialog(context, device, tcss, handler, 9, currentView, currentItem);
			dialog9.show();
			break;
		case R.id.three_addcustom_ll_row3_column3:
			wr3c3.setBackgroundResource(R.drawable.shape_corner_square_focus);
			tempView = wr3c3;
			currentView = r3c3;
			currentItem = 10;
		//	initSepan(10);
			AddThreeCustomRgbDialog dialog10 = new AddThreeCustomRgbDialog(context, device, tcss, handler, 10, currentView, currentItem);
			dialog10.show();
			break;
		case R.id.three_addcustom_ll_row3_column4:
			wr3c4.setBackgroundResource(R.drawable.shape_corner_square_focus);
			tempView = wr3c4;
			currentView = r3c4;
			currentItem = 11;
		//	initSepan(11);
			AddThreeCustomRgbDialog dialog11 = new AddThreeCustomRgbDialog(context, device, tcss, handler, 11, currentView, currentItem);
			dialog11.show();
			break;
		case R.id.three_addcustom_ll_row4_column1:
			wr4c1.setBackgroundResource(R.drawable.shape_corner_square_focus);
			tempView = wr4c1;
			currentView = r4c1;
			currentItem = 12;
		//	initSepan(12);
			AddThreeCustomRgbDialog dialog12 = new AddThreeCustomRgbDialog(context, device, tcss, handler, 12, currentView, currentItem);
			dialog12.show();
			break;
		case R.id.three_addcustom_ll_row4_column2:
			wr4c2.setBackgroundResource(R.drawable.shape_corner_square_focus);
			tempView = wr4c2;
			currentView = r4c2;
			currentItem = 13;
		//	initSepan(13);
			AddThreeCustomRgbDialog dialog13 = new AddThreeCustomRgbDialog(context, device, tcss, handler, 13, currentView, currentItem);
			dialog13.show();
			break;
		case R.id.three_addcustom_ll_row4_column3:
			wr4c3.setBackgroundResource(R.drawable.shape_corner_square_focus);
			tempView = wr4c3;
			currentView = r4c3;
			currentItem = 14;
	//		initSepan(14);
			AddThreeCustomRgbDialog dialog14 = new AddThreeCustomRgbDialog(context, device, tcss, handler, 14, currentView, currentItem);
			dialog14.show();
			break;
		case R.id.three_addcustom_ll_row4_column4:
			wr4c4.setBackgroundResource(R.drawable.shape_corner_square_focus);
			tempView = wr4c4;
			currentView = r4c4;
			currentItem = 15;
	//		initSepan(15);
			AddThreeCustomRgbDialog dialog15 = new AddThreeCustomRgbDialog(context, device, tcss, handler, 15, currentView, currentItem);
			dialog15.show();
			break;
		default:
			break;
		}
	}

	private void initSepan(int i) {
		ThreeColourSave t = tcss.get(i);
		tempRed = t.getR();
		tempGreen = t.getG();
		tempBlue = t.getB();

		int hold = (int) (Float.parseFloat(t.getTc_hold()) * 10);
		int speed = (int) (Float.parseFloat(t.getTc_shade()) * 10);
		if (hold == 0 && speed == 0) {
			hold = add_vsb_hold.getProgress();
			speed = add_vsb_speed.getProgress();
			tcss.get(currentItem).setTc_hold(hold * 1.0 / 10 + "");
			tcss.get(currentItem).setTc_shade(speed * 1.0 / 10 + "");
			tcss.get(currentItem).setStatus(0);
		}

		add_vsb_hold.setProgress(hold);
		add_vsb_speed.setProgress(speed);
		isChange = false;
		add_vsb_diaphaneity.setProgress(t.getDiaphaneity());
		red = (int) (tempRed * (add_vsb_diaphaneity.getProgress() / 100.0));
		green = (int) (tempGreen * (add_vsb_diaphaneity.getProgress() / 100.0));
		blue = (int) (tempBlue * (add_vsb_diaphaneity.getProgress() / 100.0));
		Tred.setText(red + "");
		Tgreen.setText(green + "");
		Tblue.setText(blue + "");

	}

	@Override
	public boolean onLongClick(View v){
		switch (v.getId()) {
		case R.id.three_addcustom_ll_row1_column1:
			r1c1.setBackgroundColor(0);
			tcss.get(0).setStatus(0);

			break;
		case R.id.three_addcustom_ll_row1_column2:
			r1c2.setBackgroundColor(0);
			tcss.get(1).setStatus(0);

			break;
		case R.id.three_addcustom_ll_row1_column3:
			r1c3.setBackgroundColor(0);
			tcss.get(2).setStatus(0);

			break;
		case R.id.three_addcustom_ll_row1_column4:
			r1c4.setBackgroundColor(0);
			tcss.get(3).setStatus(0);

			break;
		case R.id.three_addcustom_ll_row2_column1:
			r2c1.setBackgroundColor(0);
			tcss.get(4).setStatus(0);

			break;
		case R.id.three_addcustom_ll_row2_column2:
			r2c2.setBackgroundColor(0);
			tcss.get(5).setStatus(0);

			break;
		case R.id.three_addcustom_ll_row2_column3:
			r2c3.setBackgroundColor(0);
			tcss.get(6).setStatus(0);

			break;
		case R.id.three_addcustom_ll_row2_column4:
			r2c4.setBackgroundColor(0);
			tcss.get(7).setStatus(0);

			break;
		case R.id.three_addcustom_ll_row3_column1:
			r3c1.setBackgroundColor(0);
			tcss.get(8).setStatus(0);

			break;
		case R.id.three_addcustom_ll_row3_column2:
			r3c2.setBackgroundColor(0);
			tcss.get(9).setStatus(0);

			break;
		case R.id.three_addcustom_ll_row3_column3:
			r3c3.setBackgroundColor(0);
			tcss.get(10).setStatus(0);

			break;
		case R.id.three_addcustom_ll_row3_column4:
			r3c4.setBackgroundColor(0);
			tcss.get(11).setStatus(0);

			break;
		case R.id.three_addcustom_ll_row4_column1:
			r4c1.setBackgroundColor(0);
			tcss.get(12).setStatus(0);

			break;
		case R.id.three_addcustom_ll_row4_column2:
			r4c2.setBackgroundColor(0);
			tcss.get(13).setStatus(0);

			break;
		case R.id.three_addcustom_ll_row4_column3:
			r4c3.setBackgroundColor(0);
			tcss.get(14).setStatus(0);
			break;
		case R.id.three_addcustom_ll_row4_column4:
			r4c4.setBackgroundColor(0);
			tcss.get(15).setStatus(0);
			break;

		default:
			break;
		}
		return true;
	}

	private void setPopupWindows() {
		resBitmap = ((BitmapDrawable) color.getBackground()).getBitmap();
		view = LayoutInflater.from(getContext()).inflate(
				R.layout.three_colour_popupwindows, null);
		popupWindow = new PopupWindow(view, 40, 40);
		popupWindow.setAnimationStyle(android.R.style.Animation_Toast);
		popupWindow.setTouchable(false);
	}

	private Object $(int id) {
		return findViewById(id);
	}



	Runnable showCurPointColor = new Runnable() {
		@Override
		public void run() {

			popupWindow.showAtLocation(color, Gravity.NO_GRAVITY, x
					+ p_off_width, y + p_off_height - 10);
		}
	};

	protected void onStop() {
		super.onStop();
	}

	public void onProgressChanged(VerticalSeekBar verticalSeekBar,
			int progress, boolean fromUser) {
		// TODO Auto-generated method stub
		switch (verticalSeekBar.getId()) {
		case R.id.add_vsb_hold:
			float num = (float) ((progress * (1.0)) / 10);
			add_txt_hold.setText(num + "s");
			if (currentView != null) {
				tcss.get(currentItem).setTc_hold(num + "");
				tcss.get(currentItem).setStatus(1);
			}
			break;
		case R.id.add_vsb_speed:
			float num2 = (float) ((progress * (1.0)) / 10);
			add_txt_speed.setText(num2 + "s");
			if (currentView != null) {
				tcss.get(currentItem).setTc_shade(num2 + "");
				tcss.get(currentItem).setStatus(1);
			}
			break;
		case R.id.add_vsb_diaphaneity:
			add_txt_diaphaneity.setText(progress + "%");
			red = (int) (tempRed * (progress / 100.0));
			green = (int) (tempGreen * (progress / 100.0));
			blue = (int) (tempBlue * (progress / 100.0));
			Tred.setText(red + "");
			Tgreen.setText(green + "");
			Tblue.setText(blue + "");
			if (currentView != null && isChange) {
				tcss.get(currentItem).setDiaphaneity(progress);
				tcss.get(currentItem).setStatus(1);
				currentView.setBackgroundColor(Color.rgb(red, green, blue));
				Util.sendCommand("setrgb", new String[] { red + "", green + "",
						blue + "" }, device.getLamp().getL_sequence());
			}
			isChange = true;
			break;
		default:
			break;
		}
	}

	public void onStartTrackingTouch(VerticalSeekBar VerticalSeekBar){
	}

	public void onStopTrackingTouch(VerticalSeekBar VerticalSeekBar) {
	}

}
