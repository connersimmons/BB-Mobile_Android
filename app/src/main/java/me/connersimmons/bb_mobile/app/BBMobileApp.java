package me.connersimmons.bb_mobile.app;

import android.app.Application;
import android.util.Log;

import io.realm.Realm;
import io.realm.RealmConfiguration;

/**
 * Created by connersimmons on 3/15/16.
 */
public class BBMobileApp extends Application {

    private static BBMobileApp instance;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        RealmConfiguration realmConfiguration = new RealmConfiguration.Builder(this).build();
        Realm.setDefaultConfiguration(realmConfiguration);

        Log.d(BBMobileApp.class.getName(), "REALM CONFIGURED!");
    }

    public static BBMobileApp getInstance() {
        return instance;
    }

}
