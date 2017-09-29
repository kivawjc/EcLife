package weifen.com.eclife.activity;

import android.Manifest;
import android.annotation.TargetApi;
import android.content.ContentUris;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.datatype.BmobGeoPoint;
import weifen.com.common.base.BaseActivity;
import weifen.com.common.utils.ActivityCollector;
import weifen.com.common.utils.FlowLayout;
import weifen.com.eclife.R;
import weifen.com.eclife.app.MyApplication;
import weifen.com.eclife.domain.Constant;

/**
 * Created by zhurencong on 2017/9/20.
 */
public class PublishActivity extends BaseActivity implements View.OnClickListener {

    private ImageView back,draftBox,shoppingImage,imageButton,relocation;
    private EditText title,description,contactName,shopNumber,price;
    private LinearLayout label,address,addressIsVisiable;
    private TextView locationAddress,userDefine,publish,fromAlbum,fromCamera,cancel;
    private FlowLayout flowLayout;
    private int mark=R.id.address,whichType;
    private PopupWindow popup;
    private RelativeLayout rootview;
    private final int TAKE_PHOTO=1,TAKE_PITURE=2;
    private Uri imageUri;
    private String imagePath;
    private ViewGroup.MarginLayoutParams lp;
    private String[]arrays;
    private int length,flowLastPosition=-1;
    private int hasFillData=0;

    //地图实例化
    public LocationClient mLocationClient;
    public LocationClientOption option;

    @Override
    protected int setLayout() {
        return R.layout.publish_layout;
    }

    @Override
    protected void initView() {

        ActivityCollector.addActivity(this);

        Intent intent=getIntent();
        whichType=intent.getIntExtra(Constant.WHICHTYPE,0);

        lp = new ViewGroup.MarginLayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,ViewGroup.LayoutParams.WRAP_CONTENT);
        lp.leftMargin = 15;
        lp.rightMargin = 15;
        lp.topMargin = 15;
        lp.bottomMargin = 15;

        arrays=getResources().getStringArray(R.array.maintain);
        length=arrays.length;

        getLocationPermission();

