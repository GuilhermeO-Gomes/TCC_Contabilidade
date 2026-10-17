USE erp_contabil;
INSERT INTO shared_permissao (codigo, descricao) VALUES
('CONTABILIDADE_CONSULTAR', 'Consultar dados contábeis da empresa vinculada'),
('LANCAMENTO_SALVAR', 'Criar e editar rascunhos contábeis'),
('LANCAMENTO_CONTABILIZAR', 'Contabilizar lançamentos balanceados em exercício aberto'),
('LANCAMENTO_ESTORNAR', 'Estornar lançamentos contabilizados sem apagar o original'),
('LANCAMENTO_EXCLUIR', 'Excluir somente rascunhos contábeis'),
('AUDITORIA_EXECUTAR', 'Executar checagens acadêmicas e registrar inconsistências'),
('AUDITORIA_REVISAR', 'Revisar ou ignorar inconsistências contábeis'),
('ECF_PREPARAR', 'Preparar ECF, apurações, ajustes e signatários'),
('ECF_VALIDAR', 'Validar dados acadêmicos da ECF'),
('ECF_GERAR', 'Registrar arquivo existente e seu hash na ECF acadêmica'),
('PLANO_CONTAS_MANTER', 'Criar, editar e ativar ou inativar contas da empresa')
ON DUPLICATE KEY UPDATE descricao = VALUES(descricao);
