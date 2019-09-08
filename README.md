# German Novikov
The solution to test task for BigBank IT.

#### Used technologies
- Java 1.8 
- Spring Boot 
- Gradle wrapper

## Run
### As Executable .jar
Package the project using the following command: 
```
gradle bootJar
```
After that you can run .jar file from a console using the command: 
```
java -jar dungeons-and-dragons-0.0.1-SNAPSHOT.jar
```
You can also provide the name of a dragon (optional) as the first argument.
For example:
```
java -jar dungeons-and-dragons-0.0.1-SNAPSHOT.jar MIGHTY_DRAGON
```

### Directly from an IDE
The solution can be launched from an IDE in two ways:
#### Using main class
Run main class named `Application` (package `com.german.dungeons.and.dragons`).
(Default name of the dragon will be provided).

#### Using test class
Run test class named `RunGameTest`.
(Default name of the dragon will be provided.)

<b>You can track the progress of the game and see the final result in the logs.</b>
Logs are generated for all three launch methods.

## "Dragons of Mugloar" - description of the solution
The solution includes:

- Sorting the tasks from the messageboard by priority and reward.
- Selecting the easiest task from the sorted list.
- Buying healing potion when lives are low (less than 3)
- Counting encrypted tasks and acting accordingly to the obtained result: 
  - a) if there are more than 4 encrypted tasks, then a book of tricks is purchased; 
  - b) a book of mega tricks is bought if there is more than
4 encrypted tasks and the dragon level is greater than 15.
- Counting failed tasks and acting accordingly to the obtained result:
  - a) if there are more than 2 failed tasks, then claw honing is purchased;
  - b) claw sharpening is purchased if there are more than 2 failed tasks
and dragon level is greater than 15.
- Counting tasks with high risk and acting accordingly to the obtained result:
  - a) if there are more than 4 tasks with high risk then potion 
  of awesome wings is bought;
  - b) potion of stronger wings is bought if there are more than 4
  tasks with high risk and dragon level is greater than 15

##### High score of the solution was 6183 points (see [attached screenshot](HighScoreSolution.png)).
