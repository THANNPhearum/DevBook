package com.dmi.devbook.service;

import com.dmi.devbook.model.Dev;

import java.util.List;

import retrofit.http.GET;

public interface DevService {

    @GET("/gekko/android.json")
    List<Dev> getAndroidDevs();

    @GET("/gekko/ios.json")
    List<Dev> getIosDevs();

    @GET("/gekko/backend.json")
    List<Dev> getBackendDevs();

}
