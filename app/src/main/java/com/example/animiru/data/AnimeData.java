package com.example.animiru.data;

import java.io.Serializable;
import java.lang.Boolean;
import java.lang.Double;
import java.lang.Integer;
import java.lang.Object;
import java.lang.String;
import java.util.List;

public class AnimeData implements Serializable {
  private Data data;

  public Data getData() {
    return this.data;
  }

  public void setData(Data data) {
    this.data = data;
  }

  public static class Data implements Serializable {
    private String title_japanese;

    private Integer favorites;

    private Broadcast broadcast;

    private Integer year;

    private String rating;

    private Integer scored_by;

    private List<String> title_synonyms;

    private String source;

    private String title;

    private String type;

    private Trailer trailer;

    private String duration;

    private Double score;

    private Boolean approved;

    private List<Genres> genres;

    private Integer popularity;

    private Integer members;

    private String title_english;

    private Integer rank;

    private String season;

    private Boolean airing;

    private Object episodes;

    private Aired aired;

    private Images images;

    private List<Genres> studios;

    private Integer mal_id;

    private List<Titles> titles;

    private String synopsis;

    private List<Genres> licensors;

    private String url;

    private List<Genres> producers;

    private String background;

    private String status;

    private List<Genres> demographics;

    public String getTitle_japanese() {
      return this.title_japanese;
    }

    public void setTitle_japanese(String title_japanese) {
      this.title_japanese = title_japanese;
    }

    public Integer getFavorites() {
      return this.favorites;
    }

    public void setFavorites(Integer favorites) {
      this.favorites = favorites;
    }

    public Broadcast getBroadcast() {
      return this.broadcast;
    }

    public void setBroadcast(Broadcast broadcast) {
      this.broadcast = broadcast;
    }

    public Integer getYear() {
      return this.year;
    }

    public void setYear(Integer year) {
      this.year = year;
    }

    public String getRating() {
      return this.rating;
    }

    public void setRating(String rating) {
      this.rating = rating;
    }

    public Integer getScored_by() {
      return this.scored_by;
    }

    public void setScored_by(Integer scored_by) {
      this.scored_by = scored_by;
    }

    public List<String> getTitle_synonyms() {
      return this.title_synonyms;
    }

    public void setTitle_synonyms(List<String> title_synonyms) {
      this.title_synonyms = title_synonyms;
    }

    public String getSource() {
      return this.source;
    }

    public void setSource(String source) {
      this.source = source;
    }

    public String getTitle() {
      return this.title;
    }

    public void setTitle(String title) {
      this.title = title;
    }

    public String getType() {
      return this.type;
    }

    public void setType(String type) {
      this.type = type;
    }

    public Trailer getTrailer() {
      return this.trailer;
    }

    public void setTrailer(Trailer trailer) {
      this.trailer = trailer;
    }

    public String getDuration() {
      return this.duration;
    }

    public void setDuration(String duration) {
      this.duration = duration;
    }

    public Double getScore() {
      return this.score;
    }

    public void setScore(Double score) {
      this.score = score;
    }

    public Boolean getApproved() {
      return this.approved;
    }

    public void setApproved(Boolean approved) {
      this.approved = approved;
    }

    public List<Genres> getGenres() {
      return this.genres;
    }

    public void setGenres(List<Genres> genres) {
      this.genres = genres;
    }

    public Integer getPopularity() {
      return this.popularity;
    }

    public void setPopularity(Integer popularity) {
      this.popularity = popularity;
    }

    public Integer getMembers() {
      return this.members;
    }

    public void setMembers(Integer members) {
      this.members = members;
    }

    public String getTitle_english() {
      return this.title_english;
    }

    public void setTitle_english(String title_english) {
      this.title_english = title_english;
    }

    public Integer getRank() {
      return this.rank;
    }

    public void setRank(Integer rank) {
      this.rank = rank;
    }

    public String getSeason() {
      return this.season;
    }

    public void setSeason(String season) {
      this.season = season;
    }

    public Boolean getAiring() {
      return this.airing;
    }

    public void setAiring(Boolean airing) {
      this.airing = airing;
    }

    public Object getEpisodes() {
      return this.episodes;
    }

    public void setEpisodes(Object episodes) {
      this.episodes = episodes;
    }

    public Aired getAired() {
      return this.aired;
    }

    public void setAired(Aired aired) {
      this.aired = aired;
    }

    public Images getImages() {
      return this.images;
    }

    public void setImages(Images images) {
      this.images = images;
    }

    public List<Genres> getStudios() {
      return this.studios;
    }

    public void setStudios(List<Genres> studios) {
      this.studios = studios;
    }

    public Integer getMal_id() {
      return this.mal_id;
    }

    public void setMal_id(Integer mal_id) {
      this.mal_id = mal_id;
    }

    public List<Titles> getTitles() {
      return this.titles;
    }

    public void setTitles(List<Titles> titles) {
      this.titles = titles;
    }

    public String getSynopsis() {
      return this.synopsis;
    }

    public void setSynopsis(String synopsis) {
      this.synopsis = synopsis;
    }

    public List<Genres> getLicensors() {
      return this.licensors;
    }

    public void setLicensors(List<Genres> licensors) {
      this.licensors = licensors;
    }

    public String getUrl() {
      return this.url;
    }

    public void setUrl(String url) {
      this.url = url;
    }

    public List<Genres> getProducers() {
      return this.producers;
    }

    public void setProducers(List<Genres> producers) {
      this.producers = producers;
    }

    public String getBackground() {
      return this.background;
    }

    public void setBackground(String background) {
      this.background = background;
    }

    public String getStatus() {
      return this.status;
    }

