-- =========================
-- 1. Création des tables
-- =========================

CREATE TABLE TypeProduit (
    id_type SERIAL PRIMARY KEY,
    libelle VARCHAR(30) NOT NULL 
);

CREATE TABLE Produit (
    id_produit SERIAL PRIMARY KEY,
    nom VARCHAR(50) NOT NULL,
    id_type INT NOT NULL REFERENCES TypeProduit(id_type)
);

CREATE TABLE Vente (
    id_vente SERIAL PRIMARY KEY,
    date_vente DATE NOT NULL,
    id_produit INT NOT NULL REFERENCES Produit(id_produit),
    quantite_kg DECIMAL(7,2) NOT NULL,
    prix_kg DECIMAL(7,2) NOT NULL
);

CREATE TABLE Client (
    id_client SERIAL PRIMARY KEY,
    nom VARCHAR(50) NOT NULL,
    prenom VARCHAR(50),
    adresse VARCHAR(100),
    telephone VARCHAR(20),
    email VARCHAR(100)
);

CREATE TABLE Materiel (
    id_materiel SERIAL PRIMARY KEY,
    id_client INT NOT NULL REFERENCES Client(id_client),
    type_materiel VARCHAR(50) NOT NULL, 
    marque VARCHAR(50),
    modele VARCHAR(50)
);

CREATE TABLE TypeIntervention (
    id_type_intervention SERIAL PRIMARY KEY,
    libelle VARCHAR(50) NOT NULL,
    prix_horaire DECIMAL(7,2) NOT NULL
);

CREATE TABLE Intervention (
    id_intervention SERIAL PRIMARY KEY,
    id_client INT NOT NULL REFERENCES Client(id_client),
    id_materiel INT NOT NULL REFERENCES Materiel(id_materiel),
    id_type_intervention INT NOT NULL REFERENCES TypeIntervention(id_type_intervention),
    date_intervention DATE NOT NULL,
    duree_heures DECIMAL(4,2) NOT NULL,
    commentaire TEXT
);

CREATE TABLE Composant (
    id_composant SERIAL PRIMARY KEY,
    nom VARCHAR(50) NOT NULL,
    prix_unitaire DECIMAL(7,2) NOT NULL
);

CREATE TABLE VenteComposant (
    id_vente_composant SERIAL PRIMARY KEY,
    id_intervention INT NOT NULL REFERENCES Intervention(id_intervention),
    id_composant INT NOT NULL REFERENCES Composant(id_composant),
    quantite INT NOT NULL
);

-- =========================
-- 2. Exemples de contenu
-- =========================

INSERT INTO TypeProduit (libelle) VALUES
('Animal'), ('Légume'), ('Fruit');

INSERT INTO Produit (nom, id_type) VALUES
('Lapin', 1), ('Poulet', 1), ('Dinde', 1), ('Veau', 1), ('Cochon', 1),
('Chou', 2), ('Pomme de terre', 2), ('Carotte', 2),
('Fraise', 3), ('Poire', 3), ('Pomme', 3);

INSERT INTO Vente (date_vente, id_produit, quantite_kg, prix_kg) VALUES
('2025-05-01', 1, 5.2, 8.00),  
('2025-05-01', 6, 10.0, 2.00),
('2025-05-02', 9, 3.5, 6.00);  

INSERT INTO Client (nom, prenom, adresse, telephone, email) VALUES
('Dupont', 'Jean', '1 rue de la Paix', '0600000000', 'jean.dupont@email.com'),
('Martin', 'Sophie', '2 avenue des Lilas', '0611111111', 'sophie.martin@email.com');


INSERT INTO Materiel (id_client, type_materiel, marque, modele) VALUES
(1, 'PC', 'Dell', 'Inspiron 15'),
(2, 'Imprimante', 'HP', 'DeskJet 2700');


INSERT INTO TypeIntervention (libelle, prix_horaire) VALUES
('Dépannage simple', 30.00),
('Réparation complexe', 50.00);

INSERT INTO Intervention (id_client, id_materiel, id_type_intervention, date_intervention, duree_heures, commentaire) VALUES
(1, 1, 1, '2025-05-10', 1.5, 'Nettoyage virus'),
(2, 2, 2, '2025-05-12', 2.0, 'Remplacement tête impression');

INSERT INTO Composant (nom, prix_unitaire) VALUES
('Barrette RAM 8Go', 40.00),
('Tête impression HP', 35.00);


INSERT INTO VenteComposant (id_intervention, id_composant, quantite) VALUES
(2, 2, 1); 

-- =========================
-- 3. Requêtes exemples
-- =========================

