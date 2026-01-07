-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Jan 07, 2026 at 07:30 PM
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
-- Database: `latriesta`
--

--
-- Dumping data for table `ingredients`
--

INSERT INTO `ingredients` (`id`, `name`) VALUES
(1, 'Paradajková omáčka'),
(2, 'Mozzarella'),
(3, 'Šunka'),
(4, 'Saláma'),
(5, 'Šampióny'),
(6, 'Olivy'),
(7, 'Kapary'),
(8, 'Ananas'),
(9, 'Kukurica'),
(10, 'Paprika'),
(11, 'Cibuľa'),
(12, 'Cesnak'),
(13, 'Bazalka'),
(14, 'Oregano'),
(15, 'Parmazán'),
(16, 'Gorgonzola'),
(17, 'Ricotta'),
(18, 'Bryndza'),
(20, 'Klobása'),
(21, 'Tuniak'),
(22, 'Krevety'),
(23, 'Jalapeño'),
(24, 'Rukola'),
(25, 'Paradajky cherry'),
(26, 'Sušené paradajky'),
(27, 'Pesto'),
(29, 'Smotana'),
(31, 'Chilli'),
(32, 'Bazalkové pesto'),
(33, 'Prosciutto'),
(34, 'Artičoky'),
(35, 'Melón');

--
-- Dumping data for table `orders`
--

INSERT INTO `orders` (`id`, `users_id`, `status`, `note`, `total_price`, `created_at`, `updated_at`, `chef_id`, `courier_id`) VALUES
(1, 2, 'cancelled', 'Prosím bez cibule', 26.80, '2025-12-15 11:30:00', '2026-01-03 21:03:52', NULL, 6),
(10, 32, 'ready', NULL, 10.90, '2025-12-21 10:22:18', '2026-01-05 12:21:50', 33, NULL),
(11, 32, 'delivered', 'poznamka', 6.90, '2025-12-21 10:24:28', '2025-12-21 18:32:50', 33, 36),
(14, 32, 'delivered', '', 7.90, '2025-12-21 18:35:01', '2026-01-05 16:08:31', 33, 36),
(15, 34, 'delivering', 'som v zobore', 21.00, '2025-12-21 19:16:55', '2026-01-07 17:27:22', NULL, 36),
(16, 32, 'delivering', '', 6.90, '2026-01-03 13:06:44', '2026-01-07 17:27:19', 33, 36),
(17, 33, 'ready', '', 8.90, '2026-01-03 14:03:39', '2026-01-05 12:21:41', 33, NULL),
(25, 38, 'cancelled', '', 207.00, '2026-01-05 12:19:25', '2026-01-05 12:20:05', NULL, NULL),
(27, 33, 'ready', '', 7.90, '2026-01-05 12:22:33', '2026-01-05 12:22:37', 33, NULL),
(28, 38, 'cancelled', '', 8.90, '2026-01-05 13:54:28', '2026-01-05 13:54:33', NULL, NULL),
(30, 33, 'delivered', '', 14.00, '2026-01-05 16:07:21', '2026-01-05 16:08:29', 33, 36),
(31, 42, 'cancelled', 'Danke', 21.80, '2026-01-07 14:04:49', '2026-01-07 14:05:04', NULL, NULL),
(32, 42, 'delivered', '', 28.00, '2026-01-07 14:05:44', '2026-01-07 14:08:11', 33, 36),
(33, 36, 'cancelled', '', 8.90, '2026-01-07 14:08:27', '2026-01-07 14:29:02', NULL, NULL),
(34, 33, 'delivered', 'Budem cakat vonku', 54.80, '2026-01-07 14:13:37', '2026-01-07 14:16:07', 33, 36),
(35, 42, 'delivered', '', 9.50, '2026-01-07 14:50:47', '2026-01-07 14:52:16', 33, 36),
(36, 33, 'delivered', 'Bez cibule', 7.90, '2026-01-07 15:04:36', '2026-01-07 15:05:01', 33, 36),
(37, 36, 'preparing', '', 10.50, '2026-01-07 15:05:52', '2026-01-07 15:07:33', 33, NULL),
(38, 36, 'pending', '', 75.40, '2026-01-07 15:06:17', '2026-01-07 15:06:17', NULL, NULL),
(39, 32, 'pending', '', 8.90, '2026-01-07 15:06:36', '2026-01-07 15:06:36', NULL, NULL),
(40, 32, 'preparing', '', 47.40, '2026-01-07 15:06:47', '2026-01-07 15:07:35', 33, NULL),
(41, 32, 'pending', '', 10.90, '2026-01-07 15:06:55', '2026-01-07 15:06:55', NULL, NULL),
(42, 32, 'preparing', '', 40.60, '2026-01-07 15:07:14', '2026-01-07 15:07:40', 33, NULL),
(43, 43, 'delivered', 'modifica di un utente esistente - l\'attributo password non è obbligatorio', 20.40, '2026-01-07 16:07:28', '2026-01-07 16:09:39', 33, 36),
(46, 34, 'delivered', '', 9.90, '2026-01-07 17:26:42', '2026-01-07 17:27:18', 33, 36);

