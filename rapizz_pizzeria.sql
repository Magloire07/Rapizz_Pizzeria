-- MySQL dump 10.13  Distrib 8.0.35, for Linux (x86_64)
--
-- Host: localhost    Database: rapizz_pizzeria
-- ------------------------------------------------------
-- Server version	8.0.35

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8mb4 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `Client`
--

DROP TABLE IF EXISTS `Client`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `Client` (
  `idClient` int NOT NULL AUTO_INCREMENT,
  `nom` varchar(50) DEFAULT NULL,
  `prenom` varchar(50) DEFAULT NULL,
  `adresse` varchar(255) DEFAULT NULL,
  `solde` double DEFAULT NULL,
  `cagnote` int DEFAULT NULL,
  PRIMARY KEY (`idClient`)
) ENGINE=InnoDB AUTO_INCREMENT=200 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Client`
--

LOCK TABLES `Client` WRITE;
/*!40000 ALTER TABLE `Client` DISABLE KEYS */;
INSERT INTO `Client` VALUES (1,'Client1','Prenom1','Adresse 1',110,0),(2,'Client2','Prenom2','Adresse 2',120,0),(3,'Client3','Prenom3','Adresse 3',130,0),(4,'Client4','Prenom4','Adresse 4',140,0),(5,'Client5','Prenom5','Adresse 5',150,0),(6,'Client268','Prenom321','Adresse84',215,1),(7,'Client289','Prenom702','Adresse33',509,5),(8,'Client305','Prenom821','Adresse5',381,5),(9,'Client426','Prenom589','Adresse42',446,5),(10,'Client79','Prenom292','Adresse7',440,7),(11,'Client70','Prenom827','Adresse37',497,7),(12,'Client722','Prenom25','Adresse5',332,8),(13,'Client71','Prenom557','Adresse18',155,4),(14,'Client601','Prenom499','Adresse30',187,7),(15,'Client157','Prenom504','Adresse20',172,5),(16,'Client785','Prenom488','Adresse56',300,3),(17,'Client805','Prenom406','Adresse40',597,3),(18,'Client776','Prenom766','Adresse45',314,2),(19,'Client141','Prenom670','Adresse5',466,5),(20,'Client98','Prenom966','Adresse20',327,2),(21,'Client848','Prenom6','Adresse45',381,0),(22,'Client672','Prenom776','Adresse36',471,7),(23,'Client412','Prenom767','Adresse40',507,4),(24,'Client553','Prenom749','Adresse96',355,2),(25,'Client565','Prenom637','Adresse87',311,6),(26,'Client906','Prenom56','Adresse51',316,3),(27,'Client187','Prenom858','Adresse18',209,0),(28,'Client156','Prenom277','Adresse10',511,3),(29,'Client974','Prenom369','Adresse64',303,7),(30,'Client524','Prenom280','Adresse48',132,4),(31,'Client314','Prenom226','Adresse13',402,8),(32,'Client645','Prenom259','Adresse39',393,9),(33,'Client260','Prenom449','Adresse1',374,4),(34,'Client715','Prenom903','Adresse91',562,6),(35,'Client720','Prenom377','Adresse0',520,8),(36,'Client925','Prenom780','Adresse3',539,9),(37,'Client565','Prenom670','Adresse36',492,8),(38,'Client730','Prenom681','Adresse39',209,0),(39,'Client379','Prenom702','Adresse53',218,3),(40,'Client572','Prenom378','Adresse65',393,0),(41,'Client554','Prenom543','Adresse66',597,3),(42,'Client560','Prenom614','Adresse60',289,1),(43,'Client279','Prenom802','Adresse39',547,5),(44,'Client22','Prenom474','Adresse9',524,1),(45,'Client31','Prenom462','Adresse33',135,0),(46,'Client30','Prenom803','Adresse91',315,3),(47,'Client902','Prenom179','Adresse10',252,8),(48,'Client176','Prenom516','Adresse56',326,6),(49,'Client627','Prenom691','Adresse53',474,3),(50,'Client278','Prenom55','Adresse84',279,2),(51,'Client122','Prenom848','Adresse19',341,5),(52,'Client296','Prenom644','Adresse31',109,9),(53,'Client707','Prenom226','Adresse89',161,0),(54,'Client9','Prenom807','Adresse61',319,7),(55,'Client90','Prenom387','Adresse36',231,9),(56,'Client409','Prenom473','Adresse18',154,1),(57,'Client527','Prenom223','Adresse97',394,8),(58,'Client186','Prenom542','Adresse70',598,7),(59,'Client367','Prenom541','Adresse74',546,9),(60,'Client406','Prenom284','Adresse77',537,9),(61,'Client170','Prenom425','Adresse61',388,5),(62,'Client97','Prenom280','Adresse75',463,4),(63,'Client438','Prenom121','Adresse38',386,6),(64,'Client324','Prenom144','Adresse50',273,3),(65,'Client312','Prenom644','Adresse93',348,5),(66,'Client463','Prenom923','Adresse8',575,8),(67,'Client938','Prenom951','Adresse11',260,8),(68,'Client550','Prenom419','Adresse68',178,1),(69,'Client136','Prenom193','Adresse44',267,1),(70,'Client829','Prenom827','Adresse39',244,8),(71,'Client768','Prenom859','Adresse57',273,6),(72,'Client357','Prenom964','Adresse52',522,8),(73,'Client826','Prenom889','Adresse20',471,6),(74,'Client334','Prenom881','Adresse53',187,2),(75,'Client958','Prenom410','Adresse31',144,1),(76,'Client649','Prenom760','Adresse88',239,4),(77,'Client847','Prenom308','Adresse0',150,9),(78,'Client617','Prenom115','Adresse7',113,0),(79,'Client472','Prenom689','Adresse52',320,0),(80,'Client337','Prenom17','Adresse14',434,0),(81,'Client625','Prenom750','Adresse26',332,9),(82,'Client752','Prenom543','Adresse60',314,3),(83,'Client263','Prenom967','Adresse69',594,6),(84,'Client806','Prenom924','Adresse43',468,9),(85,'Client923','Prenom432','Adresse54',332,5),(86,'Client738','Prenom406','Adresse7',360,3),(87,'Client608','Prenom781','Adresse3',557,4),(88,'Client954','Prenom138','Adresse35',577,8),(89,'Client197','Prenom564','Adresse26',296,9),(90,'Client128','Prenom679','Adresse82',553,3),(91,'Client405','Prenom795','Adresse65',328,6),(92,'Client128','Prenom748','Adresse53',110,1),(93,'Client963','Prenom727','Adresse2',411,1),(94,'Client993','Prenom623','Adresse23',351,5),(95,'Client654','Prenom821','Adresse42',158,8),(96,'Client749','Prenom139','Adresse17',509,8),(97,'Client811','Prenom757','Adresse3',478,9),(98,'Client667','Prenom966','Adresse25',137,5),(99,'Client666','Prenom308','Adresse76',366,5),(100,'Client27','Prenom169','Adresse30',260,1),(101,'Client385','Prenom785','Adresse54',569,6),(102,'Client109','Prenom615','Adresse17',171,0),(103,'Client526','Prenom518','Adresse40',373,6),(104,'Client651','Prenom0','Adresse10',251,3),(105,'Client509','Prenom450','Adresse94',110,1),(106,'Client207','Prenom228','Adresse21',328,5),(107,'Client233','Prenom541','Adresse96',143,2),(108,'Client370','Prenom822','Adresse20',269,0),(109,'Client289','Prenom444','Adresse8',499,4),(110,'Client357','Prenom780','Adresse66',262,6),(111,'Client443','Prenom241','Adresse17',189,8),(112,'Client634','Prenom845','Adresse9',144,3),(113,'Client443','Prenom272','Adresse96',377,4),(114,'Client35','Prenom512','Adresse55',203,3),(115,'Client940','Prenom599','Adresse75',305,2),(116,'Client977','Prenom128','Adresse70',123,5),(117,'Client698','Prenom500','Adresse26',574,2),(118,'Client924','Prenom322','Adresse21',199,3),(119,'Client708','Prenom201','Adresse89',568,3),(120,'Client610','Prenom469','Adresse39',194,0),(121,'Client658','Prenom880','Adresse3',302,4),(122,'Client445','Prenom943','Adresse87',242,8),(123,'Client619','Prenom882','Adresse19',280,5),(124,'Client672','Prenom669','Adresse69',589,5),(125,'Client807','Prenom562','Adresse28',307,2),(126,'Client581','Prenom874','Adresse62',187,0),(127,'Client133','Prenom153','Adresse52',196,6),(128,'Client56','Prenom371','Adresse17',114,1),(129,'Client685','Prenom793','Adresse22',123,0),(130,'Client567','Prenom900','Adresse79',392,2),(131,'Client997','Prenom923','Adresse40',374,3),(132,'Client426','Prenom907','Adresse10',390,1),(133,'Client915','Prenom803','Adresse37',425,4),(134,'Client399','Prenom980','Adresse64',570,7),(135,'Client909','Prenom190','Adresse34',121,3),(136,'Client148','Prenom803','Adresse38',465,2),(137,'Client635','Prenom472','Adresse49',202,7),(138,'Client660','Prenom300','Adresse78',241,5),(139,'Client750','Prenom886','Adresse31',209,5),(140,'Client503','Prenom490','Adresse7',449,6),(141,'Client54','Prenom33','Adresse77',154,3),(142,'Client455','Prenom578','Adresse17',554,0),(143,'Client652','Prenom304','Adresse33',518,7),(144,'Client133','Prenom8','Adresse41',392,4),(145,'Client537','Prenom16','Adresse98',561,5),(146,'Client912','Prenom838','Adresse7',233,6),(147,'Client252','Prenom732','Adresse78',293,4),(148,'Client900','Prenom77','Adresse20',592,1),(149,'Client99','Prenom375','Adresse17',377,9),(150,'Client569','Prenom108','Adresse65',320,7),(151,'Client866','Prenom774','Adresse53',490,8),(152,'Client661','Prenom363','Adresse97',398,7),(153,'Client126','Prenom236','Adresse42',357,7),(154,'Client842','Prenom734','Adresse33',399,0),(155,'Client742','Prenom483','Adresse47',597,6),(156,'Client200','Prenom765','Adresse96',399,2),(157,'Client910','Prenom473','Adresse11',367,3),(158,'Client647','Prenom474','Adresse98',371,7),(159,'Client658','Prenom637','Adresse79',581,8),(160,'Client701','Prenom225','Adresse68',321,5),(161,'Client917','Prenom883','Adresse35',226,1),(162,'Client376','Prenom986','Adresse76',550,8),(163,'Client933','Prenom733','Adresse36',556,9),(164,'Client458','Prenom357','Adresse11',511,5),(165,'Client380','Prenom82','Adresse52',530,9),(166,'Client301','Prenom144','Adresse13',338,1),(167,'Client128','Prenom840','Adresse23',436,0),(168,'Client682','Prenom347','Adresse59',535,8),(169,'Client769','Prenom413','Adresse51',531,2),(170,'Client5','Prenom107','Adresse71',155,8),(171,'Client477','Prenom382','Adresse64',328,5),(172,'Client408','Prenom154','Adresse12',505,3),(173,'Client99','Prenom156','Adresse12',549,8),(174,'Client17','Prenom231','Adresse85',361,8),(175,'Client388','Prenom409','Adresse52',198,9),(176,'Client244','Prenom381','Adresse1',336,3),(177,'Client204','Prenom150','Adresse95',362,6),(178,'Client20','Prenom467','Adresse56',206,9),(179,'Client911','Prenom251','Adresse19',251,1),(180,'Client529','Prenom481','Adresse94',191,7),(181,'Client502','Prenom325','Adresse55',469,8),(182,'Client51','Prenom590','Adresse8',109,1),(183,'Client600','Prenom666','Adresse6',304,2),(184,'Client900','Prenom486','Adresse54',110,0),(185,'Client197','Prenom600','Adresse56',328,7),(186,'Client527','Prenom794','Adresse11',461,5),(187,'Client923','Prenom827','Adresse22',358,0),(188,'Client592','Prenom988','Adresse98',309,4),(189,'Client42','Prenom506','Adresse4',424,8),(190,'Client326','Prenom92','Adresse78',506,1),(191,'Client252','Prenom236','Adresse98',420,0),(192,'Client926','Prenom876','Adresse49',380,4),(193,'Client495','Prenom192','Adresse52',324,1),(194,'Client66','Prenom895','Adresse7',313,2),(195,'Client623','Prenom477','Adresse44',569,3),(196,'Client525','Prenom486','Adresse84',445,9),(197,'Client294','Prenom613','Adresse63',435,6),(198,'Client596','Prenom369','Adresse27',363,3),(199,'Client111','Prenom512','Adresse35',206,6);
/*!40000 ALTER TABLE `Client` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Commande`
--

