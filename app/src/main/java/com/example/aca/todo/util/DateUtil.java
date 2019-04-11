package com.example.aca.todo.util;

import android.arch.persistence.room.TypeConverter;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public final class DateUtil {
    @TypeConverter
    public static String formatDateToString (Date date){

        return DateFormat.getDateInstance().format(date);
    }

    @TypeConverter
    public static String formatTimeToString(Date date){

        SimpleDateFormat dt = new SimpleDateFormat("HH:mm");

        return dt.format(date.getTime());
    }

    @TypeConverter
    public static Date formatStringToDate(String string) throws ParseException {

        return DateFormat.getDateInstance().parse(string);
    }

}
