package multyunits.wochu.com.multyunits.ui;

import android.app.AlertDialog;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.okhttp.Request;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import multyunits.wochu.com.multyunits.R;
import multyunits.wochu.com.multyunits.adapter.SimpleOrderDetailAdapter;
import multyunits.wochu.com.multyunits.baseact.MyBaseActivity;
import multyunits.wochu.com.multyunits.bean.CheckOrderInfo;
import multyunits.wochu.com.multyunits.bean.OrderBeanForFbox;
import multyunits.wochu.com.multyunits.net.OkHttpClientManager;
import multyunits.wochu.com.multyunits.url.AppClient;
import multyunits.wochu.com.multyunits.utils.LogUtil;
import multyunits.wochu.com.multyunits.utils.SharePreUtil;
import multyunits.wochu.com.multyunits.wiget.DatePickWiget;

/**
 * 检查订单状态
 * @author Administrator
 *
 */
public class CheckFboxAct extends MyBaseActivity implements OnClickListener,MediaPlayer.OnCompletionListener{
	private EditText et_fbox;
	private String userName;
	private int userID;
	private Button button_post;

	private Button button_confirm;
	private Button button_cancel;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.checkfbox_layout);
		userName = SharePreUtil.getString(getApplicationContext(), "OPRNAME", "sa");
		userID = SharePreUtil.getInteger(getApplicationContext(), "userID", 3);
	
		initView();
//		playAlarm();
		
	}
	String date;
	private void initView(){
		
		ll_date_choose = (DatePickWiget) findViewById(R.id.ll_date_choose);
		date = ll_date_choose.getDate();
		tv_FboxNum = (TextView) findViewById(R.id.tv_FboxNum);
		tv_orderNum = (TextView) findViewById(R.id.tv_orderNum);
		
		sorting_place = (TextView) findViewById(R.id.sorting_place);
		et_fbox = (EditText) findViewById(R.id.et_fbox);


		button_confirm = (Button) findViewById(R.id.button_confirm);
		button_cancel = (Button) findViewById(R.id.button_cancel);

		tv_totalCount = (TextView) findViewById(R.id.tv_totalCount);

		button_confirm.setOnClickListener(this);
		button_cancel.setOnClickListener(this);
	ll_date_choose.setOnConfirmListenner(new DatePickWiget.onConfirmListenner() {
		
		@Override
		public void onConfirmClick() {
		
			date = ll_date_choose.getDate();
		}
	});
		

		rv_goodsDetail = (RecyclerView) findViewById(R.id.rv_goodsDetail);
		LinearLayoutManager manager = new LinearLayoutManager(this);
		manager.setOrientation(LinearLayoutManager.VERTICAL);
		rv_goodsDetail.setLayoutManager(manager);
		showChooseSortingPositionDialog();
	}

//	/**
//	 * 检查订单状态
//	 * @param code
//	 */
//	private void checkOrder(final String code){
//		OkHttpClientManager.getAsyn(AppClient.GETORDERSTATE + date + "&BoxNo=" + code, new OkHttpClientManager.ResultCallback<OrderCheckBean>() {
//
//			@Override
//			public void onError(Request request, Exception e) {
//				e.printStackTrace();
//				LogUtil.e("出错", "接口有误");
//
//			}
//
//			@Override
//			public void onResponse(OrderCheckBean response) {
//				processCheckInfo(response, code);
////				LogUtil.i("响应", response);
//
//			}
//		});
//	}
//
//	/**
//	 * 提交核单信息
//	 */
//	private void postSortOrder(String boxCode){
//		if(cb_isright.isChecked()&&boxCode!=null&&!TextUtils.isEmpty(boxCode)){
//		OkHttpClientManager.postAsyn(postParams(boxCode,userID,userName), new OkHttpClientManager.ResultCallback<SimpleInfo>() {
//
//			@Override
//			public void onError( Request request, Exception e) {
//
//
//			}
//
//			@Override
//			public void onResponse(SimpleInfo response) {
//			showMyToast("提交结果", response.MESSAGE);
//				et_fbox.setText("");
//				tv_totalCount.setText("");
//				cb_isright.setChecked(false);
//			}
//		});
//		}else{
//			showMyToast("非法操作", "订单信息有误");
//			//playAlarm();
//		}
//	}
	
