package multyunits.wochu.com.multyunits.baseact;


import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import multyunits.wochu.com.multyunits.R;
import multyunits.wochu.com.multyunits.receiver.PDAReceiver;
import multyunits.wochu.com.multyunits.utils.NetUtils;
import multyunits.wochu.com.multyunits.utils.ToastUtil;
import multyunits.wochu.com.multyunits.wiget.MaterialDialog;

public abstract class MyBaseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
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

    //带标题栏的基类activity

    /**
     * 公共toast
     *
     * @param msg
     */
    public void ToastCheese(String msg) {
        ToastUtil.makeText(this, msg).show();
    }

    /**
     * 判断网络是否连接
     *
     * @return
     */
    protected boolean baseHasNet() {
        if (!NetUtils.isConnected(this)) {
            ToastCheese("亲，当前网络不给力!");
            return false;
        }
        return true;
    }

    /**
     * 加载圆形进度条
     */
    private static Dialog mProgressDialog;
    private MaterialDialog materialDialog;

    public void showLoadingDialog(String message) {

        if (mProgressDialog == null) {
            mProgressDialog = new Dialog(this, R.style.dialog_tran);
            View view = LayoutInflater.from(this).inflate(R.layout.progress_dialog, null);
            WindowManager.LayoutParams params = getWindow().getAttributes();
            params.height = ViewGroup.LayoutParams.MATCH_PARENT;
            params.width = ViewGroup.LayoutParams.MATCH_PARENT;
            //getWindow().setAttributes((android.view.WindowManager.LayoutParams) params);
            mProgressDialog.setCanceledOnTouchOutside(true);
            mProgressDialog.setCancelable(false);
            mProgressDialog.addContentView(view, params);
        }
        if (!mProgressDialog.isShowing()) {
            mProgressDialog.show();
        }
    }

    /**
     * 判断进度条是否已显示
     *
     * @return
     */
    public boolean isDialogShowing() {
        return mProgressDialog != null && mProgressDialog.isShowing();
    }

    /**
     * 隐藏进度条
     */
    public void hideLoadingDialog() {
        if (mProgressDialog != null && mProgressDialog.isShowing()) {
            mProgressDialog.dismiss();
        }
        mProgressDialog = null;
    }

    /****
     * 公用跳转方法
     */
    public void intentTo(Context packageContext, Class<?> cls) {
        Intent i = new Intent();
        i.setClass(packageContext, cls);
        startActivity(i);
    }

    /**
     * 自定义对话框
     *
     * @param title
     * @param message
     */
    public void showMaterialDialog(String title, String message) {
        if (materialDialog == null) {
            materialDialog = new MaterialDialog(this);
        }
        materialDialog.setTitle(title).setMessage(message).setPositiveButton("确定", new OnClickListener() {

            @Override
            public void onClick(View v) {
                materialDialog.dismiss();
                if (mListenner != null) {
                    mListenner.onPositiveClick();
                }

            }
        }).setNegativeButton("取消", new OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                materialDialog.dismiss();
                if (mListenner != null) {
                    mListenner.onNagativeClick();
                }
            }
        }).setCanceledOnTouchOutside(false).show();

    }

    /**
     * 带点击事件的普通对话框
     *
     * @param title
     * @param message
     * @param listenner
     */
    public void showMaterialDialog(String title, String message, OncheckListenner listenner) {
        mListenner = listenner;
        materialDialog = new MaterialDialog(this);

        materialDialog.setTitle(title).setMessage(message).setPositiveButton("确定", new OnClickListener() {

            @Override
            public void onClick(View v) {
                materialDialog.dismiss();
                if (mListenner != null) {
                    mListenner.onPositiveClick();
                }

            }
        }).setNegativeButton("取消", new OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                materialDialog.dismiss();
                if (mListenner != null) {
                    mListenner.onNagativeClick();
                }
            }
        }).setCanceledOnTouchOutside(false).show();

    }


    /**
     * 自定义布局的dialog
     *
     * @param title
     * @param contentView
     * @param listenner
     */
    public void showMaterialDialog(String title, int layout, OncheckListenner listenner) {
        mListenner = listenner;
        materialDialog = new MaterialDialog(this);
        materialDialog.setTitle(title).setContentView(layout).setPositiveButton("确定", new OnClickListener() {

            @Override
            public void onClick(View v) {
                if (mListenner != null) {
                    mListenner.onPositiveClick();
                }
                materialDialog.dismiss();
                materialDialog = null;
            }
        }).setNegativeButton("取消", new OnClickListener() {

            @Override
            public void onClick(View v) {
                if (mListenner != null) {
                    mListenner.onNagativeClick();
                }
                materialDialog.dismiss();
                materialDialog = null;

            }
        }).setCanceledOnTouchOutside(false).show();
        ;
    }


    /**
     * 自定义布局的dialog
     *
     * @param title
     * @param contentView
     * @param listenner
     */
    public void showMaterialDialog(String title, View view, OncheckListenner listenner) {
        mListenner = listenner;
        materialDialog = new MaterialDialog(this);
        materialDialog.setTitle(title).setContentView(view).setPositiveButton("确定", new OnClickListener() {

            @Override
            public void onClick(View v) {
                if (mListenner != null) {
                    mListenner.onPositiveClick();
                }
                materialDialog.dismiss();
                materialDialog = null;
            }
        }).setNegativeButton("取消", new OnClickListener() {

            @Override
            public void onClick(View v) {
                if (mListenner != null) {
                    mListenner.onNagativeClick();
                }
                materialDialog.dismiss();
                materialDialog = null;

            }
        }).setCanceledOnTouchOutside(false).show();
        ;
    }

    /**
     * 自定义提示框
     *
     * @param title
     * @param content
     */
    public void showMyToast(String title, String content) {
        LayoutInflater inflater = getLayoutInflater();
        View layout = inflater.inflate(R.layout.mytoast,
                (ViewGroup) findViewById(R.id.llToast));
        ImageView image = (ImageView) layout
                .findViewById(R.id.tvImageToast);
        image.setImageResource(R.mipmap.warn);
        TextView tv_title = (TextView) layout.findViewById(R.id.tvTitleToast);
        tv_title.setText(title);
        TextView text = (TextView) layout.findViewById(R.id.tvTextToast);
        text.setText(content);
        Toast toast = new Toast(getBaseContext());
        toast.setGravity(Gravity.CENTER_HORIZONTAL | Gravity.CENTER_VERTICAL, 12, 40);
        toast.setDuration(Toast.LENGTH_SHORT);
        toast.setView(layout);
        toast.show();
    }

    public OncheckListenner mListenner;
    protected PDAReceiver receiver;
    private IntentFilter scanDataIntentFilter;

    public void setOncheckListenner(OncheckListenner listenner) {
        mListenner = listenner;
    }

    public interface OncheckListenner {
        public void onPositiveClick();

        public void onNagativeClick();
    }

//	public void hideMaterialDialog(){
//		materialDialog.dismiss();
//		materialDialog = null;
//	}

    public abstract void handleCode(String code);

    @Override
    protected void onDestroy() {

        super.onDestroy();
        unregisterReceiver(receiver);
    }

    /**
     * 给子类需要强制注销的时候使用
     */
    public void unregist() {
        unregisterReceiver(receiver);
    }

    /**
     * 给子类需要强制注册广播的时候使用
     */
    public void regist() {
        registerReceiver(receiver, scanDataIntentFilter);
    }
}
