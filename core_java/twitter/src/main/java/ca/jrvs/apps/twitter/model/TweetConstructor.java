package ca.jrvs.apps.twitter.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TweetConstructor {

  public static Tweet buildTweet(String text, Double longitude, Double latitude){
    Tweet tweet = new Tweet();
    tweet.setText(text);
    Coordinates coordinates = new Coordinates();
    coordinates.setType("point");
    List<Double> coor = new ArrayList<>();
    coor.add(longitude);
    coor.add(latitude);
    coordinates.setCoordinatesTweet(coor);
    tweet.setCoordinates(coordinates);
    return tweet;
  }
}