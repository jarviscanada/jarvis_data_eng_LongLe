package ca.jrvs.apps.twitter.dao;

import ca.jrvs.apps.example.JsonParser;
import ca.jrvs.apps.twitter.dao.helper.HttpHelper;
import ca.jrvs.apps.twitter.model.Tweet;
import com.google.gdata.util.common.base.PercentEscaper;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

@Repository
public class TwitterDAO implements CrdDao<Tweet,String> {

  private static final String API_BASE_URI = "https://api.twitter.com";
  private static final String POST_PATH = "/1.1/statuses/update.json";
  private static final String SHOW_PATH = "/1.1/statuses/show.json";
  private static final String DELETE_PATH = "/1.1/statuses/destroy";

  private static final String QUERY_SYM = "?";
  private static final String AMPERSAND = "&";
  private static final String EQUAL = "=";

  private static final int HTTP_OK = 200;

  private HttpHelper httpHelper;

  @Autowired
  public TwitterDAO (HttpHelper httpHelperIn){
    this.httpHelper = httpHelperIn;
  }

  @Override
  public Tweet create(Tweet tweet) {
    URI uri;
    try {
      uri = getPostUri(tweet);
    } catch (URISyntaxException e){
      throw new IllegalArgumentException("Invalid tweet input ", e);
    }

    HttpResponse response = httpHelper.httpPost(uri);
    return parseResponse(response);
  }

  private URI getPostUri(Tweet tweet) throws URISyntaxException {
    String status = tweet.getText();
    Double longitude = tweet.getCoordinates().getCoordinatesTweet().get(0);
    Double lat = tweet.getCoordinates().getCoordinatesTweet().get(1);
    PercentEscaper percentEscaper = new PercentEscaper("",false);
    return new URI(API_BASE_URI + POST_PATH + QUERY_SYM + "status" + EQUAL +
        percentEscaper.escape(status) + AMPERSAND + "long" + EQUAL + longitude  +
        AMPERSAND + "lat" + EQUAL+lat);
  }

  @Override
  public Tweet findById(String id) {
    URI uri;
    try {
      uri = getShowUri(id);
    } catch (URISyntaxException e){
      throw new IllegalArgumentException("Invalid tweet input ", e);
    }

    HttpResponse response = httpHelper.httpGet(uri);
    return parseResponse(response);
  }

  private URI getShowUri(String id) throws URISyntaxException {
    return new URI(API_BASE_URI + SHOW_PATH + QUERY_SYM + "id" + EQUAL + id);
  }

  @Override
  public Tweet deleteById(String id) {
    URI uri;
    try {
      uri = getDeleteUri(id);
    } catch (URISyntaxException e){
      throw new IllegalArgumentException("Invalid tweet input ", e);
    }

    HttpResponse response = httpHelper.httpPost(uri);
    return parseResponse(response);
  }

  private URI getDeleteUri(String id) throws URISyntaxException {
    return new URI(API_BASE_URI + DELETE_PATH + "/" + id + ".json" );
  }

  public Tweet parseResponse(HttpResponse response){
    Tweet tweet = null;

    int status = response.getStatusLine().getStatusCode();
    if(status != HTTP_OK){
      try {
        System.out.println(EntityUtils.toString(response.getEntity()));
      }catch (IOException e){
        System.out.println("Response has no entity");
      }
      throw new RuntimeException("Unexpected HTTP status: " + status);
    }

    if (response.getEntity() == null){
      throw new RuntimeException("Empty response body.");
    }

    String jsonString;
    try{
      HttpEntity httpEntity = response.getEntity();
      jsonString = EntityUtils.toString(httpEntity);
    }catch (IOException e){
      throw new RuntimeException("Failed to convert from entity to String", e);
    }
    System.out.println(jsonString);
    try{
      tweet = JsonParser.toObjectFromJson(jsonString, Tweet.class);
    }catch (IOException e){
      throw new RuntimeException("Unable to convert from Json String to Object", e);
    }

    return tweet;
  }
}