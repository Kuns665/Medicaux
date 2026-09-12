package com.medical.rendezvous.service;

import com.medical.rendezvous.exception.ResourceNotFoundException;
import com.medical.rendezvous.model.Specialite;
import com.medical.rendezvous.repository.SpecialiteRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SpecialiteService {

    private final SpecialiteRepository repository;

    public SpecialiteService(SpecialiteRepository repository) {
        this.repository = repository;
    }

    public List<Specialite> getAll() { return repository.findAll(); }

    public Specialite getById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Spécialité introuvable : " + id));
    }

    public Specialite create(Specialite specialite) {
        specialite.setId(null);
        return repository.save(specialite);
    }

    public Specialite update(Long id, Specialite data) {
        Specialite specialite = getById(id);
        specialite.setNom(data.getNom());
        return repository.save(specialite);
    }

    public void delete(Long id) {
        repository.delete(getById(id));
    }
}
