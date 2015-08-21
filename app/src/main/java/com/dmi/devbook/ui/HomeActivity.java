package com.dmi.devbook.ui;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;

import com.dmi.devbook.R;
import com.dmi.devbook.model.Dev;
import com.dmi.devbook.sqlite.DatabaseHelper;
import com.dmi.devbook.ui.fragments.DevFragment;
import com.dmi.devbook.util.DrawableUtil;
import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.j256.ormlite.dao.Dao;
import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.DrawerBuilder;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.SecondaryDrawerItem;
import com.mikepenz.materialdrawer.model.SectionDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;

import java.sql.SQLException;

public class HomeActivity extends AppCompatActivity {

    private static final String SELECTED_DRAWER = "SELECTED_DRAWER";
    private DrawableUtil mDrawableUtil;
    private DatabaseHelper mDatabaseHelper;
    private int mNbFavorite = 0;
    private int mDrawerSelected = Dev.ANDROID_DEVELOPER;
    private Toolbar mToolbar;
    private Dao<Dev, Integer> mDevDao;
    private Drawer mDrawer;

    public void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Uncomment these lines to start using bugsense
//        if(!BuildConfig.DEBUG) {
//            BugSenseHandler.initAndStartSession(HomeActivity.this, getResources().getString(R.string.bugsense_api_key));
//        }
        setContentView(R.layout.activity_home);
        // Handle Toolbar
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);

        if (savedInstanceState != null) {
            mDrawerSelected = savedInstanceState.getInt(SELECTED_DRAWER);
        }
        //Initial Drawable Icon
        mDrawableUtil = new DrawableUtil(this);

        //Create DrawerLayout
        mDrawer = new DrawerBuilder()
                .withActivity(this)
                .withHeader(R.layout.header)
                .addDrawerItems(
                        new PrimaryDrawerItem().withName(R.string.drawer_item_android).withIcon(mDrawableUtil.getAndroid(DrawableUtil.COLOR_RED)).withIdentifier(1),
                        new PrimaryDrawerItem().withName(R.string.drawer_item_ios).withIcon(mDrawableUtil.getIos(DrawableUtil.COLOR_RED)),
                        new PrimaryDrawerItem().withName(R.string.drawer_item_backend).withIcon(mDrawableUtil.getBackend(DrawableUtil.COLOR_RED)),
                        new PrimaryDrawerItem().withName(R.string.drawer_item_favorite).withIcon(mDrawableUtil.getFavorite(DrawableUtil.COLOR_RED)).withBadge(mNbFavorite + ""),
                        new SectionDrawerItem().withName(R.string.drawer_item_section_header),
                        new SecondaryDrawerItem().withName(R.string.drawer_item_settings).withIcon(mDrawableUtil.getSetting(DrawableUtil.COLOR_RED)),
                        new SecondaryDrawerItem().withName(R.string.drawer_item_help).withIcon(mDrawableUtil.getHelp(DrawableUtil.COLOR_RED))
                ).withOnDrawerItemClickListener(new Drawer.OnDrawerItemClickListener() {
                    @Override
                    public boolean onItemClick(AdapterView<?> parent, View view, int position, long id, IDrawerItem drawerItem) {
                        mDrawerSelected = position + 1;
                        if (drawerItem != null) {
                            switchFragment(mDrawerSelected);
                        }
                        return false;
                    }
                }).withOnDrawerListener(new Drawer.OnDrawerListener() {
                    @Override
                    public void onDrawerOpened(View view) {
                        //update the number of Favorite
                        updateFavorite();
                    }

                    @Override
                    public void onDrawerClosed(View view) {
                    }

                    @Override
                    public void onDrawerSlide(View view, float v) {
                    }
                }).build();
        //Load default
        setTitle(getString(R.string.drawer_item_android));
        switchFragment(mDrawerSelected);
        updateFavorite();
    }

    private void updateFavorite() {
        try {
            mDevDao = getHelper().getDao();
            mNbFavorite = (int) mDevDao.countOf();
            if (mNbFavorite > 0) {
                mDrawer.updateBadge(mNbFavorite + "", Dev.FAVORITE_DEVELOPER - 1);
            }
        } catch (SQLException e) {
            Log.e(Dev.TAG, "Database exception", e);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (mDrawerSelected == Dev.FAVORITE_DEVELOPER) {
            //Do refresh after delete
            switchFragment(Dev.FAVORITE_DEVELOPER);
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        //This method is called when the up button is pressed. Just the pop back stack.
        getSupportFragmentManager().popBackStack();
        return true;
    }

    private void switchFragment(int position) {
        if (position == Dev.ANDROID_DEVELOPER) {
            onFragmentSelected(DevFragment.newInstance(Dev.ANDROID_DEVELOPER));
            mToolbar.setTitle(getString(R.string.drawer_item_android));
            mToolbar.setNavigationIcon(mDrawableUtil.getAndroid(DrawableUtil.COLOR_WITHE));

        } else if (position == Dev.IOS_DEVELOPER) {
            onFragmentSelected(DevFragment.newInstance(Dev.IOS_DEVELOPER));
            mToolbar.setTitle(getString(R.string.drawer_item_ios));
            mToolbar.setNavigationIcon(mDrawableUtil.getIos(DrawableUtil.COLOR_WITHE));

        } else if (position == Dev.BACKEND_DEVELOPER) {
            onFragmentSelected(DevFragment.newInstance(Dev.BACKEND_DEVELOPER));
            mToolbar.setTitle(getString(R.string.drawer_item_backend));
            mToolbar.setNavigationIcon(mDrawableUtil.getBackend(DrawableUtil.COLOR_WITHE));

        } else {
            onFragmentSelected(DevFragment.newInstance(Dev.FAVORITE_DEVELOPER));
            mToolbar.setTitle(getString(R.string.drawer_item_favorite));
            mToolbar.setNavigationIcon(mDrawableUtil.getFavorite(DrawableUtil.COLOR_WITHE));

        }
    }


    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(SELECTED_DRAWER, mDrawerSelected);
    }

    public void onFragmentSelected(final Fragment fragment) {
        final FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.view_content, fragment)
                .setTransition(FragmentTransaction.TRANSIT_NONE)
                .commit();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //Release the helper when done.
        if (mDatabaseHelper != null) {
            OpenHelperManager.releaseHelper();
            mDatabaseHelper = null;
        }
    }

    private DatabaseHelper getHelper() {
        if (mDatabaseHelper == null) {
            mDatabaseHelper = new DatabaseHelper(getApplicationContext());
        }
        return mDatabaseHelper;
    }
}
