package android.tracker.coin.webservice;

import android.tracker.coin.models.TestPostModel;

import io.reactivex.Observable;
import retrofit2.http.GET;

/**
 * Created by Mayur on 19-09-2017.
 */

public interface APIService {

    // FIXME change the urls
    @GET("posts/1")
    Observable<TestPostModel> getFirstPost();

}
