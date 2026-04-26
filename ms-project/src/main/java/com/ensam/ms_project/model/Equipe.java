package com.ensam.ms_project.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Equipe {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer equipe_id;
    private String nom;
    @OneToMany(mappedBy = "equipe")
    List<MembreEquipe> membreEquipe;
}
