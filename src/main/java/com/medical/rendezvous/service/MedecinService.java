package com.medical.rendezvous.service;

import com.medical.rendezvous.exception.ResourceNotFoundException;
import com.medical.rendezvous.model.Medecin;
import com.medical.rendezvous.model.Specialite;
import com.medical.rendezvous.repository.MedecinRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MedecinService {

    private final MedecinRepository repository;
    private final SpecialiteService specialiteService;

    public MedecinService(MedecinRepository repository, SpecialiteService specialiteService) {
        this.repository = repository;
        this.specialiteService = specialiteService;
    }

    public List<Medecin> getAll(Long specialiteId) {
        return specialiteId == null ? repository.findAll() : repository.findBySpecialiteId(specialiteId);
    }

    public Medecin getById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Médecin introuvable : " + id));
    }

    public Medecin create(Medecin medecin, Long specialiteId) {
        Specialite specialite = specialiteService.getById(specialiteId);
        medecin.setId(null);
        medecin.setSpecialite(specialite);
        return repository.save(medecin);
    }

    public Medecin update(Long id, Medecin data, Long specialiteId) {
        Medecin medecin = getById(id);
        medecin.setNom(data.getNom());
        medecin.setPrenom(data.getPrenom());
        medecin.setTelephone(data.getTelephone());
        medecin.setEmail(data.getEmail());
        medecin.setSpecialite(specialiteService.getById(specialiteId));
        return repository.save(medecin);
    }

    public void delete(Long id) {
        repository.delete(getById(id));
    }
}
