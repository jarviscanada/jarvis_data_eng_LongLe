package ca.jrvs.apps.twitter.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class Coordinates {

  @JsonProperty("coordinates")
  private List<Double> coordinatesTweet;

  @JsonProperty("type")
  private String type;

  public List<Double> getCoordinatesTweet() {
    return coordinatesTweet;
  }

  public void setCoordinatesTweet(List<Double> coordinatesTweet) {
    this.coordinatesTweet = coordinatesTweet;
  }

  public String getType() {
    return type;
  }

  public void setType(String type) {
    this.type = type;
  }
}