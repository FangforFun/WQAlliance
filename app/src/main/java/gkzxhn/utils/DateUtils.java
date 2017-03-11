package gkzxhn.utils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Author: Huang ZN
 * Date: 2017/3/10
 * Email:943852572@qq.com
 * Description:
 */

public class DateUtils {

    /**
     * 返回时间字符串 若超过24小时则带日期  不超过24小时只返回hh:mm
     * @param ms
     * @return
     */
    public static String getTimeString(long ms){
        long current = System.currentTimeMillis();
        long day = 1000L * 60L * 60L * 24L;// 一天
        long twoDay = day * 2L;// 两天
        long threeDay = day * 3L;// 三天
        String format = "HH:mm";
        long timeDiff = current - ms;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format, Locale.getDefault());
        Date date = new Date(ms);
        if(timeDiff >= 0 && timeDiff < day){
            // 一天内显示HH:mm
            return simpleDateFormat.format(date);
        }else if(timeDiff >= day && timeDiff < twoDay){
            // 大于一天小于两天显示 昨天 HH:mm
            return "昨天 " + simpleDateFormat.format(date);
        }else if(timeDiff >= twoDay && timeDiff < threeDay){
            // 大于两天小于三天显示 前天 HH:mm
            return "前天 " + simpleDateFormat.format(date);
        }else {
            // 超过三天显示具体日期
            format = "yyyy-MM-dd HH:mm";
            simpleDateFormat = new SimpleDateFormat(format, Locale.getDefault());
            return simpleDateFormat.format(date);
        }
    }

    /**
     * 格式化时间
     * @param time  时间毫秒值
     * @param pattern 格式
     * @return 返回格式化后的时间
     */
    public static String formatTime(long time, String pattern){
        Date date = new Date(time);
        SimpleDateFormat format = new SimpleDateFormat(pattern, Locale.getDefault());
        return format.format(date);
    }
}