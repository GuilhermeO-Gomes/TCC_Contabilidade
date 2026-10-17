# Módulo contábil

Projeto de contabilidade em Java Swing com JDBC e banco `erp_contabil`. O código segue o guia de ajustes enviado pelo Lameiras. A ECF é acadêmica e não gera SPED oficial.

## O que mudou

| Área | Antes | Agora |
|---|---|---|
| Dados | Listas mantidas em `DadosContabeis` | Consultas e transações no banco; `DadosContabeis` transporta o contexto e o service |
| Lançamentos | Status escolhido livremente e exclusão em memória | Rascunho, contabilização e estorno controlados pelo service |
| Partidas | Conta e centro digitados como texto | Combos carregam objetos e gravam IDs; valores em `BigDecimal` |
| Dashboard | Totais e achados calculados somente na sessão | Agregação SQL por empresa e período e pendências persistidas |
| Auditoria | Severidades próprias e revisão temporária | BAIXA, MEDIA, ALTA, CRITICA; revisão com usuário, data e observação |
| ECF | Simulação sem persistência | Preparação, validação, filhos e registro de arquivo persistidos |
| Plano de Contas | DAO com colunas do banco antigo e inclusão incompleta | Model e DAO do esquema final; criação e edição por empresa; inativação preserva histórico |
| Interface | Consultas na ação da tela | `SwingWorker` consulta fora da thread gráfica; retorno atualiza a interface |

## Arquitetura

```text
View Swing
   -> ContabilidadeService
       -> DAO e Jdbc
           -> erp_contabil
```

As classes de `model` representam os dados. Os DAOs fazem as consultas e gravações. `ContabilidadeService` verifica empresa, permissões e regras dos lançamentos e controla as transações. `DadosContabeis` guarda os IDs da sessão e o acesso ao service.

Os IDs usam `long` ou `Long`. Campos opcionais aceitam `null`. Valores monetários usam `BigDecimal`; datas usam `LocalDate` e `LocalDateTime`. O DAO converte as datas para os tipos do JDBC.

`DashboardResumo` recebe os totais consultados no banco e substitui o antigo `ResumoContabil`.

## Configurar e executar no Eclipse

1. Importe a pasta como projeto Java existente. A biblioteca JDBC está em `src/lib/mysql-connector-j-9.7.0.jar`, referenciada no `.classpath`.
2. Crie o banco de desenvolvimento com `banco/script.sql`. Ele mantém o esquema do SQL enviado, sem os comentários. O script contém DROP TABLE: use em um banco vazio, pois ele apaga as tabelas existentes.
3. Execute `banco/permissoes_contabilidade.sql` para cadastrar os onze códigos de permissão. Esse arquivo não cria usuários, empresas, perfis, vínculos nem concede acesso automaticamente.
4. Vincule o usuário à empresa em `shared_usuario_empresa`, o perfil em `shared_usuario_empresa_perfil` e as permissões em `shared_perfil_permissao`. Esses cadastros precisam estar ativos.
5. Em **Run Configurations > Java Application > app.Main > Environment**, configure:

| Variável | Uso |
|---|---|
| `ERP_DB_URL` | URL JDBC da sua instância e do esquema `erp_contabil`, por exemplo `jdbc:mysql://localhost:3306/erp_contabil?connectTimeout=5000&socketTimeout=15000` |
| `ERP_DB_USUARIO` | Usuário do banco |
| `ERP_DB_SENHA` | Senha do banco; não salvar credenciais no Git |
| `ERP_EMPRESA_ID` | ID real da empresa do contexto |
| `ERP_USUARIO_ID` | ID real do usuário do contexto |

Sem essas variáveis, a conexão usa localhost, porta 3306, banco `erp_contabil`, usuário `root` e senha vazia. Ajuste os valores para a sua instalação.

Também existem as propriedades de JVM `erp.db.url`, `erp.db.usuario`, `erp.db.senha`, `erp.empresaId` e `erp.usuarioId`. As propriedades têm precedência sobre as variáveis de ambiente.

A sessão de login ainda precisa ser integrada. Por enquanto, os IDs vêm da configuração de execução. Quando o login estiver pronto, ele deve passar os IDs para `new DadosContabeis(empresaId, usuarioId)`. O service confere o vínculo com a empresa e as permissões a cada operação.

## Fluxo dos lançamentos

1. **Novo** abre o editor. Selecione exercício, conta analítica ativa e, se necessário, centro, histórico padrão e lote reais.
2. **Salvar rascunho** permite débitos diferentes de créditos, inclusive um rascunho ainda sem partidas. Documento e descrição são obrigatórios. Partidas existentes precisam ter conta válida e valor positivo com até duas casas decimais, pois o banco já exige isso.
3. **Salvar e contabilizar** valida e grava cabeçalho, partidas, status, usuário/data e eventos em uma única transação. Se falhar, tudo é revertido. Na lista, **Contabilizar** também pode atuar sobre um rascunho já salvo.
4. Lançamento contabilizado só pode ser corrigido por estorno. A tela e o service bloqueiam edição e exclusão.
5. **Estornar** cria outro lançamento, inverte débito/crédito e vincula `estorno_de_id`. A data não pode anteceder o original e o exercício de destino deve estar aberto. O original e suas partidas permanecem, com status ESTORNADO. Uma segunda tentativa sobre o mesmo original é recusada.
6. Excluir é permitido somente para rascunhos em exercício aberto. Os eventos permanecem vinculados ao exercício, com o ID excluído na descrição; as inconsistências operacionais daquele rascunho são removidas junto às partidas.

