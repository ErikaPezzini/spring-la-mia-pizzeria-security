package com.example.spring_la_mia_pizzeria_crud.model;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Entity
public class OffertaSpeciale {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Integer id;

    @NotBlank(message = "Il titolo è obbligatorio")
    @Size(max = 255, message = "Il titolo non può superare i 255 caratteri")
    @Column(unique=true,length=255,nullable=false)
    private String titolo;

    @Column(name="data_inizio")
    private LocalDate dataInizio;
    @Column(name="data_fine")
    private LocalDate dataFine;

    @ManyToOne
    @JoinColumn(name="id_pizza")
    private Pizza pizza;
}
