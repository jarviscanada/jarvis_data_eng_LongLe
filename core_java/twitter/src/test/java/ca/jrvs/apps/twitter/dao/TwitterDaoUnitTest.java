package ca.jrvs.apps.twitter.dao;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.isNotNull;
import static org.mockito.ArgumentMatchers.nullable;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.when;

import ca.jrvs.apps.example.JsonParser;
import ca.jrvs.apps.twitter.dao.helper.HttpHelper;
import ca.jrvs.apps.twitter.model.Tweet;
import ca.jrvs.apps.twitter.model.TweetConstructor;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class TwitterDaoUnitTest {

  @Mock
  HttpHelper mockHelper;

  @InjectMocks
  TwitterDAO dao;

  @Test
  public void showTweet() throws Exception {
    //Test failed request
    String hashTag = "#abc";
    String text = "@someone sometext " + hashTag + " " + System.currentTimeMillis();
    Double lat = 1d;
    Double lon = -1d;

    //Exception is exepected here
    when(mockHelper.httpPost(isNotNull())).thenThrow(new RuntimeException("mock"));
    try {
      dao.create(TweetConstructor.buildTweet(text, lon, lat));
      fail();
    } catch (RuntimeException e) {
      assertTrue(true);
    }

    //Test happy path
    //However, we don't want to call parseResponseBody
    //We will make a spyDAO which can fake parseResponseBody return value
    String tweetJsonStr = "{\n"
        + "   \"created_at\":\"Mon Feb 21:24:39 +1111 2019\", \n"
        + "   \"id\":1205582607922139137,\n"
        + "   \"id_str\":\"1205582607922139137\",\n"
        + "   \"text\":\"some text here\",\n"
        + "   \"entities\":{\n"
        + "       \"hashtag\":[],\n"
        + "       \"user_mentions\":[]\n"
        + "   },\n"
        + "   \"coordinated\":null,\n"
        + "   \"retweet_count\":0,\n"
        + "   \"favorited_count\":0,\n"
        + "   \"favorited\":false,\n"
        + "   \"retweeted\":false\n"
        + "}";

    when(mockHelper.httpPost(isNotNull())).thenReturn(null);
    TwitterDAO spyDao = Mockito.spy(dao);
    Tweet expectedTweet = JsonParser.toObjectFromJson(tweetJsonStr, Tweet.class);

    //Mock parseResponseBody
    doReturn(expectedTweet).when(spyDao).parseResponse(any());
    Tweet tweet = spyDao.create(TweetConstructor.buildTweet(text, lon, lat));
    assertNotNull(tweet);
    assertNotNull(tweet.getText());
  }

}
