package com.medical.rendezvous.repository;

import com.medical.rendezvous.model.Specialite;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SpecialiteRepository extends JpaRepository<Specialite, Long> {
}
