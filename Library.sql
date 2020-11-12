CREATE USER IF NOT EXISTS jwduser IDENTIFIED BY 'pass';

DROP DATABASE IF EXISTS library;
CREATE DATABASE library DEFAULT CHARACTER SET utf8;

USE library;

GRANT ALL ON library.* TO 'jwduser'@'%';

FLUSH PRIVILEGES;

FLUSH PRIVILEGES;

create table if not exists persistent_logins ( 
  username varchar(100) not null, 
  series varchar(64) primary key, 
  token varchar(64) not null, 
  last_used timestamp not null
);

