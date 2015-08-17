package com.dmi.devbook.loader;

import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;

import com.dmi.devbook.TemplateApplication;
import com.dmi.devbook.service.LoremService;

import javax.inject.Inject;

public class LoremLoader extends AbstractLoader<String> {

    @SuppressWarnings("checkstyle:visibilitymodifier")
    @Inject
    LoremService mLoremService;

    private final int mParagraphs;

    public LoremLoader(final TemplateApplication application, final int paragraphs) {
        super(application);

        mParagraphs = paragraphs;

        application.inject(this);
    }

    @Override
    public String loadInBackground() {
        return mLoremService.getLorem(mParagraphs);
    }

    public abstract static class AbstractLoremLoaderCallbacks implements LoaderManager.LoaderCallbacks<String> {

        private final TemplateApplication mApplication;
        private final int mParagraphs;

        protected AbstractLoremLoaderCallbacks(final TemplateApplication application, final int paragraphs) {
            mApplication = application;
            mParagraphs = paragraphs;
        }

        @Override
        public LoremLoader onCreateLoader(final int id, final Bundle args) {
            return new LoremLoader(mApplication, mParagraphs);
        }

        @Override
        public abstract void onLoadFinished(final Loader<String> loader, final String lorem);

        @Override
        public void onLoaderReset(final Loader loader) {
            // unused
        }
    }
}
