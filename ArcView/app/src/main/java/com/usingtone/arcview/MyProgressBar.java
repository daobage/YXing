package com.usingtone.arcview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by luze on 2018-01-15.
 */

public class MyProgressBar extends View {
    Paint paint;
    Paint paint2;
    int max = 100;
    int progress =0;
    RectF rectF;
    int width;

    public MyProgressBar(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    public MyProgressBar(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }
    void initView(){
        paint = new Paint();
        paint.setAntiAlias(true);
        paint.setDither(true);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(4);


        paint2 = new Paint();
        paint2.setAntiAlias(true);
        paint2.setDither(true);
        paint2.setTextAlign(Paint.Align.CENTER);
        paint2.setColor(Color.BLUE);
        paint2.setTextSize(30);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        width =getWidth();
        rectF = new RectF(width/2+100,width/2+100,width/2+300,width/2+300);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        paint.setColor(Color.GRAY);
        canvas.drawCircle(width/2+200,width/2+200,100,paint);
        paint.setColor(Color.RED);

        canvas.drawArc(rectF,-90,progress*1f/max*360,false,paint);

        canvas.drawText((int)(progress*1f/max*100f)+"%",width/2+200,width/2+200,paint2);
    }

    public void startToProgress(){
        new Thread(){
            @Override
            public void run() {
                super.run();
                while (progress<max){
                    progress++;
                    try {
                        sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    postInvalidate();
                }
                if (listener!=null){
                    listener.onFinish();
                }

            }
        }.start();
    }

    public interface OnProgressCompleteListener{
        void onFinish();
    }
    OnProgressCompleteListener listener;

    public void setListener(OnProgressCompleteListener listener) {
        this.listener = listener;
    }
}
