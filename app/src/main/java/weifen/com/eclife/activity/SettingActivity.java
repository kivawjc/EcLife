package weifen.com.eclife.activity;

import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import weifen.com.common.base.BaseActivity;
import weifen.com.common.utils.ActivityCollector;
import weifen.com.eclife.R;

/**
 * Created by zhurencong on 2017/9/20.
 */
public class SettingActivity extends BaseActivity implements View.OnClickListener {

    private ImageView back;
    private LinearLayout personalData,accountSafe,infoAuthentication,contact_Us,agreement;
    private TextView exit;

    @Override
    protected int setLayout() {
        return R.layout.setting_layout;
    }

    @Override
    protected void initView() {

        ActivityCollector.addActivity(this);

        back= (ImageView) findViewById(R.id.back);
        personalData= (LinearLayout) findViewById(R.id.personal_data);
        accountSafe= (LinearLayout) findViewById(R.id.account_safe);
        infoAuthentication= (LinearLayout) findViewById(R.id.info_authentication);
        contact_Us= (LinearLayout) findViewById(R.id.contact_us);
        agreement= (LinearLayout) findViewById(R.id.agreement);
        exit= (TextView) findViewById(R.id.exit);

        back.setOnClickListener(this);
        personalData.setOnClickListener(this);
        accountSafe.setOnClickListener(this);
        infoAuthentication.setOnClickListener(this);
        contact_Us.setOnClickListener(this);
        agreement.setOnClickListener(this);
        exit.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            //返回
            case R.id.back:
                finish();
                break;
            //个人资料
            case R.id.personal_data:
                break;
            //账户安全
            case R.id.account_safe:
                break;
            //信息认证
            case R.id.info_authentication:
                break;
            //联系我们
            case R.id.contact_us:
                break;
            //协议
            case R.id.agreement:
                break;
            //退出登录
            case R.id.exit:
                ActivityCollector.finishAll();
                break;
        }
    }
}
