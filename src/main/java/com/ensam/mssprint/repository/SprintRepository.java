package com.ensam.mssprint.repository;

import com.ensam.mssprint.entity.Sprint;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;



// JpaRepository fournit automatiquement les opérations CRUD de base
// (save, findById, findAll, deleteById, etc.)
public interface SprintRepository extends JpaRepository<Sprint, Integer> {

    // Récupère tous les sprints appartenant à un projet donné
    // Spring génère automatiquement la requête SQL depuis le nom de la méthode
    List<Sprint> findByProjetId(Integer projetId);

    // Récupère les sprints d'un projet filtrés par leur statut
    // termine = false → sprints actifs
    // termine = true  → sprints terminés
    List<Sprint> findByProjetIdAndTermine(Integer projetId, Boolean termine);

    // utile pour analyse (retard)
    List<Sprint> findByDateFinReelleAfter(LocalDate date);
}

