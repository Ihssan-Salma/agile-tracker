package com.ensam.mssprint.service;

import com.ensam.mssprint.client.ProjectClient;
import com.ensam.mssprint.entity.Sprint;
import com.ensam.mssprint.repository.SprintRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class SprintServiceTest {

    private SprintRepository sprintRepository = mock(SprintRepository.class);
    private ProjectClient projectClient = mock(ProjectClient.class);
    private SprintService sprintService;

    @BeforeEach
    void setUp() {
        sprintService = new SprintService(sprintRepository, projectClient, false);
    }

    @Test
    void createSprint_devrait_sauvegarder() {
        Sprint sprint = new Sprint();
        sprint.setNom("Sprint 1");
        when(sprintRepository.save(any())).thenReturn(sprint);

        Sprint result = sprintService.createSprint(sprint);

        assertThat(result.getNom()).isEqualTo("Sprint 1");
        assertThat(result.getTermine()).isFalse();
    }

    @Test
    void demarrerSprint_devrait_mettre_date_debut() {
        Sprint sprint = new Sprint();
        sprint.setId(1);
        when(sprintRepository.findById(1)).thenReturn(Optional.of(sprint));
        when(sprintRepository.save(any())).thenAnswer(inv -> inv.getArgument(0));

        Sprint result = sprintService.demarrerSprint(1);

        assertThat(result.getDateDebutReelle()).isEqualTo(LocalDate.now());
    }

    @Test
    void demarrerSprint_deuxieme_fois_devrait_echouer() {
        Sprint sprint = new Sprint();
        sprint.setId(1);
        sprint.setDateDebutReelle(LocalDate.now());
        when(sprintRepository.findById(1)).thenReturn(Optional.of(sprint));

        assertThatThrownBy(() -> sprintService.demarrerSprint(1))
                .isInstanceOf(RuntimeException.class)
                .hasMessageContaining("déjà démarré");
    }

    @Test
    void cloturerSprint_devrait_mettre_termine_true() {
        Sprint sprint = new Sprint();
        sprint.setId(1);
        sprint.setDateDebutReelle(LocalDate.now().minusDays(5));
        sprint.setTermine(false);
        when(sprintRepository.findById(1)).thenReturn(Optional.of(sprint));
        when(sprintRepository.save(any())).thenAnswer(inv -> inv.getArgument(0));

        Sprint result = sprintService.cloturerSprint(1);

        assertThat(result.getTermine()).isTrue();
    }

    @Test
    void cloturerSprint_non_demarre_devrait_echouer() {
        Sprint sprint = new Sprint();
        sprint.setId(1);
        when(sprintRepository.findById(1)).thenReturn(Optional.of(sprint));

        assertThatThrownBy(() -> sprintService.cloturerSprint(1))
                .isInstanceOf(RuntimeException.class)
                .hasMessageContaining("non démarré");
    }

    @Test
    void deleteSprint_demarre_devrait_echouer() {
        Sprint sprint = new Sprint();
        sprint.setId(1);
        sprint.setDateDebutReelle(LocalDate.now());
        when(sprintRepository.findById(1)).thenReturn(Optional.of(sprint));

        assertThatThrownBy(() -> sprintService.deleteSprint(1))
                .isInstanceOf(RuntimeException.class)
                .hasMessageContaining("déjà démarré");
    }

    @Test
    void getSprintById_introuvable_devrait_echouer() {
        when(sprintRepository.findById(99)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> sprintService.getSprintById(99))
                .isInstanceOf(RuntimeException.class)
                .hasMessageContaining("Sprint non trouvé");
    }
}