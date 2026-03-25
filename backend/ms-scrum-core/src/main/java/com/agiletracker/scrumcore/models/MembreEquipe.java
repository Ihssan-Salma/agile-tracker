package com.agiletracker.scrumcore.models;
import jakarta.persistence.*;
/**
 * @author salma
 **/
@Entity @Table(name = "membres_equipe")
public class MembreEquipe {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne @JoinColumn(name = "equipe_id")
    private Equipe equipe;
    private Long utilisateurId; // Lien externe
}
