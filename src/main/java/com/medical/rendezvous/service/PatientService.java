package com.medical.rendezvous.service;

import com.medical.rendezvous.exception.ResourceNotFoundException;
import com.medical.rendezvous.model.Patient;
import com.medical.rendezvous.repository.PatientRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PatientService {

    private final PatientRepository repository;

    public PatientService(PatientRepository repository) {
        this.repository = repository;
    }

    public List<Patient> getAll(String search) {
        if (search == null || search.isBlank()) return repository.findAll();
        return repository.findByNomContainingIgnoreCaseOrPrenomContainingIgnoreCase(search, search);
    }

    public Patient getById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Patient introuvable : " + id));
    }

    public Patient create(Patient patient) {
        patient.setId(null);
        return repository.save(patient);
    }

    public Patient update(Long id, Patient data) {
        Patient patient = getById(id);
        patient.setNom(data.getNom());
        patient.setPrenom(data.getPrenom());
        patient.setTelephone(data.getTelephone());
        patient.setEmail(data.getEmail());
        return repository.save(patient);
    }

    public void delete(Long id) {
        repository.delete(getById(id));
    }
}
