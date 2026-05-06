package com.agile_tracker.user_access_ms.repositories;

import com.agile_tracker.user_access_ms.models.Utilisateur;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UtilisateurRepository extends JpaRepository<Utilisateur, Integer> {

    Optional<Utilisateur> findByUsername(String username);

    Optional<Utilisateur> findByEmail(String email);

    boolean existsByEmail(String email);

    boolean existsByUsername(String username);
}
