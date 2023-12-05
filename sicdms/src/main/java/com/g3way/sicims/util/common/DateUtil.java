package com.g3way.sicims.util.common;


import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import com.g3way.sicims.exception.SicimsException;


/**
 * 날짜 관련 유틸리티 class
 */
public class DateUtil {

    //** RAW 4자리 년도 날짜+시간 *//*
    public static final String RAW_LONG_FULL = "yyyyMMddkkmmss";


    /**
     * 2개의 시간의 차이를 계산하여 설정된 시간보다 큰지 작은지 여부 판단
     *
     * @param left 비교될 시간
     * @param right 비교할 시간
     * @param interval 지연 시간 (milli-sec)
     * @return interval보다 시간이 지났으면 true, otherwise false
     */
    public static boolean checkInterval(Date left, Date right, long interval) {

        return checkInterval(left.getTime(), right.getTime(), interval);
    }


    /**
     * 오늘과 대상 시간의 차이를 계산하여 설정된 시간보다 큰지 작은지 여부 판단
     *
     * @param date 대상 시간
     * @param interval 지연 시간 (milli-sec)
     * @return interval보다 시간이 지났으면 true, otherwise false
     */
    public static boolean checkInterval(Date date, long interval) {

        return checkInterval(date, new Date(), interval);
    }

    /**
     * 2개의 시간의 차이를 계산하여 설정된 시간보다 큰지 작은지 여부 판단
     *
     * @param left 비교될 시간
     * @param right 비교할 시간
     * @param interval 지연 시간 (milli-sec)
     * @return interval보다 시간이 지났으면 ture, otherwise false
     */
    public static boolean checkInterval(long left, long right, long interval) {

        if (right - left > interval)
            return true;

        return false;
    }


    /**
     * 오늘 날짜와 <code>date</code>를 비교하여 같은 경우는 0, 오늘 날짜가 <code>date</code>보다
     * 이후인 경우 양수, 오늘 날짜가 <code>date</code>보다 이전인 경우 음수를 리턴한다.
     *
     * @param date 비교 대상 날짜
     * @return 같은경우 0, 오늘보다 이후인 경우 양수, 그외 음수
     */
    public static int compareDate(long date) {

        Date leftDate = new Date();
        Date rightDate = new Date(date);
        return leftDate.compareTo(rightDate);
    }


    /**
     * <code>left</code>와 <code>right</code>를 비교하여 같은 경우는 0,
     * <code>left</code>가 <code>date</code>보다 이후인 경우 양수, <code>left</code>가
     * <code>date</code>보다 이전인 경우 음수를 리턴한다.
     *
     * @param left 날짜 1
     * @param right 날짜 2
     * @return 같은경우 0, 왼쪽이 이후인 경우 양수, 그외 음수
     */
    public static int compareDate(long left, long right) {

        Date leftDate = new Date(left);
        Date rightDate = new Date(right);
        return leftDate.compareTo(rightDate);
    }


    /**
     * 오늘 날짜와 <code>date</code>를 비교하여 같은 경우는 0, 오늘 날짜가 <code>date</code>보다
     * 이후인 경우 양수, 오늘 날짜가 <code>date</code>보다 이전인 경우 음수를 리턴한다.
     *
     * @param date 비교 대상 날짜
     * @param pattern 파싱할 형식
     * @return 같은경우 0, 오늘보다 이후인 경우 양수, 그외 음수
     */
    public static int compareDate(String date, String pattern) {

        Date leftDate = new Date();
        Date rightDate = parse(date, pattern);
        return leftDate.compareTo(rightDate);
    }


    /**
     * <code>left</code>와 <code>right</code>를 비교하여 같은 경우는 0,
     * <code>left</code>가 <code>date</code>보다 이후인 경우 양수, <code>left</code>가
     * <code>date</code>보다 이전인 경우 음수를 리턴한다.
     *
     * @param left 날짜 1
     * @param right 날짜 2
     * @param pattern 파싱할 형식
     * @return 같은경우 0, 왼쪽이 이후인 경우 양수, 그외 음수
     */
    public static int compareDate(String left, String right, String pattern) {

        Date leftDate = parse(left, pattern);
        Date rightDate = parse(right, pattern);
        if(leftDate != null) {
        	return leftDate.compareTo(rightDate);
        }
        else {
        	throw new SicimsException("날짜 파싱중 오류가 발생하여 날짜를 비교할수 없습니다.");
        }
    }


    /**
     * 날짜 차이를 구하는 함수
     *
     * @param begin
     * @param end
     * @return 날짜 차이
     * @throws ParseException
     */
    public static long diffOfDate(String begin, String end) throws ParseException {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd", Locale.ENGLISH);
        Date beginDate = formatter.parse(begin);
        Date endDate = formatter.parse(end);
        long diff = endDate.getTime() - beginDate.getTime();
        long diffDays = diff / (24 * 60 * 60 * 1000);
        return diffDays;
    }


    /**
     * 현재날짜와 날짜 차이를 구하는 함수
     *
     * @param begin
     * @return 현재날짜와 날짜 차이
     * @throws ParseException
     */
    public static long diffOfNowDate(String begin) throws ParseException {
        return diffOfDate(begin, format(new Date(), "yyyyMMdd"));
    }


