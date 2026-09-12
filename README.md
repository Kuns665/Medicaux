# 🏥 Gestion des Rendez-vous Médicaux avec CI/CD

## Technologies
- Java 21
- Spring Boot
- Spring Data JPA / Hibernate
- MySQL
- Maven
- HTML / CSS / JavaScript
- GitHub Actions

## Prérequis
- JDK 21 ou supérieur
- Maven
- MySQL

## Base de données

Créez la base :

```sql
CREATE DATABASE gestion_rendez_vous_medical;
```

Modifiez si nécessaire :

`src/main/resources/application.properties`

```properties
spring.datasource.username=root
spring.datasource.password=
```

## Lancer dans Eclipse

1. Ouvrir Eclipse.
2. `File > Import`.
3. `Maven > Existing Maven Projects`.
4. Sélectionner le dossier du projet.
5. Attendre le téléchargement des dépendances Maven.
6. Clic droit sur `GestionRendezVousApplication.java`.
7. `Run As > Java Application` ou `Spring Boot App`.

L'application est disponible sur :

`http://localhost:8080`

## Tests

```bash
mvn test
```

## Build

```bash
mvn clean package
```

Le fichier JAR sera créé dans :

```text
target/
```

## CI/CD

À chaque `git push`, GitHub Actions :

1. récupère le code ;
2. installe Java ;
3. exécute les tests ;
4. construit le JAR ;
5. sauvegarde le JAR comme artefact.

Le workflow est dans :

```text
.github/workflows/ci-cd.yml
```

## API

- `/api/patients`
- `/api/specialites`
- `/api/medecins`
- `/api/rendez-vous`
