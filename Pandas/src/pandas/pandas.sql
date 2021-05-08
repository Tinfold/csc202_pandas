-- phpMyAdmin SQL Dump
-- version 5.1.0
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: May 08, 2021 at 11:12 PM
-- Server version: 10.4.18-MariaDB
-- PHP Version: 8.0.5

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: pandas
--
CREATE DATABASE IF NOT EXISTS pandas DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci;
USE pandas;

-- --------------------------------------------------------

--
-- Table structure for table bids
--

DROP TABLE IF EXISTS bids;
CREATE TABLE IF NOT EXISTS bids (
  bid double NOT NULL,
  maxBid double NOT NULL,
  custID int(32) NOT NULL,
  itemName varchar(64) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table bids
--

INSERT INTO bids (bid, maxBid, custID, itemName) VALUES
(24000, 32000, 1000, 'The Starry Night'),
(35000, 36000, 1000, 'The Starry Night'),
(40000, 45000, 1000, 'The Starry Night');

-- --------------------------------------------------------

--
-- Table structure for table customers
--

DROP TABLE IF EXISTS customers;
CREATE TABLE IF NOT EXISTS customers (
  custID int(32) NOT NULL,
  name varchar(32) NOT NULL,
  username varchar(32) NOT NULL,
  password varchar(32) NOT NULL,
  PRIMARY KEY (custID)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table customers
--

INSERT INTO customers (custID, name, username, password) VALUES
(1000, 'John Doe', 'jdoe', 'password');

-- --------------------------------------------------------

--
-- Table structure for table items
--

DROP TABLE IF EXISTS items;
CREATE TABLE IF NOT EXISTS items (
  name varchar(32) NOT NULL,
  minBid double NOT NULL,
  increment int(32) NOT NULL,
  PRIMARY KEY (name)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table items
--

INSERT INTO items (name, minBid, increment) VALUES
('American Gothic', 9000, 200),
('Mona Lisa', 10000, 1000),
('The Last Supper', 50000, 5000),
('The Starry Night', 12000, 500),
('The Storm on the Sea of Galilee', 6000, 100);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
