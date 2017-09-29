package weifen.com.eclife.activity;

import android.app.Fragment;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.viewpagerindicator.CirclePageIndicator;

import java.util.ArrayList;
import java.util.List;

import weifen.com.common.adapter.MyFragmentStatePagerAdapter;
import weifen.com.common.base.BaseActivity;
import weifen.com.common.commonAdapter.PageAdapter;
import weifen.com.common.utils.ActivityCollector;
import weifen.com.eclife.R;
import weifen.com.eclife.fragment.MaintainTabFragment;

/**
 * Created by zhurencong on 2017/9/19.
 * 上门维修
 */
public class MaintainActivity extends BaseActivity implements View.OnClickListener {

    private ImageView back,search;
    private ViewPager viewPager,viewPagerContent;
    private CirclePageIndicator indicator;
    private LinearLayout sort,screening;
    private TabLayout maintainTablayout;


    //广告图片集合
    private List<ImageView> advImages;

    @Override
    protected int setLayout() {
        return R.layout.maintain_layout;
    }

    @Override
    protected void initView() {

        ActivityCollector.addActivity(this);

        findView();
        initListener();

        advImages=new ArrayList<>();
        //网络加载数据
        int []imageResId = new int[]{R.mipmap.test00,R.mipmap.test01,R.mipmap.test02,R.mipmap.test03,R.mipmap.test04,R.mipmap.test05};

        for(int i=0;i<imageResId.length;i++){
            ImageView imageView = new ImageView(this);
            imageView.setBackgroundResource(imageResId[i]);
            advImages.add(imageView);
        }

        viewPager.setAdapter(new PageAdapter(advImages));
        indicator.setViewPager(viewPager);
        indicator.setFillColor(getResources().getColor(R.color.divider));

        //绑定viewPager
        MaintainPagerAdapter maintainPagerAdapter=new MaintainPagerAdapter(getFragmentManager(),getResources().getStringArray(R.array.maintain));
        viewPagerContent.setAdapter(maintainPagerAdapter);
        maintainTablayout.setupWithViewPager(viewPagerContent);
    }

    private void findView() {
        back= (ImageView) findViewById(R.id.back);
        search= (ImageView) findViewById(R.id.search);
        viewPager= (ViewPager) findViewById(R.id.viewpager);
        indicator= (CirclePageIndicator) findViewById(R.id.indicator);
        sort= (LinearLayout) findViewById(R.id.sort);
        screening= (LinearLayout) findViewById(R.id.screening);

        maintainTablayout= (TabLayout) findViewById(R.id.tab_layout_maintain);
        viewPagerContent= (ViewPager) findViewById(R.id.view_pager_content);//显示维修分类
    }

    private void initListener() {
        back.setOnClickListener(this);
        search.setOnClickListener(this);
        sort.setOnClickListener(this);
        screening.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {

        switch (view.getId()){
            //返回
            case R.id.back:
                finish();
                break;
            //搜索
            case R.id.search:
                break;
            //排序
            case R.id.sort:
                break;
            //筛选
            case R.id.screening:
                break;

        }
    }

    private class MaintainPagerAdapter extends MyFragmentStatePagerAdapter{
        String[] titleTabs;
        public MaintainPagerAdapter(android.app.FragmentManager fm, String[] titleTabs){
            super(fm);
            this.titleTabs=titleTabs;
        }
        @Override
        public Fragment getItem(int position) {
            MaintainTabFragment fm=MaintainTabFragment.getInstance(position);
            return fm;
        }

        @Override
        public int getCount() {
            return titleTabs==null?0:titleTabs.length;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return titleTabs[position];
        }
    }
}
