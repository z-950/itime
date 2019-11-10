package com.example.casper.itime;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class ColorSelectDialog extends Dialog {
    public interface DialogEventListener {
        void DialogEvent(int color);
    }

    private DialogEventListener listener;
    private Context context;
    private int currentColor;

    public ColorSelectDialog(Context context, int color, DialogEventListener listener) {
        super(context, R.style.dialog);
        this.context = context;
        this.listener = listener;
        currentColor = color;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View layout = inflater.inflate(R.layout.color_select_layout, null);

        ArrayList<Integer> colors = getColors();
        GridArrayAdapter adapter = new GridArrayAdapter(context, R.layout.color_select_item_layout, colors, listener);

        ((TextView) layout.findViewById(R.id.color_select_dialog_title)).setText("选择颜色");

        GridView gridView = layout.findViewById(R.id.color_select_grid);
        gridView.setAdapter(adapter);

        this.setContentView(layout);
    }

    private ArrayList<Integer> getColors() {
        ArrayList<Integer> colors = new ArrayList<>();

        colors.add(0xff0d47a1);
        colors.add(0xff2196f3);
        colors.add(0xff607d8b);
        colors.add(0xffffeb3b);
        colors.add(0xffff5722);
        colors.add(0xff00796b);
        colors.add(0xff9c27b0);
        colors.add(0xffe53935);

        return colors;
    }

    class GridArrayAdapter extends ArrayAdapter<Integer> {
        private int resourceId;
        DialogEventListener listener;
        private int colorsLen;

        public GridArrayAdapter(Context context, int resource, List<Integer> objects, DialogEventListener listener) {
            super(context, resource, objects);
            resourceId = resource;
            this.listener = listener;
            this.colorsLen = objects.size();
        }

        @Override
        public int getCount() {
            return super.getCount() + 1;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            LayoutInflater mInflater = LayoutInflater.from(this.getContext());
            View item = mInflater.inflate(this.resourceId, parent, false);
            Button button = item.findViewById(R.id.color_select_button);

            if (position == colorsLen) {
                button.setBackgroundResource(R.drawable.baseline_color_lens_black_18dp);
                button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        new ColorPickerDialog(context, currentColor, listener).show();
                        dismiss();
                    }
                });
            } else {
                final int color = this.getItem(position);

                button.setBackgroundColor(color);
                button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        listener.DialogEvent(color);
                        currentColor = color;
                    }
                });
            }
            return item;
        }
    }
}
