package multyunits.wochu.com.multyunits.bean;

import java.util.ArrayList;

public class OrderBeanForFbox {

    @Override
    public String toString() {
        return "OrderBeanForFbox [RESULT=" + RESULT + ", MESSAGE=" + MESSAGE + ", DATA=" + DATA + "]";
    }

    public String RESULT;
    public String MESSAGE;
    public ArrayList<GoodsDetail> DATA;

    public class GoodsDetail {

        public String GOODSCODE;
        public String GOODSNAME;
        public String SHEETID;
        public int QTY;

        @Override
        public String toString() {
            return "GoodsDetail [GOODSCODE=" + GOODSCODE + ", GOODSNAME=" + GOODSNAME + ", SHEETID=" + SHEETID
                    + ", QTY=" + QTY + "]";
        }


    }

}
