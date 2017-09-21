package coin.tracker.zxr.models;

import java.util.HashMap;

/**
 * Created by Mayur on 22-09-2017.
 */

public class CoinListResponse {
    HashMap<String, CoinListItem> Data;

    public HashMap<String, CoinListItem> getData() {
        return Data;
    }
}
