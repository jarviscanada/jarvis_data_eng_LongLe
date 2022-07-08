package ca.jrvs.apps.twitter.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Tweet {

  //@JsonProperty(name), tells Jackson ObjectMapper to map the JSON property name to the annotated Java field's name.
  @JsonProperty("created_at")
  private String createdAt;
  @JsonProperty("id")
  private long id;
  @JsonProperty("id_str")
  private String idStr;
  @JsonProperty("text")
  private String text;
  @JsonProperty("entities")
  private Entities entities;
  @JsonProperty("coordinates")
  private Coordinates location;
  @JsonProperty("retweet_count")
  private int retweets;
  @JsonProperty("favorite_count")
  private int favorites;
  @JsonProperty("favorited")
  private boolean favorited;
  @JsonProperty("retweeted")
  private boolean retweeted;

  //Default constructor
  public Tweet() {

  }

  public String getCreatedAt() {
    return createdAt;
  }

  public void setCreatedAt(String createdAt) {
    this.createdAt = createdAt;
  }

  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }

  public String getIdStr() {
    return idStr;
  }

  public void setIdStr(String idStr) {
    this.idStr = idStr;
  }

  public String getText() {
    return text;
  }

  public void setText(String text) {
    this.text = text;
  }

  public Entities getEntities() {
    return entities;
  }

  public void setEntities(Entities entities) {
    this.entities = entities;
  }

  public Coordinates getLocation() {
    return location;
  }

  public void setLocation(Coordinates location) {
    this.location = location;
  }

  public int getRetweets() {
    return retweets;
  }

  public void setRetweets(int retweets) {
    this.retweets = retweets;
  }

  public int getFavorites() {
    return favorites;
  }

  public void setFavorites(int favorites) {
    this.favorites = favorites;
  }

  public boolean isFavorited() {
    return favorited;
  }

  public void setFavorited(boolean favorited) {
    this.favorited = favorited;
  }

  public boolean isRetweeted() {
    return retweeted;
  }

  public void setRetweeted(boolean retweeted) {
    this.retweeted = retweeted;
  }

  @Override
  public String toString() {
    return "Tweet{"
        + "createdAt='" + createdAt + '\''
        + ", id=" + id
        + ", idStr='" + idStr + '\''
        + ", text='" + text + '\''
        + ", entities=" + entities
        + ", location=" + location
        + ", retweets=" + retweets
        + ", favorites=" + favorites
        + ", favorited=" + favorited
        + ", retweeted=" + retweeted
        + '}';
  }
}