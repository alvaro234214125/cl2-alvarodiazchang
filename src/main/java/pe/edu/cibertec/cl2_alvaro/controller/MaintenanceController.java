package pe.edu.cibertec.cl2_alvaro.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import pe.edu.cibertec.cl2_alvaro.dto.FilmDetailDto;
import pe.edu.cibertec.cl2_alvaro.dto.FilmDto;
import pe.edu.cibertec.cl2_alvaro.service.MaintenanceService;

import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/maintenance")
public class MaintenanceController {

    @Autowired
    MaintenanceService maintenanceService;

    @GetMapping("/start")
    public String start(Model model) {
        List<FilmDto> films = maintenanceService.findAllFilms();
        model.addAttribute("films", films);
        return "maintenance";
    }

    @GetMapping("/detail/{id}")
    public String detail(@PathVariable Integer id, Model model) {
        FilmDetailDto filmDetailDto = maintenanceService.findFilmById(id);
        model.addAttribute("film", filmDetailDto);
        return "maintenance_detail";
    }

    @GetMapping("/edit/{id}")
    public String edit(@PathVariable Integer id, Model model) {
        FilmDetailDto filmDetailDto = maintenanceService.findFilmById(id);
        model.addAttribute("film", filmDetailDto);
        return "maintenance_edit";
    }

    @PostMapping("/edit-confirm")
    public String editConfirm(@ModelAttribute FilmDetailDto filmDetailDto, Model model) {
        maintenanceService.updateFilm(filmDetailDto);
        return "redirect:/maintenance/start";
    }

    @PostMapping("/remove/{id}")
    public String remove(@PathVariable Integer id, RedirectAttributes redirectAttributes) {
        boolean isDeleted = maintenanceService.deleteFilmById(id);
        if (isDeleted) {
            redirectAttributes.addFlashAttribute("successMessage", "Película eliminada correctamente.");
        } else {
            redirectAttributes.addFlashAttribute("errorMessage", "Error: La película no existe o no se pudo eliminar.");
        }
        return "redirect:/maintenance/start";
    }
    @GetMapping("/create")
    public String createForm(Model model) {
        model.addAttribute("film", new FilmDetailDto(null, "", "", null, null, "", null, null, null, null, "", "", new Date()));
        return "maintenance_create";
    }

    @PostMapping("/create")
    public String create(@ModelAttribute FilmDetailDto filmDetailDto, RedirectAttributes redirectAttributes) {
        boolean isCreated = maintenanceService.createFilm(filmDetailDto);
        if (isCreated) {
            redirectAttributes.addFlashAttribute("successMessage", "Película registrada correctamente.");
        } else {
            redirectAttributes.addFlashAttribute("errorMessage", "Error al registrar la película.");
        }return "redirect:/maintenance/start";
    }
}



