package com.agile_tracker.user_access_ms.controllers;

import com.agile_tracker.user_access_ms.dtos.invites.InviteUserRequest;
import com.agile_tracker.user_access_ms.dtos.invites.InviteUserResponse;
import com.agile_tracker.user_access_ms.services.InviteService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/auth/invites")
@RequiredArgsConstructor
@Validated
public class InviteController {

    private final InviteService inviteService;

    @PostMapping
    public ResponseEntity<InviteUserResponse> inviteUser(@Valid @RequestBody InviteUserRequest request) {
        return ResponseEntity.ok(inviteService.inviteUser(request));
    }
}
