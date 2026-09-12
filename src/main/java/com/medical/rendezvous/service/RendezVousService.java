package com.medical.rendezvous.service;

import com.medical.rendezvous.exception.ResourceNotFoundException;
import com.medical.rendezvous.model.*;
import com.medical.rendezvous.repository.RendezVousRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RendezVousService {

    private final RendezVousRepository repository;
    private final PatientService patientService;
    private final MedecinService medecinService;

    public RendezVousService(RendezVousRepository repository,
                             PatientService patientService,
                             MedecinService medecinService) {
        this.repository = repository;
        this.patientService = patientService;
        this.medecinService = medecinService;
    }

    public List<RendezVous> getAll() {
        return repository.findAll();
    }

    public RendezVous getById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Rendez-vous introuvable : " + id));
    }

    public RendezVous create(RendezVous data, Long patientId, Long medecinId) {
        if (repository.existsByMedecinIdAndDateRdvAndHeureRdv(
                medecinId, data.getDateRdv(), data.getHeureRdv())) {
            throw new IllegalArgumentException("Ce médecin possède déjà un rendez-vous à cette date et cette heure.");
        }

        data.setId(null);
        data.setPatient(patientService.getById(patientId));
        data.setMedecin(medecinService.getById(medecinId));

        if (data.getStatut() == null) {
            data.setStatut(StatutRendezVous.EN_ATTENTE);
        }

        return repository.save(data);
    }

    public RendezVous update(Long id, RendezVous data, Long patientId, Long medecinId) {
        RendezVous rdv = getById(id);

        boolean slotChanged = !rdv.getMedecin().getId().equals(medecinId)
                || !rdv.getDateRdv().equals(data.getDateRdv())
                || !rdv.getHeureRdv().equals(data.getHeureRdv());

        if (slotChanged && repository.existsByMedecinIdAndDateRdvAndHeureRdv(
                medecinId, data.getDateRdv(), data.getHeureRdv())) {
            throw new IllegalArgumentException("Ce créneau est déjà occupé.");
        }

        rdv.setDateRdv(data.getDateRdv());
        rdv.setHeureRdv(data.getHeureRdv());
        rdv.setStatut(data.getStatut() == null ? rdv.getStatut() : data.getStatut());
        rdv.setPatient(patientService.getById(patientId));
        rdv.setMedecin(medecinService.getById(medecinId));

        return repository.save(rdv);
    }

    public RendezVous updateStatus(Long id, StatutRendezVous statut) {
        RendezVous rdv = getById(id);
        rdv.setStatut(statut);
        return repository.save(rdv);
    }

    public void delete(Long id) {
        repository.delete(getById(id));
    }
}
