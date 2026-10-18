create database if not exists erp_contabil
	character set utf8mb4
    collate utf8mb4_unicode_ci;
    
use erp_contabil;
SET FOREIGN_KEY_CHECKS = 0;

DROP TABLE IF EXISTS conciliacao_item;
DROP TABLE IF EXISTS conciliacao_contabil;

DROP TABLE IF EXISTS integracao_contabil;

DROP TABLE IF EXISTS ref_financeiro_extrato_movimento;
DROP TABLE IF EXISTS ref_financeiro_movimentacao;
DROP TABLE IF EXISTS ref_financeiro_conta_bancaria;
DROP TABLE IF EXISTS ref_vendas_pedido;
DROP TABLE IF EXISTS ref_estoque_movimentacao;
DROP TABLE IF EXISTS ref_rh_evento_custo;

DROP TABLE IF EXISTS balanco_patrimonial_item;
DROP TABLE IF EXISTS balanco_patrimonial;

DROP TABLE IF EXISTS dre_item;
DROP TABLE IF EXISTS dre;

DROP TABLE IF EXISTS auditoria_inconsistencia;
DROP TABLE IF EXISTS auditoria;

DROP TABLE IF EXISTS rateio_item;
DROP TABLE IF EXISTS rateio;

DROP TABLE IF EXISTS partida_lancamento;
DROP TABLE IF EXISTS lancamento_contabil;

DROP TABLE IF EXISTS regra_contabilizacao_item;
DROP TABLE IF EXISTS regra_contabilizacao;

DROP TABLE IF EXISTS lote_lancamento;
DROP TABLE IF EXISTS plano_contas;
DROP TABLE IF EXISTS historico_padrao;
DROP TABLE IF EXISTS codigo_aglutinacao;
DROP TABLE IF EXISTS exercicio_social;

DROP TABLE IF EXISTS shared_centro_custos;
DROP TABLE IF EXISTS shared_usuario_empresa_perfil;
DROP TABLE IF EXISTS shared_usuario_empresa;

DROP TABLE IF EXISTS shared_perfil_permissao;
DROP TABLE IF EXISTS shared_permissao;
DROP TABLE IF EXISTS shared_perfil;

DROP TABLE IF EXISTS shared_usuario;
DROP TABLE IF EXISTS shared_empresa;


DROP TABLE IF EXISTS patrimonio_baixa;
DROP TABLE IF EXISTS patrimonio_reavaliacao;
DROP TABLE IF EXISTS patrimonio_depreciacao;
DROP TABLE IF EXISTS patrimonio_bem;
DROP TABLE IF EXISTS patrimonio_categoria;


DROP TABLE IF EXISTS dfc_item_aglutinacao;
DROP TABLE IF EXISTS dfc_item;
DROP TABLE IF EXISTS dfc;

DROP TABLE IF EXISTS ecd_validacao;
DROP TABLE IF EXISTS ecd_signatario;
DROP TABLE IF EXISTS ecd_geracao;

DROP TABLE IF EXISTS ecf_validacao;
DROP TABLE IF EXISTS ecf_ajuste;
DROP TABLE IF EXISTS ecf_apuracao;
DROP TABLE IF EXISTS ecf_signatario;
DROP TABLE IF EXISTS ecf_geracao;

DROP TABLE IF EXISTS consolidacao_eliminacao_item;
DROP TABLE IF EXISTS consolidacao_eliminacao;

DROP TABLE IF EXISTS consolidacao_empresa;
DROP TABLE IF EXISTS consolidacao;

DROP TABLE IF EXISTS consolidacao_mapeamento;
DROP TABLE IF EXISTS consolidacao_rubrica;

DROP TABLE IF EXISTS grupo_empresarial_empresa;
DROP TABLE IF EXISTS grupo_empresarial;


SET FOREIGN_KEY_CHECKS = 1;



-- ======================================================================
-- Shared: tabelas que serão compartilhadas com todos os modulos do ERP
-- ======================================================================


create table shared_empresa (
	id bigint unsigned auto_increment primary key,
    
    cnpj varchar(18) not null unique,
    
    razao_social varchar(150) not null,
    nome_fantasia varchar(100) null,
    
    inscricao_estadual varchar(30) null,
    inscricao_municipal varchar(30) null,
    
    logradouro varchar(150) null,
    numero varchar(20) null,
    complemento varchar(100) null,
    bairro varchar(100) null,
    cidade varchar(100) null,
    uf char(2) null,
    cep varchar(10) null,
    
    telefone varchar(20) null,
    
    ativo boolean not null default true,
    
    criado_em datetime not null default current_timestamp
);

create table shared_usuario (
	id bigint unsigned auto_increment primary key,
    
    nome varchar(150) not null,
    
    login varchar(100) not null unique,
    email varchar(255) null unique,
    
    senha_hash varchar(255) not null,
    
    ativo boolean not null default true,
    
    criado_em datetime not null default current_timestamp
);


create table shared_centro_custos (
	id bigint unsigned auto_increment primary key,
    
    empresa_id bigint unsigned not null,
    
    codigo varchar(20) not null,
    nome varchar(100) not null,
    descricao varchar(200) null,
    
    centro_superior_id bigint unsigned null,
    
    nivel int unsigned null,
    
    responsavel_usuario_id bigint unsigned null,
    
    ativo boolean not null default true,
    
    constraint uq_centro_custos_empresa_codigo
		unique (empresa_id, codigo),
        
	constraint fk_centro_custos_empresa 
		foreign key (empresa_id)
        references shared_empresa(id),
        
	constraint fk_centro_custos_superior
		foreign key (centro_superior_id) 
        references shared_centro_custos(id),
        
	constraint fk_centro_custos_responsavel
		foreign key (responsavel_usuario_id)
        references shared_usuario(id)
);

CREATE TABLE shared_perfil (
    id BIGINT UNSIGNED AUTO_INCREMENT PRIMARY KEY,

    nome VARCHAR(100) NOT NULL,
    descricao VARCHAR(255) NULL,

    ativo BOOLEAN NOT NULL DEFAULT TRUE,

    CONSTRAINT uq_shared_perfil_nome
        UNIQUE (nome)
);

CREATE TABLE shared_permissao (
    id BIGINT UNSIGNED AUTO_INCREMENT PRIMARY KEY,

    codigo VARCHAR(100) NOT NULL,
    descricao VARCHAR(255) NOT NULL,

    ativo BOOLEAN NOT NULL DEFAULT TRUE,

    CONSTRAINT uq_shared_permissao_codigo
        UNIQUE (codigo)
);

CREATE TABLE shared_perfil_permissao (
    perfil_id BIGINT UNSIGNED NOT NULL,
    permissao_id BIGINT UNSIGNED NOT NULL,

    PRIMARY KEY (
        perfil_id,
        permissao_id
    ),

    CONSTRAINT fk_perfil_permissao_perfil
        FOREIGN KEY (perfil_id)
        REFERENCES shared_perfil(id),

    CONSTRAINT fk_perfil_permissao_permissao
        FOREIGN KEY (permissao_id)
        REFERENCES shared_permissao(id)
);

CREATE TABLE shared_usuario_empresa (
    id BIGINT UNSIGNED AUTO_INCREMENT PRIMARY KEY,

    usuario_id BIGINT UNSIGNED NOT NULL,
    empresa_id BIGINT UNSIGNED NOT NULL,

    ativo BOOLEAN NOT NULL DEFAULT TRUE,

    criado_em DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,

    CONSTRAINT fk_usuario_empresa_usuario
        FOREIGN KEY (usuario_id)
        REFERENCES shared_usuario(id),

    CONSTRAINT fk_usuario_empresa_empresa
        FOREIGN KEY (empresa_id)
        REFERENCES shared_empresa(id),

    CONSTRAINT uq_usuario_empresa
        UNIQUE (
            usuario_id,
            empresa_id
        )
);

