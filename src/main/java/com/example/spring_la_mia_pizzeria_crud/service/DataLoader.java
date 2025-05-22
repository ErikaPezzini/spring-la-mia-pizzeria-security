package com.example.spring_la_mia_pizzeria_crud.service;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.example.spring_la_mia_pizzeria_crud.model.security.Ruolo;
import com.example.spring_la_mia_pizzeria_crud.model.security.Utente;
import com.example.spring_la_mia_pizzeria_crud.repository.RuoloRepository;
import com.example.spring_la_mia_pizzeria_crud.repository.UtenteRepository;

@Configuration
public class DataLoader {

    @Bean
    public CommandLineRunner loadUtenti(
        UtenteRepository utenteRepository,
        RuoloRepository ruoloRepository,
        PasswordEncoder passwordEncoder) {
        
        return args -> {
            Ruolo ruoloAdmin = ruoloRepository.findByNome("ROLE_ADMIN")
                .orElseGet(() -> ruoloRepository.save(new Ruolo("ROLE_ADMIN")));
            
            Ruolo ruoloUser = ruoloRepository.findByNome("ROLE_USER")
                .orElseGet(() -> ruoloRepository.save(new Ruolo("ROLE_USER")));

            if (utenteRepository.findByUsername("admin").isEmpty()) {
                Utente admin = new Utente();
                admin.setUsername("admin");
                admin.setPassword(passwordEncoder.encode("admin123"));
                admin.getRuoli().add(ruoloAdmin);  
                utenteRepository.save(admin);
                System.out.println("Creato utente admin con ruolo ROLE_ADMIN");
            }

            if (utenteRepository.findByUsername("user").isEmpty()) {
                Utente user = new Utente();
                user.setUsername("user");
                user.setPassword(passwordEncoder.encode("user123"));
                user.getRuoli().add(ruoloUser);   
                utenteRepository.save(user);
                System.out.println("Creato utente user con ruolo ROLE_USER");
            }
        };
    }
}
