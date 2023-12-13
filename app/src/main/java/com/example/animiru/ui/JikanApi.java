package com.example.animiru.ui;
import com.example.animiru.data.AnimeData;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface JikanApi {

    @GET("anime/{id}")
    Call<AnimeData> getAnimeDetails(@Path("id") int animeId);


    @GET("anime?")
    Call<AnimeData> doGetUserList(@Query("page") String page);


}
