package com.usingtone.arcview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;
import android.graphics.Shader;
import android.graphics.drawable.Drawable;
import android.support.annotation.ColorInt;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.security.cert.PolicyNode;

/**
 * Created by luze on 2018-01-15.
 */

public class ArcView extends View implements Target {

    Paint mPaint;
    int mWidth;
    int mHeight;
    float mRadius;
    Point mCicleCenter;
    RectF mRect = new RectF();
    int mStartColor;
    int mEndColor;
    boolean isImage = true;
    LinearGradient mLinearGradient;
    Bitmap mBitmap;


    public ArcView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        readAttr(attrs);
        init();
    }

    public ArcView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        readAttr(attrs);
        init();
    }
    void readAttr(AttributeSet set){
        TypedArray array = getContext().obtainStyledAttributes(set,R.styleable.arcView);
        mStartColor = array.getColor(R.styleable.arcView_startColor, Color.parseColor("#546451"));
        mEndColor = array.getColor(R.styleable.arcView_endColor,Color.parseColor("#504406"));
        isImage = array.getBoolean(R.styleable.arcView_isImage,true);
    }

    void init(){
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setColor(Color.WHITE);
        mPaint.setStyle(Paint.Style.FILL);

    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mHeight = getHeight();
        int width = getWidth();
        mWidth = getWidth();
        mRadius = (float) (width*1.5);
        mRect.top = 0;
        mRect.left = 0;
        mRect.bottom = mHeight;
        mRect.right = mWidth;
        mCicleCenter = new Point();
        mCicleCenter.x = width/2;
        mCicleCenter.y = (int) (mHeight-width*1.5);
        mLinearGradient = new LinearGradient(width/2,0,width/2,mHeight,mStartColor,mEndColor, Shader.TileMode.MIRROR);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int canvasWidth = canvas.getWidth();
        int canvasHeight = canvas.getHeight();
        int layerId = canvas.saveLayer(0,0,canvasWidth,canvasHeight,null,Canvas.ALL_SAVE_FLAG);
        canvas.drawCircle(mCicleCenter.x,mCicleCenter.y,mRadius,mPaint);
        mPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        if (isImage){
            if(mBitmap != null){
                canvas.drawBitmap(mBitmap,null,mRect,mPaint);
            }
        }else {
            mPaint.setShader(mLinearGradient);
            canvas.drawRect(mRect,mPaint);
        }
        mPaint.setXfermode(null);
        canvas.restoreToCount(layerId);
    }

    public void setColor(@ColorInt int mStartColor,@ColorInt int mEndColor){
        this.mStartColor = mStartColor;
        this.mEndColor = mEndColor;
        isImage = false;
        mLinearGradient = new LinearGradient(mWidth/2,0,mWidth/2,mHeight,mStartColor,mEndColor, Shader.TileMode.MIRROR);
        invalidate();
    }
    public void setImage(String url){
        Picasso.with(getContext()).load(url).into(this);
    }
    @Override
    public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
        Log.d("tag","onBitmapLoaded...");
        mBitmap = bitmap;
        invalidate();
    }

    @Override
    public void onBitmapFailed(Drawable errorDrawable) {
        Log.d("tag","onBitmapFailed...");
    }

    @Override
    public void onPrepareLoad(Drawable placeHolderDrawable) {
        Log.d("tag","onPrepareLoad...");
    }
}
