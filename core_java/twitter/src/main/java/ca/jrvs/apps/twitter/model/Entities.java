package ca.jrvs.apps.twitter.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Entities {

  @JsonProperty("hashtags")
  private Hashtag[] hashtags;
  @JsonProperty("user_mentions")
  private UserMention[] mentions;

  public Entities() {
  }

  public Hashtag[] getHashtags() {
    return hashtags;
  }

  public void setHashtags(Hashtag[] hashtags) {
    this.hashtags = hashtags;
  }

  public UserMention[] getMentions() {
    return mentions;
  }

  public void setMentions(UserMention[] mentions) {
    this.mentions = mentions;
  }
}