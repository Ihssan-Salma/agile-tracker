package com.ensam.ms_project.repository;

import com.ensam.ms_project.model.Equipe;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EquipeRepository extends JpaRepository<Equipe,Integer> {
}
