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
-- Table structure for table `coin_latest_news`
--

DROP TABLE IF EXISTS `coin_latest_news`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `coin_latest_news` (
  `id` int NOT NULL AUTO_INCREMENT,
  `coin_name` varchar(50) COLLATE utf8mb4_general_ci NOT NULL,
  `news_title` varchar(255) COLLATE utf8mb4_general_ci NOT NULL,
  `news_url` varchar(255) COLLATE utf8mb4_general_ci NOT NULL,
  `published_at` datetime NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=51 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `coin_latest_news`
--

LOCK TABLES `coin_latest_news` WRITE;
/*!40000 ALTER TABLE `coin_latest_news` DISABLE KEYS */;
INSERT INTO `coin_latest_news` VALUES (1,'Bitcoin','Bitcoin hits new all-time high','https://news.example.com/bitcoin1','2025-08-19 08:00:00'),(2,'Ethereum','Ethereum 2.0 upgrade launches successfully','https://news.example.com/eth1','2025-08-19 09:00:00'),(3,'Solana','Solana sees surge in DeFi activity','https://news.example.com/sol1','2025-08-19 10:00:00'),(4,'Cardano','Cardano partners with African governments','https://news.example.com/ada1','2025-08-18 08:30:00'),(5,'Dogecoin','Elon Musk tweets about Dogecoin again','https://news.example.com/doge1','2025-08-18 09:15:00'),(6,'Ripple','Ripple wins partial victory in SEC lawsuit','https://news.example.com/xrp1','2025-08-17 11:00:00'),(7,'Bitcoin','Bitcoin mining difficulty increases','https://news.example.com/bitcoin2','2025-08-17 12:00:00'),(8,'Ethereum','ETH gas fees drop significantly','https://news.example.com/eth2','2025-08-17 13:00:00'),(9,'Polkadot','Polkadot parachain auction heats up','https://news.example.com/dot1','2025-08-17 14:30:00'),(10,'Chainlink','Chainlink launches new oracle network','https://news.example.com/link1','2025-08-16 15:00:00'),(11,'Bitcoin','MicroStrategy buys more Bitcoin','https://news.example.com/bitcoin3','2025-08-16 10:00:00'),(12,'Ethereum','Ethereum ETFs approved in US','https://news.example.com/eth3','2025-08-16 11:30:00'),(13,'Litecoin','Litecoin halving complete','https://news.example.com/ltc1','2025-08-15 16:00:00'),(14,'Avalanche','Avalanche adds new subnet projects','https://news.example.com/avax1','2025-08-15 17:30:00'),(15,'Shiba Inu','Shiba Inu announces metaverse plans','https://news.example.com/shiba1','2025-08-14 09:45:00'),(16,'Bitcoin','Bitcoin transaction volume spikes','https://news.example.com/bitcoin4','2025-08-14 10:30:00'),(17,'Ethereum','Vitalik publishes new Ethereum roadmap','https://news.example.com/eth4','2025-08-14 11:00:00'),(18,'Polygon','Polygon partners with major bank','https://news.example.com/matic1','2025-08-14 12:00:00'),(19,'Algorand','Algorand chosen for CBDC pilot','https://news.example.com/algo1','2025-08-14 13:30:00'),(20,'Bitcoin Cash','Bitcoin Cash undergoes hard fork','https://news.example.com/bch1','2025-08-13 15:00:00'),(21,'Ethereum','DeFi TVL on Ethereum hits $100B','https://news.example.com/eth5','2025-08-13 08:00:00'),(22,'Solana','Solana recovers from recent outage','https://news.example.com/sol2','2025-08-13 09:00:00'),(23,'Cardano','Cardano launches smart contract upgrade','https://news.example.com/ada2','2025-08-13 10:00:00'),(24,'Dogecoin','Doge accepted by major retailer','https://news.example.com/doge2','2025-08-12 11:15:00'),(25,'Ripple','Ripple expands in Asia market','https://news.example.com/xrp2','2025-08-12 12:00:00'),(26,'Bitcoin','Bitcoin hash rate reaches record high','https://news.example.com/bitcoin5','2025-08-12 13:00:00'),(27,'Ethereum','Staking rewards increase','https://news.example.com/eth6','2025-08-12 14:00:00'),(28,'Polkadot','New governance model approved','https://news.example.com/dot2','2025-08-12 15:00:00'),(29,'Chainlink','Chainlink expands to Avalanche','https://news.example.com/link2','2025-08-11 10:30:00'),(30,'Litecoin','Litecoin adoption grows in Asia','https://news.example.com/ltc2','2025-08-11 11:45:00'),(31,'Bitcoin','Bitcoin adoption in South America rises','https://news.example.com/bitcoin6','2025-08-11 12:30:00'),(32,'Ethereum','New L2 solution integrated with Ethereum','https://news.example.com/eth7','2025-08-11 13:15:00'),(33,'Shiba Inu','ShibaSwap sees record transactions','https://news.example.com/shiba2','2025-08-10 14:45:00'),(34,'Polygon','Polygon releases zkEVM upgrade','https://news.example.com/matic2','2025-08-10 15:30:00'),(35,'Avalanche','Institutional interest in Avalanche grows','https://news.example.com/avax2','2025-08-10 16:00:00'),(36,'Cardano','Cardano reaches 1000 smart contracts','https://news.example.com/ada3','2025-08-09 09:00:00'),(37,'Solana','Solana introduces fee market system','https://news.example.com/sol3','2025-08-09 10:15:00'),(38,'Dogecoin','Dogecoin foundation announces roadmap','https://news.example.com/doge3','2025-08-09 11:45:00'),(39,'Ripple','Ripple XRP integrated in payment app','https://news.example.com/xrp3','2025-08-09 12:30:00'),(40,'Litecoin','Litecoin sees 20% price jump','https://news.example.com/ltc3','2025-08-08 13:00:00'),(41,'Bitcoin','Bitcoin ETF sees record inflows','https://news.example.com/bitcoin7','2025-08-08 14:00:00'),(42,'Ethereum','Ethereum devs test sharding','https://news.example.com/eth8','2025-08-08 15:00:00'),(43,'Polkadot','Parachain project raises $50M','https://news.example.com/dot3','2025-08-07 08:00:00'),(44,'Chainlink','Chainlink CCIP gains traction','https://news.example.com/link3','2025-08-07 09:00:00'),(45,'Algorand','Algorand announces green initiative','https://news.example.com/algo2','2025-08-07 10:00:00'),(46,'Bitcoin Cash','Major exchange relists BCH','https://news.example.com/bch2','2025-08-07 11:00:00'),(47,'Polygon','Polygon sees spike in active wallets','https://news.example.com/matic3','2025-08-06 12:00:00'),(48,'Shiba Inu','Shiba Inu joins payment gateway','https://news.example.com/shiba3','2025-08-06 13:00:00'),(49,'Avalanche','Avalanche adds support for stablecoins','https://news.example.com/avax3','2025-08-06 14:00:00'),(50,'Ethereum','ETH community debates fee burn model','https://news.example.com/eth9','2025-08-06 15:00:00');
/*!40000 ALTER TABLE `coin_latest_news` ENABLE KEYS */;
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
