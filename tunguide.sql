-- phpMyAdmin SQL Dump
-- version 4.8.5
-- https://www.phpmyadmin.net/
--
-- Hôte : 127.0.0.1
-- Généré le :  mer. 08 jan. 2020 à 20:57
-- Version du serveur :  10.1.38-MariaDB
-- Version de PHP :  7.3.2

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET AUTOCOMMIT = 0;
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de données :  `tunguide`
--

-- --------------------------------------------------------

--
-- Structure de la table `activities`
--

CREATE TABLE `activities` (
  `activity_id` int(2) NOT NULL,
  `activity_name` varchar(50) NOT NULL,
  `activity_adresse` varchar(50) NOT NULL,
  `activity_image` int(2) NOT NULL,
  `activity_price` float NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Structure de la table `hotels`
--

CREATE TABLE `hotels` (
  `hotel_id` int(11) NOT NULL,
  `hotel_name` varchar(50) NOT NULL,
  `hotel_adresse` varchar(50) NOT NULL,
  `hotel_image` text NOT NULL,
  `hotel_star` int(2) NOT NULL,
  `hotel_price` double NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Déchargement des données de la table `hotels`
--

INSERT INTO `hotels` (`hotel_id`, `hotel_name`, `hotel_adresse`, `hotel_image`, `hotel_star`, `hotel_price`) VALUES
(1, 'Royal Victoria', 'La Medina', 'default_hotel.png', 4, 210),
(2, 'Ibis Sfax', 'Sfax', 'default_hotel.png', 3, 140),
(3, 'Hasdrubal Thalassa & Spa', 'Hammamet', 'default_hotel.png', 5, 350),
(4, 'Concorde Green Park Palace', 'Sousse', 'default_hotel.png', 5, 430),
(5, 'TUI MAGIC LIFE Africana', 'Hammamet', 'default_hotel.png', 5, 755),
(6, 'The Pearl', 'Sousse', 'default_hotel.png', 5, 200),
(7, 'La Badira', 'Hammamet', 'default_hotel.png', 5, 300),
(8, 'nom', 'adrese', 'default_hotel.png', 5, 500),
(9, 'gfgf', 'gf', 'default_hotel.png', 1, 2),
(10, 'hh', 'hah', 'f0821bf4cb19cf5eacb92b948eb4686b', 12, 2);

-- --------------------------------------------------------

--
-- Structure de la table `restaurants`
--

CREATE TABLE `restaurants` (
  `resto_id` int(11) NOT NULL,
  `resto_name` varchar(50) NOT NULL,
  `resto_adresse` varchar(50) NOT NULL,
  `resto_image` text NOT NULL,
  `resto_star` int(2) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Déchargement des données de la table `restaurants`
--

INSERT INTO `restaurants` (`resto_id`, `resto_name`, `resto_adresse`, `resto_image`, `resto_star`) VALUES
(1, 'El Ali', 'Tunis', 'default_restaurant.png', 3),
(2, 'Fondouk El Attarine', 'Tunis', 'default_restaurant.png', 3),
(3, 'El Walima', 'Tunis', 'default_restaurant.png', 3),
(4, 'Restaurant Lemdina', 'Tunis', 'default_restaurant.png', 3),
(5, 'Cote jardin', 'Lac', 'default_restaurant.png', 3),
(6, 'Le pirate', 'Monastir', 'default_restaurant.png', 5);

-- --------------------------------------------------------

--
-- Structure de la table `user`
--

CREATE TABLE `user` (
  `id` int(11) NOT NULL,
  `unique_id` varchar(23) NOT NULL,
  `name` varchar(50) NOT NULL,
  `lastName` varchar(50) DEFAULT NULL,
  `email` varchar(100) NOT NULL,
  `phoneNumber` int(11) DEFAULT NULL,
  `encrypted_password` varchar(128) NOT NULL,
  `salt` varchar(16) NOT NULL,
  `created_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `image_url` varchar(255) NOT NULL DEFAULT 'default_avatar.png'
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Déchargement des données de la table `user`
--

INSERT INTO `user` (`id`, `unique_id`, `name`, `lastName`, `email`, `phoneNumber`, `encrypted_password`, `salt`, `created_at`, `updated_at`, `image_url`) VALUES
(1, 'fed1128b-50bf-4be1-a7f4', 'asma', '', 'asma@gmail.com', 0, 'f11a5e25417726dad7acb6f05e1ac2988aaf37094cfd1163ecead740c10481d842a9fb2d6ce839ceea36d288388355c89c1edd808e829ce1785a2cd2', '9f7f630973d60db0', '2019-11-24 21:35:21', '2019-11-24 21:35:21', 'default_avatar.png'),
(2, 'd9185be3-3b73-4596-b825', 'mouna', '', 'asma1@gmail.com', 0, '88bb559b85272d2cad473fddeeb71049ad272fa6d94974cc5cea14871c2267209ef8961e3bcc1d5d7b0b12ae9e26462ca4640ce13e0db55e962385db', '39fcffa38d117428', '2019-11-24 22:20:10', '2019-11-24 22:20:10', 'default_avatar.png'),
(3, 'bba57481-76a4-4fcf-a549', 'houda', '', 'hou@hotmail.fr', 0, '81a092157e953215b85e27097a4b776b35c6946c56ff91143913e7012cd9ba44a5ecf88a37eec1109d4fc9c86bda5ce82952a80ccd14de0aed9b81f3', '0a124440785ca626', '2019-11-24 22:27:13', '2019-11-24 22:27:13', 'default_avatar.png'),
(4, '44afa384-cf5e-478a-962d', 'asmaaa', '', 'test@hotmail.com', 0, '77a8b4a4bd75153d5dfe2e5406e0ab4e3ea214d58813600ea9699cb2e210180e8596ad2180ac0ea4e188c4945dcb2ae3fdfac5657074c7d423739b4b', 'b9d857cf5f109947', '2019-11-25 01:26:48', '2019-11-25 01:26:48', 'default_avatar.png'),
(5, '709c1949-9404-4c9f-9324', 'uuu', '', 'test', 0, '66c47d874bafd28af9bdc3e104f022cc8dfddc2c0f5ac48633faad5f8287d1a0f9d86ccf2e613e877d5f0c8b7cade93deab16c1feb6715cb08368b3e', '2cff28eb2b3833c8', '2019-11-25 01:28:30', '2019-11-25 01:28:30', 'default_avatar.png'),
(6, 'e48061f0-a66d-4df4-ae22', 'name', '', 'email@email.fr', 0, '862edf8987c9f041ab2485fe9f43969d29c1df70d3294c0141354e094abcf780b5bdbd05b0440ce6bfa76491f6da5fa3d75b7d6c029338bab597d226', 'b6018cfbd7087091', '2019-11-25 08:33:42', '2019-11-25 08:33:42', 'default_avatar.png'),
(7, 'e738738b-cf56-40b5-b8c6', 'yasmine', '', 'yasmije@esprit.tn', 0, '54a0530706f99f1d0a3ddf1be528ab4b8a319cf93d139305d5a20fdb9d84f5121b7e3886dccb1e36ef936c7dd422e101566c6c63e7699cf50c59821e', '0f59dd48e7ab8133', '2019-11-25 10:38:07', '2019-11-25 10:38:07', 'default_avatar.png'),
(8, '56d9fad6-5090-4b76-80a5', 'marouane', '', 'mar@esprit.tn', 0, 'd7c5a1de3c9d207a5ce32c364298b9e7b319d9dcac93a58019dd93c4ec13603247762cf3bd82fae4e81eead10ab049230d6cdbdc51090b57c0d42351', 'f0da4d71739aed84', '2019-11-25 10:43:43', '2019-11-25 10:43:43', 'default_avatar.png'),
(9, 'e32498f3-2353-49a6-a011', 'sarra', '', 'sarra@gmail.com', 0, '921ea2480229c49e006df7854fa56ecd6b43aebcc00fa181474a3338c1bef9999cb499be61836a918f12a130cba47ed53482fee20612b1e7e9cfeee31569af08', 'd74110ea864fa437', '2019-12-01 13:05:27', '2019-12-01 13:05:27', 'default_avatar.png'),
(10, '05e5296e-eddb-4620-8705', 'yosra', '', 'yosra@gmail.com', 0, '146b9bdf68b736b19ec8de684822eb894ada3db00f1f8133d4bca564828f4015daabb168cd064410ea6726a0f88d847b08cb23ab2a8e3c2c3a3fbadbffde0461', '525b10dc359468a2', '2019-12-01 14:28:00', '2019-12-01 14:28:00', 'default_avatar.png'),
(11, 'ad87c1aa-a4d8-45c1-acce', 'imed', '', 'imed@gmail.com', 0, '4caa4094519e1478a8b8cdfdf7ac2984f611cd7d33d9549cae88775ec6bc221bc01d15459316ee972f4c5cd8f8034d537e75b1b3e0635701d5be8324adc17f92', '5057cbc0df243854', '2019-12-01 18:37:28', '2019-12-01 18:37:28', 'default_avatar.png'),
(12, 'f30961fe-7817-4b49-ab69', 'amine', NULL, 'a@gmail.com', 147, '0cee2205856d52bf0757d922ae8c98ae4445383ff7d549b44034b9c610f9cad1748e184805ff0d9f2642a2c81865c11e6f0f4d153528cfe35b9dd1c04f2bab1d', 'cc270e75d50bbefe', '2019-12-01 19:29:04', '2019-12-01 19:29:04', 'default_avatar.png'),
(13, 'b30485e3-6910-404b-86e1', 'leila', 'zee', 'leila@gmail.com', 125, '7b92e809b12fd853322ff1ae8be0bffaccfc4b91f746b9620f57a8901c02130517925ba26fcfdbd84bc11bbf81a9600a70d183b945d66531010ae5730a164b07', 'f853d944bc7df432', '2019-12-01 19:33:22', '2019-12-01 19:33:22', 'default_avatar.png'),
(14, '3737e424-1810-4abb-a9c9', 'lamis', NULL, 'lamis@gmail.fr', NULL, '646bae3d4e444fcb00f1abac73f59dd5202fd88abbab2d049db3e258be1e0f61d50d8a45df538f20587c11720ac809fb6982c7a86f863726821a352ff1c735b7', '3864cb024f7dcc35', '2019-12-01 21:36:33', '2019-12-01 21:36:33', 'default_avatar.png'),
(15, 'c2cddac0-4161-4441-a83b', 'Marwen', 'Marwen', 'marwen@gmail.com', 12345678, '7d3e87043625c96323095c5a0bc33d9efb5cddf73ea75a09f609d97af38bffd96c0670791be1955c700793f06a74978d18e879aae16eb562d033a9b8e7adaa20', 'a415af9f31852951', '2019-12-31 17:38:06', '2019-12-31 17:38:06', '007f89ff0e787d399f528a02a7a5ccbd');

--
-- Index pour les tables déchargées
--

--
-- Index pour la table `hotels`
--
ALTER TABLE `hotels`
  ADD PRIMARY KEY (`hotel_id`);

--
-- Index pour la table `restaurants`
--
ALTER TABLE `restaurants`
  ADD PRIMARY KEY (`resto_id`);

--
-- Index pour la table `user`
--
ALTER TABLE `user`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `unique_id` (`unique_id`);

--
-- AUTO_INCREMENT pour les tables déchargées
--

--
-- AUTO_INCREMENT pour la table `hotels`
--
ALTER TABLE `hotels`
  MODIFY `hotel_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=11;

--
-- AUTO_INCREMENT pour la table `restaurants`
--
ALTER TABLE `restaurants`
  MODIFY `resto_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=7;

--
-- AUTO_INCREMENT pour la table `user`
--
ALTER TABLE `user`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=16;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
