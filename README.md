<h1>Creating microservice using Quarkus, Consul,Spring cloud gateway with Keycloak And Minio in Docker Containers</h1>


Quarkus the "Supersonic Subatomic Java" provides set of libraries for creating micro services in Java.

<ul>
<li>Consul - service registration and discovery</li>
<li>Spring cloud gateway - gateway service that provides dynamic routing, monitoring, resiliency, security, and more</li>
<li>Keycloak - Keycloak is an open-source Identity and Access Management solution aimed at modern applications
and services. It makes it easy to secure applications and services with little to no code.</li>
<li> Minio - Minio is an object storage server
</ul>


<h2>Components used in this project </h2>


<ul>
<li>gateway - this service that provides dynamic routing, monitoring, resiliency, security, and more. 
In this keycloak is implemented with Open id connect to generate JWT Token, it acts as a central authentication mechanism for microservices</li>

<li>pfe-dashboard: - its a microservice, where Hystrix circuit breaker is implemented </li>
<li>pfe-objective: - its a microservice, </li>
<li>pfe-workflow: - its a microservice, where Hystrix circuit breaker is implemented </li>
<li>docker: - containes the docker-compose.yml file and some configurations </li>
<li>consul: -  is used for service discovery and registration </li>
</ul>

<h2>Running this project </h2>
<b>just go to docker directory and run the command <h3>docker-compose up</h3></b>


<h1>Project Architecture</h1>

![alt text](https://github.com/malek0903/pfe-final-work/blob/fae51c466ed01b37d1e5cb2f41dc169226caf70d/Architecture%20physique.png)
