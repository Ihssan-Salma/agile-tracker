INSERT INTO utilisateurs (nom, prenom, email, username, password_hash, competences, capacite_hebdo, disponibilite)
VALUES ('Test', 'User', 'test.user@example.com', 'testuser', '$2a$10$7EqJtq98hPqEX7fNZaFWoOhi5cWw5y6IM6l4/36w9d6t2L5rQ2E5m', 'Java,Spring', 35.0, 0.8)
ON CONFLICT DO NOTHING;
