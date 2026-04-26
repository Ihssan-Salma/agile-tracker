package com.ensam.ms_project.model;

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
    private Integer memequipe_id;

    @ManyToOne
    @JoinColumn(name = "equipe_id")
    Equipe equipe;

    private int utilisateur_id;

}
