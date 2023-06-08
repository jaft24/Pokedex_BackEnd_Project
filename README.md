# Pokedex_BackEnd_Project
Author: Jaleta Tesgera
Date: June, 08, 2023

# Project Description
This project is a backend for a Pokemon api. This api end points that lets users execute tasks such as, but not limited to, retrieving a paginated list of pokemon, retrieving a specific pokemon by providing its number, or filter pokemon by providing its type. Moreover, if users want to engage with the api and train pokeomons they will need to create their account from a keycloak api end point, by getting a master token from a keycloak admin. They will be able to execute tasks such as, but not limited to, capturing pokemons, or retrieveing a list of pokemons they have captured.

# How to Install and Run
1. Both the postgress sql and keycloak are set up in the pokedex docker container and you will find a "docker-compose.yml" file that contains both configurations.
2. Port, database connection, and spring securty is set up in the application.properties file.
3. Command to run the docker compose: docker compose up -d
4. Once that docker container has started run the spring boot application with pokedex bootRun (http://localhost:8081/)
5. KeyCloak is running on http://localhost:8083/, and you should be able to access all the end points on Postman

# List of sample users created in KeyCloak
  username  Name          Email              Password
1	asmith    Alex Smith	  alex@gmail.com	   password123
2	jdoe      John Doe	    john@yahoo.com	   abcd1234
3	mwill     Marry Will	  marry@yahoo.com    a1b2c3d4
4 swalker   Sky Walker    sky@fastmail.com   starwars123

# How to use the project
# To create a user with POSTMAN
1. run a POST request with this end point asking for a master token from a master KeyCloak realm (http://localhost:8083/realms/master/protocol/openid-connect/token)
2. run a POST request with this end point in the pokedexapi realm 

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

# Resources
