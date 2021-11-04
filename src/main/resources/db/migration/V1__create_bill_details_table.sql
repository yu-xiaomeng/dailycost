CREATE TABLE IF NOT EXISTS `bill_details`(
      `id` varchar(255) NOT NULL,
      `category_id` varchar(255) NOT NULL,
      `type` varchar(255) DEFAULT NULL,
      `amount` decimal(10,2) DEFAULT NULL,
      `note` varchar(1000) DEFAULT NULL,
      `date` date DEFAULT NULL,
      `created_by` varchar(50) NOT NULL,
      `created_time` bigint DEFAULT NULL,
      `updated_time` bigint DEFAULT NULL,
      PRIMARY KEY (`id`)
)ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;