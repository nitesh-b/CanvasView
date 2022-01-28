package com.yalantis.ucrop.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class CanvasView extends androidx.appcompat.widget.AppCompatImageView {
    public int widht;
    public int height;
    private Bitmap mbitmap;
    private Canvas mcanvas;
    private Path path;
    private Paint paint;
    private float mX, mY;
    Context context;
    private static final float Tolerance = 5;
    private ArrayList<Paint> paints = new ArrayList<>();
    private int color = Color.BLACK;
    private float stroke = 4f;
    private Map<Paint, Path> paintMap = new HashMap<>();

    public CanvasView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CanvasView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        this.context = context;
        freshPaint();
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mbitmap = Bitmap.createBitmap(w,h,Bitmap.Config.ARGB_8888);
        mcanvas = new Canvas(mbitmap);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        this.mcanvas = canvas;
        if(paint != null && path != null) {
            paintMap.forEach((paint, path) ->{
                mcanvas.drawPath(path, paint);
            });
            mcanvas.save();
        }else{
            Log.d("Paths", "onDraw: null null");
        }

    }

    public void clear() {
        if(paint != null && path != null){
            paint.reset();
            path.reset();
            paintMap.clear();
            if(mcanvas != null){
                mcanvas.drawColor(Color.TRANSPARENT, PorterDuff.Mode.CLEAR);
                freshPaint();
            }
        }



    }

    public void freshPaint(){
        paint = new Paint();
        this.paint.setAntiAlias(true);
        this.paint.setStyle(Paint.Style.STROKE);
        this.paint.setStrokeJoin(Paint.Join.ROUND);
        paint.setColor(color);
        paint.setStrokeWidth(stroke);
        path = new Path();
        paintMap.put(paint, path);
    }

    public void setColor(int color){
        this.color = color;
    }

    public void setStroke(float stroke){
        this.stroke = stroke;
    }


    private void startTouch (float x , float y){
        path.moveTo(x,y);
        mX = x;
        mY = y ;
    }
    public void moveTouche (float x,float y ) {
        float dx = Math.abs(x - mX);
        float dy = Math.abs(y - mY);
        if(dx >= Tolerance || dy >= Tolerance){
            path.quadTo(mX,mY,(x+mX)/2,(y+mY)/2);
            mX = x ;
            mY = y;

        }
    }
    private void upTouch(){
        path.lineTo(mX,mY);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float x = event.getX();
        float y = event.getY();
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                startTouch(x,y);
                invalidate();
                break ;
            case MotionEvent.ACTION_UP:
                upTouch();
                invalidate();
                break ;
            case MotionEvent.ACTION_MOVE:
                moveTouche(x,y);
                invalidate();
                break ;

        }
        return true ;
    }



}
