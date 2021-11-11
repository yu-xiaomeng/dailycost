CREATE TABLE IF NOT EXISTS `bill`(
      `id` varchar(255) NOT NULL,
      `time_dimension` varchar(50) NOT NULL,
      `date` date DEFAULT NULL,
      `expense` decimal(10,2) DEFAULT NULL,
      `income` decimal(10,2) DEFAULT NULL,
      `balance` decimal(10,2) DEFAULT NULL,
      `username` varchar(50) NOT NULL,
      PRIMARY KEY (`id`)
)ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;