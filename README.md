# football-website

# User Stories

## User Story 1

als **Coach**
möchte ich **Play als Bilddatei hochladen**,
damit **mein Team neue Spielzüge visuell erhält**

## User Story 2

als **Coach**
möchte ich **Ordner und Unterordner anlegen**,
damit **ich Plays übersichtlich nach Offense, Defense etc. sortieren kann**

## User Story 3

als **Coach**
möchte ich **einen Play-Eintrag löschen**,
damit **veraltete oder fehlerhafte Plays entfernt werden**

## User Story 4

als **Player**
möchte ich **mich mit E-Mail und Passwort einloggen**,
damit **ich mein persönliches Playbook sehen kann**

## User Story 5

als **Player**
möchte ich **das Playbook hierarchisch durchklicken**,
damit **ich schnell den passenden Spielzug finde**

## User Story 6

als **Admin**
möchte ich **globale Rollen (Coach / Player) verwalten**,
damit **Berechtigungen sauber gesetzt sind**

## User Story 7

als **Coach**
möchte ich **Trainingstermine anlegen**,
damit **Spieler sich rechtzeitig anmelden können**

## User Story 8

als **Coach**
möchte ich **eine Anwesenheitsliste einsehen**,
damit **ich den Überblick über angemeldete und abgemeldete Spieler behalte**

## User Story 9

als **Player**
möchte ich **mich zu einem Trainingstermin anmelden**,
damit **mein Coach weiß, dass ich teilnehme**

## User Story 10

als **Player**
möchte ich **meine Anmeldung zu einem Trainingstermin wieder stornieren**,
damit **mein Platz freigegeben wird, falls ich verhindert bin**
# ERD Diagramm
```mermaid

erDiagram
  roles {
    int role_id PK
    varchar name
  }

  users {
    int user_id PK
    varchar email UNIQUE
    varchar password_hash
    varchar first_name
    varchar last_name
    varchar status
    timestamp created_at
    timestamp updated_at
    int role_id FK
  }

  refresh_tokens {
    int token_id PK
    int user_id FK
    varchar token
    timestamp expiry
    timestamp created_at
    timestamp revoked_at
  }

  folders {
    int folder_id PK
    varchar name
    int parent_folder_id FK
    int created_by FK
    timestamp created_at
  }

  plays {
    int play_id PK
    varchar name
    varchar file_path
    varchar category
    varchar formation
    int current_version_id FK
    int created_by FK
    int folder_id FK
    timestamp created_at
    timestamp updated_at
  }

  play_versions {
    int version_id PK
    int play_id FK
    varchar file_path
    int version_number
    varchar comment
    int created_by FK
    timestamp created_at
  }

  training_sessions {
    int training_id PK
    varchar title
    timestamp session_datetime
    varchar location
    int created_by FK
    timestamp created_at
  }

  training_attendance {
    int attendance_id PK
    int training_id FK
    int user_id FK
    varchar status
    timestamp timestamp
  }

  notifications {
    int notification_id PK
    varchar message
    varchar link
    timestamp created_at
    int created_by FK
  }

  user_notifications {
    int notification_id FK
    int user_id FK
    boolean is_read
    timestamp read_at
    PK notification_id,user_id
  }

  audit_logs {
    int log_id PK
    varchar entity_type
    int entity_id
    varchar action
    varchar description
    int created_by FK
    timestamp created_at
  }

  roles ||--o{ users : ""
  users ||--o{ refresh_tokens : ""
  users ||--o{ folders : ""
  folders ||--o{ folders : "parent"
  users ||--o{ plays : ""
  folders ||--o{ plays : ""
  plays ||--o{ play_versions : ""
  users ||--o{ play_versions : ""
  users ||--o{ training_sessions : ""
  training_sessions ||--o{ training_attendance : ""
  users ||--o{ training_attendance : ""
  notifications ||--o{ user_notifications : ""
  users ||--o{ notifications : ""
  users ||--o{ user_notifications : ""
  users ||--o{ audit_logs : ""
```
