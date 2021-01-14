package com.springboot.utils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.temporal.TemporalUnit;
import java.util.Date;
import java.util.Objects;

public class DateUtils {
    private static final String DATE_FORMAT = "yyyy-MM-dd";

    public static String convertStr(String format, Date date){
        DateFormat dateFormat = new SimpleDateFormat(format);
        return dateFormat.format(date);
    }

    public static String convertDateStr(Date date){
        if(date == null){
            return null;
        }
        return convertStr(DATE_FORMAT, date);
    }

    public static Date localDateTimeToDate(LocalDateTime localDateTime){
        ZoneId zoneId = ZoneId.systemDefault();
        ZonedDateTime zdt = localDateTime.atZone(zoneId);
        return Date.from(zdt.toInstant());
    }

    public static Date localDateToDate(LocalDate localDate) {
        if(null == localDate) {
            return null;
        }
        ZonedDateTime zonedDateTime = localDate.atStartOfDay(ZoneId.systemDefault());
        return Date.from(zonedDateTime.toInstant());
    }

    public static String convertDateStr(LocalDateTime localDateTime){
        return convertDateStr(localDateTimeToDate(localDateTime));
    }

    public static LocalDateTime dateToLocalDateTime(Date date){
        if(Objects.isNull(date)){
            return null;
        }
        return date.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
    }

    public int disDateFrom(LocalDate date) {
        return LocalDate.now().getYear() - date.getYear();
    }

    public static Date currentDate() {
       return localDateToDate(LocalDate.now());
    }

    public static Date currentDateTime() {
        return localDateTimeToDate(LocalDateTime.now());
    }

    public static LocalDateTime addMinute(Long minutes){
        return LocalDateTime.now().plusMinutes(minutes);
    }

}
