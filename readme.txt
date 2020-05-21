Email client spring boot project.
Provides and endpoint to trigger a connection to the specified email service.
In the request you can specify the details of the connection and the email folder you want check and you can give a string to filter out parts of the subject.
It will connect and loop through every email every time you trigger the endpoint.
Every email subject, and email body(html multipart body tested) it found will be saved in the database.
It is a basic implementation what suits my need perfectly and hacked in an afternoon if you want to improve it be my guest but would be nice to know if my "baby" grows up :)
Please check the app properties related section to make sure you have everything set up.

Not unit tested / bdd tested.
Spring security enabled.
Postgres db.

The application.properties are outsourced. It is a good practice to do.
To start the application in the run/vm options
in windows the base is starting with the drive like C:\ "file:C:\<location>\application.properties"
-Dspring.config.location="file:/home/<location>/application.properties" 
if you want to build the jar from the command line:
mvn clean install -Dspring.config.location="file:/home/<location>/application.properties"
if you want to run from the command line:
java -jar email-client-1.0.0-SNAPSHOT.jar --spring.config.location="file:/home/<location>/application.properties"

!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
!Must define in application.properties to build/run the following properties!
!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!

spring.datasource.url=jdbc:postgresql://<your url e.g: localhost>:5432/<database_name>
spring.datasource.username=<dbusername>
spring.datasource.password=<db_password>
spring.security.user.name=<username_to_call_the_api>
spring.security.user.password=<password_to_call_the_api>
spring.security.user.role=ROLE_ADMIN

@POST {{url}}/rest/email
Example request for outlook. 
For other email providers duck-duck-go around it is easy to find the info.
Request body:
{
	"host":"outlook.office365.com",
	"port":"995",
	"protocol":"pop3",
	"email":"<email_address>",
	"pw":"<password>",
	"filter":"<String for subject.contains(filter)>"
	"folder":"<email folder like INBOX>"
}
