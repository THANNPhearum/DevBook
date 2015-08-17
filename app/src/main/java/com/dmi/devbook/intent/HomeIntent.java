package com.dmi.devbook.intent;

import android.content.Context;
import android.content.Intent;

import com.dmi.devbook.ui.HomeActivity;

public class HomeIntent extends Intent {

    public HomeIntent(final Context context) {
        super(context, HomeActivity.class);
    }

}
