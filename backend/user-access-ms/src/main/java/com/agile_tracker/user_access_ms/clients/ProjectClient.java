package com.agile_tracker.user_access_ms.clients;

/**
 * @author salma
 **/

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

// We use a placeholder URL for now since ms-project isn't running
@FeignClient(name = "ms-project", url = "${app.ms-project.url:http://localhost:8083}")
public interface ProjectClient {

    @PostMapping("/internal/projects/assign")
    void assignUserToProject(@RequestBody Object assignment);
    // You can replace 'Object' with a specific DTO once ms-project is ready
}
