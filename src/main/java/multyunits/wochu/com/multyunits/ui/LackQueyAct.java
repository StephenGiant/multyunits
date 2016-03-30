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
import multyunits.wochu.com.multyunits.bean.GoodsCountBean;
import multyunits.wochu.com.multyunits.net.OkHttpClientManager;
import multyunits.wochu.com.multyunits.url.AppClient;
import multyunits.wochu.com.multyunits.wiget.DatePickWiget;

/**
 * 缺货查询
 *
 * @author Administrator
 */
public class LackQueyAct extends MyBaseActivity implements OnClickListener {

    private DatePickWiget ll_date_choose;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.goodscountquery_layout);
        initView();
    }

    private String date;
    private Button qurry_count;
    private EditText et_batch;
    private EditText et_goodsCode;
    private TextView tv_goodsName;
    private TextView tv_goodsCode;
    private TextView tv_goodsQTY;
    private TextView tv_goodsPQTY;
    private TextView tv_lackQTY;

    private void initView() {
        ll_date_choose = (DatePickWiget) findViewById(R.id.ll_date_choose);
        et_batch = (EditText) findViewById(R.id.et_batch);
        et_goodsCode = (EditText) findViewById(R.id.et_goodsCode);
        qurry_count = (Button) findViewById(R.id.qurry_count);
        tv_goodsName = (TextView) findViewById(R.id.tv_goodsName);
        tv_goodsCode = (TextView) findViewById(R.id.tv_goodsCode);
        tv_goodsQTY = (TextView) findViewById(R.id.tv_goodsQTY);
        tv_goodsPQTY = (TextView) findViewById(R.id.tv_goodsPQTY);
        tv_lackQTY = (TextView) findViewById(R.id.tv_lackQTY);
        qurry_count.setOnClickListener(this);
        date = ll_date_choose.getDate();
        findViewById(R.id.et_batch);
        findViewById(R.id.et_goodsCode);
        ll_date_choose.setOnConfirmListenner(new DatePickWiget.onConfirmListenner() {

            @Override
            public void onConfirmClick() {
                date = ll_date_choose.getDate();

            }
        });
    }

    @Override
    public void handleCode(String code) {
        Pattern compile = Pattern.compile("WOC\\d*");
        Matcher matcher = compile.matcher(code);
        if (matcher.matches()) {
            et_goodsCode.setText(code);
            if (et_batch.getText().toString() != null && !TextUtils.isEmpty(et_batch.getText().toString())) {

            }
        }
    }

    /**
     * 获取缺货详情
     *
     * @param date
     * @param boci
     * @param code
     */
    private void getLack(String date, String boci, String code) {
        OkHttpClientManager.getAsyn(AppClient.GOODSCOUNTQUERY + date + "&batchNumber=" + boci + "&GoodsCode=" + code,
                new OkHttpClientManager.ResultCallback<GoodsCountBean>() {

                    @Override
                    public void onBefore(Request request) {
                        // TODO Auto-generated method stub
                        super.onBefore(request);
                        showLoadingDialog("");
                    }

                    @Override
                    public void onError(Request request, Exception e) {
                        hideLoadingDialog();
                        runOnUiThread(new Runnable() {

                            @Override
                            public void run() {
                                showMyToast("获取信息失败", "请检查网络");

                            }
                        });

                        clearInfo();
                    }

                    @Override
                    public void onResponse(GoodsCountBean response) {

                        hideLoadingDialog();
                        processLackInfo(response);
                    }
                });
    }

    private void processLackInfo(GoodsCountBean response) {
        if ("请求成功".equals(response.MESSAGE) && response.DATA != null && response.DATA.Table != null) {

            tv_goodsName.setText(response.DATA.Table.get(0).ITEMNAME);
            ;
            tv_goodsCode.setText(response.DATA.Table.get(0).ITEMCODE);
            double Qty = response.DATA.Table.get(0).SUMQTY;
            double Pqty = response.DATA.Table.get(0).SUMPQTY;
            double lack = Qty - Pqty;
            tv_goodsQTY.setText("" + Qty);
            tv_goodsPQTY.setText("" + Pqty);
            tv_lackQTY.setText("" + lack);

        } else {
            clearInfo();
            showMyToast("获取信息失败", "请确认波次与配送日期！");
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.qurry_count:
                String boci = et_batch.getText().toString();
                String goodsCode = et_goodsCode.getText().toString();
                if (boci != null && !TextUtils.isEmpty(boci) && goodsCode != null && !TextUtils.isEmpty(goodsCode)) {
                    getLack(date, boci, goodsCode);
                }
                break;

            default:
                break;
        }

    }

    private void clearInfo() {
        tv_goodsName.setText("");
        ;
        tv_goodsCode.setText("");
        tv_goodsQTY.setText("");
        tv_goodsPQTY.setText("");
        tv_lackQTY.setText("");
    }
}
