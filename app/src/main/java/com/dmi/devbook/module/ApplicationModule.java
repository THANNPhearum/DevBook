package com.dmi.devbook.module;

import android.app.Application;

import com.dmi.devbook.DevBookApplication;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by kevin on 7/6/15.
 */
@Module(library = true)
public class ApplicationModule {
    private final DevBookApplication mApp;

    public ApplicationModule(DevBookApplication app) {
        this.mApp = app;
    }

    @Provides
    @Singleton
    Application provideApplication() {
        return mApp;
    }
}
