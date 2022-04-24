Tech Spec
Java JDK 11
Maven 3.6.3
Spring Boot 2.5.3
Packaging Jar
Port 8093
Swagger 3.0.0 ( http://localhost:8093/swagger-ui/index.html )


JAR FILE DEPLOYMENT

1. Open up a terminal.

2. CD to the project root directory where the pom.xml file is located and run the below command
   mvn clean install
   This would build a jar file and place it inside the target foler

3. CD to the project's target directory and there will be a jar file there.

3. CD to the same location where the jar file is and run the below command to start up the application packaged in the jar file.
   java -jar zatec-0.0.1-SNAPSHOT.jar

4. Application should be up and running on port 8093

5. Swagger will now be available on this url http://localhost:8093/swagger-ui/index.html to explore and test the end points.

=======================

TOMCAT DEPLOYMENT

If you would prefer to deploy on Tomcat then you have to change the packaging in the pom.xml file from jar to war

2. Open up a terminal.

3. CD to the project root directory where the pom.xml file is located and run the below command
   mvn clean install
   This would build a war file and place it inside the target foler.

4. Copy the war file into Tomcat webapp folder.

5. CD to tomcat bin directory and run the appropriate tomcat startup scripts below.
   startup or startup.sh

6. Tomcat would startup and explode the war file into a folder.

7. Remember to add the context path to the application url while testing or simply rename the war file to ROOT so that you do not need to add a context path to the application url while accessing the end points.








