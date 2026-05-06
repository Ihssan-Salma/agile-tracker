package com.agile_tracker.user_access_ms.services;

import com.agile_tracker.user_access_ms.dtos.invites.InviteUserRequest;
import com.agile_tracker.user_access_ms.dtos.invites.InviteUserResponse;

public interface InviteService {

    InviteUserResponse inviteUser(InviteUserRequest request);
}
