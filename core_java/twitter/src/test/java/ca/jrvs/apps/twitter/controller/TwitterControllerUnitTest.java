package ca.jrvs.apps.twitter.controller;

import ca.jrvs.apps.twitter.model.Tweet;
import ca.jrvs.apps.twitter.service.TwitterService;
import java.util.ArrayList;
import java.util.List;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import static org.mockito.Mockito.*;

import static org.junit.Assert.*;

@RunWith(MockitoJUnitRunner.class)
public class TwitterControllerUnitTest {

  @Mock
  TwitterService twitterService;

  @InjectMocks
  TwitterController twitterController;

  @Test
  public void postTweet(){
//    when(twitterService.postTweet(isNotNull())).thenReturn(null);
//    TwitterController spyController = Mockito.spy(twitterController);
//    String textEx = "Example Text test";
//
//    String[] input = {"post", textEx, "-30d:60d"};
//    Tweet tweet = spyController.postTweet(input);
//    assertNotNull(tweet);
    when(twitterService.postTweet(any())).thenReturn(new Tweet());
    String[] input = {"post", "This is some text to post", "-30:60"};
    Tweet tweet = twitterController.postTweet(input);
    assertNotNull(tweet);
  }

  @Test
  public void showTweet(){
    when(twitterService.showTweet(any(),any())).thenReturn(new Tweet());
    String[] input = {"show", "1234567890123456789", "created_at,id_str"};
    Tweet tweet = twitterController.showTweet(input);
    assertNotNull(tweet);
  }

  @Test public void deleteTweet(){
    Tweet tweet = new Tweet();
    List<Tweet> tweetList = new ArrayList<>();
    tweetList.add(tweet);
    when(twitterService.deleteTweets(any())).thenReturn(tweetList);
    String[] input = {"delete", "1111111111111111111,0123456789012345678"};
    List<Tweet> tweets = twitterController.deleteTweet(input);
    for (Tweet tweetObj : tweets){
      assertEquals(null, tweet.getId());
      assertEquals(null, tweet.getText());
      assertEquals(null, tweet.getCreatedTime());
      assertEquals(null, tweet.getEntities());
      assertEquals(null, tweet.getCoordinates());
      assertEquals(null, tweet.getId_str());
      assertEquals(null, tweet.getFavoriteCount());
      assertEquals(null, tweet.getRetweetCount());
      assertEquals(null, tweet.getFavorited());
      assertEquals(null, tweet.getRetweeted());
    }
  }
}