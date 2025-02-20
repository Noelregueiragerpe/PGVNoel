CREATE DATABASE `nextplore` 

CREATE TABLE `usuario` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `contrasena` varchar(255) DEFAULT NULL,
  `correo` varchar(255) DEFAULT NULL,
  `nombre` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `correo_UNIQUE` (`correo`),
  UNIQUE KEY `nombre_UNIQUE` (`nombre`)
) 

CREATE TABLE `pelicula` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `nombre` varchar(255) NOT NULL,
  `genero` varchar(255) NOT NULL,
  `anio` int NOT NULL,
  PRIMARY KEY (`id`),
  CONSTRAINT `pelicula_chk_1` CHECK ((`anio` >= 1888))
) 

CREATE TABLE `lugar` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `categoria` varchar(255) DEFAULT NULL,
  `ciudad` varchar(255) DEFAULT NULL,
  `coordenadasx` double NOT NULL,
  `coordenadasy` double NOT NULL,
  `nombre` varchar(255) DEFAULT NULL,
  `id_pelicula` bigint DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_lugar_pelicula` (`id_pelicula`),
  CONSTRAINT `fk_lugar_pelicula` FOREIGN KEY (`id_pelicula`) REFERENCES `pelicula` (`id`) ON DELETE CASCADE
) 

CREATE TABLE `notificaciones` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `mensaje` varchar(255) NOT NULL,
  `tipo` varchar(255) DEFAULT NULL,
  `usuario_id` bigint NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_notificaciones_usuario` (`usuario_id`),
  CONSTRAINT `fk_notificaciones_usuario` FOREIGN KEY (`usuario_id`) REFERENCES `usuario` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;


CREATE TABLE `explorado` (
  `id_lugar` bigint NOT NULL,
  `id_usuario` bigint NOT NULL,
  `favorito` bit(1) NOT NULL,
  PRIMARY KEY (`id_lugar`,`id_usuario`),
  KEY `FKpv4v0rmtotsuf1ixr786waqhg` (`id_usuario`),
  CONSTRAINT `FKpv4v0rmtotsuf1ixr786waqhg` FOREIGN KEY (`id_usuario`) REFERENCES `usuario` (`id`),
  CONSTRAINT `FKsombeq5vyrrrm9f78oxl7mk1p` FOREIGN KEY (`id_lugar`) REFERENCES `lugar` (`id`)
) 
