package coin.tracker.zxr.models;

import java.util.HashMap;

/**
 * Created by Mayur on 19-09-2017.
 */

public class DisplayPrice {
    String PRICE;
    String CHANGE24HOUR;
    String COINTAG;

    public DisplayPrice(HashMap hash) {
        if (hash.containsKey("PRICE")) {
            this.PRICE = (String) hash.get("PRICE");
        }

        if (hash.containsKey("CHANGE24HOUR")) {
            this.PRICE = (String) hash.get("CHANGE24HOUR");
        }

        if (hash.containsKey("COINTAG")) {
            this.PRICE = (String) hash.get("COINTAG");
        }
    }

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
}
