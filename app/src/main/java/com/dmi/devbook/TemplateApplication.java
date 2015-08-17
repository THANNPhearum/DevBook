package com.dmi.devbook;

import android.app.Application;

import com.dmi.devbook.module.Modules;
import com.google.android.gms.analytics.Tracker;

import javax.inject.Inject;

import dagger.ObjectGraph;
import timber.log.Timber;

public class TemplateApplication extends Application {

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

    public Tracker getTracker() {
        return mTracker;
    }

    public <T> T inject(final T t) {
        return mObjectsGraph.inject(t);
    }
}
