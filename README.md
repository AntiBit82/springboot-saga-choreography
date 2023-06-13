# springboot-saga-choreography

Prerequisites: Apache Kafka installation

Microservices SAGA-choreography pattern example (order <-> payment) using Spring Boot Reactive Cloud Streaming, Kafka bindings and H2 databases.

Create order via POST to localhost:8081/order/create

body raw/JSON
```
{
    "userId": 1,
    "productId": 3,
    "amount": 700
}
```
