package com.dmi.devbook.module;

import android.app.Application;

import com.dmi.devbook.TemplateApplication;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by kevin on 7/6/15.
 */
@Module(library = true)
public class ApplicationModule {
    private final TemplateApplication mApp;

    public ApplicationModule(TemplateApplication app) {
        this.mApp = app;
    }

    @Provides
    @Singleton
    Application provideApplication() {
        return mApp;
    }
}
