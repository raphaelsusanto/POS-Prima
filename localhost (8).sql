-- phpMyAdmin SQL Dump
-- version 3.4.8
-- http://www.phpmyadmin.net
--
-- Host: localhost
-- Generation Time: Apr 30, 2012 at 06:25 AM
-- Server version: 5.1.41
-- PHP Version: 5.3.1

SET SQL_MODE="NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;

--
-- Database: `primaDB`
--
CREATE DATABASE `primaDB` DEFAULT CHARACTER SET latin1 COLLATE latin1_swedish_ci;
USE `primaDB`;

DELIMITER $$
--
-- Procedures
--
CREATE DEFINER=`root`@`localhost` PROCEDURE `sp_addDetailTransaksi`(in jumlah double,in harga double,in mata_uang bit,in id_barang int, in id_transaksi varchar(10))
BEGIN
declare kode char(12);
declare no char(10);
declare kode1 varchar(12);
set kode=right(concat('00',month(now())),2);
set kode=concat('D',concat(year(now()),kode));
set no=ifnull(right(concat('000000',(select max(convert(right(id_detail_transaksi,3),decimal(3)))+1 from detail_transaksi where left(id_detail_transaksi,7)=kode)),3),'001');
set kode=concat(kode,no);
insert into detail_transaksi values(kode,jumlah,harga,mata_uang,id_barang,id_transaksi);
call sp_refreshStockBarang(id_barang);
set kode1=kode;
select kode1;
END$$

CREATE DEFINER=`root`@`localhost` PROCEDURE `sp_addPembelian`(in kurs int , in id_supplier int,in harga_total int)
BEGIN
declare kode char(12);
declare no char(10);
declare kode1 varchar(12);
set kode=right(concat('00',month(now())),2);
set kode=concat('B',concat(year(now()),kode));
set no=ifnull(right(concat('000000',(select max(convert(right(id_pembelian,3),decimal(3)))+1 from pembelian where left(id_pembelian,7)=kode)),3),'001');
set kode=concat(kode,no);
insert into pembelian values(kode,now(),kurs,harga_total,1,id_supplier);
set kode1=kode;
select kode1;
END$$

CREATE DEFINER=`root`@`localhost` PROCEDURE `sp_addPembelianDetail`(in jumlah int, in harga double,in mata_uang bit,in id_barang int, in id_pembelian varchar(10))
BEGIN
declare kode char(12);
declare no char(10);
declare kode1 varchar(12);

set kode=right(concat('00',month(now())),2);
set kode=concat('D',concat(year(now()),kode));
set no=ifnull(right(concat('000000',(select max(convert(right(id_pembelian_detail,3),decimal(3)))+1 from pembelian_detail where left(id_pembelian_detail,7)=kode)),3),'001');
set kode=concat(kode,no);
insert into pembelian_detail values(kode,jumlah,harga,mata_uang,id_barang,id_pembelian);
call sp_refreshStockBarang(id_barang);
set kode1=kode;
select kode1;
END$$

CREATE DEFINER=`root`@`localhost` PROCEDURE `sp_addSuratJalan`(in kendaraan varchar(45),in no_kendaraan varchar(45),in id_transaksi char(10))
BEGIN
declare kode char(12);
declare no char(10);
declare kode1 varchar(12);
set kode=right(concat('00',month(now())),2);
set kode=concat('SJ',right(concat(year(now()),kode),4));
set no=ifnull(right(concat('000000',(select max(convert(right(id_surat_jalan,3),decimal(3)))+1 from surat_jalan where left(id_surat_jalan,6)=kode)),3),'001');
set kode=concat(kode,no);
insert into surat_jalan values(kode,kendaraan,no_kendaraan,id_transaksi);
END$$

CREATE DEFINER=`root`@`localhost` PROCEDURE `sp_addTransaksi`(in kurs int,in harga_jual int,in harga_usd double,in mata_uang bit,in id_pembeli int)
BEGIN
declare kode char(12);
declare no char(10);
declare kode1 varchar(12);
set kode=right(concat('00',month(now())),2);
set kode=concat('IV',right(concat(year(now()),kode),4));
set no=ifnull(right(concat('000000',(select max(convert(right(id_transaksi,3),decimal(3)))+1 from transaksi where left(id_transaksi,6)=kode)),3),'001');
set kode=concat(kode,no);
insert into transaksi values(kode,now(),kurs,harga_jual,harga_usd,mata_uang,1,id_pembeli);
set kode1=kode;
select kode1;
END$$