O Dashboard soma os lançamentos CONTABILIZADO e ESTORNADO. O original estornado continua no cálculo junto do lançamento reverso, para que um anule o outro. Rascunhos ficam fora dos valores, mas podem aparecer nas pendências da auditoria. No Livro Diário, o filtro permite consultar todos os estados.

## Auditoria

Ao abrir a tela, os achados são consultados no banco. O botão Executar auditoria analisa o período e grava novas inconsistências. Cada achado é identificado pelo lançamento, código e partida. Executar novamente preserva as revisões e não duplica os registros.

| Código | Checagem acadêmica | Severidade |
|---|---|---|
| AUD001 | Sem partidas | CRITICA |
| AUD002 | Débito diferente de crédito | CRITICA |
| AUD003 | Descrição vazia | MEDIA |
| AUD004 | Documento vazio | BAIXA |
| AUD005 | Partida sem conta | ALTA |
| AUD006 | Valor não positivo | CRITICA |

Algumas regras servem para conferir dados antigos ou gravados por outros módulos, pois o editor já exige conta e valor positivo. Corrigir um lançamento mantém a ocorrência para revisão manual. Excluir a partida ou o rascunho remove suas inconsistências. A tabela `auditoria` guarda os eventos; `auditoria_inconsistencia` guarda os achados.

## ECF acadêmica

O cabeçalho corresponde a `ecf_geracao`. `EcfApuracao`, `EcfAjuste`, `EcfSignatario` e `ValidacaoECF` correspondem às respectivas tabelas filhas.

- **Preparar nova ECF** salva empresa do contexto, exercício, período, finalidade, regime, referência da ECF retificada e os campos opcionais da configuração.
- **Apurações / ajustes / signatários** permite incluir apurações manuais, editar seus valores, incluir/remover ajustes e incluir/remover signatários. Ajustes e signatários podem ser corrigidos por remoção e nova inclusão durante a preparação. A base informada e o valor do tributo não são calculados por uma regra fiscal oficial. Aritmética de adições, exclusões e compensações é conferida na validação.
- Os totais dos ajustes são derivados dos filhos da apuração. Alterar a estrutura ou a configuração invalida a validação anterior. A nova validação recupera dados do banco.
- **Validar** grava ERRO e AVISO em `ecf_validacao`. Erros abertos deixam a ECF como COM_ERROS e bloqueiam o registro do arquivo. Sem erros, ela fica VALIDADA. Essa validação não comprova conformidade com o SPED.
- **Registrar arquivo** seleciona um arquivo existente e guarda nome, SHA-256, usuário, data e status GERADA após revalidar. O conteúdo fiscal do arquivo não é conferido nem salvo no banco.
- Uma ECF GERADA/ASSINADA/TRANSMITIDA/CANCELADA não pode ser reconfigurada por estas ações. Prepare uma retificadora ligada à mesma empresa e período.

Geração oficial de ECF/SPED, assinatura digital, transmissão e regras fiscais completas de IRPJ/CSLL ainda não estão implementadas. Os models têm os campos previstos no banco. A ECD entra apenas como vínculo opcional, conferido por empresa.

## Levar as telas para a release

A release consultada foi `origin/release` em 17/10/2026. Sua `TelaPrincipalContabilidade` usa construtores compatíveis com os deste módulo, mas importa as quatro telas diretamente de `view`. Os imports corretos são:

```java
import view.dashboard.TelaDashboard;
import view.diario.TelaLivroDiario;
import view.auditoria.TelaAuditoriaDigital;
import view.ecf.TelaGeracaoECF;
import view.planocontas.TelaPlanoDeContas;
```

Mantenha as telas nos subpacotes, sem criar cópias na raiz de `view`. Plano de Contas deve ter um único model, DAO e controller. O editor de lançamentos e o Livro Diário devem usar o mesmo service. `ExercicioSocial` veio da release e recebeu `toString` para aparecer no combo.

O arquivo `apoio-modulo-contabil/integracao-20261017/release-imports.patch` corrige esses imports e remove a tela antiga de Plano de Contas da raiz de `view`. Aplique junto dos arquivos deste módulo. A release também depende de telas dos colegas que ainda precisam ser entregues.

O SQL final é a referência para os nomes, campos e relacionamentos usados pelo módulo.

## Verificação

O SQL foi testado em uma instância separada do MariaDB 10.4.32 do XAMPP e criou 58 tabelas. Os dados usados nos testes ficaram nessa instância, fora do código e do SQL do projeto.

Passaram 43 verificações de integração e 14 de Swing, incluindo gravação, permissões, estorno, rollback, concorrência e ECF. As telas foram testadas em modo headless. O código compilou com `javac --release 8`; ainda é preciso conferir o JRE e o servidor MySQL usados pela equipe.

Os scripts, logs e imagens dos testes ficam em `apoio-modulo-contabil/integracao-20261017`, fora da pasta do projeto.