--
-- Dumping data for table `order_items`
--

INSERT INTO `order_items` (`id`, `orders_id`, `pizza_item_id`, `item_name`, `item_size`, `item_price`, `quantity`) VALUES
(1, 1, 17, 'Zakarpátska', 'Stredná', 10.50, 1),
(2, 1, 8, 'Salámová', 'Stredná', 8.90, 1),
(3, 1, 2, 'Margherita', 'Stredná', 7.90, 1),
(19, 10, 9, 'Salámová', 'Veľká', 10.90, 1),
(20, 11, 13, 'Hawai', 'Malá', 6.90, 1),
(21, 12, 23, 'Capricciosa', 'Stredná', 9.90, 3),
(23, 14, 10, 'Quattro Formaggi', 'Malá', 7.90, 1),
(24, 15, 17, 'Zakarpátska', 'Stredná', 10.50, 2),
(25, 16, 7, 'Salámová', 'Malá', 6.90, 1),
(26, 17, 8, 'Salámová', 'Stredná', 8.90, 1),
(34, 24, 9, 'Salámová', 'Veľká', 10.90, 9),
(35, 25, 9, 'Salámová', 'Veľká', 10.90, 10),
(36, 25, 77, 'Quattro Formaggi', 'Veľká', 14.00, 7),
(38, 27, 10, 'Quattro Formaggi', 'Malá', 7.90, 1),
(39, 28, 8, 'Salámová', 'Stredná', 8.90, 1),
(41, 30, 77, 'Quattro Formaggi', 'Veľká', 14.00, 1),
(42, 31, 9, 'Salámová', 'Veľká', 10.90, 2),
(43, 32, 77, 'Quattro Formaggi', 'Veľká', 14.00, 2),
(44, 33, 14, 'Hawai', 'Stredná', 8.90, 1),
(45, 34, 17, 'Zakarpátska', 'Stredná', 10.50, 3),
(46, 34, 31, 'BBQ Kurča', 'Malá', 7.90, 2),
(47, 34, 25, 'Tonno', 'Malá', 7.50, 1),
(48, 35, 20, 'Diavola', 'Stredná', 9.50, 1),
(49, 36, 22, 'Capricciosa', 'Malá', 7.90, 1),
(50, 37, 17, 'Zakarpátska', 'Stredná', 10.50, 1),
(51, 38, 31, 'BBQ Kurča', 'Malá', 7.90, 1),
(52, 38, 17, 'Zakarpátska', 'Stredná', 10.50, 3),
(53, 38, 78, 'Tonno', 'Stredná', 9.00, 4),
(54, 39, 29, 'Vegetariána', 'Stredná', 8.90, 1),
(55, 40, 31, 'BBQ Kurča', 'Malá', 7.90, 6),
(56, 41, 15, 'Hawai', 'Veľká', 10.90, 1),
(57, 42, 9, 'Salámová', 'Veľká', 10.90, 1),
(58, 42, 11, 'Quattro Formaggi', 'Stredná', 9.90, 3),
(59, 43, 10, 'Quattro Formaggi', 'Malá', 7.90, 1),
(60, 43, 18, 'Zakarpátska', 'Veľká', 12.50, 1),
(63, 46, 11, 'Quattro Formaggi', 'Stredná', 9.90, 1);

--
-- Dumping data for table `pizzas`
--

