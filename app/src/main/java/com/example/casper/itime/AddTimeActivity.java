package com.example.casper.itime;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import com.example.casper.itime.data.RepeatDay;

import java.util.ArrayList;
import java.util.Calendar;

import static com.example.casper.itime.MainActivity.setStatusBarTransparent;

public class AddTimeActivity extends AppCompatActivity {
    private EditText editTitle, editRemark;

    private int year, month, day;
    private TextView dateTextView;

    private RepeatDay repeatDay;
    private TextView repeatDayTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_time);

        // 状态栏透明
        setStatusBarTransparent(this);

        // 获取颜色
        Intent intent = getIntent();
        int color = intent.getIntExtra("color", 0xFF000000);

        Toolbar toolbar = this.findViewById(R.id.tool_bar);
        // 设置颜色
        this.findViewById(R.id.app_bar_layout).setBackgroundColor(color);
        this.findViewById(R.id.head_layout).setBackgroundColor(color);
        setSupportActionBar(toolbar);
        toolbar.setBackgroundColor(color);

        // 工具栏按钮
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 取消
                AddTimeActivity.this.finish();
            }
        });
        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                // 保存数据
                AddTimeActivity.this.finish();
                return true;
            }
        });

        // 工具栏内容设置
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle("");

        editTitle = this.findViewById(R.id.title_edit_text);
        editRemark = this.findViewById(R.id.remark_edit_text);
        dateTextView = this.findViewById(R.id.date_detail_text);
        repeatDayTextView = this.findViewById(R.id.repeat_detail_text);

        // 初始化日期
        Calendar c = Calendar.getInstance();
        setDate(c.get(Calendar.YEAR), c.get(Calendar.MONTH) + 1, c.get(Calendar.DAY_OF_MONTH));
        showSelectedDate();
        // 初始化重复
        repeatDay = new RepeatDay();
        repeatDayTextView.setText(repeatDay.toString());

        this.findViewById(R.id.date_layout).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 选择日期
                Calendar c = Calendar.getInstance();
                DatePickerDialog dialog = new DatePickerDialog(AddTimeActivity.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                                // 储存日期
                                setDate(year, monthOfYear + 1, dayOfMonth);
                                // 显示日期
                                showSelectedDate();
                            }
                        }, c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH));
                dialog.show();
            }
        });
        this.findViewById(R.id.repeat_layout).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 选择重复
                ArrayList<String> items = (ArrayList<String>) RepeatDay.repeatDayItemLabel.clone();
                if (repeatDay.type == RepeatDay.NONE) {
                    items.remove(items.size() - 1);
                }
                new AlertDialog.Builder(AddTimeActivity.this)
                        .setTitle("周期")
                        .setItems(items.toArray(new String[items.size()]), new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                if (which != RepeatDay.repeatDayItemType.indexOf(RepeatDay.CUSTOMIZE)) {
                                    repeatDay.setType(RepeatDay.repeatDayItemType.get(which));
                                    repeatDayTextView.setText(repeatDay.toString());
                                } else {
                                    // 自定义日期
                                    RepeatDayCustomizeDialog repeatDayCustomizeDialog = new RepeatDayCustomizeDialog(AddTimeActivity.this, new RepeatDayCustomizeDialog.DialogEventListener() {
                                        @Override
                                        public void DialogEvent(int day) {
                                            repeatDay.setCustomizeDay(day);
                                            repeatDayTextView.setText(repeatDay.toString());
                                        }
                                    });
                                    repeatDayCustomizeDialog.setTitle("周期");
                                    repeatDayCustomizeDialog.show();
                                    dialog.dismiss();
                                }
                            }
                        })
                        .show();
            }
        });
        this.findViewById(R.id.background_image_layout).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 选择图片
            }
        });
    }

    private void setDate(int year, int month, int day) {
        this.year = year;
        this.month = month;
        this.day = day;
    }

    private void showSelectedDate() {
        dateTextView.setText(year + "年" + month + "月" + day + "日");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // 设置菜单栏按钮图片
        getMenuInflater().inflate(R.menu.activity_add_time_confirm, menu);
        return true;
    }
}