CREATE TABLE shared_usuario_empresa_perfil (
    usuario_empresa_id BIGINT UNSIGNED NOT NULL,
    perfil_id BIGINT UNSIGNED NOT NULL,

    PRIMARY KEY (
        usuario_empresa_id,
        perfil_id
    ),

    CONSTRAINT fk_usuario_empresa_perfil_acesso
        FOREIGN KEY (usuario_empresa_id)
        REFERENCES shared_usuario_empresa(id),

    CONSTRAINT fk_usuario_empresa_perfil_perfil
        FOREIGN KEY (perfil_id)
        REFERENCES shared_perfil(id)
);

-- ===============
-- CONTABILIDADE
-- ===============



create table exercicio_social (
	id bigint unsigned auto_increment primary key,
    
    empresa_id bigint unsigned not null,
    
    ano_referencia smallint unsigned not null,
    data_inicio date not null,
    data_fim date not null,
    ativo boolean not null default true,
    status_contabil enum ('NAO_INICIADO', 'ABERTO', 'ENCERRADO') not null default 'NAO_INICIADO',
    
	constraint fk_exercicio_empresa
		foreign key(empresa_id)	
		references shared_empresa(id),
    
    constraint uq_exercicio_empresa_ano
		unique (empresa_id, ano_referencia),
        
	constraint chk_exercicio_periodo
		check (data_inicio <= data_fim)
        
);

create table codigo_aglutinacao (
	id bigint unsigned auto_increment primary key,
    
    empresa_id bigint unsigned not null,
    
    valor varchar(30) not null,
    descricao varchar(150) not null,
    observacao varchar(500) null,
    ativo boolean not null default true,
    
    constraint fk_aglutinacao_empresa
		foreign key (empresa_id)
        references shared_empresa(id),
        
	constraint uq_aglutinacao_empresa_valor
		unique (empresa_id, valor)
);



create table historico_padrao(
	id bigint unsigned auto_increment primary key,
    
    empresa_id bigint unsigned not null,
    
    descricao varchar(255) not null,
    ativo boolean not null default true,
    
    constraint fk_historico_empresa
		foreign key (empresa_id)
        references shared_empresa(id)
);



create table plano_contas (
	id bigint unsigned auto_increment primary key,
    
    empresa_id bigint unsigned not null,
    
    codigo varchar(20) not null,
    descricao varchar(150) not null,
    tipo enum('SINTETICA', 'ANALITICA') not null,
    natureza enum('DEVEDORA','CREDORA') not null,
    ativo boolean not null default true,
    
    conta_pai_id bigint unsigned null,
    
    codigo_aglutinacao_id bigint unsigned null,
    
    constraint fk_plano_contas_pai
		foreign key (conta_pai_id)
        references plano_contas(id),
        
	constraint fk_plano_contas_aglutinacao
		foreign key (codigo_aglutinacao_id)
        references codigo_aglutinacao(id),
        
	constraint fk_plano_empresa
		foreign key (empresa_id)
        references shared_empresa(id),
        
	constraint uq_plano_empresa_codigo
		unique (empresa_id, codigo)
);




create table lote_lancamento (
	id bigint unsigned auto_increment primary key,
    
    empresa_id bigint unsigned not null,
    
    nome varchar(100) not null,
    descricao varchar(255) null,
    
    data_abertura date not null,
    data_inicial date not null,
    data_final date not null,
    
    bloqueado boolean not null default false,
    
    criado_em datetime not null default current_timestamp,
    
    constraint chk_lote_periodo
		check (data_inicial <= data_final),
        
	constraint fk_lote_empresa
		foreign key (empresa_id)
        references shared_empresa(id)
);



create table regra_contabilizacao (
	id bigint unsigned auto_increment primary key,
    
    empresa_id bigint unsigned not null,
    
    modulo_origem varchar(30) not null,
    evento_origem varchar(100) not null,
    
    descricao_evento varchar(150) not null,
    
    ativo boolean not null default true,
    
    criado_em datetime not null default current_timestamp,
    
    constraint fk_regra_empresa
		foreign key (empresa_id)
        references shared_empresa(id),
        
        constraint uq_regra_empresa_evento 
			unique (empresa_id, modulo_origem, evento_origem)
);

create table regra_contabilizacao_item (
	id bigint unsigned auto_increment primary key,
    
    regra_id bigint unsigned not null,
    
    ordem int unsigned not null,
    
    conta_id bigint unsigned not null,
    
    tipo_movimento enum('DEBITO','CREDITO') not null,
    
    percentual decimal(7,4) not null default 100.0000,
    
    descricao varchar(255) null,
    
    constraint chk_regra_item_percentual
		check (percentual > 0 and percentual <= 100),
        
	constraint fk_regra_item_regra
		foreign key (regra_id) 
        references regra_contabilizacao(id),
        
	constraint fk_regra_item_conta 
		foreign key (conta_id)
        references plano_contas(id),
        
	constraint uq_regra_item_ordem
		unique (regra_id, ordem)
);


create table lancamento_contabil (
	id bigint unsigned auto_increment primary key,
    
    empresa_id bigint unsigned not null,
    
    
    
    numero_documento varchar(50) not null,
    data_lancamento date not null,
    descricao varchar(255) not null,
    origem varchar(30) not null default 'MANUAL',
    status enum('RASCUNHO', 'CONTABILIZADO', 'ESTORNADO') not null default 'RASCUNHO',
    
    exercicio_social_id bigint unsigned not null,
    
    
    criado_por bigint unsigned null,
    contabilizado_por bigint unsigned null,
    data_contabilizacao datetime null,
    
    estorno_de_id bigint unsigned null,
    
    criado_em datetime not null default current_timestamp,
    
    historico_padrao_id bigint unsigned null,
    
    lote_id bigint unsigned null,
    
    constraint fk_lancamento_exercicio
		foreign key (exercicio_social_id)
        references exercicio_social(id),
        
	constraint fk_lancamento_criado_por
		foreign key (criado_por)
        references shared_usuario(id),
        
	constraint fk_lancamento_contabilizado_por
		foreign key (contabilizado_por)
        references shared_usuario(id),
        
	constraint fk_lancamento_estorno
		foreign key (estorno_de_id)
        references lancamento_contabil(id),
        
	constraint fk_lancamento_historico_padrao
		foreign key (historico_padrao_id)
        references historico_padrao(id),
        
	constraint fk_lancamento_lote
		foreign key (lote_id)
		references lote_lancamento(id),
        
	constraint fk_lancamento_empresa
		foreign key (empresa_id)
        references shared_empresa(id)
        
);



create table partida_lancamento (
	id bigint unsigned auto_increment primary key,
    
   
    lancamento_id bigint unsigned not null,
    conta_id bigint unsigned not null,
     centro_custo_id bigint unsigned null,
    
    tipo_movimento enum('DEBITO', 'CREDITO') not null,
    valor decimal(15,2) not null,
    descricao varchar(255) null,
    
    constraint chk_partida_valor
		check (valor>0),
        
	constraint fk_partida_lancamento
		foreign key (lancamento_id)
        references lancamento_contabil(id),
        
	constraint fk_partida_conta
		foreign key (conta_id)
        references plano_contas(id),
        
	constraint fk_partida_centro_custo
		foreign key (centro_custo_id)
        references shared_centro_custos(id)
);

