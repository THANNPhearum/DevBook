package com.dmi.devbook.util;

import android.content.Context;
import android.graphics.drawable.Drawable;

import com.dmi.devbook.R;
import com.mikepenz.google_material_typeface_library.GoogleMaterial;
import com.mikepenz.iconics.IconicsDrawable;


public class DrawableUtil {

    public static final int COLOR_WITHE = R.color.color_white;
    public static final int COLOR_RED = R.color.color_red;
    private Context mContext;

    public DrawableUtil(Context myContext) {
        this.mContext = myContext;

    }

    public Drawable getAndroid(int color) {
        return new IconicsDrawable(mContext).icon(GoogleMaterial.Icon.gmd_android).colorRes(color).sizeDp(24);
    }

    public Drawable getPhone(int color) {
        return new IconicsDrawable(mContext).icon(GoogleMaterial.Icon.gmd_phone_iphone).colorRes(color).sizeDp(24);

    }

    public Drawable getEmail(int color) {
        return new IconicsDrawable(mContext).icon(GoogleMaterial.Icon.gmd_email).colorRes(color).sizeDp(24);

    }

    public Drawable getLocation(int color) {
        return new IconicsDrawable(mContext).icon(GoogleMaterial.Icon.gmd_map).colorRes(color).sizeDp(24);

    }

    public Drawable getDepartment(int color) {
        return new IconicsDrawable(mContext).icon(GoogleMaterial.Icon.gmd_class).colorRes(color).sizeDp(24);

    }

    public Drawable getBackend(int color) {
        return new IconicsDrawable(mContext).icon(GoogleMaterial.Icon.gmd_open_in_browser).colorRes(color).sizeDp(24);

    }

    public Drawable getIos(int color) {
        return new IconicsDrawable(mContext).icon(GoogleMaterial.Icon.gmd_phone_iphone).colorRes(color).sizeDp(24);

    }

    public Drawable getSetting(int color) {
        return new IconicsDrawable(mContext).icon(GoogleMaterial.Icon.gmd_settings).colorRes(color).sizeDp(24);

    }

    public Drawable getHelp(int color) {
        return new IconicsDrawable(mContext).icon(GoogleMaterial.Icon.gmd_help).colorRes(color).sizeDp(24);

    }

    public Drawable getSkype(int color) {
        return new IconicsDrawable(mContext).icon(GoogleMaterial.Icon.gmd_chat).colorRes(color).sizeDp(24);

    }

    public Drawable getFavorite(int color) {
        return new IconicsDrawable(mContext).icon(GoogleMaterial.Icon.gmd_favorite).colorRes(color).sizeDp(24);

    }

    public Drawable getDelete(int color) {
        return new IconicsDrawable(mContext).icon(GoogleMaterial.Icon.gmd_delete).colorRes(color).sizeDp(24);

    }

}
