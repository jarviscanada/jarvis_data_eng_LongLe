package ca.jrvs.apps.twitter.dao;

import ca.jrvs.apps.example.JsonParser;
import ca.jrvs.apps.twitter.dao.helper.HttpHelper;
import ca.jrvs.apps.twitter.model.Tweet;
import com.google.gdata.util.common.base.PercentEscaper;
import com.sun.jndi.toolkit.url.Uri;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;
import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;

public class TwitterDAO implements CrdDao<Tweet, String> {

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
  public TwitterDAO(HttpHelper httpHelperIn) {
    this.httpHelper = httpHelperIn;
  }

  /**
   * Create an entity(Tweet) to the underlying storage
   *
   * @param tweet entity that to be created
   * @return created entity
   */
  @Override
  public Tweet create(Tweet tweet) {
    URI uri;
    try {
      uri = getPostUri(tweet);
    } catch (URISyntaxException e) {
      throw new IllegalArgumentException("Invalid Tweet input", e);
    }

    //Execute HTTP Request
    HttpResponse response = httpHelper.httpPost(uri);

    //Validate response and deser response to Tweet Object
    return parseResponseBody(response, HTTP_OK);
  }

  private URI getPostUri(Tweet tweet) throws URISyntaxException {
    String status = tweet.getText();
    float longitude = tweet.getLocation().getCoordinates()[0];
    float lat = tweet.getLocation().getCoordinates()[1];
    PercentEscaper percentEscaper = new PercentEscaper("", false);
    URI uri = new URI(API_BASE_URI + POST_PATH + QUERY_SYM + "status" + EQUAL +
        percentEscaper.escape(status) + AMPERSAND + "long" + EQUAL + longitude +
        AMPERSAND + "lat" + EQUAL + lat);
    return uri;
  }

  private Tweet parseResponseBody(HttpResponse response, Integer expectedStatusCode) {
    Tweet tweet = null;

    //Check response status
    int status = response.getStatusLine().getStatusCode();
    if (status != expectedStatusCode) {
      try {
        System.out.println(EntityUtils.toString(response.getEntity()));
      } catch (IOException e) {
        System.out.println("Response has no entity");
      }
      throw new RuntimeException("Unexpected HTTP status: " + status);
    }

    //Convert Response Entity to str
    String jsonStr;
    try {
      jsonStr = EntityUtils.toString(response.getEntity());
    } catch (IOException e) {
      throw new RuntimeException("Unable to convert JSON str to Object", e);
    }

    //Deser JSON String to Tweet Object
    try {
      tweet = JsonParser.toObjectFromJson(jsonStr, Tweet.class);
    } catch (IOException e) {
      throw new RuntimeException("Unable to convert to JSON str to Object", e);
    }
    return tweet;
  }

  /**
   * Find an entity(Tweet) by its id
   *
   * @param id entity id
   * @return Tweet entity
   */
  @Override
  public Tweet findById(String id) {
    URI uri;
    try {
      uri = getUribyId(id);
    } catch (URISyntaxException e) {
      throw new IllegalArgumentException("Invalid tweet input ", e);
    }
    HttpResponse response = httpHelper.httpGet(uri);
    return parseResponseBody(response, HTTP_OK);
  }

  private URI getUribyId(String id) throws URISyntaxException {
    return new URI(API_BASE_URI + SHOW_PATH + QUERY_SYM + "id" + EQUAL + id);
  }

  /**
   * Delete an entity(Tweet) by its ID
   *
   * @param id of the entity to be deleted
   * @return deleted entity
   */
  @Override
  public Tweet deleteById(String id) {
    URI uri;
    try {
      uri = getDeleteUri(id);
    } catch (URISyntaxException e) {
      throw new IllegalArgumentException("Invalid tweet input ", e);
    }

    HttpResponse response = httpHelper.httpPost(uri);
    return parseResponseBody(response, HTTP_OK);
  }

  private URI getDeleteUri(String id) throws URISyntaxException {
    return new URI(API_BASE_URI + DELETE_PATH + "/" + id + ".json");
  }
}
