USE Parc;

SET SQL_SAFE_UPDATES = 0;

SELECT * FROM Segment;
SELECT nLog, typeLog, prix FROM Logiciel;

UPDATE Segment SET etage = 0 WHERE indIP = "130.120.80";
UPDATE Segment SET etage = 1 WHERE indIP = "130.120.81";
UPDATE Segment SET etage = 2 WHERE indIP = "130.120.82";
UPDATE Segment SET etage = 3 WHERE indIP = "130.120.83";

UPDATE Logiciel SET prix = prix * 0.9 WHERE typeLog = 'PCNT';

SELECT * FROM Segment;
SELECT nLog, typeLog, prix FROM Logiciel;

SET SQL_SAFE_UPDATES = 1;
