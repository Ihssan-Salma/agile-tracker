package com.ensam.ms_project.repository;

import com.ensam.ms_project.model.MembreProjet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MembreProjetRepo extends JpaRepository<MembreProjet,Integer> {
    List<MembreProjet> findByProjet_ProjetId(int projetId);

    boolean existsByUtilisateurIdAndProjet_ProjetId(int utilisateurId, int projetId);
}
