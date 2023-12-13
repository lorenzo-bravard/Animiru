package com.example.animiru.data;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface JikanAPI {

    @GET("anime/{id}")
    Call<Anime> getAnimeDetails(@Path("id") int animeId);

    @GET("anime/{id}/episodes/{episodeNumber}")
    Call<Episode> getEpisodeDetails(
            @Path("id") int animeId,
            @Path("episodeNumber") int episodeNumber
    );


}
