package multyunits.wochu.com.multyunits.bean;

import java.util.ArrayList;

public class BatchOrderBean {

    public String RESULT;
    public String MESSAGE;
    public ArrayList<BatchInfo> DATA;

    public class BatchInfo {
        public double SALEORDERFLAG;
        public String SHEETID;
        public String RATIONDATE;
        public String APPORDERNO;//波次号
        public String BATCHNUMBER;//批次号
        public String GOODSCODE;
        public String GOODSNAME;
        public double QTY;
    }
}
