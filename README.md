# springboot-saga-choreography
Microservices SAGA-choreography pattern example (order <-> payment) using Spring Boot Reactive Cloud Streaming, Kafka bindings and H2 databases.

# Prerequisites # 
Apache Kafka installation TEST

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

User 1 has a balance of 1000.-, so order 1 will be paid successfully and order will be completed.

2nd order will fail as there is not sufficient balance left.


# View all orders #
GET localhost:8081/order
