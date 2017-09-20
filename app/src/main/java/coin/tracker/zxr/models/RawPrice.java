package coin.tracker.zxr.models;

/**
 * Created by Mayur on 20-09-2017.
 */

public class RawPrice {

    String PRICE;
    String CHANGE24HOUR;
    String FROMSYMBOL;

    public String getPRICE() {
        return PRICE;
    }

    public void setPRICE(String PRICE) {
        this.PRICE = PRICE;
    }

    public String getCHANGE24HOUR() {
        return CHANGE24HOUR;
    }

    public void setCHANGE24HOUR(String CHANGE24HOUR) {
        this.CHANGE24HOUR = CHANGE24HOUR;
    }

    public String getFROMSYMBOL() {
        return FROMSYMBOL;
    }

    public void setFROMSYMBOL(String FROMSYMBOL) {
        this.FROMSYMBOL = FROMSYMBOL;
    }
}
