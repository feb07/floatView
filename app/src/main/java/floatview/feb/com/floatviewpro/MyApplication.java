package floatview.feb.com.floatviewpro;

import android.app.Application;

/**
 * Created by lilichun on 2018/11/22.
 */
public class MyApplication extends Application {

    private static MyApplication mApp;

    public static MyApplication getInstance() {
        return mApp;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mApp = this;

    }
}
