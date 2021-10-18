# car-service

This service provides endpoints to create, retrieve and delete cars.
H2 database is used to store data.

Endpoints supported :
POST /cars
GET /cars
PUT /cars/{id}
GET /cars/{id}
DELETE /cars/{id}

The service additionally returns a set words that sound like the model name with every call to GET /cars or POST /cars

