package weifen.com.eclife.fragment;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import weifen.com.common.adapter.MyRecycleAdapter;
import weifen.com.common.base.BaseFragment;
import weifen.com.eclife.R;

/**
 * 附近的分类列表
 */
public class NeighbourTabFragment extends BaseFragment {
    RecyclerView recyclerView;
    MyRecycleAdapter recycleAdapter;
    ListView listView;
    SimpleAdapter listAdapter;

    List<Map<String,Object>> recycleDatas =new ArrayList<Map<String,Object>>();
    List<Map<String,Object>> listDatas =new ArrayList<Map<String,Object>>();

    String[] category=new String[]{"家教","保洁","陪护","月嫂","洗护"};
    public static final String CATEGORY_NAME="categoryName";

    public static int type;//用于区别是来自于哪个点击类型

    public static NeighbourTabFragment getInstance(int typeCode){
        NeighbourTabFragment neighbourTabFragment=new NeighbourTabFragment();
        type=typeCode;
        return neighbourTabFragment;
    }
    @Override
    protected int getLayout() {
        return R.layout.fragment_neighbour_tab;
    }

    @Override
    protected void init(View view) {
        initView(view);
        initAdapter();
        initData();
    }

    private void initData() {
        fillRecycleData(category.length);
        fillListData(5);
    }

    private void fillListData(int size) {
        //TODO 走网路请求数据
        listDatas.clear();
        for(int i=0;i<size;i++){
            Map<String,Object> itemData=new HashMap<String, Object>();
            itemData.put("icon", R.mipmap.category_list_01);
            itemData.put("title","家教");
            itemData.put("info","高中英语数学");
            itemData.put("updateTime","2018-8-5");
            itemData.put("address","广东工业大学华立学院");
            itemData.put("price","500.0");
            listDatas.add(itemData);
        }
        listAdapter.notifyDataSetChanged();
    }

    private void fillRecycleData(int size) {
        recycleDatas.clear();
        for(int i=0;i<size;i++){
            Map<String,Object> itemData=new HashMap<String, Object>();
            itemData.put(CATEGORY_NAME,category[i]);
            recycleDatas.add(itemData);
        }
        recycleAdapter.notifyDataSetChanged();
    }

    private void initAdapter() {
        recycleAdapter=new MyRecycleAdapter(getActivity(), recycleDatas, R.layout.item_neighnour_category,
                new String[]{CATEGORY_NAME}, new int[]{R.id.tv_name_neighbour}, new MyRecycleAdapter.MyHolderCallback() {
            @Override
            public void renderData(View itemView, Map<String, Object> currentData) {
                //TODO 设置点击事件
            }

            @Override
            public void init(View itemView) {

            }
        });
        recyclerView.setAdapter(recycleAdapter);
        //TODO 需要重新设置适配器,这里只做测试
        listAdapter=new SimpleAdapter(getActivity(),listDatas, R.layout.item_category,
                new String[]{"icon","title","info","updateTime","address","price"},new int[]{R.id.category_icon, R.id.tv_category_title,
                R.id.tv_category_info, R.id.tv_category_update_time,
                R.id.tv_category_address, R.id.tv_category_price});
        listView.setAdapter(listAdapter);
    }

    private void initView(View view) {
        recyclerView= (RecyclerView) view.findViewById(R.id.recycle_tab_neighbour);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL,false));

        listView= (ListView) view.findViewById(R.id.list_tab_neighbour);
    }
}
