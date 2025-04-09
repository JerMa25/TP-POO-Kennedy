CREATE DATABASE IF NOT EXISTS myPropertyDB;

USE myPropertyDB;

CREATE TABLE IF NOT EXISTS Utilisateur (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nom VARCHAR(100) NOT NULL,
    date_naissance DATE,
    ville VARCHAR(100),
	quartier VARCHAR(20),
    email VARCHAR(100),
    tel_number VARCHAR(20),
    gender ENUM("Masculin","Feminin")
);

CREATE TABLE IF NOT EXISTS stolenPeripheral (
    id INT AUTO_INCREMENT PRIMARY KEY,
    utilID INT,
    marque VARCHAR(100),
    modele VARCHAR(100),
    processeur VARCHAR(100) ,-- Ordi & Tel
    systeme_exploitation VARCHAR(100),
	addresseMac VARCHAR(100),
    ram INT,
    stockage INT,
    vitesse_max INT, -- Modem
    FOREIGN KEY (utilID) REFERENCES Utilisateur(id)
);

CREATE TABLE IF NOT EXISTS declaredPeripheral (
    id INT AUTO_INCREMENT PRIMARY KEY,
    finderID INT,
    stolenObjID INT,
    modele VARCHAR(100),
    reportDate DATE NOT NULL,
    location VARCHAR(100),
    additionalDesc TEXT,
    FOREIGN KEY (finderID) REFERENCES Utilisateur(id),
    FOREIGN KEY (stolenObjID) REFERENCES stolenPeripheral(id)
);
