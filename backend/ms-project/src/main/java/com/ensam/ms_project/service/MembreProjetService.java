package com.ensam.ms_project.service;

import com.ensam.ms_project.dto.MembreProjetDTO;
import com.ensam.ms_project.model.MembreProjet;
import com.ensam.ms_project.model.Projet;
import com.ensam.ms_project.model.Role;
import com.ensam.ms_project.repository.MembreProjetRepo;
import com.ensam.ms_project.repository.ProjetRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class MembreProjetService {
    private final MembreProjetRepo membreProjetRepo;
    private final ProjetRepository projetRepository;

    public MembreProjetService(MembreProjetRepo membreProjetRepo,
                               ProjetRepository projetRepository) {
        this.membreProjetRepo = membreProjetRepo;
        this.projetRepository = projetRepository;
    }


    public MembreProjetDTO inviterMembre(int user_id, int projet_id, Role rolemem, Role myrole) {

        if (myrole != Role.PRODUCT_OWNER) {
            throw new RuntimeException("Only Product Owner can manage project members");
        }

        Projet projet = projetRepository.findById(projet_id)
                .orElseThrow(() -> new RuntimeException("Projet not found"));

        MembreProjet membreProjet = new MembreProjet();
        membreProjet.setUtilisateurId(user_id);
        membreProjet.setRole(rolemem);
        membreProjet.setProjet(projet);

        MembreProjet saved = membreProjetRepo.save(membreProjet);

        MembreProjetDTO dto = new MembreProjetDTO();
        dto.setId(saved.getMemprojetId());
        dto.setUserId(saved.getUtilisateurId());
        dto.setProjetId(saved.getProjet().getProjetId());
        dto.setRole(saved.getRole());

        return dto;
    }

    public String retirerMembre(int membre_id,int projet_id,Role myrole){
        if (myrole != Role.PRODUCT_OWNER) {
            throw new RuntimeException("Only Product Owner can edit project's Date");
        }
        MembreProjet membreProjet = membreProjetRepo.findById(membre_id).orElseThrow(()->new RuntimeException("Membre not found"));
        if (membreProjet.getProjet() == null ||
                !membreProjet.getProjet().getProjetId().equals(projet_id)) {
            throw new RuntimeException("Member does not belong to this project");
        }

        membreProjetRepo.delete(membreProjet);
        return "Membre supprimé";
    }

    public List<MembreProjet> getMembreProjet(int projet_id){
        return membreProjetRepo.findByProjet_ProjetId(projet_id);
    }
}
