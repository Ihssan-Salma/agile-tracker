package com.ensam.ms_project.controller;

import com.ensam.ms_project.dto.ProjetRequestDTO;
import com.ensam.ms_project.dto.ProjetUpdateDTO;
import com.ensam.ms_project.model.Role;
import com.ensam.ms_project.service.ProjetService;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/projet")
public class ProjetController {
    private final ProjetService projetService;

    public ProjetController(ProjetService projetService) {
        this.projetService = projetService;
    }

    @PostMapping("/create")
    public ResponseEntity<?> createProjet(

            @RequestBody ProjetRequestDTO projetDTO,

            @RequestParam Role role,

            @RequestHeader("X-User-Id") Long userId,

            @RequestHeader("X-User-Exp") Long expiration,

            @RequestHeader("Authorization") String token
    ) {

        try {

            System.out.println("User ID : " + userId);
            System.out.println("Expiration : " + expiration);
            System.out.println("Token : " + token);

            return ResponseEntity.ok(
                    projetService.createProjet(projetDTO, userId)
            );

        } catch(Exception e) {

            return ResponseEntity
                    .badRequest()
                    .body(e.getMessage());
        }
    }
    @PutMapping("/update")
    public ResponseEntity<?> updateProjet(@RequestBody ProjetUpdateDTO projetUpdateDTO,@RequestParam int projet_id,@RequestParam Role role){
        try{
            return ResponseEntity.ok(projetService.updateProjet(projetUpdateDTO,projet_id,role));

        }catch(Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }

    }
    @DeleteMapping("/delete")
    public ResponseEntity<?> deleteProjet(@RequestParam int projet_id,@RequestParam Role role){
        try{
            return ResponseEntity.ok(projetService.deleteProjet(projet_id,role));

        }catch(Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }

    }
    @GetMapping("/get-projet")
    public ResponseEntity<?> get_projet(){
        try{
            return ResponseEntity.ok(projetService.getProjet());

        }catch(Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }

    }


}
