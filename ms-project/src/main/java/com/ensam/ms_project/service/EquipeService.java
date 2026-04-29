package com.ensam.ms_project.service;

import com.ensam.ms_project.model.*;
import com.ensam.ms_project.repository.EquipeRepository;
import com.ensam.ms_project.repository.MembreEquipeRepo;
import com.ensam.ms_project.repository.MembreProjetRepo;
import com.ensam.ms_project.repository.ProjetRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EquipeService {
    private EquipeRepository equipeRepository;
    private ProjetRepository projetRepository;
    private MembreEquipeRepo membreEquipeRepo;
    private MembreProjetRepo membreProjetRepo;

    public EquipeService(EquipeRepository equipeRepository,
                         ProjetRepository projetRepository,
                         MembreEquipeRepo membreEquipeRepo,
                         MembreProjetRepo membreProjetRepo) {
        this.equipeRepository = equipeRepository;
        this.projetRepository=projetRepository;
        this.membreEquipeRepo = membreEquipeRepo;
        this.membreProjetRepo = membreProjetRepo;
    }

    public Equipe createEquipe(int projet_id,String nomEquipe,Role myRole){
        if (myRole != Role.PRODUCT_OWNER) {
            throw new RuntimeException("Only Product Owner can manage project members");
        }
        Projet projet = projetRepository.findById(projet_id).orElseThrow(()->new RuntimeException("Project not found"));
        Equipe equipe = new Equipe();
        equipe.setNom(nomEquipe);
        equipe.setProjet(projet);
        return equipeRepository.save(equipe);
    }

    public String addMemberEquipe(int user_id,int equipe_id,Role myRole){
        if (myRole != Role.PRODUCT_OWNER) {
            throw new RuntimeException("Only Product Owner can manage project members");
        }
        Equipe equipe = equipeRepository.findById(equipe_id).orElseThrow(()->new RuntimeException("equipe not found"));
        int projet_id = equipe.getProjet().getProjetId();  //get current id of project
        boolean exists = membreProjetRepo.existsByUtilisateurIdAndProjet_ProjetId(user_id,projet_id);
        if(!exists){
            throw new RuntimeException("User is not in this project");
        }
        boolean existsEquipe = membreEquipeRepo.existsByUtilisateurIdAndEquipe_EquipeId(user_id,equipe_id);
        if(existsEquipe){
            throw new RuntimeException("Member already in this team");
        }
        MembreEquipe membreEquipe = new MembreEquipe();
        membreEquipe.setEquipe(equipe);
        membreEquipe.setUtilisateurId(user_id);
         membreEquipeRepo.save(membreEquipe);
         return "Membre ajouté";

    }

    public String retirerMember(int user_id, int equipe_id,Role myRole) {
        if (myRole != Role.PRODUCT_OWNER) {
            throw new RuntimeException("Only Product Owner can manage project members");
        }
        Equipe equipe = equipeRepository.findById(equipe_id)
                .orElseThrow(() -> new RuntimeException("equipe not found"));

        MembreEquipe membreEquipe = membreEquipeRepo
                .findByUtilisateurIdAndEquipe_EquipeId(user_id, equipe_id)
                .orElseThrow(() -> new RuntimeException("Member doesn't exist in this equipe"));

        membreEquipeRepo.delete(membreEquipe);

        return "Member deleted successfully";
    }

    public List<Equipe> getEquipe(){
        return equipeRepository.findAll();
    }
}
