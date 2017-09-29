package weifen.com.eclife.fragment;

import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import weifen.com.common.base.BaseFragment;
import weifen.com.eclife.R;
import weifen.com.eclife.activity.PublishActivity;
import weifen.com.eclife.activity.SelectPublishSortActivity;
import weifen.com.eclife.activity.SettingActivity;
import weifen.com.widget.ParallaxScrollView;

/**
 * 我的界面
 * Created by Administrator on 2017/2/27.
 */
public class MineFragment extends BaseFragment implements View.OnClickListener {

    private TextView message,publish,nick,motto,publishNumber,orderNumber,gradeNumber;
    private ImageView setting,headImage,backgroundImage;
    private LinearLayout managerSalary,myCommunity,infoCertification;
    private ListView listView;
    @Override
    protected int getLayout() {
        return R.layout.fragment_mine;
    }

    @Override
    protected void init(View view) {
        initView(view);

        initListener();
    }

    private void initView(View view) {
        message= (TextView) view.findViewById(R.id.tv_message_mine);
        publish= (TextView) view.findViewById(R.id.tv_publish_mine);
        nick= (TextView) view.findViewById(R.id.nick);
        motto= (TextView) view.findViewById(R.id.motto);
        publishNumber= (TextView) view.findViewById(R.id.tv_publish_number_mine);
        orderNumber= (TextView) view.findViewById(R.id.tv_order_number_mine);
        gradeNumber= (TextView) view.findViewById(R.id.tv_grade_number_mine);
        setting= (ImageView) view.findViewById(R.id.iv_setting_mine);
        headImage= (ImageView) view.findViewById(R.id.head_image);
        managerSalary= (LinearLayout) view.findViewById(R.id.manager_salary_mine);
        myCommunity= (LinearLayout) view.findViewById(R.id.my_community_mine);
        infoCertification= (LinearLayout) view.findViewById(R.id.info_certification_mine);
        listView= (ListView) view.findViewById(R.id.list_order_mine);
        backgroundImage= (ImageView) view.findViewById(R.id.iv_background_mine);
    }

    private void initListener() {
        message.setOnClickListener(this);
        publish.setOnClickListener(this);
        managerSalary.setOnClickListener(this);
        myCommunity.setOnClickListener(this);
        infoCertification.setOnClickListener(this);
        setting.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {

        Intent intent;

        switch (view.getId()){
            //消息
            case R.id.tv_message_mine:
                break;
            //发布
            case R.id.tv_publish_mine:
                intent=new Intent(activity, SelectPublishSortActivity.class);
                startActivity(intent);
                break;
            //设置
            case R.id.iv_setting_mine:
                intent=new Intent(activity, SettingActivity.class);
                startActivity(intent);
                break;
            //资金去管理
            case R.id.manager_salary_mine:
                break;
            //我的社区
            case R.id.my_community_mine:
                break;
            //信息认证
            case R.id.info_certification_mine:
                break;
        }
    }
}
