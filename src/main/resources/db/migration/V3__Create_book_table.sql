CREATE TABLE books (
  id int(11) PRIMARY KEY AUTO_INCREMENT COMMENT 'ID',
  title varchar(255) NOT NULL COMMENT 'タイトル',
  price int(11) NOT NULL COMMENT '価格',
  author_id int(11) NOT NULL COMMENT '著者ID',
  category_id int(11) NOT NULL COMMENT 'カテゴリID',
  created_at datetime NOT NULL DEFAULT current_timestamp() COMMENT '登録日時',
  modified_at datetime NOT NULL DEFAULT current_timestamp() ON UPDATE current_timestamp() 
  COMMENT '更新日時'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='書籍';
 