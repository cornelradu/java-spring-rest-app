# java-spring-rest-app

Prerequisites:
- mysql
- java 8
- Spring Boot 2.6.6
- maven

1. Install mysql:

apt-get install mysql-server

2. Create the database

sudo mysql --password

mysql> create database db_example; -- Creates the new database
mysql> create user 'springuser'@'%' identified by 'ThePassword';
mysql> grant all on db_example.* to 'springuser'@'%';

3. Build app

./mvnw clean package

4. RUN SPRING APP
 
java -jar target/entex-0.0.1-SNAPSHOT.jar test-java-fullstack-county.csv test-java-fullstack-locality.csv 

The jar file receives 2 arguments: first the county csv file, then the locality csv file.

5. Populate the database

Make a rest call to populate the database.

curl -X GET http://localhost:8080/demo/populate_database

This may take a couple of minutes.

select count(*) from locality; should return 13733 entries
select count(*) from county; 42 entries

6. Login

Get token

curl -X POST http://localhost:8080/demo/login -d username=administrator -d password=password

If username is 'administrator' and password is 'password' a token will be generated for further requests.

7. Get all users

curl -X GET http://localhost:8080/demo/user -H "token: 25213761-0a10-4e2d-bb48-b0818d835df5" 

[{"name":"Cornel","email":"cornel@gmail.com","password":"password","countyName":"Bucuresti","localityName":"Bucuresti","userId":18},{"name":"Ion","email":"ion@gmail.com","password":"password","countyName":"Suceava","localityName":"Suceava","userId":21},{"name":"Ana","email":"ana@gmail.com","password":"password","countyName":"Suceava","localityName":"Suceava","userId":22},{"name":"Cornel","email":"cornelao2@gmail.com","password":"password2","countyName":"Bucuresti","localityName":"Bucuresti","userId":25},{"name":"Cornel","email":"cornelao4@gmail.com","password":"password2","countyName":"Suceava","localityName":"Suceava","userId":27},{"name":"Maria","email":"marian91@gmail.com","password":"parola","countyName":"Dolj","localityName":"Craiova","userId":28}]

8. Get all users with a specific name

curl -X GET http://localhost:8080/demo/user?name=Maria -H "token: 25213761-0a10-4e2d-bb48-b0818d835df5"

[{"name":"Maria","email":"marian91@gmail.com","password":"parola","countyName":"Dolj","localityName":"Craiova","userId":28}]

9. Create user 

curl -X POST http://localhost:8080/demo/user -H "token: 25213761-0a10-4e2d-bb48-b0818d835df5" -d name=Marius -d email=costin@gmail.com -d password=password -d locality_name=Craiova

{"name":"Marius","email":"costin@gmail.com","password":"password","countyName":"Dolj","localityName":"Craiova","userId":31}

10. Edit user (for example email)

curl -X PUT http://localhost:8080/demo/user -H "token: 25213761-0a10-4e2d-bb48-b0818d835df5" -d id=31 -d email=mariussmecherul@gmail.com

{"name":"Marius","email":"mariussmecherul@gmail.com","password":"password","countyName":"Dolj","localityName":"Craiova","userId":31}

11. Delete User

curl -X DELETE http://localhost:8080/demo/user -H "token: 25213761-0a10-4e2d-bb48-b0818d835df5" -d id=25

Deletion was succesfull.

12. Open Browser to see the user table

http://localhost:8080/demo/user/details



