package com.dmi.devbook;

import android.app.Application;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.dmi.devbook.module.Modules;
import com.google.android.gms.analytics.Tracker;

import javax.inject.Inject;

import dagger.ObjectGraph;
import timber.log.Timber;

public class DevBookApplication extends Application {

    @SuppressWarnings("checkstyle:visibilitymodifier")
    @Inject
    Tracker mTracker;

    private ObjectGraph mObjectsGraph;


    @Override
    public void onCreate() {
        super.onCreate();

        if (BuildConfig.DEBUG) {
            Timber.plant(new Timber.DebugTree());
        }
        mObjectsGraph = ObjectGraph.create(Modules.list(this));

    }
    public static DevBookApplication get(Context context) {
        return (DevBookApplication) context.getApplicationContext();
    }
    public Tracker getTracker() {
        return mTracker;
    }

    public <T> T inject(final T t) {
        return mObjectsGraph.inject(t);
    }

    public boolean isNetworkAvailable() {
        ConnectivityManager cm =
                (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnectedOrConnecting();
    }
}
