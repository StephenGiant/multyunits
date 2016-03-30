package multyunits.wochu.com.multyunits.ui;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.squareup.okhttp.Request;

import multyunits.wochu.com.multyunits.R;
import multyunits.wochu.com.multyunits.baseact.MyBaseActivity;
import multyunits.wochu.com.multyunits.bean.UserInfo;
import multyunits.wochu.com.multyunits.net.OkHttpClientManager;
import multyunits.wochu.com.multyunits.receiver.PDAReceiver;
import multyunits.wochu.com.multyunits.url.AppClient;
import multyunits.wochu.com.multyunits.utils.LogUtil;
import multyunits.wochu.com.multyunits.utils.SharePreUtil;

public class LoginAct extends MyBaseActivity implements OnClickListener {
    private Context mcontext;
    private EditText et_username;
    private EditText et_password;
    private Button btn_login;
    private Button btn_equit;
    private String userName;
    private String password;
    private Intent intent;
    private PDAReceiver receiver;
    private IntentFilter scanDataIntentFilter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_login);
        et_username = (EditText) findViewById(R.id.et_username);
        et_password = (EditText) findViewById(R.id.et_password);
        btn_login = (Button) findViewById(R.id.btn_login);
        btn_equit = (Button) findViewById(R.id.btn_equit);
        btn_login.setOnClickListener(this);
        btn_equit.setOnClickListener(this);
        mcontext = this;

        receiver = new PDAReceiver() {

            @Override
            public void dispathCode(String code) {
                handleCode(code);

            }
        };
        scanDataIntentFilter = new IntentFilter();
        //scanDataIntentFilter.addAction("com.android.scancontext"); //前台输出
        scanDataIntentFilter.addAction("com.android.scanservice.scancontext"); //后台输出
        registerReceiver(receiver, scanDataIntentFilter);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_login:
                userName = et_username.getText().toString();
                password = et_password.getText().toString();
                boolean loginChecked = LoginChecked(userName, password);
                if (loginChecked) {
                    login(userName, password);//开始登录//来了来了
                }
                //toMainAct();
                break;
            case R.id.btn_equit:
                finish();
                break;
            default:
                break;
        }
    }

    /**
     * 检验登陆信息
     *
     * @param userName1
     * @param password1
     */
    private boolean LoginChecked(String userName1, String password1) {
        // TODO Auto-generated method stub
        if (TextUtils.isEmpty(userName1)) {
            Toast.makeText(getBaseContext(), "工号不能为空", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (TextUtils.isEmpty(password1)) {
            Toast.makeText(getBaseContext(), "密码不能为空", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    /**
     * 登录操作
     *
     * @param username
     * @param password
     */
    private void login(final String username, final String password) {

        OkHttpClientManager.getAsyn(AppClient.loginUrl(username, password), new OkHttpClientManager.ResultCallback<UserInfo>() {

            @Override
            public void onBefore(Request request) {
                // TODO Auto-generated method stub
                super.onBefore(request);
                showLoadingDialog("登陆中");
            }

            @Override
            public void onError(Request request, Exception e) {
                // TODO Auto-generated method stub
//				toMainAct();
                runOnUiThread(new Runnable() {
                    public void run() {
                        showMyToast("登录失败！", "服务器无响应！");
                        hideLoadingDialog();
                    }
                });
            }

            @Override
            public void onResponse(UserInfo response) {
                // TODO Auto-generated method stub
                if ("1".equals(response.RESULT)) {
                    Toast.makeText(getBaseContext(), response.MESSAGE, Toast.LENGTH_SHORT).show();
                    LogUtil.e("LoginAct", response.MESSAGE);
                    SharePreUtil.putString(mcontext, "OPRNAME", username);//保存工人的工号
                    SharePreUtil.putInteger(mcontext, "userID", response.DATA.USERID);// 保存工人的账号
                    toMainAct();
                } else {
                    showMyToast("登录失败！", "请检查用户名或密码");
                }
            }

            @Override
            public void onAfter() {
                // TODO Auto-generated method stub
                super.onAfter();
                hideLoadingDialog();
            }
        });
    }

    private void toMainAct() {
        if (intent == null) {
            intent = new Intent(this, MainActivity.class);
        }
        startActivity(intent);
        finish();
    }

    @Override
    public void handleCode(String code) {

        et_username.setText(code);
    }

    @Override
    protected void onDestroy() {
        // TODO Auto-generated method stub
        super.onDestroy();
        unregisterReceiver(receiver);
    }


}