//	private void processCheckInfo(OrderCheckBean response,String code){
//if(response.DATA!=null&&"0".equals(response.DATA.RESULT)){
//	tv_FboxNum.setText(et_fbox.getText().toString());
//
////	if(response.DATA==null){
////	postSortOrder(code);
//	orderCode = code;
//	cb_isright.setChecked(true);
//	OkHttpClientManager.getAsyn(AppClient.GETORDERDETAIL_BYFBOX + code, new OkHttpClientManager.ResultCallback<OrderBeanForFbox>() {
//
//		@Override
//		public void onError(Request request, Exception e) {
//			e.printStackTrace();
//
//		}
//
//		@Override
//		public void onResponse(OrderBeanForFbox response) {
////			LogUtil.i("QTY数量","(" +response.DATA+")");
//			processCount(response);
//
//		}
//	});
//}else{
//	showMaterialDialog("请检查发货箱!", response.MESSAGE);
//	warn();
//}
//
//	}

	private void warn(){
		playAlarm();

		clearinfo();
	}
	private void processCheckInfo(CheckOrderInfo response,String code){
			if("1".equals(response.RESULT)){
				if(response.DATA!=null){
					tv_FboxNum.setText(et_fbox.getText().toString());
				processCount(response);


				}else{
					showMaterialDialog("请检查发货箱!",response.MESSAGE );
					warn();
				}
			}
	}

	
	String orderCode;
	
	private RecyclerView rv_goodsDetail;

	private TextView tv_totalCount;
	private EditText et_sortingPlace;
	private AlertDialog checkLoacationDialog;
	private Button btn_confirm_checklocation;
	private Button btn_cancle_checklocation;
	private TextView sorting_place;
	private String checkLocation;
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.button_confirm:
			handleCode(et_fbox.getText().toString());
			break;
case R.id.button_cancel:
	et_fbox.setText("");
			break;

case R.id.btn_confirm_checklocation:
			checkLocation = et_sortingPlace.getText().toString();
	Pattern pattern = Pattern.compile("^[A-D]\\d{1,2}");
	Matcher matcher = pattern.matcher(checkLocation);
	
	if(!matcher.matches()){
		Toast.makeText(getApplicationContext(), "请输入正确复核位", Toast.LENGTH_SHORT).show();
		
	}else{
		sorting_place.setText(checkLocation);
		checkLoacationDialog.dismiss();
	}
	break;
case R.id.btn_cancle_checklocation:
//	checkLoacationDialog.dismiss();
	et_sortingPlace.setText("");
	break;
	

		default:
			break;
		}
		
	}
	
	private String postParams(String BoxNo,int UserID,String UserName){
		String url = AppClient.POSTODERSORT+BoxNo+"&UserID="+UserID+"&UserName="+UserName+"&CheckLocation="
	+checkLocation;
		return url;
	}
	@Override
	public void handleCode(String code){
		Pattern compile = Pattern.compile("[BJLX]\\d{6}");
		Matcher matcher = compile.matcher(code);
		if(matcher.matches()){
			checkOrder(code);
			et_fbox.setText(code);
		}else if(isCheckLocation(code)){
			sorting_place.setText(code);
		}else{
			showMyToast("条码有误", "请检查条码!");
			tv_totalCount.setText("");
			et_fbox.setText("");
			playAlarm();
		}
	}
	
private SimpleOrderDetailAdapter adapter;