create table rateio (
	id bigint unsigned auto_increment primary key,
    
    empresa_id bigint unsigned not null,
    
    nome varchar(150) not null,
    
    conta_id bigint unsigned null,
    
    criterio varchar(30) not null default 'PERCENTUAL',	
    
    ativo boolean not null default true,
    
    criado_em datetime not null default current_timestamp,
    
    constraint fk_rateio_empresa
		foreign key (empresa_id)
        references shared_empresa(id),
        
	constraint fk_rateio_conta
		foreign key (conta_id)
        references plano_contas(id),
        
	constraint uq_rateio_empresa_nome
		unique (empresa_id, nome)
);

create table rateio_item (
	id bigint unsigned auto_increment primary key,
    
    rateio_id bigint unsigned not null,
    centro_custos_id bigint unsigned not null,
    
    percentual decimal(5,2) not null,
    
    constraint chk_rateio_item_percentual
		check (percentual > 0 and percentual <= 100),
        
	constraint fK_rateio_item_rateio 
		foreign key (rateio_id)
		references rateio(id),
        
	constraint fk_rateio_item_centro_custos
		foreign key (centro_custos_id)
        references shared_centro_custos(id),
        
	constraint uq_rateio_centro
		unique (rateio_id, centro_custos_id)
);




create table auditoria (
	id bigint unsigned auto_increment primary key,
    
    usuario_id bigint unsigned null,
    lancamento_id bigint unsigned null,
    exercicio_social_id bigint unsigned null,
    
    tipo_evento enum('CRIACAO','EDICAO','CONTABILIZACAO','ESTORNO','REABERTURA_EXERCICIO') not null,
    
    descricao varchar(500) not null,
    data_evento datetime not null default current_timestamp,
    
    constraint fk_auditoria_usuario
		foreign key (usuario_id)
        references shared_usuario(id),
        
	constraint fk_auditoria_lancamento
		foreign key (lancamento_id)
        references lancamento_contabil(id),
        
	constraint fk_auditoria_exercicio
		foreign key (exercicio_social_id)
        references exercicio_social(id)

);


create table auditoria_inconsistencia (
	id bigint unsigned auto_increment primary key,
    
    codigo varchar(50) not null,
    descricao varchar(500) not null,
    recomendacao varchar(500) null,
    
    severidade enum('BAIXA','MEDIA','ALTA','CRITICA') not null default 'MEDIA',
    
    status enum('PENDENTE','REVISADO','IGNORADO') not null default 'PENDENTE',
    
    lancamento_id bigint unsigned null,
    partida_id bigint unsigned null,
    
    detectado_em datetime not null default current_timestamp,
    
    revisado_por bigint unsigned null,
    revisado_em datetime null,
    
    observacao_revisao varchar(500) null,
    
    constraint fk_inconsistencia_lancamento
		foreign key (lancamento_id)
        references lancamento_contabil(id),
        
	constraint fk_inconsistencia_partida
		foreign key (partida_id)
        references partida_lancamento(id),
        
	constraint fk_inconsistencia_revisado_por
		foreign key (revisado_por)
        references shared_usuario(id)

);

    

create table dre (
	id bigint unsigned auto_increment primary key,
    
    empresa_id bigint unsigned not null,
    
    descricao varchar(150) not null,
    favorito boolean not null default false,
    
    constraint fk_dre_empresa
		foreign key (empresa_id)
        references shared_empresa(id)
        
	
);



create table dre_item (
	id bigint unsigned auto_increment primary key,
    
    dre_id bigint unsigned not null,
    
    ordem int unsigned not null,
    
    codigo_aglutinacao_id bigint unsigned not null,
    
    grupo enum ('INCREMENTO_LUCRO','REDUCAO_LUCRO') not null,
    
    item_superior_id bigint unsigned null,
    
    ativo boolean not null default true,
    
    CONSTRAINT fk_dre_item_dre
        FOREIGN KEY (dre_id)
        REFERENCES dre(id),

    CONSTRAINT fk_dre_item_aglutinacao
        FOREIGN KEY (codigo_aglutinacao_id)
        REFERENCES codigo_aglutinacao(id),

    CONSTRAINT fk_dre_item_superior
        FOREIGN KEY (item_superior_id)
        REFERENCES dre_item(id),

    CONSTRAINT uq_dre_codigo
        UNIQUE (dre_id, codigo_aglutinacao_id),
        
        constraint uq_dre_ordem
		unique (dre_id, ordem)
        
	
    
);

create table balanco_patrimonial (
	id bigint unsigned auto_increment primary key,
    
    empresa_id bigint unsigned not null,
    
    descricao varchar(150) not null,
    favorito boolean not null default false,
    
    constraint fk_balanco_empresa
		foreign key (empresa_id)
        references shared_empresa(id)

);



CREATE TABLE balanco_patrimonial_item (
    id BIGINT UNSIGNED AUTO_INCREMENT PRIMARY KEY,

    balanco_patrimonial_id BIGINT UNSIGNED NOT NULL,

    ordem INT UNSIGNED NOT NULL,

    codigo_aglutinacao_id BIGINT UNSIGNED NOT NULL,

    grupo ENUM(
        'ATIVO',
        'PASSIVO',
        'PATRIMONIO_LIQUIDO'
    ) NOT NULL,

    item_superior_id BIGINT UNSIGNED NULL,

    ativo BOOLEAN NOT NULL DEFAULT TRUE,

    CONSTRAINT fk_balanco_item_balanco
        FOREIGN KEY (balanco_patrimonial_id)
        REFERENCES balanco_patrimonial(id),

    CONSTRAINT fk_balanco_item_aglutinacao
        FOREIGN KEY (codigo_aglutinacao_id)
        REFERENCES codigo_aglutinacao(id),

    CONSTRAINT fk_balanco_item_superior
        FOREIGN KEY (item_superior_id)
        REFERENCES balanco_patrimonial_item(id),

    CONSTRAINT uq_balanco_codigo
        UNIQUE (
            balanco_patrimonial_id,
            codigo_aglutinacao_id
        ),
        
        constraint uq_balanco_ordem
		unique (balanco_patrimonial_id, ordem)
);

create index idx_lancamento_data_status on lancamento_contabil(data_lancamento, status);

create index idx_lancamento_documento on lancamento_contabil(numero_documento);

create index idx_partida_conta_lancamento on partida_lancamento(conta_id, lancamento_id);

create index idx_inconsistencia_status_severidade on auditoria_inconsistencia (status, severidade, detectado_em);

create index idx_auditoria_data_evento on auditoria (data_evento);




CREATE TABLE patrimonio_categoria (
    id BIGINT UNSIGNED AUTO_INCREMENT PRIMARY KEY,

    empresa_id BIGINT UNSIGNED NOT NULL,

    codigo VARCHAR(30) NOT NULL,
    descricao VARCHAR(150) NOT NULL,

    vida_util_padrao_meses INT UNSIGNED NULL,

    taxa_depreciacao_anual DECIMAL(7,4) NULL,

    metodo_depreciacao ENUM(
        'LINEAR',
        'SALDOS_DECRESCENTES',
        'UNIDADES_PRODUZIDAS',
        'OUTRO'
    ) NOT NULL DEFAULT 'LINEAR',

    conta_ativo_id BIGINT UNSIGNED NOT NULL,
    conta_depreciacao_acumulada_id BIGINT UNSIGNED NOT NULL,
    conta_despesa_depreciacao_id BIGINT UNSIGNED NOT NULL,

    ativo BOOLEAN NOT NULL DEFAULT TRUE,

    CONSTRAINT fk_patrimonio_categoria_empresa
        FOREIGN KEY (empresa_id)
        REFERENCES shared_empresa(id),

    CONSTRAINT fk_patrimonio_categoria_conta_ativo
        FOREIGN KEY (conta_ativo_id)
        REFERENCES plano_contas(id),

    CONSTRAINT fk_patrimonio_categoria_conta_deprec_acum
        FOREIGN KEY (conta_depreciacao_acumulada_id)
        REFERENCES plano_contas(id),

    CONSTRAINT fk_patrimonio_categoria_conta_despesa
        FOREIGN KEY (conta_despesa_depreciacao_id)
        REFERENCES plano_contas(id),

    CONSTRAINT uq_patrimonio_categoria_codigo
        UNIQUE (empresa_id, codigo),

    CONSTRAINT chk_patrimonio_categoria_taxa
        CHECK (
            taxa_depreciacao_anual IS NULL
            OR (
                taxa_depreciacao_anual > 0
                AND taxa_depreciacao_anual <= 100
            )
        )
);


