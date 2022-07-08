package ca.jrvs.apps.twitter.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Hashtag {
  //@JsonProperty(name), tells Jackson ObjectMapper to map the JSON property name to the annotated Java field's name.
  @JsonProperty("indices")
  private int[] indices;
  @JsonProperty("text")
  private String text;

  //Default Constructor
  public Hashtag() {
  }

  public int[] getIndices() {
    return indices;
  }

  public void setIndices(int[] indices) {
    this.indices = indices;
  }

  public String getText() {
    return text;
  }

  public void setText(String text) {
    this.text = text;
  }
}