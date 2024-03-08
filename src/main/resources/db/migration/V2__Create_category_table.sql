CREATE TABLE categories (
  id int(11) PRIMARY KEY AUTO_INCREMENT COMMENT 'ID',
  name varchar(255) NOT NULL DEFAULT '' COMMENT 'カテゴリ名',
  created_at datetime NOT NULL DEFAULT current_timestamp() COMMENT '登録日時',
  modified_at datetime NOT NULL DEFAULT current_timestamp() ON UPDATE current_timestamp() COMMENT '更新日時'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='カテゴリ';
