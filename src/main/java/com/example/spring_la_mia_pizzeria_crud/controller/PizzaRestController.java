package com.example.spring_la_mia_pizzeria_crud.controller;

import java.util.List;
import java.util.Set;
import java.util.HashSet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.example.spring_la_mia_pizzeria_crud.model.Ingrediente;
import com.example.spring_la_mia_pizzeria_crud.model.Pizza;
import com.example.spring_la_mia_pizzeria_crud.repository.IngredienteRepository;
import com.example.spring_la_mia_pizzeria_crud.repository.PizzaRepository;

@RestController
@RequestMapping("/api/pizze")
public class PizzaRestController {

    @Autowired
    private PizzaRepository pizzaRepository;

    @Autowired
    private IngredienteRepository ingredienteRepository;

    @GetMapping
    public List<Pizza> listPizze(@RequestParam(required = false) String nome) {
        if (nome != null && !nome.isEmpty()) {
            return pizzaRepository.findByNomeContainingIgnoreCase(nome);
        }
        return pizzaRepository.findAll();
    }

    @GetMapping("/{id}")
    public Pizza getPizza(@PathVariable Integer id) {
        return pizzaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Pizza non trovata con id: " + id));
    }

    @PostMapping
    public Pizza createPizza(@RequestBody PizzaRequest pizzaRequest) {
        Pizza pizza = new Pizza();
        pizza.setNome(pizzaRequest.nome);
        pizza.setDescrizione(pizzaRequest.descrizione);
        pizza.setFoto(pizzaRequest.foto);
        pizza.setPrezzo(pizzaRequest.prezzo);
        pizza.setDisponibile(pizzaRequest.disponibile);

        if (pizzaRequest.ingredientiIds != null && !pizzaRequest.ingredientiIds.isEmpty()) {
            Set<Ingrediente> ingredientiSet = new HashSet<>(ingredienteRepository.findAllById(pizzaRequest.ingredientiIds));
            pizza.setIngredienti(ingredientiSet);
        }

        return pizzaRepository.save(pizza);
    }

    @PutMapping("/{id}")
    public Pizza updatePizza(@PathVariable Integer id, @RequestBody PizzaRequest pizzaRequest) {
        Pizza pizza = pizzaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Pizza non trovata con id: " + id));

        pizza.setNome(pizzaRequest.nome);
        pizza.setDescrizione(pizzaRequest.descrizione);
        pizza.setFoto(pizzaRequest.foto);
        pizza.setPrezzo(pizzaRequest.prezzo);
        pizza.setDisponibile(pizzaRequest.disponibile);

        if (pizzaRequest.ingredientiIds != null) {
            Set<Ingrediente> ingredientiSet = new HashSet<>(ingredienteRepository.findAllById(pizzaRequest.ingredientiIds));
            pizza.setIngredienti(ingredientiSet);
        } else {
            pizza.setIngredienti(new HashSet<>());
        }

        return pizzaRepository.save(pizza);
    }

    @DeleteMapping("/{id}")
    public void deletePizza(@PathVariable Integer id) {
        pizzaRepository.deleteById(id);
    }

    public static class PizzaRequest {
        public String nome;
        public String descrizione;
        public String foto;
        public java.math.BigDecimal prezzo;
        public Boolean disponibile;
        public List<Integer> ingredientiIds;
    }
}