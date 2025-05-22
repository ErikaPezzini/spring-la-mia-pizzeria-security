package com.example.spring_la_mia_pizzeria_crud.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.spring_la_mia_pizzeria_crud.model.security.Ruolo;

@Repository
public interface RuoloRepository extends JpaRepository<Ruolo, Integer> {
    Optional<Ruolo> findByNome(String nome);
}