DROP TABLE IF EXISTS `Commande`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `Commande` (
  `idCommande` int NOT NULL AUTO_INCREMENT,
  `idClient` int DEFAULT NULL,
  `idPizza` int DEFAULT NULL,
  `dateCommande` date DEFAULT NULL,
  `taille` varchar(10) DEFAULT NULL,
  PRIMARY KEY (`idCommande`),
  KEY `idClient` (`idClient`),
  KEY `idPizza` (`idPizza`),
  CONSTRAINT `Commande_ibfk_1` FOREIGN KEY (`idClient`) REFERENCES `Client` (`idClient`),
  CONSTRAINT `Commande_ibfk_2` FOREIGN KEY (`idPizza`) REFERENCES `Pizza` (`idPizza`)
) ENGINE=InnoDB AUTO_INCREMENT=357 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Commande`
--

LOCK TABLES `Commande` WRITE;
/*!40000 ALTER TABLE `Commande` DISABLE KEYS */;
/*!40000 ALTER TABLE `Commande` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Composer`
--

DROP TABLE IF EXISTS `Composer`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `Composer` (
  `idPizza` int NOT NULL,
  `idIngredient` int NOT NULL,
  PRIMARY KEY (`idPizza`,`idIngredient`),
  KEY `idIngredient` (`idIngredient`),
  CONSTRAINT `Composer_ibfk_1` FOREIGN KEY (`idPizza`) REFERENCES `Pizza` (`idPizza`),
  CONSTRAINT `Composer_ibfk_2` FOREIGN KEY (`idIngredient`) REFERENCES `Ingredient` (`idIngredient`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Composer`
--

LOCK TABLES `Composer` WRITE;
/*!40000 ALTER TABLE `Composer` DISABLE KEYS */;
INSERT INTO `Composer` VALUES (571,1),(572,1),(573,1),(574,1),(571,2),(572,2),(573,2),(574,2),(574,3),(572,4),(573,5),(574,6);
/*!40000 ALTER TABLE `Composer` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Ingredient`
--

DROP TABLE IF EXISTS `Ingredient`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `Ingredient` (
  `idIngredient` int NOT NULL AUTO_INCREMENT,
  `name` varchar(100) DEFAULT NULL,
  `quantity` int DEFAULT NULL,
  `price` double DEFAULT NULL,
  PRIMARY KEY (`idIngredient`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Ingredient`
--

LOCK TABLES `Ingredient` WRITE;
/*!40000 ALTER TABLE `Ingredient` DISABLE KEYS */;
INSERT INTO `Ingredient` VALUES (1,'Tomate',113,1),(2,'Mozzarella',12,1.5),(3,'Jambon',26,2),(4,'4 Fromages',18,2.5),(5,'Salami',6,2),(6,'Parma',122,2.8);
/*!40000 ALTER TABLE `Ingredient` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Pizza`
--

DROP TABLE IF EXISTS `Pizza`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `Pizza` (
  `idPizza` int NOT NULL AUTO_INCREMENT,
  `nom` varchar(100) DEFAULT NULL,
  `prix` double DEFAULT NULL,
  `taille` varchar(10) DEFAULT NULL,
  `iconPath` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`idPizza`)
) ENGINE=InnoDB AUTO_INCREMENT=575 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Pizza`
--

LOCK TABLES `Pizza` WRITE;
/*!40000 ALTER TABLE `Pizza` DISABLE KEYS */;
INSERT INTO `Pizza` VALUES (571,'Margherita',8,'M','Margherita.jpeg'),(572,'4 Fromages',10,'M','4Fromages.jpg'),(573,'Diavola',11,'M','Diavola.jpeg'),(574,'Parma',12,'M','Parma.jpeg');
/*!40000 ALTER TABLE `Pizza` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `livreurs`
--

DROP TABLE IF EXISTS `livreurs`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `livreurs` (
  `id_livreur` int NOT NULL AUTO_INCREMENT,
  `nom` varchar(50) DEFAULT NULL,
  `prenom` varchar(50) DEFAULT NULL,
  `nombre_livraisons` int DEFAULT '0',
  `duree_livraisons` int DEFAULT '0',
  `id_vehicule` int DEFAULT NULL,
  PRIMARY KEY (`id_livreur`),
  KEY `id_vehicule` (`id_vehicule`),
  CONSTRAINT `livreurs_ibfk_1` FOREIGN KEY (`id_vehicule`) REFERENCES `vehicules` (`id_vehicule`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `livreurs`
--

LOCK TABLES `livreurs` WRITE;
/*!40000 ALTER TABLE `livreurs` DISABLE KEYS */;
INSERT INTO `livreurs` VALUES (3,'Paul','Martin',0,0,1),(5,'Tibu','Livron',0,0,1);
/*!40000 ALTER TABLE `livreurs` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `pizzaiolos`
--

DROP TABLE IF EXISTS `pizzaiolos`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `pizzaiolos` (
  `id_pizzaiolo` int NOT NULL AUTO_INCREMENT,
  `nom` varchar(50) DEFAULT NULL,
  `prenom` varchar(50) DEFAULT NULL,
  `nombre_pizzas` int DEFAULT '0',
  PRIMARY KEY (`id_pizzaiolo`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `pizzaiolos`
--

LOCK TABLES `pizzaiolos` WRITE;
/*!40000 ALTER TABLE `pizzaiolos` DISABLE KEYS */;
INSERT INTO `pizzaiolos` VALUES (1,'Mario','Rossi',0),(2,'Léo','Léo',0),(3,'Lucas','Merlin',0);
/*!40000 ALTER TABLE `pizzaiolos` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `vehicules`
--

DROP TABLE IF EXISTS `vehicules`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `vehicules` (
  `id_vehicule` int NOT NULL AUTO_INCREMENT,
  `marque` varchar(50) DEFAULT NULL,
  `modele` varchar(50) DEFAULT NULL,
  `type` varchar(30) DEFAULT NULL,
  `disponible` tinyint(1) DEFAULT '1',
  PRIMARY KEY (`id_vehicule`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `vehicules`
--

LOCK TABLES `vehicules` WRITE;
/*!40000 ALTER TABLE `vehicules` DISABLE KEYS */;
INSERT INTO `vehicules` VALUES (1,'Peugeot','208','Scooter',1);
/*!40000 ALTER TABLE `vehicules` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2025-05-27 22:25:54
