package com.example.spring_la_mia_pizzeria_crud.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.spring_la_mia_pizzeria_crud.model.OffertaSpeciale;

@Repository
public interface OffertaSpecialeRepository extends JpaRepository<OffertaSpeciale, Integer> {

}
