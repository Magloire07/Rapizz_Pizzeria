DROP PROCEDURE IF EXISTS Exo6_1;
DELIMITER //
CREATE PROCEDURE Exo6_1()
BEGIN
  DECLARE v_numSalle VARCHAR(10);
  DECLARE v_numPoste VARCHAR(10);
  DECLARE v_nomLog   VARCHAR(100);
  DECLARE v_dateInst DATETIME;

  SELECT p.nSalle
    INTO v_numSalle
    FROM Installer i
    JOIN Poste p ON i.nPoste = p.nPoste
    ORDER BY i.dateIns DESC
    LIMIT 1;

  SELECT CONCAT(
    '+----------------------------------------+\n',
    '| Resultat1 exo 1                        |\n',
    '+----------------------------------------+\n',
    '| Derniere installation en salle : ', v_numSalle, ' |\n',
    '+----------------------------------------+'
  ) AS Resultat1;

  SELECT i.nPoste, l.nomLog, i.dateIns
    INTO v_numPoste, v_nomLog, v_dateInst
    FROM Installer i
    JOIN Logiciel l ON i.nLog = l.nLog
    ORDER BY i.dateIns DESC
    LIMIT 1;

  SELECT CONCAT(
    '+--------------------------------------------------------------------+\n',
    '| Resultat2 exo 1                                                    |\n',
    '+--------------------------------------------------------------------+\n',
    '| Poste : ', v_numPoste,
    ' Logiciel : ', v_nomLog,
    ' en date du ', DATE_FORMAT(v_dateInst, '%Y-%m-%d %H:%i:%s'), ' |\n',
    '+--------------------------------------------------------------------+'
  ) AS Resultat2;
END //
DELIMITER ;