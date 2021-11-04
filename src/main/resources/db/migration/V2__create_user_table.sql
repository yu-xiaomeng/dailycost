CREATE TABLE IF NOT EXISTS `user`(
      `id` varchar(255) NOT NULL,
      `username` varchar(50) NOT NULL,
      `password` varchar(255) NOT NULL,
      `email` varchar(255) DEFAULT NULL,
      `avatar` varchar(255) DEFAULT NULL,
      `created_time` bigint DEFAULT NULL,
      `updated_time` bigint DEFAULT NULL,
      PRIMARY KEY (`id`)
)ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;