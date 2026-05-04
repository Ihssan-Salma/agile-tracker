package com.ensam.ms_project;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import com.ensam.ms_project.model.*;
import com.ensam.ms_project.dto.*;
import com.ensam.ms_project.repository.*;
import com.ensam.ms_project.service.MembreProjetService;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class MembreProjetServiceTest {
    @Mock
    private MembreProjetRepo membreProjetRepo;

    @Mock
    private ProjetRepository projetRepository;

    @InjectMocks
    private MembreProjetService service;
    @Test
    void testInviterMembre_success() {


        Projet projet = new Projet();
        projet.setProjetId(1);

        when(projetRepository.findById(1))
                .thenReturn(Optional.of(projet));

        MembreProjet saved = new MembreProjet();
        saved.setMemprojetId(10);
        saved.setUtilisateurId(5);
        saved.setProjet(projet);
        saved.setRole(Role.DEVELOPPEUR);

        when(membreProjetRepo.save(any()))
                .thenReturn(saved);


        MembreProjetDTO dto = service.inviterMembre(
                5, 1, Role.DEVELOPPEUR, Role.PRODUCT_OWNER);

        // vérifications
        assertEquals(5, dto.getUserId());
        assertEquals(1, dto.getProjetId());

        verify(projetRepository).findById(1);
        verify(membreProjetRepo).save(any());
    }
    @Test
    void testInviterMembre_fail_role() {

        assertThrows(RuntimeException.class, () -> {
            service.inviterMembre(5, 1, Role.DEVELOPPEUR, Role.DEVELOPPEUR);
        });

        verify(membreProjetRepo, never()).save(any());
    }
    @Test
    void testInviterMembre_projetNotFound() {

        when(projetRepository.findById(1))
                .thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> {
            service.inviterMembre(5, 1, Role.DEVELOPPEUR, Role.PRODUCT_OWNER);
        });
    }
    @Test
    void testRetirerMembre_success() {

        Projet projet = new Projet();
        projet.setProjetId(1);

        MembreProjet membre = new MembreProjet();
        membre.setMemprojetId(10);
        membre.setProjet(projet);

        when(membreProjetRepo.findById(10))
                .thenReturn(Optional.of(membre));

        String result = service.retirerMembre(10, 1, Role.PRODUCT_OWNER);

        assertEquals("Membre supprimé", result);

        verify(membreProjetRepo).delete(membre);
    }
    @Test
    void testRetirerMembre_wrongProject() {

        Projet projet = new Projet();
        projet.setProjetId(2);

        MembreProjet membre = new MembreProjet();
        membre.setProjet(projet);

        when(membreProjetRepo.findById(10))
                .thenReturn(Optional.of(membre));
        //when we try to search the member with 10 we return member which is related to project 2 not 1 (wrong project error)
        assertThrows(RuntimeException.class, () -> {
            service.retirerMembre(10, 1, Role.PRODUCT_OWNER);
        });
    }
    @Test
    void testGetMembreProjet() {
        when(membreProjetRepo.findByProjet_ProjetId(1))
                .thenReturn(new ArrayList<>());

        assertNotNull(service.getMembreProjet(1));

        verify(membreProjetRepo).findByProjet_ProjetId(1);
    }
}
