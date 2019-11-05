package com.example.casper.itime;

import android.os.Bundle;

import androidx.core.view.GravityCompat;
import androidx.appcompat.app.ActionBarDrawerToggle;

import android.view.MenuItem;

import com.google.android.material.navigation.NavigationView;

import androidx.drawerlayout.widget.DrawerLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    private static final String HOME_TAG = "home";
    private static final String ABOUT_TAG = "about";


    private FragmentManager fragmentManager;
    private String currentTag;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.tool_bar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);

        fragmentManager = getSupportFragmentManager();
        initRoutes();
        showRoute(HOME_TAG);
    }

    private void initRoutes() {
        FragmentTransaction transaction = fragmentManager.beginTransaction();

        initAddRoute(transaction, new HomeFragment(), HOME_TAG);
        initAddRoute(transaction, new AboutFragment(), ABOUT_TAG);

        transaction.commitNow();
    }

    private void initAddRoute(FragmentTransaction transaction, Fragment fragment, String tab) {
        transaction.add(R.id.main_frame_layout, fragment, tab);
        transaction.hide(fragment);
    }

    private void showRoute(String tag) {
        if (tag == currentTag) {
            return;
        }

        Fragment fragmentOld = fragmentManager.findFragmentByTag(currentTag);
        Fragment fragmentNew = fragmentManager.findFragmentByTag(tag);

        if (fragmentNew != null) {
            FragmentTransaction transaction = fragmentManager.beginTransaction();
            if (fragmentOld != null) {
                transaction.hide(fragmentOld);
            }
            transaction.show(fragmentNew).commit();
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
            showRoute(HOME_TAG);
        } else if (id == R.id.nav_widget) {

        } else if (id == R.id.nav_theme) {

        } else if (id == R.id.nav_lock) {

        } else if (id == R.id.nav_settings) {

        } else if (id == R.id.nav_about) {
            showRoute(ABOUT_TAG);
        } else if (id == R.id.nav_report) {

        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
