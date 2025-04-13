CREATE DATABASE  IF NOT EXISTS `qlch` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci */ /*!80016 DEFAULT ENCRYPTION='N' */;
USE `qlch`;
-- MySQL dump 10.13  Distrib 8.0.41, for Win64 (x86_64)
--
-- Host: 127.0.0.1    Database: qlch
-- ------------------------------------------------------
-- Server version	9.2.0

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
-- Table structure for table `chi_tiet_don_hang`
--

DROP TABLE IF EXISTS `chi_tiet_don_hang`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `chi_tiet_don_hang` (
  `id_chi_tiet_don_hang` varchar(5) NOT NULL,
  `id_don_hang` varchar(5) NOT NULL,
  `id_san_pham` varchar(5) NOT NULL,
  `so_luong` int NOT NULL,
  `gia_ban` int NOT NULL,
  PRIMARY KEY (`id_chi_tiet_don_hang`),
  UNIQUE KEY `id chi tiet don hang_UNIQUE` (`id_chi_tiet_don_hang`),
  KEY `fk_ctdh_donhang` (`id_don_hang`),
  KEY `fk_ctdh_sanpham` (`id_san_pham`),
  CONSTRAINT `fk_ctdh_donhang` FOREIGN KEY (`id_don_hang`) REFERENCES `don_hang` (`id_don_hang`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `fk_ctdh_sanpham` FOREIGN KEY (`id_san_pham`) REFERENCES `san_pham` (`id_san_pham`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `chi_tiet_don_hang`
--

LOCK TABLES `chi_tiet_don_hang` WRITE;
/*!40000 ALTER TABLE `chi_tiet_don_hang` DISABLE KEYS */;
INSERT INTO `chi_tiet_don_hang` VALUES ('CT001','DH001','SP001',2,85000),('CT002','DH002','SP002',1,85000),('CT003','DH003','SP003',3,85000),('CT004','DH004','SP004',1,85000),('CT005','DH005','SP005',4,85000),('CT006','DH006','SP006',2,85000),('CT007','DH007','SP007',5,85000),('CT008','DH008','SP008',1,85000),('CT009','DH009','SP009',3,85000),('CT010','DH010','SP010',2,85000),('CT011','DH011','SP001',2,85000),('CT012','DH012','SP002',1,85000),('CT013','DH013','SP003',3,85000),('CT014','DH014','SP004',1,85000),('CT015','DH015','SP005',4,85000),('CT016','DH016','SP006',2,85000),('CT017','DH017','SP007',5,85000),('CT018','DH018','SP008',1,85000),('CT019','DH019','SP009',3,85000),('CT020','DH020','SP010',2,85000),('CT021','DH021','SP001',2,85000),('CT022','DH022','SP002',1,85000),('CT023','DH023','SP003',3,85000),('CT024','DH024','SP004',1,85000),('CT025','DH025','SP005',4,85000),('CT026','DH026','SP006',2,85000),('CT027','DH027','SP007',5,85000),('CT028','DH028','SP008',1,85000),('CT029','DH029','SP009',3,85000),('CT030','DH030','SP010',2,85000),('CT031','DH031','SP001',2,85000),('CT032','DH032','SP002',1,85000),('CT033','DH033','SP003',3,85000),('CT034','DH034','SP004',1,85000),('CT035','DH035','SP005',4,85000),('CT036','DH036','SP006',2,85000),('CT037','DH037','SP007',5,85000),('CT038','DH038','SP008',1,85000),('CT039','DH039','SP009',3,85000),('CT040','DH040','SP010',2,85000);
/*!40000 ALTER TABLE `chi_tiet_don_hang` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `chi_tiet_nhap_hang`
--

DROP TABLE IF EXISTS `chi_tiet_nhap_hang`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `chi_tiet_nhap_hang` (
  `id_chi_tiet_nhap_hang` varchar(5) NOT NULL,
  `id_nhap_hang` varchar(5) NOT NULL,
  `id_san_pham` varchar(5) NOT NULL,
  `so_luong_nhap` int NOT NULL,
  `gia_nhap` int NOT NULL,
  PRIMARY KEY (`id_chi_tiet_nhap_hang`),
  KEY `fk_ctnh_nhaphang` (`id_nhap_hang`),
  KEY `fk_ctnh_sanpham` (`id_san_pham`),
  CONSTRAINT `fk_ctnh_nhaphang` FOREIGN KEY (`id_nhap_hang`) REFERENCES `nhap_hang` (`id_nhap_hang`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `fk_ctnh_sanpham` FOREIGN KEY (`id_san_pham`) REFERENCES `san_pham` (`id_san_pham`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `chi_tiet_nhap_hang`
--

LOCK TABLES `chi_tiet_nhap_hang` WRITE;
/*!40000 ALTER TABLE `chi_tiet_nhap_hang` DISABLE KEYS */;
INSERT INTO `chi_tiet_nhap_hang` VALUES ('CN001','NH001','SP005',10,60000),('CN002','NH002','SP018',20,55000),('CN003','NH003','SP002',30,65000),('CN004','NH004','SP025',15,70000),('CN005','NH005','SP011',25,100000),('CN006','NH006','SP030',50,50000),('CN007','NH007','SP008',12,62000),('CN008','NH008','SP029',8,58000),('CN009','NH009','SP020',40,68000),('CN010','NH010','SP033',18,72000),('CN011','NH011','SP001',22,105000),('CN012','NH012','SP022',5,52000),('CN013','NH013','SP015',15,100000),('CN014','NH014','SP038',35,60000),('CN015','NH015','SP007',7,110000),('CN016','NH016','SP028',10,55000),('CN017','NH017','SP019',60,65000),('CN018','NH018','SP031',20,70000),('CN019','NH019','SP003',18,115000),('CN020','NH020','SP026',28,52000),('CN021','NH021','SP009',30,100000),('CN022','NH022','SP035',45,60000),('CN023','NH023','SP017',11,62000),('CN024','NH024','SP039',55,58000),('CN025','NH025','SP004',25,68000),('CN026','NH026','SP027',16,72000),('CN027','NH027','SP012',9,108000),('CN028','NH028','SP032',14,52000),('CN029','NH029','SP021',32,102000),('CN030','NH030','SP040',22,60000),('CN031','NH031','SP006',17,62000),('CN032','NH032','SP023',48,58000),('CN033','NH033','SP016',21,68000),('CN034','NH034','SP037',60,72000),('CN035','NH035','SP010',6,118000),('CN036','NH036','SP033',19,52000),('CN037','NH037','SP014',13,102000),('CN038','NH038','SP036',11,60000),('CN039','NH039','SP024',27,62000),('CN040','NH040','SP028',38,58000);
/*!40000 ALTER TABLE `chi_tiet_nhap_hang` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `danh_muc`
--

DROP TABLE IF EXISTS `danh_muc`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `danh_muc` (
  `id_danh_muc` varchar(5) NOT NULL,
  `ten_danh_muc` varchar(45) NOT NULL,
  PRIMARY KEY (`id_danh_muc`),
  UNIQUE KEY `id danh muc_UNIQUE` (`id_danh_muc`),
  UNIQUE KEY `ten danh muc_UNIQUE` (`ten_danh_muc`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `danh_muc`
--

LOCK TABLES `danh_muc` WRITE;
/*!40000 ALTER TABLE `danh_muc` DISABLE KEYS */;
INSERT INTO `danh_muc` VALUES ('DM005','Áo hoodie'),('DM004','Áo khoác'),('DM003','Áo thun'),('DM001','Quần jean'),('DM002','Quần short');
/*!40000 ALTER TABLE `danh_muc` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `don_hang`
--

DROP TABLE IF EXISTS `don_hang`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `don_hang` (
  `id_don_hang` varchar(5) NOT NULL,
  `id_khach_hang` varchar(5) NOT NULL,
  `id_nhan_vien` varchar(5) NOT NULL,
  `tong_tien` int NOT NULL,
  `ngay_dat_hang` datetime NOT NULL,
  `trang_thai` varchar(45) NOT NULL,
  `hinh_thuc_mua_hang` varchar(45) NOT NULL,
  `dia_diem_giao` varchar(45) NOT NULL,
  PRIMARY KEY (`id_don_hang`),
  UNIQUE KEY `id don hang_UNIQUE` (`id_don_hang`),
  UNIQUE KEY `id khach hang_UNIQUE` (`id_khach_hang`),
  UNIQUE KEY `id nhan vien_UNIQUE` (`id_nhan_vien`),
  CONSTRAINT `fk_donhang_khachhang` FOREIGN KEY (`id_khach_hang`) REFERENCES `khach_hang` (`id_khach_hang`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `fk_donhang_nhanvien` FOREIGN KEY (`id_nhan_vien`) REFERENCES `nhan_vien` (`id_nhan_vien`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `don_hang`
--

LOCK TABLES `don_hang` WRITE;
/*!40000 ALTER TABLE `don_hang` DISABLE KEYS */;
INSERT INTO `don_hang` VALUES ('DH001','KH001','NV001',170000,'2025-01-05 00:00:00','Đang xử lý','Online','Địa chỉ A'),('DH002','KH002','NV002',85000,'2025-01-10 00:00:00','Đã giao','Trực tiếp','Địa chỉ B'),('DH003','KH003','NV003',255000,'2025-01-12 00:00:00','Đã giao','Online','Địa chỉ C'),('DH004','KH004','NV004',85000,'2025-01-15 00:00:00','Đã giao','Trực tiếp','Địa chỉ A'),('DH005','KH005','NV005',340000,'2025-01-18 00:00:00','Đang xử lý','Online','Địa chỉ D'),('DH006','KH006','NV006',170000,'2025-01-20 00:00:00','Đã giao','Trực tiếp','Địa chỉ E'),('DH007','KH007','NV007',425000,'2025-01-22 00:00:00','Đã giao','Online','Địa chỉ B'),('DH008','KH008','NV008',85000,'2025-01-25 00:00:00','Đã giao','Trực tiếp','Địa chỉ F'),('DH009','KH009','NV009',255000,'2025-01-27 00:00:00','Đang xử lý','Online','Địa chỉ G'),('DH010','KH010','NV010',170000,'2025-01-30 00:00:00','Đã giao','Trực tiếp','Địa chỉ A'),('DH011','KH011','NV011',170000,'2025-02-02 00:00:00','Đã giao','Online','Địa chỉ H'),('DH012','KH012','NV012',85000,'2025-02-04 00:00:00','Đã giao','Trực tiếp','Địa chỉ B'),('DH013','KH013','NV013',255000,'2025-02-06 00:00:00','Đang xử lý','Online','Địa chỉ D'),('DH014','KH014','NV014',85000,'2025-02-08 00:00:00','Đã giao','Trực tiếp','Địa chỉ F'),('DH015','KH015','NV015',340000,'2025-02-10 00:00:00','Đã giao','Online','Địa chỉ C'),('DH016','KH016','NV016',170000,'2025-02-12 00:00:00','Đã giao','Trực tiếp','Địa chỉ E'),('DH017','KH017','NV017',425000,'2025-02-14 00:00:00','Đang xử lý','Online','Địa chỉ A'),('DH018','KH018','NV018',85000,'2025-02-16 00:00:00','Đã giao','Trực tiếp','Địa chỉ B'),('DH019','KH019','NV019',255000,'2025-02-18 00:00:00','Đã giao','Online','Địa chỉ C'),('DH020','KH020','NV020',170000,'2025-02-20 00:00:00','Đã giao','Trực tiếp','Địa chỉ D'),('DH021','KH021','NV021',170000,'2025-03-01 00:00:00','Đang xử lý','Online','Địa chỉ A'),('DH022','KH022','NV022',85000,'2025-03-02 00:00:00','Đã giao','Trực tiếp','Địa chỉ B'),('DH023','KH023','NV023',255000,'2025-03-03 00:00:00','Đã giao','Online','Địa chỉ C'),('DH024','KH024','NV024',85000,'2025-03-04 00:00:00','Đã giao','Trực tiếp','Địa chỉ D'),('DH025','KH025','NV025',340000,'2025-03-05 00:00:00','Đang xử lý','Online','Địa chỉ E'),('DH026','KH026','NV026',170000,'2025-03-06 00:00:00','Đã giao','Trực tiếp','Địa chỉ F'),('DH027','KH027','NV027',425000,'2025-03-07 00:00:00','Đã giao','Online','Địa chỉ A'),('DH028','KH028','NV028',85000,'2025-03-08 00:00:00','Đã giao','Trực tiếp','Địa chỉ B'),('DH029','KH029','NV029',255000,'2025-03-09 00:00:00','Đang xử lý','Online','Địa chỉ C'),('DH030','KH030','NV030',170000,'2025-03-10 00:00:00','Đã giao','Trực tiếp','Địa chỉ D'),('DH031','KH031','NV031',170000,'2025-03-11 00:00:00','Đã giao','Online','Địa chỉ E'),('DH032','KH032','NV032',85000,'2025-03-12 00:00:00','Đã giao','Trực tiếp','Địa chỉ F'),('DH033','KH033','NV033',255000,'2025-03-13 00:00:00','Đang xử lý','Online','Địa chỉ A'),('DH034','KH034','NV034',85000,'2025-03-14 00:00:00','Đã giao','Trực tiếp','Địa chỉ B'),('DH035','KH035','NV035',340000,'2025-03-15 00:00:00','Đã giao','Online','Địa chỉ C'),('DH036','KH036','NV036',170000,'2025-03-16 00:00:00','Đã giao','Trực tiếp','Địa chỉ D'),('DH037','KH037','NV037',425000,'2025-03-17 00:00:00','Đang xử lý','Online','Địa chỉ E'),('DH038','KH038','NV038',85000,'2025-03-18 00:00:00','Đã giao','Trực tiếp','Địa chỉ F'),('DH039','KH039','NV039',255000,'2025-03-19 00:00:00','Đã giao','Online','Địa chỉ A'),('DH040','KH040','NV040',170000,'2025-03-20 00:00:00','Đã giao','Trực tiếp','Địa chỉ B');
/*!40000 ALTER TABLE `don_hang` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `khach_hang`
--

DROP TABLE IF EXISTS `khach_hang`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `khach_hang` (
  `id_khach_hang` varchar(5) NOT NULL,
  `id_phan_quyen` int NOT NULL,
  `ho_ten` varchar(45) NOT NULL,
  `email` varchar(45) NOT NULL,
  `sdt` varchar(10) NOT NULL,
  `ten_user` varchar(20) NOT NULL,
  `password` varchar(45) NOT NULL,
  PRIMARY KEY (`id_khach_hang`),
  UNIQUE KEY `id khach hang_UNIQUE` (`id_khach_hang`),
  UNIQUE KEY `email_UNIQUE` (`email`),
  UNIQUE KEY `sđt_UNIQUE` (`sdt`),
  UNIQUE KEY `ten user_UNIQUE` (`ten_user`),
  KEY `fk_khachhang_phanquyen` (`id_phan_quyen`),
  CONSTRAINT `fk_khachhang_phanquyen` FOREIGN KEY (`id_phan_quyen`) REFERENCES `phan_quyen` (`id_phan_quyen`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `khach_hang`
--

LOCK TABLES `khach_hang` WRITE;
/*!40000 ALTER TABLE `khach_hang` DISABLE KEYS */;
INSERT INTO `khach_hang` VALUES ('88888',1,'Admin2','admin2@gmail.com','0888888888','admin2','admin2'),('99999',1,'Admin','admin@gmail.com','0999999999','admin','admin'),('KH001',2,'Nguyen Van A1','a1@gmail.com','0900000001','user1','pass1'),('KH002',2,'Nguyen Van A2','a2@gmail.com','0900000002','user2','pass2'),('KH003',2,'Nguyen Van A3','a3@gmail.com','0900000003','user3','pass3'),('KH004',2,'Nguyen Van A4','a4@gmail.com','0900000004','user4','pass4'),('KH005',2,'Nguyen Van A5','a5@gmail.com','0900000005','user5','pass5'),('KH006',2,'Nguyen Van A6','a6@gmail.com','0900000006','user6','pass6'),('KH007',2,'Nguyen Van A7','a7@gmail.com','0900000007','user7','pass7'),('KH008',2,'Nguyen Van A8','a8@gmail.com','0900000008','user8','pass8'),('KH009',2,'Nguyen Van A9','a9@gmail.com','0900000009','user9','pass9'),('KH010',2,'Nguyen Van A10','a10@gmail.com','0900000010','user10','pass10'),('KH011',2,'Nguyen Van A11','a11@gmail.com','0900000011','user11','pass11'),('KH012',2,'Nguyen Van A12','a12@gmail.com','0900000012','user12','pass12'),('KH013',2,'Nguyen Van A13','a13@gmail.com','0900000013','user13','pass13'),('KH014',2,'Nguyen Van A14','a14@gmail.com','0900000014','user14','pass14'),('KH015',2,'Nguyen Van A15','a15@gmail.com','0900000015','user15','pass15'),('KH016',2,'Nguyen Van A16','a16@gmail.com','0900000016','user16','pass16'),('KH017',2,'Nguyen Van A17','a17@gmail.com','0900000017','user17','pass17'),('KH018',2,'Nguyen Van A18','a18@gmail.com','0900000018','user18','pass18'),('KH019',2,'Nguyen Van A19','a19@gmail.com','0900000019','user19','pass19'),('KH020',2,'Nguyen Van A20','a20@gmail.com','0900000020','user20','pass20'),('KH021',2,'Nguyen Van A21','a21@gmail.com','0900000021','user21','pass21'),('KH022',2,'Nguyen Van A22','a22@gmail.com','0900000022','user22','pass22'),('KH023',2,'Nguyen Van A23','a23@gmail.com','0900000023','user23','pass23'),('KH024',2,'Nguyen Van A24','a24@gmail.com','0900000024','user24','pass24'),('KH025',2,'Nguyen Van A25','a25@gmail.com','0900000025','user25','pass25'),('KH026',2,'Nguyen Van A26','a26@gmail.com','0900000026','user26','pass26'),('KH027',2,'Nguyen Van A27','a27@gmail.com','0900000027','user27','pass27'),('KH028',2,'Nguyen Van A28','a28@gmail.com','0900000028','user28','pass28'),('KH029',2,'Nguyen Van A29','a29@gmail.com','0900000029','user29','pass29'),('KH030',2,'Nguyen Van A30','a30@gmail.com','0900000030','user30','pass30'),('KH031',2,'Nguyen Van A31','a31@gmail.com','0900000031','user31','pass31'),('KH032',2,'Nguyen Van A32','a32@gmail.com','0900000032','user32','pass32'),('KH033',2,'Nguyen Van A33','a33@gmail.com','0900000033','user33','pass33'),('KH034',2,'Nguyen Van A34','a34@gmail.com','0900000034','user34','pass34'),('KH035',2,'Nguyen Van A35','a35@gmail.com','0900000035','user35','pass35'),('KH036',2,'Nguyen Van A36','a36@gmail.com','0900000036','user36','pass36'),('KH037',2,'Nguyen Van A37','a37@gmail.com','0900000037','user37','pass37'),('KH038',2,'Nguyen Van A38','a38@gmail.com','0900000038','user38','pass38'),('KH039',2,'Nguyen Van A39','a39@gmail.com','0900000039','user39','pass39'),('KH040',2,'Nguyen Van A40','a40@gmail.com','0900000040','user40','pass40');
/*!40000 ALTER TABLE `khach_hang` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `nha_cung_cap`
--

DROP TABLE IF EXISTS `nha_cung_cap`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `nha_cung_cap` (
  `id_nha_cung_cap` varchar(5) NOT NULL,
  `ten_nha_cung_cap` varchar(45) NOT NULL,
  `dia_chi` varchar(45) NOT NULL,
  `sdt` varchar(10) NOT NULL,
  PRIMARY KEY (`id_nha_cung_cap`),
  UNIQUE KEY `id nha cung cap_UNIQUE` (`id_nha_cung_cap`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `nha_cung_cap`
--

LOCK TABLES `nha_cung_cap` WRITE;
/*!40000 ALTER TABLE `nha_cung_cap` DISABLE KEYS */;
INSERT INTO `nha_cung_cap` VALUES ('CC001','Nhà cung cấp 1','Địa chỉ 1','0900000001'),('CC002','Nhà cung cấp 2','Địa chỉ 2','0900000002'),('CC003','Nhà cung cấp 3','Địa chỉ 3','0900000003'),('CC004','Nhà cung cấp 4','Địa chỉ 4','0900000004'),('CC005','Nhà cung cấp 5','Địa chỉ 5','0900000005'),('CC006','Nhà cung cấp 6','Địa chỉ 6','0900000006'),('CC007','Nhà cung cấp 7','Địa chỉ 7','0900000007'),('CC008','Nhà cung cấp 8','Địa chỉ 8','0900000008'),('CC009','Nhà cung cấp 9','Địa chỉ 9','0900000009'),('CC010','Nhà cung cấp 10','Địa chỉ 10','0900000010'),('CC011','Nhà cung cấp 11','Địa chỉ 11','0900000011'),('CC012','Nhà cung cấp 12','Địa chỉ 12','0900000012'),('CC013','Nhà cung cấp 13','Địa chỉ 13','0900000013'),('CC014','Nhà cung cấp 14','Địa chỉ 14','0900000014'),('CC015','Nhà cung cấp 15','Địa chỉ 15','0900000015'),('CC016','Nhà cung cấp 16','Địa chỉ 16','0900000016'),('CC017','Nhà cung cấp 17','Địa chỉ 17','0900000017'),('CC018','Nhà cung cấp 18','Địa chỉ 18','0900000018'),('CC019','Nhà cung cấp 19','Địa chỉ 19','0900000019'),('CC020','Nhà cung cấp 20','Địa chỉ 20','0900000020'),('CC021','Nhà cung cấp 21','Địa chỉ 21','0900000021'),('CC022','Nhà cung cấp 22','Địa chỉ 22','0900000022'),('CC023','Nhà cung cấp 23','Địa chỉ 23','0900000023'),('CC024','Nhà cung cấp 24','Địa chỉ 24','0900000024'),('CC025','Nhà cung cấp 25','Địa chỉ 25','0900000025'),('CC026','Nhà cung cấp 26','Địa chỉ 26','0900000026'),('CC027','Nhà cung cấp 27','Địa chỉ 27','0900000027'),('CC028','Nhà cung cấp 28','Địa chỉ 28','0900000028'),('CC029','Nhà cung cấp 29','Địa chỉ 29','0900000029'),('CC030','Nhà cung cấp 30','Địa chỉ 30','0900000030'),('CC031','Nhà cung cấp 31','Địa chỉ 31','0900000031'),('CC032','Nhà cung cấp 32','Địa chỉ 32','0900000032'),('CC033','Nhà cung cấp 33','Địa chỉ 33','0900000033'),('CC034','Nhà cung cấp 34','Địa chỉ 34','0900000034'),('CC035','Nhà cung cấp 35','Địa chỉ 35','0900000035'),('CC036','Nhà cung cấp 36','Địa chỉ 36','0900000036'),('CC037','Nhà cung cấp 37','Địa chỉ 37','0900000037'),('CC038','Nhà cung cấp 38','Địa chỉ 38','0900000038'),('CC039','Nhà cung cấp 39','Địa chỉ 39','0900000039'),('CC040','Nhà cung cấp 40','Địa chỉ 40','0900000040');
/*!40000 ALTER TABLE `nha_cung_cap` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `nhan_vien`
--

DROP TABLE IF EXISTS `nhan_vien`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `nhan_vien` (
  `id_nhan_vien` varchar(5) NOT NULL,
  `ho_ten_nv` varchar(45) NOT NULL,
  `email` varchar(45) NOT NULL,
  `sdt` varchar(10) NOT NULL,
  `chuc_vu` varchar(25) NOT NULL,
  `luong` int NOT NULL,
  PRIMARY KEY (`id_nhan_vien`),
  UNIQUE KEY `email_UNIQUE` (`email`),
  UNIQUE KEY `id nhan vien_UNIQUE` (`id_nhan_vien`),
  UNIQUE KEY `sđt_UNIQUE` (`sdt`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `nhan_vien`
--

LOCK TABLES `nhan_vien` WRITE;
/*!40000 ALTER TABLE `nhan_vien` DISABLE KEYS */;
INSERT INTO `nhan_vien` VALUES ('NV001','Nguyen Van A1','nv1@gmail.com','0900000001','Quản lý',12000000),('NV002','Nguyen Van A2','nv2@gmail.com','0900000002','Quản lý',12000000),('NV003','Nguyen Van A3','nv3@gmail.com','0900000003','Quản lý',12000000),('NV004','Nguyen Van A4','nv4@gmail.com','0900000004','Quản lý',12000000),('NV005','Nguyen Van A5','nv5@gmail.com','0900000005','Quản lý',12000000),('NV006','Nguyen Van A6','nv6@gmail.com','0900000006','Quản lý',12000000),('NV007','Nguyen Van A7','nv7@gmail.com','0900000007','Quản lý',12000000),('NV008','Nguyen Van A8','nv8@gmail.com','0900000008','Quản lý',12000000),('NV009','Nguyen Van A9','nv9@gmail.com','0900000009','Quản lý',12000000),('NV010','Nguyen Van A10','nv10@gmail.com','0900000010','Quản lý',12000000),('NV011','Nguyen Van B1','nv11@gmail.com','0900000011','Nhân viên',9000000),('NV012','Nguyen Van B2','nv12@gmail.com','0900000012','Nhân viên',9000000),('NV013','Nguyen Van B3','nv13@gmail.com','0900000013','Nhân viên',9000000),('NV014','Nguyen Van B4','nv14@gmail.com','0900000014','Nhân viên',9000000),('NV015','Nguyen Van B5','nv15@gmail.com','0900000015','Nhân viên',9000000),('NV016','Nguyen Van B6','nv16@gmail.com','0900000016','Nhân viên',9000000),('NV017','Nguyen Van B7','nv17@gmail.com','0900000017','Nhân viên',9000000),('NV018','Nguyen Van B8','nv18@gmail.com','0900000018','Nhân viên',9000000),('NV019','Nguyen Van B9','nv19@gmail.com','0900000019','Nhân viên',9000000),('NV020','Nguyen Van B10','nv20@gmail.com','0900000020','Nhân viên',9000000),('NV021','Nguyen Van B11','nv21@gmail.com','0900000021','Nhân viên',9000000),('NV022','Nguyen Van B12','nv22@gmail.com','0900000022','Nhân viên',9000000),('NV023','Nguyen Van B13','nv23@gmail.com','0900000023','Nhân viên',9000000),('NV024','Nguyen Van B14','nv24@gmail.com','0900000024','Nhân viên',9000000),('NV025','Nguyen Van B15','nv25@gmail.com','0900000025','Nhân viên',9000000),('NV026','Nguyen Van B16','nv26@gmail.com','0900000026','Nhân viên',9000000),('NV027','Nguyen Van B17','nv27@gmail.com','0900000027','Nhân viên',9000000),('NV028','Nguyen Van B18','nv28@gmail.com','0900000028','Nhân viên',9000000),('NV029','Nguyen Van B19','nv29@gmail.com','0900000029','Nhân viên',9000000),('NV030','Nguyen Van B20','nv30@gmail.com','0900000030','Nhân viên',9000000),('NV031','Nguyen Van B21','nv31@gmail.com','0900000031','Nhân viên',9000000),('NV032','Nguyen Van B22','nv32@gmail.com','0900000032','Nhân viên',9000000),('NV033','Nguyen Van B23','nv33@gmail.com','0900000033','Nhân viên',9000000),('NV034','Nguyen Van B24','nv34@gmail.com','0900000034','Nhân viên',9000000),('NV035','Nguyen Van B25','nv35@gmail.com','0900000035','Nhân viên',9000000),('NV036','Nguyen Van B26','nv36@gmail.com','0900000036','Nhân viên',9000000),('NV037','Nguyen Van B27','nv37@gmail.com','0900000037','Nhân viên',9000000),('NV038','Nguyen Van B28','nv38@gmail.com','0900000038','Nhân viên',9000000),('NV039','Nguyen Van B29','nv39@gmail.com','0900000039','Nhân viên',9000000),('NV040','Nguyen Van B30','nv40@gmail.com','0900000040','Nhân viên',9000000);
/*!40000 ALTER TABLE `nhan_vien` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `nhap_hang`
--

DROP TABLE IF EXISTS `nhap_hang`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `nhap_hang` (
  `id_nhap_hang` varchar(5) NOT NULL,
  `id_nha_cung_cap` varchar(5) NOT NULL,
  `id_nhan_vien` varchar(5) NOT NULL,
  `ngay_nhap` datetime NOT NULL,
  `tong_gia_tri_nhap` int NOT NULL,
  PRIMARY KEY (`id_nhap_hang`),
  UNIQUE KEY `id nhap hang_UNIQUE` (`id_nhap_hang`),
  KEY `fk_nhaphang_ncc` (`id_nha_cung_cap`),
  KEY `fk_nhaphang_nv` (`id_nhan_vien`),
  CONSTRAINT `fk_nhaphang_ncc` FOREIGN KEY (`id_nha_cung_cap`) REFERENCES `nha_cung_cap` (`id_nha_cung_cap`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `fk_nhaphang_nv` FOREIGN KEY (`id_nhan_vien`) REFERENCES `nhan_vien` (`id_nhan_vien`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `nhap_hang`
--

LOCK TABLES `nhap_hang` WRITE;
/*!40000 ALTER TABLE `nhap_hang` DISABLE KEYS */;
INSERT INTO `nhap_hang` VALUES ('NH001','CC005','NV012','2025-01-05 00:00:00',600000),('NH002','CC018','NV025','2025-01-12 00:00:00',1100000),('NH003','CC031','NV003','2025-01-20 00:00:00',1950000),('NH004','CC002','NV038','2025-01-28 00:00:00',1050000),('NH005','CC022','NV019','2025-02-03 00:00:00',2500000),('NH006','CC011','NV033','2025-02-10 00:00:00',2500000),('NH007','CC035','NV008','2025-02-17 00:00:00',744000),('NH008','CC009','NV029','2025-02-24 00:00:00',464000),('NH009','CC027','NV015','2025-03-03 00:00:00',2720000),('NH010','CC001','NV039','2025-03-10 00:00:00',1296000),('NH011','CC015','NV021','2025-03-17 00:00:00',2310000),('NH012','CC038','NV005','2025-03-24 00:00:00',260000),('NH013','CC007','NV031','2025-03-31 00:00:00',1500000),('NH014','CC025','NV010','2025-04-07 00:00:00',2100000),('NH015','CC019','NV027','2025-04-14 00:00:00',770000),('NH016','CC033','NV002','2025-04-21 00:00:00',550000),('NH017','CC004','NV036','2025-04-28 00:00:00',3900000),('NH018','CC021','NV017','2025-05-05 00:00:00',1400000),('NH019','CC039','NV009','2025-05-12 00:00:00',2070000),('NH020','CC012','NV023','2025-05-19 00:00:00',1456000),('NH021','CC008','NV034','2025-05-26 00:00:00',3000000),('NH022','CC029','NV007','2025-06-02 00:00:00',2700000),('NH023','CC016','NV028','2025-06-09 00:00:00',682000),('NH024','CC037','NV013','2025-06-16 00:00:00',3190000),('NH025','CC003','NV032','2025-06-23 00:00:00',1700000),('NH026','CC023','NV001','2025-06-30 00:00:00',1152000),('NH027','CC010','NV020','2025-07-07 00:00:00',972000),('NH028','CC036','NV011','2025-07-14 00:00:00',728000),('NH029','CC017','NV037','2025-07-21 00:00:00',3264000),('NH030','CC006','NV026','2025-07-28 00:00:00',1320000),('NH031','CC026','NV004','2025-08-04 00:00:00',1054000),('NH032','CC013','NV030','2025-08-11 00:00:00',2784000),('NH033','CC032','NV016','2025-08-18 00:00:00',1428000),('NH034','CC008','NV022','2025-08-25 00:00:00',4320000),('NH035','CC020','NV006','2025-09-01 00:00:00',708000),('NH036','CC034','NV024','2025-09-08 00:00:00',988000),('NH037','CC014','NV040','2025-09-15 00:00:00',1326000),('NH038','CC028','NV018','2025-09-22 00:00:00',660000),('NH039','CC040','NV035','2025-09-29 00:00:00',1674000),('NH040','CC024','NV014','2025-10-06 00:00:00',2204000);
/*!40000 ALTER TABLE `nhap_hang` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `phan_quyen`
--

DROP TABLE IF EXISTS `phan_quyen`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `phan_quyen` (
  `id_phan_quyen` int NOT NULL,
  `ten_quyen` varchar(45) NOT NULL,
  PRIMARY KEY (`id_phan_quyen`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `phan_quyen`
--

LOCK TABLES `phan_quyen` WRITE;
/*!40000 ALTER TABLE `phan_quyen` DISABLE KEYS */;
INSERT INTO `phan_quyen` VALUES (1,'Admin'),(2,'User');
/*!40000 ALTER TABLE `phan_quyen` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `phuong_thuc_thanh_toan`
--

DROP TABLE IF EXISTS `phuong_thuc_thanh_toan`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `phuong_thuc_thanh_toan` (
  `id_pttt` varchar(5) NOT NULL,
  `id_don_hang` varchar(5) NOT NULL,
  `loai_thanh_toan` varchar(45) NOT NULL,
  `trang_thai_thanh_toan` varchar(45) NOT NULL,
  PRIMARY KEY (`id_pttt`),
  KEY `fk_pttt_donhang_idx` (`id_don_hang`),
  CONSTRAINT `fk_pttt_donhang` FOREIGN KEY (`id_don_hang`) REFERENCES `don_hang` (`id_don_hang`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `phuong_thuc_thanh_toan`
--

LOCK TABLES `phuong_thuc_thanh_toan` WRITE;
/*!40000 ALTER TABLE `phuong_thuc_thanh_toan` DISABLE KEYS */;
INSERT INTO `phuong_thuc_thanh_toan` VALUES ('PT001','DH001','Chuyển khoản','Đã thanh toán'),('PT002','DH002','Tiền mặt','Đã thanh toán'),('PT003','DH003','Chuyển khoản','Đã thanh toán'),('PT004','DH004','Tiền mặt','Đã thanh toán'),('PT005','DH005','Chuyển khoản','Đã thanh toán'),('PT006','DH006','Tiền mặt','Đã thanh toán'),('PT007','DH007','Chuyển khoản','Chưa thanh toán'),('PT008','DH008','Tiền mặt','Đã thanh toán'),('PT009','DH009','Chuyển khoản','Đã thanh toán'),('PT010','DH010','Tiền mặt','Đã thanh toán'),('PT011','DH011','Tiền mặt','Đã thanh toán'),('PT012','DH012','Chuyển khoản','Đã thanh toán'),('PT013','DH013','Tiền mặt','Chưa thanh toán'),('PT014','DH014','Chuyển khoản','Đã thanh toán'),('PT015','DH015','Tiền mặt','Đã thanh toán'),('PT016','DH016','Chuyển khoản','Đã thanh toán'),('PT017','DH017','Tiền mặt','Đã thanh toán'),('PT018','DH018','Chuyển khoản','Đã thanh toán'),('PT019','DH019','Tiền mặt','Chưa thanh toán'),('PT020','DH020','Chuyển khoản','Đã thanh toán'),('PT021','DH021','Tiền mặt','Đã thanh toán'),('PT022','DH022','Chuyển khoản','Đã thanh toán'),('PT023','DH023','Tiền mặt','Đã thanh toán'),('PT024','DH024','Chuyển khoản','Đã thanh toán'),('PT025','DH025','Tiền mặt','Chưa thanh toán'),('PT026','DH026','Chuyển khoản','Đã thanh toán'),('PT027','DH027','Tiền mặt','Đã thanh toán'),('PT028','DH028','Chuyển khoản','Đã thanh toán'),('PT029','DH029','Tiền mặt','Đã thanh toán'),('PT030','DH030','Chuyển khoản','Đã thanh toán'),('PT031','DH031','Tiền mặt','Chưa thanh toán'),('PT032','DH032','Chuyển khoản','Đã thanh toán'),('PT033','DH033','Tiền mặt','Đã thanh toán'),('PT034','DH034','Chuyển khoản','Đã thanh toán'),('PT035','DH035','Tiền mặt','Đã thanh toán'),('PT036','DH036','Chuyển khoản','Đã thanh toán'),('PT037','DH037','Tiền mặt','Chưa thanh toán'),('PT038','DH038','Chuyển khoản','Đã thanh toán'),('PT039','DH039','Tiền mặt','Đã thanh toán'),('PT040','DH040','Chuyển khoản','Đã thanh toán');
/*!40000 ALTER TABLE `phuong_thuc_thanh_toan` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `san_pham`
--

DROP TABLE IF EXISTS `san_pham`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `san_pham` (
  `id_san_pham` varchar(5) NOT NULL,
  `id_danh_muc` varchar(5) NOT NULL,
  `id_nha_cung_cap` varchar(5) NOT NULL,
  `gia` varchar(45) NOT NULL,
  `ten_san_pham` varchar(45) NOT NULL,
  `so_luong_ton_kho` varchar(45) NOT NULL,
  PRIMARY KEY (`id_san_pham`),
  UNIQUE KEY `id san pham_UNIQUE` (`id_san_pham`),
  KEY `fk_sanpham_danhmuc` (`id_danh_muc`),
  KEY `fk_sanpham_ncc` (`id_nha_cung_cap`),
  CONSTRAINT `fk_sanpham_danhmuc` FOREIGN KEY (`id_danh_muc`) REFERENCES `danh_muc` (`id_danh_muc`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `fk_sanpham_ncc` FOREIGN KEY (`id_nha_cung_cap`) REFERENCES `nha_cung_cap` (`id_nha_cung_cap`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `san_pham`
--

LOCK TABLES `san_pham` WRITE;
/*!40000 ALTER TABLE `san_pham` DISABLE KEYS */;
INSERT INTO `san_pham` VALUES ('SP001','DM002','CC001','85000','Sản phẩm 1','29'),('SP002','DM001','CC001','85000','Sản phẩm 2','4'),('SP003','DM003','CC001','85000','Sản phẩm 3','1'),('SP004','DM004','CC001','85000','Sản phẩm 4','80'),('SP005','DM005','CC001','85000','Sản phẩm 5','3'),('SP006','DM002','CC002','85000','Sản phẩm 6','40'),('SP007','DM001','CC002','85000','Sản phẩm 7','33'),('SP008','DM003','CC002','85000','Sản phẩm 8','59'),('SP009','DM004','CC002','85000','Sản phẩm 9','14'),('SP010','DM005','CC002','85000','Sản phẩm 10','27'),('SP011','DM002','CC003','150000','Sản phẩm 11','90'),('SP012','DM001','CC003','150000','Sản phẩm 12','55'),('SP013','DM003','CC003','150000','Sản phẩm 13','38'),('SP014','DM004','CC003','150000','Sản phẩm 14','14'),('SP015','DM005','CC003','150000','Sản phẩm 15','27'),('SP016','DM002','CC004','150000','Sản phẩm 16','76'),('SP017','DM001','CC004','150000','Sản phẩm 17','5'),('SP018','DM003','CC004','150000','Sản phẩm 18','2'),('SP019','DM004','CC004','150000','Sản phẩm 19','88'),('SP020','DM005','CC004','150000','Sản phẩm 20','41'),('SP021','DM002','CC005','150000','Sản phẩm 21','87'),('SP022','DM001','CC005','150000','Sản phẩm 22','83'),('SP023','DM003','CC005','150000','Sản phẩm 23','83'),('SP024','DM004','CC005','150000','Sản phẩm 24','30'),('SP025','DM005','CC005','150000','Sản phẩm 25','24'),('SP026','DM002','CC006','280000','Sản phẩm 26','80'),('SP027','DM001','CC006','280000','Sản phẩm 27','5'),('SP028','DM003','CC006','280000','Sản phẩm 28','69'),('SP029','DM004','CC006','280000','Sản phẩm 29','53'),('SP030','DM005','CC006','280000','Sản phẩm 30','80'),('SP031','DM002','CC007','280000','Sản phẩm 31','39'),('SP032','DM001','CC007','280000','Sản phẩm 32','22'),('SP033','DM003','CC007','280000','Sản phẩm 33','58'),('SP034','DM004','CC007','280000','Sản phẩm 34','93'),('SP035','DM005','CC007','280000','Sản phẩm 35','37'),('SP036','DM002','CC008','280000','Sản phẩm 36','57'),('SP037','DM001','CC008','280000','Sản phẩm 37','41'),('SP038','DM003','CC008','280000','Sản phẩm 38','64'),('SP039','DM004','CC008','280000','Sản phẩm 39','44'),('SP040','DM005','CC008','280000','Sản phẩm 40','55');
/*!40000 ALTER TABLE `san_pham` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2025-04-12 22:48:49
