# U6W2D5Test - Gestione Eventi e Prenotazioni

## Descrizione

Applicazione web sviluppata con **Java**, **Spring Boot** e **Maven** per la gestione di eventi, utenti e prenotazioni. Gli utenti possono registrarsi, autenticarsi e prenotare posti. Gli organizzatori possono creare, modificare ed eliminare eventi.

## Funzionalità principali

- **Autenticazione JWT**: Registrazione e login con generazione di token.
- **Gestione utenti**: Ruoli `USER` e `ORGANIZER`.
- **Gestione eventi**: CRUD eventi (solo organizzatori).
- **Prenotazioni**: Gli utenti possono prenotare posti agli eventi disponibili.
- **Validazione**: Controlli su input e gestione errori centralizzata.
- **Paginazione**: Visualizzazione eventi con paginazione.

## Struttura del progetto

- `entities/` - Modelli JPA (`User`, `Event`, `Booking`)
- `controllers/` - API REST per autenticazione, eventi e prenotazioni
- `services/` - Logica di business
- `repositories/` - Interfacce JPA per accesso ai dati
- `payloads/` - DTO per input/output
- `security/` - Filtri e strumenti JWT
- `exceptions/` - Gestione errori personalizzata

**API principali**
    - `POST /auth/register` - Registrazione utente
    - `POST /auth/login` - Login utente (restituisce JWT)
    - `GET /events` - Lista eventi (paginata)
    - `POST /events` - Crea evento (solo organizzatore)
    - `POST /bookings` - Prenota evento (solo utente autenticato)

## Esempio di richiesta autenticata

Aggiungi l'header:
```
Authorization: Bearer <token>
```

## Seeder

Il progetto include un seeder (`DataSeeder`) per popolare dati di esempio (utenti, eventi, prenotazioni).

## Autore

Mauro Tuzzolino