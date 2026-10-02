CREATE DATABASE ds_bd;
USE ds_bd;

CREATE TABLE Vendedores (
cod_vendedor int not null auto_increment primary key,
nome_vendedor VARCHAR(30) NOT NULL,
CPF VARCHAR(14) not null unique,
salario DECIMAL(10,2)
);

DELIMITER //
CREATE PROCEDURE Sp_InsereVendedor2(nome VARCHAR(30), num_CPF VARCHAR(14), Salario_fixo DECIMAL(10,2))
BEGIN 
if exists(SELECT CPF from Vendedores WHERE CPF = Num_CPF) then
SELECT "CPF já cadastrado" AS Mensagem;
ELSE 
INSERT INTO Vendedores(nome_vendedor, CPF, salario) VALUES (nome, num_CPF, Salario_fixo);
SELECT CONCAT("Vendedor(a):", nome, " cadastrado com sucesso!") AS Mensagem;
END IF;
END;
//

CALL Sp_InsereVendedor2("Pedro", "288.234.876-20", 900);

DELIMITER //
CREATE PROCEDURE Sp_ExcluirVendedor(codigo int)
BEGIN 
if not exists(SELECT cod_vendedor from Vendedores WHERE cod_vendedor = codigo) then
SELECT "Código inválido" AS Mensagem;
ELSE 
DELETE FROM Vendedores WHERE cod_vendedor = codigo; 
SELECT CONCAT("Vendedor(a):", nome, " deletado com sucesso!") AS Mensagem;
END IF;
END;
//

CALL Sp_ExcluirVendedor(1);

DELIMITER //
CREATE PROCEDURE Sp_AlteraDados(codigo int, nome VARCHAR(30))
BEGIN 
if not exists(SELECT cod_vendedor from Vendedores WHERE cod_vendedor = codigo) then
SELECT "Código inválido" AS Mensagem;
ELSE 
UPDATE Vendedores SET nome_vendedor = nome WHERE cod_vendedor = codigo; 
SELECT CONCAT("Vendedor(a):", nome, " atualizado com sucesso!") AS Mensagem;
END IF;
END;
//

CALL Sp_AlteraDados(1, "John");



DELIMITER //
CREATE PROCEDURE Sp_Aumenta_Salario(cod INT, porcento INT)
BEGIN

IF NOT EXISTS(SELECT cod_vendedor FROM Vendedores WHERE cod_vendedor = cod) THEN
SELECT 'Código inválido' AS Mensagem;

ELSE UPDATE Vendedores SET salario = salario + (salario * (porcento/100)) WHERE cod_vendedor = cod;
SELECT 'Salário alterado com sucesso!' AS Mensagem;

END IF;
END
//

SELECT * FROM Vendedores;

CALL Sp_Aumenta_Salario(2, 50);
