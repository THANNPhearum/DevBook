package com.dmi.devbook.ui.fragments;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.content.Loader;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.dmi.devbook.R;
import com.dmi.devbook.TemplateApplication;
import com.dmi.devbook.adapter.DevAdapter;
import com.dmi.devbook.intent.DetailIntent;
import com.dmi.devbook.listener.RecyclerItemClickListener;
import com.dmi.devbook.loader.DevLoader;
import com.dmi.devbook.model.Dev;

import java.util.ArrayList;
import java.util.List;

public class DevFragment extends AbstractSpinnerFragment {

    private static final String ARG_DEV_TYPE = "ARG_DEV_TYPE";
    private RecyclerView mRecyclerView;
    private List<Dev> mDevs;
    private SwipeRefreshLayout mSwipeRefreshLayout = null;

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

       // setHasOptionsMenu(true);
        mDevs = new ArrayList<>();
    }

    @Override
    public View onCreateContentView(final LayoutInflater inflater, final ViewGroup container,
                                    final Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_dev, container, false);
        mRecyclerView = (RecyclerView) view.findViewById(R.id.my_recycler_view);
        mRecyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.addOnItemTouchListener(
                new RecyclerItemClickListener(getActivity(), new RecyclerItemClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        startActivity(new DetailIntent(getActivity(), mDevs.get(position)));
                    }
                })
        );

        mSwipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipeRefreshLayout);
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                // Refresh items
                refreshItems();
            }
        });

        return view;
    }

    void refreshItems() {
        getLoaderManager().initLoader(R.id.loader_lorem, null, new AndroidDevLoaderCallbacks(getApplication()));
    }

    @Override
    public void onResume() {
        super.onResume();
        refreshItems();
    }

    @Override
    public void onCreateOptionsMenu(final Menu menu, final MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);

        inflater.inflate(R.menu.fragment_dev, menu);
    }

    @Override
    public boolean onOptionsItemSelected(final MenuItem item) {
        final int id = item.getItemId();
        if (id == R.id.menu_refresh) {
            refreshItems();
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
            mSwipeRefreshLayout.setRefreshing(false);
            mDevs.clear();
            boolean isLocal = (getDevType() == Dev.FAVORITE_DEVELOPER);
            for (Dev dev : data) {
                dev.setLocal(isLocal);
                mDevs.add(dev);
            }
            DevAdapter mDevAdapter = new DevAdapter(mDevs, getActivity());
            mRecyclerView.setAdapter(mDevAdapter);
            mDevAdapter.notifyDataSetChanged();

        }
    }
}
