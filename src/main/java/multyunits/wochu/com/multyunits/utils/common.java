package multyunits.wochu.com.multyunits.utils;

import android.app.Activity;
import android.view.WindowManager;

public class common {
    /**
     * 显示输入法
     *
     * @param cont
     */
    public static void showSoftInput(Activity cont) {
        cont.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE | WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
    }

    /**
     * 隐藏输入法
     *
     * @param cont
     */
    public static void hideSoftInput(Activity cont) {
        cont.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN | WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
    }
}
