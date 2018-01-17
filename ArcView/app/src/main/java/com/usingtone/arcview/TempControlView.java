package com.usingtone.arcview;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PaintFlagsDrawFilter;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by luze on 2018-01-16.
 */

public class TempControlView  extends View{
    int width; // 控件宽
    int height; //控件高
    int dialRadius; // 刻度盘半径
    int arcRadius; //圆弧半径
    int scaleHeight = dp2px(10); //刻度高度
    Paint dialPaint; // 刻度盘画笔
    Paint arcPaint; // 圆弧画笔
    Paint titlePaint; // 标题画笔
    Paint tempFlagPaint; //  温度标志画笔
    Paint buttonPaint; // 旋转按钮画笔
    Paint tempPaint; //温度显示画笔
    String title= "最高温度设置"; // 文本提示
    int temperature=15; // 温度

    int minTemp = 15; // 最低温度
    int maxTemp = 30; // 最高温度
    int angleRate = 4;  //四格  （每格4.5度 共18度） 代表温度1度
    Bitmap buttonImage = BitmapFactory.decodeResource(getResources(),R.mipmap.btn_rotate); //按钮图片
    Bitmap buttonImageShadow = BitmapFactory.decodeResource(getResources(),R.mipmap.btn_rotate_shadow); //按钮图片阴影

    PaintFlagsDrawFilter paintFlagsDrawFilter;

    float rotateAngle; // 当前按钮旋转角度
    float currentAngle; // 当前角度


    boolean isDown;
    boolean isMove;


    public TempControlView(Context context) {
        super(context);
        init();
    }

    public TempControlView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public TempControlView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

   private int dp2px(float value){
        float scale = getContext().getResources().getDisplayMetrics().density;
        return (int) (value*scale+0.5f);
    }
    void init(){
       dialPaint = new Paint();
       dialPaint.setAntiAlias(true);
       arcPaint = new Paint();
       arcPaint.setAntiAlias(true);
       arcPaint.setStyle(Paint.Style.STROKE);
       arcPaint.setStrokeWidth(4);
       titlePaint = new Paint();
       titlePaint.setAntiAlias(true);
       titlePaint.setTextSize(dp2px(10));
       tempFlagPaint = new Paint();
       tempFlagPaint.setAntiAlias(true);
       tempFlagPaint.setColor(Color.parseColor("#cc45e5"));
       tempFlagPaint.setTextSize(dp2px(12));
       tempPaint = new Paint();
       tempPaint.setAntiAlias(true);
       tempPaint.setColor(Color.parseColor("#cc4545"));
       tempPaint.setTextSize(dp2px(30));
       buttonPaint = new Paint();
       buttonPaint.setAntiAlias(true);

       paintFlagsDrawFilter = new PaintFlagsDrawFilter(0,Paint.FILTER_BITMAP_FLAG);

    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        width = height = Math.min(w,h);
        dialRadius = width/2-dp2px(20);
        arcRadius = dialRadius-dp2px(20);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        drawScale(canvas);
        drawArc(canvas);
        drawText(canvas);
        drawButtom(canvas);
        drawTemp(canvas);
    }

    /**
     * 绘制刻度盘
     * @param canvas 画布
     */

    private void drawScale(Canvas canvas){
        canvas.save();
        canvas.translate(getWidth()/2,getWidth()/2);
        canvas.rotate(-133);
        dialPaint.setColor(Color.parseColor("#3cb7ea"));
        for (int i =0;i<60;i++){
            canvas.drawLine(0,-dialRadius,0,-dialRadius+scaleHeight,dialPaint);
            canvas.rotate(4.5f);
        }
        canvas.rotate(90);
        dialPaint.setColor(Color.parseColor("#e37364"));
        for (int i=0;i<(temperature-minTemp)*angleRate;i++){
            canvas.drawLine(0,-dialRadius,0,-dialRadius+scaleHeight,dialPaint);
            canvas.rotate(4.5f);
        }
        canvas.restore();
    }

    /**
     * 绘制刻度盘下的圆弧
     * @param canvas
     */
    private void drawArc(Canvas canvas){
        canvas.save();
        canvas.translate(getWidth()/2,getHeight()/2);
        canvas.rotate(135+2);
        arcPaint.setColor(Color.parseColor("#3cb7ea"));
        RectF rectF = new RectF(-arcRadius,-arcRadius,arcRadius,arcRadius);
        canvas.drawArc(rectF,0,266,false,arcPaint);
        canvas.restore();
    }

