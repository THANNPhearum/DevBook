package com.dmi.devbook.ui.fragments;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.content.Loader;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.dmi.devbook.DevBookApplication;
import com.dmi.devbook.R;
import com.dmi.devbook.adapter.DevAdapter;
import com.dmi.devbook.intent.DetailIntent;
import com.dmi.devbook.listener.EndlessRecyclerOnScrollListener;
import com.dmi.devbook.listener.RecyclerItemClickListener;
import com.dmi.devbook.loader.DevLoader;
import com.dmi.devbook.model.Dev;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class DevFragment extends AbstractSpinnerFragment {

    private static final String ARG_DEV_TYPE = "ARG_DEV_TYPE";
    private RecyclerView mRecyclerView;
    private TextView mMessage;
    private List<Dev> mDevs;
    private SwipeRefreshLayout mSwipeRefreshLayout = null;
    private EndlessRecyclerOnScrollListener mRecyclerOnScrollListener;
    private DevAdapter mDevAdapter;

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
        mDevs.clear();
    }

    @Override
    public View onCreateContentView(final LayoutInflater inflater, final ViewGroup container,
                                    final Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_dev, container, false);
        mMessage = (TextView) view.findViewById(R.id.general_message);
        mRecyclerView = (RecyclerView) view.findViewById(R.id.my_recycler_view);
        mRecyclerView.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(linearLayoutManager);
        mRecyclerView.addOnItemTouchListener(
                new RecyclerItemClickListener(getActivity(), new RecyclerItemClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        if (mDevs != null) {
                            if (mDevs.size() > 0) {
                                startActivity(new DetailIntent(getActivity(), mDevs.get(position)));
                            }
                        }
                    }
                })
        );

        mSwipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipeRefreshLayout);
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                // Refresh items
                refreshItems(Dev.ANDROID_DEVELOPER);
            }
        });
        mRecyclerOnScrollListener = new EndlessRecyclerOnScrollListener(linearLayoutManager) {
            @Override
            public void onLoadMore(int current_page) {
                //load more
                boolean isLocal = (getDevType() == Dev.FAVORITE_DEVELOPER);
                if (!isLocal) {
                    refreshItems(current_page);
                }
            }
        };
        mRecyclerView.setOnScrollListener(mRecyclerOnScrollListener);
        mDevAdapter = new DevAdapter(mDevs, getActivity());
        mRecyclerView.setAdapter(mDevAdapter);
        return view;
    }

    void refreshItems(int page) {
        getLoaderManager().initLoader(R.id.loader_lorem, null, new AndroidDevLoaderCallbacks(getApplication(), page));
    }

    @Override
    public void onResume() {
        super.onResume();
        refreshItems(1);
    }

    private DevBookApplication getApplication() {
        final Activity activity = getActivity();
        return (DevBookApplication) activity.getApplication();
    }

    private int getDevType() {
        return getArguments().getInt(ARG_DEV_TYPE);
    }

    private final class AndroidDevLoaderCallbacks extends DevLoader.AbstractLoremLoaderCallbacks {

        private AndroidDevLoaderCallbacks(final DevBookApplication application, int page) {
            super(application, getDevType(), page);

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
            if (data != null) {
                mMessage.setVisibility(View.GONE);
                Log.d(Dev.TAG, "Data Size=" + data.size());
                mSwipeRefreshLayout.setRefreshing(false);
                boolean isLocal = (getDevType() == Dev.FAVORITE_DEVELOPER);
                if (isLocal) {
                    mDevs.clear();
                }
                for (Dev dev : data) {
                    dev.setLocal(isLocal);
                    mDevs.add(dev);
                }
                mRecyclerOnScrollListener.setLoading(data.size() == 0);
                mDevAdapter.notifyDataSetChanged();
            } else {
                mMessage.setVisibility(View.VISIBLE);
                String message = ((DevLoader) loader).getErrorMessage();
                mMessage.setText(message);
            }
        }
    }

}
