package com.example.casper.itime;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.example.casper.itime.data.model.MyTime;

import static com.example.casper.itime.MainActivity.setStatusBarTransparent;

public class TimeDetailActivity extends AppCompatActivity {
    //定义一个startActivityForResult（）方法用到的整型值
    private static final int requestCode = 1600;

    private int position;
    private MyTime myTime;
    private boolean isModified = false;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == this.requestCode) {
            if (resultCode == RESULT_OK) {
                isModified = true;
                Bundle bundle = data.getExtras();
                myTime = (MyTime) bundle.getSerializable("time");
                showDetail();
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.time_detail_activity);

        // 状态栏透明
        setStatusBarTransparent(this, R.id.time_detail_app_bar_layout);

        final Toolbar toolbar = this.findViewById(R.id.time_detail_tool_bar);
        setSupportActionBar(toolbar);
        // 工具栏内容设置
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle("");

        Intent intent = getIntent();

        position = intent.getIntExtra("position", -1);

        // 工具栏按钮
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 返回
                if(isModified){
                    Intent intent = new Intent();
                    Bundle bundle = new Bundle();
                    bundle.putInt("mode", HomeFragment.MODIFY_MODE);
                    bundle.putInt("position", position);
                    bundle.putSerializable("time", myTime);
                    intent.putExtras(bundle);
                    TimeDetailActivity.this.setResult(RESULT_OK, intent);
                }
                TimeDetailActivity.this.finish();
            }
        });
        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.delete_time: {
                        Intent intent = new Intent();
                        Bundle bundle = new Bundle();
                        bundle.putInt("mode", HomeFragment.DELETE_MODE);
                        bundle.putInt("position", position);
                        intent.putExtras(bundle);
                        TimeDetailActivity.this.setResult(RESULT_OK, intent);

                        TimeDetailActivity.this.finish();
                        break;
                    }
                    case R.id.share_time:
                        break;
                    case R.id.edit_time: {
                        Intent intent = new Intent(TimeDetailActivity.this, EditTimeActivity.class);
                        intent.putExtra("time", myTime);
                        intent.putExtra("mode", HomeFragment.MODIFY_MODE);
                        startActivityForResult(intent, requestCode);
                        break;
                    }
                }
                return true;
            }
        });

        myTime = (MyTime) intent.getSerializableExtra("time");

        showDetail();
    }

    private void showDetail() {
        ((TextView) this.findViewById(R.id.time_detail_title_text_view)).setText(myTime.title);
        if (myTime.title.isEmpty()) {
            ((TextView) this.findViewById(R.id.time_detail_remark_text_view)).setHeight(0);
        } else {
            ((TextView) this.findViewById(R.id.time_detail_remark_text_view)).setText(myTime.remark);
        }

        // TODO:倒计时
        ((TextView) this.findViewById(R.id.time_detail_countdown_text_view)).setText(myTime.date.year + "年" + myTime.date.month + "月" + myTime.date.day + "日");
        ((TextView) this.findViewById(R.id.time_detail_date_text_view)).setText(myTime.date.year + "年" + myTime.date.month + "月" + myTime.date.day + "日");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // 设置菜单栏按钮图片
        getMenuInflater().inflate(R.menu.activity_time_detail_options, menu);
        return true;
    }
}
