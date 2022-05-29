package ca.jrvs.apps.grep;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class JavaGrepLambdaImp extends JavaGrepImp {

  final Logger logger = LoggerFactory.getLogger((JavaGrep.class));

  /**
   * Traverse a given directory and return all files
   *
   * @param rootDir input directory
   * @return files under the rootDir
   */
  @Override
  public List<File> listFiles(String rootDir) {
    List<File> pathList = new ArrayList<>();

    try {
      pathList = Files.walk(Paths.get(rootDir)).filter((Files::isRegularFile)).map(Path::toFile)
          .collect(Collectors.toList());
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
    return pathList;
  }

  /**
   * Read a file and return all the lines
   * <p>
   * Explain FileReader, Buffered Reader, and character encoding
   *
   * @param inputFile file to be read
   * @return lines
   * @throws IllegalArgumentException if a given inputFile is not a file
   */
  @Override
  public List<String> readLines(File inputFile) {
    List<String> fileList = new ArrayList<>();
    try (Stream<String> lines = Files.lines(inputFile.toPath())) {
      fileList = lines.collect(Collectors.toList());
    } catch (IOException e) {
      throw new IllegalArgumentException("USAGE: Illegal Input File");
    }
    return fileList;
  }

  public static void main(String[] args) {
    if (args.length != 3) {
      //Creating JavaGrepLambdaImp instead of JavaGrepImp
      //JavaGrepLambdaImp inherits all methods except two override methods
      JavaGrepLambdaImp javaGrepLambdaImp = new JavaGrepLambdaImp();
      javaGrepLambdaImp.setRegex(args[0]);
      javaGrepLambdaImp.setRootPath(args[1]);
      javaGrepLambdaImp.setOutFile(args[2]);

      try {
        //calling parent method,
        //but it will call override method (in this class)
        javaGrepLambdaImp.process();
      } catch (Exception e) {
        e.printStackTrace();
      }
    }
  }
}
