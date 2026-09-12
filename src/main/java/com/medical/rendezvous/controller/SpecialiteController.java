package com.medical.rendezvous.controller;

import com.medical.rendezvous.model.Specialite;
import com.medical.rendezvous.service.SpecialiteService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/specialites")
@CrossOrigin(origins = "*")
public class SpecialiteController {

    private final SpecialiteService service;

    public SpecialiteController(SpecialiteService service) {
        this.service = service;
    }

    @GetMapping
    public List<Specialite> all() { return service.getAll(); }

    @GetMapping("/{id}")
    public Specialite one(@PathVariable Long id) { return service.getById(id); }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Specialite create(@Valid @RequestBody Specialite specialite) {
        return service.create(specialite);
    }

    @PutMapping("/{id}")
    public Specialite update(@PathVariable Long id, @Valid @RequestBody Specialite specialite) {
        return service.update(id, specialite);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) { service.delete(id); }
}
