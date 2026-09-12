package com.medical.rendezvous.repository;

import com.medical.rendezvous.model.Medecin;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MedecinRepository extends JpaRepository<Medecin, Long> {
    List<Medecin> findBySpecialiteId(Long specialiteId);
}
