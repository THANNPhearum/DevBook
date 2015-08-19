package com.dmi.devbook.intent;

import android.content.Context;
import android.content.Intent;

import com.dmi.devbook.model.Dev;
import com.dmi.devbook.ui.DetailActivity;
import com.google.gson.Gson;

public class DetailIntent extends Intent {

    public DetailIntent(final Context context, Dev dev) {
        super(context, DetailActivity.class);
        putExtra(Dev.TAG, new Gson().toJson(dev));
    }

}
