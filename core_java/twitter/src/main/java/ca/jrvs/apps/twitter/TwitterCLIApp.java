package ca.jrvs.apps.twitter;


import ca.jrvs.apps.example.JsonParser;
import ca.jrvs.apps.twitter.controller.Controller;
import ca.jrvs.apps.twitter.controller.TwitterController;
import ca.jrvs.apps.twitter.dao.CrdDao;
import ca.jrvs.apps.twitter.dao.TwitterDAO;
import ca.jrvs.apps.twitter.dao.helper.HttpHelper;
import ca.jrvs.apps.twitter.dao.helper.TwitterHTTPHelper;
import ca.jrvs.apps.twitter.model.Tweet;
import ca.jrvs.apps.twitter.service.TwitterService;
import com.fasterxml.jackson.core.JsonProcessingException;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class TwitterCLIApp {

  private Controller controller;

  @Autowired
  public TwitterCLIApp(Controller controller){
    this.controller = controller;
  }

  public static void main(String[] args) {
    String consumerKey= System.getenv("consumerKey");
    String consumerSecret = System.getenv("consumerSecret");
    String accessToken = System.getenv("accessToken");
    String tokenSecret = System.getenv("tokenSecret");

    // setting up the controller
    HttpHelper httpHelper = new TwitterHTTPHelper(consumerKey, consumerSecret, accessToken,tokenSecret);
    CrdDao dao = new TwitterDAO(httpHelper);
    TwitterService twitterService = new TwitterService(dao);
    Controller controller = new TwitterController(twitterService);
    TwitterCLIApp app = new TwitterCLIApp(controller);

    app.run(args);
  }

  public void run(String[] args) {
    if (args.length == 0){
      throw new IllegalArgumentException("USAGE: Please enter 3 arguments.");
    }
    switch (args[0].toLowerCase()){
      case "post":
        printTweet(controller.postTweet(args));
        break;
      case "show":
        printTweet(controller.showTweet(args));
        break;
      case "delete":
        controller.deleteTweet(args).forEach(this::printTweet);
        break;
      default:
        throw new IllegalArgumentException("Error: the action you enter is not valid. "
            + "Please use one of \"post\", \"show\", or \"delete\".\n"
            + "Format: TwitterCLIApp \"post/show/delete\" [option].");
    }
  }

  private void printTweet(Tweet tweet){
    try{
      System.out.println(JsonParser.toJson(tweet,true, false));
    } catch (JsonProcessingException e) {
      throw new RuntimeException("Error: Failed to convert from tweet object to Json. ",  e);
    }
  }

}