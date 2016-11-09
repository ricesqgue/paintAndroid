package com.ricesqgue.mypaintvv2;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Environment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.view.ContextThemeWrapper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.io.File;
import java.io.FileFilter;

public class MainActivity extends AppCompatActivity {

    private Button btnColor, btnWidth, btnSave, btnDelete, btnClear;
    private DrawView drawView;

    private ControlColorRGB controlColorRGB;
    private Dialog dialogColor;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.btnColor = (Button) findViewById(R.id.btnColor);
        this.btnWidth = (Button) findViewById(R.id.btnWidth);
        this.btnSave = (Button) findViewById(R.id.btnSave);
        this.drawView = (DrawView) findViewById(R.id.drawView);
        this.btnDelete = (Button) findViewById(R.id.btnDelete);
        this.btnClear = (Button) findViewById(R.id.btnClear);

        ContextThemeWrapper contextThemeWrapper = new ContextThemeWrapper(this,R.style.AppTheme);
        this.controlColorRGB = new ControlColorRGB(this);

        this.dialogColor = new Dialog(contextThemeWrapper);
        this.dialogColor.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialogColor.setContentView(this.controlColorRGB);




        inicializaEventos();
    }


    public Boolean deleteFiles(){
        File[] archivos = new File(Environment.getExternalStorageDirectory()+File.separator+"Pictures/MyPaint").listFiles(new FileFilter() {
            public boolean accept(File archivo) {
                if (archivo.isFile())
                    return archivo.getName().endsWith(".png");
                return false;
            }
        });
        for (File archivo : archivos)
        archivo.delete();
        return true;
    }

    private void inicializaEventos(){
        final String[] widths = {"5", "10", "15", "20" , "25", "30"};
        this.btnWidth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(MainActivity.this).setTitle(R.string.dialog_width_title)
                        .setItems(widths, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                switch (which){
                                    case 0:
                                        //5
                                        drawView.setMyStrokeWidth(5f);
                                        break;
                                    case 1:
                                        //10
                                        drawView.setMyStrokeWidth(10f);
                                        break;
                                    case 2:
                                        //15
                                        drawView.setMyStrokeWidth(15f);
                                        break;
                                    case 3:
                                        //20
                                        drawView.setMyStrokeWidth(20f);
                                        break;
                                    case 4:
                                        //25
                                        drawView.setMyStrokeWidth(25f);
                                        break;
                                    case 5:
                                        //30
                                        drawView.setMyStrokeWidth(30f);
                                }
                            }
                        }).show();
            }
        });

        this.btnColor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogColor.show();

            }
        });

        dialogColor.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                drawView.setMyColor(controlColorRGB.getColors());
            }
        });

        this.btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(MainActivity.this).setTitle(R.string.dialog_save_title)
                        .setPositiveButton(R.string.dialog_save_pbutton, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                if (drawView.saveBitmap()){
                                    Toast.makeText(MainActivity.this,R.string.dialog_save_success,Toast.LENGTH_SHORT).show();
                                }else{
                                    Toast.makeText(MainActivity.this,R.string.dialog_save_fail,Toast.LENGTH_SHORT).show();
                                }
                            }
                        }).setNegativeButton(R.string.dialog_save_nbutton, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                }).setMessage(R.string.dialog_save_message).show();
            }
        });

        this.btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(MainActivity.this).setTitle(R.string.dialog_delete_title)
                        .setPositiveButton(R.string.dialog_delete_pbutton, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                if (deleteFiles()){
                                    Toast.makeText(MainActivity.this,R.string.dialog_delete_success,
                                            Toast.LENGTH_SHORT).show();
                                }
                            }
                        }).setNegativeButton(R.string.dialog_delete_nbutton, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                }).setMessage(R.string.dialog_delete_message).show();
            }
        });

        this.btnClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(MainActivity.this).setTitle(R.string.dialog_erase_title)
                        .setPositiveButton(R.string.dialog_erase_pbutton, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                drawView.eraseAll();
                            }
                        }).setNegativeButton(R.string.dialog_erase_nbutton, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                }).setMessage(R.string.dialog_erase_message).show();
            }
        });


    }
}
