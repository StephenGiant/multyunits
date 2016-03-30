package multyunits.wochu.com.multyunits.bean;

import java.util.ArrayList;

public class OrderInfoByOrder {


    public String RESULT;
    public String MESSAGE;
    public ArrayList<Detail> DATA;

    @Override
    public String toString() {
        return "OrderInfoByOrder [RESULT=" + RESULT + ", MESSAGE=" + MESSAGE + ", DATA=" + DATA + "]";
    }

    public class Detail {
        public String ORDERNO;
        public ArrayList<ItemDetail> OrderInfoDetail;
        public ArrayList<String> FBOXS;

        @Override
        public String toString() {
            return "Detail [ORDERNO=" + ORDERNO + ", OrderInfoDetail=" + OrderInfoDetail + ", FBOXS=" + FBOXS + "]";
        }

    }

    public class ItemDetail {

        @Override
        public String toString() {
            return "ItemDetail [ITEMCODE=" + ITEMCODE + ", ITEMNAME=" + ITEMNAME + ", QTY=" + QTY + "]";
        }

        public String ITEMCODE;
        public String ITEMNAME;
        public double QTY;
    }
}
