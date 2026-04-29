package com.ensam.ms_project.controller;

import com.ensam.ms_project.model.Role;
import com.ensam.ms_project.service.EquipeService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/equipe")
public class EquipeController {
    private EquipeService equipeService;

    public EquipeController(EquipeService equipeService) {
        this.equipeService = equipeService;
    }

    @PostMapping("/createEquipe")
    public ResponseEntity<?> createEquipe(@RequestParam int projet_id,@RequestParam String nom_equipe,@RequestParam Role myRole){
        try{
            return ResponseEntity.ok(equipeService.createEquipe(projet_id,nom_equipe,myRole));

        }catch(Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    @PostMapping("/addMemberEquipe")
    public ResponseEntity<?> addMemberEquipe(@RequestParam int user_id,@RequestParam int equipe_id,@RequestParam Role myRole){
        try{
            return ResponseEntity.ok(equipeService.addMemberEquipe(user_id,equipe_id,myRole));

        }catch(Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/retirerMembre")
    public ResponseEntity<?> retirerMembre(@RequestParam int user_id,@RequestParam int equipe_id,@RequestParam Role myRole){
        try{
            return ResponseEntity.ok(equipeService.retirerMember(user_id,equipe_id,myRole));

        }catch(Exception e){
            System.out.println(e.getMessage());
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/getEquipes")
    public ResponseEntity<?> getEquipes(){
        try{
            return ResponseEntity.ok(equipeService.getEquipe());

        }catch(Exception e){
            System.out.println(e.getMessage());
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