CREATE TABLE patrimonio_bem (
    id BIGINT UNSIGNED AUTO_INCREMENT PRIMARY KEY,

    empresa_id BIGINT UNSIGNED NOT NULL,
    categoria_id BIGINT UNSIGNED NOT NULL,

    codigo_patrimonial VARCHAR(50) NOT NULL,

    descricao VARCHAR(200) NOT NULL,

    numero_serie VARCHAR(100) NULL,
    documento_aquisicao VARCHAR(100) NULL,

    data_aquisicao DATE NOT NULL,
    data_inicio_uso DATE NULL,

    valor_aquisicao DECIMAL(15,2) NOT NULL,
    valor_residual DECIMAL(15,2) NOT NULL DEFAULT 0,

    vida_util_meses INT UNSIGNED NOT NULL,

    metodo_depreciacao ENUM(
        'LINEAR',
        'SALDOS_DECRESCENTES',
        'UNIDADES_PRODUZIDAS',
        'OUTRO'
    ) NOT NULL DEFAULT 'LINEAR',

    centro_custo_id BIGINT UNSIGNED NULL,

    status ENUM(
        'ATIVO',
        'TOTALMENTE_DEPRECIADO',
        'BAIXADO',
        'VENDIDO',
        'INATIVO'
    ) NOT NULL DEFAULT 'ATIVO',

    observacao VARCHAR(500) NULL,

    criado_por BIGINT UNSIGNED NULL,
    criado_em DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,

    CONSTRAINT fk_patrimonio_bem_empresa
        FOREIGN KEY (empresa_id)
        REFERENCES shared_empresa(id),

    CONSTRAINT fk_patrimonio_bem_categoria
        FOREIGN KEY (categoria_id)
        REFERENCES patrimonio_categoria(id),

    CONSTRAINT fk_patrimonio_bem_centro_custo
        FOREIGN KEY (centro_custo_id)
        REFERENCES shared_centro_custos(id),

    CONSTRAINT fk_patrimonio_bem_usuario
        FOREIGN KEY (criado_por)
        REFERENCES shared_usuario(id),

    CONSTRAINT uq_patrimonio_bem_codigo
        UNIQUE (empresa_id, codigo_patrimonial),

    CONSTRAINT chk_patrimonio_bem_valor
        CHECK (valor_aquisicao > 0),

    CONSTRAINT chk_patrimonio_bem_residual
        CHECK (
            valor_residual >= 0
            AND valor_residual <= valor_aquisicao
        ),

    CONSTRAINT chk_patrimonio_bem_vida_util
        CHECK (vida_util_meses > 0)
);

CREATE TABLE patrimonio_depreciacao (
    id BIGINT UNSIGNED AUTO_INCREMENT PRIMARY KEY,

    bem_id BIGINT UNSIGNED NOT NULL,

    competencia DATE NOT NULL,

    valor_base DECIMAL(15,2) NOT NULL,

    taxa_aplicada DECIMAL(7,4) NULL,

    valor_depreciacao DECIMAL(15,2) NOT NULL,

    status ENUM(
        'CALCULADA',
        'CONTABILIZADA',
        'CANCELADA'
    ) NOT NULL DEFAULT 'CALCULADA',

    lancamento_id BIGINT UNSIGNED NULL,

    calculado_em DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,

    contabilizado_em DATETIME NULL,

    criado_por BIGINT UNSIGNED NULL,

    CONSTRAINT fk_depreciacao_bem
        FOREIGN KEY (bem_id)
        REFERENCES patrimonio_bem(id),

    CONSTRAINT fk_depreciacao_lancamento
        FOREIGN KEY (lancamento_id)
        REFERENCES lancamento_contabil(id),

    CONSTRAINT fk_depreciacao_usuario
        FOREIGN KEY (criado_por)
        REFERENCES shared_usuario(id),

    CONSTRAINT uq_depreciacao_bem_competencia
        UNIQUE (bem_id, competencia),

    CONSTRAINT chk_depreciacao_base
        CHECK (valor_base >= 0),

    CONSTRAINT chk_depreciacao_valor
        CHECK (valor_depreciacao > 0)
);

CREATE TABLE patrimonio_reavaliacao (
    id BIGINT UNSIGNED AUTO_INCREMENT PRIMARY KEY,

    bem_id BIGINT UNSIGNED NOT NULL,

    data_reavaliacao DATE NOT NULL,

    tipo ENUM(
        'AUMENTO',
        'REDUCAO'
    ) NOT NULL,

    valor_anterior DECIMAL(15,2) NOT NULL,
    valor_novo DECIMAL(15,2) NOT NULL,

    motivo VARCHAR(500) NOT NULL,

    referencia_laudo VARCHAR(150) NULL,

    status ENUM(
        'PENDENTE',
        'APROVADA',
        'CONTABILIZADA',
        'CANCELADA'
    ) NOT NULL DEFAULT 'PENDENTE',

    aprovado_por BIGINT UNSIGNED NULL,
    aprovado_em DATETIME NULL,

    lancamento_id BIGINT UNSIGNED NULL,

    criado_em DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,

    CONSTRAINT fk_reavaliacao_bem
        FOREIGN KEY (bem_id)
        REFERENCES patrimonio_bem(id),

    CONSTRAINT fk_reavaliacao_aprovado_por
        FOREIGN KEY (aprovado_por)
        REFERENCES shared_usuario(id),

    CONSTRAINT fk_reavaliacao_lancamento
        FOREIGN KEY (lancamento_id)
        REFERENCES lancamento_contabil(id),

    CONSTRAINT chk_reavaliacao_valores
        CHECK (
            valor_anterior >= 0
            AND valor_novo >= 0
            AND valor_anterior <> valor_novo
        )
);


CREATE TABLE patrimonio_baixa (
    id BIGINT UNSIGNED AUTO_INCREMENT PRIMARY KEY,

    bem_id BIGINT UNSIGNED NOT NULL,

    data_baixa DATE NOT NULL,

    tipo ENUM(
        'VENDA',
        'DESCARTE',
        'PERDA',
        'DOACAO',
        'SINISTRO',
        'OUTRO'
    ) NOT NULL,

    valor_realizacao DECIMAL(15,2) NULL,

    documento VARCHAR(100) NULL,

    motivo VARCHAR(500) NOT NULL,

    status ENUM(
        'PENDENTE',
        'CONTABILIZADA',
        'CANCELADA'
    ) NOT NULL DEFAULT 'PENDENTE',

    lancamento_id BIGINT UNSIGNED NULL,

    realizado_por BIGINT UNSIGNED NULL,

    criado_em DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,

    CONSTRAINT fk_patrimonio_baixa_bem
        FOREIGN KEY (bem_id)
        REFERENCES patrimonio_bem(id),

    CONSTRAINT fk_patrimonio_baixa_lancamento
        FOREIGN KEY (lancamento_id)
        REFERENCES lancamento_contabil(id),

    CONSTRAINT fk_patrimonio_baixa_usuario
        FOREIGN KEY (realizado_por)
        REFERENCES shared_usuario(id),

    CONSTRAINT chk_patrimonio_baixa_valor
        CHECK (
            valor_realizacao IS NULL
            OR valor_realizacao >= 0
        )
);

