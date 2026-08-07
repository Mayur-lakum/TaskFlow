-- MySQL dump 10.13  Distrib 8.0.45, for Win64 (x86_64)
--
-- Host: localhost    Database: taskflow_ai
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
-- Table structure for table `employee`
--

DROP TABLE IF EXISTS `employee`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `employee` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `availability` bit(1) DEFAULT NULL,
  `department` varchar(255) DEFAULT NULL,
  `designation` varchar(255) DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  `employee_code` varchar(255) DEFAULT NULL,
  `experience` int DEFAULT NULL,
  `first_name` varchar(255) DEFAULT NULL,
  `last_name` varchar(255) DEFAULT NULL,
  `performance_score` double DEFAULT NULL,
  `phone` varchar(255) DEFAULT NULL,
  `created_at` datetime(6) DEFAULT NULL,
  `updated_at` datetime(6) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UKfopic1oh5oln2khj8eat6ino0` (`email`)
) ENGINE=InnoDB AUTO_INCREMENT=14 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `employee`
--

LOCK TABLES `employee` WRITE;
/*!40000 ALTER TABLE `employee` DISABLE KEYS */;
INSERT INTO `employee` VALUES (1,_binary '','IT','Java Backend Developer','mayur123@gmail.com','EMP001',2,'Mayur','Lakum',92.5,'9999999999',NULL,NULL),(2,_binary '','IT','Java Developer','Yuvraj@gmail.com','EMP002',2,'Yuvraj','Lakum',95.5,'9876113210',NULL,NULL),(7,_binary '','IT','Backend Developer','mayurrr@gmail.com',NULL,1,'Mayurrr','Lakumrr',NULL,'9876832310',NULL,NULL),(10,_binary '','IT','Backend Developer','mayurfrrr@gmail.com',NULL,10,'Mayurfrrr','Lakudvmrr',NULL,'2222222222222',NULL,NULL),(11,_binary '','IT','Frontend Developer','john@gmail.com',NULL,5,'John','Alex',NULL,'1234567890',NULL,NULL);
/*!40000 ALTER TABLE `employee` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `employee_skill`
--

DROP TABLE IF EXISTS `employee_skill`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `employee_skill` (
  `employee_id` bigint NOT NULL,
  `skill_id` bigint NOT NULL,
  PRIMARY KEY (`employee_id`,`skill_id`),
  KEY `FKam2psf41jwoy33ge3uvxep8tl` (`skill_id`),
  CONSTRAINT `FKam2psf41jwoy33ge3uvxep8tl` FOREIGN KEY (`skill_id`) REFERENCES `skill` (`id`),
  CONSTRAINT `FKkd8xx37dlmjryoas0d91hri6c` FOREIGN KEY (`employee_id`) REFERENCES `employee` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `employee_skill`
--

LOCK TABLES `employee_skill` WRITE;
/*!40000 ALTER TABLE `employee_skill` DISABLE KEYS */;
INSERT INTO `employee_skill` VALUES (1,1),(2,1),(1,2),(2,2),(7,2),(2,3),(1,5),(2,5),(10,6),(11,6);
/*!40000 ALTER TABLE `employee_skill` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `project`
--

DROP TABLE IF EXISTS `project`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `project` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `client_name` varchar(255) NOT NULL,
  `created_at` datetime(6) DEFAULT NULL,
  `description` varchar(500) DEFAULT NULL,
  `end_date` date DEFAULT NULL,
  `project_code` varchar(255) NOT NULL,
  `project_name` varchar(255) NOT NULL,
  `required_experience` int NOT NULL,
  `start_date` date NOT NULL,
  `status` enum('CANCELLED','COMPLETED','IN_PROGRESS','ON_HOLD','PLANNING') NOT NULL,
  `updated_at` datetime(6) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UKpyadn2e3xipo328gs8ae6sh11` (`project_code`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `project`
--

LOCK TABLES `project` WRITE;
/*!40000 ALTER TABLE `project` DISABLE KEYS */;
INSERT INTO `project` VALUES (2,'XYZ Technologies',NULL,'Updated Description','2026-12-31','PRJ001','TaskFlow AI Updated',3,'2026-08-05','PLANNING',NULL),(3,'XYZ Technologies',NULL,'Updated Description','2026-12-31','PRJ002','TaskFlow AI Updated',3,'2026-08-05','IN_PROGRESS',NULL),(4,'Helloworld infotech',NULL,'',NULL,'PRJ003','MiniORM updated',1,'2026-08-08','PLANNING',NULL),(5,'dfghbn',NULL,'',NULL,'PRJ005','qwasedfgh',2,'2026-08-07','PLANNING',NULL),(6,'SDXVFCBV',NULL,'XCFVBN','2026-08-14','23456','SDFGH',2,'2026-08-07','PLANNING',NULL),(7,'dfcvbn',NULL,'dsf','2026-09-03','34tyu','dfgbhn',2,'2026-08-18','PLANNING',NULL),(8,'en m, j',NULL,'sdfgvbnm','2026-08-29','dfgh345','345rtyuhjkm',1,'2026-08-08','PLANNING',NULL),(9,'dwsfs',NULL,'sdfcgvbn','2026-09-05','q234t5y6','aswdfgh',0,'2026-08-08','ON_HOLD',NULL);
/*!40000 ALTER TABLE `project` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `project_skill`
--

DROP TABLE IF EXISTS `project_skill`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `project_skill` (
  `project_id` bigint NOT NULL,
  `skill_id` bigint NOT NULL,
  PRIMARY KEY (`project_id`,`skill_id`),
  KEY `FKrhm021cfqcutusb89uxtgyred` (`skill_id`),
  CONSTRAINT `FKqufv40gmkhn3vuo9opfumexss` FOREIGN KEY (`project_id`) REFERENCES `project` (`id`),
  CONSTRAINT `FKrhm021cfqcutusb89uxtgyred` FOREIGN KEY (`skill_id`) REFERENCES `skill` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `project_skill`
--

LOCK TABLES `project_skill` WRITE;
/*!40000 ALTER TABLE `project_skill` DISABLE KEYS */;
INSERT INTO `project_skill` VALUES (6,1),(7,1),(8,1),(4,2),(7,2),(8,2),(6,3),(7,3),(7,5),(9,6);
/*!40000 ALTER TABLE `project_skill` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `skill`
--

DROP TABLE IF EXISTS `skill`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `skill` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `description` varchar(300) DEFAULT NULL,
  `skill_name` varchar(255) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK1ledx6hfgc5c7ht0js8bmdqs0` (`skill_name`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `skill`
--

LOCK TABLES `skill` WRITE;
/*!40000 ALTER TABLE `skill` DISABLE KEYS */;
INSERT INTO `skill` VALUES (1,'Core Java Programming','Java'),(2,'spring framework','Springboot'),(3,'Core python Programming','Python'),(5,'frontend framework\n','React'),(6,'API testing','Postman testing');
/*!40000 ALTER TABLE `skill` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `users`
--

DROP TABLE IF EXISTS `users`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `users` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `enabled` bit(1) NOT NULL,
  `password` varchar(255) NOT NULL,
  `role` enum('ADMIN','EMPLOYEE','MANAGER') NOT NULL,
  `username` varchar(255) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UKr43af9ap4edm43mmtq01oddj6` (`username`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `users`
--

LOCK TABLES `users` WRITE;
/*!40000 ALTER TABLE `users` DISABLE KEYS */;
INSERT INTO `users` VALUES (1,_binary '','$2a$10$r0KYeFahtZKOmG5X7q1yOO6gkSyj/Gqc9N219S3ICZX4pGM.C45JO','ADMIN','admin');
/*!40000 ALTER TABLE `users` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2026-08-07 14:40:48
