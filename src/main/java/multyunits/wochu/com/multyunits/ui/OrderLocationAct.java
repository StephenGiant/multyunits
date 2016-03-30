package multyunits.wochu.com.multyunits.ui;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.squareup.okhttp.Request;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import butterknife.Bind;
import butterknife.ButterKnife;
import multyunits.wochu.com.multyunits.R;
import multyunits.wochu.com.multyunits.baseact.MyBaseActivity;
import multyunits.wochu.com.multyunits.bean.OrderLocationBean;
import multyunits.wochu.com.multyunits.net.OkHttpClientManager;
import multyunits.wochu.com.multyunits.url.AppClient;
import multyunits.wochu.com.multyunits.utils.LogUtil;

/**
 * @author qianpeng
 *         Created by qianpeng on 2016/3/27.
 */
public class OrderLocationAct extends MyBaseActivity implements View.OnClickListener {
    @Bind(R.id.editText)
    EditText editText;
    @Bind(R.id.confirm_order)
    Button confirmOrder;
    @Bind(R.id.cancel_order)
    Button cancelOrder;
    @Bind(R.id.content_order)
    TextView contentOrder;
    @Bind(R.id.content_oldLoacation)
    TextView contentOldLoacation;
    @Bind(R.id.content_curLoacation)
    TextView contentCurLoacation;


//    TextView contentOrder;
//
//    TextView contentOldLoacation;
//
//    TextView contentCurLoacation;
//
//    Button confirmOrder;
//
//    Button cancelOrder;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

//        ButterKnife.bind(this);
        setContentView(R.layout.act_orderlocation);
        ButterKnife.bind(this);
        initView();
    }


    private void initView() {
//         editText = (EditText)findViewById(R.id.editText);
//        contentOrder= (TextView) findViewById(R.id.content_order);
//        contentOldLoacation = (TextView)  findViewById(R.id.content_oldLoacation);
//        contentCurLoacation=(TextView) findViewById(R.id.content_curLoacation);
//        confirmOrder = (Button)findViewById(R.id.confirm_order);
//        cancelOrder = (Button) findViewById(R.id.cancel_order);
        confirmOrder.setOnClickListener(this);
        cancelOrder.setOnClickListener(this);

    }

    private String[] relation = new String[]{"A", "I", "W", "H"};

    private String orderFormat(String code) {
        if (code != null && !TextUtils.isEmpty(code)) {
            int parseInt = Integer.parseInt(code.charAt(0) + "");
            if (parseInt > 0 && parseInt < 5) {
                code = relation[parseInt - 1] + code.substring(1);
            } else {
                return "Error";
            }
            LogUtil.i("处理后的订单", code);
            return code;
        } else {
            return "Error";
        }

    }

    @Override
    public void handleCode(String code) {
        Pattern compile = Pattern.compile("[AIWH]\\d*");
        //纯数字的正则
        Pattern compile2 = Pattern.compile("\\d*");
        Matcher matcher = compile2.matcher(code);

        if (compile.matcher(code).matches()) {

            editText.setText(code);
            getOrderInfo(code);

        } else {
            if (matcher.matches()) {
                handleCode(orderFormat(code));
            } else {
                showMyToast("条码有误", "请检查条码");
                clearInfo();
            }
        }
    }

    private void getOrderInfo(String code) {
        OkHttpClientManager.getAsyn(AppClient.URL_GET_ORDER_INFO_BY_ORDER_NO + code, new OkHttpClientManager.ResultCallback<OrderLocationBean>() {
            @Override
            public void onError(Request request, Exception e) {
                hideLoadingDialog();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        showMyToast("无响应", "请确认网络!");
                    }
                });
            }

            @Override
            public void onBefore(Request request) {
                super.onBefore(request);
                showLoadingDialog("");
            }

            @Override
            public void onResponse(OrderLocationBean response) {
                hideLoadingDialog();
                processInfo(response);

            }
        });
    }

    private void processInfo(OrderLocationBean response) {
        if ("1".equals(response.RESULT)) {
            if (response.DATA != null) {
                contentOrder.setText(editText.getText().toString());
                contentCurLoacation.setText(response.DATA.get(0).BATCH_NO + "-" + response.DATA.get(0).BATCH_SUBNO);
                contentOldLoacation.setText(response.DATA.get(0).APPORDERNO);
            }
        } else {
            editText.setText("");
            clearInfo();
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.confirm_order:
//                showMyToast("点击了","确定");
                if (editText.getText().toString() != null && !TextUtils.isEmpty(editText.getText().toString())) {
                    handleCode(editText.getText().toString());
                }
                break;
            case R.id.cancel_order:
                editText.setText("");
                break;
        }
    }

    private void clearInfo() {
        editText.setText("");
        contentOldLoacation.setText("");
        contentCurLoacation.setText("");
        contentOrder.setText("");


    }
}
