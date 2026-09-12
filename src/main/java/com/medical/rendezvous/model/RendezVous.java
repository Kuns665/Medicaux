package com.medical.rendezvous.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Table(
    name = "rendez_vous",
    uniqueConstraints = @UniqueConstraint(
        name = "uk_medecin_date_heure",
        columnNames = {"medecin_id", "date_rdv", "heure_rdv"}
    )
)
public class RendezVous {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "La date est obligatoire")
    @Column(name = "date_rdv", nullable = false)
    private LocalDate dateRdv;

    @NotNull(message = "L'heure est obligatoire")
    @Column(name = "heure_rdv", nullable = false)
    private LocalTime heureRdv;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private StatutRendezVous statut = StatutRendezVous.EN_ATTENTE;

    @ManyToOne(optional = false)
    @JoinColumn(name = "patient_id", nullable = false)
    private Patient patient;

    @ManyToOne(optional = false)
    @JoinColumn(name = "medecin_id", nullable = false)
    private Medecin medecin;

    public RendezVous() {}

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public LocalDate getDateRdv() { return dateRdv; }
    public void setDateRdv(LocalDate dateRdv) { this.dateRdv = dateRdv; }
    public LocalTime getHeureRdv() { return heureRdv; }
    public void setHeureRdv(LocalTime heureRdv) { this.heureRdv = heureRdv; }
    public StatutRendezVous getStatut() { return statut; }
    public void setStatut(StatutRendezVous statut) { this.statut = statut; }
    public Patient getPatient() { return patient; }
    public void setPatient(Patient patient) { this.patient = patient; }
    public Medecin getMedecin() { return medecin; }
    public void setMedecin(Medecin medecin) { this.medecin = medecin; }
}
