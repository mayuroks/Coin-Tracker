package coin.tracker.zxr.utils;

import com.orhanobut.hawk.Hawk;

import java.util.ArrayList;
import java.util.HashMap;

import coin.tracker.zxr.models.CoinListItem;

/**
 * Created by Mayur on 20-09-2017.
 */

public class CoinHelper {

    private static CoinHelper INSTANCE;
    private final String ALL_COIN_LIST = "allCoinList" ;
    private final String USER_COIN_LIST = "coinList" ;
    private final String BTC = "BTC", ETH = "ETH", LTC = "LTC";
    private final String Bitcoin = "Bitcoin",
            Ethereum = "Ethereum",
            Litecoin = "Litecoin";


    // Prevent direct instantiation
    private CoinHelper() {

    }

    public static CoinHelper getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new CoinHelper();
        }

        return INSTANCE;
    }

    /*
    * Initiate user coins with ETH, BTC, LTC
    * */
    public void init() {
        if (!Hawk.contains(BTC)) {
            addUserCoin(BTC, Bitcoin);
        }

        if (!Hawk.contains(ETH)) {
            addUserCoin(ETH, Ethereum);
        }

        if (!Hawk.contains(LTC)) {
            addUserCoin(LTC, Litecoin);
        }
    }

    /*
    * Get pre-populated coins
    * */
    public ArrayList<String> getInitialCoins() {
        ArrayList<String> initialCoins = new ArrayList<>();
        initialCoins.add(BTC);
        initialCoins.add(ETH);
        initialCoins.add(LTC);

        return initialCoins;
    }

    /*
    * Get a coin name based on coinsymbol
    * */
    public String getCoinName(String symbol) {
        String coinName = null;

        if (Hawk.contains(symbol)) {
            coinName = Hawk.get(symbol);
        }

        return coinName;
    }

    /*
    * Get all save coins
    * */
    public ArrayList<String> getAllUserCoins() {
        return Hawk.get(USER_COIN_LIST, new ArrayList<String>());
    }

    /*
    * Add a coin to list of user tracked coin-symbols
    * */
    private void addCoinToList(String symbol) {
        ArrayList<String> coins = Hawk.get(USER_COIN_LIST, new ArrayList<String>());

        if (!coins.contains(symbol)) {
            coins.add(symbol);
        }

        Hawk.put(USER_COIN_LIST, coins);
    }

    /*
    * Add a coin to list of user tracked coin-symbols
    * */
    public void deleteCoinFromList(String symbol) {
        ArrayList<String> coins = Hawk.get(USER_COIN_LIST, new ArrayList<String>());

        if (!coins.contains(symbol)) {
            coins.remove(symbol);
            Hawk.put(USER_COIN_LIST, coins);
        }
    }

    /*
    * Save a coin with key as Symbol and value as name
    * And add the symbol to list
    * */
    public void addUserCoin(String symbol, String coinName) {
        Hawk.put(symbol, coinName);
        addCoinToList(symbol);
    }

    /*
    * Remove a tracked coin based on symbol
    * */
    public void deleteCoin(String symbol) {
        Hawk.delete(symbol);
        deleteCoinFromList(symbol);
    }

    /*
    * Cache all universally available coins as <CoinTag, CoinName>
    * */
    public void updateAllCachedCoins(HashMap<String, CoinListItem> allCoins,
                              boolean isForced) {
        HashMap<String, String> allCachedCoins = getAllCachedCoins();

        // update all cached coins only
        // if size is 0 OR if the update is forced
        if (allCachedCoins.size() == 0 || isForced) {
            for (String coinTag : allCoins.keySet()) {
                String coinName = allCoins.get(coinTag).getCoinName();

                if (TextUtils.isValidString(coinTag) &&
                        TextUtils.isValidString(coinName))
                allCachedCoins.put(coinTag, coinName);
            }

            Hawk.put(ALL_COIN_LIST, allCachedCoins);
        }
    }

    /*
    * Get all cached coins
    * */
    public HashMap<String, String> getAllCachedCoins() {
        return Hawk.get(ALL_COIN_LIST,
                new HashMap<String, String>());
    }
}
