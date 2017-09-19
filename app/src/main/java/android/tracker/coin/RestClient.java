package android.tracker.coin;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Mayur on 19-09-2017.
 */

public class RestClient {

    // FIXME change base URL
    private static Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("https://jsonplaceholder.typicode.com/")
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
