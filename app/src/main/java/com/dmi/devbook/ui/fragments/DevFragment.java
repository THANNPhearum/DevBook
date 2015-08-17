package com.dmi.devbook.ui.fragments;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.content.Loader;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.dmi.devbook.R;
import com.dmi.devbook.TemplateApplication;
import com.dmi.devbook.adapter.DevAdapter;
import com.dmi.devbook.loader.DevLoader;
import com.dmi.devbook.model.Dev;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class DevFragment extends AbstractSpinnerFragment {

    private static final String ARG_DEV_TYPE = "ARG_DEV_TYPE";
    private RecyclerView mRecyclerView;
    private RecyclerView.LayoutManager mLayoutManager;
    private DevAdapter mDevAdapter;
    private List<Dev> mDevs;

    public static DevFragment newInstance(final int devType) {
        final Bundle args = new Bundle();
        args.putInt(ARG_DEV_TYPE, devType);

        final DevFragment devFragment = new DevFragment();
        devFragment.setArguments(args);

        return devFragment;
    }

    @Override
    public void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setHasOptionsMenu(true);
        mDevs = new ArrayList<Dev>();
    }

    @Override
    public View onCreateContentView(final LayoutInflater inflater, final ViewGroup container,
                                    final Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_lorem, container, false);
        mRecyclerView = (RecyclerView) view.findViewById(R.id.my_recycler_view);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);


        return view;
    }

    @Override
    public void onActivityCreated(final Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getLoaderManager().initLoader(R.id.loader_lorem, null, new AndroidDevLoaderCallbacks(getApplication()));

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
            getLoaderManager().restartLoader(R.id.loader_lorem, null, new AndroidDevLoaderCallbacks(getApplication()));

            return true;
        } else {
            return false;
        }
    }

    private TemplateApplication getApplication() {
        final Activity activity = getActivity();
        return (TemplateApplication) activity.getApplication();
    }

    private int getDevType() {
        return getArguments().getInt(ARG_DEV_TYPE);
    }

    private final class AndroidDevLoaderCallbacks extends DevLoader.AbstractLoremLoaderCallbacks {

        private AndroidDevLoaderCallbacks(final TemplateApplication application) {
            super(application, getDevType());
        }

        @Override
        public DevLoader onCreateLoader(final int id, final Bundle args) {
            showSpinner();
            return super.onCreateLoader(id, args);
        }

        @Override
        public void onLoadFinished(Loader<List<Dev>> loader, List<Dev> data) {
            super.onLoadFinished(loader, data);
            hideSpinner();
            mDevs.clear();
            for (Dev dev : data) {
                mDevs.add(dev);
            }
            mDevAdapter = new DevAdapter(mDevs, getActivity());
            mRecyclerView.setAdapter(mDevAdapter);
            if (mDevAdapter != null)
                mDevAdapter.notifyDataSetChanged();

        }
    }
}
