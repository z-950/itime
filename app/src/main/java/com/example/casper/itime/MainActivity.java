package com.example.casper.itime;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import androidx.core.view.GravityCompat;
import androidx.appcompat.app.ActionBarDrawerToggle;

import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.example.casper.itime.util.ColorManager;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.navigation.NavigationView;

import androidx.drawerlayout.widget.DrawerLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    private static final int MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE = 283726352;

    private static final String HOME_TAG = "home";
    private static final String ABOUT_TAG = "about";

    private FragmentManager fragmentManager;
    private String currentTag;

    private Toolbar toolbar;
    private AppBarLayout barLayout;

    private int themeColor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);

        setStatusBarTransparent(this, R.id.main_app_bar_layout);

        // 初始化工具栏
        toolbar = findViewById(R.id.main_tool_bar);
        barLayout = findViewById(R.id.main_app_bar_layout);

        // 初始化抽屉
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);

        // 动态申请权限
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE);
            }
        }

        // 初始化fragment
        fragmentManager = getSupportFragmentManager();
        initFragments();
        showFragment(HOME_TAG);

        // 获取储存的颜色
        themeColor = ColorManager.load(this);
        if (themeColor == 0) {
            themeColor = 0xFF000000;
        }

        // 初始化主题颜色
        setThemeColor(themeColor);
    }

    void setThemeColor(int color) {
        themeColor = color;
        // 工具栏颜色
        toolbar.setBackgroundColor(color);
        barLayout.setBackgroundColor(color);
        // 按钮颜色
        ((HomeFragment) fragmentManager.findFragmentByTag(HOME_TAG)).setColor(color);
        // 保存到本地
        ColorManager.save(this, color);
    }

    public static void setStatusBarTransparent(Activity activity, int barLayoutId) {
        Window window = activity.getWindow();
        // 添加Flag把状态栏设为可绘制模式
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        // 添加设置Window半透明的Flag
        window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        // 设置系统状态栏处于可见状态
        window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_VISIBLE);
        // 调整高度
        fixToolBarHeight(activity, barLayoutId);
    }

    public static void fixToolBarHeight(Activity activity, int barLayoutId) {
        int height = getStatusBarHeight(activity);
        activity.findViewById(barLayoutId).setPadding(0, height, 0, 0);
    }

    public static int getStatusBarHeight(Context context) {
        Resources resources = context.getResources();
        int resourceId = resources.getIdentifier("status_bar_height", "dimen", "android");
        int height = resources.getDimensionPixelSize(resourceId);
        return height;
    }

    private void initFragments() {
        FragmentTransaction transaction = fragmentManager.beginTransaction();

        initAddFragment(transaction, new HomeFragment(), HOME_TAG);
        initAddFragment(transaction, new AboutFragment(), ABOUT_TAG);

        transaction.commitNow();
    }

    private void initAddFragment(FragmentTransaction transaction, Fragment fragment, String tab) {
        transaction.add(R.id.main_frame_layout, fragment, tab);
        transaction.hide(fragment);
    }

    private void showFragment(String tag) {
        if (tag == currentTag) {
            return;
        }

        Fragment fragmentOld = fragmentManager.findFragmentByTag(currentTag);
        Fragment fragmentNew = fragmentManager.findFragmentByTag(tag);

        if (fragmentNew != null) {
            FragmentTransaction transaction = fragmentManager.beginTransaction();
            transaction.show(fragmentNew);
            if (fragmentOld != null) {
                transaction.hide(fragmentOld);
            }
            transaction.commit();
            currentTag = tag;
        }
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.nav_home) {
            showFragment(HOME_TAG);
        } else if (id == R.id.nav_widget) {

        } else if (id == R.id.nav_theme) {
            ColorSelectDialog dialog = new ColorSelectDialog(MainActivity.this, themeColor, new ColorSelectDialog.DialogEventListener() {
                public void DialogEvent(int color) {
                    //在这里就获取到了从对话框传回来的值
                    setThemeColor(color);
                }
            });
            dialog.show();
        } else if (id == R.id.edit_time_confirm) {

        } else if (id == R.id.nav_settings) {
            Intent intent = new Intent(this, SettingsActivity.class);
            startActivity(intent);
        } else if (id == R.id.nav_about) {
            showFragment(ABOUT_TAG);
        } else if (id == R.id.nav_report) {
            // 从默认浏览器打开
            Uri uri = Uri.parse("https://github.com/z-950/itime/issues/new");
            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
            startActivity(intent);
        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
