package ca.jrvs.apps.twitter.dao;


import ca.jrvs.apps.example.JsonParser;
import ca.jrvs.apps.twitter.dao.TwitterDAO;
import ca.jrvs.apps.twitter.dao.helper.HttpHelper;
import ca.jrvs.apps.twitter.dao.helper.TwitterHTTPHelper;
import ca.jrvs.apps.twitter.model.TweetConstructor;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import ca.jrvs.apps.twitter.model.Tweet;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class TwitterDaoIntTest {

  private TwitterDAO dao;


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
    this.dao = new TwitterDAO(httpHelper);
  }

  @Test
  public void create() throws Exception {
    String hashtag = "#abc";
    String text = "@someone sometext " + hashtag + " " + System.currentTimeMillis();
    Double lat = 34d;
    Double lon = -118d;
    Tweet postTweet = TweetConstructor.buildTweet(text, lon, lat);
    System.out.println(JsonParser.toJson(postTweet, true, false));

    Tweet tweet = dao.create(postTweet);

    assertEquals(text, tweet.getText());

//    assertNotNull(tweet.getCoordinates());
//    assertEquals(2,tweet.getCoordinates().getCoordinatesTweet().size());
//    assertEquals(lon, tweet.getCoordinates().getCoordinatesTweet().get(0));
//    assertEquals(lat, tweet.getCoordinates().getCoordinatesTweet().get(1));

    assertTrue(hashtag.contains(tweet.getEntities().getHashtags().get(0).getText()));
  }


}
