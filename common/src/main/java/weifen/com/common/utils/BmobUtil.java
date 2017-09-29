package weifen.com.common.utils;

import android.content.Context;

import java.util.List;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobConfig;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.listener.FindListener;
import weifen.com.common.app.BaseApplication;
import weifen.com.common.db.Shopping;

/**
 * Created by zhurencong on 2017/9/17.
 */
public class BmobUtil {

    //初始化bmob
    public static void initBmob(Context context){

        BmobConfig config =new BmobConfig.Builder(context)
        //设置appkey
        .setApplicationId(BaseApplication.applicationID)
        //请求超时时间（单位为秒）：默认15s
        .setConnectTimeout(30)
        //文件分片上传时每片的大小（单位字节），默认512*1024
        .setUploadBlockSize(1024*1024)
        //文件的过期时间(单位为秒)：默认1800s
        .setFileExpiration(2500)
        .build();
        Bmob.initialize(config);
    }

    //查询Person表
    public static void queryShopping(final Context context,final ShoppingCallback callback){
        BmobUtil.initBmob(context);
        BmobQuery<Shopping> bmobQuery=new BmobQuery<Shopping>();
        bmobQuery.findObjects(new FindListener<Shopping>() {
            @Override
            public void done(List<Shopping> list, cn.bmob.v3.exception.BmobException e) {
                if(e==null){
                    callback.personOk(list);
                }
            }
        });
    }

    public interface ShoppingCallback{
        void personOk(List<Shopping> list);
    }

}
