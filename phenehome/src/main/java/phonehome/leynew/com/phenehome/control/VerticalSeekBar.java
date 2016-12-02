package phonehome.leynew.com.phenehome.control;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.AbsSeekBar;
import android.widget.SeekBar;

public class VerticalSeekBar extends SeekBar {

    private Drawable mThumb;
    private OnSeekBarChangeListener mOnSeekBarChangeListener;

    public interface OnSeekBarChangeListener {
        void onProgressChanged(VerticalSeekBar VerticalSeekBar, int progress, boolean fromUser);

        void onStartTrackingTouch(VerticalSeekBar VerticalSeekBar);

        void onStopTrackingTouch(VerticalSeekBar VerticalSeekBar);
    }

    public VerticalSeekBar(Context context) {
        super(context, null);
    }


    public VerticalSeekBar(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public VerticalSeekBar(Context context, AttributeSet attrs) {
        super(context, attrs);

        //设置系统进度条的监听
        super.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

            @Override
            public void onStopTrackingTouch(SeekBar arg0) {
                if (mOnSeekBarChangeListener != null) {
                    mOnSeekBarChangeListener.onStopTrackingTouch((VerticalSeekBar) arg0);
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar arg0) {
                if (mOnSeekBarChangeListener != null) {
                    mOnSeekBarChangeListener.onStartTrackingTouch((VerticalSeekBar) arg0);
                }
            }

            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (mOnSeekBarChangeListener != null)
                    //接口回调
                    mOnSeekBarChangeListener.onProgressChanged((VerticalSeekBar) seekBar, progress,fromUser);
            }
        });

        setMax(100);
    }


	    public void setOnSeekBarChangeListener(OnSeekBarChangeListener l) {
	        mOnSeekBarChangeListener = l;
	    }




//
//	    private void setThumbPos(int w, Drawable thumb, float scale, int gap){
//	        int available = w - getPaddingLeft() - getPaddingRight();
//	        int thumbWidth = thumb.getIntrinsicWidth();
//	        int thumbHeight = thumb.getIntrinsicHeight();
//	        available -= thumbWidth;
//	        available += getThumbOffset() * 2;
//	        int thumbPos = (int) (scale * available);
//	        int topBound, bottomBound;
//	        if (gap == Integer.MIN_VALUE) {
//	            Rect oldBounds = thumb.getBounds();
//	            topBound = oldBounds.top;
//	            bottomBound = oldBounds.bottom;
//	        } else {
//	            topBound = gap;
//	            bottomBound = gap + thumbHeight;
//	        }
//	        thumb.setBounds(thumbPos, topBound, thumbPos + thumbWidth, bottomBound);
//	    }
//
//	    @Override
//	    protected void onDraw(Canvas c){
//	        c.rotate(-90);//
//	        c.translate(-getHeight(), 0);//
//	        super.onDraw(c);
//	    }
//
//	    @Override
//	    protected synchronized void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
//	        super.onMeasure(heightMeasureSpec, widthMeasureSpec);
//	        setMeasuredDimension(getMeasuredHeight(), getMeasuredWidth());//
//	    }
//
//	    @Override
//	    public void setThumb(Drawable thumb) {
//	        mThumb = thumb;
//	        super.setThumb(thumb);
//	    }
//
//	    @Override
//	    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
//	        super.onSizeChanged(h, w, oldw, oldh);//
//	    }

//	    @Override
//	    public boolean onTouchEvent(MotionEvent event){
//	        if (!isEnabled()) {  return false;  }
//	        switch (event.getAction()) {
//	            case MotionEvent.ACTION_DOWN:
//	                setPressed(true);
//	                trackTouchEvent(event);
//	                break;
//	            case MotionEvent.ACTION_MOVE:
//	                trackTouchEvent(event);
//	                attemptClaimDrag();
//	                break;
//
//	            case MotionEvent.ACTION_UP:
//	                trackTouchEvent(event);
//	                onStopTrackingTouch();
//	                invalidate();
//	                break;
//
//	            case MotionEvent.ACTION_CANCEL:
//	                onStopTrackingTouch();
//
//	                invalidate(); // see above explanation
//	                break;
//	        }
//	        return true;
//	    }
//
//	    private void trackTouchEvent(MotionEvent event){
//	        final int height = getHeight();
//	        final int available = height - getPaddingBottom() - getPaddingTop();
//	        int Y = (int) event.getY();
//	        float scale;
//	        float progress = 0;
//	        if (Y > height - getPaddingBottom()){
//	            scale = 0.0f;
//	        } else if (Y < getPaddingTop()){
//	            scale = 1.0f;
//	        } else {
//	            scale = (float) (height - getPaddingBottom() - Y) / (float) available;
//	        }
//	        final int max = getMax();
//	        progress = scale * max;
//	        setProgress((int) progress);
//	    }
//
//	    private void attemptClaimDrag(){
//	        if (getParent() != null) {
//	            getParent().requestDisallowInterceptTouchEvent(true);
//	        }
//	    }


//	//进度条改变的接口
//	public interface OnSeekBarPageChangeListener {
//		public abstract void setSeekBarPageChanged(int page);
//	}
//
//	//注册接口
//	public void setSeekBarPageChangeListener(OnSeekBarPageChangeListener listener) {
//		mBarPageChangeListener = listener;
//	}

	//Thumb的变化情况
	protected void onSizeChanged(int w, int h, int oldw, int oldh) {
		super.onSizeChanged(h, w, oldh, oldw);
	}

	//测量宽度也需要旋转
	@Override
	protected synchronized void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		super.onMeasure(heightMeasureSpec, widthMeasureSpec);
		setMeasuredDimension(getMeasuredHeight(), getMeasuredWidth());
	}



	//画布旋转90
	protected void onDraw(Canvas c) {
		c.rotate(-90);
		c.translate(-getHeight(),0);

		super.onDraw(c);
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		if (!isEnabled()) {
			return false;
		}

		switch (event.getAction()) {
			case MotionEvent.ACTION_DOWN:
			case MotionEvent.ACTION_MOVE:
			case MotionEvent.ACTION_UP:
				int i=0;
				//设置y值为进度条的值
				i=getMax() - (int) (getMax() * event.getY() / getHeight());
				setProgress(i);
				onSizeChanged(getWidth(), getHeight(), 0, 0);
				break;

			case MotionEvent.ACTION_CANCEL:
				break;
		}
		return true;
	}

	@Override
	public synchronized void setProgress(int progress) {
		super.setProgress(progress);
		//设置进度条按钮跟着移动
		onSizeChanged(getWidth(), getHeight(), 0, 0);
	}


}