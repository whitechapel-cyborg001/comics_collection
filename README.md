# comics_collection
####################################################################### 2025/01/03
JAVA CON SPRING BOOT

ENTORNO :   JAVA 21.0.9
            SPRING BOOT 4.0.1
            VSCODE
            MAVEN 3.9.12
            POSTGRESQL 16.11
            ¿POSTMAN o INSOMNIA?
            GIT

BASE DE DATOS POSTGRESQL: comics_collection

SPRING INITIALIZR dependencies:     Spring Web
                                    Spring Data JPA
                                    PostgreSQL Drive
                                    Spring Security
                                    Lowbok
                                    Spring Boot DecTools
                                    Validation

pom.xml -> added Springdoc OpenAI

application.properties added PostgreSQL + Swagger UI

http://localhost:8080

./config/SecurityConfig.java : Autologgin for tests

./WelcomeController.java : redirect to swagger-ui.html

Create entity : Published   - Editorial
                Series      - Colleccion
                Creator     - Author
                Comic       - Main class

####################################################################### 2025/01/04

psql : estructura de tablas creada por Hibernate -> ok

Crear reposity y controller para Comic, Publisher

####################################################################### 2025/01/09

JSON : 
{
  "title": "Amazing Fantasy #15",
  "issueNumber": 15,
  "year": 1962,
  "variant": "Standard Cover",
  "series": { "id": 1 },
  "creators": [ { "id": 1 } ],
  "owned": true,
  "condition": "Near Mint",
  "purchasePrice": 1000.0,
  "purchaseDate": "2020-01-01",
  "notes": "Primera aparición de Spider-Man",
  "coverImageUrl": "https://example.com/cover.jpg"
}

repository y controller para Series y Creator

Add validations to Comic, Creator, Publisher y Series.java

Exception control: exception/GlobalExceptionHandler.java