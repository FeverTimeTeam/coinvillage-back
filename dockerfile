FROM openjdk:11
ARG JAR_FILE=build/libs/coinvillage-0.0.1-SNAPSHOT.war
COPY ${JAR_FILE} coinvillage-0.0.1-SNAPSHOT.war ./
ENTRYPOINT ["java","-jar","/coinvillage-0.0.1-SNAPSHOT.war"]