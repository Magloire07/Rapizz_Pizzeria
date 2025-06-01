ALTER TABLE Segment ADD nbSalle TINYINT(2) DEFAULT 0;
ALTER TABLE Segment ADD nbPoste TINYINT(2) DEFAULT 0;
ALTER TABLE Logiciel ADD nbInstall TINYINT(2) DEFAULT 0;
ALTER TABLE Poste ADD nbLog TINYINT(2) DEFAULT 0;

ALTER TABLE Salle MODIFY COLUMN nomSalle VARCHAR(30);
ALTER TABLE Segment MODIFY COLUMN nomSegment VARCHAR(15);


ALTER TABLE Installer ADD CONSTRAINT unique_poste_logiciel UNIQUE (nPoste, nLog);

ALTER TABLE Installer ADD CONSTRAINT fk_installer_poste FOREIGN KEY (nPoste) REFERENCES Poste(nPoste);
ALTER TABLE Installer ADD CONSTRAINT fk_installer_logiciel FOREIGN KEY (nLog) REFERENCES Logiciel(nLog);


SELECT nSalle, indIP FROM Salle WHERE indIP NOT IN (SELECT indIP FROM Segment);
SELECT nLog, typeLog FROM Logiciel WHERE typeLog NOT IN (SELECT typeLP FROM Types);

DELETE FROM Salle WHERE indIP NOT IN (SELECT indIP FROM Segment);

INSERT INTO Types (typeLP, nomType) VALUES ('BeOS', 'système Be');

ALTER TABLE Salle ADD CONSTRAINT fk_salle_segment FOREIGN KEY (indIP) REFERENCES Segment(indIP);
ALTER TABLE Logiciel ADD CONSTRAINT fk_logiciel_type FOREIGN KEY (typeLog) REFERENCES Types(typeLP);

DESCRIBE Salle;
DESCRIBE Segment;
DESCRIBE Logiciel;
DESCRIBE Types;

SELECT * FROM Salle;
SELECT * FROM Segment;
SELECT * FROM Logiciel;
SELECT * FROM Types;

SELECT nSalle, indIP FROM Salle WHERE indIP NOT IN (SELECT indIP FROM Segment);
SELECT nLog, typeLog FROM Logiciel WHERE typeLog NOT IN (SELECT typeLP FROM Types);
