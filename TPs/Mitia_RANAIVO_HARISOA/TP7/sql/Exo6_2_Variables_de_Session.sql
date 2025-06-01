DROP PROCEDURE IF EXISTS Exo6_2;
DELIMITER //
CREATE PROCEDURE Exo6_2()
BEGIN
  DECLARE v_countPostes INT DEFAULT 0;
  DECLARE v_countInst   INT DEFAULT 0;

  SELECT COUNT(*) 
    INTO v_countPostes
    FROM Poste
    WHERE nSalle = @numSalle;

  SELECT COUNT(*)
    INTO v_countInst
    FROM Installer i
    JOIN Poste p ON i.nPoste = p.nPoste
    WHERE p.typePoste = @typePoste;

  SELECT CONCAT(
    '---------------------------------------------------\n',
    '| Resultat exo2                                   |\n',
    '---------------------------------------------------\n',
    '| ',
    v_countPostes, ' poste(s) installé(s) en salle ', @numSalle,
    ', ', v_countInst, ' installation(s) de type ', @typePoste, ' |\n',
    '---------------------------------------------------'
  ) AS ResultatExo2;
END //
DELIMITER ;