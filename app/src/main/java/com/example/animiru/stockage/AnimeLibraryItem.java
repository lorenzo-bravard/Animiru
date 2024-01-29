package com.example.animiru.stockage;

public class AnimeLibraryItem {
    private int animeId;
    private int lastWatchedEpisode;

    public AnimeLibraryItem(int animeId, int lastWatchedEpisode) {
        this.animeId = animeId;
        this.lastWatchedEpisode = lastWatchedEpisode;
    }

    public int getAnimeId() {
        return animeId;
    }

    public int getLastWatchedEpisode() {
        return lastWatchedEpisode;
    }

    @Override
    public String toString() {
        return "AnimeLibraryItem{" +
                "animeId=" + animeId +
                ", lastWatchedEpisode=" + lastWatchedEpisode +
                '}';
    }
}
