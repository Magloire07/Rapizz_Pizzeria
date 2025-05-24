CREATE TABLE IF NOT EXISTS Client (
    idClient INT PRIMARY KEY AUTO_INCREMENT,
    nom VARCHAR(50),
    prenom VARCHAR(50),
    adresse VARCHAR(255),
    solde DOUBLE,
    cagnote INT
);

CREATE TABLE IF NOT EXISTS Pizza (
    idPizza INT PRIMARY KEY AUTO_INCREMENT,
    nom VARCHAR(100),
    prix DOUBLE,
    taille VARCHAR(10),
    iconPath VARCHAR(255)
);

CREATE TABLE IF NOT EXISTS Commande (
    idCommande INT PRIMARY KEY AUTO_INCREMENT,
    idClient INT,
    idPizza INT,
    dateCommande DATE,
    taille VARCHAR(10),
    FOREIGN KEY (idClient) REFERENCES Client(idClient),
    FOREIGN KEY (idPizza) REFERENCES Pizza(idPizza)
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

CREATE TABLE IF NOT EXISTS Ingredient (
    idIngredient INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(100),
    quantity INT,
    price DOUBLE
);

CREATE TABLE IF NOT EXISTS Composer (
    idPizza INT,
    idIngredient INT,
    PRIMARY KEY (idPizza, idIngredient),
    FOREIGN KEY (idPizza) REFERENCES Pizza(idPizza),
    FOREIGN KEY (idIngredient) REFERENCES Ingredient(idIngredient)
);
