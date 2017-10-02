package coin.tracker.zxr.webservice;

import com.orhanobut.logger.Logger;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Mayur on 19-09-2017.
 */

public class RestClient {

    private static String API_BASE_URL_1 = "https://min-api.cryptocompare.com/data/";
    private static String API_BASE_URL_2 = "https://www.cryptocompare.com/api/data/";

    private static String API_BASE_URL = "https://min-api.cryptocompare.com/data/";

    private static HttpLoggingInterceptor logging = getLoggerInterceptor();

    private static OkHttpClient okHttpClient = new OkHttpClient.Builder()
            .addInterceptor(logging)
            .addInterceptor(new Interceptor() {
                @Override
                public Response intercept(Chain chain) throws IOException {
                    Request original = chain.request();
                    Request request;
                    Response response;
                    String url = original.url().toString();
                    Logger.i("OLDURL => " + url);

                    if (url.contains("coinlist")) {
                        String newUrl = url.replace(API_BASE_URL_1, API_BASE_URL_2);
                        Request.Builder requestBuilder = original.newBuilder().url(newUrl);
                        request = requestBuilder.build();
                        response = chain.proceed(request);
                        Logger.i("NEWURL => " + newUrl);
                    } else {
                        response = chain.proceed(original);
                    }

                    return response;
                }
            })
            .build();

    private static Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(API_BASE_URL)
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build();

    private static APIService service = retrofit.create(APIService.class);

    public static APIService getAPIService() {
        if (service == null) {
            service = retrofit.create(APIService.class);
        }

        return service;
    }

    /*
    * https://stackoverflow.com/questions/39784243/cant-resolve-setlevel-on-httplogginginterceptor-retrofit2-0
    * */
    private static HttpLoggingInterceptor getLoggerInterceptor() {
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);
        return logging;
    }
}
