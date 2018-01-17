package com.usingtone.arcview;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by luze on 2018-01-17.
 */

public class TemperatureView extends View {
    Paint  externalPaint; //外层画笔
    RectF externalRectF;
    float externalRadius;
    int strokeWidth = dp2px(15);
    int width; // 控件宽
    int height; // 控件高

    Paint innerPaint;
    float innerRadius;
    RectF innerRectf;

    Paint textPaint;
    String normal = "正常";
    String warning = "预警";
    String danger = "危险";

    Paint scalePaint;
    int shortScale = dp2px(5);
    int longScale = dp2px(10);

    Paint scaleFlagPaint;

    Bitmap arrowBitmap = BitmapFactory.decodeResource(getResources(),R.mipmap.icon_arrow);
    Paint bitmapPaint;

    public TemperatureView(Context context) {
        super(context);
        init();
    }

    public TemperatureView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public TemperatureView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    void init (){
        externalPaint = new Paint();
        externalPaint.setStyle(Paint.Style.STROKE);
//        externalPaint.setStrokeCap(Paint.Cap.BUTT);
//        externalPaint.setStrokeCap(Paint.Cap.ROUND); // 圆弧头
        externalPaint.setStrokeCap(Paint.Cap.SQUARE);
        externalPaint.setStrokeWidth(strokeWidth);

        innerPaint = new Paint();
        innerPaint.setAntiAlias(true);
        innerPaint.setStyle(Paint.Style.STROKE);
        innerPaint.setStrokeWidth(4);

        textPaint = new Paint();
        textPaint.setAntiAlias(true);
        textPaint.setTextSize(dp2px(10));

        scalePaint = new Paint();
        scalePaint.setAntiAlias(true);

        scaleFlagPaint = new Paint();
        scaleFlagPaint.setAntiAlias(true);
        scaleFlagPaint.setTextSize(dp2px(10));
        scaleFlagPaint.setTextAlign(Paint.Align.CENTER);

        bitmapPaint = new Paint();
        bitmapPaint.setAntiAlias(true);

    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        width = height = getWidth();
        externalRadius = (width - strokeWidth)/2-dp2px(5);
        innerRadius = externalRadius-strokeWidth/2;
    }

    int dp2px(float value){
        float scale = getContext().getResources().getDisplayMetrics().density;
        return (int) (value*scale+0.5f);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        drawExternal(canvas);
        drawInnerArc(canvas);
        drawScale(canvas);
        drawScaleFlag(canvas);
        drawStrings(canvas);
        drawArrowBitmap(canvas);
    }

    /**
     * 绘制圆弧 颜色区分
     * @param canvas
     */
    void drawExternal(Canvas canvas){
        canvas.save();
        canvas.translate(width/2,width/2);
        canvas.rotate(135);
        externalRectF = new RectF(-externalRadius,-externalRadius,externalRadius,externalRadius);
        externalPaint.setColor(Color.GREEN);
        canvas.drawArc(externalRectF,0,100,false,externalPaint);
        externalPaint.setColor(Color.YELLOW);
        canvas.drawArc(externalRectF,100,90,false,externalPaint);
        externalPaint.setColor(Color.RED);
        canvas.drawArc(externalRectF,190,80,false,externalPaint);
        canvas.restore();
    }

    /**
     * 绘制 刻度弧
     * @param canvas
     */
    void drawInnerArc(Canvas canvas){
        canvas.save();
        canvas.translate(width/2,width/2);
        canvas.rotate(132);
        innerRectf = new RectF(-innerRadius,-innerRadius,innerRadius,innerRadius);
        canvas.drawArc(innerRectf,0,276,false,innerPaint);
        canvas.restore();
    }

    /**
     * 绘制刻度盘
     * @param canvas
     */
    void drawScale(Canvas canvas){
        canvas.save();
        canvas.translate(width/2,width/2);
        canvas.rotate(45);
        for (int i =0;i<=40;i++){
            if (i%5==0){
                canvas.drawLine(0,innerRadius-longScale,0,innerRadius,scalePaint);
            }else {
                canvas.drawLine(0,innerRadius-shortScale,0,innerRadius,scalePaint);
            }
            canvas.rotate(6.75f);
        }
        canvas.restore();

    }

    /**
     * 绘制 刻度 数字
     * @param canvas
     */
    void drawScaleFlag(Canvas canvas){
        canvas.save();
        canvas.translate(width/2,width/2);
        canvas.rotate(135);
        for (int i=40;i>=0;i--){
            if (i%5 == 0){
                canvas.drawText(i+"",0,-innerRadius+longScale+dp2px(10),scaleFlagPaint);
            }
            canvas.rotate(-6.75f);
        }
        canvas.restore();
    }

    /**
     * 绘制 表盘上的提示文字
     * @param canvas
     */
    void drawStrings(Canvas canvas){
        canvas.save();
        canvas.translate(width/2,width/2);
        canvas.rotate(-90);
        canvas.drawText(normal,0,-externalRadius+strokeWidth/2-dp2px(5),textPaint);
        canvas.rotate(175);
        canvas.drawText(danger,0,-externalRadius+strokeWidth/2-dp2px(5),textPaint);
        canvas.rotate(-85);
        canvas.drawText(warning,0,-externalRadius+strokeWidth/2-dp2px(5),textPaint);
        canvas.restore();
        canvas.save();
    }

    void drawArrowBitmap(Canvas canvas){
        canvas.save();
        int buttonWidth = arrowBitmap.getWidth();
        int buttonHeight = arrowBitmap.getHeight();
        Matrix matrix = new Matrix();
//        matrix.preTranslate(width/2,width/2);
//        matrix.setRotate(45);
        matrix.setTranslate(buttonWidth/2,buttonHeight/2);
        matrix.preRotate(45);
        matrix.preTranslate(-buttonWidth/2,-buttonHeight/2);
        matrix.postTranslate((width-buttonWidth)/2,(height-buttonHeight)/2);
        canvas.drawBitmap(arrowBitmap,matrix,bitmapPaint);

    }
}
