package com.medical.rendezvous.controller;

import com.medical.rendezvous.model.RendezVous;
import com.medical.rendezvous.model.StatutRendezVous;
import com.medical.rendezvous.service.RendezVousService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/rendez-vous")
@CrossOrigin(origins = "*")
public class RendezVousController {

    private final RendezVousService service;

    public RendezVousController(RendezVousService service) {
        this.service = service;
    }

    @GetMapping
    public List<RendezVous> all() {
        return service.getAll();
    }

    @GetMapping("/{id}")
    public RendezVous one(@PathVariable Long id) {
        return service.getById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public RendezVous create(@Valid @RequestBody RendezVous rendezVous,
                             @RequestParam Long patientId,
                             @RequestParam Long medecinId) {
        return service.create(rendezVous, patientId, medecinId);
    }

    @PutMapping("/{id}")
    public RendezVous update(@PathVariable Long id,
                             @Valid @RequestBody RendezVous rendezVous,
                             @RequestParam Long patientId,
                             @RequestParam Long medecinId) {
        return service.update(id, rendezVous, patientId, medecinId);
    }

    @PatchMapping("/{id}/statut")
    public RendezVous updateStatus(@PathVariable Long id,
                                   @RequestParam StatutRendezVous statut) {
        return service.updateStatus(id, statut);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }
}
