package coin.tracker.zxr.models;

import java.util.ArrayList;

/**
 * Created by Mayur on 27-09-2017.
 */

public class PriceDetailsResponse {

    ArrayList<PricePoint> Data;

    public ArrayList<PricePoint> getData() {
        return Data;
    }

    public void setData(ArrayList<PricePoint> data) {
        Data = data;
    }
}
