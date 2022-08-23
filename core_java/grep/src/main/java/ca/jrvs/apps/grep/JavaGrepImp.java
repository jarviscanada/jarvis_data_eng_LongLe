package ca.jrvs.apps.grep;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import org.apache.log4j.BasicConfigurator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class JavaGrepImp implements JavaGrep {

  final Logger logger = LoggerFactory.getLogger((JavaGrep.class));

  private String regex;
  private String rootPath;
  private String outFile;

  @Override
  public String getRegex() {
    return regex;
  }

  @Override
  public void setRegex(String regex) {
    this.regex = regex;
  }

  @Override
  public String getRootPath() {
    return rootPath;
  }

  @Override
  public void setRootPath(String rootPath) {
    this.rootPath = rootPath;
  }

  @Override
  public String getOutFile() {
    return outFile;
  }

  @Override
  public void setOutFile(String outFile) {
    this.outFile = outFile;
  }

  @Override
  public void process() throws IOException {
    List<String> lines = new ArrayList<>();

    for (File file : listFiles(rootPath)) {
      for (String line : readLines(file)) {
        if (containsPattern(line)) {
          lines.add(file.getName() + ": " + line);
        }
      }
    }
    writeToFile(lines);
  }


  @Override
  public List<File> listFiles(String rootDir) {
    File filePath = new File(rootDir);

    //Capture all files and directories in file path
    File[] list = filePath.listFiles();
    List<File> allFile = new ArrayList<>();

//    for (File file : list) {
//      //There are directories and files in this. So we need to check if File file is a directory or a fiel
//      //If it's a file
//      if (file.isFile()) {
//        allFile.add(file);
//      }
//      //If it's a diretory
//      else {
//        allFile.addAll(listFiles(file.getAbsolutePath()));
//      }
//    }
//    return allFile;

    if (filePath.isDirectory()) {
      for (File file : list) {
        if (file.isFile()) {
          allFile.add(file);
        } else if (file.isDirectory()) {
          allFile.addAll(listFiles(file.getAbsolutePath()));
        }
      }
    }
    return allFile;
  }

  @Override
  public List<String> readLines(File inputFile) {
    List<String> allLines = new ArrayList<>();

    /*
    FileReader read contents of inputFile and stored the data using character encoding
    BufferedReader reads text from fileReader by wrapping FileReader and buffering character
     */
    try (BufferedReader br = new BufferedReader(new FileReader(inputFile))) {
      String line;

      while ((line = br.readLine()) != null) {
        allLines.add(line);
      }
    } catch (IOException e) {
      throw new IllegalArgumentException("USAGE: Illegal Input File");
    }
    return allLines;
  }

  @Override
  public boolean containsPattern(String line) {
    return line.matches(this.regex);
  }

  @Override
  public void writeToFile(List<String> lines) throws IOException {
    try {
      BufferedWriter buffWriter = new BufferedWriter((new FileWriter(outFile)));
      for (String l : lines) {
        buffWriter.write(l + "\n");
      }
      buffWriter.close();
    } catch (IOException e) {
      e.getMessage();
    }
  }

  public static void main(String[] args) {
    if (args.length != 3) {
      throw new IllegalArgumentException("USAGE: JavaGrep regex rootPath outFile");
    }

    //Use default logger config
    BasicConfigurator.configure();

    JavaGrepImp javaGrepImp = new JavaGrepImp();
    javaGrepImp.setRegex(args[0]);
    javaGrepImp.setRootPath(args[1]);
    javaGrepImp.setOutFile(args[2]);

    try {
      javaGrepImp.process();
    } catch (Exception ex) {
      javaGrepImp.logger.error("Error: Unable to process", ex);
    }
  }

}