INSERT INTO `pizzas` (`id`, `name`, `description`, `image_url`, `available`, `deleted`, `slug`, `created_at`, `updated_at`) VALUES
(3, 'Salámová', 'Paradajková omáčka, mozzarella, saláma a oregano', 'https://macabaneengaspesie.com/wp-content/uploads/2020/02/pizza-a-lerable-500x500.jpg', 1, 0, 'salamova', '2025-12-16 17:29:34', '2026-01-05 12:04:18'),
(4, 'Quattro Formaggi', 'Biela pizza so štyrmi druhmi syra:  mozzarella, gorgonzola, parmazán a ricotta', 'https://www.insidetherustickitchen.com/wp-content/uploads/2020/07/Quattro-formaggi-pizza-square-Inside-the-rustic-kitchen.jpg', 1, 0, 'quattro-formaggi-1', '2025-12-16 17:29:34', '2026-01-03 23:15:28'),
(5, 'Hawai', 'Paradajková omáčka, mozzarella, šunka a ananas', 'https://magazin.klarstein.sk/wp-content/uploads/2023/02/KS_Magazine_0223_Pizza-Hawaii_1300x1300px.jpg', 1, 0, 'hawai-1', '2025-12-16 17:29:34', '2026-01-03 23:05:55'),
(6, 'Zakarpátska', 'Špeciálna pizza s bryndzou, slaninou, cibuľou a smotanou na bielom základe', 'https://www.modernhoney.com/wp-content/uploads/2019/05/The-Best-3-Cheese-White-Pizza-3.jpg', 1, 0, 'zakarpatska-1', '2025-12-16 17:29:34', '2026-01-07 12:53:03'),
(7, 'Diavola', 'Pikantná pizza s paradajkovou omáčkou, mozzarellou, pikantnou salámou a jalapeño', 'https://thepizzaheaven.com/wp-content/uploads/2021/07/Pizza-Diavola.jpg', 1, 0, 'diavola-1', '2025-12-16 17:29:34', '2026-01-03 23:16:43'),
(8, 'Capricciosa', 'Paradajková omáčka, mozzarella, šunka, šampióny, olivy a artičoky', 'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcT8f7grXkFnmiC0i5oqPU2CxdbK6kHAp5Gnaw&s', 1, 0, 'capricciosa-1', '2025-12-16 17:29:34', '2026-01-03 23:03:44'),
(9, 'Tonno', 'Paradajková omáčka, mozzarella, tuniak, cibuľa a kapary', 'https://assets.tmecosys.cn/image/upload/t_web_rdp_recipe_584x480/img/recipe/ras/Assets/31e9601e-fd7f-487c-9da0-2b9b9120dba1/Derivates/bb9a0e97-ae12-4a31-aba3-5bde7c50ab84.jpg', 1, 0, 'tonno', '2025-12-16 17:29:34', '2026-01-07 18:25:29'),
(10, 'Vegetariána', 'Paradajková omáčka, mozzarella, šampióny, paprika, cibuľa, olivy a kukurica', 'https://www.vindulge.com/wp-content/uploads/2023/02/Vegetarian-Pizza-with-Caramelized-Onions-Mushrooms-Jalapeno-FI.jpg', 1, 0, 'vegetariana-1', '2025-12-16 17:29:34', '2026-01-03 23:12:34'),
(11, 'BBQ Kurča', 'BBQ omáčka, mozzarella, grilované kurča, cibuľa a kukurica', 'https://kristineskitchenblog.com/wp-content/uploads/2025/05/bbq-chicken-pizza-09-2.jpg', 1, 0, 'bbq-kurca', '2025-12-16 17:29:34', '2026-01-07 16:23:59'),
(40, 'Mexicana', 'Lorem ipsum dolor sit amet, consectetur adipiscing. Ut enim ad minim veniam, quis.', 'https://hips.hearstapps.com/hmg-prod/images/spicy-sopressata-pizza-64c955fed4312.jpg?crop=0.668xw:1.00xh;0.167xw,0&resize=1200:*', 1, 0, 'mexicana-1', '2026-01-07 16:33:01', '2026-01-07 17:27:40');

--
-- Dumping data for table `pizza_ingredients`
--

INSERT INTO `pizza_ingredients` (`ingredients_id`, `pizzas_id`) VALUES
(1, 3),
(1, 5),
(1, 7),
(1, 8),
(1, 9),
(1, 10),
(1, 40),
(2, 3),
(2, 4),
(2, 5),
(2, 6),
(2, 7),
(2, 8),
(2, 9),
(2, 10),
(2, 11),
(2, 40),
(3, 5),
(3, 8),
(4, 3),
(4, 7),
(5, 8),
(5, 10),
(5, 11),
(6, 8),
(6, 10),
(7, 9),
(7, 40),
(8, 5),
(9, 10),
(9, 11),
(10, 10),
(10, 40),
(11, 6),
(11, 9),
(11, 10),
(11, 11),
(11, 40),
(14, 3),
(15, 4),
(16, 4),
(17, 4),
(18, 6),
(20, 40),
(21, 9),
(23, 7),
(23, 40),
(29, 6),
(31, 7),
(31, 40),
(34, 8);

