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

import com.example.casper.itime.data.model.MyTime;
import com.example.casper.itime.data.model.RepeatDay;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.Calendar;

import static com.example.casper.itime.MainActivity.setStatusBarTransparent;

public class AddTimeActivity extends AppCompatActivity {
    private EditText editTitle, editRemark;

    private TextView dateTextView;
    private TextView repeatDayTextView;

    private MyTime myTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_time);

        // 状态栏透明
        setStatusBarTransparent(this);

        // 获取颜色
        Intent intent = getIntent();
        int color = intent.getIntExtra("color", 0xFF000000);

        final Toolbar toolbar = this.findViewById(R.id.tool_bar);
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
                myTime.title = editTitle.getText().toString();
                myTime.remark = editRemark.getText().toString();

                if (myTime.title.isEmpty()) {
                    // TODO:收起键盘，键盘挡住snackbar
                    Snackbar.make(toolbar, "标题不能为空", Snackbar.LENGTH_LONG).show();
                    return false;
                }

                // 保存数据
                Intent intent = new Intent();
                Bundle bundle = new Bundle();
                bundle.putSerializable("time", myTime);
                intent.putExtras(bundle);
                AddTimeActivity.this.setResult(RESULT_OK, intent);

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

        myTime = new MyTime();
        // 初始化日期
        Calendar c = Calendar.getInstance();
        setDate(c.get(Calendar.YEAR), c.get(Calendar.MONTH) + 1, c.get(Calendar.DAY_OF_MONTH));
        showSelectedDate();
        // 初始化重复
        repeatDayTextView.setText(myTime.repeatDay.toString());

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
                if (myTime.repeatDay.type == RepeatDay.NONE) {
                    items.remove(items.size() - 1);
                }
                new AlertDialog.Builder(AddTimeActivity.this)
                        .setTitle("周期")
                        .setItems(items.toArray(new String[items.size()]), new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                if (which != RepeatDay.repeatDayItemType.indexOf(RepeatDay.CUSTOMIZE)) {
                                    myTime.repeatDay.setType(RepeatDay.repeatDayItemType.get(which));
                                    repeatDayTextView.setText(myTime.repeatDay.toString());
                                } else {
                                    // 自定义日期
                                    RepeatDayCustomizeDialog repeatDayCustomizeDialog = new RepeatDayCustomizeDialog(AddTimeActivity.this, new RepeatDayCustomizeDialog.DialogEventListener() {
                                        @Override
                                        public void DialogEvent(int day) {
                                            myTime.repeatDay.setCustomizeDay(day);
                                            repeatDayTextView.setText(myTime.repeatDay.toString());
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
        myTime.setDate(year, month, day);
    }

    private void showSelectedDate() {
        dateTextView.setText(myTime.date.year + "年" + myTime.date.month + "月" + myTime.date.day + "日");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // 设置菜单栏按钮图片
        getMenuInflater().inflate(R.menu.activity_add_time_confirm, menu);
        return true;
    }
}
