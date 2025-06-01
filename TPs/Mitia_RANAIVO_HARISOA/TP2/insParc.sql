USE Parc;

INSERT INTO Segment(indIP, nomSegment)
VALUES ("130.120.80", "Brin RDC");
INSERT INTO Segment(indIP, nomSegment)
VALUES ("130.120.81", "Brin 1er étage");
INSERT INTO Segment(indIP, nomSegment)
VALUES ("130.120.82", "Brin 2e étage");
INSERT INTO Segment(indIP, nomSegment)
VALUES ("130.120.83", "Brin Extra");

INSERT INTO Salle (nSalle, nomSalle, nbPoste, indIP)
VALUES ("s01", "Salle 1", 3, "130.120.80");
INSERT INTO Salle (nSalle, nomSalle, nbPoste, indIP)
VALUES ("s02", "Salle 2", 2, "130.120.80");
INSERT INTO Salle (nSalle, nomSalle, nbPoste, indIP)
VALUES ("s03", "Salle 3", 2, "130.120.80");
INSERT INTO Salle (nSalle, nomSalle, nbPoste, indIP)
VALUES ("s11", "Salle 11", 2, "130.120.81");
INSERT INTO Salle (nSalle, nomSalle, nbPoste, indIP)
VALUES ("s12", "Salle 12", 1, "130.120.81");
INSERT INTO Salle (nSalle, nomSalle, nbPoste, indIP)
VALUES ("s21", "Salle 21", 2, "130.120.82");
INSERT INTO Salle (nSalle, nomSalle, nbPoste, indIP)
VALUES ("s22", "Salle 22", 0, "130.120.83");
INSERT INTO Salle (nSalle, nomSalle, nbPoste, indIP)
VALUES ("s23", "Salle 23", 0, "130.120.83");

INSERT INTO Poste (nPoste, nomPoste, indIP, ad, typePoste, nSalle)
Values ("p1", "Poste 1", "130.120.80", 1, "TX", "s01");
INSERT INTO Poste (nPoste, nomPoste, indIP, ad, typePoste, nSalle)
Values ("p2", "Poste 2", "130.120.80", 2, "UNIX", "s01");
INSERT INTO Poste (nPoste, nomPoste, indIP, ad, typePoste, nSalle)
Values ("p3", "Poste 3", "130.120.80", 3, "TX", "s01");
INSERT INTO Poste (nPoste, nomPoste, indIP, ad, typePoste, nSalle)
Values ("p4", "Poste 4", "130.120.80", 4, "PCWS", "s02");
INSERT INTO Poste (nPoste, nomPoste, indIP, ad, typePoste, nSalle)
Values ("p5", "Poste 5", "130.120.80", 5, "PCWS", "s02");
INSERT INTO Poste (nPoste, nomPoste, indIP, ad, typePoste, nSalle)
Values ("p6", "Poste 6", "130.120.80", 6, "UNIX", "s03");
INSERT INTO Poste (nPoste, nomPoste, indIP, ad, typePoste, nSalle)
Values ("p7", "Poste 7", "130.120.80", 7, "TX", "s03");
INSERT INTO Poste (nPoste, nomPoste, indIP, ad, typePoste, nSalle)
Values ("p8", "Poste 8", "130.120.81", 1, "UNIX", "s11");
INSERT INTO Poste (nPoste, nomPoste, indIP, ad, typePoste, nSalle)
Values ("p9", "Poste 9", "130.120.81", 2, "TX", "s11");
INSERT INTO Poste (nPoste, nomPoste, indIP, ad, typePoste, nSalle)
Values ("p10", "Poste 10", "130.120.81", 3, "UNIX", "s12");
INSERT INTO Poste (nPoste, nomPoste, indIP, ad, typePoste, nSalle)
Values ("p11", "Poste 11", "130.120.82", 1, "PCNT", "s21");
INSERT INTO Poste (nPoste, nomPoste, indIP, ad, typePoste, nSalle)
Values ("p12", "Poste 12", "130.120.82", 2, "PCWS", "s21");

INSERT INTO Logiciel (nLog, nomLog, dateAch, version, typeLog, prix)
VALUES ("log1", "Oracle 6", "1995-05-13", "6.2", "UNIX", 3000);
INSERT INTO Logiciel (nLog, nomLog, dateAch, version, typeLog, prix)
VALUES ("log2", "Oracle 8", "1999-09-15", "8i", "UNIX", 5600);
INSERT INTO Logiciel (nLog, nomLog, dateAch, version, typeLog, prix)
VALUES ("log3", "SQL Server", "1998-04-12", "7", "PCNT", 2700);
INSERT INTO Logiciel (nLog, nomLog, dateAch, version, typeLog, prix)
VALUES ("log4", "Front Page", "1997-06-03", "5", "PCWS", 500);
INSERT INTO Logiciel (nLog, nomLog, dateAch, version, typeLog, prix)
VALUES ("log5", "WinDev", "1997-05-12", "5", "PCWS", 750);
INSERT INTO Logiciel (nLog, nomLog, version, typeLog, prix)
VALUES ("log6", "SQL*Net", "2.0", "UNIX", 500);
INSERT INTO Logiciel (nLog, nomLog, dateAch, version, typeLog, prix)
VALUES ("log7", "I. I. S.", "2002-04-12", "2", "PCNT", 810);
INSERT INTO Logiciel (nLog, nomLog, dateAch, version, typeLog, prix)
VALUES ("log8", "DreamWeaver", "2003-09-21", "2.0", "BeOS", 1400);

INSERT INTO Types (typeLP, nomType)
VALUES ("TX", "Terminal X-Window");
INSERT INTO Types (typeLP, nomType)
VALUES ("UNIX", "Système Unix");
INSERT INTO Types (typeLP, nomType)
VALUES ("PCNT", "PC Windows NT");
INSERT INTO Types (typeLP, nomType)
VALUES ("PCWS", "PC Windows");
INSERT INTO Types (typeLP, nomType)
VALUES ("NC", "Network Computer");
INSERT INTO Types (typeLP, nomType) VALUES ("BeOS", "Système Be");

INSERT INTO Installer (NPOSTE, NLOG, DATEINS) VALUES 
('p2', 'log1', '2003-05-15'),
('p2', 'log2', '2003-09-17'),
('p6', 'log6', '2003-05-20'),
('p6', 'log5', '2003-05-20'),
('p8', 'log6', '2003-05-19'),
('p8', 'log7', '2003-05-20'),
('p11', 'log4', '2003-04-20'),
('p12', 'log3', '2003-04-20'),
('p11', 'log7', '2003-04-10'),
('p7', 'log7', '2002-04-01');



