-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Oct 05, 2025 at 05:02 AM
-- Server version: 10.4.32-MariaDB
-- PHP Version: 8.2.12

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `user_db`
--

-- --------------------------------------------------------

--
-- Table structure for table `reservations`
--

CREATE TABLE `reservations` (
  `pnr` varchar(20) NOT NULL,
  `name` varchar(50) DEFAULT NULL,
  `gender` varchar(10) DEFAULT NULL,
  `reservationDate` date DEFAULT NULL,
  `train_type` varchar(20) DEFAULT NULL,
  `class_type` varchar(50) DEFAULT NULL,
  `fromPlace` varchar(100) DEFAULT NULL,
  `toPlace` varchar(100) DEFAULT NULL,
  `train_no` int(10) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Table structure for table `trains`
--

CREATE TABLE `trains` (
  `train_no` int(11) NOT NULL,
  `train_name` varchar(255) DEFAULT NULL,
  `train_type` varchar(255) DEFAULT NULL,
  `availTime` time DEFAULT NULL,
  `fromPlace` varchar(100) DEFAULT NULL,
  `toPlace` varchar(100) DEFAULT NULL,
  `ac_seats` int(11) DEFAULT 0,
  `sleeper_seats` int(11) DEFAULT 0
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `trains`
--

INSERT INTO `trains` (`train_no`, `train_name`, `train_type`, `availTime`, `fromPlace`, `toPlace`, `ac_seats`, `sleeper_seats`) VALUES
(120101, 'Rajdhani Express', 'Superfast', '06:00:00', 'Delhi', 'Mumbai', 50, 120),
(120102, 'Shatabdi Express', 'Shatabdi', '07:30:00', 'Delhi', 'Chennai', 40, 100),
(120103, 'Duronto Express', 'Superfast', '08:15:00', 'Mumbai', 'Bangalore', 60, 140),
(120104, 'Garib Rath', 'Express', '05:45:00', 'Chennai', 'Kolkata', 29, 79),
(120105, 'Sampark Kranti', 'Express', '09:00:00', 'Delhi', 'Mysuru', 55, 110),
(120106, 'Tejas Express', 'Superfast', '10:30:00', 'Mumbai', 'Chennai', 45, 90),
(120107, 'Humsafar Express', 'Superfast', '11:00:00', 'Bangalore', 'Delhi', 50, 120),
(120108, 'Kavi Guru Express', 'Express', '12:15:00', 'Kolkata', 'Mumbai', 35, 70),
(120109, 'Vande Bharat', 'Shatabdi', '13:00:00', 'Delhi', 'Bangalore', 60, 130),
(120110, 'Jan Shatabdi', 'Shatabdi', '14:30:00', 'Chennai', 'Mysuru', 25, 60),
(120111, 'Mysuru Express', 'Express', '06:45:00', 'Mysuru', 'Bangalore', 40, 90),
(120112, 'Nizamuddin Express', 'Superfast', '07:50:00', 'Delhi', 'Mumbai', 50, 120),
(120113, 'Konkan Express', 'Express', '08:30:00', 'Mumbai', 'Chennai', 45, 100),
(120114, 'Himalayan Express', 'Superfast', '09:15:00', 'Delhi', 'Kolkata', 55, 110),
(120115, 'Chennai Express', 'Express', '10:00:00', 'Mumbai', 'Chennai', 50, 120),
(120116, 'Swarna Jayanti', 'Superfast', '11:45:00', 'Bangalore', 'Mysuru', 60, 130),
(120117, 'East Coast Express', 'Express', '12:30:00', 'Kolkata', 'Chennai', 35, 80),
(120118, 'West Coast Express', 'Superfast', '13:15:00', 'Mumbai', 'Bangalore', 50, 110),
(120119, 'Kolkata Express', 'Express', '14:00:00', 'Kolkata', 'Delhi', 40, 100),
(120120, 'Howrah Rajdhani', 'Shatabdi', '15:30:00', 'Kolkata', 'Mumbai', 55, 120),
(120121, 'Vijayawada Express', 'Express', '16:00:00', 'Bangalore', 'Chennai', 35, 80),
(120122, 'Kanyakumari Express', 'Superfast', '16:45:00', 'Chennai', 'Bangalore', 60, 130),
(120123, 'Mysuru Shatabdi', 'Shatabdi', '17:30:00', 'Bangalore', 'Mysuru', 50, 110),
(120124, 'Bhopal Express', 'Superfast', '18:15:00', 'Delhi', 'Mumbai', 55, 120),
(120125, 'Patna Express', 'Express', '19:00:00', 'Delhi', 'Kolkata', 40, 100),
(120126, 'Jaipur Express', 'Superfast', '19:45:00', 'Delhi', 'Mysuru', 50, 110),
(120127, 'Goa Express', 'Express', '20:30:00', 'Mumbai', 'Bangalore', 45, 90),
(120128, 'Hampi Express', 'Superfast', '21:15:00', 'Bangalore', 'Mysuru', 50, 120),
(120129, 'Trivandrum Express', 'Express', '22:00:00', 'Chennai', 'Bangalore', 34, 80),
(120130, 'Hyderabad Express', 'Superfast', '22:45:00', 'Mumbai', 'Delhi', 55, 120),
(120131, 'Kochi Express', 'Express', '23:30:00', 'Bangalore', 'Mumbai', 40, 90),
(120132, 'Delhi Express', 'Superfast', '00:15:00', 'Delhi', 'Mumbai', 60, 130),
(120133, 'Mumbai Rajdhani', 'Shatabdi', '01:00:00', 'Mumbai', 'Delhi', 50, 120),
(120134, 'Chennai Rajdhani', 'Shatabdi', '01:45:00', 'Chennai', 'Delhi', 55, 120),
(120135, 'Kolkata Mail', 'Express', '02:30:00', 'Kolkata', 'Delhi', 40, 100),
(120136, 'Mysuru Mail', 'Express', '03:15:00', 'Mysuru', 'Bangalore', 35, 79),
(120137, 'Lucknow Express', 'Superfast', '04:00:00', 'Delhi', 'Mumbai', 50, 120),
(120138, 'Ahmedabad Express', 'Express', '04:45:00', 'Mumbai', 'Delhi', 40, 90),
(120139, 'Patna Rajdhani', 'Shatabdi', '05:30:00', 'Delhi', 'Kolkata', 55, 120),
(120140, 'Trivandrum Mail', 'Express', '06:15:00', 'Chennai', 'Bangalore', 34, 80),
(120141, 'Jaipur Mail', 'Express', '07:00:00', 'Delhi', 'Mysuru', 40, 90),
(120142, 'Goa Mail', 'Express', '07:45:00', 'Mumbai', 'Bangalore', 45, 100),
(120143, 'Bangalore Mail', 'Superfast', '08:30:00', 'Bangalore', 'Mumbai', 50, 120),
(120144, 'Hyderabad Mail', 'Superfast', '09:15:00', 'Mumbai', 'Chennai', 50, 120),
(120145, 'Kochi Mail', 'Express', '10:00:00', 'Bangalore', 'Mysuru', 33, 80),
(120146, 'Delhi Shatabdi', 'Shatabdi', '10:45:00', 'Delhi', 'Mumbai', 50, 110),
(120147, 'Mumbai Shatabdi', 'Shatabdi', '11:30:00', 'Mumbai', 'Chennai', 45, 100),
(120148, 'Chennai Shatabdi', 'Shatabdi', '12:15:00', 'Chennai', 'Bangalore', 50, 120),
(120149, 'Kolkata Shatabdi', 'Shatabdi', '13:00:00', 'Kolkata', 'Delhi', 55, 120),
(120150, 'Bangalore Shatabdi', 'Shatabdi', '13:45:00', 'Bangalore', 'Mumbai', 50, 120);

-- --------------------------------------------------------

--
-- Table structure for table `users`
--

CREATE TABLE `users` (
  `id` int(20) NOT NULL,
  `name` varchar(50) NOT NULL,
  `pass` varchar(300) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `users`
--

INSERT INTO `users` (`id`, `name`, `pass`) VALUES
(1, 'admin', '7676aaafb027c825bd9abab78b234070e702752f625b752e55e55b48e607e358');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `reservations`
--
ALTER TABLE `reservations`
  ADD PRIMARY KEY (`pnr`);

--
-- Indexes for table `trains`
--
ALTER TABLE `trains`
  ADD PRIMARY KEY (`train_no`);

--
-- Indexes for table `users`
--
ALTER TABLE `users`
  ADD PRIMARY KEY (`id`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
