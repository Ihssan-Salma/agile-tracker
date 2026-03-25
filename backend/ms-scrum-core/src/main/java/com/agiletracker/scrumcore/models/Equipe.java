package com.agiletracker.scrumcore.models;

import jakarta.persistence.*;

/**
 * @author salma
 **/
@Entity
@Table(name = "equipes")
public class Equipe {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nom;
}
