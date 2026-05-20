package com.ensam.ms_project;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import com.ensam.ms_project.model.*;
import com.ensam.ms_project.repository.*;
import com.ensam.ms_project.service.EquipeService;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.List;
@ExtendWith(MockitoExtension.class)
public class EquipeServiceTest {
    @Mock
    private EquipeRepository equipeRepository;

    @Mock
    private ProjetRepository projetRepository;

    @Mock
    private MembreEquipeRepo membreEquipeRepo;

    @Mock
    private MembreProjetRepo membreProjetRepo;

    @InjectMocks
    private EquipeService service;
    @Test
    void testCreateEquipe_success() {

        Projet projet = new Projet();
        projet.setProjetId(1);

        when(projetRepository.findById(1))
                .thenReturn(Optional.of(projet));

        Equipe saved = new Equipe();
        saved.setNom("Team A");
        saved.setProjet(projet);

        when(equipeRepository.save(any()))
                .thenReturn(saved);

        Equipe result = service.createEquipe(1, "Team A", Role.PRODUCT_OWNER);

        assertEquals("Team A", result.getNom());

        verify(projetRepository).findById(1);
        verify(equipeRepository).save(any());
    }
    @Test
    void testCreateEquipe_fail_role() {

        assertThrows(RuntimeException.class, () -> {
            service.createEquipe(1, "Team A", Role.DEVELOPPEUR);
        });

        verify(equipeRepository, never()).save(any());
    }
    @Test
    void testAddMemberEquipe_success() {

        Projet projet = new Projet();
        projet.setProjetId(1);

        Equipe equipe = new Equipe();
        equipe.setEquipeId(10);
        equipe.setProjet(projet);

        when(equipeRepository.findById(10))
                .thenReturn(Optional.of(equipe));

        when(membreProjetRepo.existsByUtilisateurIdAndProjet_ProjetId(5, 1))
                .thenReturn(true);

        when(membreEquipeRepo.existsByUtilisateurIdAndEquipe_EquipeId(5, 10))
                .thenReturn(false);

        String result = service.addMemberEquipe(5, 10, Role.PRODUCT_OWNER);

        assertEquals("Membre ajouté", result);

        verify(membreEquipeRepo).save(any());
    }
    @Test
    void testAddMemberEquipe_userNotInProject() {

        Projet projet = new Projet();
        projet.setProjetId(1);

        Equipe equipe = new Equipe();
        equipe.setProjet(projet);

        when(equipeRepository.findById(10))
                .thenReturn(Optional.of(equipe));

        when(membreProjetRepo.existsByUtilisateurIdAndProjet_ProjetId(5, 1))
                .thenReturn(false);

        assertThrows(RuntimeException.class, () -> {
            service.addMemberEquipe(5, 10, Role.PRODUCT_OWNER);
        });
    }
    @Test
    void testAddMemberEquipe_alreadyExists() {

        Projet projet = new Projet();
        projet.setProjetId(1);

        Equipe equipe = new Equipe();
        equipe.setProjet(projet);

        when(equipeRepository.findById(10))
                .thenReturn(Optional.of(equipe));

        when(membreProjetRepo.existsByUtilisateurIdAndProjet_ProjetId(5, 1))
                .thenReturn(true);

        when(membreEquipeRepo.existsByUtilisateurIdAndEquipe_EquipeId(5, 10))
                .thenReturn(true);

        assertThrows(RuntimeException.class, () -> {
            service.addMemberEquipe(5, 10, Role.PRODUCT_OWNER);
        });
    }
    @Test
    void testRetirerMember_success() {

        Equipe equipe = new Equipe();

        MembreEquipe membre = new MembreEquipe();

        when(equipeRepository.findById(10))
                .thenReturn(Optional.of(equipe));

        when(membreEquipeRepo
                .findByUtilisateurIdAndEquipe_EquipeId(5, 10))
                .thenReturn(Optional.of(membre));

        String result = service.retirerMember(5, 10, Role.PRODUCT_OWNER);

        assertEquals("Member deleted successfully", result);

        verify(membreEquipeRepo).delete(membre);
    }
    @Test
    void testGetEquipe() {

        when(equipeRepository.findAll())
                .thenReturn(List.of(new Equipe()));

        List<Equipe> result = service.getEquipe();

        assertFalse(result.isEmpty());

        verify(equipeRepository).findAll();
    }
}
