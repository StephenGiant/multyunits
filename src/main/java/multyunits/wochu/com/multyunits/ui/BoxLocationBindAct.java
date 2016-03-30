package multyunits.wochu.com.multyunits.ui;

import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.okhttp.Request;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import multyunits.wochu.com.multyunits.R;
import multyunits.wochu.com.multyunits.adapter.PostFboxCacheAdapter;
import multyunits.wochu.com.multyunits.adapter.SimpleOrderAdapter;
import multyunits.wochu.com.multyunits.baseact.MyBaseActivity;
import multyunits.wochu.com.multyunits.bean.BatchInfo;
import multyunits.wochu.com.multyunits.bean.BatchNumberBean;
import multyunits.wochu.com.multyunits.bean.OrderDetail;
import multyunits.wochu.com.multyunits.bean.OrderFboxEntity;
import multyunits.wochu.com.multyunits.bean.SimpleInfo;
import multyunits.wochu.com.multyunits.bean.SimpleOrderBean;
import multyunits.wochu.com.multyunits.net.OkHttpClientManager;
import multyunits.wochu.com.multyunits.url.AppClient;
import multyunits.wochu.com.multyunits.utils.LogUtil;
import multyunits.wochu.com.multyunits.utils.SharePreUtil;
import multyunits.wochu.com.multyunits.wiget.NumericWheelAdapter;
import multyunits.wochu.com.multyunits.wiget.OnWheelScrollListener;
import multyunits.wochu.com.multyunits.wiget.WheelView;

public class BoxLocationBindAct extends MyBaseActivity implements OnClickListener {

