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
public class MyRadioButton1 extends RadioButton{
    private int mDrawableSize;// xml文件中设置的大小

    Bitmap bitmap = null;
    private float left;
    private float top;


    public MyRadioButton1(Context context) {
        super(context);
    }

    public MyRadioButton1(Context context, AttributeSet attrs) {
        super(context, attrs);

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




}
