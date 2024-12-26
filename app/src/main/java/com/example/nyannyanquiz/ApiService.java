package com.example.nyannyanquiz;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Url;

public interface ApiService {
    @GET
    Call<String> fetchDynamicData(@Url String endpoint); // Use @Url for dynamic URLs
}
