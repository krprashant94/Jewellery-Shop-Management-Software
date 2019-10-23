-- phpMyAdmin SQL Dump
-- version 4.6.5.2
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Jul 09, 2019 at 08:13 PM
-- Server version: 10.1.21-MariaDB
-- PHP Version: 5.6.30

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+05:30";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `shop_database`
--

-- --------------------------------------------------------

--
-- Table structure for table `assets`
--

CREATE TABLE `assets` (
  `goods_id` int(11) NOT NULL,
  `name` varchar(50) NOT NULL,
  `price` int(11) NOT NULL,
  `purchase_date` varchar(10) NOT NULL,
  `dist_id` int(11) NOT NULL,
  `notes` varchar(300) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1 ROW_FORMAT=COMPACT;

-- --------------------------------------------------------

--
-- Table structure for table `bill`
--

CREATE TABLE `bill` (
  `id` int(11) NOT NULL,
  `date` varchar(10) NOT NULL,
  `name` varchar(50) NOT NULL,
  `address` varchar(50) NOT NULL,
  `ph` int(11) NOT NULL,
  `PAN_no` int(11) NOT NULL,
  `aadhar_no` int(11) NOT NULL,
  `purchase_item_id` varchar(500) NOT NULL COMMENT 'split with ,',
  `return_item_id` int(11) NOT NULL,
  `refurnish_item_id` int(11) NOT NULL,
  `prices` int(11) NOT NULL COMMENT 'all item respective price split with '',''',
  `total` int(11) NOT NULL,
  `tax` int(11) NOT NULL,
  `discount` int(11) NOT NULL,
  `g_total` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `contact`
--

CREATE TABLE `contact` (
  `cocntact_id` int(11) NOT NULL,
  `name` varchar(30) NOT NULL,
  `address` varchar(200) NOT NULL,
  `ph` varchar(13) NOT NULL,
  `notes` varchar(200) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `orderlist`
--

CREATE TABLE `orderlist` (
  `order_id` int(11) NOT NULL,
  `order_date` varchar(10) NOT NULL,
  `c_name` varchar(50) NOT NULL,
  `address` varchar(100) NOT NULL,
  `ph` varchar(13) NOT NULL,
  `item_name` varchar(30) NOT NULL,
  `metal` varchar(10) NOT NULL COMMENT 'gold silver other',
  `min_weight` double NOT NULL,
  `max_weight` float NOT NULL,
  `stock_id` int(11) NOT NULL DEFAULT '0',
  `product_id` int(11) NOT NULL DEFAULT '0',
  `advance` float NOT NULL,
  `notes` varchar(200) NOT NULL,
  `status` varchar(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `products`
--

CREATE TABLE `products` (
  `item_id` int(11) NOT NULL,
  `stock_id` int(11) NOT NULL DEFAULT '0',
  `bill_id` int(11) DEFAULT '0',
  `item_name` varchar(50) NOT NULL,
  `date_of_adding` varchar(10) NOT NULL,
  `date_of_sale` varchar(10) NOT NULL DEFAULT '0',
  `weight` float NOT NULL,
  `making_charge` int(11) NOT NULL DEFAULT '0',
  `price` float NOT NULL DEFAULT '0',
  `metal` varchar(10) NOT NULL COMMENT 'gold silver other',
  `metal_rate_1gm` int(11) NOT NULL DEFAULT '0',
  `spical_order` int(1) NOT NULL DEFAULT '0'
) ENGINE=InnoDB DEFAULT CHARSET=latin1 ROW_FORMAT=COMPACT;

--
-- Dumping data for table `products`
--

INSERT INTO `products` (`item_id`, `stock_id`, `bill_id`, `item_name`, `date_of_adding`, `date_of_sale`, `weight`, `making_charge`, `price`, `metal`, `metal_rate_1gm`, `spical_order`) VALUES
(1, 1, 0, 'Ring', '2017-10-21', '0', 0, 3, 0, 'Gold', 0, 0),
(2, 1, 0, 'Ring', '2019-07-09', '0', 0, 3, 0, 'Gold', 0, 0),
(3, 1, 0, 'Ring', '2019-07-09', '0', 10, 3, 0, 'Gold', 0, 0),
(4, 1, 0, 'Ring', '2019-07-09', '0', 10, 3, 0, 'Gold', 0, 0),
(5, 1, 0, 'Ring', '2019-07-09', '0', 10, 3, 0, 'Gold', 0, 0),
(6, 1, 0, 'Ring', '2019-07-09', '0', 20, 3, 0, 'Gold', 0, 0);

-- --------------------------------------------------------

--
-- Table structure for table `refurnish_item`
--

CREATE TABLE `refurnish_item` (
  `id` int(11) NOT NULL,
  `date` varchar(10) NOT NULL,
  `item_name` varchar(10) NOT NULL,
  `work` varchar(20) NOT NULL,
  `weight` float NOT NULL,
  `price` int(11) NOT NULL,
  `status` varchar(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `return_item`
--

CREATE TABLE `return_item` (
  `id` int(11) NOT NULL,
  `date` varchar(10) NOT NULL,
  `description` int(11) NOT NULL,
  `weight` int(11) NOT NULL,
  `percentage` int(11) NOT NULL,
  `money_given` int(11) NOT NULL,
  `current_rate` varchar(30) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `stock`
--

CREATE TABLE `stock` (
  `stock_id` int(11) NOT NULL,
  `purchase_date` varchar(10) NOT NULL,
  `item_name` varchar(50) NOT NULL,
  `metal` varchar(20) NOT NULL,
  `weight` float NOT NULL,
  `stock_price` float NOT NULL,
  `stock_unit` int(11) NOT NULL,
  `distributor_id` int(11) NOT NULL,
  `order_id` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `stock`
--

INSERT INTO `stock` (`stock_id`, `purchase_date`, `item_name`, `metal`, `weight`, `stock_price`, `stock_unit`, `distributor_id`, `order_id`) VALUES
(1, '21-10-2017', 'Ring', 'Gold', 0, 132, 44, 10, 0);

-- --------------------------------------------------------

--
-- Table structure for table `tax`
--

CREATE TABLE `tax` (
  `tax_name` int(11) NOT NULL,
  `per_amt` tinyint(1) NOT NULL,
  `unit` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `worker`
--

CREATE TABLE `worker` (
  `worker_id` int(11) NOT NULL,
  `name` varchar(50) NOT NULL,
  `address` varchar(200) NOT NULL,
  `ph` int(11) NOT NULL,
  `work_name` varchar(30) NOT NULL,
  `pay_scale` int(11) NOT NULL,
  `doj` varchar(15) NOT NULL,
  `dol` varchar(15) NOT NULL,
  `id_type` varchar(30) NOT NULL,
  `id_no` varchar(30) NOT NULL,
  `work_status` varchar(1) NOT NULL DEFAULT 'Y'
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `works`
--

CREATE TABLE `works` (
  `work_id` int(11) NOT NULL,
  `worker_id` int(11) NOT NULL,
  `type` varchar(10) NOT NULL COMMENT 'order or refurnish or other specify',
  `type_id` int(11) NOT NULL,
  `item_name` varchar(20) NOT NULL,
  `date_of_start` varchar(15) NOT NULL,
  `date_of_return` varchar(15) NOT NULL COMMENT 'check for complete',
  `metal` varchar(8) NOT NULL,
  `given_matel_wt` float NOT NULL,
  `return_metal_wt` float NOT NULL,
  `item_wt` float NOT NULL,
  `price_paid` int(11) NOT NULL,
  `metal_paid` float NOT NULL,
  `notes` varchar(100) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Indexes for dumped tables
--

--
-- Indexes for table `bill`
--
ALTER TABLE `bill`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `contact`
--
ALTER TABLE `contact`
  ADD PRIMARY KEY (`cocntact_id`);

--
-- Indexes for table `orderlist`
--
ALTER TABLE `orderlist`
  ADD PRIMARY KEY (`order_id`);

--
-- Indexes for table `products`
--
ALTER TABLE `products`
  ADD PRIMARY KEY (`item_id`);

--
-- Indexes for table `refurnish_item`
--
ALTER TABLE `refurnish_item`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `return_item`
--
ALTER TABLE `return_item`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `stock`
--
ALTER TABLE `stock`
  ADD PRIMARY KEY (`stock_id`);

--
-- Indexes for table `worker`
--
ALTER TABLE `worker`
  ADD PRIMARY KEY (`worker_id`);

--
-- Indexes for table `works`
--
ALTER TABLE `works`
  ADD PRIMARY KEY (`work_id`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `orderlist`
--
ALTER TABLE `orderlist`
  MODIFY `order_id` int(11) NOT NULL AUTO_INCREMENT;
--
-- AUTO_INCREMENT for table `products`
--
ALTER TABLE `products`
  MODIFY `item_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=9;
--
-- AUTO_INCREMENT for table `refurnish_item`
--
ALTER TABLE `refurnish_item`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;
--
-- AUTO_INCREMENT for table `return_item`
--
ALTER TABLE `return_item`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;
--
-- AUTO_INCREMENT for table `stock`
--
ALTER TABLE `stock`
  MODIFY `stock_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;
--
-- AUTO_INCREMENT for table `works`
--
ALTER TABLE `works`
  MODIFY `work_id` int(11) NOT NULL AUTO_INCREMENT;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
