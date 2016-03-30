package multyunits.wochu.com.multyunits.ui;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.squareup.okhttp.Request;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import multyunits.wochu.com.multyunits.R;
import multyunits.wochu.com.multyunits.baseact.MyBaseActivity;
import multyunits.wochu.com.multyunits.bean.BindZboxInfo;
import multyunits.wochu.com.multyunits.net.OkHttpClientManager;
import multyunits.wochu.com.multyunits.url.AppClient;
import multyunits.wochu.com.multyunits.utils.LogUtil;

public class BindZboxAct extends MyBaseActivity implements OnClickListener {

    private EditText et_Zbox;
    private Button btn_confirm;
    private Button btn_cancel;
    private TextView tv_goodsCode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_bindzbox);
        initView();
    }

    private void initView() {
        et_Zbox = (EditText) findViewById(R.id.et_Zbox);
        btn_confirm = (Button) findViewById(R.id.btn_confirm);
        btn_cancel = (Button) findViewById(R.id.btn_cancel);
        tv_goodsCode = (TextView) findViewById(R.id.goodsCode);
        btn_confirm.setOnClickListener(this);
        btn_cancel.setOnClickListener(this);
    }

    @Override
    public void handleCode(String code) {
        //判断是否为周转箱
        String goods = tv_goodsCode.getText().toString();
        String ZboxCode = et_Zbox.getText().toString();
        if (isZbox(code)) {
            et_Zbox.setText(code);
            if (goods != null && !TextUtils.isEmpty(goods)) {
                postBind(code, goods);

            }
        } else {
            //为商品条码
            if (isGoodsCode(code)) {
                tv_goodsCode.setText(code);
                if (ZboxCode != null && !TextUtils.isEmpty(ZboxCode)) {
                    postBind(ZboxCode, code);
                }
            } else {
                showMyToast("条码有误", "请检查条码");
            }
        }

    }

    /**
     * 判断是否为周转箱
     *
     * @param code
     * @return
     */
    private boolean isZbox(String code) {
        Pattern compile = Pattern.compile("[Z]\\d{6}");

        return compile.matcher(code).matches();
    }

    private boolean isGoodsCode(String code) {
        Pattern compile = Pattern.compile("WOC\\d*");
        Matcher matcher = compile.matcher(code);
        return matcher.matches();
    }

    /**
     * 判断周转箱是否已解绑
     *
     * @param code
     */
    private void checkZbox(String code) {

    }

    /**
     * 捡货提交
     */
    private void postBind(String Zbox, String GoodsCode) {
        LogUtil.i("url", AppClient.BINDZBOX + GoodsCode + "&ZboxNo=" + Zbox);
        OkHttpClientManager.postAsyn(AppClient.BINDZBOX + GoodsCode + "&ZboxNo=" + Zbox, new OkHttpClientManager.ResultCallback<BindZboxInfo>() {

            @Override
            public void onBefore(Request request) {

                super.onBefore(request);
                showLoadingDialog("");
            }

            @Override
            public void onError(Request request, Exception e) {
                LogUtil.e("提交", "失败");
                hideLoadingDialog();
            }

            @Override
            public void onResponse(BindZboxInfo response) {
                LogUtil.i("响应", response.toString());
                processInfo(response);
                hideLoadingDialog();
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_confirm:
                handleCode(et_Zbox.getText().toString());
            case R.id.btn_cancel:
                et_Zbox.setText("");
                break;

            default:
                break;
        }

    }

    private void processInfo(BindZboxInfo response) {
        if ("1".equals(response.RESULT)) {
            //绑定成功
            LogUtil.i("结果", "绑定成功");
            clearData();
        } else {
            LogUtil.i("结果", response.RESULT);
            clearData();
            showMyToast("绑定失败", "请检查商品或周转箱");
        }
    }

    private void clearData() {
        tv_goodsCode.setText("");
        et_Zbox.setText("");

    }

}
