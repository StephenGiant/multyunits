package multyunits.wochu.com.multyunits.ui;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.squareup.okhttp.Request;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import multyunits.wochu.com.multyunits.R;
import multyunits.wochu.com.multyunits.adapter.OrderDetailAdapter;
import multyunits.wochu.com.multyunits.baseact.MyBaseActivity;
import multyunits.wochu.com.multyunits.bean.OrderBeanForFbox;
import multyunits.wochu.com.multyunits.bean.OrderInfoByOrder;
import multyunits.wochu.com.multyunits.bean.OrderInfoByOrder.ItemDetail;
import multyunits.wochu.com.multyunits.decorator.MyDivider;
import multyunits.wochu.com.multyunits.net.OkHttpClientManager;
import multyunits.wochu.com.multyunits.url.AppClient;
import multyunits.wochu.com.multyunits.utils.LogUtil;

public class OrderDetailInfoAct extends MyBaseActivity implements OnClickListener {

    private TextView tv_sendBoxText;
    private TextView tv_orderNum;
    private TextView tv_order_Count;
    //	private TextView tv_sorting_Count;
    private Button btn_confCode;
    private Button btn_cancCode;
    private EditText et_getCode;
    private RecyclerView rv_details;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_checkorderdetail);
        initView();
    }

    private void initView() {
        rv_details = (RecyclerView) findViewById(R.id.rv_details);
        btn_confCode = (Button) findViewById(R.id.btn_confCode);
        btn_cancCode = (Button) findViewById(R.id.btn_cancCode);
        et_getCode = (EditText) findViewById(R.id.et_getCode);
        btn_confCode.setOnClickListener(this);
        btn_cancCode.setOnClickListener(this);
        tv_sendBoxText = (TextView) findViewById(R.id.tv_sendBoxText);
        tv_orderNum = (TextView) findViewById(R.id.tv_orderNum);
        tv_order_Count = (TextView) findViewById(R.id.tv_order_Count);
//		tv_sorting_Count = (TextView) findViewById(R.id.tv_sorting_Count);
        LinearLayoutManager manager = new LinearLayoutManager(this);
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        rv_details.setLayoutManager(manager);
        rv_details.addItemDecoration(new MyDivider(this, MyDivider.VERTICAL_LIST));
    }

    @Override
    public void handleCode(String code) {
        Pattern compile = Pattern.compile("[BJLX]\\d{6}");
        Pattern compile2 = Pattern.compile("[AIWH]\\d*");
        Pattern compile3 = Pattern.compile("\\d*");
        Matcher matcher3 = compile3.matcher(code);
        Matcher matcher = compile.matcher(code);
        Matcher matcher2 = compile2.matcher(code);
        et_getCode.setText(code);
        if (matcher.matches()) {
            //是发货箱
            getOrderDetailByFbox(code);
            return;
        }
        if (matcher2.matches()) {

            getOrderDetailByOrder(code);
        } else {
            if (matcher3.matches()) {
                handleCode(orderFormat(code));
            } else {
                showMyToast("条码有误", "请检查条码");
            }
        }

    }


    private void getOrderDetailByFbox(String code) {


        OkHttpClientManager.getAsyn(AppClient.GETORDERDETAIL_BYFBOX_DPS + code, new OkHttpClientManager.ResultCallback<OrderInfoByOrder>() {

            @Override
            public void onError(Request request, Exception e) {

//				clearInfo();
            }

            @Override
            public void onResponse(OrderInfoByOrder response) {
                processInfo(response);

            }
        });


    }

    private void processInfo(OrderBeanForFbox response) {
        if ("1".equals(response.RESULT) && response.DATA != null) {
            tv_orderNum.setText(response.DATA.get(0).SHEETID);
            tv_sendBoxText.setText(et_getCode.getText().toString());
            tv_orderNum.setText(et_getCode.getText().toString());

            setData2(response.DATA);
            tv_order_Count.setText(String.valueOf(countTotal()));


        } else {
            clearInfo();
        }

    }

    private void processInfo(OrderInfoByOrder response) {
        LogUtil.i("数据", response.toString());
        if ("1".equals(response.RESULT) && response.DATA != null) {
            StringBuilder sb = new StringBuilder();

            for (int x = 0; x < response.DATA.get(0).FBOXS.size(); x++) {
                if (x < response.DATA.get(0).FBOXS.size() - 1) {
                    sb.append(response.DATA.get(0).FBOXS.get(x) + ",");
                } else {
                    sb.append(response.DATA.get(0).FBOXS.get(x));
                }
            }
            tv_sendBoxText.setText(sb.toString());
            tv_orderNum.setText(response.DATA.get(0).ORDERNO);
            setData(response.DATA.get(0).OrderInfoDetail);
            tv_order_Count.setText(String.valueOf(countTotal()));
        } else {
            if (listData.size() > 0) {
                listData.clear();
                adapter.refresh(listData);
                clearInfo();
            }
            et_getCode.setText("");
            showMyToast("无此订单信息", "请查证后重试");
        }
    }

    private void getOrderDetailByOrder(String code) {
        OkHttpClientManager.getAsyn(AppClient.GETORDERDETAIL_BYORDERNUM + code, new OkHttpClientManager.ResultCallback<OrderInfoByOrder>() {

            @Override
            public void onError(Request request, Exception e) {

//				clearInfo();
            }

            @Override
            public void onResponse(OrderInfoByOrder response) {

                processInfo(response);


            }
        });
    }

    private void clearInfo() {
        tv_sendBoxText.setText("");
        ;
        tv_orderNum.setText("");
        ;
        tv_order_Count.setText("");
        ;
//		 tv_sorting_Count.setText("");
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_cancCode:
                et_getCode.setText("");
                break;
            case R.id.btn_confCode:
                handleCode(et_getCode.getText().toString());
                break;

            default:
                break;
        }
    }

    private OrderDetailAdapter adapter;

    private void setData(ArrayList<ItemDetail> detail) {
        listData.clear();
        for (ItemDetail itd : detail) {
            BeanForDetail beanForDetail = new BeanForDetail(itd.ITEMNAME, itd.ITEMCODE, itd.QTY);
            listData.add(beanForDetail);
        }
        if (adapter == null) {
            adapter = new OrderDetailAdapter(listData, this);
            rv_details.setAdapter(adapter);

        } else {
            adapter.refresh(listData);
        }

    }

    /**
     * 设置list列表数据
     *
     * @param detail
     */
    private void setData2(ArrayList<OrderBeanForFbox.GoodsDetail> detail) {

        for (OrderBeanForFbox.GoodsDetail gdl : detail) {
            listData.clear();
            BeanForDetail beanForDetail = new BeanForDetail(gdl.GOODSNAME, gdl.GOODSCODE, gdl.QTY);
            listData.add(beanForDetail);
        }

        if (adapter == null) {
            adapter = new OrderDetailAdapter(listData, this);
            rv_details.setAdapter(adapter);

        } else {
            adapter.refresh(listData);
        }

    }

    private ArrayList<BeanForDetail> listData = new ArrayList<BeanForDetail>();

    public class BeanForDetail {

        public BeanForDetail(String goodsName, String goodsCode, double qty) {
            super();
            GoodsName = goodsName;
            GoodsCode = goodsCode;
            this.qty = qty;
        }

        public String GoodsName;
        public String GoodsCode;
        public double qty;

    }

    private double countTotal() {
        double total = 0;
        for (BeanForDetail bfd : listData) {
            total += bfd.qty;
        }
        return total;
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
}
