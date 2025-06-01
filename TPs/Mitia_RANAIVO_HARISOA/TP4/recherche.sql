SELECT * FROM employes;

SELECT nom, prenom FROM employes;
SELECT nom, prenom, salaire FROM employes WHERE salaire > 2000;

SELECT * FROM employes WHERE service = (
    SELECT id_service FROM services WHERE nom = 'commercial'
);

SELECT * FROM employes WHERE date_embauche > '2010-01-01';

SELECT * FROM employes ORDER BY salaire ASC;

SELECT * FROM employes ORDER BY nom ASC;

SELECT * FROM employes ORDER BY salaire DESC, nom ASC;

SELECT DISTINCT salaire FROM employes;

SELECT * FROM employes WHERE salaire BETWEEN 1500 AND 3000;

SELECT * FROM employes WHERE salaire IN (2000, 2500, 3000);

SELECT * FROM employes WHERE nom LIKE 'B%';

SELECT * FROM employes WHERE prenom LIKE '%a%';

SELECT AVG(salaire) AS salaire_moyen FROM employes;

SELECT service, COUNT(*) AS nb_employes FROM employes GROUP BY service;

SELECT MAX(salaire) AS salaire_max, MIN(salaire) AS salaire_min FROM employes;

SELECT employes.nom, services.nom AS service
FROM employes
JOIN services ON employes.service = services.id_service;

SELECT employes.nom AS Nom, employes.prenom AS Prénom, services.nom AS Service
FROM employes
JOIN services ON employes.service = services.id_service;

SELECT * FROM employes
WHERE salaire > (SELECT AVG(salaire) FROM employes);