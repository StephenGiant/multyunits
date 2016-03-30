package multyunits.wochu.com.multyunits.bean;

import java.util.ArrayList;

/**
 * Created by Administrator on 2016/3/27.
 */
public class OrderLocationBean {
    public String RESULT;
    public String MESSAGE;
    public ArrayList<OrderLocationDetail> DATA;

    public class OrderLocationDetail {
        public String APPORDERNO;
        public String BATCH_NO;
        public String BATCH_SUBNO;
    }

}
