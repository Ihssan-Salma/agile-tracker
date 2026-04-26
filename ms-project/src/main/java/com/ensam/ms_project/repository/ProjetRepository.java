package com.ensam.ms_project.repository;

import com.ensam.ms_project.model.Projet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProjetRepository extends JpaRepository<Projet,Integer> {
}
