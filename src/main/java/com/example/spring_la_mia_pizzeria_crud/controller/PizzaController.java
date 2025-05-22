package com.example.spring_la_mia_pizzeria_crud.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.spring_la_mia_pizzeria_crud.model.Pizza;
import com.example.spring_la_mia_pizzeria_crud.repository.PizzaRepository;

import jakarta.validation.Valid;


@Controller
@RequestMapping("/pizze")
public class PizzaController {

    @Autowired
    private PizzaRepository pizzaRepository;

    @GetMapping
    public String index(Model model){
        List<Pizza> result = pizzaRepository.findAll(); // = ...dati dal db...
        model.addAttribute("list", result);
        return "/pizze/index";
    }

    @GetMapping("/create")
    public String createForm(Model model) {
        model.addAttribute("pizza", new Pizza());
        return "/pizze/create";
    }

    @PostMapping("/create")
    public String doCreate(@Valid @ModelAttribute("pizza") Pizza formPizza, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "/pizze/create";
        }
        pizzaRepository.save(formPizza);
        return "redirect:/pizze";
    }

    @GetMapping("/edit/{id}")
    public String edit(@PathVariable Integer id, Model model) {
    Pizza pizza = pizzaRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("ID non valido: " + id));
    model.addAttribute("pizza", pizza);
    return "/pizze/edit";
    }

    @PostMapping("/edit/{id}")
    public String update(@PathVariable Integer id, @Valid @ModelAttribute("pizza") Pizza pizza, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "/pizze/edit";
        }
    pizza.setId(id); // Assicura che venga aggiornato l'elemento corretto
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