        //初始化控件
        init();
        setOnClickListener();

    }

    private void init(){
        back= (ImageView) findViewById(R.id.back);
        draftBox= (ImageView) findViewById(R.id.draft_box);
        shoppingImage= (ImageView) findViewById(R.id.shopping_image);
        imageButton= (ImageView) findViewById(R.id.image_button);
        relocation= (ImageView) findViewById(R.id.relocation);
        title= (EditText) findViewById(R.id.title);
        description= (EditText) findViewById(R.id.description);
        contactName= (EditText) findViewById(R.id.contact_name);
        shopNumber= (EditText) findViewById(R.id.shop_number);
        price= (EditText) findViewById(R.id.price);
        label= (LinearLayout) findViewById(R.id.label);
        address= (LinearLayout) findViewById(R.id.address);
        locationAddress= (TextView) findViewById(R.id.location_address);
        userDefine= (TextView) findViewById(R.id.user_define);
        flowLayout= (FlowLayout) findViewById(R.id.flowlayout);
        publish= (TextView) findViewById(R.id.publish);
        addressIsVisiable= (LinearLayout) findViewById(R.id.address_is_visiable);
        rootview= (RelativeLayout) findViewById(R.id.rootview);
    }
    private void setOnClickListener(){
        back.setOnClickListener(this);
        draftBox.setOnClickListener(this);
        label.setOnClickListener(this);
        address.setOnClickListener(this);
        imageButton.setOnClickListener(this);
        publish.setOnClickListener(this);
        relocation.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {

        if(mark==view.getId()){
            return;
        }

        switch (view.getId()){
            //返回
            case R.id.back:
                finish();
                break;
            //草稿箱
            case R.id.draft_box:
                break;
            //标签
            case R.id.label:
                mark=R.id.label;
                flowLayout.setVisibility(View.VISIBLE);
                addressIsVisiable.setVisibility(View.INVISIBLE);
                label.setBackgroundColor(getResources().getColor(R.color.white));
                address.setBackgroundColor(getResources().getColor(R.color.honeydew));
                fillFlowLayoutData();
                break;
            //地址
            case R.id.address:
                mark=R.id.address;
                flowLayout.setVisibility(View.INVISIBLE);
                addressIsVisiable.setVisibility(View.VISIBLE);
                label.setBackgroundColor(getResources().getColor(R.color.honeydew));
                address.setBackgroundColor(getResources().getColor(R.color.white));
                break;
            //挑选图片
            case R.id.image_button:
                setPopup();
                break;
            //重新定位
            case R.id.relocation:
                requestLocation();
                break;
            //发布
            case R.id.publish:
                break;

            //从相册选择图片
            case R.id.album:
                if(ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)!= PackageManager.PERMISSION_GRANTED){
                    ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},1);
                }else {
                    fromAlbum();
                }
                popup.dismiss();
                break;
            //相机拍照
            case R.id.camera:
                fromCamera();
                break;
            //取消popupwindow
            case R.id.cancel:
                popup.dismiss();
                break;
        }
    }


    private void fromAlbum(){
        Intent intent=new Intent("android.intent.action.GET_CONTENT");
        intent.setType("image/*");
        startActivityForResult(intent,TAKE_PITURE);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case Constant.PITRUE_CODE_PERMISSION:
                if(grantResults.length>0&&grantResults[0]==PackageManager.PERMISSION_GRANTED){
                    fromAlbum();
                }else {
                    Toast.makeText(this,"You denied the permission",Toast.LENGTH_SHORT).show();
                }
            break;
            case Constant.SCAN_CODE_PERMISSION:
                if(grantResults.length>0){
                    for(int result:grantResults){
                        if(result!=PackageManager.PERMISSION_GRANTED){
                            Toast.makeText(this,"You denied the permission",Toast.LENGTH_SHORT).show();
                            return;
                        }
                    }
                    requestLocation();
                }else {
                    Toast.makeText(this,"发送未知错误",Toast.LENGTH_SHORT).show();
                }
                break;

        }
    }

    @TargetApi(19)
    private void handleImageKitat(Intent data){
        String imagePath=null;
        Uri uri=data.getData();
        if(DocumentsContract.isDocumentUri(this,uri)){
            String docId=DocumentsContract.getDocumentId(uri);
            if("com.android.providers.media.documents".equals(uri.getAuthority())){
                String id=docId.split(":")[1];
                String selection=MediaStore.Images.Media._ID+"="+id;
                imagePath=getImagePath(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,selection);
            }else if("com.android.providers.downloads.documents".equals(uri.getAuthority())){
                Uri contentUri= ContentUris.withAppendedId(Uri.parse("content://downloads/public_downloads"),Long.valueOf(docId));
                imagePath=getImagePath(contentUri,null);
            }
        }else if("content".equalsIgnoreCase(uri.getScheme())){
            imagePath=getImagePath(uri,null);
        }else if("file".equalsIgnoreCase(uri.getScheme())){
            imagePath=uri.getPath();
        }
        displayImage(imagePath);
    }

    private void handleImageBeforeKitKat(Intent data){
        Uri uri=data.getData();
        String imagePath=getImagePath(uri,null);
        displayImage(imagePath);
    }

    private String getImagePath(Uri uri,String selection){
        String path=null;
        Cursor cursor=getContentResolver().query(uri,null,selection,null,null);
        if(cursor!=null){
            if(cursor.moveToFirst()){
                path=cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));
            }
            cursor.close();
        }
        return path;
    }

    private void displayImage(String imagePath){
        if(imagePath!=null){
            Bitmap bitmap=BitmapFactory.decodeFile(imagePath);
            this.imagePath=imagePath;
            shoppingImage.setImageBitmap(bitmap);
        }else {
            Toast.makeText(this,"failed to get image",Toast.LENGTH_SHORT).show();
        }
    }

    private void fromCamera(){
        File outputImage=new File(getExternalCacheDir(),"output_image.png");
        try{
            if(outputImage.exists()){
                outputImage.delete();
            }
            outputImage.createNewFile();
        }catch (Exception e){
            e.printStackTrace();
        }

        if(Build.VERSION.SDK_INT>=24){
            imageUri= FileProvider.getUriForFile(this,"weifen.com.eclife.activity.fileprovider",outputImage);
        }else {
            imageUri= Uri.fromFile(outputImage);
        }

        Intent intent=new Intent("android.media.action.IMAGE_CAPTURE");
        intent.putExtra(MediaStore.EXTRA_OUTPUT,imageUri);
        startActivityForResult(intent,TAKE_PHOTO);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode){
            case TAKE_PHOTO:
                if(resultCode==RESULT_OK){
                    try{
                        Bitmap bitmap= BitmapFactory.decodeStream(getContentResolver().openInputStream(imageUri));
                        shoppingImage.setImageBitmap(bitmap);
                        this.imagePath=imageUri.getPath();
                        popup.dismiss();
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }
                break;
            case TAKE_PITURE:
                if(resultCode==RESULT_OK){
                    if(Build.VERSION.SDK_INT>=19){
                        handleImageKitat(data);
                    }else {
                        handleImageBeforeKitKat(data);
                    }
                }
                break;
        }
    }

    private void setPopup(){
        View root=getLayoutInflater().inflate(R.layout.select_image_layout,null);
        popup=new PopupWindow(root, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);

        popup.setOutsideTouchable(false);
        popup.setAnimationStyle(R.style.Animations_GrowFromBottom);
        popup.showAtLocation(rootview, Gravity.BOTTOM, 0, 0);

        fromAlbum= (TextView) root.findViewById(R.id.album);
        fromCamera= (TextView) root.findViewById(R.id.camera);
        cancel= (TextView) root.findViewById(R.id.cancel);

        fromCamera.setOnClickListener(this);
        fromAlbum.setOnClickListener(this);
        cancel.setOnClickListener(this);
    }

    //请求位置
    private void requestLocation(){
        //重新定位初始化和设置参数
        mLocationClient=new LocationClient(this);
        mLocationClient.registerLocationListener(new MyLocationListener());
        option=new LocationClientOption();
        option.setScanSpan(5000);
        option.setIsNeedAddress(true);
        option.setIsNeedAddress(true);
        option.setLocationMode(LocationClientOption.LocationMode.Hight_Accuracy);
        mLocationClient.setLocOption(option);

        if(!mLocationClient.isStarted()){
            mLocationClient.start();
        }
    }

    public class MyLocationListener implements BDLocationListener {

        @Override
        public void onReceiveLocation(BDLocation bdLocation) {
            StringBuilder currentPosition=new StringBuilder();
            //bdLocation.getProvince()+bdLocation.getCity()+ bdLocation.getDistrict()+bdLocation.getStreet()+
            /***describeContents  getBuildingID() getDirection()  .getFloor()
             *
             */
            currentPosition.append(bdLocation.getDistrict()+bdLocation.getStreet());
            locationAddress.setText(currentPosition);
            mLocationClient.unRegisterLocationListener(this);
        }
    }

    private void getLocationPermission(){
        //权限申请
        List<String> permissionList=new ArrayList<>();
        if(ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)!= PackageManager.PERMISSION_GRANTED){
            permissionList.add(Manifest.permission.ACCESS_FINE_LOCATION);
        }
        if(ContextCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE)!= PackageManager.PERMISSION_GRANTED){
            permissionList.add(Manifest.permission.READ_PHONE_STATE);
        }
        if(ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)!= PackageManager.PERMISSION_GRANTED){
            permissionList.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
        }
        if(ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)!= PackageManager.PERMISSION_GRANTED){
            permissionList.add(Manifest.permission.ACCESS_COARSE_LOCATION);
        }

        if(!permissionList.isEmpty()){
            String[]permissions=permissionList.toArray(new String[permissionList.size()]);
            ActivityCompat.requestPermissions(this,permissions,1);
        }else {
            requestLocation();
        }
    }

    //填充标签里面的数据
    private void fillFlowLayoutData(){

        switch (whichType){
            //导游
            case Constant.GUIDE:
                break;
            //家教
            case Constant.HOMETEACH:
                break;
            //保洁陪护
            case Constant.CLEAN:
                break;
            //上门维护
            case Constant.MAINTAIN:

                if(hasFillData==0){
                    for(int i = 0; i < length; i ++){
                        TextView view = new TextView(this);
                        view.setText(arrays[i]);
                        view.setTextSize(16);
                        view.setTextColor(getResources().getColor(R.color.indianRed));
                        final int position=i;

                        view.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                if(flowLastPosition!=-1){
                                    ((TextView)flowLayout.getChildAt(flowLastPosition)).setTextColor(getResources().getColor(R.color.indianRed));
                                    ((TextView)flowLayout.getChildAt(flowLastPosition)).setBackgroundDrawable(getResources().getDrawable(R.drawable.flowlayout_text));
                                }
                                flowLastPosition=position;
                                ((TextView)view).setBackgroundColor(getResources().getColor(R.color.lightCoral));
                                ((TextView)view).setTextColor(getResources().getColor(R.color.white));
                            }
                        });
                        view.setBackgroundDrawable(getResources().getDrawable(R.drawable.flowlayout_text));
                        flowLayout.addView(view,lp);
                    }
                    hasFillData=1;
                }else {
                    if(flowLastPosition!=-1){
                        ((TextView)flowLayout.getChildAt(flowLastPosition)).setBackgroundColor(getResources().getColor(R.color.lightCoral));
                        ((TextView)flowLayout.getChildAt(flowLastPosition)).setTextColor(getResources().getColor(R.color.white));
                    }
                }
                break;
            //其他分类
            case Constant.ALL:
                break;
        }
    }
}