    private ArrayAdapter<String> adapter_boci;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.boxlocationbind_layout);
        initView();


    }

    private String boci;

    private void initView() {
        ib_edit = (ImageButton) findViewById(R.id.ib_edit);
        tv_FboxNum = (TextView) findViewById(R.id.tv_FboxNum);
        tv_OrderNum = (TextView) findViewById(R.id.tv_OrderNum);
//		btn_serrch = (Button) findViewById(R.id.serch);
        postInfo = (Button) findViewById(R.id.postInfo);
        ib_edit.setOnClickListener(this);
//		btn_serrch.setOnClickListener(this);
        postInfo.setOnClickListener(this);
//		mSpinner_boci = (Spinner) findViewById(R.id.mSpinner);
//		et_batchNum = (TextView) findViewById(R.id.tv_batchNum);
        rv_orderInfo = (RecyclerView) findViewById(R.id.rv_orderInfo);
        LinearLayoutManager manager = new LinearLayoutManager(this);
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        rv_orderInfo.setLayoutManager(manager);
//		rv_orderInfo.addItemDecoration(new MyDivider(this, MyDivider.VERTICAL_LIST));
//		mSpinner_boci.setOnItemSelectedListener(new OnItemSelectedListener() {
//
//			@Override
//			public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//				boci = batchArray[position];
//				
//			}
//
//			@Override
//			public void onNothingSelected(AdapterView<?> parent) {
//				// TODO Auto-generated method stub
//				
//			}
//		});
        LinearLayout ll_rationdate = (LinearLayout) findViewById(R.id.ll_rationdate);
        et_date = (EditText) ll_rationdate.findViewById(R.id.et_date);
        getDataPick();
        et_date.setText(curTime);
//		getBoci(curTime);
//		getOrderInfo();
        getBatchInfo(curTime);
//		testNet();
        et_date.setOnClickListener(this);


    }

    OnWheelScrollListener scrollListener = new OnWheelScrollListener() {

        @Override
        public void onScrollingStarted(WheelView wheel) {

        }

        @Override
        public void onScrollingFinished(WheelView wheel) {
            // TODO Auto-generated method stub
            int n_year = year.getCurrentItem() + 1950;//
            int n_month = month.getCurrentItem() + 1;//
            initDay(n_year, n_month);
        }
    };
    private String curTime;
    private WheelView year;
    private WheelView month;
    private WheelView day;
    private PopupWindow datePopupWindow;

    private View getDataPick() {
        Calendar c = Calendar.getInstance();
        int curYear = c.get(Calendar.YEAR);
        int curMonth = c.get(Calendar.MONTH) + 1;//通过Calendar算出的月数要+1
        int curDate = c.get(Calendar.DATE);
        String month_str = "";
        String date_str = "";
        if (curMonth < 10) {
            month_str = "0" + curMonth;
        } else {
            month_str += curMonth;
        }
        if (curDate < 10) {
            date_str = "0" + curDate;
        } else {
            date_str += curDate;
        }
        curTime = curYear + "-" + month_str + "-" + date_str;
        final View view = View.inflate(this, R.layout.datapick, null);

        year = (WheelView) view.findViewById(R.id.year);
        year.setAdapter(new NumericWheelAdapter(1950, curYear));
        year.setLabel("年");
        year.setCyclic(true);
        year.addScrollingListener(scrollListener);

        month = (WheelView) view.findViewById(R.id.month);
        month.setAdapter(new NumericWheelAdapter(1, 12));
        month.setLabel("月");
        month.setCyclic(true);
        month.addScrollingListener(scrollListener);

        day = (WheelView) view.findViewById(R.id.day);
        initDay(curYear, curMonth);
        day.setLabel("日");
        day.setCyclic(true);

        year.setCurrentItem(curYear - 1950);
        month.setCurrentItem(curMonth - 1);
        day.setCurrentItem(curDate - 1);

        Button bt = (Button) view.findViewById(R.id.set);
        bt.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                String str = null;
                if ((month.getCurrentItem()) + 1 < 10 && (day.getCurrentItem() + 1) < 10) {
                    str = (year.getCurrentItem() + 1950) + "-0" + (month.getCurrentItem() + 1) + "-0" + (day.getCurrentItem() + 1);
                } else {

                    str = (year.getCurrentItem() + 1950) + "-" + (month.getCurrentItem() + 1) + "-" + (day.getCurrentItem() + 1);
                }
                Toast.makeText(BoxLocationBindAct.this, str, Toast.LENGTH_LONG).show();
                et_date.setText(str);
                curTime = str;
//				getOrderInfo();
                getBatchInfo(curTime);
                datePopupWindow.dismiss();
            }
        });
        Button cancel = (Button) view.findViewById(R.id.cancel);
        cancel.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                datePopupWindow.dismiss();
            }
        });
        return view;
    }

    private void initDay(int arg1, int arg2) {
        day.setAdapter(new NumericWheelAdapter(1, getDay(arg1, arg2), "%02d"));
    }

    private int getDay(int year, int month) {
        int day = 30;
        boolean flag = false;
        switch (year % 4) {
            case 0:
                flag = true;
                break;
            default:
                flag = false;
                break;
        }
        switch (month) {
            case 1:
            case 3:
            case 5:
            case 7:
            case 8:
            case 10:
            case 12:
                day = 31;
                break;
            case 2:
                day = flag ? 29 : 28;
                break;
            default:
                day = 30;

                break;
        }
        return day;
    }

    private String[] batchArray;

    private EditText et_date;


    /**
     * 获取波次数据
     * @param date
     * @return
     */
