package com.example.spring_la_mia_pizzeria_crud.controller;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.spring_la_mia_pizzeria_crud.model.Ingrediente;
import com.example.spring_la_mia_pizzeria_crud.model.Pizza;
import com.example.spring_la_mia_pizzeria_crud.repository.IngredienteRepository;
import com.example.spring_la_mia_pizzeria_crud.repository.PizzaRepository;

import jakarta.validation.Valid;

@Controller
@RequestMapping("/pizze")
public class PizzaController {

    @Autowired
    private PizzaRepository pizzaRepository;

    @Autowired
    private IngredienteRepository ingredienteRepository;

    @GetMapping
    public String index(Model model){
        List<Pizza> result = pizzaRepository.findAll();
        model.addAttribute("list", result);
        return "/pizze/index";
    }

    @GetMapping("/create")
    public String creaPizzaForm(Model model) {
        model.addAttribute("pizza", new Pizza());
        List<Ingrediente> tuttiIngredienti = ingredienteRepository.findAll();  
        model.addAttribute("ingredientiDisponibili", tuttiIngredienti);
        return "pizze/create";
    }

    @PostMapping("/create")
    public String creaPizzaSubmit(@Valid @ModelAttribute Pizza pizza, BindingResult bindingResult,
    @RequestParam(required=false) List<Integer> ingredientiSelezionati, Model model) {
        if(bindingResult.hasErrors()) {
            List<Ingrediente> tuttiIngredienti = ingredienteRepository.findAll();
            model.addAttribute("ingredientiDisponibili", tuttiIngredienti);
            return "pizze/create";
        }

        if (ingredientiSelezionati != null) {
            Set<Ingrediente> ingredientiSet = new HashSet<>(ingredienteRepository.findAllById(ingredientiSelezionati));
            pizza.setIngredienti(ingredientiSet);
        } else {
            pizza.setIngredienti(new HashSet<>());
        }
        pizzaRepository.save(pizza);
        return "redirect:/pizze";
    }

    @GetMapping("/edit/{id}")
    public String editPizzaForm(@PathVariable Integer id, Model model) {
        Pizza pizza = pizzaRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Pizza non trovata: " + id));
        model.addAttribute("pizza", pizza);

        List<Ingrediente> tuttiIngredienti = ingredienteRepository.findAll();
        model.addAttribute("ingredientiDisponibili", tuttiIngredienti);

        return "pizze/edit";
    }

    @PostMapping("/edit/{id}")
    public String updatePizza(@PathVariable Integer id, @Valid @ModelAttribute("pizza") Pizza pizza, BindingResult bindingResult,
    @RequestParam(required = false) List<Integer> ingredientiSelezionati, Model model) {
        if (bindingResult.hasErrors()) {
            List<Ingrediente> tuttiIngredienti = ingredienteRepository.findAll();
            model.addAttribute("ingredientiDisponibili", tuttiIngredienti);
            return "pizze/edit";
        }

        pizza.setId(id);
        if (ingredientiSelezionati != null) {
            Set<Ingrediente> ingredientiSet = new HashSet<>(ingredienteRepository.findAllById(ingredientiSelezionati));
            pizza.setIngredienti(ingredientiSet);
        } else {
            pizza.setIngredienti(new HashSet<>());
        }

        pizzaRepository.save(pizza);
        return "redirect:/pizze";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Integer id) {
        pizzaRepository.deleteById(id);
        return "redirect:/pizze";
    }

    @GetMapping("/{id}")
    public String show(@PathVariable Integer id, Model model) {
        Pizza pizza = pizzaRepository.findById(id).orElseThrow();
        model.addAttribute("pizza", pizza);
        return "pizze/dettagli";
    }

    @GetMapping("/test")
    @ResponseBody
    public String test() {
        return "Controller attivo";
    }
}
