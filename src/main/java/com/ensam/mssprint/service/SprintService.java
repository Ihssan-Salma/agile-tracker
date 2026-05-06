package com.ensam.mssprint.service;

import com.ensam.mssprint.client.ProjectClient;
import com.ensam.mssprint.entity.Sprint;
import com.ensam.mssprint.repository.SprintRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class SprintService {

    private final SprintRepository sprintRepository;
    private final ProjectClient projectClient;
    private final boolean projectValidationEnabled;

    public SprintService(SprintRepository sprintRepository,
                         ProjectClient projectClient,
                         @Value("${app.project-validation.enabled:true}") boolean projectValidationEnabled) {
        this.sprintRepository = sprintRepository;
        this.projectClient = projectClient;
        this.projectValidationEnabled = projectValidationEnabled;
    }

    // ===============================
    // PRODUCT OWNER (WRITE)
    // ===============================

    public Sprint createSprint(Sprint sprint) {

        if (projectValidationEnabled && sprint.getProjetId() != null) {
            projectClient.getProjetById(sprint.getProjetId()); // exception si KO
        }

        sprint.setTermine(false);
        return sprintRepository.save(sprint);
    }

    public Sprint updateSprint(Integer id, Sprint updated) {
        Sprint sprint = getSprintById(id);

        sprint.setNom(updated.getNom());
        sprint.setDateDebutPlanifiee(updated.getDateDebutPlanifiee());
        sprint.setDateFinPlanifiee(updated.getDateFinPlanifiee());
        sprint.setObjectif(updated.getObjectif());
        sprint.setStoryPoints(updated.getStoryPoints());

        return sprintRepository.save(sprint);
    }

    public void deleteSprint(Integer id) {
        Sprint sprint = getSprintById(id);

        if (sprint.getDateDebutReelle() != null) {
            throw new RuntimeException("Sprint déjà démarré, suppression interdite");
        }

        sprintRepository.deleteById(id);
    }

    public Sprint adjustStoryPoints(Integer id, Integer storyPoints) {
        Sprint sprint = getSprintById(id);
        sprint.setStoryPoints(storyPoints);
        return sprintRepository.save(sprint);
    }

    public Sprint demarrerSprint(Integer id) {
        Sprint sprint = getSprintById(id);

        if (sprint.getDateDebutReelle() != null) {
            throw new RuntimeException("Sprint déjà démarré");
        }

        sprint.setDateDebutReelle(LocalDate.now());
        sprint.setTermine(false);

        return sprintRepository.save(sprint);
    }

    public Sprint cloturerSprint(Integer id) {
        Sprint sprint = getSprintById(id);

        if (sprint.getDateDebutReelle() == null) {
            throw new RuntimeException("Sprint non démarré");
        }

        if (Boolean.TRUE.equals(sprint.getTermine())) {
            throw new RuntimeException("Sprint déjà clôturé");
        }

        sprint.setDateFinReelle(LocalDate.now());
        sprint.setTermine(true);

        return sprintRepository.save(sprint);
    }

    public Sprint updateDatesReelles(Integer id, Sprint data) {
        Sprint sprint = getSprintById(id);

        sprint.setDateDebutReelle(data.getDateDebutReelle());
        sprint.setDateFinReelle(data.getDateFinReelle());

        return sprintRepository.save(sprint);
    }

    // ===============================
    // SCRUM MASTER (READ ONLY)
    // ===============================

    public List<Sprint> getSprintsActifs(Integer projetId) {
        return sprintRepository.findByProjetIdAndTermine(projetId, false);
    }

    public List<Sprint> getSprintsTermines(Integer projetId) {
        return sprintRepository.findByProjetIdAndTermine(projetId, true);
    }

    public List<Sprint> getSprintsEnRetard(Integer projetId) {
        return sprintRepository.findByProjetId(projetId)
                .stream()
                .filter(s ->
                        s.getDateFinReelle() != null &&
                                s.getDateFinPlanifiee() != null &&
                                s.getDateFinReelle().isAfter(s.getDateFinPlanifiee()))
                .toList();
    }

    public double getProgression(Integer sprintId) {
        // simulation (à connecter avec ms-taches plus tard)
        return 0.0;
    }

    // ===============================
    // COMMUN
    // ===============================

    public Sprint getSprintById(Integer id) {
        return sprintRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Sprint non trouvé : " + id));
    }

    public List<Sprint> getSprintsByProjet(Integer projetId) {
        return sprintRepository.findByProjetId(projetId);
    }
}