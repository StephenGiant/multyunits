package multyunits.wochu.com.multyunits.bean;

import java.util.ArrayList;

/**
 * Created by Administrator on 2016/3/25.
 */
public class CheckOrderInfo {
    public String RESULT;
    public String MESSAGE;
    public ArrayList<CheckInfoDetail> DATA;

    public class CheckInfoDetail {
        public String ORDERNO;

        public ArrayList<CheckItemDetail> OrderInfoDetail;
    }

    public class CheckItemDetail {
        public String ITEMCODE;
        public String ITEMNAME;
        public double QTY;
    }
}
