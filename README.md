# Instalation guide xmen application


## Design

Java 8 como lenguaje de programacion y Spring como framework base de la aplicacion, ya que nos permite facilmente crear la capa de servicios rest y la ejecucion de los test de los 
requerimientos funcionales.

Se decidio utilizar Tomcat 7.0.85 como Web Application Server el cual esta instalado en dos instancias de ubuntu (AWS EC2) las cuales pertenecen a un auto-scaling group, lo cual nos
permitirar escalar la carga horizontalmente cuando alcance un umbral del 80% de la utilizacion de la instancia. La carga de las instancias estan balanceadas por un application load balancer
Como base de datos del sistema se eligio MongoDB Atlas por su feature Sharding que permite incrementar a medida que crecen los datos.
 
## Layers

- REST
- Business Service Layer
- Repository Layer


## Local Environment Installation
- Clone the repo: [https://github.com/josemasuarez/xmen.git]
- Execute Maven 
```
mvn clean install spring-boot:run jacoco:report
```

## Usage

POST - [http://localhost:8080/mutant]

Json to send example:
```
{
	"dna":[ "AAAAGA", "CCCCGC", "TTTTTT", "AAACGG","GCGTCA", "TCACTG" ]
}
```

GET - [http://localhost:8080/stats]

Json to receive example:
```
{
    "count_mutant_dna": 1,
    "count_human_dna": 2,
    "ratio": 0.5
}
```


## Access to the application using AWS Cloud 
POST - [http://meli-load-balancer-1858996997.us-west-1.elb.amazonaws.com/xmen/mutant]

GET - [http://meli-load-balancer-1858996997.us-west-1.elb.amazonaws.com/xmen/stats]


