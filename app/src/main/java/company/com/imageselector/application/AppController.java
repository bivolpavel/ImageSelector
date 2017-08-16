package company.com.imageselector.application;

import android.app.Application;

import company.com.imageselector.utils.RealmManager;

/**
 * Created by Bivol Pavel on 15.08.2017.
 */

public class AppController extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        RealmManager.initRealm(this);
    }
}