    /**
     * 绘制温度标识 和 标题
     * @param canvas
     */
    private void drawText(Canvas canvas){
        canvas.save();
        float textWidth = titlePaint.measureText(title);
        canvas.drawText(title,(width-textWidth)/2,dialRadius*2+dp2px(10),titlePaint);

        String minTempFlag = minTemp<10?"0"+minTemp:minTemp+"";
        //最高温度标识
        canvas.rotate(-50,getWidth()/2,getHeight()/2);
        float tempFlagWidth = titlePaint.measureText(maxTemp+"");
        canvas.drawText(maxTemp+"",(width-tempFlagWidth)/2,height-dp2px(4),tempFlagPaint);
        canvas.rotate(101,getWidth()/2,getHeight()/2);
        canvas.drawText(minTemp+"",(width-tempFlagWidth)/2,height-dp2px(4),tempFlagPaint);
        canvas.restore();
    }

    /**
     * 绘制旋转按钮
     * @param canvas
     */
    private void drawButtom(Canvas canvas){
        int buttonWidth = buttonImage.getWidth();
        int buttonHeight = buttonImage.getHeight();
        int buttonShadowWidth = buttonImageShadow.getWidth();
        int buttonShadowHeight = buttonImageShadow.getHeight();
        canvas.drawBitmap(buttonImageShadow,(width-buttonShadowWidth)/2,(height-buttonShadowHeight)/2,buttonPaint);
        Matrix matrix = new Matrix();
        matrix.setTranslate(buttonWidth/2,buttonHeight/2);
        matrix.preRotate(45+rotateAngle);
        matrix.preTranslate(-buttonWidth/2,-buttonHeight/2);
        matrix.postTranslate((width-buttonWidth)/2,(height-buttonHeight)/2);
        canvas.setDrawFilter(paintFlagsDrawFilter);
        canvas.drawBitmap(buttonImage,matrix,buttonPaint);

    }
    /**
     * 绘制温度
     * @param canvas
     */
    private void drawTemp(Canvas canvas){
        canvas.save();
        canvas.translate(getWidth()/2,getHeight()/2);
        float tempWidth = tempPaint.measureText(temperature+"");
        float tempHeight = (tempPaint.ascent()+tempPaint.descent())/2;
        canvas.drawText(temperature+"°",-tempWidth/2-dp2px(5),-tempHeight,tempPaint);
        canvas.restore();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                isDown = true;
                float downX = event.getX();
                float downY = event.getY();
                currentAngle = calcAngle(downX,downY);
                break;
            case MotionEvent.ACTION_MOVE:
                isMove = true;
                float targetX;
                float targetY;
                downX = targetX = event.getX();
                downY = targetY = event.getY();
                float angle = calcAngle(targetX,targetY);

                float angleIncreased = angle-currentAngle;

                if (angleIncreased <-270){
                    angleIncreased = angleIncreased+360;
                }else if (angleIncreased>270){
                    angleIncreased = angleIncreased-360;
                }

                IncreaseAngle(angleIncreased);
                currentAngle = angle;
                invalidate();
                break;
            case MotionEvent.ACTION_UP:
                if (isDown&&isMove){
                    rotateAngle = (float) ((temperature-minTemp)*angleRate*4.5);
                    invalidate();
                    if (listener != null){
                        listener.change(temperature);
                    }
                    isDown = false;
                    isMove = false;
                }
                break;
        }
        return true;
    }

    /**
     *  以按钮圆心为坐标原点 建立坐标系 求出（targetx ，targety）坐标与x轴的夹角
     * @param targetX
     * @param targetY
     * @return 夹角
     */
    private float calcAngle(float targetX,float targetY){
        float x = targetX-width/2;
        float y = targetY-height/2;
        double radian ;
        if (x!= 0) {
            float tan = Math.abs(y / x);
            if (x > 0) {
                if (y >= 0) {
                    radian = Math.atan(tan);
                } else {
                    radian = x * Math.PI - Math.atan(tan);
                }
            }else {
                if (y>=0){
                    radian = Math.PI-Math.atan(tan);
                }else {
                    radian = Math.PI+Math.atan(tan);
                }
            }
        }else {
            if (y>0){
                radian = Math.PI/2;
            }else {
                radian = -Math.PI/2;
            }
        }

        return (float) (radian*180/Math.PI);
    }

    /**
     * 增加旋转角度
     * @param angle
     */
    private void IncreaseAngle(float angle){
        rotateAngle += angle;
        if (rotateAngle <0){
            rotateAngle =0;
        }else if(rotateAngle>270) {
            rotateAngle =270;
        }
        temperature = (int) (rotateAngle/4.5/angle+minTemp);
    }

    public void setTemp(int minTemp,int maxTemp,int temperature){
        this.minTemp = minTemp;
        this.maxTemp = maxTemp;
        this.temperature = temperature;
        this.angleRate = 60/(maxTemp-minTemp);
        rotateAngle = (float) ((temperature-minTemp)*angleRate*4.5);
        invalidate();
    }
    OnTempChangeListener listener;

    public void setListener(OnTempChangeListener listener) {
        this.listener = listener;
    }

    public interface OnTempChangeListener{
        void change(int temp);
    }
}
