package com.ricesqgue.mypaintvv2;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

/***
 * Created by Ricardo on 04/11/2016.
 */
public class CircleView extends View {
    private Paint paint;
    private int colorr;

    public CircleView(Context context) {
        super(context);
        inicializa();
    }

    public CircleView(Context context, AttributeSet attrs) {
        super(context, attrs);
        inicializa();
    }

    public CircleView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        inicializa();
    }

    private void inicializa(){
        this.paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        this.setColorCirculo(255,0,0);
        paint.setColor(colorr);

    }

    public void setColorCirculo(int red, int green, int blue){
        this.colorr = Color.rgb(red, green, blue);
        invalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        this.paint.setColor(this.colorr);
        canvas.drawCircle((canvas.getWidth())/2,canvas.getHeight()/2,(canvas.getHeight()-100)/2,this.paint);
    }
}
