package com.agiletracker.timereporting.models;
import jakarta.persistence.*;

import java.time.LocalDate;

/**
 * @author salma
 **/
@Entity @Table(name = "rapports")
public class Rapport {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String type;
    private LocalDate dateGeneration;
    private Long projetId;      // Vers MS Scrum
}
