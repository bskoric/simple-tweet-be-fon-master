SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `simple_tweet_alati`
--

-- --------------------------------------------------------

--
-- Table structure for table `friendship`
--

CREATE TABLE IF NOT EXISTS `friendship` (
  `user_id` int(7) NOT NULL,
  `friend_id` int(7) NOT NULL,
  `date` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `friendship`
--

INSERT INTO `friendship` (`user_id`, `friend_id`, `date`) VALUES
(1, 2, '2020-08-09 00:00:00'),
(1, 3, '2020-08-09 00:00:00'),
(2, 1, '2020-08-21 22:11:32'),
(3, 1, '2020-08-09 00:00:00');

-- --------------------------------------------------------

--
-- Table structure for table `likes`
--

CREATE TABLE IF NOT EXISTS `likes` (
  `like_id` int(7) NOT NULL,
  `tweet_id` int(7) NOT NULL,
  `user_id` int(7) NOT NULL,
  `date` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP
) ENGINE=InnoDB AUTO_INCREMENT=20 DEFAULT CHARSET=utf8;

--
-- Dumping data for table `likes`
--

INSERT INTO `likes` (`like_id`, `tweet_id`, `user_id`, `date`) VALUES
(2, 3, 1, '2020-08-21 23:57:22'),
(5, 4, 1, '2020-08-22 00:24:18'),
(15, 3, 2, '2020-08-22 00:38:45'),
(16, 29, 3, '2020-08-22 01:54:54'),
(17, 27, 3, '2020-08-22 01:54:56'),
(19, 28, 1, '2020-08-22 14:13:37');

-- --------------------------------------------------------

--
-- Table structure for table `tweet`
--

CREATE TABLE IF NOT EXISTS `tweet` (
  `tweet_id` int(7) NOT NULL,
  `title` varchar(255) NOT NULL,
  `post` text NOT NULL,
  `date` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `user` int(7) NOT NULL
) ENGINE=InnoDB AUTO_INCREMENT=30 DEFAULT CHARSET=utf8;

--
-- Dumping data for table `tweet`
--

INSERT INTO `tweet` (`tweet_id`, `title`, `post`, `date`, `user`) VALUES
(1, 'First tweet', 'This is my first tweet. How are you ?', '2020-08-05 16:00:00', 1),
(3, 'Lorem Ipsum', 'Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat', '2020-08-09 00:00:00', 2),
(4, 'Lorem Ipsum 2 - english', 'Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry''s standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it ', '2020-08-09 00:00:00', 3),
(27, 'Tweet', 'Bird vocalization includes both bird calls and bird songs. In non-technical use, bird songs are the bird sounds that are melodious to the human ear. In ornithology and birding, songs (relatively complex vocalizations) are distinguished by function from calls (relatively simple vocalizations).', '2020-08-22 01:41:42', 1),
(28, 'Twitter', 'Twitter is an American microblogging and social networking service on which users post and interact with messages known as "tweets". Registered users can post, like, and retweet tweets, but unregistered users can only read them. ', '2020-08-22 01:43:19', 2),
(29, 'Football', 'Football is a family of team sports that involve, to varying degrees, kicking a ball to score a goal.', '2020-08-22 01:44:03', 1);

-- --------------------------------------------------------

--
-- Table structure for table `user`
--

CREATE TABLE IF NOT EXISTS `user` (
  `user_id` int(7) NOT NULL,
  `first_name` varchar(255) NOT NULL,
  `last_name` varchar(255) NOT NULL,
  `username` varchar(255) NOT NULL,
  `password` varchar(255) NOT NULL,
  `email` varchar(255) NOT NULL
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8;

--
-- Dumping data for table `user`
--

INSERT INTO `user` (`user_id`, `first_name`, `last_name`, `username`, `password`, `email`) VALUES
(1, 'Branko', 'Skoric', 'bskoric', '$2a$11$d9p/kLVugzX5Sr/3SkXbTueIYfbyTVaHKXzHEYmE8mxC/5wsYZz0S', 'skoric.branko@yahoo.com'),
(2, 'Marko', 'Markovic', 'mmarkovic', '$2a$11$Ck8hPHFOt2htcedZ6hXhI.58T2WEtHazXLTU9RQQOjbrGQl0dZa5G', 'mmarkovic@test.com'),
(3, 'Pera', 'Peric', 'pperic', '$2a$11$tJ6gOp59xkYgePR01z2.0urTiGUUYzo6tzzPXD/PhWGUeQ8WdSZNW', 'pperic@test.com'),
(11, 'Test', 'Tester', 'ttester', '$2a$11$T/U0Cn2rBYEk5nSXJ637wuJSnE6.RNpe6DoSjz44pnjn/uTEfcGwK', 'ttester@email.com');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `friendship`
--
ALTER TABLE `friendship`
  ADD PRIMARY KEY (`user_id`,`friend_id`),
  ADD KEY `friend_friend_fk` (`friend_id`);

--
-- Indexes for table `likes`
--
ALTER TABLE `likes`
  ADD PRIMARY KEY (`like_id`,`tweet_id`,`user_id`),
  ADD KEY `tweet_like_fk` (`tweet_id`),
  ADD KEY `user_like_fk` (`user_id`);

--
-- Indexes for table `tweet`
--
ALTER TABLE `tweet`
  ADD PRIMARY KEY (`tweet_id`),
  ADD KEY `user` (`user`);

--
-- Indexes for table `user`
--
ALTER TABLE `user`
  ADD PRIMARY KEY (`user_id`),
  ADD UNIQUE KEY `email` (`email`),
  ADD UNIQUE KEY `username` (`username`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `likes`
--
ALTER TABLE `likes`
  MODIFY `like_id` int(7) NOT NULL AUTO_INCREMENT,AUTO_INCREMENT=20;
--
-- AUTO_INCREMENT for table `tweet`
--
ALTER TABLE `tweet`
  MODIFY `tweet_id` int(7) NOT NULL AUTO_INCREMENT,AUTO_INCREMENT=30;
--
-- AUTO_INCREMENT for table `user`
--
ALTER TABLE `user`
  MODIFY `user_id` int(7) NOT NULL AUTO_INCREMENT,AUTO_INCREMENT=11;
--
-- Constraints for dumped tables
--

--
-- Constraints for table `friendship`
--
ALTER TABLE `friendship`
  ADD CONSTRAINT `friend_friend_fk` FOREIGN KEY (`friend_id`) REFERENCES `user` (`user_id`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `user_friend_fk` FOREIGN KEY (`user_id`) REFERENCES `user` (`user_id`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Constraints for table `likes`
--
ALTER TABLE `likes`
  ADD CONSTRAINT `tweet_like_fk` FOREIGN KEY (`tweet_id`) REFERENCES `tweet` (`tweet_id`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `user_like_fk` FOREIGN KEY (`user_id`) REFERENCES `user` (`user_id`) ON UPDATE CASCADE;

--
-- Constraints for table `tweet`
--
ALTER TABLE `tweet`
  ADD CONSTRAINT `user_fk` FOREIGN KEY (`user`) REFERENCES `user` (`user_id`) ON DELETE CASCADE ON UPDATE CASCADE;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
