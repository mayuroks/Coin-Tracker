package coin.tracker.zxr.utils;

import android.content.Context;
import android.content.Intent;
import android.support.v4.content.LocalBroadcastManager;

import com.orhanobut.hawk.Hawk;
import com.orhanobut.logger.Logger;

import java.util.ArrayList;
import java.util.HashMap;

import coin.tracker.zxr.models.CoinListItem;

/**
 * Created by Mayur on 20-09-2017.
 */

public class CoinHelper {

    private Context context;
    private static ArrayList<String> allCachedCoinTags = new ArrayList<>();
    private static ArrayList<String> allCachedCoinNames = new ArrayList<>();
    private static CoinHelper INSTANCE;
    private static final int COIN_ITEMS_PER_PAGE = 50;
    public static final String ALL_COINS_CACHING_COMPLETED = "allCoinsCachingCompleted";
    private final String ALL_COIN_TAGS_LIST = "allCoinList";
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

    public void setContext(Context context) {
        this.context = context;
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

        if (coins.contains(symbol)) {
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
        deleteCoinFromList(symbol);
    }

    /*
    * Cache all universally available coins as <CoinTag, CoinName>
    * */
    public void updateAllCachedCoins(HashMap<String, CoinListItem> allCoins,
                                     boolean isForced) {
        ArrayList<String> allCachedCoinTags1 = getAllCachedCoinTags();
        ArrayList<String> allCachedCoinNames1 = new ArrayList<>();

        // update all cached coins only
        // if size is 0 OR if the update is forced
        Logger.i("INITLIST allCoinTags started");
        if (allCachedCoinTags1.size() == 0 || isForced) {
            /*
            * Updating In memory first
            * */
            for (String coinTag : allCoins.keySet()) {
                String coinName = allCoins.get(coinTag).getCoinName();

                if (TextUtils.isValidString(coinTag) &&
                        TextUtils.isValidString(coinName))
                    allCachedCoinTags1.add(coinTag);
                allCachedCoinNames1.add(coinName);
            }

            allCachedCoinTags = allCachedCoinTags1;
            allCachedCoinNames = allCachedCoinNames1;

            /*
            * Updating in Hawk now
            * */
            for (String coinTag : allCoins.keySet()) {
                String coinName = allCoins.get(coinTag).getCoinName();

                if (TextUtils.isValidString(coinTag) &&
                        TextUtils.isValidString(coinName))
                    allCachedCoinTags1.add(coinTag);
                allCachedCoinNames1.add(coinName);

                // Save coinTag and coinName in Hawk
                Hawk.put(coinTag, coinName);
            }

            Logger.i("INITLIST allCoinTags inserted");

            Hawk.put(ALL_COIN_NAMES_LIST, allCachedCoinNames1);
            Logger.i("INITLIST allCoinNames size " + allCachedCoinNames1.size());

            Hawk.put(ALL_COIN_TAGS_LIST, allCachedCoinTags1);
            Logger.i("INITLIST allCoinTags size " + allCachedCoinTags1.size());

            if (context != null) {
                Intent intent = new Intent(ALL_COINS_CACHING_COMPLETED);
                LocalBroadcastManager.getInstance(context).sendBroadcast(intent);
                Logger.i("INITLIST allCoinTags broadcast sent");
            } else {
                Logger.i("INITLIST allCoinTags broadcast context is null");
            }
        }
    }

    /*
    * Get all cached coin tags
    * */
    public ArrayList<String> getAllCachedCoinTags() {
        if (allCachedCoinTags.size() == 0) {
            allCachedCoinTags = Hawk.get(ALL_COIN_TAGS_LIST,
                    new ArrayList<String>());
        }

        return allCachedCoinTags;
    }

    /*
    * AutoComplete results
    * */
    public ArrayList<String> getAllCachedCoinNames() {
        if (allCachedCoinNames.size() == 0) {
            allCachedCoinNames = Hawk.get(ALL_COIN_NAMES_LIST,
                    new ArrayList<String>());
        }

        return allCachedCoinNames;
    }

    /*
    * Get cached coins in chunks rather than all at once
    * */
    public ArrayList<String> getCachedCoinsByPage(int page) {
        ArrayList<String> cachedCoins = getAllCachedCoinTags();
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

}
