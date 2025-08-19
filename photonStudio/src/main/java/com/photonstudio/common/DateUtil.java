package com.photonstudio.common;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtil {
	public static SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
	
    public static String currenttime = DateUtil.formatter.format(new Date());
    
    public static boolean authorize_date(String date){
        if(1 == compare_date(currenttime,date) || 0 == compare_date(currenttime,date)){
            return true;
        }
        return false;
    }


    public static int compare_date(String date1, String date2) {


        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        try {
        	
            Date dt1 = df.parse(date1);
            Date dt2 = df.parse(date2);
            System.out.println("dt1==="+date1);
            System.out.println("dt2==="+date2);
            if (dt1.getTime() > dt2.getTime()) {
                return -1;
            } else if (dt1.getTime() < dt2.getTime()) {
                return 1;
            } else {
                return 0;
            }
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        return 0;
    }
}
