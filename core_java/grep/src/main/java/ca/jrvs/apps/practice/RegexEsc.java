package ca.jrvs.apps.practice;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public interface RegexEsc {

  /**
   * return true if filename extension is jpg or jpeg (case insensitive)
   *
   * @param filename
   * @return
   */
  boolean matchJpeg(String filename);

  /**
   * return true if ip is valid
   *
   * @param ip
   * @return
   */
  boolean matchIp(String ip);

  /**
   * return true if line is empty (e.g empty, white space, tabs, etc..)
   *
   * @param line
   * @return
   */
  boolean isEmptyLine(String line);
}
