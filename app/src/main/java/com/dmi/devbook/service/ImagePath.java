package com.dmi.devbook.service;

import android.content.Context;

import com.dmi.devbook.R;

/**
 * Created by thannphearum on 8/18/15.
 */
public class ImagePath {

    public static String getPhotoUrl(Context context, String fileName) {
        return context.getString(R.string.server_photo_url) + fileName;
    }
    public static String getPhotoThumbUrl(Context context, String fileName) {
        return context.getString(R.string.server_photo_thumb_url) + fileName;
    }
}
