package coin.tracker.zxr.models;

import java.util.HashMap;

/**
 * Created by Mayur on 19-09-2017.
 */

public class DisplayPrice {
    String PRICE;
    String CHANGE24HOUR;
    String COINTAG;
    String FROMSYMBOL;

    public String getCHANGE24HOUR() {
        return CHANGE24HOUR;
    }

    public void setCHANGE24HOUR(String CHANGE24HOUR) {
        this.CHANGE24HOUR = CHANGE24HOUR;
    }

    public String getCOINTAG() {
        return COINTAG;
    }

    public void setCOINTAG(String COINTAG) {
        this.COINTAG = COINTAG;
    }

    public String getPRICE() {
        return PRICE;
    }

    public void setPRICE(String PRICE) {
        this.PRICE = PRICE;
    }

    public String getFROMSYMBOL() {
        return FROMSYMBOL;
    }

    public void setFROMSYMBOL(String FROMSYMBOL) {
        this.FROMSYMBOL = FROMSYMBOL;
    }
}
