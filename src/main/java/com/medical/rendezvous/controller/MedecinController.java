package com.medical.rendezvous.controller;

import com.medical.rendezvous.model.Medecin;
import com.medical.rendezvous.service.MedecinService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/medecins")
@CrossOrigin(origins = "*")
public class MedecinController {

    private final MedecinService service;

    public MedecinController(MedecinService service) {
        this.service = service;
    }

    @GetMapping
    public List<Medecin> all(@RequestParam(required = false) Long specialiteId) {
        return service.getAll(specialiteId);
    }

    @GetMapping("/{id}")
    public Medecin one(@PathVariable Long id) {
        return service.getById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Medecin create(@Valid @RequestBody Medecin medecin,
                          @RequestParam Long specialiteId) {
        return service.create(medecin, specialiteId);
    }

    @PutMapping("/{id}")
    public Medecin update(@PathVariable Long id,
                          @Valid @RequestBody Medecin medecin,
                          @RequestParam Long specialiteId) {
        return service.update(id, medecin, specialiteId);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }
}
