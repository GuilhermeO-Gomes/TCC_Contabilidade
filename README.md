Funcionalidades das telas

Painel de Integração
A tela que contém o painel de Integração tem como objetivo de gerar  lançamentos contábeis com base nas transações realizadas por outros módulos que ainda não foram registradas como lançamentos. A tela permite fazer esse registro com base na empresa, no período em que a transação foi feita, no módulo, e permite consultar quais transações foram integradas ou não, podendo reprocessar os dados.

Button Integrar -> pega os dados data, modulo, documento, transacao, valor, status de transações bancárias realizadas em outros módulos que não estejam tenham sido transformados em lançamentos contábeis(dados que não estejam na tabela de Lançamentos contábeis) e salva eles na tabela de integração e de lançamentos contábeis.

Button Reprocessar -> recarrega a tabela.

Button Buscar -> busca registros na tabela com a mesma data indicada.

Button Mostrar Todos -> mostra todos os registros da tabela. 


Geração da ECD
A tela sobre geração da ECD tem o dever de gerar o documento exigido no SPED que contém informações relacionadas a escrituração contábil da empresa. A tela precisa gerar um documento de texto contendo informações sobre o período em que está sendo gerado, livro diário, livro razão, lançamentos contábeis, balanços, informações da empresa tais como nome empresarial, cnpj, inscrição estadual, endereço, CEP, bairro municipio, UF, telefone,dados dos indivíduos que assinaram a escrituração digital e dados complementares.

Button Processar -> produz um documento de texto com as informações dadas, no padrão da ECD segundo o SPED.


Ajuste de exercícios
A tela de ajuste de exercícios tem como finalidade a modificação de dados referentes a exercícios sociais registrados anteriormente. A tela pode consultar ajustes feitos, pode também registrar um novo ajuste nos exercícios sociais, verificando o exercício, o período em que foi feito, as contas afetadas, justificativa para o ajuste, valor do ajuste, do crédito , do débito, tipo de ajuste, documento ďe origem, status dos ajustes, podendo também reverter ajustes realizados.

Button Pesquisar -> busca registros na tabela com os dados indicados.

Button Limpar -> limpa os campos do filtro de pesquisa.

Button Novo -> abre um JDialog para um novo ajuste.

Button Alterar -> permite alterar os dados da tabela e salvá-los.


Novo ajuste de exercício

Button Salvar -> salva os dados informados na tabela de ajustes de exercício.

Button Cancelar -> fecha o JDialog.


Consolidação de balanços
A tela de consolidação de balanços tem como objetivo principal consolidar balanços patrimoniais de um grupo de empresas. A tela deve permitir a geração de uma consolidação dos balanços de mais de uma empresa cadastrada, podendo ter a opção de eliminar transações entre as empresas, mapeamento de contas, geração por exercício, período, empresa e o registro de ajustes.

Button Pesquisar -> busca registros na tabela com os dados indicados.

Button Limpar -> limpa os campos do filtro de pesquisa.
