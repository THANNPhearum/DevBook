package com.dmi.devbook.loader;

import android.content.Context;
import android.support.v4.content.AsyncTaskLoader;

import com.dmi.devbook.DevBookApplication;
import com.dmi.devbook.R;

import java.util.List;

import retrofit.RetrofitError;
import retrofit.client.Response;

abstract class AbstractLoader<T> extends AsyncTaskLoader<List<T>> {

    private List<T> mResult;
    private boolean mIsNetworkError = false;
    private String mErrorMessage;
    private int mErrorCode;

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

    public boolean isNetworkError() {
        return mIsNetworkError;
    }

    public void setIsNetworkError(boolean isNetworkError) {
        mIsNetworkError = isNetworkError;
    }

    @Override
    public void deliverResult(final List<T> result) {
        super.deliverResult(result);
        mResult = result;
    }

    protected void reactToError(RetrofitError error) {
        if (error.getKind() == RetrofitError.Kind.NETWORK) {
            setIsNetworkError(true);
            setErrorMessage(getContext().getString(R.string.error_no_internet));
        } else {
            interpretError(error);
        }
    }

    protected abstract List<T> queryApiForData() throws RetrofitError;

    @Override
    protected List<T> onLoadInBackground() {
        try {
            if (DevBookApplication.get(getContext()).isNetworkAvailable()) {
                mResult = queryApiForData();
            }
        } catch (RetrofitError error) {
            reactToError(error);
        }
        return mResult;
    }

    /**
     * Basic implementation of error reading, this method
     * just sets the error code and the error message
     * found in the HTTP response to the loader.
     *
     * @param error
     */
    protected void interpretError(RetrofitError error) {
        Response response = error.getResponse();
        if (response != null) {
            setErrorCode(response.getStatus());
            setErrorMessage(error.getMessage());
        }
    }

    public String getErrorMessage() {
        return mErrorMessage;
    }

    public void setErrorMessage(String mErrorMessage) {
        this.mErrorMessage = mErrorMessage;
    }

    public int getErrorCode() {
        return mErrorCode;
    }

    public void setErrorCode(int mErrorCode) {
        this.mErrorCode = mErrorCode;
    }

}
