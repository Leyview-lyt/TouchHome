package phonehome.leynew.com.phenehome.view;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.RadioButton;

import phonehome.leynew.com.phenehome.R;

/**
 * Created by Administrator on 2016/11/17/017.
 */
public class MyRadioButton extends RadioButton{
    private int mDrawableSize;// xml文件中设置的大小

    Bitmap bitmap = null;
    private float left;
    private float top;


    public MyRadioButton(Context context) {
        super(context);
    }

    public MyRadioButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        bitmap = decodeBitmap(getResources(), R.drawable.check_pic,30,30);
        Drawable drawableLeft = null, drawableTop = null, drawableRight = null, drawableBottom = null;
        TypedArray a = context.obtainStyledAttributes(attrs,
                R.styleable.MyRadioButton);

        int n = a.getIndexCount();
        for (int i = 0; i < n; i++) {
            int attr = a.getIndex(i);
            Log.i("MyRadioButton", "attr:" + attr);
            switch (attr) {
                case R.styleable.MyRadioButton_drawableSize://获取属性设置的大小,默认80
                    mDrawableSize = a.getDimensionPixelSize(R.styleable.MyRadioButton_drawableSize, 80);
                    Log.i("MyRadioButton", "mDrawableSize:" + mDrawableSize);
                    break;
                case R.styleable.MyRadioButton_drawableTop:
                    drawableTop = a.getDrawable(attr);//获取属性设置的图片
                    break;
                case R.styleable.MyRadioButton_drawableBottom:
                    drawableBottom = a.getDrawable(attr);
                    break;
                case R.styleable.MyRadioButton_drawableRight:
                    drawableRight = a.getDrawable(attr);
                    break;
                case R.styleable.MyRadioButton_drawableLeft:
                    drawableLeft = a.getDrawable(attr);
                    break;
                default:
                    break;
            }
        }
        a.recycle();
        setCompoundDrawablesWithIntrinsicBounds(drawableLeft, drawableTop, drawableRight, drawableBottom);

    }

    /**
     * 根据属性设置图片大小
     * @param left
     * @param top
     * @param right
     * @param bottom
     */
    public void setCompoundDrawablesWithIntrinsicBounds(Drawable left, Drawable top, Drawable right, Drawable bottom) {
        if (left != null) {
            left.setBounds(0, 0, mDrawableSize, mDrawableSize+10);
        }
        if (right != null) {
            right.setBounds(0, 0, mDrawableSize, mDrawableSize+10);
        }
        if (top != null) {
            top.setBounds(0, 0, mDrawableSize, mDrawableSize);
        }
        if (bottom != null) {
            bottom.setBounds(0, 0, mDrawableSize, mDrawableSize);
        }
        setCompoundDrawables(left, top, right, bottom);//真正起作用
    }


    @Override
    public boolean isChecked() {
        if (a != super.isChecked()) {
            invalidate();
        }
        a = super.isChecked();
        return a;
    }


    boolean a;
    @Override
    protected void onDraw(Canvas canvas) {
        Paint paint = new Paint();
        super.onDraw(canvas);
        top = getHeight()-30 ;
        left = getWidth()/2+mDrawableSize/2-30 ;
        if (a) {
            Log.i("========","onDraw"+a);
            canvas.drawBitmap(bitmap,left,top,paint);
        }

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
