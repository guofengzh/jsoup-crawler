REVOKE ALL PRIVILEGES ON crawler.* FROM 'crawler'@'localhost';
REVOKE ALL PRIVILEGES ON crawler.* FROM 'crawler'@'%';

DROP USER 'crawler'@'localhost';
DROP USER 'crawler'@'%';

DROP DATABASE crawler;
