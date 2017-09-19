package android.tracker.coin.webservice;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Mayur on 19-09-2017.
 */

public class RestClient {

    private static String API_BASE_URL = "https://min-api.cryptocompare.com/data/";

    private static Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(API_BASE_URL)
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .build();

    private static APIService service = retrofit.create(APIService.class);

    public static APIService getAPIService() {
        if (service == null) {
            service = retrofit.create(APIService.class);
        }

        return service;
    }
}
