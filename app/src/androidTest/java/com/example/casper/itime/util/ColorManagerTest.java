package com.example.casper.itime.util;

import android.content.Context;

import androidx.test.core.app.ApplicationProvider;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class ColorManagerTest {
    private Context context;
    private int color;

    @Before
    public void setUp() throws Exception {
        context = ApplicationProvider.getApplicationContext();
        ObjectInputStream inputStream = new ObjectInputStream(context.openFileInput(ColorManager.fileName));
        color = inputStream.readInt();
        inputStream.close();
    }

    @After
    public void tearDown() throws Exception {
        ObjectOutputStream outputStream = new ObjectOutputStream(context.openFileOutput(ColorManager.fileName, Context.MODE_PRIVATE));
        outputStream.writeInt(color);
        outputStream.close();
    }

    @Test
    public void save() throws IOException {
        int saveColor = 0xf87d8e9a;

        ColorManager.save(context, saveColor);

        ObjectInputStream inputStream = new ObjectInputStream(context.openFileInput(ColorManager.fileName));
        int loadColor = inputStream.readInt();
        Assert.assertEquals(saveColor, loadColor);
    }

    @Test
    public void load() throws IOException {
        int saveColor = 0xd87d8e9a;
        ObjectOutputStream outputStream = new ObjectOutputStream(context.openFileOutput(ColorManager.fileName, Context.MODE_PRIVATE));
        outputStream.writeInt(saveColor);
        outputStream.close();

        int loadColor = ColorManager.load(context);
        Assert.assertEquals(saveColor, loadColor);
    }
}