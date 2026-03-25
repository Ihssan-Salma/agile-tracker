package com.agiletracker.scrumcore.models;

import jakarta.persistence.*;

/**
 * @author salma
 **/
@Entity
@Table(name = "taches")
public class Tache {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String titre;
    private String description;
    private String type;
    private Integer priorite;
    private Float estimation;
    private String statut;

    @ManyToOne
    @JoinColumn(name = "sprint_id")
    private Sprint sprint;

    @ManyToOne @JoinColumn(name = "backlogitem_id")
    private BacklogItem backlogItem;

    // Référence externe vers MS User Access
    private Long developpeurId;
}
