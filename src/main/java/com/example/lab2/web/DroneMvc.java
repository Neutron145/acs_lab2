package com.example.lab2.web;

import com.example.lab2.model.Drone;
import com.example.lab2.service.DroneService;
import com.example.lab2.service.FlightControllerService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@Controller
@RequestMapping("/drones")
public class DroneMvc {

    private final DroneService droneService;
    private final FlightControllerService fcService;

    public DroneMvc(DroneService droneService, FlightControllerService fcService) {
        this.droneService = droneService;
        this.fcService = fcService;
    }

    // Список дронов: модель "items" для шаблона drones/list.html
    @GetMapping
    public String list(Model model) {
        model.addAttribute("items", droneService.getAll());
        return "drones/list";
    }

    // Форма создания: form + список контроллеров + выбранный контроллер = null
    @GetMapping("/new")
    public String createForm(Model model) {
        model.addAttribute("form", new Drone());
        model.addAttribute("controllers", fcService.getAll());
        model.addAttribute("selectedControllerId", null);
        return "drones/form";
    }

    // Сохранение нового: при ошибках ВОЗВРАЩАЕМ те же модельные атрибуты
    @PostMapping
    public String create(@ModelAttribute("form") @Valid Drone form,
                         BindingResult br,
                         @RequestParam(value = "controllerId", required = false) Long controllerId,
                         Model model) {
        if (br.hasErrors()) {
            model.addAttribute("controllers", fcService.getAll());
            model.addAttribute("selectedControllerId", controllerId);
            return "drones/form";
        }
        droneService.create(form.getType(), controllerId, form.getWeight());
        return "redirect:/drones";
    }

    // Форма редактирования: подставляем текущие значения
    @GetMapping("/{id}/edit")
    public String editForm(@PathVariable long id, Model model) {
        Drone d = droneService.getById(id)
                .orElseThrow(() -> new IllegalArgumentException("Не найден дрон id=" + id));
        model.addAttribute("form", d);
        model.addAttribute("controllers", fcService.getAll());
        model.addAttribute("selectedControllerId", d.getController() != null ? d.getController().getId() : null);
        return "drones/form";
    }

    // Обновление: при ошибках — вернуть форму с теми же атрибутами
    @PostMapping("/{id}")
    public String update(@PathVariable long id,
                         @ModelAttribute("form") @Valid Drone form,
                         BindingResult br,
                         @RequestParam(value = "controllerId", required = false) Long controllerId,
                         Model model) {
        if (br.hasErrors()) {
            model.addAttribute("controllers", fcService.getAll());
            model.addAttribute("selectedControllerId", controllerId);
            return "drones/form";
        }
        droneService.update(id, form.getType(), controllerId, form.getWeight());
        return "redirect:/drones";
    }

    @PostMapping("/{id}/delete")
    public String delete(@PathVariable long id) {
        droneService.delete(id);
        return "redirect:/drones";
    }
}
