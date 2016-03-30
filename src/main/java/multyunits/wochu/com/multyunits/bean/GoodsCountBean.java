package multyunits.wochu.com.multyunits.bean;

import java.util.ArrayList;

public class GoodsCountBean {

    public String MESSAGE;
    public String RESULT;
    public GoodsCountInfo DATA;

    public class GoodsCountInfo {
        public ArrayList<GoodsCount> Table;

        public class GoodsCount {
            public String ITEMCODE;
            public String ITEMNAME;
            public double SUMQTY;
            public double SUMPQTY;
        }
    }
}
