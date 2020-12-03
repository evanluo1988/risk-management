package com.springboot.util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Date;
import java.util.Objects;

public class DateUtils {
    private static final String DATE_FORMAT = "yyyy-MM-dd";

    public static String convertStr(String format, Date date){
        DateFormat dateFormat = new SimpleDateFormat(format);
        return dateFormat.format(date);
    }

    public static String convertDateStr(Date date){
        return convertStr(DATE_FORMAT, date);
    }

    public static Date localDateTimeToDate(LocalDateTime localDateTime){
        ZoneId zoneId = ZoneId.systemDefault();
        ZonedDateTime zdt = localDateTime.atZone(zoneId);
        return Date.from(zdt.toInstant());
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

}
