package com.ensam.ms_project;

import com.ensam.ms_project.dto.ProjetRequestDTO;
import com.ensam.ms_project.dto.ProjetResponseDTO;
import com.ensam.ms_project.dto.ProjetUpdateDTO;
import com.ensam.ms_project.model.Projet;
import com.ensam.ms_project.model.Role;
import com.ensam.ms_project.repository.ProjetRepository;
import com.ensam.ms_project.service.ProjetService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ProjetServiceTest {
    @Mock
    private ProjetRepository projetRepository;

    @InjectMocks
    private ProjetService projetService;
    @Test
    void testCreateProjet_success() {
        ProjetRequestDTO dto = new ProjetRequestDTO();
        dto.setNom("Projet Test");

        Projet projetSaved = new Projet();
        projetSaved.setProjetId(1);
        projetSaved.setNom("Projet Test");

        //when projetrepo.save called return the savedprojet
        when(projetRepository.save(any(Projet.class)))
                .thenReturn(projetSaved);


        ProjetResponseDTO response =
                projetService.createProjet(dto, Role.PRODUCT_OWNER);

        //verification
        assertEquals("Projet Test", response.getNom());
        assertEquals(1, response.getProjetId());
        //verify if the projetrepo.save is called
        verify(projetRepository).save(any(Projet.class));
    }
    @Test
    void testCreateProjet_fail_role() {

        ProjetRequestDTO dto = new ProjetRequestDTO();
        //(wait until)verify that createprojet return error if role is developper
        assertThrows(RuntimeException.class, () -> {
            projetService.createProjet(dto, Role.DEVELOPPEUR);
        });
        //verify that save was never called
        verify(projetRepository, never()).save(any());
    }

    @Test
    void testUpdateProjet_success() {

        Projet projet = new Projet();
        projet.setProjetId(1);

        ProjetUpdateDTO dto = new ProjetUpdateDTO();

        when(projetRepository.findById(1))
                .thenReturn(Optional.of(projet));

        when(projetRepository.save(any()))
                .thenReturn(projet);

        ProjetResponseDTO response =
                projetService.updateProjet(dto, 1, Role.PRODUCT_OWNER);
        //verify that the response was not null(contains 1)
        assertNotNull(response);

        verify(projetRepository).findById(1);
        verify(projetRepository).save(projet);
    }
}
