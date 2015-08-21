package com.dmi.devbook.loader;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;

import com.dmi.devbook.DevBookApplication;
import com.dmi.devbook.model.Dev;
import com.dmi.devbook.service.DevService;
import com.dmi.devbook.sqlite.DatabaseHelper;
import com.j256.ormlite.dao.Dao;

import java.sql.SQLException;
import java.util.List;

import javax.inject.Inject;

public class DevLoader extends AbstractLoader<Dev> {

    @SuppressWarnings("checkstyle:visibilitymodifier")
    @Inject
    DevService mDevService;
    private Dao<Dev, Integer> mDevDao;
    private int mDevType;
    private int mPage;
    private DatabaseHelper mDatabaseHelper;
    private DevBookApplication mDevBookApplication = null;

    public DevLoader(final DevBookApplication application, int devType, int page) {
        super(application);
        application.inject(this);
        this.mDevBookApplication = application;
        this.mDevType = devType;
        this.mPage = page;
        try {
            this.mDevDao = getHelper(mDevBookApplication.getApplicationContext()).getDao();
        } catch (SQLException ex) {
            //Error
        }

    }


    @Override
    public List<Dev> loadInBackground() {
        if (mDevType == Dev.ANDROID_DEVELOPER) {
            //return mDevService.getAndroidDevs(mPage);
            return  mDevService.getDevs("android",mPage);
        } else if (mDevType == Dev.IOS_DEVELOPER) {
            return  mDevService.getDevs("ios", mPage);
        } else if (mDevType == Dev.BACKEND_DEVELOPER) {
            return  mDevService.getDevs("backend", mPage);
        } else {
            try {
                if (mDevDao != null) {
                    return mDevDao.queryForAll();
                } else {
                    return null;
                }
            } catch (SQLException ex) {
                //return null for empty favorite
            }
            return null;
        }
    }

    public abstract static class AbstractLoremLoaderCallbacks implements LoaderManager.LoaderCallbacks<List<Dev>> {
        private final DevBookApplication mApplication;
        private int mDevType;
        private int mPage;


        protected AbstractLoremLoaderCallbacks(final DevBookApplication application, int devType, int page) {
            mApplication = application;
            this.mDevType = devType;
            this.mPage = page;
        }

        @Override
        public DevLoader onCreateLoader(int id, Bundle args) {
            return new DevLoader(mApplication, mDevType, mPage);
        }

        @Override
        public void onLoadFinished(Loader<List<Dev>> loader, List<Dev> data) {

        }


        @Override
        public void onLoaderReset(final Loader loader) {
            // unused
        }
    }

    private DatabaseHelper getHelper(Context context) {
        if (mDatabaseHelper == null) {
            mDatabaseHelper = new DatabaseHelper(context);
        }
        return mDatabaseHelper;
    }
}
