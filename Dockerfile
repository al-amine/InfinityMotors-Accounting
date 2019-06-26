FROM openjdk
ADD /target/accounting-app-0.0.1-SNAPSHOT.jar jarFile.jar
CMD java -jar jarFile.jar

