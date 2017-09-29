package weifen.com.eclife.fragment;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.acker.simplezxing.activity.CaptureActivity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import weifen.com.common.adapter.RecycleViewAdapter;
import weifen.com.common.adapter.MyRecycleAdapter;
import weifen.com.common.adapter.ViewHolder;
import weifen.com.common.base.BaseFragment;
import weifen.com.eclife.db.ShoppingCom;
import weifen.com.common.utils.DateFormatUtils;
import weifen.com.common.utils.FixedViewUtil;
import weifen.com.eclife.R;
import weifen.com.widget.GradualScrollView;

/**
 * 社区界面
 */
public class CommunityFragment extends BaseFragment implements View.OnClickListener {

    ImageView searchIV,scanIV;//搜索|扫描
    RecyclerView popularCommunityView;//热门社区
    ListView pcListView;//社区列表
    private static final int REQ_CODE_PERMISSION = 0x1111;

    LinearLayout topView;
    GradualScrollView gradualScroll;

    MyRecycleAdapter myRecycleAdapter;
    List<Map<String,Object>> popularCommunityDatas=new ArrayList<Map<String,Object>>();

    //作为传入适配器的key
    private static final String PC_ICON="popularCommunityIcon";
    private static final String PC_INFO="popularCommunityInfo";

    //假数据
    String[] pcInfoData=new String[]{"罗马家园小区","碧泉小区","微风科技","凤凰城"};

    List<ShoppingCom> shoppingList=new ArrayList<ShoppingCom>();
    RecycleViewAdapter<ShoppingCom> myAdapter;
    @Override
    protected int getLayout() {
        return R.layout.fragment_community;
    }

    @Override
    protected void init(View view) {
        initView(view);
        initData();
    }

    private void initData() {
        addPopularCommunityDatas(pcInfoData.length);
        myRecycleAdapter=new MyRecycleAdapter(getActivity(),popularCommunityDatas, R.layout.item_popular_community,
                new String[]{PC_ICON,PC_INFO},new int[]{R.id.iv_icon_pc, R.id.tv_info_pc},myHolderCallback);
        popularCommunityView.setAdapter(myRecycleAdapter);

        //社区列表适配器
        addCommunityListDatas(5);
        myAdapter=new RecycleViewAdapter<ShoppingCom>(getActivity(),shoppingList, R.layout.item_community_list) {
            @Override
            public void convert(ViewHolder holder, ShoppingCom item) {
                holder.setImageResource(R.id.category_icon,item.getIcon());
                holder.setText(R.id.tv_category_title,item.getType());
                holder.setText(R.id.tv_category_info,item.getTitle());
                holder.setText(R.id.tv_category_update_time,item.getTime());
                holder.setText(R.id.tv_category_address,item.getDistance());
                holder.setText(R.id.tv_category_price,item.getPrice());
                holder.setText(R.id.tv_small_title_community,item.getSmallTitle());
                holder.setImageResource(R.id.iv_small_icon_community,item.getSmallIcon());
            }
        };
        pcListView.setAdapter(myAdapter);
        FixedViewUtil.resetListViewHeight(pcListView);
    }

