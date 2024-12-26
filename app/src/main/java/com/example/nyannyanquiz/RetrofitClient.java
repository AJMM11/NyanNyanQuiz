package com.example.nyannyanquiz;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {

    private static final String BASE_URL = "https://opentdb.com/api.php?amount=10&category=31"; // Replace with your API base URL
    private static Retrofit retrofit = null;

    public static Retrofit getQuestions() {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create()) // Gson for JSON parsing
                    .build();
        }
        return retrofit;
    }
}
