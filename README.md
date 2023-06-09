# Pokedex_BackEnd_Project
Author: Jaleta Tesgera <br>
Date: June, 08, 2023

# Project Description
This project is a backend for a Pokemon api. This api end points that lets users execute tasks such as, but not limited to, retrieving a paginated list of pokemon, retrieving a specific pokemon by providing its number, or filter pokemon by providing its type. Moreover, if users want to engage with the api and train pokeomons they will need to create their account from a keycloak api end point, by getting a master token from a keycloak admin. They will be able to execute tasks such as, but not limited to, capturing pokemons, or retrieveing a list of pokemons they have captured. (More end points in the How to Use the project section below.)

# How to Install and Run
1. Both the postgress sql and keycloak are set up in the pokedex docker container and you will find a "docker-compose.yml" file that contains both configurations.
2. Port, database connection, and spring securty is set up in the application.properties file.
3. The json file for the realm I created ("pokedexapi") should be found in the keycloak, realm folder.
4. Command to run the docker compose: docker compose up -d
5. Once that docker container has started run the spring boot application with pokedex bootRun (http://localhost:8081/)
6. If you have run it sucessfully please find the resources/db/changelog/changelog_master.xml and comment out the changelog include files as they only should be run once to create the tables, load their data, and create their relationships.
7. KeyCloak is running on http://localhost:8083/, and you should be able to access the pokedexapi realm by utilizing the password and name specified in the "docker-compose.yml" file, and run all the end points on Postman

# List of sample users created in KeyCloak

|   Username    |      Name     |     Email        |    Password   |
| ------------- | ------------- | ---------------- | ------------- |
| asmith        | Alex Smith    | alex@gmail.com   | password123   |
| jdoe          | John Doe      | john@yahoo.com   | abcd1234      |
| mwill         | Marry Will    | marry@yahoo.com  | a1b2c3d4      |
| swalker       | Sky Walker    | sky@fastmail.com | starwars123   | 

# How to use the project (POSTMAN)
I configured the security of the project where it would only ask for autentication if a capture end point is run ("/api/capture/- ") , and if users are running a pokemon end point ("/api/pokemon/- ") it will not ask for authentication, as anyone should be able to see, search, and filter through the pokemon api. (More information in the security configuration file)

# 1.To get a Token
The POST end point to get a token is: http://localhost:8083/realms/pokedexapi/protocol/openid-connect/token
Eg: Lets us simulate a login for Sky Walker (username and passowrd are the only user information needed.) The body (x-www-form-unlencoded) key values should contain the following information to get a token.
 |      Key      |               Value                |
 | ------------- | ---------------------------------- |
 | client_id     |  trainer                           |
 | username      |  swalker                           | 
 | password      |  starwars123                       |
 | grant_type    |  password                          |
 | client_secret |  sHXxsqaebT4jeGWlLqTHLs4EUqMG31R5  |
 
# 2. Capture End Points (Authorization JWT Token Needed = Bearer + Token)
1. Capture pokemon by specifying the Id. http://localhost:8081/api/capture/{id}
2. Retrive a list of all the captured pokemon by the Logged In trainer. http://localhost:8081/api/capture/getAll
3. Remove a pokemon from a captured list. http://localhost:8081/api/capture/remove/{id}
4. Remove all pokemons captured by a trainer. http://localhost:8081/api/capture/removeAll

# 3. Pokemon End Points (No Authorization Needed)
NB: Pagination rule if a Response Entity has 10 or more than 10 pokemons it will be paginated or else it is either a List of Pokemon or a single Pokemon
1. Get all pokemon. http://localhost:8081/api/pokemon/all
2. Get pokemon by Id. http://localhost:8081/api/pokemon/byID/{id}
3. Get pokemon by Name. http://localhost:8081/api/pokemon/byName/{name}
5. Filter pokemon by Genus. http://localhost:8081/api/pokemon/byGenus/{genus}
6. Filter pokemon by Height. http://localhost:8081/api/pokemon/byHeight/{height}
7. Filter pokemon by Weight. http://localhost:8081/api/pokemon/byWeight/{weight}
8. Filter pokemon by Type. http://localhost:8081/api/pokemon/byType/{type}
9. Filter pokemon by Ability. http://localhost:8081/api/pokemon/byAbility/{ability}
10. Filter pokemon by EggGroup. http://localhost:8081/api/pokemon/byEggGroup/{eggGroup}
11. Combining all filters. The parameters (Request(Query)Parameters) for this end point are all optional being that if all of them are null (value not specified) it returns a paginated list of all pokemons, the more parameters applied the narrower the list that fit them. A sample Query Param could look likem this:  
End Point: http://localhost:8081/api/pokemon/filter
|   Key    |      Value     |
| -------- | -------------- |
| genus    |  Seed Pokémon  |
| weight   |  69            |
| height   |  7             |
| type     |  grass         |
| ability  |  chlorophyll   |
| eggGroup |  monster       |

# Project Architecture
* **main**
  *  **pokedex**
       * config
       * controller
       * model
       * repositoy
       * service
* **test**
  *  **pokedex**
       * controllerTest
       * serviceTest
     
# Additional Notices for Reviwers
This notice is from the past reviews we have had and expections I have on my code.
1. You will notice a separate "040_autoincrement_pokemon_captured.xml" liquibase file instead of just having the id to autoincrement in the tabe creation. For some reason I kept getting that error when I had it as a proporty of a colummn "autoincrememt = true" so I made a separate liquibase changeset for it.
2. In all of my Test files you will see wild card imports for my models (import com.bushelpowered.jaleta.pokedex.model.*), and I wanted to let you know that I did it on purpose because my tests utilize all my models thus, I decided to do a wild card import instead of writing them all since it significantly shortens my code. Moreover, it is an import from my own files so I thought it would be okay to do so.

# Resources

Raw Pokemon List CSV File: https://bitbucket.org/!api/2.0/snippets/myriadmobile/Rerr8E/96d04ea30f8e177149dd0c1c98271f1843b5f9b7/files/pokedex.csv  
Wrangled and Organized CSV Files: https://github.com/JaletaTesgera/Pokedex/tree/main/DBFiles  

** Debugging and General: https://stackoverflow.com/

** Kotlin Resources: 
*.   Learn Kotlin Programming - Full Courses For Beginners: https://www.youtube.com/watch?v=EExSSotojVI
*.   Genral: https://kotlinlang.org/

** Spring Boot Resources:
*.   General: https://kotlinlang.org/
*.   Spring Boot Tutorials Full Course: https://www.youtube.com/watch?v=9SGDpanrc8U
*.   Baeldung | Learn Spring Boot: https://www.baeldung.com/spring-boot
** Data JPA:
*.   Getting Started | Accessing Data with JPA: https://spring.io/guides/gs/accessing-data-jpa/
*.   Introduction to Spring Data JPA: https://www.baeldung.com/the-persistence-layer-with-spring-data-jpa
*.   Database Access using Spring JPA: https://www.youtube.com/watch?v=P8Y4VxR1UtA
*.   How to write custom method in repository in Spring Data JPA: https://javatute.com/jpa/how-to-write-custom-method-in-repository-in-spring-data-jpa/

** Liquibase Documentations: https://docs.liquibase.com/home.html

** Postman Resources:
*.   Postman Learning Center: https://learning.postman.com/
*.   Postman Beginer's Course - API Testing: https://www.youtube.com/watch?v=VywxIQ2ZXw4

** KeyCloak Resources:
*.   Running KeyCloak from a docker container: https://bushel.atlassian.net/wiki/spaces/SBW/pages/8658059287/Configuring+local+Keycloak+instance+like+Bushel+SSO
*.   A Quick Guide to Using Keycloak with Spring Boot: https://www.baeldung.com/spring-boot-keycloak
*.   KeyCloak Documentation: https://www.keycloak.org/documentation
*.   Using KeyCloak with Spring Boot 3.0: https://medium.com/geekculture/using-keycloak-with-spring-boot-3-0-376fa9f60e0b
*.   KeyCloak - Creating user from API end point: https://www.youtube.com/watch?v=kIXs5k4gyuM

------- END -------