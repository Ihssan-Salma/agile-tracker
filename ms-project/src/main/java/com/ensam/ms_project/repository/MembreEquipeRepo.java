package com.ensam.ms_project.repository;

import com.ensam.ms_project.model.MembreEquipe;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository
public interface MembreEquipeRepo extends JpaRepository<MembreEquipe,Integer> {
    boolean existsByUtilisateurIdAndEquipe_EquipeId(int utilisateurId, int equipeId);

    Optional<MembreEquipe> findByUtilisateurIdAndEquipe_EquipeId(int utilisateurId, int equipeId);

}
