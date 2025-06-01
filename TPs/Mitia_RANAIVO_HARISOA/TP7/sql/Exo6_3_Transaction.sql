DROP PROCEDURE IF EXISTS Exo6_3;
DELIMITER //
CREATE PROCEDURE Exo6_3()
BEGIN
  DECLARE v_dateAchat    DATETIME;
  DECLARE v_dateInstall  DATETIME;
  DECLARE v_resultSleep  INT;

  START TRANSACTION;

  INSERT INTO Logiciel (
        nLog,
        nomLog,
        version,
        typeLog,
        Prix,
        dateAch
      )
  VALUES (
        @numLog,
        @nomLog,
        @versionLog,
        @typeLog,
        @prixLog,
        NOW()
      );
  SET v_dateAchat = NOW();

  SELECT 'Logiciel insere dans la base' AS message1;
  SELECT CONCAT('Date achat : ', DATE_FORMAT(v_dateAchat, '%Y-%m-%d %H:%i:%s')) AS message2;

  SELECT SLEEP(5) INTO v_resultSleep;

  INSERT INTO Installer (
        nPoste,
        nLog,
        dateIns
      )
  VALUES (
        @posteCode,
        @numLog,
        NOW()
      );
  SET v_dateInstall = NOW();

  UPDATE Installer
    SET delai = TIMEDIFF(v_dateInstall, v_dateAchat)
    WHERE nPoste = @posteCode
      AND nLog = @numLog
      AND dateIns = v_dateInstall;

  SELECT CONCAT('Date installation : ', DATE_FORMAT(v_dateInstall, '%Y-%m-%d %H:%i:%s')) AS message3;
  SELECT 'Logiciel installe sur le poste' AS message4;

  COMMIT;
END //
DELIMITER ;