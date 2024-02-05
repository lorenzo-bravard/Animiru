package com.example.animiru.stockage;

public class AnimeLibraryItem {
    private int animeId;
    private int lastWatchedEpisode;
    private String syn;
    private String ep;
    private String studio;
    private String genres;
    private String images;
    private String title;


    public AnimeLibraryItem(int animeId, int lastWatchedEpisode, String syn, String ep, String studio, String genres, String images, String title) {
        this.animeId = animeId;
        this.lastWatchedEpisode = lastWatchedEpisode;
        this.syn = syn;
        this.ep = ep;
        this.studio = studio;
        this.genres = genres;
        this.images = images;
        this.title = title;
    }

    public int getAnimeId() {
        return animeId;
    }

    public int getLastWatchedEpisode() {
        return lastWatchedEpisode;
    }
    public void setLastWatchedEpisode(int lastWatchedEpisode) {
        this.lastWatchedEpisode = lastWatchedEpisode;
    }
    public String syn() {
        return syn;
    }

    public String ep() {
        return ep;
    }

    public String studio() {
        return studio;
    }

    public String genres() {
        return genres;
    }

    public String images() {
        return images;
    }
    public String title() {
        return title;
    }


    @Override
    public String toString() {
        return "AnimeLibraryItem{" +
                "animeId=" + animeId +
                ", lastWatchedEpisode=" + lastWatchedEpisode +
                ", syn='" + syn + '\'' +
                ", ep=" + ep +
                ", studio='" + studio + '\'' +
                ", genres='" + genres + '\'' +
                ", images='" + images + '\'' +
                ", title='" + title + '\'' +
                '}';
    }
}
