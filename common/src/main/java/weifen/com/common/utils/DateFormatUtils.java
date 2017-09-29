package weifen.com.common.utils;

import android.text.TextUtils;

import java.text.SimpleDateFormat;

/**
 * Created by Administrator on 2017/9/18.
 */
public class DateFormatUtils {
    public static String getDate(long date,String format){
        if(TextUtils.isEmpty(format)){
            format="yyyy-MM-dd hh:mm:ss";
        }
        return new SimpleDateFormat(format).format(date).toString();
    }
}
