package com.dmi.devbook.ui;

import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.MenuItem;

import com.dmi.devbook.R;
import com.dmi.devbook.ui.fragments.MenuFragment;

import butterknife.ButterKnife;
import butterknife.InjectView;

public abstract class AbstractMenuActivity extends AppCompatActivity implements MenuFragment.MenuListener {

    @SuppressWarnings("checkstyle:visibilitymodifier")
    @InjectView(R.id.view_drawer)
    DrawerLayout mDrawerLayout;

    private ActionBarDrawerToggle mDrawerToggle;

    @Override
    protected void onPostCreate(final Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);

        ButterKnife.inject(this);

        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, R.drawable.ic_drawer,
                R.string.label_drawer_open, R.string.label_drawer_close);
        mDrawerLayout.setDrawerListener(mDrawerToggle);
        mDrawerLayout.setDrawerShadow(R.drawable.drawer_shadow, Gravity.START);

        final ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeButtonEnabled(true);

        mDrawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(final Configuration newConfig) {
        super.onConfigurationChanged(newConfig);

        mDrawerToggle.onConfigurationChanged(newConfig);
    }

    @Override
    public boolean onOptionsItemSelected(final MenuItem item) {
        return mDrawerToggle.onOptionsItemSelected(item)
                || super.onOptionsItemSelected(item);
    }

    public void onFragmentSelected(final Fragment fragment) {
        final FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.view_content, fragment)
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                .commit();

        if (mDrawerLayout != null) {
            mDrawerLayout.closeDrawers();
        }
    }
}
