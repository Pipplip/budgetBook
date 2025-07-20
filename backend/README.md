### Notes for Spring Boot JAX-RS Webservices

1) Spring initializr
<ol type="a">
<li>Spring Boot 3.4.1, War File, Java 17</li>
<li>Dependencies: Spring Web, h2 Database, MySQL Driver, Spring Java JPA, Lombok</li>
</ol>
2) Eclipse Import Maven Project

3) Optional: Add Server in Eclipse - here Wildfly (Please note: Spring Boot has a integrated Tomcat within Spring Web dependency)

4) Start create entities (An entitiy represents a DB table) User and BudgetList in a new package "entity"

5) Create Repository layer: a repository is a design pattern to manage storage and handle data access
Create new package "repository" and new Interfaces BudgetListItemRepository, UserRepository

6) Create Service layer: A service has business logic implementations
New Package "service" and Class UserService, BudgetListItemService

In the MVC pattern, the Entity, Service and Repository is the Model combined.

7) Create Controller: Controller handles http request from client, processes user input, calls methods in the service layer and returns response to the client (Manages traffic)
New Package "Controller" and class BudgetListController

8) Create dto (data transfer object) package and classes.
A DTO (Data Transfer Object) is a simple data object that is used to transfer data between different system components or layers. It contains only the necessary data and no business logic. DTOs optimize data transfer by containing only the relevant information and thus simplifying communication between systems.

9) Start application:

Define Database Configuration in application.properties and also Hibernate configuration

<ol type="a">
<li>Right click on pom.xml -> run as... -> Maven build... -> Goals: clean install</li>
<li>Right click on BudgetBookApplication -> run as... -> Java Application (Spring web has a tomcat integrated and starts automatically)</li>
</ol>

Problems with CrossOrigin errors:
Add config package and class WebConfig (allow origins...) in addCorsMappings

9) JWT - Json Web Token Authentication and Authorization
A JWT has a header.payload.signature all encrypted with BASE64
The payload has different "claims" like sub=Subject oder exp=Expiration Date of Token

See the Life-Cycle image:
src/main/resources/static/images where all images in a Spring Boot application are located.

Import 3 dependencies in pom.xml (jsonwebtoken jjwt and spring-boot-starter-security)

Add implements UserDetails to User Entity.
Adapt WebConfig class with UserDetailsService
Create class JwtService

Add Filter:
New package "security" and class JwtAuthenticationFilter
and class SecurityConfiguration

Create new Endpoints for Authentication:
class Controller/AuthController and AuthenticationResponse and RegisterRequest and AuthenticationRequest

Create also new service/AuthService

The User Entity has been adapted and the UserService and UserController are now deprecated and should not be used anymore, because we use the AuthController Endpoint for user administration now.

---

Typischer Ablauf einer Abfrage (s. Bild in resources)
Zusammenfassung des Ablaufs einer HTTP-Anfrage mit Filter und Security in Spring Boot:

- Eingehende Anfrage: Der Client sendet eine HTTP-Anfrage an den Server.
- Filter-Kette: Die Anfrage durchläuft alle konfigurierten Filter (z. B. Authentifizierungsfilter, Logging-Filter).
- Spring Security:
-- Spring Security überprüft die Authentifizierung (z. B. mit einem JWT-Token).
-- Es wird geprüft, ob der Benutzer autorisiert ist, auf den Endpunkt zuzugreifen.
- DispatcherServlet: Nach der Sicherheitsprüfung wird die Anfrage an den passenden Controller weitergegeben.
- Verarbeitung im Controller: Der Controller verarbeitet die Anfrage und gibt eine Antwort zurück.
- Rückgabe der Antwort: Die Antwort wird durch Spring oder andere Filter nachbearbeitet und dann an den Client gesendet.
- Optional: Nachbearbeitung durch Filter: Antwort kann durch Response-Filter weiter bearbeitet werden.
