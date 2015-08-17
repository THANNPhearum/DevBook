package com.dmi.devbook.module;

import android.app.Application;

import com.dmi.devbook.BuildConfig;
import com.dmi.devbook.R;
import com.google.android.gms.analytics.GoogleAnalytics;
import com.google.android.gms.analytics.Logger;
import com.google.android.gms.analytics.Tracker;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by gguser on 10/10/14.
 */
@Module(
    complete = false,
    library = true)

public class GoogleAnalyticsModule {

    public GoogleAnalyticsModule() {
    }

    @Provides
    @Singleton
    Tracker provideTracker(Application app) {
        GoogleAnalytics analytics = GoogleAnalytics.getInstance(app);
        if (BuildConfig.DEBUG) {
            analytics.getLogger().setLogLevel(Logger.LogLevel.VERBOSE);
            analytics.setDryRun(true);
        }
        return analytics.newTracker(app.getString(R.string.tracking_id));
    }

}