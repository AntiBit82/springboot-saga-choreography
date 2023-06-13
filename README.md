# springboot-saga-choreography
Microservices SAGA-choreography pattern example (order <-> payment) using Spring Boot Reactive Cloud Streaming, Kafka bindings and H2 databases.

# Prerequisites # 
Apache Kafka installation

# Create order #
POST to localhost:8081/order/create

body raw/JSON
```
{
    "userId": 1,
    "productId": 3,
    "amount": 700
}
```


# View all orders #
GET localhost:8081/order
