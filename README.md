MLexam
July 2014

## Tools and Technologies used:
- JDK 1.7 + JRE 1.7
- Maven 3.2.2
- Hibernate
- Eclipse Kepler (with eGit and m2e plugins)
- Google App Engine 1.9.7
- Heroku PostgreSQL (Cloud DB)

## See it running well on local, but with problems on cloud
http://youtu.be/gfH5vdDB0Ig

## How to run on local?
- Clone this repository on the workspace of Eclipse
- In Eclipse, import as a Maven project (Eclipse must have installed mentioned plugins)
- Wait that Maven download all libraries and AppEngineSDK (can take up to 30 minutes)
- Deploy the project on local appengine devserver (Right click on project>Run As>Maven build...>Goal: appengine:devserver)
- Use the API URLs that are on the class com.ml.weatherforecast.api.V1_Weather, using the prefix /api/v1/clima/. 
Example: http://localhost:8080/api/v1/client/helloWorld


Sebastián Prestifilippo
Software Engineer
www.sebapresti.com.ar
