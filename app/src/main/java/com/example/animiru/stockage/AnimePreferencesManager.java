package com.example.animiru.stockage;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class AnimePreferencesManager {
    private static final String PREF_NAME = "AnimePreferences";
    public static final String KEY_ANIME_LIBRARY = "animeLibrary";

    private SharedPreferences preferences;

    private static final String KEY_INSTALLATION_ID = "installation_id";

    public String getInstallationId() {
        String installationId = preferences.getString(KEY_INSTALLATION_ID, null);
        if (installationId == null) {
            installationId = generateInstallationId();
            preferences.edit().putString(KEY_INSTALLATION_ID, installationId).apply();
        }
        return installationId;
    }

    private String generateInstallationId() {
        // Utilisez une méthode appropriée pour générer un identifiant unique, par exemple :
        return UUID.randomUUID().toString();
    }

    public AnimePreferencesManager(Context context) {
        preferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
    }

    public List<AnimeLibraryItem> getAnimeLibrary() {
        String json = preferences.getString(KEY_ANIME_LIBRARY, "[]");
        Type type = new TypeToken<List<AnimeLibraryItem>>() {}.getType();

        List<AnimeLibraryItem> animeLibrary = new Gson().fromJson(json, type);

        // Créer une nouvelle liste filtrée
        List<AnimeLibraryItem> filteredList = new ArrayList<>();

        // Vérifier si la liste n'est pas vide avant de la traiter
        if (animeLibrary != null && !animeLibrary.isEmpty()) {
            for (AnimeLibraryItem item : animeLibrary) {
                // Filtrer les éléments avec animeId différent de 0
                if (item != null && item.getAnimeId() != 0) {
                    int animeId = item.getAnimeId();
                    int lastWatchedEpisode = item.getLastWatchedEpisode();

                    // Faites quelque chose avec les valeurs entières (par exemple, affichez-les dans la console)
                    Log.d("AnimeLibrary", "if ? " + animeId + ", LastWatchedEpisode: " + lastWatchedEpisode);

                    // Vérifier si animeId est déjà présent dans la liste filtrée
                    boolean isAnimeIdAlreadyAdded = false;
                    for (AnimeLibraryItem filteredItem : filteredList) {
                        if (filteredItem.getAnimeId() == animeId) {
                            isAnimeIdAlreadyAdded = true;
                            break;
                        }
                    }

                    // Ajouter l'élément à la liste filtrée s'il n'est pas déjà présent
                    if (!isAnimeIdAlreadyAdded) {
                        filteredList.add(item);
                    }
                }
            }
        } else {

        }

        // Mettez à jour les données dans SharedPreferences avec la liste filtrée
        String filteredJson = new Gson().toJson(filteredList);
        preferences.edit().putString(KEY_ANIME_LIBRARY, filteredJson).apply();

        return filteredList;
    }



    public void saveAnimeLibrary(List<AnimeLibraryItem> animeLibrary) {
        SharedPreferences.Editor editor = preferences.edit();
        String json = new Gson().toJson(animeLibrary);
        editor.putString(KEY_ANIME_LIBRARY, json);
        editor.apply();
    }

    public void resetAnimeLibraryAndJson() {
        // Réinitialiser complètement les préférences partagées
        preferences.edit().clear().apply();

        // Réinitialiser la liste filtrée à une liste vide
        List<AnimeLibraryItem> emptyAnimeLibrary = new ArrayList<>();

        // Mettre à jour les données dans SharedPreferences avec la liste vide
        String emptyJson = new Gson().toJson(emptyAnimeLibrary);
        preferences.edit().putString(KEY_ANIME_LIBRARY, emptyJson).apply();
    }
}
