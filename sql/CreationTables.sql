CREATE TABLE CLIENT (
    numero_abonne INT PRIMARY KEY,
    nom VARCHAR(50),
    prenom VARCHAR(50),
    adresse VARCHAR(255),
    solde DECIMAL(6,2),
    nombre_commandes INT
);

CREATE TABLE PIZZA (
    id_pizza INT PRIMARY KEY,
    nom VARCHAR(100),
    prix_base DECIMAL(5,2)
);

CREATE TABLE INGREDIENT (
    id_ingredient INT PRIMARY KEY,
    nom VARCHAR(100),
    allergene BOOLEAN
);

CREATE TABLE VEHICULE (
    id_vehicule INT PRIMARY KEY,
    type VARCHAR(50),
    immatriculation VARCHAR(15),
    nombre_livraisons INT,
    duree INT
);

CREATE TABLE LIVREUR (
    id_livreur INT PRIMARY KEY,
    nom VARCHAR(50),
    prenom VARCHAR(50),
    nombre_livraisons INT,
    duree_livraisons INT
);

CREATE TABLE COMMANDE (
    num_commande INT PRIMARY KEY,
    date_commande DATE,
    montant DECIMAL(6,2),
    numero_abonne INT,
    FOREIGN KEY (numero_abonne) REFERENCES CLIENT(numero_abonne)
);

CREATE TABLE LIVRAISON (
    id_livraison INT PRIMARY KEY,
    date_livraison DATE,
    duree_minutes INT,
    adresse_livraison VARCHAR(255)
);

CREATE TABLE COMMANDER (
    num_commande INT,
    id_pizza INT,
    taille_pizza VARCHAR(20),
    PRIMARY KEY (num_commande, id_pizza),
    FOREIGN KEY (num_commande) REFERENCES COMMANDE(num_commande),
    FOREIGN KEY (id_pizza) REFERENCES PIZZA(id_pizza)
);

CREATE TABLE COMPOSER (
    id_pizza INT,
    id_ingredient INT,
    PRIMARY KEY (id_pizza, id_ingredient),
    FOREIGN KEY (id_pizza) REFERENCES PIZZA(id_pizza),
    FOREIGN KEY (id_ingredient) REFERENCES INGREDIENT(id_ingredient)
);


CREATE TABLE LIVRER (
    num_commande INT,
    id_livraison INT,
    PRIMARY KEY (num_commande, id_livraison),
    FOREIGN KEY (num_commande) REFERENCES COMMANDE(num_commande),
    FOREIGN KEY (id_livraison) REFERENCES LIVRAISON(id_livraison)
);

CREATE TABLE IF NOT EXISTS livreurs (
    id_livreur INT PRIMARY KEY AUTO_INCREMENT,
    nom VARCHAR(50),
    prenom VARCHAR(50),
    nombre_livraisons INT DEFAULT 0,
    duree_livraisons INT DEFAULT 0,
    id_vehicule INT,
    FOREIGN KEY (id_vehicule) REFERENCES vehicules(id_vehicule)
);

CREATE TABLE IF NOT EXISTS vehicules (
    id_vehicule INT PRIMARY KEY AUTO_INCREMENT,
    marque VARCHAR(50),
    modele VARCHAR(50),
    type VARCHAR(30),
    disponible BOOLEAN DEFAULT TRUE
);

CREATE TABLE IF NOT EXISTS pizzaiolos (
    id_pizzaiolo INT PRIMARY KEY AUTO_INCREMENT,
    nom VARCHAR(50),
    prenom VARCHAR(50),
    nombre_pizzas INT DEFAULT 0
);
