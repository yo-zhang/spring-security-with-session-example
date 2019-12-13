-- Dumping structure for table account.account
CREATE TABLE IF NOT EXISTS `account` (
  `id` varchar(20) NOT NULL,
  `password` varchar(100) NOT NULL,
  `phone` varchar(20) DEFAULT NULL,
PRIMARY KEY (`id`),
UNIQUE KEY `phone` (`phone`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- Dumping data for table account.account: ~4 rows (approximately)
INSERT INTO `account` (`id`, `password`, `phone`) VALUES
('andy', '$2a$10$uSbFJPGuE2CUgpk4G0FX2u.Jg5F4ENHi.TbhWZ8K5D3aQhmrta6T.', '12345678'),
('hung', '$2a$10$4fV3TfWmDtKkPMYVWF4ZKuqHOZMBpVw0g9SrVf0mPdGMcHzaCfcwe', '123456789'),
('manson', '$2a$10$GjCT5NyWcLWlVgqY.cSbruOEht7BXHyvP49FbiobRIISMbGJSEu2q', '1234567890'),
('yo', '$2a$10$5oKfCbTtFuOup.99LOHGguxryYCqRKzbW2v5XBqneX97Eqk6T9RZm', '1234567');

-- Dumping structure for table account.account_role
CREATE TABLE IF NOT EXISTS `account_role` (
  `account_id` varchar(20) NOT NULL,
  `role_name` varchar(20) NOT NULL,
PRIMARY KEY (`account_id`,`role_name`),
KEY `fk_accountrole_role_rolename` (`role_name`),
CONSTRAINT `fk_accountrole_account_accountid` FOREIGN KEY (`account_id`) REFERENCES `account` (`id`),
CONSTRAINT `fk_accountrole_role_rolename` FOREIGN KEY (`role_name`) REFERENCES `role` (`role_name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- Dumping data for table account.account_role: ~5 rows (approximately)
INSERT INTO `account_role` (`account_id`, `role_name`) VALUES
('yo', 'ADMIN'),
('andy', 'DBA'),
('hung', 'USER'),
('manson', 'USER'),
('yo', 'USER');

-- Dumping structure for table account.role
CREATE TABLE IF NOT EXISTS `role` (
  `role_name` varchar(20) NOT NULL,
  `role_desc` varchar(50) DEFAULT NULL,
PRIMARY KEY (`role_name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- Dumping data for table account.role: ~3 rows (approximately)
INSERT INTO `role` (`role_name`, `role_desc`) VALUES
('ADMIN', NULL),
('DBA', NULL),
('USER', NULL);
