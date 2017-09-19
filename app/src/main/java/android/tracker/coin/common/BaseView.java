package android.tracker.coin.common;

/**
 * Created by Mayur on 19-09-2017.
 */

public interface BaseView<T> {

    void initView();

    void setPresenter(T presenter);

}
