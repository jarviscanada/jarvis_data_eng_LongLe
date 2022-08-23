package ca.jrvs.apps.twitter.controller;

import static org.junit.Assert.*;


import ca.jrvs.apps.example.JsonParser;
import ca.jrvs.apps.twitter.dao.TwitterDAO;
import ca.jrvs.apps.twitter.dao.helper.HttpHelper;
import ca.jrvs.apps.twitter.dao.helper.TwitterHTTPHelper;
import ca.jrvs.apps.twitter.model.Tweet;
import ca.jrvs.apps.twitter.model.TweetConstructor;
import ca.jrvs.apps.twitter.service.TwitterService;
import java.util.List;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class TwitterControllerIntTest {
  private TwitterController controller;

  private Tweet tweet;
  private Tweet postTweet;

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
    TwitterService twitterService = new TwitterService(dao);
    this.controller = new TwitterController(twitterService);
  }

  @Test
  public void postTweet() throws Exception {
    String hashtag = "#abc";
    String text = "@someone sometext " + hashtag + " " + System.currentTimeMillis();
    Double lat = 20d;
    Double lon = -38d;
    Tweet postTweet = TweetConstructor.buildTweet(text, lon, lat);
    System.out.println(JsonParser.toJson(postTweet, true, false));

    //Testing twitterController postTweet
    String[] input = {"run", postTweet.getText(), "-28d:54d"};
    tweet = controller.postTweet(input);
    assertEquals(text, tweet.getText());
  }

  @After
  public void deleteTweet(){
    String id = tweet.getId_str();
    String[] input = {"delete", id};
    try {
      List<Tweet> tweetsDelete = controller.deleteTweet(input);
      System.out.println("\nTweet deleted successfully");
    }catch (RuntimeException e){
      System.out.println("The following error message is expected: " + e);
      assertTrue(true);
    }
  }
}
