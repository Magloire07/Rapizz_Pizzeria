
-- Voir toutes les commandes d'un client
SELECT * FROM COMMANDE WHERE numero_abonne = 1;

-- Voir la composition d'une pizza
SELECT I.nom, I.allergene
FROM INGREDIENT I
JOIN COMPOSER C ON I.id_ingredient = C.id_ingredient
WHERE C.id_pizza = 101;


-- Voir l'historique de livraison d'un livreur
SELECT L.*, V.type, V.immatriculation
FROM LIVRAISON L
JOIN LIVRER LR ON L.id_livraison = LR.id_livraison
JOIN COMMANDE C ON C.num_commande = LR.num_commande
JOIN LIVREUR LV ON LV.id_livreur = 401
JOIN UTILISER U ON LV.id_livreur = U.id_livreur
JOIN VEHICULE V ON V.id_vehicule = U.id_vehicule;

-- Voir total des commandes d'un client 
SELECT SUM(montant) AS total_depense
FROM COMMANDE
WHERE numero_abonne = 1;
