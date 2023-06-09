# Pokedex_BackEnd_Project
Author: Jaleta Tesgera <br>
Date: June, 08, 2023

# Project Description
This project is a backend for a Pokemon api. This api end points that lets users execute tasks such as, but not limited to, retrieving a paginated list of pokemon, retrieving a specific pokemon by providing its number, or filter pokemon by providing its type. Moreover, if users want to engage with the api and train pokeomons they will need to create their account from a keycloak api end point, by getting a master token from a keycloak admin. They will be able to execute tasks such as, but not limited to, capturing pokemons, or retrieveing a list of pokemons they have captured. (More end points in the How to Use the project section below.)

# How to Install and Run
1. Both the postgress sql and keycloak are set up in the pokedex docker container and you will find a "docker-compose.yml" file that contains both configurations.
2. Port, database connection, and spring securty is set up in the application.properties file.
3. The keycloak relam import json file for the realm ("pokedexapi") should be found in the keycloak, realm folder.
4. Command to run the docker compose: docker compose up -d (this comand willl run also automatiucally import the "pokedexapi" relam to the running keycloak instance)
5. Once that docker container has started running, run the spring boot application with pokedex bootRun which will start its server on (http://localhost:8081/)
6. If you have run it sucessfully, and created the necessary databases please find the resources/db/changelog/changelog_master.xml and comment out the changelog includeAll code line as the changelogs should only run once to create the tables, load their data, and create their relationships.
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
NB: Please note that since you ran all the changelog files as new and filled table values from local csv files, the captured table will be empty. Thus, you will need to run the capture api to capture some pokemon in order to retrive a list of captured pokemon by the authenticated trainer, or else the retrieve end point will return an empty list.
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
11. Combining all filters. The parameters (Request(Query)Parameters) for this end point are all optional being that if all of them are null (value not specified) it returns a paginated list of all pokemons, the more parameters applied the narrower the list that fit them. A sample Query Param could look likem this: End Point: http://localhost:8081/api/pokemon/filter

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
This note is from past reviwer comment on other codes and the exceptions I have on my code.
1. You will notice a separate "040_autoincrement_pokemon_captured.xml" liquibase file instead of just having the id to autoincrement in the tabe creation. For some reason I kept getting an "unsupported" error when I had it as a property of a colummn "autoincrememt = true" so I made a separate liquibase changeset for it.
2. In my changelogs you will notice that there are upto 40 liquibase migrations, but it is actually not, I have an order system where for every type of chnagelog I jump to a new double digit. Meaning create, load, relationship, and autoincrement are all on different 2nd digit indexes (-0-, -2-, -3-, and -4-). I did this to leave space for unpredicatbale tables I might have to create, load data, or relate in the future and still maintain a numeral order, and good organization in my liquibase migrations.
3. In the pokemon service class the combinedPokemonFilter function makes use of the nullable? operation. Eventhough it was recommended that I don't use nullable variables, I had to use them in this case since my functions execution and result depends on these values such that the function should still execute even if the parameters are null or not.
4. Code is formatted with a Prettier Plugin.

# Resources

Raw Pokemon List CSV File: https://bitbucket.org/!api/2.0/snippets/myriadmobile/Rerr8E/96d04ea30f8e177149dd0c1c98271f1843b5f9b7/files/pokedex.csv <br>
Wrangled and Organized CSV Files: https://github.com/JaletaTesgera/Pokedex/tree/main/DBFiles <br> <br>

** Debugging and General: https://stackoverflow.com/ <br> <br>

** Kotlin Resources:  <br>
*.   Learn Kotlin Programming - Full Courses For Beginners: https://www.youtube.com/watch?v=EExSSotojVI <br>
*.   Genral: https://kotlinlang.org/ <br> <br>

** Spring Boot Resources: <br>
*.   General: https://kotlinlang.org/ <br>
*.   Spring Boot Tutorials Full Course: https://www.youtube.com/watch?v=9SGDpanrc8U <br>
*.   Baeldung | Learn Spring Boot: https://www.baeldung.com/spring-boot <br>
** Data JPA: <br>
*.   Getting Started | Accessing Data with JPA: https://spring.io/guides/gs/accessing-data-jpa/ <br>
*.   Introduction to Spring Data JPA: https://www.baeldung.com/the-persistence-layer-with-spring-data-jpa <br>
*.   Database Access using Spring JPA: https://www.youtube.com/watch?v=P8Y4VxR1UtA <br>
*.   How to write custom method in repository in Spring Data JPA: https://javatute.com/jpa/how-to-write-custom-method-in-repository-in-spring-data-jpa/ <br> <br>

** Liquibase Documentations: https://docs.liquibase.com/home.html <br> <br>

** Postman Resources: <br>
*.   Postman Learning Center: https://learning.postman.com/ <br>
*.   Postman Beginer's Course - API Testing: https://www.youtube.com/watch?v=VywxIQ2ZXw4 <br> <br>

** KeyCloak Resources: <br>
*.   Running KeyCloak from a docker container: https://bushel.atlassian.net/wiki/spaces/SBW/pages/8658059287/Configuring+local+Keycloak+instance+like+Bushel+SSO <br>
*.   A Quick Guide to Using Keycloak with Spring Boot: https://www.baeldung.com/spring-boot-keycloak <br>
*.   KeyCloak Documentation: https://www.keycloak.org/documentation <br>
*.   Using KeyCloak with Spring Boot 3.0: https://medium.com/geekculture/using-keycloak-with-spring-boot-3-0-376fa9f60e0b <br>
*.   KeyCloak - Creating user from API end point: https://www.youtube.com/watch?v=kIXs5k4gyuM <br>

------- END -------
