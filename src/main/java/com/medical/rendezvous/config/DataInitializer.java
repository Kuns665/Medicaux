package com.medical.rendezvous.config;

import com.medical.rendezvous.model.Specialite;
import com.medical.rendezvous.repository.SpecialiteRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DataInitializer {

    @Bean
    CommandLineRunner initSpecialites(SpecialiteRepository repository) {
        return args -> {
            if (repository.count() == 0) {
                for (String nom : new String[]{
                        "Médecine générale", "Cardiologie", "Dermatologie",
                        "Pédiatrie", "Ophtalmologie"
                }) {
                    Specialite s = new Specialite();
                    s.setNom(nom);
                    repository.save(s);
                }
            }
        };
    }
}
