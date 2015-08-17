package com.dmi.devbook.ui.fragments;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.content.Loader;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.dmi.devbook.R;
import com.dmi.devbook.TemplateApplication;
import com.dmi.devbook.loader.LoremLoader;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class LoremFragment extends AbstractSpinnerFragment {

    private static final String ARG_PARAGRAPHS = "ARG_PARAGRAPHS";

    @SuppressWarnings("checkstyle:visibilitymodifier")
    @InjectView(R.id.view_lorem)
    TextView mLoremView;

    public static LoremFragment newInstance(final int paragraphs) {
        final Bundle args = new Bundle();
        args.putInt(ARG_PARAGRAPHS, paragraphs);

        final LoremFragment loremFragment = new LoremFragment();
        loremFragment.setArguments(args);

        return loremFragment;
    }

    @Override
    public void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateContentView(final LayoutInflater inflater, final ViewGroup container,
                                    final Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_lorem, container, false);

        ButterKnife.inject(this, view);

        return view;
    }

    @Override
    public void onActivityCreated(final Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        getLoaderManager().initLoader(R.id.loader_lorem, null,
                new LoremLoaderCallbacks(getApplication(), getParagraphs()));
    }

    @Override
    public void onCreateOptionsMenu(final Menu menu, final MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);

        inflater.inflate(R.menu.fragment_lorem, menu);
    }

    @Override
    public boolean onOptionsItemSelected(final MenuItem item) {
        final int id = item.getItemId();
        if (id == R.id.menu_refresh) {
            getLoaderManager().restartLoader(R.id.loader_lorem, null,
                    new LoremLoaderCallbacks(getApplication(), getParagraphs()));

            return true;
        } else {
            return false;
        }
    }

    private TemplateApplication getApplication() {
        final Activity activity = getActivity();
        return (TemplateApplication) activity.getApplication();
    }

    private int getParagraphs() {
        return getArguments().getInt(ARG_PARAGRAPHS);
    }

    private final class LoremLoaderCallbacks extends LoremLoader.AbstractLoremLoaderCallbacks {

        private LoremLoaderCallbacks(final TemplateApplication application, final int paragraphs) {
            super(application, paragraphs);
        }

        @Override
        public LoremLoader onCreateLoader(final int id, final Bundle args) {
            showSpinner();
            return super.onCreateLoader(id, args);
        }

        @Override
        public void onLoadFinished(final Loader<String> loader, final String lorem) {
            hideSpinner();
            mLoremView.setText(lorem);
        }
    }
}
