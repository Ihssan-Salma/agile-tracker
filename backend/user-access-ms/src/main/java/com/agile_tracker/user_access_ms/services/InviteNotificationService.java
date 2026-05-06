package com.agile_tracker.user_access_ms.services;

public interface InviteNotificationService {

    void sendInvite(String email, String username, String password);
}
