package com.example.casper.itime.util;

import android.content.Context;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class ColorManager {
    private static final String fileName = "colorSerializable";

    static public void save(Context context, int color) {
        try {
            ObjectOutputStream outputStream = new ObjectOutputStream(context.openFileOutput(fileName, Context.MODE_PRIVATE));
            outputStream.writeInt(color);
            outputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    static public int load(Context context) {
        int color = 0;
        try {
            ObjectInputStream inputStream = new ObjectInputStream(context.openFileInput(fileName));
            color = inputStream.readInt();
            inputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return color;
    }
}