CREATE TABLE dfc (
    id BIGINT UNSIGNED AUTO_INCREMENT PRIMARY KEY,

    empresa_id BIGINT UNSIGNED NOT NULL,

    descricao VARCHAR(150) NOT NULL,

    metodo ENUM(
        'DIRETO',
        'INDIRETO'
    ) NOT NULL DEFAULT 'DIRETO',

    favorito BOOLEAN NOT NULL DEFAULT FALSE,

    ativo BOOLEAN NOT NULL DEFAULT TRUE,

    criado_em DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,

    CONSTRAINT fk_dfc_empresa
        FOREIGN KEY (empresa_id)
        REFERENCES shared_empresa(id),

    CONSTRAINT uq_dfc_empresa_descricao
        UNIQUE (empresa_id, descricao)
);

CREATE TABLE dfc_item (
    id BIGINT UNSIGNED AUTO_INCREMENT PRIMARY KEY,

    dfc_id BIGINT UNSIGNED NOT NULL,

    ordem INT UNSIGNED NOT NULL,

    descricao VARCHAR(200) NOT NULL,

    grupo ENUM(
        'OPERACIONAL',
        'INVESTIMENTO',
        'FINANCIAMENTO',
        'EFEITO_CAMBIAL'
    ) NOT NULL,

    tipo_linha ENUM(
        'DETALHE',
        'SUBTOTAL',
        'TOTAL',
        'CAIXA_INICIAL',
        'CAIXA_FINAL'
    ) NOT NULL DEFAULT 'DETALHE',

    efeito ENUM(
        'SOMA',
        'SUBTRAI',
        'NEUTRO'
    ) NOT NULL DEFAULT 'SOMA',

    item_superior_id BIGINT UNSIGNED NULL,

    ativo BOOLEAN NOT NULL DEFAULT TRUE,

    CONSTRAINT fk_dfc_item_dfc
        FOREIGN KEY (dfc_id)
        REFERENCES dfc(id),

    CONSTRAINT fk_dfc_item_superior
        FOREIGN KEY (item_superior_id)
        REFERENCES dfc_item(id),

    CONSTRAINT uq_dfc_item_ordem
        UNIQUE (dfc_id, ordem)
);

CREATE TABLE dfc_item_aglutinacao (
    id BIGINT UNSIGNED AUTO_INCREMENT PRIMARY KEY,

    dfc_item_id BIGINT UNSIGNED NOT NULL,

    codigo_aglutinacao_id BIGINT UNSIGNED NOT NULL,

    CONSTRAINT fk_dfc_aglutinacao_item
        FOREIGN KEY (dfc_item_id)
        REFERENCES dfc_item(id),

    CONSTRAINT fk_dfc_aglutinacao_codigo
        FOREIGN KEY (codigo_aglutinacao_id)
        REFERENCES codigo_aglutinacao(id),

    CONSTRAINT uq_dfc_item_codigo
        UNIQUE (
            dfc_item_id,
            codigo_aglutinacao_id
        )
);


CREATE TABLE ecd_geracao (
    id BIGINT UNSIGNED AUTO_INCREMENT PRIMARY KEY,

    empresa_id BIGINT UNSIGNED NOT NULL,
    exercicio_social_id BIGINT UNSIGNED NOT NULL,

    data_inicio DATE NOT NULL,
    data_fim DATE NOT NULL,

    finalidade ENUM(
        'ORIGINAL',
        'SUBSTITUTA'
    ) NOT NULL DEFAULT 'ORIGINAL',

    ecd_substituida_id BIGINT UNSIGNED NULL,

    tipo_livro VARCHAR(30) NULL,
    numero_livro VARCHAR(30) NULL,

    versao_layout VARCHAR(20) NULL,

    status ENUM(
        'EM_PREPARACAO',
        'GERADA',
        'COM_ERROS',
        'VALIDADA',
        'ASSINADA',
        'TRANSMITIDA',
        'CANCELADA'
    ) NOT NULL DEFAULT 'EM_PREPARACAO',

    nome_arquivo VARCHAR(255) NULL,
    hash_arquivo VARCHAR(128) NULL,

    numero_recibo VARCHAR(100) NULL,

    gerado_por BIGINT UNSIGNED NULL,
    gerado_em DATETIME NULL,

    transmitido_em DATETIME NULL,

    observacao VARCHAR(500) NULL,

    criado_em DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,

    CONSTRAINT fk_ecd_empresa
        FOREIGN KEY (empresa_id)
        REFERENCES shared_empresa(id),

    CONSTRAINT fk_ecd_exercicio
        FOREIGN KEY (exercicio_social_id)
        REFERENCES exercicio_social(id),

    CONSTRAINT fk_ecd_substituida
        FOREIGN KEY (ecd_substituida_id)
        REFERENCES ecd_geracao(id),

    CONSTRAINT fk_ecd_gerado_por
        FOREIGN KEY (gerado_por)
        REFERENCES shared_usuario(id),

    CONSTRAINT chk_ecd_periodo
        CHECK (data_inicio <= data_fim)
);


CREATE TABLE ecd_signatario (
    id BIGINT UNSIGNED AUTO_INCREMENT PRIMARY KEY,

    ecd_id BIGINT UNSIGNED NOT NULL,

    nome VARCHAR(150) NOT NULL,

    cpf VARCHAR(14) NULL,

    tipo ENUM(
        'RESPONSAVEL_LEGAL',
        'CONTADOR',
        'OUTRO'
    ) NOT NULL,

    crc VARCHAR(30) NULL,
    uf_crc CHAR(2) NULL,

    email VARCHAR(254) NULL,

    assinou BOOLEAN NOT NULL DEFAULT FALSE,
    assinado_em DATETIME NULL,

    CONSTRAINT fk_ecd_signatario_ecd
        FOREIGN KEY (ecd_id)
        REFERENCES ecd_geracao(id)
);

CREATE TABLE ecd_validacao (
    id BIGINT UNSIGNED AUTO_INCREMENT PRIMARY KEY,

    ecd_id BIGINT UNSIGNED NOT NULL,

    tipo ENUM(
        'ERRO',
        'AVISO'
    ) NOT NULL,

    codigo VARCHAR(50) NULL,

    descricao VARCHAR(500) NOT NULL,

    referencia VARCHAR(150) NULL,

    resolvido BOOLEAN NOT NULL DEFAULT FALSE,

    resolvido_por BIGINT UNSIGNED NULL,
    resolvido_em DATETIME NULL,

    detectado_em DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,

    CONSTRAINT fk_ecd_validacao_ecd
        FOREIGN KEY (ecd_id)
        REFERENCES ecd_geracao(id),

    CONSTRAINT fk_ecd_validacao_usuario
        FOREIGN KEY (resolvido_por)
        REFERENCES shared_usuario(id)
);


