#German Novikov
Solution of test task for BigBank IT.

####Dependencies
- Java 1.8 
- Spring Boot 
- Gradle wrapper.

##Run application
For running application need package project using command 
```
gradle bootJar
```
after that you can run jar file from console using command 
```
java -jar dungeons-and-dragons-0.0.1-SNAPSHOT.jar"
```
As argument added possibility to add dragon name.

Second variant: solution can be run from IDE if run main class Application
 
Third variant to run application is run test class RunGameTest.

<b>Process and result of game you can see in logs.</b>

##First solution of "Dragons of Mugloar"
First solution consist:

- Sorting task by priority
- Take the most easy task from sorting list
- Buy healing point when lives less than 3
- Count encrypted task if number more than 4 buy book of tricks or buy book of megatricks if dragon level more than 15
- Count failed task if number more than 2 buy claw honing or buy claw sharpening if dragon level more than 15
- Count task with high risk if number more than 4 buy potion of awesome wings or buy potion of stronger wings if dragon level more than 15

#####High score of first solution was 6183 add screenshot