//	private void getBoci(String date){
//		LogUtil.i("访问地址", AppClient.GETBATCHNUMBER + date);
//		OkHttpClientManager.getAsyn(AppClient.GETBATCHNUMBER + date, new OkHttpClientManager.ResultCallback<BatchNumberBean>() {
//
//			@Override
//			public void onBefore(Request request) {
//				// TODO Auto-generated method stub
//				super.onBefore(request);
//			}
//
//			@Override
//			public void onError(Request request, Exception e) {
//				// TODO Auto-generated method stub
//				LogUtil.e("无响应", "url有误");
//			}
//
//			@Override
//			public void onResponse(BatchNumberBean response) {
////				LogUtil.i("响应", response);
//				processBociData(response);
//
//			}
//		});
//
//
//	}

    /**
     * 解析批次数组
     *
     * @param bean
     */
    private void processBociData(BatchNumberBean bean) {

        if (bean != null && bean.DATA != null) {

            batchArray = new String[bean.DATA.size()];
            for (int x = 0; x < bean.DATA.size(); x++) {
                LogUtil.i("波次号", bean.DATA.get(x));
                batchArray[x] = bean.DATA.get(x);
            }
            adapter_boci = new ArrayAdapter<String>(BoxLocationBindAct.this, android.R.layout.simple_spinner_item, batchArray);
            adapter_boci.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//		runOnUiThread(new Runnable() {
//			
//			@Override
//			public void run() {
//				LogUtil.i("看看", "ui线程");
//			mSpinner_boci.setAdapter(adapter_boci);
//				
//			}
//		});

//			mSpinner_boci.setVisibility(View.VISIBLE);
        } else {
            if (adapter_boci != null) {

                batchArray = new String[]{"无批次数据!!!"};
                adapter_boci = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, batchArray);
                adapter_boci.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//				mSpinner_boci.setAdapter(adapter_boci);
            }
//			adapter_boci.notifyDataSetChanged();
        }
    }


    private void showDatePopuwindow() {
        View view = getDataPick();
        datePopupWindow = new PopupWindow(view, LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
        datePopupWindow.setFocusable(true);
        datePopupWindow.setBackgroundDrawable(new BitmapDrawable());
        datePopupWindow.showAtLocation(view, Gravity.CENTER_HORIZONTAL, 0, 0);


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.et_date:
                showDatePopuwindow();
                break;
//	case R.id.serch:
//		//查询订单
//		LogUtil.i("点击查询了", "点了");
//		getOrderInfo();
//		break;
            case R.id.postInfo:
                //绑定发货箱
                //先检查发货箱
//		handleCode(tv_FboxNum.getText().toString());
                //提交缓存数据
                if (caches.size() > 0) {
                    bindFbox();
                }
                break;
            case R.id.ib_edit:
                showManualEditDialog();
                break;
            default:
                break;
        }
    }


    /**
     * 绑定发货箱
     */
    private void bindFbox() {


        OkHttpClientManager.postAsyn(AppClient.BINDFBOX_DPS, new OkHttpClientManager.ResultCallback<SimpleInfo>() {

            @Override
            public void onBefore(Request request) {
                // TODO Auto-generated method stub
                super.onBefore(request);
                showLoadingDialog("");
            }


            @Override
            public void onError(Request request, Exception e) {

                hideLoadingDialog();
            }

            @Override
            public void onResponse(SimpleInfo response) {
                hideLoadingDialog();
                LogUtil.i("成功", response.toString());
                tv_OrderNum.setText("");
                tv_FboxNum.setText("");
                caches.clear();
                pfa.refreshData(caches);

            }
        }, postParam(caches));

    }

    private void saveEntity(String code) {
        if (!TextUtils.isEmpty(tv_OrderNum.getText().toString())) {
            //保存缓存信息
            caches.add(new OrderFboxEntity(code, tv_OrderNum.getText().toString()));

            tv_FboxNum.setText("");
            tv_OrderNum.setText("");
            showSaveEntity();
        } else {
            tv_FboxNum.setText("");
            showMyToast("错误操作", "请先扫描订单！");
        }
    }


    private ArrayList<OrderFboxEntity> caches = new ArrayList<OrderFboxEntity>();

    /**
     * 请求体参数
     *
     * @return
     */
    private String postParams() {
        if (orderinfoList != null && !orderinfoList.isEmpty()) {
            int userID = SharePreUtil.getInteger(this, "userID", 3);
            String userName = SharePreUtil.getString(this, "OPRNAME", "sa");
            JSONArray jArray = new JSONArray();
            for (OrderFboxEntity of : caches) {
                JSONObject jsonObject = new JSONObject();
                try {
                    jsonObject.put("ORDERNO", of.OrderNum);
//			jsonObject.put("ORDERNO", tv_OrderNum.getText().toString());
                    jsonObject.put("EXCEPTYPE", 1);
                    jsonObject.put("USERID", userID);
                    jsonObject.put("CHECK_LOCATION", "");
                    jsonObject.put("USERNAME", userName);
                    JSONArray jsonArray = new JSONArray();
                    jsonArray.put(of.Fbox);
//			jsonArray.put(fboxCode);
                    jsonObject.put("FBOXS", jsonArray);
                    jArray.put(jsonObject);


                } catch (JSONException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
            LogUtil.i("请求体", jArray.toString());

            return jArray.toString();
        } else {
            return null;
        }
    }

    private String fboxCode;

    /**
     * 检查发货箱
     *
     * @param code
     */
    private void checkSendBox(final String code) {
        OkHttpClientManager.getAsyn(AppClient.SENDBOXSERVER + code, new OkHttpClientManager.ResultCallback<OrderDetail>() {

            @Override
            public void onError(Request request, Exception e) {
                // TODO Auto-generated method stub

            }

            @Override
            public void onResponse(OrderDetail response) {
                if (response.RESULT != "1" && response.DATA == null) {
                    //发货箱已解绑
                    tv_FboxNum.setText(code);

//					bindFbox();
                    saveEntity(code);
                } else {
                    showMyToast("发货箱未解绑", "不能绑定订单!");
                }

            }
        });

    }

    @Override
    public void handleCode(String code) {

        LogUtil.i("广播", "收到");
        Pattern compile = Pattern.compile("[BJLX]\\d{6}");
        Matcher matcher = compile.matcher(code);
        Pattern compile3 = Pattern.compile("\\d*");
        Matcher matcher3 = compile3.matcher(code);
        if (matcher.matches()) {
            fboxCode = code;
            //是发货箱条码,先检查发货箱
            checkSendBox(fboxCode);
        } else {
//判断是否为订单号
            //先判断上个订单有无完结


            if (tv_OrderNum.getText().toString() == null || TextUtils.isEmpty(tv_OrderNum.getText().toString())) {


                Pattern compile2 = Pattern.compile("[A-Z]\\d*");
                Matcher matcher2 = compile2.matcher(code);
                if (matcher2.matches()) {
                    //检查此订单是否存在

                    LogUtil.i("订单!", code);
                    if (orderinfoList != null && !orderinfoList.isEmpty()) {
                        LogUtil.i("遍历", "进来了");
                        //遍历集合
//					boolean flag = false;
//
//
//
//					for(BatchOrderBean.BatchInfo info :orderinfoList){
//						if(code.equals(info.SHEETID)){
//							flag = true;
//						}
//					}
                        if (getOrderState(code)) {

                            tv_OrderNum.setText(code);

                        } else {
                            showMyToast("无此订单或订单异常！", "请检查订单号!");
                            tv_OrderNum.setText("");
                        }
                    } else {

                        showMyToast("非法操作", "请先获取订单信息");

                    }
                } else {
                    //如果首个字符不是字母
                    if (matcher3.matches() && code != null && !TextUtils.isEmpty(code)) {
                        LogUtil.i("纯数字", code);
                        handleCode(orderFormat(code));
                    }
                }


            } else {
                showMaterialDialog("上一单未绑定完", "请先绑定一单再扫下一单");
            }
        }
    }


    private String orderFormat(String code) {
        if (code != null && !TextUtils.isEmpty(code)) {
            int parseInt = Integer.parseInt(code.charAt(0) + "");
            code = relation[parseInt - 1] + code.substring(1);
            LogUtil.i("处理后的订单", code);
        }
        return code;
    }

//	private void getOrderInfo(){
//		final long time1 = System.currentTimeMillis();
//		LogUtil.i("url地址", AppClient.GETODERBYBATCH+curTime+"&batchNumber="+4);
//		String url = AppClient.GETODERBYBATCH+curTime+"&batchNumber="+4;
////		String url = "http://192.168.15.112:8080/APIS/GetSaleOrderInfo?rationdate=2016/2/4&batchnumber=4";
////		String url = AppClient.GETODERBYBATCH+"2016-2-4"+"&batchNumber="+boci;
//		OkHttpClientManager.getAsyn(url, new OkHttpClientManager.ResultCallback<BatchOrderBean>() {
//
//			@Override
//			public void onBefore(Request request) {
//
//				super.onBefore(request);
//				showLoadingDialog("");
//			}
//			@Override
//			public void onError(Request request, Exception e) {
//				LogUtil.e("访问失败", "url有误");
//				hideLoadingDialog();
//			}
//
//			@Override
//			public void onResponse(BatchOrderBean response) {
//				long time2 = System.currentTimeMillis();
//				LogUtil.i("时间差", (time2-time1)+"");
//				LogUtil.i("响应", response.RESULT+"");
//				tv_OrderNum.setText("");
////				processOrder(response);
//				saveOrderInfo(response);
//				hideLoadingDialog();
//			}
//		});
//	}

    private ArrayList<BatchInfo.OrderNumInfo> orderinfoList;
    private ArrayList<SimpleOrderBean> simpleOrderInfo = new ArrayList<SimpleOrderBean>();
    //	private TextView et_batchNum;
    private RecyclerView rv_orderInfo;
    private SimpleOrderAdapter simpleOrderAdapter;
    private Button postInfo;

    private TextView tv_OrderNum;
    private TextView tv_FboxNum;
//	protected void processOrder(BatchOrderBean response) {
//		if(response!=null&&response.DATA!=null){
//			orderinfoList = response.DATA;
//
//			if(!simpleOrderInfo.isEmpty()){
//				simpleOrderInfo.clear();
//			}
//			String orderNum = null;
//			for(BatchOrderBean.BatchInfo info :orderinfoList){
//				if(orderNum!=info.SHEETID){
//					SimpleOrderBean bean = new SimpleOrderBean(info.SHEETID, info.APPORDERNO);
//					simpleOrderInfo.add(bean);
//				orderNum = info.SHEETID;
//				}
//			}
//			for(int x=0;x<simpleOrderInfo.size();x++){
//				LogUtil.i("订单", simpleOrderInfo.get(x).orderNum);
//			}
////			tv_OrderNum.setText(orderinfoList.get(0).SHEETID);
//			LogUtil.i("波次号", orderinfoList.get(0).APPORDERNO+"");
////			et_batchNum.setText(orderinfoList.get(0).APPORDERNO==null?"":orderinfoList.get(0).APPORDERNO);
//			if(simpleOrderAdapter==null){
//			simpleOrderAdapter = new SimpleOrderAdapter(this, simpleOrderInfo);
//			rv_orderInfo.setAdapter(simpleOrderAdapter);
//
//			}else{
//				simpleOrderAdapter.refresh(simpleOrderInfo);
//			}
//		}else{
//			if(!simpleOrderInfo.isEmpty()){
//			simpleOrderInfo.clear();
//			}
//			if(simpleOrderAdapter!=null){
//			simpleOrderAdapter.refresh(simpleOrderInfo);
//			}
//		}
//
//	}


//	private void saveOrderInfo(BatchOrderBean response){
//		if(response!=null&&response.DATA!=null){
//orderinfoList = response.DATA;
//
//			if(!simpleOrderInfo.isEmpty()){
//				simpleOrderInfo.clear();
//			}
//			//过滤重复数据
//			String orderNum = null;
//			for(BatchOrderBean.BatchInfo info :orderinfoList){
//				if(orderNum!=info.SHEETID){
//					SimpleOrderBean bean = new SimpleOrderBean(info.SHEETID, info.APPORDERNO);
//					simpleOrderInfo.add(bean);
//				orderNum = info.SHEETID;
//				}
//			}
//			for(int x=0;x<simpleOrderInfo.size();x++){
//				LogUtil.i("订单", simpleOrderInfo.get(x).orderNum);
//			}
//
//		}
//	}

    private PostFboxCacheAdapter pfa;

    /**
     * 将要提交的数据展示在recyclerview中
     */
    private void showSaveEntity() {
        if (caches.size() > 0) {
            if (pfa == null) {
                pfa = new PostFboxCacheAdapter(this, caches);
                rv_orderInfo.setAdapter(pfa);
            } else {
                pfa.refreshData(caches);
            }
        }
        if (caches.size() > 19) {
            //注销广播 防止弹出对话框的时候 操作工还扫订单
            unregist();
            showMaterialDialog("已扫20条订单", "将自动提交!", new OncheckListenner() {

                @Override
                public void onPositiveClick() {
                    regist();
                    bindFbox();
                }

                @Override
                public void onNagativeClick() {
                    regist();
                    bindFbox();

                }
            });
        }
    }

    /**
     * 检查订单状态
     *
     * @param order
     * @return
     */
    private boolean getOrderState(String order) {
        boolean flag = false;
        for (BatchInfo.OrderNumInfo info : orderinfoList) {
//			if(info.SHEETID.equals(order)&&info.SALEORDERFLAG<=10){
            if (info.ORDERNO.equals(order)) {
                flag = true;
            }
        }
        return flag;
    }

    private ArrayList<String> orderWords;
    //	private HashMap<String, String> relation = new HashMap<String, String>();
    private String[] relation = new String[]{"A", "I", "W", "H"};
    private ArrayList<SimpleOrderBean> operatedData = new ArrayList<SimpleOrderBean>();
    private ImageButton ib_edit;
    private EditText et_order_dialog;
    private EditText et_fbox_dialog;

    /**
     * 获取操作过的订单集合
     *
     * @param order
     * @return
     */
    private void getOpratedOrder(String order) {
        boolean flag = false;
        int x = 0;
        for (SimpleOrderBean info : simpleOrderInfo) {
            if (info.orderNum.equals(order)) {
                x++;
                operatedData.add(info);
                flag = true;
            }
        }
        if (flag) {
            tv_OrderNum.setText(order);
            simpleOrderAdapter.refresh(operatedData);
        } else {
            tv_OrderNum.setText("");
            showMyToast("无此订单或订单异常！", "请检查订单号!");
        }

    }

    private void testNet() {

        final long time1 = System.currentTimeMillis();
        String url = "http://116.228.118.218:9091/APIS/GetSaleOrderInfo?rationdate=2015/12/4&batchnumber=4";
//		String url = AppClient.GETODERBYBATCH_TEST+"2015-12-4"+"&batchNumber="+4;
        OkHttpClientManager.getAsyn(url, new OkHttpClientManager.ResultCallback<String>() {

            @Override
            public void onError(Request request, Exception e) {


            }

            @Override
            public void onResponse(String response) {
                long time2 = System.currentTimeMillis();
                LogUtil.i("时间差", (time2 - time1) + "");
            }
        });
    }

    private void showManualEditDialog() {
        View view = View.inflate(this, R.layout.dialog_addbox, null);

        et_fbox_dialog = (EditText) view.findViewById(R.id.et_fbox);
        showMaterialDialog("手动绑定", view, new OncheckListenner() {

            @Override
            public void onPositiveClick() {
                handleCode(et_fbox_dialog.getText().toString());

            }

            @Override
            public void onNagativeClick() {


            }
        });
    }


    private void getBatchInfo(String code) {
        OkHttpClientManager.getAsyn(AppClient.GETBATCHORDER_DPS + code, new OkHttpClientManager.ResultCallback<BatchInfo>() {
            @Override
            public void onBefore(Request request) {
                super.onBefore(request);
                showLoadingDialog("");
            }

            @Override
            public void onError(Request request, Exception e) {
                hideLoadingDialog();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        showMyToast("响应失败", "请检查网络");
                    }
                });
            }

            @Override
            public void onResponse(BatchInfo response) {
                hideLoadingDialog();
                processBatchInfo(response);
            }
        });
    }

    private void processBatchInfo(BatchInfo response) {
        if ("1".equals(response.RESULT) && response.DATA != null) {
            orderinfoList = response.DATA;
            for (BatchInfo.OrderNumInfo oni : response.DATA) {
                simpleOrderInfo.add(new SimpleOrderBean(oni.ORDERNO, oni.BATCH_NO));
            }
        } else {
            showMyToast("获取数据失败", "请检查配送日期");
        }
    }

    private String postParam(ArrayList<OrderFboxEntity> caches) {
        int userID = SharePreUtil.getInteger(this, "userID", 3);
        String userName = SharePreUtil.getString(this, "OPRNAME", "sa");
        if (!caches.isEmpty()) {

            JSONArray jsonArray = new JSONArray();
            for (OrderFboxEntity entity : caches) {
                try {
                    JSONObject jsonObject = new JSONObject().put("Key", entity.OrderNum);
                    JSONArray jArray = new JSONArray();

                    jArray.put(entity.Fbox);
                    jsonObject.put("Value", jArray);
                    jsonArray.put(jsonObject);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            return jsonArray.toString();
        }


        return "";
    }
}
