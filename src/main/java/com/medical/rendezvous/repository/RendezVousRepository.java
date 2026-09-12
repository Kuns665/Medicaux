package com.medical.rendezvous.repository;

import com.medical.rendezvous.model.RendezVous;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.time.LocalTime;

public interface RendezVousRepository extends JpaRepository<RendezVous, Long> {
    boolean existsByMedecinIdAndDateRdvAndHeureRdv(Long medecinId, LocalDate dateRdv, LocalTime heureRdv);
}
