package com.agile_tracker.user_access_ms.services.impl;

import com.agile_tracker.user_access_ms.services.InviteNotificationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class LoggingInviteNotificationService implements InviteNotificationService {

    private static final Logger LOGGER = LoggerFactory.getLogger(LoggingInviteNotificationService.class);

    @Override
    public void sendInvite(String email, String username, String password) {
        LOGGER.info("Invite email to {} with username {}. Temp password: {}", email, username, password);
    }
}
