FROM tomcat:9.0-alpine

COPY ./target/zatec-0.0.1-SNAPSHOT.war /usr/local/tomcat/webapps/

RUN rm -r /usr/local/tomcat/webapps/ROOT
RUN mv /usr/local/tomcat/webapps/zatec-0.0.1-SNAPSHOT.war /usr/local/tomcat/webapps/ROOT.war

EXPOSE 8089

CMD java -version
CMD ["catalina.sh", "run"]