package phonehome.leynew.com.phenehome.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import java.util.ArrayList;
import java.util.List;

import phonehome.leynew.com.phenehome.R;
import phonehome.leynew.com.phenehome.damain.Images;


public class AreaAddGVAdapter extends BaseAdapter {

	private Context context;
	// private Map<Integer, String> images = new HashMap<Integer, String>();
	private List<Images> images = new ArrayList<Images>();

	public AreaAddGVAdapter(Context context, List<Images> images) {
		super();
		this.context = context;
		this.images = images;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return images.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return images.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub

		convertView = LayoutInflater.from(context).inflate(
				R.layout.fragment_area_add_view_grid_item, null);
//		RelativeLayout itemly = (RelativeLayout) convertView
//				.findViewById(R.id.gv_item_rl);
		ImageView img = (ImageView) convertView.findViewById(R.id.gv_item_img);

		// TODO: 2016/10/31/031 宽高???????
		//宽高设置
//		int width = (int) (Util.getDisplayMetricsWidth(context));
//		int height = (int) (Util.getDisplayMetricsHeight(context));
//		int aa = (int) (height / 10);
//		int bb = (int) (height / 9);
//		RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
//				bb, bb);
//		itemly.setLayoutParams(params);
//		RelativeLayout.LayoutParams paramsimg = new RelativeLayout.LayoutParams(
//				aa, aa);
//		img.setLayoutParams(paramsimg);


		// if (position<FinalClass.IMAGES_SIZE) {
		img.setBackgroundResource(Integer.parseInt(images.get(position)
				.getPath()));
		// }else {
		// }
		return convertView;
	}

}
