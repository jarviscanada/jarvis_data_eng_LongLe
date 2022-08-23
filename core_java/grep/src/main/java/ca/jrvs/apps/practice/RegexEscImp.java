package ca.jrvs.apps.practice;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegexEscImp implements RegexEsc {

  @Override
  public boolean isEmptyLine(String line) {
    Pattern p = Pattern.compile("^\\s*$");
    Matcher m = p.matcher(line);
    return m.matches();
  }

  @Override
  public boolean matchIp(String ip) {
    Pattern p = Pattern.compile("^(?:\\d{1,3}\\.){3}\\d{1,3}$");
    Matcher m = p.matcher(ip);
    return m.matches();
  }

  @Override
  public boolean matchJpeg(String filename) {
    Pattern p = Pattern.compile(".+\\.(jpg|jpeg)$");
    Matcher m = p.matcher(filename);
    return m.matches();
  }

  //Testing Java Regex
  public static void main(String[] args) {

    RegexEscImp test = new RegexEscImp();

    String line = "";
    String ip = "123.132.66.112";
    String filename = "abc.jpg";

    System.out.println(test.matchJpeg(filename));
    System.out.println(test.matchIp(ip));
    System.out.println(test.isEmptyLine(line));
  }
}
