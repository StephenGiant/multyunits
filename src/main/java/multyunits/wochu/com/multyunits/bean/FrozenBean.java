package multyunits.wochu.com.multyunits.bean;

import java.io.Serializable;
import java.util.ArrayList;

public class FrozenBean implements Serializable {

    @Override
    public String toString() {
        return "FrozenBean [RESULT=" + RESULT + ", MESSAGE=" + MESSAGE + ", DATA=" + DATA + "]";
    }

    public String RESULT;
    public String MESSAGE;
    public ArrayList<FrozenDetail> DATA;

    public class FrozenDetail implements Serializable {
        public String GOODSCODE;
        public String GOODSNAME;
        public double QTY;

        @Override
        public String toString() {
            return "FrozenDetail [GOODSCODE=" + GOODSCODE + ", GOODSNAME=" + GOODSNAME + ", QTY=" + QTY + "]";
        }

    }

}
