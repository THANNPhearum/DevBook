package com.dmi.devbook.model;

/**
 * The Dev entity to store brief information of the developer.
 */
public class Dev {

    public static final int ANDROID_DEVELOPER = 1;
    public static final int IOS_DEVELOPER = 2;
    public static final int BACKEND_DEVELOPER = 3;
    private String mName;
    private String mPosition;
    private String mPhone;
    private String mSkype;
    private String mEmail;
    private String mPhoto;
    private String mDepartment;
    private String mLocation;

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

    @Override
    public String toString() {
        return "Name=" + this.mName + "\t" + "Phone=" + this.mPhone + "\n";
    }
}
