package coin.tracker.zxr.search;

/**
 * Created by Mayur on 22-09-2017.
 */

public interface SearchCoinListener {

    void onCoinSelected(String coinTag, String coinName);

    void onCoinUnselected(String coinTag, String coinName);

}
