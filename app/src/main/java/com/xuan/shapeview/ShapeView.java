package com.xuan.shapeview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.view.View;

/**
 * com.xuan.shapeview
 *
 * @author by xuan on 2018/5/18
 * @version [版本号, 2018/5/18]
 * @update by xuan on 2018/5/18
 * @descript
 */
public class ShapeView extends View {

    private int squareColor= ContextCompat.getColor(getContext(),R.color.red);
    private int circleColor= ContextCompat.getColor(getContext(),R.color.green);
    private int triangleColor= ContextCompat.getColor(getContext(),R.color.colorPrimary);

    public void autoChange() {
        switch (shape){
            case circle:
                shape=Shape.square;
                break;
            case square:
                shape=Shape.triangle;
                break;
            case triangle:
                shape=Shape.circle;
                break;
        }
        invalidate();
    }

    public enum Shape{
        square,circle,triangle
    }
    private Shape shape=Shape.circle;

    private Paint squarePaint,circlePaint,trianglePaint;

    private Path trianglePath;

    public ShapeView(Context context) {
        this(context,null);
    }

    public ShapeView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public ShapeView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        initAttrs(context,attrs);

        squarePaint=new Paint();
        initPaint(squarePaint,squareColor);

        circlePaint=new Paint();
        initPaint(circlePaint,circleColor);
        circlePaint.setStrokeCap(Paint.Cap.ROUND);
        circlePaint.setStyle(Paint.Style.FILL);

        trianglePaint=new Paint();
        initPaint(trianglePaint,triangleColor);

        trianglePath = new Path();

    }

    private void initPaint(Paint paint, int bgColor) {
        paint.setAntiAlias(true);
        paint.setDither(true);

        paint.setColor(bgColor);
    }

    private void initAttrs(Context context, AttributeSet attrs) {
        TypedArray typedArray = context.obtainStyledAttributes(attrs,R.styleable.ShapeView);

        squareColor=typedArray.getColor(R.styleable.ShapeView_squareColor,squareColor);
        circleColor=typedArray.getColor(R.styleable.ShapeView_circleColor,circleColor);
        triangleColor=typedArray.getColor(R.styleable.ShapeView_triangleColor,triangleColor);

        typedArray.recycle();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        //长宽一致。
        int width=MeasureSpec.getSize(widthMeasureSpec);
        int height=MeasureSpec.getSize(heightMeasureSpec);
        setMeasuredDimension(Math.min(width,height),Math.min(width,height));
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        if(shape==Shape.square){
            //画正方形
            canvas.drawRect(0,0,getWidth(),getHeight(),squarePaint);

        }else if(shape==Shape.circle){
         /* 画一个圆
            drawCircle(float cx, float cy, float radius, @NonNull Paint paint)
            cx:圆心的x坐标。
            cy:圆心的y坐标。
            radius:圆的半径。
            paint:绘制时所使用的画笔。
        */
            canvas.drawCircle(getWidth()/2,getWidth()/2,getWidth()/2,circlePaint);

        }else if(shape==Shape.triangle){
            //画三角形
            //算出正方形中的等边三角形中顶点到底部的高度 为Y轴起始点
            float dy= (float) ((getWidth()/2)*Math.sqrt(3));
            trianglePath.moveTo(getWidth()/2,0);
            trianglePath.lineTo(0,dy);
            trianglePath.lineTo(getWidth(),dy);
            trianglePath.close();// trianglePath.lineTo(getWidth()/2,0); 闭合

            canvas.drawPath(trianglePath,trianglePaint);
        }


    }

    public synchronized void setShape(Shape shape){
        this.shape=shape;
        invalidate();
    }


}
