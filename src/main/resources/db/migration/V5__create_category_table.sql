CREATE TABLE IF NOT EXISTS `category`(
      `id` varchar(255) NOT NULL,
      `name` varchar(50) NOT NULL,
      `icon_id` varchar(255) NOT NULL,
      `created_by` varchar(50) NOT NULL,
      PRIMARY KEY (`id`)
)ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;