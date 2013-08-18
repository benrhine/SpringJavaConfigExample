USE test;

CREATE TABLE IF NOT EXISTS `ROLE` (
  `ID` bigint(20) NOT NULL AUTO_INCREMENT,
  `ROLE` int(11) DEFAULT NULL,
  `USER_ID` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`ID`),
  KEY `FK3580769128426C` (`USER_ID`)
) ENGINE=InnoDB  DEFAULT CHARSET=UTF8 AUTO_INCREMENT=14 ;


CREATE TABLE IF NOT EXISTS `USER` (
  `ID` bigint(20) NOT NULL AUTO_INCREMENT,
  `FIRST_NAME` varchar(30) DEFAULT NULL,
  `LAST_NAME` varchar(30) DEFAULT NULL,
  `USER_PASS` varchar(255) DEFAULT NULL,
  `USER_NAME` varchar(50) DEFAULT NULL,
   `SHORT_USER_NAME` varchar(40) DEFAULT NULL,
  `ENABLED` tinyint(1) NOT NULL,
  PRIMARY KEY (`ID`),
  UNIQUE KEY `USER_NAME` (`USER_NAME`)
) ENGINE=InnoDB  DEFAULT CHARSET=UTF8 AUTO_INCREMENT=14 ;