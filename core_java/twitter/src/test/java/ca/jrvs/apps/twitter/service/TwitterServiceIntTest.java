package ca.jrvs.apps.twitter.service;

import static org.junit.Assert.*;

import ca.jrvs.apps.example.JsonParser;
import ca.jrvs.apps.twitter.dao.TwitterDAO;
import ca.jrvs.apps.twitter.dao.helper.HttpHelper;
import ca.jrvs.apps.twitter.dao.helper.TwitterHTTPHelper;
import ca.jrvs.apps.twitter.model.Tweet;
import ca.jrvs.apps.twitter.model.TweetConstructor;
import java.util.List;
import org.junit.Before;
import org.junit.Test;

public class TwitterServiceIntTest {

  private TwitterService twitterService;
  private Tweet tweet;

  @Before
  public void setup() {
    String consumerKey = System.getenv("consumerKey");
    String consumerSecret = System.getenv("consumerSecret");
    String accessToken = System.getenv("accessToken");
    String tokenSecret = System.getenv("tokenSecret");
    System.out.println(consumerKey + "|" + consumerSecret + "|" + accessToken + "|" + tokenSecret);

    //set up dependency
    HttpHelper httpHelper = new TwitterHTTPHelper(consumerKey, consumerSecret, accessToken,
        tokenSecret);

    //pass dependency
    TwitterDAO dao = new TwitterDAO(httpHelper);
    this.twitterService = new TwitterService(dao);
  }

  @Test
  public void showTweet() throws Exception {
    String hashtag = "#abc";
    String text = "@someone sometext " + hashtag + " " + System.currentTimeMillis();
    Double lat = 20d;
    Double lon = -38d;
    Tweet postTweet = TweetConstructor.buildTweet(text, lon, lat);
    System.out.println(JsonParser.toJson(postTweet, true, false));

    //Testing twitterService postTweet
    tweet = twitterService.postTweet(postTweet);

    //Testing twitterService showTweet
    String id = String.valueOf(tweet.getId());
    String[] fields = {id, tweet.getText()};
    tweet = twitterService.showTweet(id, fields);
    assertEquals(text, tweet.getText());

    String[] delId = {id};
    //Testing TwitterService deleteTweet
    List<Tweet> list = twitterService.deleteTweets(delId);
    list.get(0);
  }
}