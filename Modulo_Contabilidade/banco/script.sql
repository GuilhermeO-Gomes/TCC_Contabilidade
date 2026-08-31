CREATE DATABASE IF NOT EXISTS cadastro_java;
USE cadastro_java;

CREATE TABLE IF NOT EXISTS aluno (
    id INT PRIMARY KEY AUTO_INCREMENT,
    nome VARCHAR(100) NOT NULL,
    nome_social VARCHAR(100) NULL,
    cpf CHAR(14) NOT NULL,
    genero VARCHAR(30) NOT NULL,
    afrodescendente BOOLEAN NOT NULL,
    escolaridade_publica BOOLEAN NOT NULL,
    data_nascimento DATE NOT NULL,
    nacionalidade VARCHAR(50) NOT NULL,
    responsavel_legal VARCHAR(100) NOT NULL,
    grau_parentesco VARCHAR(30) NOT NULL,
    habilitacao VARCHAR(150) NOT NULL,
    serie VARCHAR(10) NOT NULL,
    periodo VARCHAR(20) NOT NULL,
    rua VARCHAR(100) NOT NULL, 
    complemento VARCHAR(100) NULL,
    apto VARCHAR(10) NULL,
    bloco VARCHAR(10) NULL,
    bairro VARCHAR(50) NOT NULL,
    cidade VARCHAR(100) NOT NULL,
    CEP CHAR(9) NOT NULL,
    telefone VARCHAR(25) NOT NULL,
    email VARCHAR(100) NOT NULL
);
