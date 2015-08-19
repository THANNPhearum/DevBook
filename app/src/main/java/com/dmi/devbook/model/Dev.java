package com.dmi.devbook.model;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * The Dev entity to store brief information of the developer.
 */


@DatabaseTable(tableName = "devs")
public class Dev {
    public static final String TAG = "DEV";
    public static final int ANDROID_DEVELOPER = 1;
    public static final int IOS_DEVELOPER = 2;
    public static final int BACKEND_DEVELOPER = 3;
    public static final int FAVORITE_DEVELOPER = 4;
    @DatabaseField(id = true)
    private String mName;
    @DatabaseField
    private String mPosition;
    @DatabaseField
    private String mPhone;
    @DatabaseField
    private String mSkype;
    @DatabaseField
    private String mEmail;
    @DatabaseField
    private String mPhoto;
    @DatabaseField
    private String mDepartment;
    @DatabaseField
    private String mLocation;
    private boolean local;

    public Dev() {

    }

    public String getName() {
        return mName;
    }

    public void setName(String mName) {
        this.mName = mName;
    }

    public String getPosition() {
        return mPosition;
    }

    public void setPosition(String mPosition) {
        this.mPosition = mPosition;
    }

    public String getPhone() {
        return mPhone;
    }

    public void setPhone(String mPhone) {
        this.mPhone = mPhone;
    }

    public String getSkype() {
        return mSkype;
    }

    public void setSkype(String mSkype) {
        this.mSkype = mSkype;
    }

    public String getEmail() {
        return mEmail;
    }

    public void setEmail(String mEmail) {
        this.mEmail = mEmail;
    }

    public String getPhoto() {
        return mPhoto;
    }

    public void setPhoto(String mPhoto) {
        this.mPhoto = mPhoto;
    }

    public String getDepartment() {
        return mDepartment;
    }

    public void setDepartment(String mDepartment) {
        this.mDepartment = mDepartment;
    }

    public String getLocation() {
        return mLocation;
    }

    public void setLocation(String mLocation) {
        this.mLocation = mLocation;
    }

    public boolean isLocal() {
        return local;
    }

    public void setLocal(boolean local) {
        this.local = local;
    }
}