    /**
     * 해당 날짜를 해당 패턴으로 변환
     *
     * @param date 해당 날짜
     * @param pattern 패턴
     * @return 해당 날짜가 지정된 패턴으로 String화 된 값
     */
    public static String format(Date date, String pattern) {

        SimpleDateFormat df = new SimpleDateFormat(pattern, Locale.ENGLISH);

        if (date != null) {
            return df.format(date);
        }

        return "";
    }


    /**
     * 해당 날짜를 해당 패턴으로 변환
     *
     * @param date 해당 날짜
     * @param pattern 패턴
     * @return 해당 날짜가 지정된 패턴으로 String화 된 값
     */
    public static String format(Date date, String pattern, Locale locale) {

        SimpleDateFormat df = new SimpleDateFormat(pattern, locale);

        if (date != null) {
            return df.format(date);
        }

        return "";
    }

    /**
     * 해당 날짜를 해당 패턴으로 변환
     *
     * @param date 해당 날짜
     * @param pattern 패턴
     * @return 해당 날짜가 지정된 패턴으로 String화 된 값
     */
    public static String format(long date, String pattern) {

        return format(new Date(date), pattern);
    }


    /**
     * 해당 날짜를 패턴으로 변환하여 스트링 리턴
     *
     * @param date 해당 날짜
     * @param pattern 출력 패턴
     * @return 해당 날짜가 지정된 패턴으로 String화 된 값
     */
    public static String format(Object date, String pattern) {
        return format((Date) date, pattern);
    }


    /**
     * 오늘 날짜를 해당 패턴으로 변환
     *
     * @param pattern 패턴
     * @return 오늘 날짜가 지정된 패턴으로 String화 된 값
     */
    public static String format(String pattern) {

        return format(new Date(), pattern);
    }


    /**
     * 해당 날짜를 패턴으로 변환하여 스트링 리턴
     *
     * @param date 해당 날짜
     * @param pattern 출력 패턴
     * @return 해당 날짜가 지정된 패턴으로 String화 된 값
     */
    public static String format(String date, String pattern) {

        return format(date, DateUtil.RAW_LONG_FULL, pattern);
    }


    /**
     * 해당 날짜를 지정된 패턴으로 변환
     *
     * @param date 해당 날짜
     * @param parsePattern 파싱할 패턴
     * @param formatPattern 출력될 패턴
     * @return 해당 날짜가 지정된 패턴으로 String화 된 값
     */
    public static String format(String date, String parsePattern, String formatPattern) {

        return format(parse(date, parsePattern), formatPattern);
    }

    /**
     * 해당 날짜를 지정된 패턴으로 변환
     *
     * @param date 해당 날짜
     * @param parsePattern 파싱할 패턴
     * @param formatPattern 출력될 패턴
     * @return 해당 날짜가 지정된 패턴으로 String화 된 값
     */
    public static String format(String date, String parsePattern, String formatPattern, Locale locale) {

        return format(parse(date, parsePattern, locale), formatPattern, locale);
    }

    /**
     * String 타입의 날짜를 지정한 형식의 Date 타입의 날짜로 반환한다.
     *
     * @param strDate String 타입의 날짜
     * @param pFormat SimpleDateFormat에 정의된 날짜형식
     * @return 변경된 날짜
     * @throws ParseException
     */
    public static Date getDate(String strDate, String pFormat) throws ParseException {

        if (strDate == null)
            return null;

        return new SimpleDateFormat(pFormat, Locale.ENGLISH).parse(strDate, new ParsePosition(0));
    }


    /**
     * 해당 년월의 첫날의 요일을 구한다.
     *
     * @param year
     * @param month
     * @return 해당 년월의 첫날의 요일
     */
    public static int getFirstDayWeek(String year, String month) {

        int dayOfWeek = 0;

        Calendar cal = Calendar.getInstance();
        cal.set(Integer.parseInt(year), Integer.parseInt(month) - 1, 1);
        dayOfWeek = cal.get(Calendar.DAY_OF_WEEK);

        return dayOfWeek;
    }


    /**
     * @param start
     * @param end
     * @return Interval
     */
    public static int getInterval(Date start, Date end) {

        long hr = 60 * 60 * 1000L;
        int returnvalue = 0;
        try {
        	returnvalue = Math.toIntExact((end.getTime() - start.getTime() + hr) / (hr * 24));
        }catch (ArithmeticException e) {
        	returnvalue = 0;
		}catch (Exception e) {
        	returnvalue = 0;
		}
        return returnvalue;
    }


    /**
     * 해당 달의 마지막 날을 계산해준다.
     *
     * @param year
     * @param month
     * @return 해당 달의 마지막 날
     */
    public static int getLastDayWeek(String year, String month) {

        int ret = 0;
        Calendar cal = Calendar.getInstance();
        cal.set(Integer.parseInt(year), Integer.parseInt(month) - 1, 1);

        ret = cal.getMaximum(Calendar.DAY_OF_MONTH);

        return ret;
    }