    public void setStatus(String status) {
      this.status = status;
    }

    public List<Genres> getDemographics() {
      return this.demographics;
    }

    public void setDemographics(List<Genres> demographics) {
      this.demographics = demographics;
    }

    public static class Broadcast implements Serializable {
      private String string;

      private String timezone;

      private String time;

      private String day;

      public String getString() {
        return this.string;
      }

      public void setString(String string) {
        this.string = string;
      }

      public String getTimezone() {
        return this.timezone;
      }

      public void setTimezone(String timezone) {
        this.timezone = timezone;
      }

      public String getTime() {
        return this.time;
      }

      public void setTime(String time) {
        this.time = time;
      }

      public String getDay() {
        return this.day;
      }

      public void setDay(String day) {
        this.day = day;
      }
    }

    public static class Trailer implements Serializable {
      private Images images;

      private String embed_url;

      private String youtube_id;

      private String url;

      public Images getImages() {
        return this.images;
      }

      public void setImages(Images images) {
        this.images = images;
      }

      public String getEmbed_url() {
        return this.embed_url;
      }

      public void setEmbed_url(String embed_url) {
        this.embed_url = embed_url;
      }

      public String getYoutube_id() {
        return this.youtube_id;
      }

      public void setYoutube_id(String youtube_id) {
        this.youtube_id = youtube_id;
      }

      public String getUrl() {
        return this.url;
      }

      public void setUrl(String url) {
        this.url = url;
      }

      public static class Images implements Serializable {
        private String large_image_url;

        private String small_image_url;

        private String image_url;

        private String medium_image_url;

        private String maximum_image_url;

        public String getLarge_image_url() {
          return this.large_image_url;
        }

        public void setLarge_image_url(String large_image_url) {
          this.large_image_url = large_image_url;
        }

        public String getSmall_image_url() {
          return this.small_image_url;
        }

        public void setSmall_image_url(String small_image_url) {
          this.small_image_url = small_image_url;
        }

        public String getImage_url() {
          return this.image_url;
        }

        public void setImage_url(String image_url) {
          this.image_url = image_url;
        }

        public String getMedium_image_url() {
          return this.medium_image_url;
        }

        public void setMedium_image_url(String medium_image_url) {
          this.medium_image_url = medium_image_url;
        }

        public String getMaximum_image_url() {
          return this.maximum_image_url;
        }

        public void setMaximum_image_url(String maximum_image_url) {
          this.maximum_image_url = maximum_image_url;
        }
      }
    }

    public static class Genres implements Serializable {
      private String name;

      private Integer mal_id;

      private String type;

      private String url;

      public String getName() {
        return this.name;
      }

      public void setName(String name) {
        this.name = name;
      }

      public Integer getMal_id() {
        return this.mal_id;
      }

      public void setMal_id(Integer mal_id) {
        this.mal_id = mal_id;
      }

      public String getType() {
        return this.type;
      }

      public void setType(String type) {
        this.type = type;
      }

      public String getUrl() {
        return this.url;
      }

      public void setUrl(String url) {
        this.url = url;
      }
    }

    public static class Aired implements Serializable {
      private String string;

      private Prop prop;

      private String from;

      private Object to;

      public String getString() {
        return this.string;
      }

      public void setString(String string) {
        this.string = string;
      }

      public Prop getProp() {
        return this.prop;
      }

      public void setProp(Prop prop) {
        this.prop = prop;
      }

      public String getFrom() {
        return this.from;
      }

      public void setFrom(String from) {
        this.from = from;
      }

      public Object getTo() {
        return this.to;
      }

      public void setTo(Object to) {
        this.to = to;
      }

      public static class Prop implements Serializable {
        private From from;

        private From to;

        public From getFrom() {
          return this.from;
        }

        public void setFrom(From from) {
          this.from = from;
        }

        public From getTo() {
          return this.to;
        }

        public void setTo(From to) {
          this.to = to;
        }

        public static class From implements Serializable {
          private Integer month;

          private Integer year;

          private Integer day;

          public Integer getMonth() {
            return this.month;
          }

          public void setMonth(Integer month) {
            this.month = month;
          }

          public Integer getYear() {
            return this.year;
          }

          public void setYear(Integer year) {
            this.year = year;
          }

          public Integer getDay() {
            return this.day;
          }

          public void setDay(Integer day) {
            this.day = day;
          }
        }
      }
    }

    public static class Images implements Serializable {
      private Jpg jpg;

      private Jpg webp;

      public Jpg getJpg() {
        return this.jpg;
      }

      public void setJpg(Jpg jpg) {
        this.jpg = jpg;
      }

      public Jpg getWebp() {
        return this.webp;
      }

      public void setWebp(Jpg webp) {
        this.webp = webp;
      }

      public static class Jpg implements Serializable {
        private String large_image_url;

        private String small_image_url;

        private String image_url;

        public String getLarge_image_url() {
          return this.large_image_url;
        }

        public void setLarge_image_url(String large_image_url) {
          this.large_image_url = large_image_url;
        }

        public String getSmall_image_url() {
          return this.small_image_url;
        }

        public void setSmall_image_url(String small_image_url) {
          this.small_image_url = small_image_url;
        }

        public String getImage_url() {
          return this.image_url;
        }

        public void setImage_url(String image_url) {
          this.image_url = image_url;
        }
      }
    }

    public static class Titles implements Serializable {
      private String type;

      private String title;

      public String getType() {
        return this.type;
      }

      public void setType(String type) {
        this.type = type;
      }

      public String getTitle() {
        return this.title;
      }

      public void setTitle(String title) {
        this.title = title;
      }
    }
  }
}
