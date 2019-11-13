package com.example.casper.itime.util;

import android.content.Context;

import com.example.casper.itime.data.model.MyTime;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

public class MyTimeManager {
    public static final String fileName = "myTimeSerializable";

    static public void save(Context context, ArrayList<MyTime> myTimes) {
        try {
            ObjectOutputStream outputStream = new ObjectOutputStream(context.openFileOutput(fileName, Context.MODE_PRIVATE));
            outputStream.writeObject(myTimes);
            outputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    static public ArrayList<MyTime> load(Context context) {
        ArrayList<MyTime> myTimes = new ArrayList<>();
        try {
            ObjectInputStream inputStream = new ObjectInputStream(context.openFileInput(fileName));
            myTimes = (ArrayList<MyTime>) inputStream.readObject();
            inputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return myTimes;
    }
}
