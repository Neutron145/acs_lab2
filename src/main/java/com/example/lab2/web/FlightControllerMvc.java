package com.example.lab2.web;

import com.example.lab2.model.FlightController;
import com.example.lab2.service.FlightControllerService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@Controller
@RequestMapping("/controllers")
public class FlightControllerMvc {
    private final FlightControllerService service;

    public FlightControllerMvc(FlightControllerService service) {
        this.service = service;
    }

    /** Список */
    @GetMapping
    public String list(Model model) {
        model.addAttribute("items", service.getAll());
        return "controllers/list";
    }

    /** Форма создания */
    @GetMapping("/new")
    public String createForm(Model model) {
        model.addAttribute("form", new FlightController());
        return "controllers/form";
    }

    /** Обработка создания */
    @PostMapping
    public String create(@ModelAttribute("form") @Valid FlightController form,
                         BindingResult br,
                         Model model) {
        if (br.hasErrors()) {
            return "controllers/form";
        }
        service.create(form.getName(), form.getManufacturer(),
                form.getCost() == null ? BigDecimal.ZERO : form.getCost());
        return "redirect:/controllers";
    }

    /** Форма редактирования */
    @GetMapping("/{id}/edit")
    public String editForm(@PathVariable long id, Model model) {
        FlightController fc = service.getById(id)
                .orElseThrow(() -> new IllegalArgumentException("Не найден контроллер id=" + id));
        model.addAttribute("form", fc);
        return "controllers/form";
    }

    /** Обработка редактирования */
    @PostMapping("/{id}")
    public String update(@PathVariable long id,
                         @ModelAttribute("form") @Valid FlightController form,
                         BindingResult br) {
        if (br.hasErrors()) {
            return "controllers/form";
        }
        service.update(id, form.getName(), form.getManufacturer(), form.getCost());
        return "redirect:/controllers";
    }

    /** Удаление */
    @PostMapping("/{id}/delete")
    public String delete(@PathVariable long id) {
        service.delete(id);
        return "redirect:/controllers";
    }
}
