package com.example.casper.itime.data;

import java.util.ArrayList;
import java.util.Arrays;

public class RepeatDay {
    public static final String WEEK = "week";
    public static final String MONTH = "month";
    public static final String YEAR = "year";
    public static final String CUSTOMIZE = "customize";
    public static final String NONE = "none";

    public static final ArrayList<String> repeatDayItemLabel = new ArrayList<>(Arrays.asList(new String[]{"每周", "每月", "每年", "自定义", "无"}));
    public static final ArrayList<String> repeatDayItemType = new ArrayList<>(Arrays.asList(new String[]{WEEK, MONTH, YEAR, CUSTOMIZE, NONE}));

    public String type;
    public int customizeDay;

    public RepeatDay() {
        type = NONE;
        customizeDay = 0;
    }


    public void setType(String type) {
        if (type == NONE) {
            customizeDay = 0;
        }
        this.type = type;
    }

    public void setCustomizeDay(int day) {
        if (day == 0) {
            setType(NONE);
        } else {
            setType(CUSTOMIZE);
            this.customizeDay = day;
        }
    }

    public String toString() {
        if (type == CUSTOMIZE) {
            return customizeDay + "天";
        } else {
            int i = repeatDayItemType.indexOf(type);
            return repeatDayItemLabel.get(i);
        }
    }
}
