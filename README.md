# Anomaly Detection Service
This service is designed to detect anomalies in a stream of temperature measurements. The service uses Apache Kafka for generating data and Cassandra for data storage.

I decided to use Apache Cassandra to store data as I have never used it neither in production environment, only during part-time studies. I believe a good practice is to practice some new technologies, specially when applying for a new position.

## How it works
The service listens to a Kafka topic for incoming temperature measurements.

As each measurement arrives, it's passed to an AnomalyDetector instance for analysis.
An AnomalyDetector is an interface with a single method, `isAnomaly(TemperatureMeasurement temperatureMeasurement)`. 

This method returns a boolean indicating whether the given measurement is considered an anomaly.

There are two implementations of AnomalyDetector: BufferBasedAnomalyDetector and TimeBasedAnomalyDetector. Each uses a different strategy to determine whether a measurement is an anomaly.
The type of AnomalyDetector to use is determined by a property in the application's configuration file (anomaly.detector.type). The valid values are bufferBased and timeBased.

If a measurement is found to be an anomaly, it's saved to a Cassandra database and sent to an AnomalyStreamController for further processing.
## Configuration
The service can be configured by editing the application.properties file. Here are the available settings:

`anomaly.detector.type`: Determines which type of AnomalyDetector to use. Valid values are bufferBased and timeBased.

`kafka.topic`: The Kafka topic to listen to for incoming temperature measurements.

`kafka.group-id`: The Kafka consumer group ID.

`spring.cassandra.contact-points`: The contact points to connect to the Cassandra cluster.

`spring.cassandra.keyspace-name`: The keyspace to use in the Cassandra cluster.

`spring.cassandra.local-datacenter`: The local datacenter that the driver connects to.

I wanted this project to be able to run in any environment. For this there is also a `docker-compose.yml` file.

This is description of their configuration:

* cassandra: This service is based on the cassandra:3.11 image (using latest version caused some issue on configuration of data source). It exposes port 9042 for connecting to the Cassandra database. It also mounts a local script init-db.cql into the container and runs it to initialize the database after Cassandra starts.
* zookeeper: This service is based on the confluentinc/cp-zookeeper:latest image. It is used by the Kafka service for maintaining configuration information, providing distributed synchronization, and providing group services.
* kafka: This service is based on the confluentinc/cp-kafka:latest image. It depends on the zookeeper service and exposes port 9092 for connecting to the Kafka broker.


To start all services, you can run docker-compose up in the directory containing the docker-compose.yml file. This will create the Cassandra and Kafka services, and initialize the Cassandra database using the init-db.cql script.



## How to run it:

1. Start docker containers using command `docker-compose up`
2. Verify that all of the docker containers started by typing `docker ps`. At this point you should see three services, zookeeper, cassandra and kafka itself.


## How does it work

After starting project it should generate 20000 measurements. Basing on that fact it should also store this data into database store.
For this project there are two endpoints for fetching information about measurements, by its roomId and temperature. Additionaly, there is also an endpoint 

## Possible improvements