CREATE TABLE ecf_geracao (
    id BIGINT UNSIGNED AUTO_INCREMENT PRIMARY KEY,

    empresa_id BIGINT UNSIGNED NOT NULL,
    exercicio_social_id BIGINT UNSIGNED NOT NULL,

    ecd_geracao_id BIGINT UNSIGNED NULL,

    data_inicio DATE NOT NULL,
    data_fim DATE NOT NULL,

    finalidade ENUM(
        'ORIGINAL',
        'RETIFICADORA'
    ) NOT NULL DEFAULT 'ORIGINAL',

    ecf_retificada_id BIGINT UNSIGNED NULL,

    regime_tributacao ENUM(
        'LUCRO_REAL',
        'LUCRO_PRESUMIDO',
        'LUCRO_ARBITRADO',
        'IMUNE_ISENTA',
        'OUTRO'
    ) NOT NULL,

    versao_layout VARCHAR(20) NULL,

    status ENUM(
        'EM_PREPARACAO',
        'GERADA',
        'COM_ERROS',
        'VALIDADA',
        'ASSINADA',
        'TRANSMITIDA',
        'CANCELADA'
    ) NOT NULL DEFAULT 'EM_PREPARACAO',

    nome_arquivo VARCHAR(255) NULL,
    hash_arquivo VARCHAR(128) NULL,

    numero_recibo VARCHAR(100) NULL,

    gerado_por BIGINT UNSIGNED NULL,
    gerado_em DATETIME NULL,

    transmitido_em DATETIME NULL,

    observacao VARCHAR(500) NULL,

    criado_em DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,

    CONSTRAINT fk_ecf_empresa
        FOREIGN KEY (empresa_id)
        REFERENCES shared_empresa(id),

    CONSTRAINT fk_ecf_exercicio
        FOREIGN KEY (exercicio_social_id)
        REFERENCES exercicio_social(id),

    CONSTRAINT fk_ecf_ecd
        FOREIGN KEY (ecd_geracao_id)
        REFERENCES ecd_geracao(id),

    CONSTRAINT fk_ecf_retificada
        FOREIGN KEY (ecf_retificada_id)
        REFERENCES ecf_geracao(id),

    CONSTRAINT fk_ecf_gerado_por
        FOREIGN KEY (gerado_por)
        REFERENCES shared_usuario(id),

    CONSTRAINT chk_ecf_periodo
        CHECK (data_inicio <= data_fim)
);

CREATE TABLE ecf_signatario (
    id BIGINT UNSIGNED AUTO_INCREMENT PRIMARY KEY,

    ecf_id BIGINT UNSIGNED NOT NULL,

    nome VARCHAR(150) NOT NULL,

    cpf VARCHAR(14) NULL,

    tipo ENUM(
        'RESPONSAVEL_LEGAL',
        'CONTADOR',
        'OUTRO'
    ) NOT NULL,

    crc VARCHAR(30) NULL,
    uf_crc CHAR(2) NULL,

    email VARCHAR(254) NULL,

    assinou BOOLEAN NOT NULL DEFAULT FALSE,
    assinado_em DATETIME NULL,

    CONSTRAINT fk_ecf_signatario_ecf
        FOREIGN KEY (ecf_id)
        REFERENCES ecf_geracao(id)
);

CREATE TABLE ecf_apuracao (
    id BIGINT UNSIGNED AUTO_INCREMENT PRIMARY KEY,

    ecf_id BIGINT UNSIGNED NOT NULL,

    tributo ENUM(
        'IRPJ',
        'CSLL'
    ) NOT NULL,

    data_inicio DATE NOT NULL,
    data_fim DATE NOT NULL,

    base_inicial DECIMAL(15,2) NOT NULL DEFAULT 0,

    total_adicoes DECIMAL(15,2) NOT NULL DEFAULT 0,
    total_exclusoes DECIMAL(15,2) NOT NULL DEFAULT 0,
    total_compensacoes DECIMAL(15,2) NOT NULL DEFAULT 0,

    base_calculo DECIMAL(15,2) NOT NULL DEFAULT 0,

    aliquota DECIMAL(7,4) NULL,

    valor_tributo DECIMAL(15,2) NOT NULL DEFAULT 0,

    status ENUM(
        'EM_CALCULO',
        'CALCULADA',
        'REVISADA',
        'FECHADA'
    ) NOT NULL DEFAULT 'EM_CALCULO',

    criado_em DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,

    CONSTRAINT fk_ecf_apuracao_ecf
        FOREIGN KEY (ecf_id)
        REFERENCES ecf_geracao(id),

    CONSTRAINT chk_ecf_apuracao_periodo
        CHECK (data_inicio <= data_fim),

    CONSTRAINT chk_ecf_apuracao_aliquota
        CHECK (
            aliquota IS NULL
            OR (
                aliquota >= 0
                AND aliquota <= 100
            )
        ),

    CONSTRAINT uq_ecf_apuracao
        UNIQUE (
            ecf_id,
            tributo,
            data_inicio,
            data_fim
        )
);


CREATE TABLE ecf_ajuste (
    id BIGINT UNSIGNED AUTO_INCREMENT PRIMARY KEY,

    apuracao_id BIGINT UNSIGNED NOT NULL,

    tipo ENUM(
        'ADICAO',
        'EXCLUSAO',
        'COMPENSACAO'
    ) NOT NULL,

    codigo VARCHAR(50) NULL,

    descricao VARCHAR(255) NOT NULL,

    valor DECIMAL(15,2) NOT NULL,

    conta_id BIGINT UNSIGNED NULL,

    documento_referencia VARCHAR(100) NULL,

    observacao VARCHAR(500) NULL,

    CONSTRAINT fk_ecf_ajuste_apuracao
        FOREIGN KEY (apuracao_id)
        REFERENCES ecf_apuracao(id),

    CONSTRAINT fk_ecf_ajuste_conta
        FOREIGN KEY (conta_id)
        REFERENCES plano_contas(id),

    CONSTRAINT chk_ecf_ajuste_valor
        CHECK (valor > 0)
);


CREATE TABLE ecf_validacao (
    id BIGINT UNSIGNED AUTO_INCREMENT PRIMARY KEY,

    ecf_id BIGINT UNSIGNED NOT NULL,

    tipo ENUM(
        'ERRO',
        'AVISO'
    ) NOT NULL,

    codigo VARCHAR(50) NULL,

    descricao VARCHAR(500) NOT NULL,

    referencia VARCHAR(150) NULL,

    resolvido BOOLEAN NOT NULL DEFAULT FALSE,

    resolvido_por BIGINT UNSIGNED NULL,
    resolvido_em DATETIME NULL,

    detectado_em DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,

    CONSTRAINT fk_ecf_validacao_ecf
        FOREIGN KEY (ecf_id)
        REFERENCES ecf_geracao(id),

    CONSTRAINT fk_ecf_validacao_usuario
        FOREIGN KEY (resolvido_por)
        REFERENCES shared_usuario(id)
);

CREATE TABLE grupo_empresarial (
    id BIGINT UNSIGNED AUTO_INCREMENT PRIMARY KEY,

    nome VARCHAR(150) NOT NULL,
    descricao VARCHAR(500) NULL,

    ativo BOOLEAN NOT NULL DEFAULT TRUE,

    criado_em DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,

    CONSTRAINT uq_grupo_empresarial_nome
        UNIQUE (nome)
);


CREATE TABLE grupo_empresarial_empresa (
    id BIGINT UNSIGNED AUTO_INCREMENT PRIMARY KEY,

    grupo_id BIGINT UNSIGNED NOT NULL,
    empresa_id BIGINT UNSIGNED NOT NULL,

    tipo_participacao ENUM(
        'CONTROLADORA',
        'CONTROLADA',
        'COLIGADA',
        'OUTRA'
    ) NOT NULL,

    percentual_participacao DECIMAL(7,4) NOT NULL DEFAULT 100.0000,

    data_inicio DATE NOT NULL,
    data_fim DATE NULL,

    ativo BOOLEAN NOT NULL DEFAULT TRUE,

    CONSTRAINT fk_grupo_empresa_grupo
        FOREIGN KEY (grupo_id)
        REFERENCES grupo_empresarial(id),

    CONSTRAINT fk_grupo_empresa_empresa
        FOREIGN KEY (empresa_id)
        REFERENCES shared_empresa(id),

    CONSTRAINT uq_grupo_empresa
        UNIQUE (grupo_id, empresa_id),

    CONSTRAINT chk_grupo_participacao
        CHECK (
            percentual_participacao > 0
            AND percentual_participacao <= 100
        ),

    CONSTRAINT chk_grupo_periodo
        CHECK (
            data_fim IS NULL
            OR data_inicio <= data_fim
        )
);


