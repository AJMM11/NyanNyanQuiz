package com.example.nyannyanquiz;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Body;

public interface TranslatorService {

    @Headers({
            "Ocp-Apim-Subscription-Key: xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx",
            "Ocp-Apim-Subscription-Region: westeurope",// Replace with your subscription key
            "Content-Type: application/json"
    })
    @POST("translate?api-version=3.0&to=es") // Example: Translates to Spanish (change 'to' parameter as needed)
    Call<List<TranslateResponse>> translateText(@Body List<TranslateRequest> requests);
}