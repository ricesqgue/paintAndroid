package com.ricesqgue.mypaintvv2;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.os.Environment;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.util.Calendar;

/***
 * Created by Ricardo on 08/11/2016.
 */
public class DrawView extends View {
    private Bitmap myBitmap;
    private Canvas myCanvas;
    private Path myPath, myCirclePath;
    private Paint myPaint, myBitmapPaint, myCirclePaint;
    private int myColor;
    private float myStrokeWidth;

    private float mX, mY;
    private static final float TOUCH = 4;


    public DrawView(Context context) {
        super(context);
        inicializa();
    }

    public DrawView(Context context, AttributeSet attrs) {
        super(context, attrs);
        inicializa();
    }

    public DrawView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        inicializa();
    }

    private void inicializa(){
        this.myColor = Color.CYAN;
        this.myStrokeWidth = 10f;

        this.myPath = new Path();

        this.myBitmapPaint = new Paint(Paint.DITHER_FLAG);
        this.myCirclePaint = new Paint();
        this.myCirclePath = new Path();
        this.myCirclePaint.setAntiAlias(true);
        this.myCirclePaint.setColor(Color.BLUE);
        this.myCirclePaint.setStyle(Paint.Style.STROKE);
        this.myCirclePaint.setStrokeJoin(Paint.Join.MITER);
        this.myCirclePaint.setStrokeWidth(4f);
        this.myPaint = new Paint();
        this.myPaint.setAntiAlias(true);
        this.myPaint.setDither(true);
        this.myPaint.setColor(this.myColor);
        this.myPaint.setStyle(Paint.Style.STROKE);
        this.myPaint.setStrokeJoin(Paint.Join.ROUND);
        this.myPaint.setStrokeCap(Paint.Cap.ROUND);
        this.myPaint.setStrokeWidth(12);



    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

        this.myBitmap = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
        this.myCanvas = new Canvas(this.myBitmap);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        this.myPaint.setStrokeWidth(this.myStrokeWidth);
        this.myPaint.setColor(this.myColor);
        canvas.drawBitmap(this.myBitmap, 0, 0, this.myBitmapPaint);
        canvas.drawPath(this.myPath, this.myPaint);
        canvas.drawPath(this.myCirclePath, this.myCirclePaint);

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float x = event.getX();
        float y = event.getY();

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                this.myPath.reset();
                this.myPath.moveTo(x,y);
                this.mX = x;
                this.mY = y;
                invalidate();
                break;

            case MotionEvent.ACTION_MOVE:
                float dx = Math.abs(x - this.mX);
                float dy = Math.abs(y - this.mY);
                if (dx >= TOUCH || dy >= TOUCH) {
                    this.myPath.quadTo(this.mX, this.mY, (x + this.mX)/2, (y + this.mY)/2);
                    this.mX = x;
                    this.mY = y;

                    this.myCirclePath.reset();
                    this.myCirclePath.addCircle(this.mX, this.mY, 30, Path.Direction.CW);
                }
                invalidate();
                break;

            case MotionEvent.ACTION_UP:{
                this.myPath.lineTo(this.mX, this.mY);
                this.myCirclePath.reset();
                this.myCanvas.drawPath(this.myPath, this.myPaint);
                this.myPath.reset();
                invalidate();
                break;
            }
        }

        return true;
    }

    public void setMyColor (int color){
        this.myColor = color;
    }

    public void setMyStrokeWidth(float width){
        this.myStrokeWidth = width;
    }

    public Bitmap getMyBitmap(){
        return this.myBitmap;
    }

    public void setMyBitmap(Bitmap bitmap){
        this.myBitmap = bitmap;
    }

    public void saveBitmap() {

        Calendar c = Calendar.getInstance();


        ByteArrayOutputStream bytes = new ByteArrayOutputStream();

        this.myBitmap.compress(Bitmap.CompressFormat.JPEG, 60, bytes);
        try {
            File f = new File(Environment.getExternalStorageDirectory()
                    + File.separator + "MyPaint" + File.separator +"imagen"+c.get(Calendar.YEAR)+
                    c.get(Calendar.MONTH)+c.get(Calendar.DAY_OF_MONTH)+c.get(Calendar.HOUR)+
                    c.get(Calendar.MINUTE)+c.get(Calendar.SECOND)+".jpg");
            f.createNewFile();
            FileOutputStream fo = new FileOutputStream(f);
            fo.write(bytes.toByteArray());
            fo.close();

        } catch (Exception e){

        }

    }
}