CREATE TABLE consolidacao_rubrica (
    id BIGINT UNSIGNED AUTO_INCREMENT PRIMARY KEY,

    grupo_id BIGINT UNSIGNED NOT NULL,

    codigo VARCHAR(30) NOT NULL,
    descricao VARCHAR(150) NOT NULL,

    tipo_relatorio ENUM(
        'BALANCO',
        'DRE'
    ) NOT NULL,

    natureza ENUM(
        'DEVEDORA',
        'CREDORA'
    ) NOT NULL,

    ordem INT UNSIGNED NOT NULL,

    rubrica_superior_id BIGINT UNSIGNED NULL,

    ativo BOOLEAN NOT NULL DEFAULT TRUE,

    CONSTRAINT fk_consolidacao_rubrica_grupo
        FOREIGN KEY (grupo_id)
        REFERENCES grupo_empresarial(id),

    CONSTRAINT fk_consolidacao_rubrica_superior
        FOREIGN KEY (rubrica_superior_id)
        REFERENCES consolidacao_rubrica(id),

    CONSTRAINT uq_consolidacao_rubrica_codigo
        UNIQUE (grupo_id, codigo),

    CONSTRAINT uq_consolidacao_rubrica_ordem
        UNIQUE (grupo_id, tipo_relatorio, ordem)
);

CREATE TABLE consolidacao_mapeamento (
    id BIGINT UNSIGNED AUTO_INCREMENT PRIMARY KEY,

    grupo_id BIGINT UNSIGNED NOT NULL,
    empresa_id BIGINT UNSIGNED NOT NULL,

    conta_id BIGINT UNSIGNED NOT NULL,
    rubrica_id BIGINT UNSIGNED NOT NULL,

    ativo BOOLEAN NOT NULL DEFAULT TRUE,

    CONSTRAINT fk_consolidacao_map_grupo
        FOREIGN KEY (grupo_id)
        REFERENCES grupo_empresarial(id),

    CONSTRAINT fk_consolidacao_map_empresa
        FOREIGN KEY (empresa_id)
        REFERENCES shared_empresa(id),

    CONSTRAINT fk_consolidacao_map_conta
        FOREIGN KEY (conta_id)
        REFERENCES plano_contas(id),

    CONSTRAINT fk_consolidacao_map_rubrica
        FOREIGN KEY (rubrica_id)
        REFERENCES consolidacao_rubrica(id),

    CONSTRAINT uq_consolidacao_map_conta
        UNIQUE (grupo_id, empresa_id, conta_id)
);


CREATE TABLE consolidacao (
    id BIGINT UNSIGNED AUTO_INCREMENT PRIMARY KEY,

    grupo_id BIGINT UNSIGNED NOT NULL,

    descricao VARCHAR(150) NOT NULL,

    data_inicio DATE NOT NULL,
    data_fim DATE NOT NULL,

    status ENUM(
        'EM_PREPARACAO',
        'PROCESSANDO',
        'CONSOLIDADA',
        'FECHADA',
        'CANCELADA'
    ) NOT NULL DEFAULT 'EM_PREPARACAO',

    criado_por BIGINT UNSIGNED NULL,
    criado_em DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,

    finalizado_por BIGINT UNSIGNED NULL,
    finalizado_em DATETIME NULL,

    observacao VARCHAR(500) NULL,

    CONSTRAINT fk_consolidacao_grupo
        FOREIGN KEY (grupo_id)
        REFERENCES grupo_empresarial(id),

    CONSTRAINT fk_consolidacao_criado_por
        FOREIGN KEY (criado_por)
        REFERENCES shared_usuario(id),

    CONSTRAINT fk_consolidacao_finalizado_por
        FOREIGN KEY (finalizado_por)
        REFERENCES shared_usuario(id),

    CONSTRAINT chk_consolidacao_periodo
        CHECK (data_inicio <= data_fim)
);

CREATE TABLE consolidacao_empresa (
    id BIGINT UNSIGNED AUTO_INCREMENT PRIMARY KEY,

    consolidacao_id BIGINT UNSIGNED NOT NULL,
    empresa_id BIGINT UNSIGNED NOT NULL,

    percentual_participacao DECIMAL(7,4) NOT NULL DEFAULT 100.0000,

    incluida BOOLEAN NOT NULL DEFAULT TRUE,

    CONSTRAINT fk_consolidacao_empresa_consolidacao
        FOREIGN KEY (consolidacao_id)
        REFERENCES consolidacao(id),

    CONSTRAINT fk_consolidacao_empresa_empresa
        FOREIGN KEY (empresa_id)
        REFERENCES shared_empresa(id),

    CONSTRAINT uq_consolidacao_empresa
        UNIQUE (consolidacao_id, empresa_id),

    CONSTRAINT chk_consolidacao_empresa_percentual
        CHECK (
            percentual_participacao > 0
            AND percentual_participacao <= 100
        )
);

CREATE TABLE consolidacao_eliminacao (
    id BIGINT UNSIGNED AUTO_INCREMENT PRIMARY KEY,

    consolidacao_id BIGINT UNSIGNED NOT NULL,

    data_eliminacao DATE NOT NULL,

    tipo ENUM(
        'INTERCOMPANY',
        'INVESTIMENTO',
        'DIVIDENDO',
        'LUCRO_NAO_REALIZADO',
        'OUTRO'
    ) NOT NULL,

    descricao VARCHAR(500) NOT NULL,

    status ENUM(
        'RASCUNHO',
        'APLICADA',
        'CANCELADA'
    ) NOT NULL DEFAULT 'RASCUNHO',

    criado_por BIGINT UNSIGNED NULL,
    criado_em DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,

    CONSTRAINT fk_eliminacao_consolidacao
        FOREIGN KEY (consolidacao_id)
        REFERENCES consolidacao(id),

    CONSTRAINT fk_eliminacao_usuario
        FOREIGN KEY (criado_por)
        REFERENCES shared_usuario(id)
);


CREATE TABLE consolidacao_eliminacao_item (
    id BIGINT UNSIGNED AUTO_INCREMENT PRIMARY KEY,

    eliminacao_id BIGINT UNSIGNED NOT NULL,

    empresa_id BIGINT UNSIGNED NULL,
    rubrica_id BIGINT UNSIGNED NOT NULL,

    tipo_movimento ENUM(
        'DEBITO',
        'CREDITO'
    ) NOT NULL,

    valor DECIMAL(15,2) NOT NULL,

    descricao VARCHAR(255) NULL,

    CONSTRAINT fk_eliminacao_item_eliminacao
        FOREIGN KEY (eliminacao_id)
        REFERENCES consolidacao_eliminacao(id),

    CONSTRAINT fk_eliminacao_item_empresa
        FOREIGN KEY (empresa_id)
        REFERENCES shared_empresa(id),

    CONSTRAINT fk_eliminacao_item_rubrica
        FOREIGN KEY (rubrica_id)
        REFERENCES consolidacao_rubrica(id),

    CONSTRAINT chk_eliminacao_item_valor
        CHECK (valor > 0)
);


-- ===========================================================================================
-- REF: tabelas que pertence a outros modulos e que contabilidade pegará apenas alguns dados
-- ============================================================================================



