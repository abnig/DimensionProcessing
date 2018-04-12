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
-- Table structure for table `EmployeeAddress`
--

DROP TABLE IF EXISTS `EmployeeAddress`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `EmployeeAddress` (
  `empAddId` varchar(255) NOT NULL,
  `empId` varchar(255) NOT NULL,
  `address` varchar(255) NOT NULL,
  PRIMARY KEY (`empAddId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `EmployeeAddress`
--

LOCK TABLES `EmployeeAddress` WRITE;
/*!40000 ALTER TABLE `EmployeeAddress` DISABLE KEYS */;
INSERT INTO `EmployeeAddress` VALUES ('0f331439-41e4-45f4-a7f5-e343409d5d0b','00be3dd3-7f64-4b4e-ba77-b41861284df6','Wilmington, DE'),('3caa6d83-bad7-446f-9321-1ab66bb523ba','ae7d8e1d-5356-4b78-b568-6de327c014d5','Jersey City, NJ'),('506ec724-d1d8-41fa-86fa-b643f02c9676','6cce8428-5f3d-4ae7-bd39-b030e5aae7d8','Edison, NJ'),('6d6963aa-00b5-4d4e-8e0f-4639f1e7f32f','3b2a346a-bd6d-4d89-b948-4fc3008c3709','Woodbridge, NJ'),('7bc54aeb-36e7-4aa9-955c-16cc8357e61e','c81545a5-7a01-49b8-b7ce-6d3224b9de63','Woodbridge, NJ'),('b0e23cae-7ff7-4c33-897b-fea15c0e2e54','abd09360-692e-49e9-918a-656a45c26529','Long Island City, NY');
/*!40000 ALTER TABLE `EmployeeAddress` ENABLE KEYS */;
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
