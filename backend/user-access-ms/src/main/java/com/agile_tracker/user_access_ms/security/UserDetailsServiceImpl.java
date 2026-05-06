package com.agile_tracker.user_access_ms.security;

import com.agile_tracker.user_access_ms.repositories.UtilisateurRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UtilisateurRepository utilisateurRepository;

    @Override
    public UserDetails loadUserByUsername(String usernameOrEmail) throws UsernameNotFoundException {
        return utilisateurRepository.findByUsername(usernameOrEmail)
                .or(() -> utilisateurRepository.findByEmail(usernameOrEmail))
                .map(AuthenticatedUser::new)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }
}
