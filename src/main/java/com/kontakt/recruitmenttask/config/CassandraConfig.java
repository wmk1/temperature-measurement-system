package com.kontakt.recruitmenttask.config;

import com.datastax.oss.driver.api.core.CqlSession;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.net.InetSocketAddress;

@Configuration
public class CassandraConfig {

    @Value("${spring.cassandra.keyspace-name}")
    private String keyspace;

    @Value("${spring.cassandra.contact-points}")
    private String contactPoints;

    @Value("${spring.cassandra.local-datacenter}")
    private String localDataCenter;

    @Bean
    public CqlSession cqlSession() {
        return CqlSession.builder()
                .withKeyspace(keyspace)
                .addContactPoint(new InetSocketAddress(contactPoints, 9042))
                .withLocalDatacenter(localDataCenter)
                .build();
    }
}