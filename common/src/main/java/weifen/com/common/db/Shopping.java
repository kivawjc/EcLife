package weifen.com.common.db;

import cn.bmob.v3.BmobObject;
import cn.bmob.v3.datatype.BmobGeoPoint;

/**
 * Created by zhurencong on 2017/9/21.
 */
public class Shopping extends BmobObject{

    //添加
    //注意：number是该账号绑定的电话，shopNumber是发布商品时的电话
    private String iconUrl,type,smallType,title,time,price,number,shopNumber,distance;
    //保存经纬度
    private BmobGeoPoint geoPoint;

    public String getIconUrl() {
        return iconUrl;
    }

    public void setIconUrl(String iconUrl) {
        this.iconUrl = iconUrl;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getSmallType() {
        return smallType;
    }

    public void setSmallType(String smallType) {
        this.smallType = smallType;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public BmobGeoPoint getGeoPoint() {
        return geoPoint;
    }

    public void setGeoPoint(BmobGeoPoint geoPoint) {
        this.geoPoint = geoPoint;
    }

    public String getShopNumber() {
        return shopNumber;
    }

    public void setShopNumber(String shopNumber) {
        this.shopNumber = shopNumber;
    }

    public String getDistance() {
        return distance;
    }

    public void setDistance(String distance) {
        this.distance = distance;
    }
}
