# URL-Shortener-Service

Note: Requires Java 1.8 

- Execute the following sql statements
create database troops;
DROP table IF EXISTS urlclicks;
DROP table IF EXISTS urls;

^
create table urls
(
	id int unsigned AUTO_INCREMENT,
	url varchar(2083),
    fCountCode varchar(100),
    createdOn timestamp DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (id)
);
ALTER TABLE urls AUTO_INCREMENT=3364;
ALTER TABLE urls ADD INDEX idx_fCountCode (fCountCode);

^
create table urlclicks
(
	id int unsigned,
    clicks bigint unsigned DEFAULT 0,
    lastAccessedOn timestamp DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (id) REFERENCES urls(id)
);

- Provide the following information in hikari.properties file
	* Database username and password
	- dataSource.user (default is root)
	- dataSource.password (default is root)

	* Database hostname and port
	- dataSource.portNumber (default is 3306)
	- dataSource.serverName (default is localhost)

- Provide the following information in config.properties file
	- hostname (default is localhost)

- Execute using the jar
java -cp GetTroops-1.0-jar-with-dependencies.jar com.troops.service.URLShortenerService

- Compile and Execute
mvn clean compile assembly:single

- To create the short url 
Request:
POST http://localhost:4567/shorten
{
	"url":"google.com"
}

Response:
{
  "ShortURL": "http://localhost:4567/bab"
}

- To get the long url
Request:
GET http://localhost:4567/baa
* where "baa" shortened url code

Response
{
  "url": "google.com"
}

- To get the number of clicks for short url 
Request:
GET http://localhost:4567/getclicks/baa
* where "baa" shortened url code
Response:
{
  "url": "google.com",
  "clicks": 5,
  "createdOn": "2016-05-01 00:11:07.0",
  "lastAccessedOn": "2016-05-01 15:47:32.0"
}

- Folder GetTroops contains all the sources and can imported in eclipse using option
	"import existing maven projects"




