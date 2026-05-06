package com.ensam.mssprint.controller;

import com.ensam.mssprint.entity.Sprint;
import com.ensam.mssprint.service.SprintService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/sprints")
@RequiredArgsConstructor
public class SprintController {

    private final SprintService sprintService;

    // ===============================
    // PRODUCT OWNER
    // ===============================

    @PostMapping
    public ResponseEntity<Sprint> createSprint(@RequestBody Sprint sprint) {
        return ResponseEntity.ok(sprintService.createSprint(sprint));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Sprint> updateSprint(@PathVariable Integer id,
                                               @RequestBody Sprint sprint) {
        return ResponseEntity.ok(sprintService.updateSprint(id, sprint));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSprint(@PathVariable Integer id) {
        sprintService.deleteSprint(id);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}/story-points")
    public ResponseEntity<Sprint> adjustStoryPoints(@PathVariable Integer id,
                                                    @RequestParam Integer storyPoints) {
        return ResponseEntity.ok(sprintService.adjustStoryPoints(id, storyPoints));
    }
//    @PatchMapping("/{id}/demarrer")
//    public ResponseEntity<Sprint> demarrerSprint(
//            @PathVariable Integer id,
//            @RequestHeader("Role") String role) {
//
//        if (!role.equals("PRODUCT_OWNER")) {
//            throw new RuntimeException("Accès refusé : seul PRODUCT OWNER peut démarrer un sprint");
//        }
//
//        return ResponseEntity.ok(sprintService.demarrerSprint(id));
//    }
    @PatchMapping("/{id}/demarrer")
    public ResponseEntity<Sprint> demarrerSprint(@PathVariable Integer id) {
        return ResponseEntity.ok(sprintService.demarrerSprint(id));
    }

    @PatchMapping("/{id}/cloturer")
    public ResponseEntity<Sprint> cloturerSprint(@PathVariable Integer id) {
        return ResponseEntity.ok(sprintService.cloturerSprint(id));
    }

    @PatchMapping("/{id}/dates-reelles")
    public ResponseEntity<Sprint> updateDates(@PathVariable Integer id,
                                              @RequestBody Sprint data) {
        return ResponseEntity.ok(sprintService.updateDatesReelles(id, data));
    }

    // ===============================
    // SCRUM MASTER (READ)
    // ===============================

    @GetMapping("/projet/{projetId}/actifs")
    public ResponseEntity<List<Sprint>> getActifs(@PathVariable Integer projetId) {
        return ResponseEntity.ok(sprintService.getSprintsActifs(projetId));
    }

    @GetMapping("/projet/{projetId}/termines")
    public ResponseEntity<List<Sprint>> getTermines(@PathVariable Integer projetId) {
        return ResponseEntity.ok(sprintService.getSprintsTermines(projetId));
    }

    @GetMapping("/projet/{projetId}/retard")
    public ResponseEntity<List<Sprint>> getRetard(@PathVariable Integer projetId) {
        return ResponseEntity.ok(sprintService.getSprintsEnRetard(projetId));
    }

    @GetMapping("/{id}/progression")
    public ResponseEntity<Double> getProgression(@PathVariable Integer id) {
        return ResponseEntity.ok(sprintService.getProgression(id));
    }

    // ===============================
    // COMMUN
    // ===============================

    @GetMapping("/{id}")
    public ResponseEntity<Sprint> getById(@PathVariable Integer id) {
        return ResponseEntity.ok(sprintService.getSprintById(id));
    }

    @GetMapping("/projet/{projetId}")
    public ResponseEntity<List<Sprint>> getByProjet(@PathVariable Integer projetId) {
        return ResponseEntity.ok(sprintService.getSprintsByProjet(projetId));
    }
}