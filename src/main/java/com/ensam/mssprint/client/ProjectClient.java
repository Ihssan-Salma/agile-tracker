package com.ensam.mssprint.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;



// Feign va automatiquement trouver ms-projets via Eureka
// grâce au name = "ms-projets" (nom exact dans son application.properties)
// Pas besoin de connaître son IP ou son port
@FeignClient(name = "ms-projets")
public interface ProjectClient {

    // Appelle GET http://ms-projets/api/projets/{id}
    // Permet de vérifier qu'un projet existe avant de créer un sprint
    @GetMapping("/api/projets/{id}")
    Object getProjetById(@PathVariable("id") Integer id);
}