    private void initView(View view) {
        searchIV= (ImageView) view.findViewById(R.id.iv_search_community);
        scanIV= (ImageView) view.findViewById(R.id.iv_scan_community);
        popularCommunityView= (RecyclerView) view.findViewById(R.id.recycle_view_community);
        popularCommunityView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL,false));
        pcListView= (ListView) view.findViewById(R.id.listView_community);
        //TODO
        topView= (LinearLayout) view.findViewById(R.id.top_view);
        gradualScroll= (GradualScrollView) view.findViewById(R.id.gradualScroll);
        gradualScroll.setTopView(topView);

        scanIV.setOnClickListener(this);
    }

    private void addPopularCommunityDatas(int size){
        //先传入假数据,后期再加网络请求 TODO
        for(int i=0;i<size;i++){
            Map<String,Object> itemData=new HashMap<String,Object>();
            itemData.put(PC_ICON, R.mipmap.popular_community_01+i);
            itemData.put(PC_INFO,pcInfoData[i]);
            popularCommunityDatas.add(itemData);
        }
    }

    /**
     * 加载社区列表的数据
     */
    private void addCommunityListDatas(int size){
        //shoppingList的数据
        for(int i=0;i<size;i++){
            ShoppingCom itemShop=new ShoppingCom();
            itemShop.setIcon(R.mipmap.category_list_01);
            itemShop.setSmallIcon(R.mipmap.popular_community_03);
            itemShop.setType("家教");
            itemShop.setTitle("高中英语教学");
            itemShop.setSmallTitle("万达广场");
            itemShop.setTime(DateFormatUtils.getDate(System.currentTimeMillis(),"yyyy.MM.dd"));
            itemShop.setDistance("距离"+(int)(Math.random()*10*i)+"m");
            itemShop.setPrice("￥"+(int)(Math.random()*1000)+".0");

            shoppingList.add(itemShop);
        }
    }

    /**
     * 热门列表适配器的回调接口
     */
    MyRecycleAdapter.MyHolderCallback myHolderCallback=new MyRecycleAdapter.MyHolderCallback() {
        @Override
        public void renderData(View itemView, final Map<String, Object> currentData) {
            //点击加入社区
            TextView insertCommunityTV= (TextView) itemView.findViewById(R.id.tv_insert_pc);
            insertCommunityTV.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //TODO 加入社区，网络请求
                    Toast.makeText(getActivity(),"加入社区",Toast.LENGTH_SHORT).show();
                }
            });
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //TODO 点击进入社区简介
                    Toast.makeText(getActivity(),"点击了"+currentData.get(PC_INFO),Toast.LENGTH_SHORT).show();
                }
            });
        }

        @Override
        public void init(View itemView) {
        }
    };

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            //扫描二维码
            case R.id.iv_scan_community:
                if (ContextCompat.checkSelfPermission(activity, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                    // Do not have the permission of camera, request it.
                    ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.CAMERA}, REQ_CODE_PERMISSION);
                } else {
                    // Have gotten the permission
                    startCaptureActivityForResult();
                }
                break;
        }
    }

    //调用二维码界面
    private void startCaptureActivityForResult() {
        Intent intent = new Intent(activity, CaptureActivity.class);
        Bundle bundle = new Bundle();
        bundle.putBoolean(CaptureActivity.KEY_NEED_BEEP, CaptureActivity.VALUE_BEEP);
        bundle.putBoolean(CaptureActivity.KEY_NEED_VIBRATION, CaptureActivity.VALUE_VIBRATION);
        bundle.putBoolean(CaptureActivity.KEY_NEED_EXPOSURE, CaptureActivity.VALUE_NO_EXPOSURE);
        bundle.putByte(CaptureActivity.KEY_FLASHLIGHT_MODE, CaptureActivity.VALUE_FLASHLIGHT_OFF);
        bundle.putByte(CaptureActivity.KEY_ORIENTATION_MODE, CaptureActivity.VALUE_ORIENTATION_AUTO);
        bundle.putBoolean(CaptureActivity.KEY_SCAN_AREA_FULL_SCREEN, CaptureActivity.VALUE_SCAN_AREA_FULL_SCREEN);
        bundle.putBoolean(CaptureActivity.KEY_NEED_SCAN_HINT_TEXT, CaptureActivity.VALUE_SCAN_HINT_TEXT);
        intent.putExtra(CaptureActivity.EXTRA_SETTING_BUNDLE, bundle);
        startActivityForResult(intent, CaptureActivity.REQ_CODE);
    }

    //请求权限
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case REQ_CODE_PERMISSION: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    startCaptureActivityForResult();
                } else {
                    // User disagree the permission
                    Toast.makeText(activity, "You must agree the camera permission request before you use the code scan function", Toast.LENGTH_LONG).show();
                }
            }
            break;
            default:

        }
    }

    //扫描结果
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case CaptureActivity.REQ_CODE:
                switch (resultCode) {
                    case -1:
                        Toast.makeText(activity,data.getStringExtra(CaptureActivity.EXTRA_SCAN_RESULT)+"",Toast.LENGTH_SHORT).show();
                        break;
                    case 0:
                        if (data != null) {
                            // for some reason camera is not working correctly
                            Toast.makeText(activity,"扫描失败",Toast.LENGTH_SHORT).show();
                        }
                        break;
                }
                break;
        }
    }

}
