package android.tracker.coin.models;

import java.util.HashMap;

/**
 * Created by Mayur on 19-09-2017.
 */

public class PriceMultiFull {

    HashMap<String, HashMap> RAW;
    HashMap<String, HashMap> DISPLAY;

    public HashMap<String, HashMap> getRAW() {
        return RAW;
    }

    public void setRAW(HashMap<String, HashMap> RAW) {
        this.RAW = RAW;
    }

    public HashMap<String, HashMap> getDISPLAY() {
        return DISPLAY;
    }

    public void setDISPLAY(HashMap<String, HashMap> DISPLAY) {
        this.DISPLAY = DISPLAY;
    }
}
