CREATE DATABASE IF NOT EXISTS contabilidade;
USE contabilidade;

CREATE TABLE IF NOT EXISTS plano_contas (
    conta VARCHAR(20) PRIMARY KEY,
    descricao VARCHAR(100) NOT NULL,
    reduzida INT NOT NULL UNIQUE,
    saldo VARCHAR(10) NOT NULL,
    tipo VARCHAR(10) NOT NULL,
    cc VARCHAR(3) NOT NULL,
    situacao VARCHAR(10) NOT NULL
);

INSERT INTO plano_contas (conta, descricao, reduzida, saldo, tipo, cc, situacao) VALUES
('1', 'ATIVO', 100, 'Devedor', 'Sintética', 'Não', 'Ativa'),
('1.1', 'DISPONIBILIDADES', 110, 'Devedor', 'Sintética', 'Não', 'Ativa'),
('1.1.01', 'CAIXA E EQUIVALENTES', 111, 'Devedor', 'Sintética', 'Não', 'Ativa'),
('1.1.01.001', 'Caixa Geral', 112, 'Devedor', 'Analítica', 'Não', 'Ativa'),
('1.1.01.002', 'Fundo Fixo Administrativo', 113, 'Devedor', 'Analítica', 'Sim', 'Ativa'),
('1.1.02', 'CONTAS BANCÁRIAS', 120, 'Devedor', 'Sintética', 'Não', 'Ativa'),
('1.1.02.001', 'Banco Comercial - Conta Corrente', 121, 'Devedor', 'Analítica', 'Não', 'Ativa'),
('1.1.02.002', 'Banco Digital - Conta Corrente', 122, 'Devedor', 'Analítica', 'Não', 'Ativa'),
('1.1.03', 'APLICAÇÕES FINANCEIRAS', 130, 'Devedor', 'Sintética', 'Não', 'Ativa'),
('1.1.03.001', 'Aplicação de Liquidez Imediata', 131, 'Devedor', 'Analítica', 'Não', 'Ativa'),
('1.1.04', 'CLIENTES', 140, 'Devedor', 'Sintética', 'Não', 'Ativa'),
('1.1.04.001', 'Clientes do Mercado Nacional', 141, 'Devedor', 'Analítica', 'Não', 'Ativa'),
('2', 'PASSIVO', 200, 'Credor', 'Sintética', 'Não', 'Ativa'),
('2.1', 'OBRIGAÇÕES OPERACIONAIS', 210, 'Credor', 'Sintética', 'Não', 'Ativa'),
('2.1.01', 'FORNECEDORES', 211, 'Credor', 'Sintética', 'Não', 'Ativa'),
('2.1.01.001', 'Fornecedores Nacionais', 212, 'Credor', 'Analítica', 'Não', 'Ativa'),
('3', 'PATRIMÔNIO LÍQUIDO', 300, 'Credor', 'Sintética', 'Não', 'Ativa'),
('3.1', 'CAPITAL SOCIAL', 310, 'Credor', 'Sintética', 'Não', 'Ativa'),
('3.1.01.001', 'Capital Social Integralizado', 311, 'Credor', 'Analítica', 'Não', 'Ativa'),
('4', 'RECEITAS', 400, 'Credor', 'Sintética', 'Não', 'Ativa'),
('4.1.01.001', 'Receita de Serviços Prestados', 411, 'Credor', 'Analítica', 'Sim', 'Ativa'),
('5', 'DESPESAS', 500, 'Devedor', 'Sintética', 'Não', 'Ativa'),
('5.1', 'DESPESAS ADMINISTRATIVAS', 510, 'Devedor', 'Sintética', 'Sim', 'Ativa'),
('5.1.01.001', 'Materiais de Expediente', 511, 'Devedor', 'Analítica', 'Sim', 'Ativa'),
('5.1.02.001', 'Serviços de Terceiros', 512, 'Devedor', 'Analítica', 'Sim', 'Cancelada');

