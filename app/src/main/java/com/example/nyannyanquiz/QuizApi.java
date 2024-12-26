package com.example.nyannyanquiz;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface QuizApi {
    @GET("api.php")
    Call<ApiResponse> getQuestions(@Query("amount") int amount, @Query("category") int category, @Query("difficulty") String difficulty, @Query("type") String type);
}