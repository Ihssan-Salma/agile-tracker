package com.agile_tracker.user_access_ms.dtos.invites;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class InviteUserResponse {

    private Integer userId;
    private String email;
    private String username;
    private boolean created;
}
