package com.dmi.devbook.service;

import retrofit.http.GET;
import retrofit.http.Path;

public interface LoremService {

    @GET("/api/plaintext/{paragraphs}")
    String getLorem(@Path("paragraphs") int paragraphs);

}
