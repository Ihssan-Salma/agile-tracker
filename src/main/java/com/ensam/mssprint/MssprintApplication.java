package com.ensam.mssprint;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

// Point d'entrée principal du microservice
@SpringBootApplication

// Active l'enregistrement automatique auprès du serveur Eureka
@EnableDiscoveryClient

// Active les clients Feign pour appeler les autres microservices
@EnableFeignClients
public class MssprintApplication {

    public static void main(String[] args) {
        SpringApplication.run(MssprintApplication.class, args);
    }
}