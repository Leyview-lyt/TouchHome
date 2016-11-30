package phonehome.leynew.com.phenehome.util;

import android.content.Context;

import phonehome.leynew.com.phenehome.R;


public class FinalClass {
	
	public Context context;

	public static final int IMAGES_SIZE = 58;
	public static final int ROOM_IMAGES_SIZE = 11;
	public static final int SCENE_IMAGES_SIZE = 13;
	public static final int DEVICE_IMAGES_SIZE = 34;
	public static final int CUSTOM_SIZE = 10;
	public static final int MODELS_VARIABLE = 56;
	public static int tempPosition = 0;
	
	public String models[];
	public int model_values[] ;
	
	public FinalClass(Context context){
		this.context = context;
		models = new String[] { 
						context.getString(R.string.model_m1),
						context.getString(R.string.model_m2),
						context.getString(R.string.model_m3),
						context.getString(R.string.model_m4),
						context.getString(R.string.model_m5),
						context.getString(R.string.model_m6),
						context.getString(R.string.model_m7),
						context.getString(R.string.model_m8),
						context.getString(R.string.model_m9),
						context.getString(R.string.model_m10),
						context.getString(R.string.model_m11),
						context.getString(R.string.model_m12),
						context.getString(R.string.model_m13),
						context.getString(R.string.model_m14),
						context.getString(R.string.model_m15),
						context.getString(R.string.model_m16),
						context.getString(R.string.model_m17),
						context.getString(R.string.model_m18),
						context.getString(R.string.model_m19),
						context.getString(R.string.model_m20),
						context.getString(R.string.model_m21),
						};
		
		model_values = new int[models.length];
		for (int i = 0; i < models.length; i++) {
			model_values[i] = i + MODELS_VARIABLE;
		}
	}
	
}
