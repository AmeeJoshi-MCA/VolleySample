package com.lithe.service.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;

public class DateTimeUtils {

    private Calendar calendar;

    private String dayString, monthString, yearString, hour24String, hour12String, minuteString, secondString, stringAmPm;
    private int dayNumber, monthNumber, yearNumber, hour24Number, hour12Number, minuteNumber, secondNumber;

    public static final String[] monthsHalfNames = {"Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"};
    public static final String[] monthsFullNames = {"January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"};
    public static final String[] dayFullNames = {"Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday"};

    public DateTimeUtils(Calendar calendar) {
        this.calendar = calendar;

        dayNumber = calendar.get(Calendar.DAY_OF_MONTH);

        if (dayNumber < 10) {
            dayString = "0" + dayNumber;
        } else {
            dayString = "" + dayNumber;
        }

        monthNumber = calendar.get(Calendar.MONTH);

        monthNumber += 1;
        if (monthNumber < 10) {
            monthString = "0" + monthNumber;
        } else {
            monthString = "" + monthNumber;
        }

        yearNumber = calendar.get(Calendar.YEAR);

        yearString = yearNumber + "";

        hour24Number = calendar.get(Calendar.HOUR_OF_DAY);

        if (hour24Number < 10) {
            hour24String = "0" + hour24Number;
        } else {
            hour24String = "" + hour24Number;
        }

        hour12Number = calendar.get(Calendar.HOUR);

        if (hour12Number < 10) {
            hour12String = "0" + hour12Number;
        } else {
            hour12String = "" + hour12Number;
        }

        if (hour12Number == 0) {
            hour12String = "12";
        }

        minuteNumber = calendar.get(Calendar.MINUTE);

        if (minuteNumber < 10) {
            minuteString = "0" + minuteNumber;
        } else {
            minuteString = "" + minuteNumber;
        }

        secondNumber = calendar.get(Calendar.SECOND);

        if (secondNumber < 10) {
            secondString = "0" + secondNumber;
        } else {
            secondString = "" + secondNumber;
        }

        int AmPm = calendar.get(Calendar.AM_PM);

        if (AmPm == Calendar.AM) {
            stringAmPm = "AM";
        } else if (AmPm == Calendar.PM) {
            stringAmPm = "PM";
        } else {
            stringAmPm = "";
        }

    }

    public static String getMonthName(int numberOfMonth) {
        if (numberOfMonth > 0 && numberOfMonth <= 12) {
            return monthsHalfNames[numberOfMonth - 1];
        }
        return "???";
    }

    public static String getOrdinal(int value) {
        String[] fixes = new String[]{"th", "st", "nd", "rd", "th", "th", "th", "th", "th", "th"};
        if (value % 100 > 9 && value % 100 < 21) {
            return value + fixes[0];
        } else {
            return value + fixes[value % 10];
        }
    }

    public String getDay() {
        return dayString;
    }

    public String getMonth() {
        return monthString;
    }

    public String getYear() {
        return yearString;
    }

    public String getHour24() {
        return hour24String;
    }

    public String getHour12() {
        return hour12String;
    }

    public String getMinute() {
        return minuteString;
    }

    public String getSecond() {
        return secondString;
    }

    public int getDayNumber() {
        return dayNumber;
    }

    public void setDayNumber(int dayNumber) {
        this.dayNumber = dayNumber;
    }

    public int getMonthNumber() {
        return monthNumber;
    }

    public void setMonthNumber(int monthNumber) {
        this.monthNumber = monthNumber;
    }

    public int getYearNumber() {
        return yearNumber;
    }

    public void setYearNumber(int yearNumber) {
        this.yearNumber = yearNumber;
    }

    public int getHour24Number() {
        return hour24Number;
    }

    public void setHour24Number(int hour24Number) {
        this.hour24Number = hour24Number;
    }

    public int getHour12Number() {
        return hour12Number;
    }

    public void setHour12Number(int hour12Number) {
        this.hour12Number = hour12Number;
    }

    public int getMinuteNumber() {
        return minuteNumber;
    }