//	/**
//	 * 解析数量
//	 * @param obf
//	 */
//	private void processCount(OrderBeanForFbox obf){
//		if(obf.DATA!=null){
////算出总数
//			LogUtil.i("data封装", obf.DATA.toString());
//			tv_totalCount.setText(getTotalCount(obf.DATA));
//			tv_orderNum.setText(""+obf.DATA.get(0).SHEETID);
//		if(adapter==null){
//			adapter = new SimpleOrderDetailAdapter(this, obf.DATA);
//			rv_goodsDetail.setAdapter(adapter);
//		}else{
//			adapter.refreshData(obf.DATA);
//		}
//		}else{
//
//			clearinfo();
//			LogUtil.e("出错", "无数据");
//		}
//	}

	private void processCount(CheckOrderInfo info){
		if(info.DATA!=null){
			tv_totalCount.setText(getTotalCount(info.DATA.get(0).OrderInfoDetail));
			tv_orderNum.setText(info.DATA.get(0).ORDERNO);
			if(adapter==null){
				adapter = new SimpleOrderDetailAdapter(this,info.DATA.get(0).OrderInfoDetail);
				rv_goodsDetail.setAdapter(adapter);
			}else{
				adapter.refreshData(info.DATA.get(0).OrderInfoDetail);
			}
		}else{
			clearinfo();
		}
	}
	
	/**
	 * 选择复核位
	 */
	private void showChooseSortingPositionDialog(){
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		View view = View.inflate(this, R.layout.sortingplace_dialog_layout, null);
		et_sortingPlace = (EditText) view.findViewById(R.id.et_sortingPlace);
		btn_confirm_checklocation = (Button) view.findViewById(R.id.btn_confirm_checklocation);
		btn_cancle_checklocation = (Button) view.findViewById(R.id.btn_cancle_checklocation);
		btn_confirm_checklocation.setOnClickListener(this);
		btn_cancle_checklocation.setOnClickListener(this);
		builder.setCancelable(false);
		checkLoacationDialog = builder.create();
		checkLoacationDialog.setView(view, 0, 0, 0, 0);
		checkLoacationDialog.show();
	}
	private MediaPlayer mPlayer;
	private TextView tv_FboxNum;
	private TextView tv_orderNum;
	private DatePickWiget ll_date_choose;

	private void playAlarm(){
		if(mPlayer==null){
			LogUtil.i("提示", "铃声");
			mPlayer = MediaPlayer.create(this, R.raw.msn);
			mPlayer.setOnCompletionListener(this);
			mPlayer.start();
		}else{
		
			mPlayer.stop();
			mPlayer.release();
			mPlayer=null;
		}
	}
	
	@Override
	public void onCompletion(MediaPlayer mp) {
		
		mPlayer.release();
		mPlayer=null;
	}

	private String getTotalCount(List<OrderBeanForFbox.GoodsDetail> data){
		int totalCount = 0;
		for(OrderBeanForFbox.GoodsDetail gd:data){
			totalCount += gd.QTY;
		}
		return String.valueOf(totalCount);
	}

	private String getTotalCount(ArrayList<CheckOrderInfo.CheckItemDetail> listData){
		double totalCount = 0;
		for(CheckOrderInfo.CheckItemDetail detail:listData){
			LogUtil.i("单数:",detail.QTY+"");
			totalCount+=detail.QTY;

		}
LogUtil.i("总数",totalCount+"");
		return String.valueOf(totalCount);
	}
	
	private void clearinfo(){
		tv_FboxNum.setText("");
		tv_orderNum.setText("");
		tv_totalCount.setText("");
		et_fbox.setText("");
		
	
		
		
		
	}
	
	private boolean isCheckLocation(String code){
		Pattern pattern = Pattern.compile("^[A-D]\\d{1,2}");
		Matcher matcher = pattern.matcher(code);
		return matcher.matches();
	}

	private void checkOrder(final String code){
		OkHttpClientManager.getAsyn(AppClient.GET_CONFIRMCHECKORDER + ll_date_choose.getDate() + "&FBoxNO=" + code + "&UserID=" + userID + "&UserName=" + userName, new OkHttpClientManager.ResultCallback<CheckOrderInfo>() {
			@Override
			public void onError(Request request, Exception e) {

			}

			@Override
			public void onResponse(CheckOrderInfo response) {

				processCheckInfo(response, code);
			}
		});
	}
}