SELECT tp.libelle AS type_produit, SUM(v.quantite_kg * v.prix_kg) AS total_vente
FROM Vente v
JOIN Produit p ON v.id_produit = p.id_produit
JOIN TypeProduit tp ON p.id_type = tp.id_type
WHERE EXTRACT(MONTH FROM v.date_vente) = 5 AND EXTRACT(YEAR FROM v.date_vente) = 2025
GROUP BY tp.libelle;

-- Récapitulatif mensuel par produit
SELECT p.nom AS produit, SUM(v.quantite_kg * v.prix_kg) AS total_vente
FROM Vente v
JOIN Produit p ON v.id_produit = p.id_produit
WHERE EXTRACT(MONTH FROM v.date_vente) = 5 AND EXTRACT(YEAR FROM v.date_vente) = 2025
GROUP BY p.nom;

-- =========================
-- 4. Explications MCD/MLD
-- =========================
-- Entités : TypeProduit, Produit, Vente
-- Associations : Un Produit appartient à un TypeProduit, une Vente concerne un Produit
-- Attributs : nom, libelle, quantite_kg, prix_kg, date_vente

-- =========================
-- Exercice 2 : Auto-entrepreneur informatique
-- =========================
-- 1. Création des tables

CREATE TABLE Client (
    id_client SERIAL PRIMARY KEY,
    nom VARCHAR(50) NOT NULL,
    prenom VARCHAR(50),
    adresse VARCHAR(100),
    telephone VARCHAR(20),
    email VARCHAR(100)
);

CREATE TABLE Materiel (
    id_materiel SERIAL PRIMARY KEY,
    id_client INT NOT NULL REFERENCES Client(id_client),
    type_materiel VARCHAR(50) NOT NULL, 
    marque VARCHAR(50),
    modele VARCHAR(50)
);

CREATE TABLE TypeIntervention (
    id_type_intervention SERIAL PRIMARY KEY,
    libelle VARCHAR(50) NOT NULL,
    prix_horaire DECIMAL(7,2) NOT NULL
);

CREATE TABLE Intervention (
    id_intervention SERIAL PRIMARY KEY,
    id_client INT NOT NULL REFERENCES Client(id_client),
    id_materiel INT NOT NULL REFERENCES Materiel(id_materiel),
    id_type_intervention INT NOT NULL REFERENCES TypeIntervention(id_type_intervention),
    date_intervention DATE NOT NULL,
    duree_heures DECIMAL(4,2) NOT NULL,
    commentaire TEXT
);

CREATE TABLE Composant (
    id_composant SERIAL PRIMARY KEY,
    nom VARCHAR(50) NOT NULL,
    prix_unitaire DECIMAL(7,2) NOT NULL
);

CREATE TABLE VenteComposant (
    id_vente_composant SERIAL PRIMARY KEY,
    id_intervention INT NOT NULL REFERENCES Intervention(id_intervention),
    id_composant INT NOT NULL REFERENCES Composant(id_composant),
    quantite INT NOT NULL
);

-- 2. Exemples de contenu

INSERT INTO Client (nom, prenom, adresse, telephone, email) VALUES
('Dupont', 'Jean', '1 rue de la Paix', '0600000000', 'jean.dupont@email.com'),
('Martin', 'Sophie', '2 avenue des Lilas', '0611111111', 'sophie.martin@email.com');

INSERT INTO Materiel (id_client, type_materiel, marque, modele) VALUES
(1, 'PC', 'Dell', 'Inspiron 15'),
(2, 'Imprimante', 'HP', 'DeskJet 2700');

INSERT INTO TypeIntervention (libelle, prix_horaire) VALUES
('Dépannage simple', 30.00),
('Réparation complexe', 50.00);

INSERT INTO Intervention (id_client, id_materiel, id_type_intervention, date_intervention, duree_heures, commentaire) VALUES
(1, 1, 1, '2025-05-10', 1.5, 'Nettoyage virus'),
(2, 2, 2, '2025-05-12', 2.0, 'Remplacement tête impression');

INSERT INTO Composant (nom, prix_unitaire) VALUES
('Barrette RAM 8Go', 40.00),
('Tête impression HP', 35.00);

INSERT INTO VenteComposant (id_intervention, id_composant, quantite) VALUES
(2, 2, 1);

-- 3. Explications MCD/MLD
-- Entités : Client, Materiel, TypeIntervention, Intervention, Composant, VenteComposant
-- Associations :
--   - Un Client possède plusieurs Matériels
--   - Une Intervention concerne un Client, un Matériel, un Type d’Intervention
--   - Une Intervention peut donner lieu à la vente de plusieurs Composants
-- Attributs : nom, prenom, adresse, type_materiel, marque, modele, libelle, prix_horaire, date_intervention, duree_heures, commentaire, prix_unitaire, quantite
