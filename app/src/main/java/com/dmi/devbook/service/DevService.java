package com.dmi.devbook.service;

import com.dmi.devbook.model.Dev;

import java.util.List;

import retrofit.http.GET;

public interface DevService {

    @GET("/goldengekko/android.json")
    List<Dev> getAndroidDevs();

    @GET("/goldengekko/ios.json")
    List<Dev> getIosDevs();

    @GET("/goldengekko/backend.json")
    List<Dev> getBackendDevs();

}
