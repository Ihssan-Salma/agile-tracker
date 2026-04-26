package com.ensam.ms_project.service;

import com.ensam.ms_project.dto.ProjetRequestDTO;
import com.ensam.ms_project.dto.ProjetResponseDTO;
import com.ensam.ms_project.dto.ProjetUpdateDTO;
import com.ensam.ms_project.model.Projet;
import com.ensam.ms_project.model.Role;
import com.ensam.ms_project.repository.ProjetRepository;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProjetService {
    private final ProjetRepository projetRepository;

    public ProjetService(ProjetRepository projetRepository) {
        this.projetRepository = projetRepository;
    }
    public ProjetResponseDTO createProjet(ProjetRequestDTO projetDTO, Role role){
        if (role != Role.PRODUCT_OWNER) {
            throw new RuntimeException("Only Product Owner can create project");
        }
        Projet projet = new Projet();
        projet.setNom(projetDTO.getNom());
        projet.setDateDebutPlanifiee(projetDTO.getDateDebutPlanifiee());
        projet.setDateFinPlanifiee(projetDTO.getDateFinPlanifiee());
        projet.setCapaciteSprint(projetDTO.getCapaciteSprint());
        Projet saved = projetRepository.save(projet);
        ProjetResponseDTO response = new ProjetResponseDTO();
        response.setProjetId(saved.getProjetId());
        response.setNom(saved.getNom());
        response.setDateDebutPlanifiee(saved.getDateDebutPlanifiee());
        response.setDateFinPlanifiee(saved.getDateFinPlanifiee());
        response.setCapaciteSprint(saved.getCapaciteSprint());
        return response;
    }

    public ProjetResponseDTO updateProjet(ProjetUpdateDTO projetUpdateDTO,
                                          int projet_id,
                                          Role role) {

        if (role != Role.PRODUCT_OWNER) {
            throw new RuntimeException("Only Product Owner can edit project's dates");
        }

        Projet projet = projetRepository.findById(projet_id)
                .orElseThrow(() -> new RuntimeException("Projet not found"));

        if (projetUpdateDTO.getDateDebutReelle() != null) {
            projet.setDateDebutReelle(projetUpdateDTO.getDateDebutReelle());
        }

        if (projetUpdateDTO.getDateFinReelle() != null) {
            projet.setDateFinReelle(projetUpdateDTO.getDateFinReelle());
        }

        if (projetUpdateDTO.getDateDebutPlanifiee() != null) {
            projet.setDateDebutPlanifiee(projetUpdateDTO.getDateDebutPlanifiee());
        }

        if (projetUpdateDTO.getDateFinPlanifiee() != null) {
            projet.setDateFinPlanifiee(projetUpdateDTO.getDateFinPlanifiee());
        }

        Projet saved = projetRepository.save(projet);

        return mapToDTO(saved);
    }
    //mapping
    private ProjetResponseDTO mapToDTO(Projet projet) {
        ProjetResponseDTO dto = new ProjetResponseDTO();
        dto.setProjetId(projet.getProjetId());
        dto.setNom(projet.getNom());
        dto.setDateDebutPlanifiee(projet.getDateDebutPlanifiee());
        dto.setDateFinPlanifiee(projet.getDateFinPlanifiee());
        dto.setCapaciteSprint(projet.getCapaciteSprint());
        return dto;
    }
    public String deleteProjet(int projet_id,Role role){
        if (role != Role.PRODUCT_OWNER) {
            throw new RuntimeException("Only Product Owner can edit project's Date");
        }
        projetRepository.deleteById(projet_id);
        return "Projet Supprimé";
    }
    public List<Projet> getProjet(){
        return projetRepository.findAll();
    }

}
