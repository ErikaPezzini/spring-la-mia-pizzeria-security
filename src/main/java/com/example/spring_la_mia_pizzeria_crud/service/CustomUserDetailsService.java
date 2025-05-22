package com.example.spring_la_mia_pizzeria_crud.service;

import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.spring_la_mia_pizzeria_crud.model.security.Utente;
import com.example.spring_la_mia_pizzeria_crud.repository.UtenteRepository;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UtenteRepository utenteRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Utente utente = utenteRepository.findByUsername(username)
            .orElseThrow(() -> new UsernameNotFoundException("Utente non trovato: " + username));

        Set<GrantedAuthority> authorities = utente.getRuoli().stream()
            .map(ruolo -> new SimpleGrantedAuthority(ruolo.getNome()))
            .collect(Collectors.toSet());

        return new org.springframework.security.core.userdetails.User(
            utente.getUsername(),
            utente.getPassword(),
            authorities
        );
    }
}
