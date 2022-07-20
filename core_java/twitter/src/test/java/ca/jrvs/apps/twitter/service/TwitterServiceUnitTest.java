package ca.jrvs.apps.twitter.service;

import static org.mockito.Mockito.*;
import ca.jrvs.apps.twitter.dao.CrdDao;
import ca.jrvs.apps.twitter.model.Tweet;
import ca.jrvs.apps.twitter.model.TweetConstructor;
import java.util.List;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.junit.Assert.*;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class TwitterServiceUnitTest {

  @Mock
  CrdDao dao;

  @InjectMocks
  TwitterService twitterService;

  @Test
  public void postTweet(){
    Tweet tweet = new Tweet();
    when(dao.create(any())).thenReturn(tweet);
    tweet = twitterService.postTweet(TweetConstructor.buildTweet("some test", 30.0, 0.0));
    tester(tweet);
  }

  @Test
  public void showTweet(){
    Tweet tweet = new Tweet();
    String id = "0123456789012345678";
    String[] fields = {"created_at", "id_str"};
    when(dao.findById(any())).thenReturn(tweet);
    tweet = twitterService.showTweet(id, fields);
    tester(tweet);
  }

  @Test
  public void deleteTweet(){
    Tweet tweet = new Tweet();
    String[] idArray = {"1111111111111111111", "0123456789012345678"};
    when(dao.deleteById(any())).thenReturn(tweet);
    List<Tweet> tweets = twitterService.deleteTweets(idArray);
    for (Tweet tweetObj : tweets){
      tester(tweetObj);
    }
  }

  private void tester(Tweet tweet){
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