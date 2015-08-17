package com.dmi.devbook.ui.fragments;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.dmi.devbook.R;

import butterknife.ButterKnife;
import butterknife.InjectView;
import hugo.weaving.DebugLog;

public class MenuFragment extends Fragment {

    private static final int MENU_ENTRIES_DEFAULT = 0;
    private static final MenuEntry[] MENU_ENTRIES = new MenuEntry[]{
        new MenuEntry(R.string.label_menu_oneparagraph) {
            @Override
            public Fragment getFragment() {
                return LoremFragment.newInstance(1);
            }
        },
        new MenuEntry(R.string.label_menu_twoparagraphs) {
            @Override
            public Fragment getFragment() {
                return LoremFragment.newInstance(2);
            }
        },
        new MenuEntry(R.string.label_menu_threeparagraphs) {
            @Override
            public Fragment getFragment() {
                return LoremFragment.newInstance(3);
            }
        },
        new MenuEntry(R.string.label_menu_fourparagraphs) {
            @Override
            public Fragment getFragment() {
                return LoremFragment.newInstance(4);
            }
        },
        new MenuEntry(R.string.label_menu_fiveparagraphs) {
            @Override
            public Fragment getFragment() {
                return LoremFragment.newInstance(5);
            }
        },
    };

    @SuppressWarnings("checkstyle:visibilitymodifier")
    @InjectView(R.id.view_menu)
    ListView mMenuView;

    private MenuListener mMenuListener;

    @Override
    public View onCreateView(final LayoutInflater inflater, final ViewGroup container, final Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_menu, container, false);

        ButterKnife.inject(this, view);

        return view;
    }

    @Override
    public void onViewCreated(final View view, final Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        final MenuAdapter menuAdapter = new MenuAdapter(MENU_ENTRIES);

        mMenuView.setAdapter(menuAdapter);
        mMenuView.setOnItemClickListener(menuAdapter);
    }

    @Override
    public void onAttach(final Activity activity) {
        super.onAttach(activity);

        if (activity instanceof MenuListener) {
            mMenuListener = (MenuListener) activity;
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();

        mMenuListener = null;
    }

    @Override
    public void onActivityCreated(final Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        if (savedInstanceState == null && mMenuListener != null) {
            final MenuEntry defaultMenuEntry = MENU_ENTRIES[MENU_ENTRIES_DEFAULT];
            final Fragment defaultFragment = defaultMenuEntry.getFragment();
            if (defaultFragment != null) {
                mMenuListener.onFragmentSelected(defaultFragment);
            }
        }
    }

    private final class MenuAdapter extends BaseAdapter implements AdapterView.OnItemClickListener {

        private final MenuEntry[] mEntries;

        private MenuAdapter(final MenuEntry[] entries) {
            mEntries = entries;
        }

        @Override
        public int getCount() {
            if (mEntries != null) {
                return mEntries.length;
            } else {
                return 0;
            }
        }

        @Override
        public MenuEntry getItem(final int position) {
            return mEntries[position];
        }

        @Override
        public long getItemId(final int position) {
            return position;
        }

        @SuppressWarnings({"checkstyle:finalparameters", "checkstyle:parameterassignment"})
        @Override
        public View getView(final int position, View convertView,
                            final ViewGroup parent) {
            final Context context = parent.getContext();
            final H h;
            if (convertView == null) {
                h = new H();
                convertView = LayoutInflater.from(context).inflate(R.layout.view_menuentry, parent, false);
                convertView.setTag(h);
                ButterKnife.inject(h, convertView);
            } else {
                h = (H) convertView.getTag();
            }

            final MenuEntry menuEntry = getItem(position);
            h.mLabelView.setText(menuEntry.getLabel());

            return convertView;
        }

        @Override
        @DebugLog
        public void onItemClick(final AdapterView<?> parent, final View view, final int position, final long id) {
            if (mMenuListener != null) {
                final MenuEntry menuEntry = getItem(position);
                final Fragment fragment = menuEntry.getFragment();
                if (fragment != null) {
                    mMenuListener.onFragmentSelected(fragment);
                }
            }
        }
    }

    static class H {
        @SuppressWarnings("checkstyle:visibilitymodifier")
        @InjectView(R.id.view_label)
        TextView mLabelView;
    }

    private static class MenuEntry {

        private final int mLabel;

        protected MenuEntry(final int label) {
            mLabel = label;
        }

        public int getLabel() {
            return mLabel;
        }

        public Fragment getFragment() {
            return null;
        }
    }

    public interface MenuListener {

        void onFragmentSelected(Fragment fragment);
    }
}
