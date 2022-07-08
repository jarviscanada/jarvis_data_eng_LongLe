package ca.jrvs.apps.twitter.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Coordinates {
  //@JsonProperty(name), tells Jackson ObjectMapper to map the JSON property name to the annotated Java field's name.
  @JsonProperty("coordinates")
  private float[] coordinates;
  @JsonProperty("type")
  private String type;

  //Default Constructor
  public Coordinates() {
  }

  public float[] getCoordinates() {
    return coordinates;
  }

  public void setCoordinates(float[] coordinates) {
    this.coordinates = coordinates;
  }

  public String getType() {
    return type;
  }

  public void setType(String type) {
    this.type = type;
  }
}