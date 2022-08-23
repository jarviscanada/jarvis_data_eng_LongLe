package ca.jrvs.apps.twitter.service;

import ca.jrvs.apps.twitter.dao.CrdDao;
import ca.jrvs.apps.twitter.model.Tweet;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;

@org.springframework.stereotype.Service
public class TwitterService implements Service {

  private CrdDao dao;

  @Autowired
  public TwitterService(CrdDao dao) {
    this.dao = dao;
  }

  /**
   * Validate and post a user input Tweet
   *
   * @param tweet tweet to be created
   * @return created tweet
   * @throws IllegalArgumentException if text exceed max number of allowed characters or lat/long
   *                                  out of range
   */
  @Override
  public Tweet postTweet(Tweet tweet) {
    //Business logic
    //Text length, lat/lon range, id format
    validatePostTweet(tweet);
    return (Tweet) dao.create(tweet);
  }

  private void validatePostTweet(Tweet tweet) {
    //Check tweet length
    if (tweet.getText().length() > 140) {
      throw new RuntimeException("Tweet length exceeded 140 characters");
    }

    //Check valid longitude
    if (tweet.getCoordinates().getCoordinatesTweet().get(0) > 180 ||
        tweet.getCoordinates().getCoordinatesTweet().get(0) < -180) {
      throw new RuntimeException("The longitude you entered is not valid");
    }

    //Check valid latitude
    if (tweet.getCoordinates().getCoordinatesTweet().get(1) > 180 ||
        tweet.getCoordinates().getCoordinatesTweet().get(1) < -180) {
      throw new RuntimeException("The latitude you entered is not valid");
    }
  }

  /**
   * Search a tweet by ID
   *
   * @param id     tweet id
   * @param fields set fields not in the list to null
   * @return Tweet object which is returned by the Twitter API
   * @throws IllegalArgumentException if id or fields param is invalid
   */
  @Override
  public Tweet showTweet(String id, String[] fields) {
    validateShowTweet(id, fields);
    Tweet tweet = (Tweet) dao.findById(id);
    return tweet;
  }

  private void validateShowTweet(String id, String[] fields) {
    if (id.length() != 19) {
      throw new RuntimeException("The tweet id is invalid");
    }
    for (String field : fields) {
      if (field.isEmpty()) {
        throw new RuntimeException("Some fields are empty");
      }
    }
  }

  /**
   * Delete Tweet(s) by id(s).
   *
   * @param ids tweet IDs which will be deleted
   * @return A list of Tweets
   * @throws IllegalArgumentException if one of the IDs is invalid.
   */
  @Override
  public List<Tweet> deleteTweets(String[] ids) {
    for (String id : ids) {
      if (id.length() != 19) {
        throw new RuntimeException("An id is invalid");
      }
    }
    List<Tweet> tweetList = new ArrayList<>();
    for (String id : ids) {
      tweetList.add((Tweet) dao.deleteById(id));
    }
    return tweetList;
  }
}
