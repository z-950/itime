package com.example.casper.itime;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.SeekBar;

public class ColorPickerDialog extends Dialog {
    private ColorSelectDialog.DialogEventListener listener;
    private Context context;
    private int currentColor;

    private int r, g, b;

    private SeekBar seekBarR, seekBarG, seekBarB;
    private ImageView preview;

    public ColorPickerDialog(Context context, int color, ColorSelectDialog.DialogEventListener listener) {
        super(context, R.style.dialog);
        this.context = context;
        this.listener = listener;
        currentColor = color;
    }

    @Override
    public void dismiss() {
        listener.DialogEvent(currentColor);
        super.dismiss();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View layout = inflater.inflate(R.layout.color_picker_layout, null);

        preview = layout.findViewById(R.id.color_picked_preview);
        preview.setBackgroundColor(currentColor);

        // 解析rgb频道
        b = (currentColor & 0x000000FF);
        g = (currentColor & 0x0000FF00) >> 8;
        r = (currentColor & 0x00FF0000) >> 16;

        seekBarR = layout.findViewById(R.id.seek_bar_r);
        seekBarG = layout.findViewById(R.id.seek_bar_g);
        seekBarB = layout.findViewById(R.id.seek_bar_b);

        seekBarR.setProgress(r);
        seekBarG.setProgress(g);
        seekBarB.setProgress(b);

        initListener();

        this.setContentView(layout);

        // 按屏幕百分比设置宽度
        DisplayMetrics metrics = new DisplayMetrics();
        ((WindowManager) context.getApplicationContext().getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay().getMetrics(metrics);
        int screenWidth = metrics.widthPixels;
        WindowManager.LayoutParams params = getWindow().getAttributes();
        params.width = (int) (screenWidth * 0.8);
        getWindow().setAttributes(params);
    }

    private void initListener() {
        seekBarR.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                r = seekBar.getProgress();
                handleChangeColor();
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });
        seekBarG.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                g = seekBar.getProgress();
                handleChangeColor();
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });
        seekBarB.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean _b) {
                b = seekBar.getProgress();
                handleChangeColor();
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });
    }

    private void calculateCurrentColor() {
        currentColor = 0xFF;
        currentColor *= 0x100;
        currentColor += r;
        currentColor *= 0x100;
        currentColor += g;
        currentColor *= 0x100;
        currentColor += b;
    }

    private void handleChangeColor() {
        calculateCurrentColor();

        preview.setBackgroundColor(currentColor);
    }


}
