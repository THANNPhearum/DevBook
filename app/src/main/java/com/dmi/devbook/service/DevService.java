package com.dmi.devbook.service;

import com.dmi.devbook.model.Dev;

import java.util.List;

import retrofit.http.GET;
import retrofit.http.Path;
import retrofit.http.Query;

public interface DevService {

    /**
     * To retrieve the developers by department and the current of loading page.
     * @param department Department of developer like android, ios, backend
     * @param page loading data by page
     * @return list of developers
     */
    @GET("/goldengekko/api.php")
    List<Dev> getDevs(@Query("dev") String department,@Query("page") int page);


}
