package coin.tracker.zxr.models;

/**
 * Created by Mayur on 19-09-2017.
 */

public class PricePoint {

    long time;
    float close;

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public float getClose() {
        return close;
    }

    public void setClose(float close) {
        this.close = close;
    }
}
