package com.ensam.ms_project.controller;

import com.ensam.ms_project.model.Role;
import com.ensam.ms_project.service.MembreProjetService;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/projet/membre-projet")
public class MembreProjetController {
    private final MembreProjetService membreProjetService;
    public MembreProjetController(MembreProjetService membreProjetService) {
        this.membreProjetService = membreProjetService;
    }

    @PostMapping("/inviter")
    public ResponseEntity<?> invitermembre(@RequestParam int user_id, @RequestParam int projet_id, @RequestParam String roleAffecte,@RequestHeader("X-User-Id") Long userId,

                                           @RequestHeader("X-User-Exp") Long expiration,

                                           @RequestHeader("Authorization") String token){
        try{
            return ResponseEntity.ok(membreProjetService.inviterMembre(userId,user_id,projet_id,roleAffecte));

        }catch(Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }

    }
    @DeleteMapping("/retirer")
    public ResponseEntity<?> retirer(@RequestParam int membre_id, @RequestParam int projet_id, @RequestParam Role myrole){
        try{
            return ResponseEntity.ok(membreProjetService.retirerMembre(membre_id,projet_id,myrole));

        }catch(Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }

    }
    @GetMapping("/get-membres")
    public ResponseEntity<?> get_members(@RequestParam int projet_id){
        try{
            return ResponseEntity.ok(membreProjetService.getMembreProjet(projet_id));

        }catch(Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }

    }

}