    public void setMinuteNumber(int minuteNumber) {
        this.minuteNumber = minuteNumber;
    }

    public int getSecondNumber() {
        return secondNumber;
    }

    public void setSecondNumber(int secondNumber) {
        this.secondNumber = secondNumber;
    }

    public String getAmPm() {
        return stringAmPm;
    }

    public Calendar getCalendar() {
        return calendar;
    }

    public String getFormattedReadableDate() {
        String day = getOrdinal(getDayNumber());
        String month = getMonthName(getMonthNumber());
        return day + " " + month + " " + getYear();
    }

    public String getMonthName() {
        return getMonthName(getMonthNumber());
    }

    public String getFormattedReadableTime() {
        return getHour12() + ":" + getMinute() + " " + getAmPm();
    }

    public String getFormattedReadableTimeWithSeconds() {
        return getHour12() + ":" + getMinute() + ":" + getSecond() + " " + getAmPm();
    }

    public String getFormattedReadableTime24() {
        return getHour24() + ":" + getMinute();
    }

    public String getFormattedReadableTime24WithSeconds() {
        return getHour24() + ":" + getMinute() + ":" + getSecond();
    }

    public String getFileNameTimeStamp12() {
        return getDay() + "_" + getMonth() + "_" + getYear() + "_" + getHour12() + "_" + getMinute() + "_" + getSecond() + "_" + getAmPm();
    }

    public String getFileNameTimeStamp24() {
        return getDay() + "_" + getMonth() + "_" + getYear() + "_" + getHour24() + "_" + getMinute() + "_" + getSecond();
    }

    public String getDBFormatDate() {
        return getYear() + "-" + getMonth() + "-" + getDay();
    }

    public String getDMYFormatDate() {
        return getDay() + "-" + getMonth() + "-" + getYear();
    }

    public String getDBFormatTime12() {
        return getHour12() + ":" + getMinute() + ":" + getSecond();
    }

    public String getDBFormatTime24() {
        return getHour24() + ":" + getMinute() + ":" + getSecond();
    }

    public String getMonthFullName() {
        return monthsFullNames[getMonthNumber() - 1];
    }

    public String getDayFullName() {
        return dayFullNames[getCalendar().get(Calendar.DAY_OF_WEEK) - 1];
    }

    public Date getDate() {
        return getCalendar().getTime();
    }

    public static DateTimeUtils getParseDateTime(String format, String date) {
        DateTimeUtils dateTimeUtils = null;
        try {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format, Locale.ENGLISH);
            Date mDate = simpleDateFormat.parse(date);
            Calendar calendar = new GregorianCalendar();
            calendar.setTime(mDate);
            dateTimeUtils = new DateTimeUtils(calendar);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return dateTimeUtils;
    }

    public static Date getParseDate(String format, String stringDate) {
        Date date = null;
        try {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format, Locale.ENGLISH);
            date = simpleDateFormat.parse(stringDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }

    public static Date getYMDParseDateFromDateTimeObject(DateTimeUtils dateTimeUtils) {
        Date date = null;
        String stringDate = dateTimeUtils.getYear() + "-" + dateTimeUtils.getMonth() + "-" + dateTimeUtils.getDay();
        try {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
            date = simpleDateFormat.parse(stringDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }

    public static Date getHMParseTimeFromDateTimeObject(DateTimeUtils dateTimeUtils) {
        Date date = null;
        String stringTime = dateTimeUtils.getHour12() + ":" + dateTimeUtils.getMinute() + " " + dateTimeUtils.getAmPm();
        try {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("hh:mm a", Locale.ENGLISH);
            date = simpleDateFormat.parse(stringTime);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }

    public static Date getYMDHMParseDateTimeFromDateTimeObject(DateTimeUtils dateTimeObjectDATE, DateTimeUtils dateTimeObjectTIME) {
        Date date = null;
        try {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm a", Locale.ENGLISH);
            date = simpleDateFormat.parse(dateTimeObjectDATE.getDBFormatDate() + " " + dateTimeObjectTIME.getFormattedReadableTime());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }

}
