FROM openjdk:8
ADD /target/SchedulerApi.jar SchedulerApi.jar
EXPOSE 8888
ENTRYPOINT ["java","-jar","SchedulerApi.jar"]