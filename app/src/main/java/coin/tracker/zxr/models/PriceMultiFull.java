package coin.tracker.zxr.models;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Mayur on 19-09-2017.
 */

public class PriceMultiFull {

    HashMap<String, HashMap> RAW;
    HashMap<String, HashMap<String, DisplayPrice>> DISPLAY;

    public HashMap<String, HashMap> getRAW() {
        return RAW;
    }

    public void setRAW(HashMap<String, HashMap> RAW) {
        this.RAW = RAW;
    }

    public HashMap<String, HashMap<String, DisplayPrice>> getDISPLAY() {
        return DISPLAY;
    }

    public void setDISPLAY(HashMap<String, HashMap<String, DisplayPrice>> DISPLAY) {
        this.DISPLAY = DISPLAY;
    }

    public ArrayList<DisplayPrice> getDisplayPrice() {
        ArrayList<DisplayPrice> displayPrices = new ArrayList<>();
        HashMap<String, HashMap<String, DisplayPrice>> displayHash = getDISPLAY();

        for (HashMap h : displayHash.values()) {
            if (h.containsKey("INR")) {
                DisplayPrice displayPrice = (DisplayPrice) h.get("INR");
                displayPrices.add(displayPrice);
            }
        }

        return displayPrices;
    }
}
