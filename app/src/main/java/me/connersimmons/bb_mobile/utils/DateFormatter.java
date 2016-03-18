package me.connersimmons.bb_mobile.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by connersimmons on 03.15.16.
 */
public class DateFormatter {

    private static String DATE_PATTERN = "M-d-yyyy hh:mm";
    private static String DATE_TIME_PATTERN = "M-d-yyyy hh:mm";

    public static Date convertStringToDateTime(String dateString, String timeString){

        String toParse = dateString + " " + timeString;
        SimpleDateFormat format = new SimpleDateFormat(DATE_TIME_PATTERN);
        Date date = null;
        try {
            date = format.parse(toParse);
            System.out.println(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return date;
    }

    public static Date convertStringToDate(String dateString){

        SimpleDateFormat format = new SimpleDateFormat(DATE_PATTERN);
        Date date = null;
        try {
            date = format.parse(dateString);
            System.out.println(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return date;
    }
}
