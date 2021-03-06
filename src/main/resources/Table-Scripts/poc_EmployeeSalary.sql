-- MySQL dump 10.13  Distrib 5.7.9, for osx10.9 (x86_64)
--
-- Host: localhost    Database: poc
-- ------------------------------------------------------
-- Server version	5.7.12

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `EmployeeSalary`
--

DROP TABLE IF EXISTS `EmployeeSalary`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `EmployeeSalary` (
  `empSalId` varchar(255) NOT NULL,
  `empId` varchar(255) NOT NULL,
  `salary` decimal(10,5) NOT NULL,
  PRIMARY KEY (`empSalId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `EmployeeSalary`
--

LOCK TABLES `EmployeeSalary` WRITE;
/*!40000 ALTER TABLE `EmployeeSalary` DISABLE KEYS */;
INSERT INTO `EmployeeSalary` VALUES ('1ac87036-11be-465a-ad2b-8c8043c01da7','ae7d8e1d-5356-4b78-b568-6de327c014d5',50000.00000),('6ce949bc-b8a5-4968-8671-0973f2bf4fb3','6cce8428-5f3d-4ae7-bd39-b030e5aae7d8',25000.00000),('8ad8db8d-fcec-462e-9e7f-98b78fa1b1a3','abd09360-692e-49e9-918a-656a45c26529',20000.00000),('d36ce137-e03e-4984-b970-743b8417cf18','3b2a346a-bd6d-4d89-b948-4fc3008c3709',5000.00000),('e10a3545-1598-4cb8-8baf-7bab03d46a86','00be3dd3-7f64-4b4e-ba77-b41861284df6',18000.00000),('ecca50dd-0d54-4271-a9cd-85ab78aaec37','c81545a5-7a01-49b8-b7ce-6d3224b9de63',15000.00000);
/*!40000 ALTER TABLE `EmployeeSalary` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2016-07-29 12:13:05
