package coin.tracker.zxr.models;

import java.util.HashMap;

/**
 * Created by Mayur on 20-09-2017.
 */

public class RawPrice {

    String PRICE;
    String CHANGE24HOUR;
    String COINTAG;

    public RawPrice(HashMap hash) {
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
}
