CREATE TABLE `t_stock` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `stock` int(11) NOT NULL DEFAULT '100',
  PRIMARY KEY (`id`)
);

create TABLE t_cart (
  id int PRIMARY KEY AUTO_INCREMENT,
  stockId int not null,
  userId int not null,
  cart_num int not null DEFAULT 1
);