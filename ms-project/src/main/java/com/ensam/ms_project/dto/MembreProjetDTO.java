package com.ensam.ms_project.dto;

import com.ensam.ms_project.model.Role;
import lombok.Data;

@Data
public class MembreProjetDTO {
    private int id;
    private int projetId;
    private int userId;
    private Role role;
}
