package com.ensam.ms_project.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class MembreEquipe {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer membreEquipeId;

    @ManyToOne
    @JoinColumn(name = "equipe_id")
    @JsonIgnore
    Equipe equipe;

    private int utilisateurId;

}
