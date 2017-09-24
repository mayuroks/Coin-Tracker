package coin.tracker.zxr.utils;

import com.orhanobut.hawk.Hawk;
import com.orhanobut.logger.Logger;

import java.util.ArrayList;
import java.util.HashMap;

import coin.tracker.zxr.models.CoinListItem;

/**
 * Created by Mayur on 20-09-2017.
 */

public class CoinHelper {

    private static CoinHelper INSTANCE;
    private static final int COIN_ITEMS_PER_PAGE = 50;
    private final String ALL_COINTAG_LIST = "allCoinList";
    private final String ALL_COIN_NAMES_LIST = "allCoinNamesList";
    private final String USER_COIN_LIST = "coinList";
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
    public void prePopulateUserCoins() {
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
    public ArrayList<String> getInitialUserCoins() {
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
    private void deleteCoinFromList(String symbol) {
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
    public void deleteUserCoin(String symbol) {
        Hawk.delete(symbol);
        deleteCoinFromList(symbol);
    }

    /*
    * Cache all universally available coins as <CoinTag, CoinName>
    * */
    public void updateAllCachedCoins(HashMap<String, CoinListItem> allCoins,
                                     boolean isForced) {
        ArrayList<String> allCachedCoins = getAllCachedCoins();
        ArrayList<String> allCachedCoinNames = new ArrayList<>();

        // update all cached coins only
        // if size is 0 OR if the update is forced
        if (allCachedCoins.size() == 0 || isForced) {
            for (String coinTag : allCoins.keySet()) {
                String coinName = allCoins.get(coinTag).getCoinName();

                if (TextUtils.isValidString(coinTag) &&
                        TextUtils.isValidString(coinName))
                    allCachedCoins.add(coinTag);
                allCachedCoinNames.add(coinName);

                // Save coinTag and coinName in Hawk
                Hawk.put(coinTag, coinName);
            }

            Hawk.put(ALL_COIN_NAMES_LIST, allCachedCoinNames);
            Hawk.put(ALL_COINTAG_LIST, allCachedCoins);

            Logger.i("INITLIST allCoinNames size " + allCachedCoinNames.size());
            Logger.i("INITLIST allCoinTags size " + allCachedCoins.size());
        }
    }

    /*
    * Get all cached coins
    * */
    public ArrayList<String> getAllCachedCoins() {
        return Hawk.get(ALL_COINTAG_LIST,
                new ArrayList<String>());
    }

    /*
    * Get cached coins in chunks rather than all at once
    * */
    public ArrayList<String> getCachedCoinsByPage(int page) {
        ArrayList<String> cachedCoins = getAllCachedCoins();
        ArrayList<String> pagedCoins = new ArrayList<>();

        int start = page * COIN_ITEMS_PER_PAGE;
        int end = page * COIN_ITEMS_PER_PAGE + COIN_ITEMS_PER_PAGE - 1;

        for (int i = start; i < end; i++) {
            if (cachedCoins.size() >= end) {
                pagedCoins.add(cachedCoins.get(i));
            }
        }

        return pagedCoins;
    }

    /*
    * AutoComplete results
    * */
    public ArrayList<String> getAllCoinsNames() {
        return Hawk.get(ALL_COIN_NAMES_LIST,
                new ArrayList<String>());
    }

}
