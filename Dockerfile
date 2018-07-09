FROM openjdk:8-jre
ENTRYPOINT ["/usr/bin/java", "-jar", "/usr/share/database-action-monitor/application.jar"]

ARG JAR_FILE
ADD /target/${JAR_FILE} /usr/share/database-action-monitor/application.jar