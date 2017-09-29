package weifen.com.eclife.fragment;

import android.text.format.DateUtils;
import android.view.View;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import weifen.com.common.base.BaseFragment;
import weifen.com.common.utils.DateFormatUtils;
import weifen.com.common.utils.FixedViewUtil;
import weifen.com.eclife.R;

/**
 * 修改上门维修界面
 * Created by Administrator on 2017/9/23.
 */
public class MaintainTabFragment extends BaseFragment{
    ListView listView;
    List<Map<String,Object>> listDatas=new ArrayList<Map<String,Object>>();
    SimpleAdapter listAdapter;
    private static int type;//区别传入的是哪个分类

    public static MaintainTabFragment getInstance(int typeCode){
        MaintainTabFragment maintainTabFragment=new MaintainTabFragment();
        type=typeCode;
        return maintainTabFragment;
    }

    @Override
    protected int getLayout() {
        return R.layout.maintain_layout_tab;
    }

    @Override
    protected void init(View view) {
        initView(view);
        initAdapter();
        initData();

//        FixedViewUtil.resetListViewHeight(listView);
    }

    private void initData() {
        fillListData(5);
    }
    //加载数据
    private void fillListData(int size) {
        //TODO 走网路请求数据
        listDatas.clear();
        for(int i=0;i<size;i++){
            Map<String,Object> itemData=new HashMap<String, Object>();
            itemData.put("icon", R.mipmap.category_list_01);
            itemData.put("title","冰箱");
            itemData.put("info","拆装");
            itemData.put("updateTime", DateFormatUtils.getDate(System.currentTimeMillis(),"yyyy.MM.dd"));
            itemData.put("address","深圳");
            itemData.put("price","￥500.0");
            listDatas.add(itemData);
        }
        listAdapter.notifyDataSetChanged();
    }

    private void initAdapter() {
        //TODO 需要重新设置适配器,这里只做测试
        listAdapter=new SimpleAdapter(getActivity(),listDatas, R.layout.item_category,
                new String[]{"icon","title","info","updateTime","address","price"},new int[]{R.id.category_icon, R.id.tv_category_title,
                R.id.tv_category_info, R.id.tv_category_update_time,
                R.id.tv_category_address, R.id.tv_category_price});
        listView.setAdapter(listAdapter);
    }

    private void initView(View view) {
        listView= (ListView) view.findViewById(R.id.list_view);
    }

}
