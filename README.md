# admin_authorization

This application is a corporate user authorization system through an administrator account.
Only an administrator can register a user in system. After registration, 
the user can log in using the login and password provided by the administrator.
After logging in to the account, the user can specify his personal data - phone and email address.

# Used technologies

Implemented with:
<ul>
 <li>JDK 17</li>
 <li>Maven 3.8.5</li>
 <li>Spring Boot 2.7.3</li>
 <li>Bootstrap 4.4.1</li>
 <li>Thymeleaf 2.7.3</li>
 <li>JDBC 4</li>
 <li>Hibernate 5.6.11.Final</li>
 <li>PostgreSQL 42.2.9</li>
 <li>Liquibase 4.15.0</li>
</ul>

# Environment requirements

<ul>
 <li>Create db "admin_auth". Login: postgres, password: password</li>
 <li>Create .jar file via maven command "mvn package"</li>
 <li>Go to the Target folder and check the presence of "admin_authorization-1.0-SNAPSHOT.jar" file</li>
 <li>Open the command line, go to the Target folder</li>
 <li>Run this file through "java -jar admin_authorization-1.0-SNAPSHOT.jar" command</li>
 <li>Then go to the "http://localhost:8080/index" page</li>
</ul>

Contact me: itfedorovsa@gmail.com

