-- phpMyAdmin SQL Dump
-- version 4.1.6
-- http://www.phpmyadmin.net
--
-- Host: 127.0.0.1
-- Generation Time: Mar 20, 2016 at 01:36 PM
-- Server version: 5.5.36
-- PHP Version: 5.4.25

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;

--
-- Database: `virtual`
--

-- --------------------------------------------------------

--
-- Table structure for table `56d7cb0ed693c3.74576617`
--

CREATE TABLE IF NOT EXISTS `56d7cb0ed693c3.74576617` (
  `pid` varchar(11) DEFAULT NULL,
  `pname` varchar(100) DEFAULT NULL,
  `prodprice` int(10) DEFAULT NULL,
  `prodqty` int(100) DEFAULT NULL,
  `prodsize` int(10) DEFAULT NULL,
  `status` varchar(10) DEFAULT NULL,
  `deleteflag` int(10) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `56d7cb0ed693c3.74576617`
--

INSERT INTO `56d7cb0ed693c3.74576617` (`pid`, `pname`, `prodprice`, `prodqty`, `prodsize`, `status`, `deleteflag`) VALUES
('1045', 'maggi', 36, 3, 10, 'pending', 1),
('1046', 'ponds facewash', 100, 1, 250, 'pending', 0),
('1046', 'ponds facewash', 200, 1, 500, 'pending', 0);

-- --------------------------------------------------------

--
-- Table structure for table `56ed30f207a124.16587780`
--

CREATE TABLE IF NOT EXISTS `56ed30f207a124.16587780` (
  `pid` varchar(11) DEFAULT NULL,
  `pname` varchar(100) DEFAULT NULL,
  `prodprice` int(10) DEFAULT NULL,
  `prodqty` int(100) DEFAULT NULL,
  `prodsize` int(10) DEFAULT NULL,
  `status` varchar(10) DEFAULT NULL,
  `deleteflag` int(10) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `56ed30f207a124.16587780`
--

INSERT INTO `56ed30f207a124.16587780` (`pid`, `pname`, `prodprice`, `prodqty`, `prodsize`, `status`, `deleteflag`) VALUES
('1046', 'ponds facewash', 200, 1, 500, 'pending', 0),
('1046', 'ponds facewash', 200, 1, 500, 'pending', 0),
('1045', 'maggi', 12, 1, 10, 'pending', 0);

-- --------------------------------------------------------

--
-- Table structure for table `c1001`
--

CREATE TABLE IF NOT EXISTS `c1001` (
  `pid` varchar(11) DEFAULT NULL,
  `pname` varchar(100) DEFAULT NULL,
  `prodprice` int(10) DEFAULT NULL,
  `prodqty` int(100) DEFAULT NULL,
  `prodsize` int(10) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `c1001`
--

INSERT INTO `c1001` (`pid`, `pname`, `prodprice`, `prodqty`, `prodsize`) VALUES
('1045', 'maggi', 10, 100, 1000);

-- --------------------------------------------------------

--
-- Table structure for table `counterdetails`
--

CREATE TABLE IF NOT EXISTS `counterdetails` (
  `custid` varchar(100) NOT NULL,
  `counterno` int(10) NOT NULL,
  `Status` varchar(10) NOT NULL,
  `bagready` varchar(10) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `counterdetails`
--

INSERT INTO `counterdetails` (`custid`, `counterno`, `Status`, `bagready`) VALUES
('56d7cb0ed693c3.74576617', 2, 'shopping', 'no'),
('56d7cb0ed693c3.74576617', 3, 'shopping', 'no'),
('56d7cb0ed693c3.74576617', 1, 'shopping', 'no'),
('56ed30f207a124.16587780', 2, 'shopping', 'no');

-- --------------------------------------------------------

--
-- Table structure for table `countval`
--

CREATE TABLE IF NOT EXISTS `countval` (
  `value` int(10) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `countval`
--

INSERT INTO `countval` (`value`) VALUES
(3);

-- --------------------------------------------------------

--
-- Table structure for table `prodmaster`
--

CREATE TABLE IF NOT EXISTS `prodmaster` (
  `prodid` varchar(100) NOT NULL,
  `prodname` varchar(100) NOT NULL,
  `prodesc` varchar(1000) NOT NULL,
  PRIMARY KEY (`prodid`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `prodmaster`
--

INSERT INTO `prodmaster` (`prodid`, `prodname`, `prodesc`) VALUES
('1045', 'maggi', 'instant '),
('1046', 'ponds facewash', 'creame');

-- --------------------------------------------------------

--
-- Table structure for table `prodspecific`
--

CREATE TABLE IF NOT EXISTS `prodspecific` (
  `prodid` varchar(100) NOT NULL,
  `size` int(100) NOT NULL,
  `qty` int(100) NOT NULL,
  `price` int(100) NOT NULL,
  KEY `prodid` (`prodid`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `prodspecific`
--

INSERT INTO `prodspecific` (`prodid`, `size`, `qty`, `price`) VALUES
('1046', 250, 19, 100),
('1046', 500, 17, 200),
('1045', 10, 19, 12),
('1045', 20, 20, 22);

-- --------------------------------------------------------

--
-- Table structure for table `product`
--

CREATE TABLE IF NOT EXISTS `product` (
  `Prodid` int(10) NOT NULL,
  `prodname` varchar(100) NOT NULL,
  `desc` varchar(200) NOT NULL,
  `prodqty` int(10) NOT NULL,
  `prodprice` int(10) NOT NULL,
  `prodman` date NOT NULL,
  `prodexp` date NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `product`
--

INSERT INTO `product` (`Prodid`, `prodname`, `desc`, `prodqty`, `prodprice`, `prodman`, `prodexp`) VALUES
(1045, 'Maggi', '2mins instant noodles', 8, 10, '2016-01-06', '2016-02-11'),
(1046, 'ponds', 'cream', 7, 140, '2016-02-01', '2016-02-29');

--
-- Constraints for dumped tables
--

--
-- Constraints for table `prodspecific`
--
ALTER TABLE `prodspecific`
  ADD CONSTRAINT `prodspecific_ibfk_1` FOREIGN KEY (`prodid`) REFERENCES `prodmaster` (`prodid`);

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
