package com.ensam.ms_project.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
    private Integer equipeId;
    private String nom;
    @OneToMany(mappedBy = "equipe")
    List<MembreEquipe> membreEquipe;

    @ManyToOne
    @JoinColumn(name="projet_id")
    @JsonIgnore
    private Projet projet;
}
