package com.example.casper.itime;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;

import com.example.casper.itime.data.MyTimeAdapter;
import com.example.casper.itime.data.model.MyTime;
import com.example.casper.itime.util.MyTimeManager;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

import static android.app.Activity.RESULT_OK;
import static androidx.core.app.ActivityCompat.startActivityForResult;

public class HomeFragment extends Fragment {
    private FloatingActionButton fab;
    private int color = 0;
    private boolean colorChanged = false;
    private boolean setable = false;

    //定义一个startActivityForResult（）方法用到的整型值
    private static final int requestCode = 1500;

    public static final int ADD_MODE = 1;
    public static final int MODIFY_MODE = 2;
    public static final int DELETE_MODE = 3;

    private ArrayList<MyTime> myTimes;
    private MyTimeAdapter myTimeAdapter;

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == this.requestCode) {
            if (resultCode == RESULT_OK) {
                //接收并添加传过来的值
                Bundle bundle = data.getExtras();
                int mode = bundle.getInt("mode");
                switch (mode) {
                    case ADD_MODE: {
                        MyTime myTime = (MyTime) bundle.getSerializable("time");
                        myTimes.add(myTime);
                        myTimeAdapter.notifyDataSetChanged();
                        break;
                    }
                    case MODIFY_MODE: {
                        MyTime myTime = (MyTime) bundle.getSerializable("time");
                        int position = bundle.getInt("position");
                        if (position >= 0 && position < myTimes.size()) {
                            myTimes.set(position, myTime);
                            myTimeAdapter.notifyDataSetChanged();
                        }
                        break;
                    }
                    case DELETE_MODE: {
                        int position = bundle.getInt("position");
                        if (position >= 0 && position < myTimes.size()) {
                            myTimes.remove(position);
                            myTimeAdapter.notifyDataSetChanged();
                        }
                        break;
                    }
                }
            }
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.home_fragment, container, false);

        fab = view.findViewById(R.id.add_fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), EditTimeActivity.class);
                intent.putExtra("mode", ADD_MODE);
                intent.putExtra("color", color);
                startActivityForResult(intent, requestCode);
            }
        });

        initData();
        GridView gridView = view.findViewById(R.id.home_my_time_grid);
        gridView.setAdapter(myTimeAdapter);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                Intent intent = new Intent(getContext(), TimeDetailActivity.class);
                intent.putExtra("position", position);
                intent.putExtra("time", myTimes.get(position));
                startActivityForResult(intent, requestCode);
            }
        });

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
