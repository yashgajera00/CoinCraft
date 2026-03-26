-- MySQL dump 10.13  Distrib 8.0.45, for Win64 (x86_64)
--
-- Host: localhost    Database: coincraft
-- ------------------------------------------------------
-- Server version	8.0.45

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `coins`
--

DROP TABLE IF EXISTS `coins`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `coins` (
  `coin_id` int NOT NULL,
  `coin_name` varchar(50) COLLATE utf8mb4_general_ci NOT NULL,
  `sumbol` varchar(50) COLLATE utf8mb4_general_ci NOT NULL,
  `price` double NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `coins`
--

LOCK TABLES `coins` WRITE;
/*!40000 ALTER TABLE `coins` DISABLE KEYS */;
INSERT INTO `coins` VALUES (1,'Bitcoin','BTC',33054.75),(2,'USDT','USD',108.01),(3,'Ethereum','ETH',21540.21),(4,'BNB','BNB',16458.16),(5,'Solana','SOL',7920.7),(6,'XRP','XRP',56.66),(7,'Cardano','ADA',35.78),(8,'Dogecoin','DOGE',6.89),(9,'Polkadot','DOT',540.54),(10,'Litecoin','LTC',6179.72),(11,'Chainlink','LINK',877.77),(12,'Stellar','XLM',13.07),(13,'Avalanche','AVAX',4096.46),(14,'Uniswap','UNI',1040.86),(15,'VeChain','VET',2.17),(16,'Cosmos','ATOM',1594.04),(17,'Internet Computer','ICP',1971.34),(18,'Aptos','APT',999.27),(19,'Maker','MKR',156245.38),(20,'NEAR Protocol','NEAR',669.45),(21,'Quant','QNT',11301.03),(22,'Algorand','ALGO',26.49),(23,'Tezos','XTZ',92.66),(24,'Filecoin','FIL',495.18),(25,'Hedera','HBAR',7.93),(26,'EOS','EOS',109.29),(27,'Aave','AAVE',15191.09),(28,'The Graph','GRT',10.54),(29,'Fantom','FTM',60.86),(30,'Decentraland','MANA',91.81),(31,'Sandbox','SAND',92.41),(32,'Theta','THETA',508.88),(33,'Kava','KAVA',104.61),(34,'Zilliqa','ZIL',3.07),(35,'IOTA','MIOTA',48.9),(36,'Enjin Coin','ENJ',71.74),(37,'Chiliz','CHZ',12.33),(38,'Basic Attention Token','BAT',18.63),(39,'Curve DAO','CRV',229.76),(40,'Gala','GALA',4.05);
/*!40000 ALTER TABLE `coins` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2026-03-13 19:20:34
