package com.example.casper.itime.util;

import android.content.Context;

import androidx.test.core.app.ApplicationProvider;

import com.example.casper.itime.data.model.MyTime;
import com.example.casper.itime.data.model.RepeatDay;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

import static org.junit.Assert.*;

public class MyTimeManagerTest {
    private Context context;
    private ArrayList<MyTime> myTimes;

    @Before
    public void setUp() throws Exception {
        context = ApplicationProvider.getApplicationContext();
        ObjectInputStream inputStream = new ObjectInputStream(context.openFileInput(MyTimeManager.fileName));
        myTimes = (ArrayList<MyTime>) inputStream.readObject();
        inputStream.close();
    }

    @After
    public void tearDown() throws Exception {
        ObjectOutputStream outputStream = new ObjectOutputStream(context.openFileOutput(MyTimeManager.fileName, Context.MODE_PRIVATE));
        outputStream.writeObject(myTimes);
        outputStream.close();
    }

    @Test
    public void save() throws IOException, ClassNotFoundException {
        ArrayList<MyTime> saveMyTimes = new ArrayList<>();
        MyTime myTime = new MyTime();
        myTime.title = "title";
        myTime.remark = "remark";
        myTime.setDate(2020, 3, 23);
        myTime.repeatDay.type = RepeatDay.WEEK;
        myTime.imageUriPath = "uri";
        saveMyTimes.add(myTime);

        MyTimeManager.save(context, saveMyTimes);

        ObjectInputStream inputStream = new ObjectInputStream(context.openFileInput(MyTimeManager.fileName));
        ArrayList<MyTime> loadMyTimes = (ArrayList<MyTime>) inputStream.readObject();

        assertEquals(saveMyTimes.size(), loadMyTimes.size());
        for (int i = 0; i < saveMyTimes.size(); i++) {
            MyTime s = saveMyTimes.get(i);
            MyTime l = loadMyTimes.get(i);
            assertEquals(s.title, l.title);
            assertEquals(s.remark, l.remark);
            assertEquals(s.date.year, l.date.year);
            assertEquals(s.date.month, l.date.month);
            assertEquals(s.date.day, l.date.day);
            assertEquals(s.repeatDay.type, l.repeatDay.type);
            assertEquals(s.repeatDay.customizeDay, l.repeatDay.customizeDay);
            assertEquals(s.imageUriPath, l.imageUriPath);
        }
    }

    @Test
    public void load() throws IOException {
        ArrayList<MyTime> saveMyTimes = new ArrayList<>();
        MyTime myTime = new MyTime();
        myTime.title = "title";
        myTime.remark = "remark";
        myTime.setDate(2020, 3, 23);
        myTime.repeatDay.type = RepeatDay.WEEK;
        myTime.imageUriPath = "uri";
        saveMyTimes.add(myTime);

        ObjectOutputStream outputStream = new ObjectOutputStream(context.openFileOutput(MyTimeManager.fileName, Context.MODE_PRIVATE));
        outputStream.writeObject(saveMyTimes);
        outputStream.close();

        ArrayList<MyTime> loadMyTimes =MyTimeManager.load(context);

        assertEquals(saveMyTimes.size(), loadMyTimes.size());
        for (int i = 0; i < saveMyTimes.size(); i++) {
            MyTime s = saveMyTimes.get(i);
            MyTime l = loadMyTimes.get(i);
            assertEquals(s.title, l.title);
            assertEquals(s.remark, l.remark);
            assertEquals(s.date.year, l.date.year);
            assertEquals(s.date.month, l.date.month);
            assertEquals(s.date.day, l.date.day);
            assertEquals(s.repeatDay.type, l.repeatDay.type);
            assertEquals(s.repeatDay.customizeDay, l.repeatDay.customizeDay);
            assertEquals(s.imageUriPath, l.imageUriPath);
        }
    }
}