create table ref_financeiro_conta_bancaria (
	id bigint unsigned auto_increment primary key,
    
    registro_origem_id int not null,
    
    empresa_id bigint unsigned not null,
    
    banco_origem_id int null,
    
    agencia varchar(20) not null,
    numero_conta varchar(30) not null,
    tipo_conta varchar(30) null,
    
    status varchar(20) not null,
    
    constraint fk_ref_conta_bancaria_empresa
		foreign key (empresa_id) 
        references shared_empresa(id),
        
	constraint uq_ref_conta_bancaria_origem
		unique (empresa_id, registro_origem_id)
);


create table ref_financeiro_movimentacao (
	id bigint unsigned auto_increment primary key,
    
    registro_origem_id int not null,
    
    empresa_id bigint unsigned not null,
    conta_bancaria_ref_id bigint unsigned null,
    
    data_movimentacao date not null,
    tipo varchar(30) not null,
    descricao varchar(200) null,
    
    valor decimal(15,2) not null,
    
    status varchar(20) not null,
    
    constraint fk_ref_fin_empresa
		foreign key (empresa_id)
		references shared_empresa(id),
        
	constraint uq_ref_financeiro_origem
		unique (empresa_id, registro_origem_id),
        
	constraint fk_ref_mov_fin_conta
		foreign key (conta_bancaria_ref_id)
        references ref_financeiro_conta_bancaria(id)
);



create table ref_financeiro_extrato_movimento (
	id bigint unsigned auto_increment primary key,
    
    empresa_id bigint unsigned not null,
    conta_bancaria_ref_id bigint unsigned not null,
    
    identificador_externo varchar(150) not null,
    
    data_movimentacao date not null,
    
    documento varchar(100) null,
    descricao varchar(255) null,
    
    natureza enum ('ENTRADA','SAIDA') not null,
    
    valor decimal(15,2) not null,
    
    arquivo_origem varchar(255) null,
    
    importado_em datetime not null default current_timestamp,
    
    constraint chk_ref_extrato_valor
		check (valor > 0),
        
	constraint fK_ref_extrato_empresa
		foreign key (empresa_id)
        references shared_empresa(id),
        
	constraint fk_ref_extrato_conta
		foreign key (conta_bancaria_ref_id)
		references ref_financeiro_conta_bancaria(id),
        
	constraint uq_ref_extrato_movimento
		unique (conta_bancaria_ref_id, identificador_externo)
	
);


create table ref_vendas_pedido (
	id bigint unsigned auto_increment primary key,
    
    registro_origem_id bigint unsigned not null,
    
    empresa_id bigint unsigned not null,
    
    numero_pedido varchar(50) not null,
	data_pedido datetime null,
    
    valor_total decimal(15,2) not null,
    
    status varchar(30) null,
    
    constraint fk_ref_vendas_empresa
		foreign key (empresa_id) 
        references shared_empresa(id),
        
	constraint uq_ref_vendas_origem
		unique (empresa_id, registro_origem_id)
	
);

create table ref_estoque_movimentacao (
	id bigint unsigned auto_increment primary key,
    
    registro_origem_id bigint not null,
    
    empresa_id bigint unsigned not null,
    
    codigo_produto varchar(100) not null,
    sku varchar(100) null,
    
    tipo_movimentacao varchar(20) not null,
	quantidade int not null,
    
    documento varchar(255) null,
    origem varchar(255) null,
    
    data_movimentacao datetime not null,
    
    valor_contabil decimal(15,2) null,
    
    constraint fk_ref_estoque_empresa
		foreign key (empresa_id)
        references shared_empresa(id),
        
	constraint uq_ref_estoque_origem
		unique (empresa_id, registro_origem_id)
);

create table ref_rh_evento_custo (
	id bigint unsigned auto_increment primary key,
    
    registro_origem_id int not null,
    
    empresa_id bigint unsigned not null,
    
    setor_origem_id int null,
    
    tipo_despesa varchar(50) not null,
    
    valor decimal(15,2) not null,
    
    data_competencia date not null,
    
    constraint fk_ref_rh_empresa
		foreign key (empresa_id)
        references shared_empresa(id),
        
	constraint uq_ref_rh_origem
		unique (empresa_id, registro_origem_id)
	
);



-- ===========
-- INTEGRACÃO
-- ===========

create table integracao_contabil (
	id bigint unsigned auto_increment primary key,
    
    empresa_id bigint unsigned not null,
    
    modulo_origem varchar(30) not null,
    
    entidade_origem varchar(50) not null,
    
    registro_origem varchar(100) not null,
    
    evento_origem varchar(100) not null,
    
    documento_origem varchar(100) null,
    
    data_evento datetime not null,
    
    valor_referencia decimal(15,2) null,
    
    status_integracao enum ('PENDENTE','PROCESSADO','ERRO','IGNORADO') not null default 'PENDENTE',
    
    lancamento_id bigint unsigned null,
    
    recebido_em datetime not null default current_timestamp,
    processado_em datetime null,
    
    mensagem_erro varchar(500) null,
    
    constraint fk_integracao_empresa
		foreign key (empresa_id)
        references shared_empresa(id),
        
	constraint fk_integracao_lancamento
		foreign key (lancamento_id)
        references lancamento_contabil(id),
        
	constraint uq_integracao_origem 
		unique (empresa_id, modulo_origem, entidade_origem, registro_origem, evento_origem)

);


-- ============
-- CONCILIAÇÃO
-- ============


create table conciliacao_contabil (
	id bigint unsigned auto_increment primary key,
    
    empresa_id bigint unsigned not null,
    conta_bancaria_ref_id bigint unsigned not null,
    
    data_inicio date not null,
    data_fim date not null,
    
    status enum('PENDENTE','EM_ANALISE','CONCILIADA','CANCELADA') not null default 'PENDENTE',
	
    criado_por bigint unsigned null,
    criado_em datetime not null default current_timestamp,
    
    finalizado_por bigint unsigned null,
    finalizado_em datetime null,
    
    observacao varchar(500) null,
    
    constraint chk_conciliacao_periodo
		check (data_inicio <= data_fim),
        
	constraint fk_conciliacao_empresa
		foreign key (empresa_id)
        references shared_empresa(id),
        
	constraint fk_conciliacao_conta
		foreign key (conta_bancaria_ref_id)
        references ref_financeiro_conta_bancaria(id),
        
	constraint fk_conciliacao_criado_por
		foreign key (criado_por) 
        references shared_usuario(id),
        
	constraint fk_conciliacao_finalizado_por
		foreign key (finalizado_por)
        references shared_usuario(id)
    
    
);

create table conciliacao_item (
	id bigint unsigned auto_increment primary key,
    
    conciliacao_id bigint unsigned not null,
    
    movimento_financeiro_ref_id bigint unsigned not null,
    movimento_extrato_ref_id bigint unsigned not null,
    
    status enum ('PENDENTE','CONCILIADO','DIVERGENTE','IGNORADO') not null default 'PENDENTE',
    
    conciliado_por bigint unsigned null,
    conciliado_em datetime null,
    
    observacao varchar(500) null,
    
    constraint fk_conciliacao_item_conciliacao
		foreign key (conciliacao_id)
        references conciliacao_contabil(id),
        
	constraint fk_conciliacao_item_financeiro
		foreign key (movimento_financeiro_ref_id)	
        references ref_financeiro_movimentacao(id),
        
	constraint fk_conciliacao_item_extrato
		foreign key (movimento_extrato_ref_id)
		references ref_financeiro_extrato_movimento(id),
        
	constraint fk_conciliacao_item_usuario
		foreign key (conciliado_por)
        references shared_usuario(id),
        
        
	constraint uq_conciliacao_mov_financeiro
        unique (conciliacao_id,movimento_financeiro_ref_id),

	constraint uq_conciliacao_mov_extrato
        unique (conciliacao_id,movimento_extrato_ref_id)
        


);





-- =====================================================
-- DADOS DE TESTE
-- =====================================================
