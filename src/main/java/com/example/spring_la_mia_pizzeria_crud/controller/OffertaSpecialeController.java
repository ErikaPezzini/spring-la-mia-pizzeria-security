package com.example.spring_la_mia_pizzeria_crud.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.spring_la_mia_pizzeria_crud.model.OffertaSpeciale;
import com.example.spring_la_mia_pizzeria_crud.repository.OffertaSpecialeRepository;
import com.example.spring_la_mia_pizzeria_crud.repository.PizzaRepository;

@Controller
public class OffertaSpecialeController {

    @Autowired
    private OffertaSpecialeRepository offertaSpecialeRepository;

    private final PizzaRepository pizzaRepository;

    @Autowired
    public OffertaSpecialeController(PizzaRepository pizzaRepository) {
        this.pizzaRepository = pizzaRepository;
    }

    @GetMapping("/offerte/nuova/{pizzaId}")
    public String create(@PathVariable Integer pizzaId, Model model) {
        OffertaSpeciale offerta = new OffertaSpeciale();
        offerta.setPizza(pizzaRepository.findById(pizzaId).orElseThrow());
        model.addAttribute("offerta", offerta);
        return "offerte/nuova";
    }

    @PostMapping("/offerte/store")
    public String store(@ModelAttribute OffertaSpeciale offerta) {
        offertaSpecialeRepository.save(offerta);
        return "redirect:/pizze/" + offerta.getPizza().getId();
    }

    @GetMapping("/offerte/modifica/{id}")
    public String edit(@PathVariable Integer id, Model model) {
        OffertaSpeciale offerta = offertaSpecialeRepository.findById(id).orElseThrow();
        model.addAttribute("offerta", offerta);
        return "offerte/modifica";
    }

    @PostMapping("/offerte/aggiorna")
    public String update(@ModelAttribute OffertaSpeciale offerta) {
        offertaSpecialeRepository.save(offerta);
        return "redirect:/pizze/" + offerta.getPizza().getId();
    }
}
