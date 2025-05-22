package com.example.spring_la_mia_pizzeria_crud.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.spring_la_mia_pizzeria_crud.model.Ingrediente;
import com.example.spring_la_mia_pizzeria_crud.repository.IngredienteRepository;

@Controller
@RequestMapping("/ingredienti")
public class IngredienteController {

    @Autowired
    private IngredienteRepository ingredienteRepository;

    @GetMapping
    public String listaIngredienti(Model model) {
        List<Ingrediente> ingredienti = ingredienteRepository.findAll();
        model.addAttribute("ingredienti", ingredienti);
        return "ingredienti/index";
    }

    @GetMapping("/create")
    public String creaIngredienteForm(Model model) {
        model.addAttribute("ingrediente", new Ingrediente());
        return "ingredienti/create";
    }

    @PostMapping("/create")
    public String creaIngredienteSubmit(@ModelAttribute Ingrediente ingrediente) {
        ingredienteRepository.save(ingrediente);
        return "redirect:/ingredienti";
    }

    @GetMapping("/delete/{id}")
    public String eliminaIngrediente(@PathVariable Integer id) {
        ingredienteRepository.deleteById(id);
        return "redirect:/ingredienti";
    }
}
