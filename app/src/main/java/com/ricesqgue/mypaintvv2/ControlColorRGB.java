package com.ricesqgue.mypaintvv2;

import android.content.Context;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.support.annotation.IntegerRes;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;


/***
 * Created by Ricardo on 04/11/2016.
 */
public class ControlColorRGB extends LinearLayout {

    private SeekBar sbRed, sbGreen, sbBlue;
    private CircleView circle;
    private TextView tvColor;

    public ControlColorRGB(Context context) {
        super(context);
        inicializar();
    }

    public ControlColorRGB(Context context, AttributeSet attrs) {
        super(context, attrs);
        inicializar();
    }

    private void inicializar(){
        String infService = Context.LAYOUT_INFLATER_SERVICE;
        LayoutInflater layoutInflater = (LayoutInflater) getContext().getSystemService(infService);
        layoutInflater.inflate(R.layout.color_control,this,true);
        this.tvColor = (TextView) findViewById(R.id.tvColor);
        this.sbRed = (SeekBar) findViewById(R.id.seekBarRed);
        this.sbGreen = (SeekBar) findViewById(R.id.seekBarGreen);
        this.sbBlue = (SeekBar) findViewById(R.id.seekBarBlue);
        this.circle = (CircleView) findViewById(R.id.circle);

        this.circle.setColorCirculo(sbRed.getProgress(), sbGreen.getProgress(), sbBlue.getProgress());
        setTextColor();


        SeekBar.OnSeekBarChangeListener seekBarChangeListener = new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                circle.setColorCirculo(sbRed.getProgress(), sbGreen.getProgress(), sbBlue.getProgress());
                setTextColor();
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        };

        this.sbRed.setOnSeekBarChangeListener(seekBarChangeListener);
        this.sbGreen.setOnSeekBarChangeListener(seekBarChangeListener);
        this.sbBlue.setOnSeekBarChangeListener(seekBarChangeListener);

        this.sbBlue.getProgressDrawable().setColorFilter(0xFF0000FF, PorterDuff.Mode.MULTIPLY);
        this.sbRed.getProgressDrawable().setColorFilter(0xFFFF0000, PorterDuff.Mode.MULTIPLY);
        this.sbGreen.getProgressDrawable().setColorFilter(0xFF00FF00, PorterDuff.Mode.MULTIPLY);



    }

    private void setTextColor(){
        String red = Integer.toHexString(sbRed.getProgress());
        red = red.length() == 1 ? "0"+red : red;
        String green = Integer.toHexString(sbGreen.getProgress());
        green = green.length() == 1 ? "0"+green : green;
        String blue = Integer.toHexString(sbBlue.getProgress());
        blue = blue.length() == 1 ? "0"+blue : blue;
        this.tvColor.setText("RBG: ("+ sbRed.getProgress() +", " + sbGreen.getProgress() +
                ", " + sbBlue.getProgress()+")\nHex: #" + red + green + blue);

    }

    public int getColors(){
        return Color.rgb(sbRed.getProgress(),sbGreen.getProgress(),sbBlue.getProgress());
    }
}
