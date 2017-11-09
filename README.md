# qvue-server

A qvue server for serving up call-center statDTOS



## Build steps
```
./gradlew build
```

## Deploy RPM package steps
```
./gradlew deployRpm
```

## Bumping the version

Use git-flow's release command.

1. Remove -SNAPSHOT from the version number in the `build.gradle` file by editing the `qvueServerVersion` property.
2. Finish the git-flow release.
3. On the develop branch, increment the `qvueServerVersion` and add "-SNAPSHOT" to the number.
4. push changes:
    - master branch to origin/master
    - develop branch to origin/develop

## Configuration
    
## Server Health
```
server:port/health - Shows application health information (when the application is secure, a simple ‘status’ when accessed over an unauthenticated connection or full message details when authenticated).
server:port/info - Displays arbitrary application info.
server:port/metrics - Shows ‘metrics’ information for the current application.
```

## Run

```
./gradlew bootRun -Dspring.profiles.active=qa
```

OR

```
java -Dspring.profiles.active=dev -Dspring.config.name=qvue-server-jar QVue-Server-0.1.1-SNAPSHOT-uber.jar
```

OR
```
Intellij
Gradle-project: qvue-server
Tasks: bootRun
VM options: -Dspring.profiles.active=dev -Dspring.config.name=qvue-server
```

## Run Bridge-hazelcast locally
``` 
 cd to your project folder/src/test/java/com/navis/qvueserver/external
java -DconfigPath=. -jar bridge-hazelcast-1.0.257-SNAPSHOT.jar
```
