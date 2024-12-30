package com.example.nyannyanquiz;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {

    private static final String BASE_URL = "https://opentdb.com/api.php?amount=10&category=31"; // Replace with your API base URL
    private static final String BASE_TRANS_URL = "https://api-eur.cognitive.microsofttranslator.com";
    private static Retrofit retrofitQuiz = null;
    private static Retrofit retrofitTrans = null;

    public static Retrofit getQuestions() {
        if (retrofitQuiz == null) {
            retrofitQuiz = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create()) // Gson for JSON parsing
                    .build();
        }
        return retrofitQuiz;
    }

    public static Retrofit getInstance() {
        if (retrofitTrans == null) {
            retrofitTrans = new Retrofit.Builder()
                    .baseUrl(BASE_TRANS_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofitTrans;
    }
}
