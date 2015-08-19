/*
 * Copyright 2014 Soichiro Kashima
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.dmi.devbook.ui;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.dmi.devbook.R;
import com.dmi.devbook.model.Dev;
import com.dmi.devbook.sqlite.DatabaseHelper;
import com.dmi.devbook.util.DrawableUtil;
import com.dmi.devbook.service.ImagePath;
import com.github.ksoichiro.android.observablescrollview.ObservableScrollView;
import com.github.ksoichiro.android.observablescrollview.ObservableScrollViewCallbacks;
import com.google.gson.Gson;
import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.j256.ormlite.dao.Dao;
import com.melnykov.fab.FloatingActionButton;
import com.squareup.picasso.Picasso;

import java.sql.SQLException;

/**
 * Warning: This example does not work on Android 2.3.
 */
public class DetailActivity extends FillGapBaseActivity<ObservableScrollView> implements ObservableScrollViewCallbacks, View.OnClickListener {

    private TextView mName, mPhone, mEmail, mPosition, mDepartment, mLocation, mSkype;
    private ImageView mPhoto, mIconEmail, mIconPhone, mIconDepartment, mIconLocation, mIconSkype;
    private Dev mDev;
    private DrawableUtil mDrawableUtil;
    private DatabaseHelper mDatabaseHelper;
    private Dao<Dev, Integer> mDevDao;

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_detail;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        String gsonDev = getIntent().getStringExtra(Dev.TAG);
        mDev = new Gson().fromJson(gsonDev, Dev.class);
        mDrawableUtil = new DrawableUtil(this);
        initDetailView();
        initDevDetail();
        try {
            mDevDao = getHelper().getDao();
        } catch (SQLException e) {
            Log.e(Dev.TAG, "Database exception", e);
        }
    }

    private void initDetailView() {
        mName = (TextView) findViewById(R.id.detail_name);
        mPhone = (TextView) findViewById(R.id.detail_phone);
        mEmail = (TextView) findViewById(R.id.detail_email);
        mPosition = (TextView) findViewById(R.id.detail_position);
        mDepartment = (TextView) findViewById(R.id.detail_department);
        mLocation = (TextView) findViewById(R.id.detail_location);
        mSkype = (TextView) findViewById(R.id.detail_skype);
        mPhoto = (ImageView) findViewById(R.id.detail_photo);

        mIconEmail = (ImageView) findViewById(R.id.detail_icon_email);
        mIconPhone = (ImageView) findViewById(R.id.detail_icon_phone);
        mIconLocation = (ImageView) findViewById(R.id.detail_icon_location);
        mIconDepartment = (ImageView) findViewById(R.id.detail_icon_department);
        mIconSkype = (ImageView) findViewById(R.id.detail_icon_skype);

        FloatingActionButton mBtnFavorite = (FloatingActionButton) findViewById(R.id.detail_btn_favorite);
        mBtnFavorite.setOnClickListener(this);
        if (mDev.isLocal()) {
            mBtnFavorite.setImageDrawable(mDrawableUtil.getDelete(DrawableUtil.COLOR_WITHE));
        } else {
            mBtnFavorite.setImageDrawable(mDrawableUtil.getFavorite(DrawableUtil.COLOR_WITHE));
        }

    }

    private void initDevDetail() {
        mIconDepartment.setImageDrawable(mDrawableUtil.getDepartment(DrawableUtil.COLOR_RED));
        mIconEmail.setImageDrawable(mDrawableUtil.getEmail(DrawableUtil.COLOR_RED));
        mIconLocation.setImageDrawable(mDrawableUtil.getLocation(DrawableUtil.COLOR_RED));
        mIconPhone.setImageDrawable(mDrawableUtil.getPhone(DrawableUtil.COLOR_RED));
        mIconSkype.setImageDrawable(mDrawableUtil.getSkype(DrawableUtil.COLOR_RED));

        mName.setText(mDev.getName());
        mPhone.setText(mDev.getPhone());
        mEmail.setText(mDev.getEmail());
        mPosition.setText(mDev.getPosition());
        mDepartment.setText(mDev.getDepartment());
        mLocation.setText(mDev.getLocation());
        mSkype.setText(mDev.getSkype());
        Picasso.with(this).load(ImagePath.getPhotoThumbUrl(this, mDev.getPhoto())).into(mPhoto);

    }


    @Override
    protected ObservableScrollView createScrollable() {
        ObservableScrollView scrollView = (ObservableScrollView) findViewById(R.id.scroll);
        scrollView.setScrollViewCallbacks(this);
        return scrollView;
    }

    @Override
    public void onClick(View v) {
        if (mDev.isLocal()) {
            //delete
            try {
                mDevDao.delete(mDev);
                Toast.makeText(this, getString(R.string.favorite_deleted), Toast.LENGTH_LONG).show();
                finish();
            } catch (SQLException ex) {
                //Error
            }
        } else {
            try {
                mDevDao.create(mDev);
                Toast.makeText(this, getString(R.string.favorite_saved), Toast.LENGTH_LONG).show();
            } catch (SQLException ex) {
                //Duplicate
                Toast.makeText(this, getString(R.string.favorite_already_added), Toast.LENGTH_LONG).show();
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

		/*
         * You'll need this in your class to release the helper when done.
		 */
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
