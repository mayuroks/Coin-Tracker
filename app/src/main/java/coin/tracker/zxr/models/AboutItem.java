package coin.tracker.zxr.models;

/**
 * Created by Mayur on 07-10-2017.
 */

public class AboutItem {

    String title;
    String subTitle;

    public AboutItem(String title, String subTitle) {
        this.title = title;
        this.subTitle = subTitle;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSubTitle() {
        return subTitle;
    }

    public void setSubTitle(String subTitle) {
        this.subTitle = subTitle;
    }
}
