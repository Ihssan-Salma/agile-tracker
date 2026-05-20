package com.agile_tracker.user_access_ms.services.impl;

import com.agile_tracker.user_access_ms.services.InviteNotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MailInviteNotificationService implements InviteNotificationService {

    private final JavaMailSender mailSender;

    @Override
    @Async // The logic: Don't make the user wait for the email to send
    public void sendInvite(String email, String username, String password, String projectName, String role) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(email);
        message.setSubject("Invitation : Rejoignez le projet " + projectName);

        String content = String.format(
                "Bonjour %s,\n\n" +
                        "Vous avez été invité à rejoindre le projet '%s' en tant que %s.\n\n" +
                        "Voici vos identifiants de connexion :\n" +
                        "Nom d'utilisateur : %s\n",
                username, projectName, role, username
        );

        if (password != null) {
            content += "Mot de passe temporaire : " + password + "\n";
        }

        content += "\nConnectez-vous dès maintenant pour commencer à travailler.";

        message.setText(content);
        mailSender.send(message);
    }
}