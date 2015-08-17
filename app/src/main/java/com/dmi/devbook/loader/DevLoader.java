package com.dmi.devbook.loader;

import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;

import com.dmi.devbook.TemplateApplication;
import com.dmi.devbook.model.Dev;
import com.dmi.devbook.service.DevService;

import java.util.List;

import javax.inject.Inject;

public class DevLoader extends AbstractLoader<Dev> {

    @SuppressWarnings("checkstyle:visibilitymodifier")
    @Inject
    DevService mDevService;

    private int devType;

    public DevLoader(final TemplateApplication application, int devType) {
        super(application);
        application.inject(this);
        this.devType = devType;
    }

    @Override
    public List<Dev> loadInBackground() {
        if (devType == Dev.ANDROID_DEVELOPER) {
            return mDevService.getAndroidDevs();
        } else if (devType == Dev.IOS_DEVELOPER) {
            return mDevService.getIosDevs();
        } else {
            return mDevService.getBackendDevs();
        }
    }

    public abstract static class AbstractLoremLoaderCallbacks implements LoaderManager.LoaderCallbacks<List<Dev>> {
        private final TemplateApplication mApplication;
        private int devType;

        protected AbstractLoremLoaderCallbacks(final TemplateApplication application, int devType) {
            mApplication = application;
            this.devType = devType;
        }

        @Override
        public DevLoader onCreateLoader(int id, Bundle args) {
            return new DevLoader(mApplication, devType);
        }

        @Override
        public void onLoadFinished(Loader<List<Dev>> loader, List<Dev> data) {

        }


        @Override
        public void onLoaderReset(final Loader loader) {
            // unused
        }
    }
}
