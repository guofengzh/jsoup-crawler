REVOKE * ON crawler.* FROM 'crawler’@'localhost';
REVOKE * ON crawler.* FROM 'crawler’@'%';

DROP USER 'crawler'@'localhost';
DROP USER 'crawler'@'%';

DROP DATABASE crawler;
