package com.example.casper.itime;

import android.content.res.ColorStateList;
import android.os.Bundle;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

public class HomeFragment extends Fragment {
    FloatingActionButton fab;
    int color = 0;
    boolean colorChanged = false;
    boolean setable = false;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        fab = view.findViewById(R.id.add_fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
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
