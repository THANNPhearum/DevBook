package com.dmi.devbook.loader;

import android.content.Context;
import android.support.v4.content.AsyncTaskLoader;

import java.util.List;

abstract class AbstractLoader<T> extends AsyncTaskLoader<List<T>> {

    private List<T> mResult;

    public AbstractLoader(final Context context) {
        super(context);
    }

    @Override
    protected void onStartLoading() {
        if (mResult == null) {
            forceLoad();
        } else {
            deliverResult(mResult);
        }
    }

    @Override
    public void deliverResult(final List<T> result) {
        super.deliverResult(result);
        mResult = result;
    }
}
