package multyunits.wochu.com.multyunits.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.okhttp.Request;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.regex.Pattern;

import multyunits.wochu.com.multyunits.R;
import multyunits.wochu.com.multyunits.adapter.FrozenAdapter;
import multyunits.wochu.com.multyunits.baseact.MyBaseActivity;
import multyunits.wochu.com.multyunits.bean.FrozenBean;
import multyunits.wochu.com.multyunits.decorator.MyDivider;
import multyunits.wochu.com.multyunits.net.OkHttpClientManager;
import multyunits.wochu.com.multyunits.url.AppClient;
import multyunits.wochu.com.multyunits.utils.LogUtil;
import multyunits.wochu.com.multyunits.wiget.DatePickWiget;

public class GetFrozenAct extends MyBaseActivity implements OnClickListener {

    //	private Spinner sp_batch;
    private DatePickWiget ll_date_choose;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.getfrozen_layout);
        initView();
    }

    private String[] batchArray;
    private RecyclerView rv_frozenDetail;

    private void initView() {
        tv_curName = (TextView) findViewById(R.id.tv_curName);
        tv_curQTY = (TextView) findViewById(R.id.tv_curQTY);
        ll_date_choose = (DatePickWiget) findViewById(R.id.ll_date_choose);
//		sp_batch = (Spinner) findViewById(R.id.sp_batch);
        rv_frozenDetail = (RecyclerView) findViewById(R.id.rv_frozenDetail);
        et_batch = (EditText) findViewById(R.id.et_batch);
        qurry_frozen = (Button) findViewById(R.id.qurry_frozen);
        ib_record = (ImageView) findViewById(R.id.ib_record);
        ib_record.setOnClickListener(this);
        qurry_frozen.setOnClickListener(this);
        LinearLayoutManager manager = new LinearLayoutManager(this);
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        rv_frozenDetail.setLayoutManager(manager);
        rv_frozenDetail.addItemDecoration(new MyDivider(this, MyDivider.VERTICAL_LIST));

        ll_date_choose.setOnConfirmListenner(new DatePickWiget.onConfirmListenner() {

            @Override
            public void onConfirmClick() {
                String date = ll_date_choose.getDate();
                LogUtil.i("日期1", date);


            }
        });
        processBatchInfo();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.qurry_frozen:
                recordList.clear();
                LogUtil.i("日期", ll_date_choose.getDate());
                getFrozenInfo(ll_date_choose.getDate(), et_batch.getText().toString());
                break;
            case R.id.ib_record:
//			showMyToast("点击了", "操作记录!");
                toRecord();
            default:
                break;
        }

    }

    /**
     * 解析批次信息
     */
    private void processBatchInfo() {

        batchArray = new String[]{"1", "2", "3"};
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, batchArray);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//		sp_batch.setAdapter(arrayAdapter);
    }

    @Override
    public void handleCode(String code) {
        //判断商品条码的正则
        Pattern compile = Pattern.compile("WOC\\d*");

        if (compile.matcher(code).matches()) {
            //是商品条码
            if (frozenList != null && frozenList.size() > 0) {
                int position = hasItem(code);
                if (position >= 0) {
                    LogUtil.i("索引", position + "");
                    tv_curName.setText(frozenList.get(position).GOODSNAME);
                    tv_curQTY.setText(frozenList.get(position).QTY + "");
                    recordList.add(frozenList.get(position));
                    rv_frozenDetail.scrollToPosition(position);
                    frozenList.remove(position);
//			fa.refreshData(frozenList);
                    fa.notifyItemRemoved(position);
                } else {
                    showMyToast("未找到此物品", "已扫描过或不在此波次中");
                }
            }

        } else {

            showMyToast("条码有误", "请检查条码！！！");

        }

    }

    private ArrayList<FrozenBean.FrozenDetail> recordList = new ArrayList<FrozenBean.FrozenDetail>();
    private ArrayList<FrozenBean.FrozenDetail> frozenList;

    private void getFrozenInfo(String date, String boci) {
        OkHttpClientManager.getAsyn(AppClient.GETFROZENITEM + date + "&batchNumber=" + boci, new OkHttpClientManager.ResultCallback<FrozenBean>() {

            @Override
            public void onBefore(Request request) {
                // TODO Auto-generated method stub
                super.onBefore(request);
                showLoadingDialog("");
            }

            @Override
            public void onError(Request request, Exception e) {
                LogUtil.e("响应失败", "url问题或者网络问题");
                hideLoadingDialog();
            }

            @Override
            public void onResponse(FrozenBean response) {
                hideLoadingDialog();
                processFrozen(response);
            }
        });
    }

    protected void processFrozen(FrozenBean response) {
        if (response.DATA != null) {
            frozenList = response.DATA;
            if (fa == null) {
                fa = new FrozenAdapter(frozenList, this);
                rv_frozenDetail.setAdapter(fa);
            } else {
                fa.refreshData(frozenList);
            }
        }
        LogUtil.i("数据", response.toString());
    }

    /**
     * 判断是否有该商品
     *
     * @param fd
     * @return
     */
    private int hasItem(String code) {

        int position = -1;
        for (int x = 0; x < frozenList.size(); x++) {
            if (frozenList.get(x).GOODSCODE.equals(code)) {
                position = x;
                break;
            }
        }

        return position;

    }

    private FrozenAdapter fa;
    //	String[] relation = new String[]{"A","I","W","H"};
    private Button qurry_frozen;
    //	private String orderFormat(String code){
//		if(code!=null&&!TextUtils.isEmpty(code)){
//		int parseInt = Integer.parseInt(code.charAt(0)+"");
//		code = relation[parseInt-1]+code.substring(1);
//		LogUtil.i("处理后的订单", code);
//		}
//		return code;
//	}
    private EditText et_batch;
    private ImageView ib_record;
    private TextView tv_curName;
    private TextView tv_curQTY;


    @Override
    protected void onRestart() {
        // TODO Auto-generated method stub
        super.onRestart();
        regist();
    }


    private void toRecord() {
        unregist();
        Intent intent = new Intent();
        intent.setClass(this, GetFrozenRecordAct.class);
        intent.putExtra("record", (Serializable) recordList);
//		intent.putExtra("record", (Serializable)frozenList);
        startActivity(intent);

    }
}
