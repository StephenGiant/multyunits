package multyunits.wochu.com.multyunits.bean;

import java.util.ArrayList;

/**
 * Created by Administrator on 2016/3/23.
 */
public class BatchInfo {

    public String RESULT;
    public String MESSAGE;
    public ArrayList<OrderNumInfo> DATA;

    public class OrderNumInfo {
        public String ORDERNO;
        public String BATCH_NO;
    }
}
