package com.cls;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.os.Looper;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;

public class ChangeIconColorAndText extends View{


	private int mColor = 0xFF45C01A;
	private Bitmap mIconBitmap;
	private String mText;
	private int mSize = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 12, getResources().getDisplayMetrics());
	
	
	private Canvas mCanvas;
	private Bitmap mBitmap;
	private Paint mPaint;
	
	private float mAlpha = 0f;
	
	private Rect mIconRect;
	private Rect mTextBound;
	private Paint mTextPaint;
	
	
	public ChangeIconColorAndText(Context context, AttributeSet attrs)
	{
		this(context, attrs, 0);
	}
	
	public ChangeIconColorAndText(Context context)
	{
		this(context,null);
	}
	
	public ChangeIconColorAndText(Context context, AttributeSet attrs,
			int defStyle) {
		super(context, attrs, defStyle);
		
		TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.ChangeIconColorAndText);
		 int n = array.getIndexCount();
		 
		for(int i = 0; i < n; i ++)
		{
			switch (i) {
			case R.styleable.ChangeIconColorAndText_icon1:
				BitmapDrawable bitmapDrawable = (BitmapDrawable) array.getDrawable(i);
				mIconBitmap = bitmapDrawable.getBitmap();
				break;
			case R.styleable.ChangeIconColorAndText_color2:
				mColor = array.getColor(i, 0xFF45C01A);
				break;
			case R.styleable.ChangeIconColorAndText_text:
				mText = (String) array.getText(i);
				break;
			case R.styleable.ChangeIconColorAndText_text_size:
				mSize = (int) array.getDimension(i, TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 12, getResources().getDisplayMetrics()));
			default:
				break;
			}
		}
		array.recycle();
		
		mTextBound = new Rect();
		mTextPaint = new Paint();
		mTextBound = new Rect();
		mTextPaint = new Paint();
		mTextPaint.setTextSize(mSize);
		mTextPaint.setColor(0Xff555555);
		mTextPaint.getTextBounds(mText, 0, mText.length(), mTextBound);
		
	}
	
	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		// TODO Auto-generated method stub
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
		int iconWidth = Math.min(getMeasuredWidth() - getPaddingLeft() - getPaddingRight(), getMeasuredHeight() - getPaddingTop()
				- getPaddingBottom() - mTextBound.height()); 
		
		int left = getMeasuredWidth() / 2 - iconWidth / 2;
		int top = getMeasuredHeight() / 2 - (mTextBound.height() + iconWidth)
				/ 2;
		mIconRect = new Rect(left, top, left + iconWidth, top + iconWidth);
	}
	
	@Override
	protected void onDraw(Canvas canvas) {
		int alpha = (int) Math.ceil(255 * mAlpha);
		setupTargetBitmap(alpha);
		drawSourceText(canvas, alpha);
		drawTargetText(canvas, alpha);
		canvas.drawBitmap(mBitmap,0,0,null);
	}

	/**
	 * 绘制原文本
	 * 
	 * @param canvas
	 * @param alpha
	 */
	private void drawSourceText(Canvas canvas, int alpha)
	{
		mTextPaint.setColor(0xff333333);
		mTextPaint.setAlpha(255 - alpha);
		int x = getMeasuredWidth() / 2 - mTextBound.width() / 2;
		int y = mIconRect.bottom + mTextBound.height();
		canvas.drawText(mText, x, y, mTextPaint);

	}
	
	/**
	 * 绘制变色的文本
	 * 
	 * @param canvas
	 * @param alpha
	 */
	private void drawTargetText(Canvas canvas, int alpha)
	{
		mTextPaint.setColor(mColor);
		mTextPaint.setAlpha(alpha);
		int x = getMeasuredWidth() / 2 - mTextBound.width() / 2;
		int y = mIconRect.bottom + mTextBound.height();
		canvas.drawText(mText, x, y, mTextPaint);

	}

	private void setupTargetBitmap(int alpha) {
		mBitmap = Bitmap.createBitmap(getMeasuredWidth(), getMeasuredHeight(),
				Config.ARGB_8888);
		mCanvas = new Canvas(mBitmap);
		mPaint = new Paint();
		mPaint.setColor(mColor);
		mPaint.setAntiAlias(true);
		mPaint.setDither(true);
		mPaint.setAlpha(alpha);
		mCanvas.drawRect(mIconRect, mPaint);
		mPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_IN));
		mCanvas.drawBitmap(mIconBitmap, null, mIconRect, mPaint);
	}

	

	public void setIconAlpha(float alpha)
	{
		this.mAlpha = alpha;
		invalidateView();
	}

	/**
	 * 重绘
	 */
	private void invalidateView()
	{
		if (Looper.getMainLooper() == Looper.myLooper())
		{
			invalidate();
		} else
		{
			postInvalidate();
		}
	}


}
