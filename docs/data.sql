CREATE TABLE `book` (
                        `id` int(11) NOT NULL AUTO_INCREMENT,
                        `book_no` int(11) DEFAULT NULL COMMENT '书本编号',
                        `name` varchar(255) NOT NULL COMMENT '书本名称',
                        `total` int(11) NOT NULL COMMENT '书本数量',
                        `status` int(11) DEFAULT '0' COMMENT '0-下架 1-上架',
                        PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE `inventory` (
                             `id` int(11) NOT NULL,
                             `book_no` int(11) NOT NULL COMMENT '图书编号',
                             `total` int(11) NOT NULL DEFAULT '0' COMMENT '库存总数',
                             `sale_num` int(11) NOT NULL DEFAULT '0' COMMENT '出售数量',
                             `residue` int(11) NOT NULL DEFAULT '0' COMMENT '剩余数量',
                             PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE `order` (
                         `id` int(11) NOT NULL,
                         `order_no` varchar(50) DEFAULT NULL COMMENT '订单流水号',
                         `user_id` int(11) NOT NULL COMMENT '用户userId',
                         `book_no` int(11) NOT NULL COMMENT '图书编号',
                         `purchase` int(11) NOT NULL COMMENT '购买数量',
                         `unit_price` decimal(10,2) DEFAULT '0.00' COMMENT '购买单价',
                         `total_price` decimal(10,2) DEFAULT '0.00' COMMENT '购买总价',
                         `status` int(11) DEFAULT '0' COMMENT '0- 初始化 1-成功 2-取消 3-过期',
                         PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE `user` (
                        `id` int(11) NOT NULL AUTO_INCREMENT,
                        `user_id` int(11) DEFAULT NULL COMMENT '用户编号',
                        `name` varchar(50) NOT NULL COMMENT '姓名',
                        `phone` char(11) NOT NULL COMMENT '手机号',
                        `password` varchar(128) NOT NULL COMMENT '密码',
                        PRIMARY KEY (`id`) USING BTREE,
                        KEY `index_phone` (`phone`) USING HASH
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4;