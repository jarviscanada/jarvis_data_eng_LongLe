package ca.jrvs.apps.twitter.controller;

import ca.jrvs.apps.twitter.model.Tweet;
import ca.jrvs.apps.twitter.model.TweetConstructor;
import ca.jrvs.apps.twitter.service.Service;
import java.util.Arrays;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;

@org.springframework.stereotype.Controller
public class TwitterController implements Controller {

  private static final String COORD_SEP = ":";
  private static final String COMMA = ",";

  private Service service;

  @Autowired
  public TwitterController(Service service) {
    this.service = service;
  }

  /**
   * Parse user argument and post a tweet by calling service classes
   *
   * @param args
   * @return a posted tweet
   * @throws IllegalArgumentException if args are invalid
   */
  @Override
  public Tweet postTweet(String[] args) {
    if (args.length != 3) {
      throw new IllegalArgumentException(
          "USAGE: TwitterCLIApp post \"tweet_text\" \"latitude:longitude\"");
    }

    String tweet_txt = args[1];
    String coord = args[2];
    String[] coordArray = coord.split(COORD_SEP);
    if (coordArray.length != 2 || StringUtils.isEmpty(tweet_txt)) {
      throw new IllegalArgumentException(
          "Invalid location format\nUSAGE: TwitterCLIApp post \"tweet_text\" \"latitude:longitude\"");
    }

    Double lon = null;
    Double lat = null;
    try {
      lat = Double.parseDouble(coordArray[0]);
      lon = Double.parseDouble(coordArray[1]);
    } catch (Exception e) {
      throw new IllegalArgumentException(
          "Invalid location format\nUSAGE: TwitterCLIApp post \"tweet_text\" \"latitude:longitude\"");
    }

    Tweet postTweet = TweetConstructor.buildTweet(tweet_txt, lon, lat);
    return service.postTweet(postTweet);
  }

  /**
   * Parse user argument and search a tweet by calling service classes
   *
   * @param args
   * @return a tweet
   * @throws IllegalArgumentException if args are invalid
   */
  @Override
  public Tweet showTweet(String[] args) {
    if (args.length != 3) {
      throw new IllegalArgumentException("Error: Number of arguments is not correct. "
          + "USAGE: TwitterCLIApp show \"tweet id\", \"field,field,...\"");
    }
    String id = args[1];
    idChecker(id);
    String[] fields = args[2].split(COMMA);
    if (fields.length > 11) {
      throw new IllegalArgumentException("ERROR: Too many fields entered.  "
          + "Please enter up to 11 fields.\n"
          + "Format: field,field,...");
    }
    return service.showTweet(id, fields);
  }

  private void idChecker(String id) {
    if (!id.matches("[0-9]+")) {
      throw new IllegalArgumentException("ERROR: id contains character or space in it. "
          + "Please make sure the id you entered is correct.");
    }
  }

  /**
   * Parse user argument and delete tweets by calling service classes
   *
   * @param args
   * @return a list of deleted tweets
   * @throws IllegalArgumentException if args are invalid
   */
  @Override
  public List<Tweet> deleteTweet(String[] args) {
    if (args.length != 2) {
      throw new IllegalArgumentException("Error: 2 arguments are required to delete a tweet : \"delete\" and  \"id\"");
    }
    String[] idArray = args[1].split(COMMA);
    for (String id : idArray) {
      idChecker(id);
    }
    return service.deleteTweets(idArray);
  }
}
