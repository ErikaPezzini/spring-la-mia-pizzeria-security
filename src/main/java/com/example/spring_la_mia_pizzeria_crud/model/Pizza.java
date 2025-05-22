package com.example.spring_la_mia_pizzeria_crud.model;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "pizza")
public class Pizza {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Integer id;

    @NotBlank(message = "Il nome è obbligatorio")
    @Size(max = 255, message = "Il nome non può superare i 255 caratteri")
    @Column(unique=true,length=255,nullable=false)
    private String nome;

    @NotBlank(message = "La descrizione è obbligatoria")
    @Size(max = 500, message = "La descrizione non può superare i 500 caratteri")
    @Column(length=500)
    private String descrizione;

    @NotBlank(message = "Il link della foto è obbligatorio")
    @Lob
    @Column(columnDefinition = "TEXT")
    private String foto;

    @NotNull(message = "Il prezzo è obbligatorio")
    @DecimalMin(value = "0.01", message = "Il prezzo deve essere maggiore di zero")
    @Column(nullable=false,precision=8,scale=2)
    private BigDecimal prezzo;

    @NotNull(message = "Disponibilità richiesta")
    @Column(nullable=false)
    private Boolean disponibile;

    @OneToMany(mappedBy = "pizza", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<OffertaSpeciale> offerte = new ArrayList<>();


    public Integer getId() {
        return id;
    }

    public List<OffertaSpeciale> getOfferte() {
        return offerte;
    }

    public void setOfferte(List<OffertaSpeciale> offerte) {
        this.offerte = offerte;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDescrizione() {
        return descrizione;
    }

    public void setDescrizione(String descrizione) {
        this.descrizione = descrizione;
    }

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }

    public BigDecimal getPrezzo() {
        return prezzo;
    }

    public void setPrezzo(BigDecimal prezzo) {
        this.prezzo = prezzo;
    }

    public Boolean getDisponibile() {
        return disponibile;
    }

    public void setDisponibile(Boolean disponibile) {
        this.disponibile = disponibile;
    }


}