    /**
     * 3개월전 날짜 가져오기
     *
     * @param yyyymmdd
     * @param gap
     * @return 3개월전 날짜
     */
    public static String getLaterDate(String yyyymmdd, String gap) {

        Calendar cal = Calendar.getInstance();

        cal.set(Integer.parseInt(yyyymmdd.substring(0, 4)), Integer.parseInt(yyyymmdd.substring(4, 6)), Integer.parseInt(yyyymmdd.substring(6)));
        cal.add(Calendar.YEAR, 0);
        cal.add(Calendar.MONTH, -Integer.parseInt(gap) - 1);
        cal.add(Calendar.DATE, 0);

        String days = String.valueOf(cal.get(Calendar.DATE));
        if (days.length() < 2)
            days = "0" + days;
        String month = String.valueOf(cal.get(Calendar.MONTH) + 1);
        if (month.length() < 2)
            month = "0" + month;
        String year = String.valueOf(cal.get(Calendar.YEAR));
        String date = year + month + days;
        // return Integer.parseInt(date);
        return date;
    }


    /**
     * 정해진 날짜로 부터 지정 날짜까지 토요일, 일요일을 뺀 날짜 일수 return
     *
     * @param dates
     * @param gap
     * @return 정해진 날짜로 부터 지정 날짜까지 토요일, 일요일을 뺀 날짜 일수
     */
    public static int getWeekDays(String dates, int gap)throws ParseException {
        int remain = 0;
        int division = 0;
        int resultDay = 0;
        int weekend = 0;

        try {
            SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd", Locale.ENGLISH);
            Date d = formatter.parse(dates);

            Calendar cal = Calendar.getInstance();
            cal.setTime(d);

            int dayNum = cal.get(Calendar.DAY_OF_WEEK);
            // 1 :일요일, 2:월요일, 3:화요일, 4:수요일, 5:목요일, 6:금요일, 7:토요일

            remain = gap % 7;
            division = gap / 7;

            if (remain > 0) {
                int days = dayNum + remain;

                if (days >= 7) {
                    days = days - 7;
                    weekend = weekend - 1;

                    if (days >= 1) {
                        weekend = weekend - 1;
                    }
                }
            }

            resultDay = gap - division * 2 + weekend;

        }
        catch (ParseException e) {
           return -1;
        }

        return resultDay;
    }


    /**
     * 날짜를 문자열로 받아들여 지정하는 패턴으로 파싱하여 Date형식으로 리턴한다.
     *
     * @param date 해당 날짜
     * @param pattern 파싱할 패턴
     * @return 해당 날짜가 지정된 패턴으로 Date화 된 값
     */
    public static Date parse(String date, String pattern) {

        SimpleDateFormat df = new SimpleDateFormat(pattern, Locale.ENGLISH);
        Date dateObj = null;
        try {
            dateObj = df.parse(date);
        } catch (ParseException e) {
        	dateObj = null;
        }

        return dateObj;
    }

    /**
     * 날짜를 문자열로 받아들여 지정하는 패턴으로 파싱하여 Date형식으로 리턴한다.
     *
     * @param date 해당 날짜
     * @param pattern 파싱할 패턴
     * @return 해당 날짜가 지정된 패턴으로 Date화 된 값
     */
    public static Date parse(String date, String pattern, Locale locale) {

        SimpleDateFormat df = new SimpleDateFormat(pattern, locale);
        Date dateObj = null;
        try {
            dateObj = df.parse(date);
        } catch (ParseException e) {
        	dateObj = null;
        }

        return dateObj;
    }


    /**
     * 그해 2월의 마지막 날을 계산해준다.
     *
     * @param iYear
     * @return 그해 2월의 마지막 날
     */
    public static int Results2Month(int iYear) {

        int ret = 0;
        ret = ((iYear % 4) == 0 && (iYear % 100) != 0 || (iYear % 400) == 0) ? 29 : 28;
        return ret;
    }

    /**
     * iDate에서 iMonth 만큼 빼거나 더한 달수의 날짜를 구한다.
     * @param iDate
     * @param iMonth
     * @param yyyyMMdd
     * @return
     */
    public static String getDate(String iDate, int iMonth) {
        String yyyy = "";
        String mm = "";
        String dd = "";
        int iYyyy = 0;
        int iMm = 0;
        int iDd = 0;

        if(iDate.length()<8){
        	return "";
        }
        iYyyy = Integer.parseInt(iDate.substring(0, 4));
        iMm = Integer.parseInt(iDate.substring(4, 6));
        iDd = Integer.parseInt(iDate.substring(6, 8));

        Calendar cal = Calendar.getInstance();
        cal.set(iYyyy, iMm-1 , iDd);
        cal.add ( Calendar.MONTH, iMonth);

        yyyy = Integer.toString(cal.get(Calendar.YEAR));
        mm = Integer.toString(cal.get(Calendar.MONTH)+1);
        dd = Integer.toString(cal.get(Calendar.DATE));

        if(mm.length()==1){
        	mm = "0"+mm;
        }
        if(dd.length()==1){
        	dd = "0"+dd;
        }

        return yyyy+mm+dd;
    }

}
