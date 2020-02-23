# flag-picker (https://github.com/sbnighut/flag-picker)
 This repo contains REST services using Spring Boot that allows user to pick different world flags. 
 After initial boot up this service picks up the content from the continents.txt file and uploads it to the database
 Flags can be fetched for either:
 
 `1. All the countries (**/country**)`
 
 `2. Specific country (**/country/{countryName}**)`
 
 `3. For countries belonging to a continent (**/country?continent=Asia**)`
 
**Steps to run the application.**
1. mvn clean install
2. java -jar target/demo-0.0.1-SNAPSHOT.jar

**API's exposed by the application:**
1. Get Country Flags by continent

    `http://localhost:8080/country?continent={continentName}`

2. Get individual country flag

    `http://localhost:8080/country/{countryName}`

**Known Limitations:**

1. The API is currently case sensitive for the path and query parameters.
2. Other CRUD operations such as POST, PUT and DELETE are not yet supported for simplicity.
3. A lot of things can be made confguration based such as file path for reading content, common 
place for storing exception templates etc. Omitting it for time being due to time constraints.

**Schema used for Database:**
1. I am using `NOSQL` database instead of `SQL`. 
    
**Reason behind using NOSQL:**

A traditional route was to create a SQL database with two tables "Country" and "Continent" and 
store the foreign key reference of Continent in the Country table.
But as per the requirements there is a dire need for search operations and also we are not updating 
continent or flag related info at all. Therefore It made a lot of sense to denormalize this data and 
keep them together in order to prevent Join operations, especially when this API will be used too frequently. 
And also country and flags have one to one relationship so they are anyways staying together. 

Why not use SQL with one table instead of MongoDB ?
At the moment both SQL and NOSQL would perform comparably the same. I am only using MongoDB because of the well 
defined regex support provided by MongoDB driver for querying records.
   
**NOSQL Scheme**

It's a single Country document with flag information as well as continent name (NOSQL Schema.png)