CREATE DEFINER=`root`@`localhost` PROCEDURE `sp_refreshStockBarang`(in id_barang int)
BEGIN
update barang b set b.stock=ifnull((select sum(pd.jumlah) from pembelian_detail pd join pembelian p on p.id_pembelian=pd.id_pembelian where pd.id_barang=id_barang and p.status=1),0)-ifnull((select sum(dt.jumlah) from detail_transaksi dt join transaksi t on t.id_transaksi=dt.id_transaksi where dt.id_barang=id_barang and t.status=1),0) where b.id_barang=id_barang;
END$$

DELIMITER ;

-- --------------------------------------------------------

--
-- Table structure for table `barang`
--

CREATE TABLE IF NOT EXISTS `barang` (
  `id_barang` int(11) NOT NULL AUTO_INCREMENT,
  `nama` varchar(45) DEFAULT NULL,
  `stock` double DEFAULT NULL,
  `harga_IDR` double DEFAULT NULL,
  `harga_USD` double NOT NULL,
  `satuan_jual` bit(1) DEFAULT NULL,
  PRIMARY KEY (`id_barang`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=14 ;

-- --------------------------------------------------------

--
-- Table structure for table `cicilan`
--

CREATE TABLE IF NOT EXISTS `cicilan` (
  `id_cicilan` int(11) NOT NULL AUTO_INCREMENT,
  `jumlah_bayar` double DEFAULT NULL,
  `tgl_cicilan` date DEFAULT NULL,
  `id_kredit` int(11) DEFAULT NULL,
  `jenis` varchar(10) NOT NULL,
  `no_giro` varchar(15) NOT NULL,
  PRIMARY KEY (`id_cicilan`),
  KEY `fk_tb_cicilan_kredit` (`id_kredit`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=6 ;

-- --------------------------------------------------------

--
-- Table structure for table `detail_transaksi`
--

CREATE TABLE IF NOT EXISTS `detail_transaksi` (
  `id_detail_transaksi` char(10) NOT NULL,
  `jumlah` double DEFAULT NULL,
  `harga` double DEFAULT NULL,
  `mata_uang` bit(1) DEFAULT NULL,
  `id_barang` int(11) DEFAULT NULL,
  `id_transaksi` char(10) DEFAULT NULL,
  PRIMARY KEY (`id_detail_transaksi`),
  KEY `fk_tb_detail_transaksi_transaksi` (`id_transaksi`),
  KEY `fk_tb_detail_transaksi_barang` (`id_barang`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `kredit`
--

CREATE TABLE IF NOT EXISTS `kredit` (
  `id_kredit` int(11) NOT NULL AUTO_INCREMENT COMMENT '	',
  `tgl_akhir_pelunasan` date DEFAULT NULL,
  `status` bit(1) DEFAULT NULL,
  `id_transaksi` char(10) DEFAULT NULL,
  PRIMARY KEY (`id_kredit`),
  KEY `fk_tb_kredit_transaksi` (`id_transaksi`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=10 ;

-- --------------------------------------------------------

--
-- Table structure for table `pembeli`
--

CREATE TABLE IF NOT EXISTS `pembeli` (
  `id_pembeli` int(11) NOT NULL AUTO_INCREMENT,
  `nama` varchar(45) DEFAULT NULL,
  `alamat` varchar(45) DEFAULT NULL,
  `telp` varchar(15) DEFAULT NULL,
  `email` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`id_pembeli`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=2 ;

-- --------------------------------------------------------

--
-- Table structure for table `pembelian`
--

CREATE TABLE IF NOT EXISTS `pembelian` (
  `id_pembelian` char(10) NOT NULL,
  `tgl_beli` datetime DEFAULT NULL,
  `kurs` int(11) DEFAULT NULL,
  `harga_total` int(11) DEFAULT NULL,
  `status` bit(1) DEFAULT NULL,
  `id_supplier` int(11) DEFAULT NULL,
  PRIMARY KEY (`id_pembelian`),
  KEY `fk_tb_pembelian_supplier` (`id_supplier`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `pembelian_detail`
--

CREATE TABLE IF NOT EXISTS `pembelian_detail` (
  `id_pembelian_detail` char(10) NOT NULL,
  `jumlah` int(11) DEFAULT NULL,
  `harga` double DEFAULT NULL,
  `mata_uang` bit(1) DEFAULT NULL,
  `id_barang` int(11) DEFAULT NULL,
  `id_pembelian` char(10) DEFAULT NULL,
  PRIMARY KEY (`id_pembelian_detail`),
  KEY `fk_tb_pembelian_detail_pembelian` (`id_pembelian`),
  KEY `fk_tb_pembelian_detail_barang` (`id_barang`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `pengguna`
--

CREATE TABLE IF NOT EXISTS `pengguna` (
  `nama_pengguna` varchar(50) NOT NULL,
  `password` varchar(45) DEFAULT NULL,
  `role` smallint(1) NOT NULL,
  PRIMARY KEY (`nama_pengguna`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `supplier`
--

CREATE TABLE IF NOT EXISTS `supplier` (
  `id_supplier` int(11) NOT NULL AUTO_INCREMENT,
  `nama` varchar(45) DEFAULT NULL,
  `alamat` varchar(45) DEFAULT NULL,
  `telp` varchar(15) DEFAULT NULL,
  `email` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`id_supplier`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=2 ;

-- --------------------------------------------------------

--
-- Table structure for table `surat_jalan`
--

CREATE TABLE IF NOT EXISTS `surat_jalan` (
  `id_surat_jalan` char(10) NOT NULL,
  `kendaraan` varchar(45) DEFAULT NULL,
  `no_kendaraan` varchar(45) DEFAULT NULL,
  `id_transaksi` char(10) DEFAULT NULL,
  PRIMARY KEY (`id_surat_jalan`),
  KEY `fk_surat_jalan_1` (`id_transaksi`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `transaksi`
--

CREATE TABLE IF NOT EXISTS `transaksi` (
  `id_transaksi` char(10) NOT NULL,
  `tgl_jual` datetime DEFAULT NULL,
  `kurs` int(11) DEFAULT NULL,
  `harga_total` int(11) DEFAULT NULL,
  `harga_totalUSD` double NOT NULL,
  `mata` bit(1) NOT NULL,
  `status` bit(1) DEFAULT NULL,
  `id_pembeli` int(11) DEFAULT NULL,
  PRIMARY KEY (`id_transaksi`),
  KEY `fk_transaksi_pembeli` (`id_pembeli`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Constraints for dumped tables
--

--
-- Constraints for table `cicilan`
--
ALTER TABLE `cicilan`
  ADD CONSTRAINT `fk_tb_cicilan_kredit` FOREIGN KEY (`id_kredit`) REFERENCES `kredit` (`id_kredit`) ON DELETE NO ACTION ON UPDATE NO ACTION;

--
-- Constraints for table `detail_transaksi`
--
ALTER TABLE `detail_transaksi`
  ADD CONSTRAINT `fk_tb_detail_transaksi_barang` FOREIGN KEY (`id_barang`) REFERENCES `barang` (`id_barang`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  ADD CONSTRAINT `fk_tb_detail_transaksi_transaksi` FOREIGN KEY (`id_transaksi`) REFERENCES `transaksi` (`id_transaksi`) ON DELETE NO ACTION ON UPDATE NO ACTION;

--
-- Constraints for table `kredit`
--
ALTER TABLE `kredit`
  ADD CONSTRAINT `fk_tb_kredit_transaksi` FOREIGN KEY (`id_transaksi`) REFERENCES `transaksi` (`id_transaksi`) ON DELETE NO ACTION ON UPDATE NO ACTION;

--
-- Constraints for table `pembelian`
--
ALTER TABLE `pembelian`
  ADD CONSTRAINT `fk_tb_pembelian_supplier` FOREIGN KEY (`id_supplier`) REFERENCES `supplier` (`id_supplier`) ON DELETE NO ACTION ON UPDATE NO ACTION;

--
-- Constraints for table `pembelian_detail`
--
ALTER TABLE `pembelian_detail`
  ADD CONSTRAINT `fk_tb_pembelian_detail_barang` FOREIGN KEY (`id_barang`) REFERENCES `barang` (`id_barang`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  ADD CONSTRAINT `fk_tb_pembelian_detail_pembelian` FOREIGN KEY (`id_pembelian`) REFERENCES `pembelian` (`id_pembelian`) ON DELETE NO ACTION ON UPDATE NO ACTION;

--
-- Constraints for table `surat_jalan`
--
ALTER TABLE `surat_jalan`
  ADD CONSTRAINT `fk_surat_jalan_1` FOREIGN KEY (`id_transaksi`) REFERENCES `transaksi` (`id_transaksi`) ON DELETE NO ACTION ON UPDATE NO ACTION;

--
-- Constraints for table `transaksi`
--
ALTER TABLE `transaksi`
  ADD CONSTRAINT `fk_transaksi_pembeli` FOREIGN KEY (`id_pembeli`) REFERENCES `pembeli` (`id_pembeli`) ON DELETE NO ACTION ON UPDATE NO ACTION;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
