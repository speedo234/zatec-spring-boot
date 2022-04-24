FROM tomcat:9-jre11-temurin

COPY ./target/zatec-0.0.1-SNAPSHOT.war /usr/local/tomcat/webapps/


RUN mv /usr/local/tomcat/webapps/zatec-0.0.1-SNAPSHOT.war /usr/local/tomcat/webapps/ROOT.war

EXPOSE 8093

CMD java -version
CMD ["catalina.sh", "run"]