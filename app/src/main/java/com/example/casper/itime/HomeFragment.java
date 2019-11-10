package com.example.casper.itime;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import com.example.casper.itime.data.MyTimeAdapter;
import com.example.casper.itime.data.model.Date;
import com.example.casper.itime.data.model.MyTime;
import com.example.casper.itime.data.model.RepeatDay;
import com.example.casper.itime.util.MyTimeManager;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

import static android.app.Activity.RESULT_OK;

public class HomeFragment extends Fragment {
    private FloatingActionButton fab;
    private int color = 0;
    private boolean colorChanged = false;
    private boolean setable = false;

    //定义一个startActivityForResult（）方法用到的整型值
    private final int requestCode = 1500;

    private ArrayList<MyTime> myTimes;
    private MyTimeAdapter myTimeAdapter;

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == this.requestCode) {
            if (resultCode == RESULT_OK) {
                //接收并添加传过来的值
                Bundle bundle = data.getExtras();
                MyTime myTime = (MyTime) bundle.getSerializable("time");
                myTimes.add(myTime);
                myTimeAdapter.notifyDataSetChanged();
            }
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        fab = view.findViewById(R.id.add_fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), AddTimeActivity.class);
                intent.putExtra("color", color);
                startActivityForResult(intent, requestCode);
            }
        });

        initData();
        GridView gridView = view.findViewById(R.id.my_time_grid);
        gridView.setAdapter(myTimeAdapter);

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        setable = true;
        if (colorChanged) {
            this.colorChanged = false;
            setFabColor();
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        setable = false;
        // myTime持久化
        MyTimeManager.save(this.getContext(), myTimes);
    }

    private void initData() {
        // 加载myTime
        myTimes = MyTimeManager.load(this.getContext());
        myTimeAdapter = new MyTimeAdapter(this.getContext(), R.layout.home_my_time_item_layout, myTimes);
    }

    public void setColor(int color) {
        if (this.color != color) {
            colorChanged = true;
            this.color = color;

            if (this.setable) {
                this.colorChanged = false;
                setFabColor();
            }
        }
    }

    private void setFabColor() {
        fab.setBackgroundTintList(ColorStateList.valueOf(color));
    }
}