--
-- Dumping data for table `pizza_item`
--

INSERT INTO `pizza_item` (`id`, `pizzas_id`, `sizes_id`, `price`, `active`) VALUES
(7, 3, 1, 6.90, 1),
(8, 3, 2, 8.90, 1),
(9, 3, 3, 10.90, 1),
(10, 4, 1, 7.90, 1),
(11, 4, 2, 9.90, 1),
(12, 4, 3, 11.90, 0),
(13, 5, 1, 6.90, 1),
(14, 5, 2, 8.90, 1),
(15, 5, 3, 10.90, 1),
(16, 6, 1, 8.50, 1),
(17, 6, 2, 10.50, 1),
(18, 6, 3, 12.50, 1),
(19, 7, 1, 7.50, 1),
(20, 7, 2, 9.50, 1),
(21, 7, 3, 11.50, 1),
(22, 8, 1, 7.90, 0),
(23, 8, 2, 9.90, 1),
(24, 8, 3, 11.90, 1),
(25, 9, 1, 7.50, 1),
(27, 9, 3, 11.50, 1),
(28, 10, 1, 6.90, 1),
(29, 10, 2, 8.90, 1),
(30, 10, 3, 10.90, 0),
(31, 11, 1, 7.90, 0),
(32, 11, 2, 9.90, 1),
(33, 11, 3, 11.90, 1),
(40, 9, 2, 99.00, 0),
(49, 7, 1, 3.00, 0),
(50, 7, 1, 4.00, 0),
(51, 4, 1, 3.00, 0),
(70, 6, 3, 66.00, 0),
(76, 4, 3, 33.00, 0),
(77, 4, 3, 14.00, 1),
(78, 9, 2, 9.00, 1),
(81, 11, 1, 8.00, 1),
(84, 10, 3, 12.00, 1),
(85, 40, 3, 14.00, 1),
(86, 40, 1, 7.00, 0),
(87, 40, 2, 10.00, 1),
(88, 8, 1, 8.00, 1),
(89, 40, 1, 8.00, 1);

--
-- Dumping data for table `pizza_tags`
--

INSERT INTO `pizza_tags` (`pizzas_id`, `tags_id`) VALUES
(3, 3),
(3, 5),
(3, 10),
(4, 1),
(4, 7),
(4, 8),
(5, 3),
(5, 5),
(6, 3),
(6, 6),
(6, 7),
(6, 23),
(7, 2),
(7, 3),
(8, 3),
(8, 5),
(9, 4),
(9, 5),
(10, 1),
(10, 10),
(11, 3),
(11, 6),
(40, 2),
(40, 3),
(40, 6),
(40, 23);

--
-- Dumping data for table `roles`
--

INSERT INTO `roles` (`id`, `name`) VALUES
(1, 'ADMIN'),
(2, 'CUSTOMER'),
(3, 'CHEF'),
(4, 'COURIER');

--
-- Dumping data for table `sizes`
--

INSERT INTO `sizes` (`id`, `name`, `diameter`, `weight`, `deleted`) VALUES
(1, 'Malá', 25.00, 300, 0),
(2, 'Stredná', 30.00, 450, 0),
(3, 'Veľká', 40.00, 700, 0);

--
-- Dumping data for table `tags`
--

INSERT INTO `tags` (`id`, `name`) VALUES
(1, 'Veganska'),
(2, 'Pikantná'),
(3, 'Mäsová'),
(4, 'Morské plody'),
(5, 'Klasická'),
(6, 'Špeciálna'),
(7, 'Biela'),
(8, 'Syrová'),
(10, 'Populárna'),
(23, 'Regionálna');

--
-- Dumping data for table `users`
--

