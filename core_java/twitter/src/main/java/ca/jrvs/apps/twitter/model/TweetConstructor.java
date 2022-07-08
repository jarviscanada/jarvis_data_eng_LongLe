package ca.jrvs.apps.twitter.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TweetConstructor {

  public static Tweet buildTweet(String text, Double longitude, Double latitude) {
    Tweet tweet = new Tweet();
    tweet.setText(text);
    Coordinates coordinates = new Coordinates();
    coordinates.setType("point");
    List<Double> coor = new ArrayList<>();
    coor.add(longitude);
    coor.add(latitude);
    coordinates.setCoordinates(coor);
    tweet.setLocation(coordinates);
    return tweet;
  }

  // The following method is in develop for the function of showTweet (not mandatory)
  public static Tweet tweetModifier(Tweet tweetOrg, String[] fields) {
    String[] availableFields = {"created_at", "id", "id_str", "text", "source",
        "coordinates", "entities", "retweet_count", "favoriated_count", "favoriated", "retweeted"};
    Tweet tweet = new Tweet();

    return tweet;
  }
}