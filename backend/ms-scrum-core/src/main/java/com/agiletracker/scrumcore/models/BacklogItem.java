package com.agiletracker.scrumcore.models;

import jakarta.persistence.*;

/**
 * @author salma
 **/
@Entity
@Table(name = "backlog_items")
public class BacklogItem {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String titre;
    private String description;
    private Integer priorite;
    private Integer storyPoints;

    @ManyToOne
    @JoinColumn(name = "projet_id")
    private Projet projet;
}
