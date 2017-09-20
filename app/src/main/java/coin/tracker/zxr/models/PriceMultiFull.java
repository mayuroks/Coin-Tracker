package coin.tracker.zxr.models;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Mayur on 19-09-2017.
 */

public class PriceMultiFull {

    HashMap<String, HashMap<String, RawPrice>> RAW;
    HashMap<String, HashMap<String, DisplayPrice>> DISPLAY;

    public HashMap<String, HashMap<String, RawPrice>> getRAW() {
        return RAW;
    }

    public void setRAW(HashMap<String, HashMap<String, RawPrice>> RAW) {
        this.RAW = RAW;
    }

    public HashMap<String, HashMap<String, DisplayPrice>> getDISPLAY() {
        return DISPLAY;
    }

    public void setDISPLAY(HashMap<String, HashMap<String, DisplayPrice>> DISPLAY) {
        this.DISPLAY = DISPLAY;
    }

    public ArrayList<DisplayPrice> getDisplayPrices() {
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

    public ArrayList<RawPrice> getRawPrices() {
        ArrayList<RawPrice> rawPrices = new ArrayList<>();
        HashMap<String, HashMap<String, RawPrice>> rawHash = getRAW();

        for (HashMap h : rawHash.values()) {
            if (h.containsKey("INR")) {
                RawPrice rawPrice = (RawPrice) h.get("INR");
                rawPrices.add(rawPrice);
            }
        }

        return rawPrices;
    }
}
