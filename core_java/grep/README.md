#  Core Java Grep Application
## Introduction
The Java Grep application is an application that imitates the command ```grep``` in Linux. The app searches for a text pattern _recursively_ in a given directory, and outputs matched lines to a file. The app takes three arguments:

``` 
USAGE: regex rootPath outFile
- regex: a special text string for describing a search pattern
- rootPath: root directory path
- outFile: output file name

Similar to
egrep -r {regex} {rootPath} > {outFile}
```
## Quick Start
 To start the project, you can launch a JVM and run the app:
 - This is the sample syntax to run the program:
 ``` java -cp {path to .jar file} {path to the app} {regex patter} {output file path}```
 
- In my personal project and directory, this command is used to run the app:
```java -cp target/grep-1.0-SNAPSHOT.jar ca.jrvs.apps.grep.JavaGrepImp .*Romeo.*Juliet.* ./data ./out/grep.txt```

## Implementation
### Pseudocode
How the `process` method works in pseudocode:
```
matchedLines = [] 
for file in listFilesRecursively(rootDir) 
	for line in readLines(file) 
		if containsPattern(line) 
			matchedLines.add(line) 
writeToFile(matchedLines)
```
- The process creates an array to store all the lines in files that matches the regex pattern. 
- Then it searches the root directory for files or other directories for more files recursively. Then it reads each line in files to find the matching pattern.
- If the matching pattern in a line is found, it adds that line into the array.
- Lastly, it writes all the stored lines in the array to an output file.

## Performance Issue
As searches are performed line by line, file by file, `space complexity: O(1)` & `time complexity: n O(n)` this program may run slowly when processing large files with large amounts of lines.
To be more efficient, a different program with different algorithms may have to be developed for more complex files

## Test
For every newly written method, a test is done to lower the range of testing that needs to be done. Bugs and errors are debugged using the built-in Debug system inside IntelliJ IDEA.

## Deployment
This application used Core Java as its main programming language. Maven is also used for the development of the app. 
There are two versions of the apps that used different coding patterns:
- `JavaGrepImp.java`: This application uses a more familiar coding style with simple recursion and standard `java.io` libraries. It implements the `JavaGrep` interface.
- `JavaGrepLambdaImp.java`: This application uses the new `Java 8 Lambda and Stream API` expressions to implement the `grep` function, instead of using traditional `for loop` expressions.

After the development phase, the application is dockerized and uploaded to DockerHub, ready for consumers to use.
## Improvement
- As this is my first time learning and using `Lambda and Stream API`, it took a lot of time to learn and refractor the `JavaGrepImp` codes. More practices are needed to be familiarized with `Lambda and Stream API`.
- Stream-based implementation may run better in a pure stream environment instead of inheriting from the list-based implementation.
