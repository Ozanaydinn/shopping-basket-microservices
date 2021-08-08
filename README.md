# shopping-basket-microservices
A repository containing multiple microservices simulating a shopping environment, microservices are created using CQRS and event sourcing.

# Microservices #

This repository contains five microservices that communicate with eachother. Apart from the _api gateway_, which has no business logic inside it and only routes requests, all other microservices are produced using **CQRS** and **Event Sourcing** to help with ACID principles, scalability and persistence. The microservices communicate with each other asyncronously using RabbitMQ queues.  