INSERT INTO `users` (`id`, `roles_id`, `username`, `email`, `password`, `address`, `phone`, `created_at`, `updated_at`, `avatar_url`) VALUES
(1, 1, 'admin', 'admin@latriesta.sk', '$2y$10$92IXUNpkjO0rOQ5byMi.Ye4oKoEa3Ro9llC/.og/at2.uheWG/igi', 'Hlavná 1, Bratislava', '+421901234567', '2025-12-16 17:29:34', '2025-12-16 17:29:34', NULL),
(2, 2, 'jozef_novak', 'jozef. novak@email.sk', '$2y$10$92IXUNpkjO0rOQ5byMi.Ye4oKoEa3Ro9llC/. og/at2.uheWG/igi', 'Lipová 15, Košice', '+421902345678', '2025-12-16 17:29:34', '2025-12-16 17:29:34', NULL),
(6, 4, 'milan_kuriér', 'milan.kurier@latriesta.sk', '$2y$10$92IXUNpkjO0rOQ5byMi. Ye4oKoEa3Ro9llC/.og/at2.uheWG/igi', 'Dopravná 11, Bratislava', '+421906789012', '2025-12-16 17:29:34', '2025-12-16 17:29:34', NULL),
(7, 4, 'lucia_rychla', 'lucia.rychla@latriesta.sk', '$2y$10$92IXUNpkjO0rOQ5byMi.Ye4oKoEa3Ro9llC/.og/at2.uheWG/igi', 'Krátka 3, Bratislava', '+421907890123', '2025-12-16 17:29:34', '2025-12-16 17:29:34', NULL),
(32, 2, 'Test User', 'user@email.com', '$2a$10$F1e5IDw9zbsFDREI0r/FUez7Fh09ouYb61ybmkZ5kIIxW078OSqFi', 'Dražovská 3/2, Nitra', '09507077777', '2025-12-16 18:20:08', '2026-01-07 17:05:12', 'https://img.freepik.com/free-vector/blond-man-young_24877-83197.jpg?semt=ais_hybrid&w=740&q=80'),
(33, 3, 'Test Chef', 'chef@email.com', '$2a$10$KZCxaDhXdyrt4mSueffQbO/Ftvqz/d3h.07Uc//blXhyfKrEhjsgW', 'Dražovská 22, Nitra', '09507079992', '2025-12-16 18:24:08', '2026-01-07 17:04:06', 'https://static.vecteezy.com/system/resources/previews/000/364/628/non_2x/vector-chef-avatar-illustration.jpg'),
(34, 1, 'Test Admin', 'admin@email.com', '$2a$10$sAFRpVInRnEHnH6wcI7GaOCnhJEJdVgBUxKxFYJlBE6LKYStooYte', 'Akacfa utca 36, Bilke', '0968400616', '2025-12-16 18:46:36', '2026-01-07 16:53:20', 'https://pc.net/img/terms/avatar.svg'),
(36, 4, 'Test Courier', 'courier@email.com', '$2a$10$WmzRkNfnpOQQOZUN/g.gruOiymbWj4mePuIwN5JHrRL1sc91VVURm', 'Via Mazzini 32, Trieste', '0968400610', '2025-12-20 22:36:22', '2026-01-07 17:04:36', 'https://static.vecteezy.com/system/resources/previews/019/494/622/non_2x/delivery-man-shipping-boy-avatar-user-person-people-service-flat-sticker-black-style-vector.jpg'),
(38, 1, 'Andri Soima', 'soimaandrii2@gmail.com', '$2a$10$RbkH6nkWsaI6J1uFP8ZUQeRwHnT6OOEXqcQnGdROmO31oK70b8YEe', 'Krmeš 14, Králova pri Senci', '0968400666', '2026-01-05 12:12:29', '2026-01-07 13:42:23', NULL),
(41, 2, 'Admin Zwei', 'roro@gmail.com', '$2a$10$d77auLuuhTrOaodLotu0buefzd2PfW1A2SEPzpcitkMw/QGLwr9N2', '	Genslerstraße 98, Berlin', '09507072005', '2026-01-05 15:03:01', '2026-01-07 13:46:53', NULL),
(42, 2, 'Leonel Van Goggel', 'goggel@email.com', '$2a$10$KOp4B9IXu6G6b8Boi8I68u6XM3Kz44D8MXWF26jgpqVGEPCn369xO', 'Museumstrasse 3, Luzern', '097899354', '2026-01-07 14:03:52', '2026-01-07 14:05:26', NULL),
(43, 3, 'Eleonora di Castello', 'elya@email.com', '$2a$10$YjxJjuux5dS0JioLRDrSb.GKfJzOiKEjYjqRkZYUhLV50J6pAvUfS', 'Via Giaiette 64, Diano Castello', '08906753555', '2026-01-07 16:05:05', '2026-01-07 16:16:47', NULL);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
