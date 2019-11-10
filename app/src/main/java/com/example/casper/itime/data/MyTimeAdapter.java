package com.example.casper.itime.data;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.casper.itime.R;
import com.example.casper.itime.data.model.MyTime;

import java.util.ArrayList;
import java.util.Calendar;


public class MyTimeAdapter extends ArrayAdapter<MyTime> {
    //定义一个startActivityForResult（）方法用到的整型值
    private final int requestCode = 1600;

    private int resourceId;

    public MyTimeAdapter(Context context, int resource, ArrayList<MyTime> objects) {
        super(context, resource, objects);
        resourceId = resource;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater mInflater = LayoutInflater.from(this.getContext());
        View view = mInflater.inflate(this.resourceId, parent, false);

        MyTime myTime = this.getItem(position);

        // 计算时间差（天）
        Calendar now = Calendar.getInstance();
        Calendar timeDate = Calendar.getInstance();
        timeDate.set(myTime.date.year, myTime.date.month - 1, myTime.date.day);
        long deltaDay = (now.getTime().getTime() - timeDate.getTime().getTime()) / (1000 * 3600 * 24);

        String deltaString;
        if (deltaDay == 0) {
            deltaString = "今天";
        } else if (deltaDay > 0) {
            deltaString = "已经" + deltaDay + "天";
        } else {
            deltaString = "还有" + Math.abs(deltaDay) + "天";
        }

        ((TextView) view.findViewById(R.id.home_my_time_title_text_iew)).setText(myTime.title);
        if (myTime.remark.isEmpty()) {
            ((TextView) view.findViewById(R.id.home_my_time_remark_text_view)).setHeight(0);
        } else {
            ((TextView) view.findViewById(R.id.home_my_time_remark_text_view)).setText(myTime.remark);
        }
        ((TextView) view.findViewById(R.id.home_my_time_countdown_text_view)).setText(deltaString);

        return view;
    }
}
