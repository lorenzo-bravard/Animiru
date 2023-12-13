package com.example.animiru.ui.biblio;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface JikanApi {

    @GET("anime/{id}")
    Call<Anime> getAnimeDetails(@Path("id") int animeId);

    @GET("anime/{id}/episodes/{episodeNumber}")
    Call<Episode> getEpisodeDetails(
            @Path("id") int animeId,
            @Path("episodeNumber") int episodeNumber
    );


}
