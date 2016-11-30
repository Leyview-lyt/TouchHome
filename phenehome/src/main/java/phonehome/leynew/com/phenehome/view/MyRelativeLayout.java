package phonehome.leynew.com.phenehome.view;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.drawable.BitmapDrawable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.RelativeLayout;

import phonehome.leynew.com.phenehome.R;

/**
 * Created by Administrator on 2016/11/18/018.
 */
public class MyRelativeLayout extends RelativeLayout {

    private Bitmap bitmap = decodeBitmap(getResources(), R.drawable.colorpicker,20,20);
    private Bitmap bitmap1 ;
    private float x;
    private float y;
    private float radiusX;
    private float radiusY;
    private int pixel;
    private int red = 255, green = 255, blue = 255;
    private boolean isShow;
    private Context context;

    public MyRelativeLayout(Context context) {
        super(context);
    }

    public MyRelativeLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MyRelativeLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }



    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Paint paint = new Paint();
        if (isShow) {
            canvas.drawBitmap(bitmap,x,y,paint);
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        super.onTouchEvent(event);
        int action = event.getAction();
        radiusX = getWidth()/2;
        radiusY = getHeight()/2;
        BitmapDrawable bd = (BitmapDrawable) getBackground();
        Bitmap innerBitmap = bd.getBitmap();
        int bmpWidth = innerBitmap.getWidth();
        int bmpHeight = innerBitmap.getHeight();
        float scale1 = (float) getWidth() / (float) bmpWidth;
        float scale2 = (float) getHeight() / (float) bmpHeight;
        Matrix matrix = new Matrix();
        matrix.postScale(scale1, scale2);
        bitmap1 = Bitmap.createBitmap(innerBitmap, 0, 0, bmpWidth, bmpHeight, matrix, true);
        if (action == MotionEvent.ACTION_DOWN || action == MotionEvent.ACTION_MOVE){
            if (((event.getX() - radiusX) * (event.getX() - radiusX) + (event.getY() - radiusY) * (event.getY() - radiusY) < radiusX * radiusX) ) {
                x =  event.getX();
                y =  event.getY();
                if ((int) x<bmpWidth && (int) y<bmpHeight) {
                    isShow = true;
                    pixel = bitmap1.getPixel((int) x,(int) y);
                    red = Color.red(pixel);
                    green = Color.green(pixel);
                    blue = Color.blue(pixel);
                    if (red == 0 && green == 0 && blue == 0) {
                        red=green=blue=255;
                    }
                    Log.i("===="," "+red+" "+green+"  "+blue);
                }
            }
            invalidate();
        }
        return  true;
    }

    public int[] getColor(){
        int color[] = new int[3];
        color[0] = red;
        color[1] = green;
        color[2] = blue;
        return  color;
    }

    /**
     * @param displayWidth 需要显示的宽度
     * @param displayHeight 需要显示的高度
     * @return Bitmap
     */
    public Bitmap decodeBitmap(Resources res, int id, int displayWidth, int displayHeight) {
        BitmapFactory.Options op = new BitmapFactory.Options();
        op.inJustDecodeBounds = true;
        // op.inJustDecodeBounds = true;表示我们只读取Bitmap的宽高等信息，不读取像素。
//        Bitmap bmp = BitmapFactory.decodeFile(path, op); // 获取尺寸信息
        Bitmap bmp = BitmapFactory.decodeResource(res,id,op);// 获取尺寸信息

        // op.outWidth表示的是图像真实的宽度
        // op.inSamplySize 表示的是缩小的比例
        // op.inSamplySize = 4,表示缩小1/4的宽和高，1/16的像素，android认为设置为2是最快的。
        // 获取比例大小
        int wRatio = (int) Math.ceil(op.outWidth / (float) displayWidth);
        int hRatio = (int) Math.ceil(op.outHeight / (float) displayHeight);
        // 如果超出指定大小，则缩小相应的比例
        if (wRatio > 1 && hRatio > 1) {
            if (wRatio > hRatio) {
                // 如果太宽，我们就缩小宽度到需要的大小，注意，高度就会变得更加的小。
                op.inSampleSize = wRatio;
            } else {
                op.inSampleSize = hRatio;
            }
        }
        op.inJustDecodeBounds = false;
        bmp = BitmapFactory.decodeResource(res,id,op);
        // 从原Bitmap创建一个给定宽高的Bitmap
        return Bitmap.createScaledBitmap(bmp, displayWidth, displayHeight, true);
    }

}
