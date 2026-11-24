
package view;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.border.TitledBorder;

import java.awt.*;

public class TelaNotaFiscalEntrada extends JPanel {

  

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JTextField txtNumero;
    private JTextField txtSerie;
    private JTextField txtChaveAcesso;
    private JTextField txtDataEmissao;
    private JTextField txtDataEntrada;

 

    private JTextField txtCnpjFornecedor;
    private JTextField txtNomeFornecedor;
    private JTextField txtInscricaoEstadual;

 

    private JComboBox<String> cbNaturezaOperacao;
    private JComboBox<String> cbTipoDocumento;
    private JComboBox<String> cbStatus;

 

    private JTextField txtValorProdutos;
    private JTextField txtFrete;
    private JTextField txtDesconto;
    private JTextField txtValorICMS;
    private JTextField txtValorIPI;
    private JTextField txtValorPIS;
    private JTextField txtValorCOFINS;
    private JTextField txtValorTotal;

 

    private JTable tabelaItens;
    private JTable tabelaNotas;

    private DefaultTableModel modeloItens;
    private DefaultTableModel modeloNotas;



    private JButton btnNovo;
    private JButton btnSalvar;
    private JButton btnEditar;
    private JButton btnExcluir;
    private JButton btnLimpar;
    private JButton btnImportarXML;
    private JButton btnAdicionarItem;
    private JButton btnRemoverItem;
    private JButton btnBuscar;

    private JTextField txtPesquisa;

    public TelaNotaFiscalEntrada() {
        montar();
        carregarDadosTeste();
    }



    private void montar() {

        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Painel superior
        JPanel painelSuperior = new JPanel(new GridLayout(1, 3, 10, 10));

        painelSuperior.add(criarPainelIdentificacao());
        painelSuperior.add(criarPainelFornecedor());
        painelSuperior.add(criarPainelOperacao());

        add(painelSuperior, BorderLayout.NORTH);

        // Abas centrais
        JTabbedPane abas = new JTabbedPane();

        abas.addTab("Itens da Nota", criarPainelItens());
        abas.addTab("Notas de Entrada", criarPainelNotas());

        add(abas, BorderLayout.CENTER);

        // Painel inferior
        JPanel painelInferior = new JPanel(new BorderLayout(5, 5));

        painelInferior.add(criarPainelTotais(), BorderLayout.CENTER);
        painelInferior.add(criarPainelBotoes(), BorderLayout.SOUTH);

        add(painelInferior, BorderLayout.SOUTH);
    }

 

    private JPanel criarPainelIdentificacao() {

        JPanel painel = new JPanel(new GridLayout(0, 2, 5, 8));

        painel.setBorder(
            BorderFactory.createTitledBorder("Identificação da Nota")
        );

        txtNumero = new JTextField();
        txtSerie = new JTextField();
        txtChaveAcesso = new JTextField();
        txtDataEmissao = new JTextField();
        txtDataEntrada = new JTextField();

        adicionarCampo(painel, "Número:", txtNumero);
        adicionarCampo(painel, "Série:", txtSerie);
        adicionarCampo(painel, "Data Emissão:", txtDataEmissao);
        adicionarCampo(painel, "Data Entrada:", txtDataEntrada);
        adicionarCampo(painel, "Chave de Acesso:", txtChaveAcesso);

        return painel;
    }

  

    private JPanel criarPainelFornecedor() {

        JPanel painel = new JPanel(new GridLayout(0, 1, 5, 8));

        painel.setBorder(
            BorderFactory.createTitledBorder("Dados do Fornecedor")
        );

        txtCnpjFornecedor = new JTextField();
        txtNomeFornecedor = new JTextField();
        txtInscricaoEstadual = new JTextField();

        adicionarCampo(painel, "CNPJ/CPF:", txtCnpjFornecedor);
        adicionarCampo(painel, "Razão Social:", txtNomeFornecedor);
        adicionarCampo(painel, "Inscrição Estadual:", txtInscricaoEstadual);

        return painel;
    }



    private JPanel criarPainelOperacao() {

        JPanel painel = new JPanel(new GridLayout(0, 1, 5, 8));

        painel.setBorder(
            BorderFactory.createTitledBorder("Dados da Operação")
        );

        cbNaturezaOperacao = new JComboBox<>(new String[] {
            "Selecione...",
            "Compra para Comercialização",
            "Compra para Industrialização",
            "Compra para Uso e Consumo",
            "Compra de Ativo Imobilizado",
            "Devolução de Mercadoria"
        });

        cbTipoDocumento = new JComboBox<>(new String[] {
            "NF-e",
            "NFS-e",
            "Outro"
        });

        cbStatus = new JComboBox<>(new String[] {
            "Pendente",
            "Registrada",
            "Cancelada"
        });

        adicionarCampo(painel, "Natureza:", cbNaturezaOperacao);
        adicionarCampo(painel, "Documento:", cbTipoDocumento);
        adicionarCampo(painel, "Situação:", cbStatus);

        return painel;
    }



    private JPanel criarPainelItens() {

        JPanel painel = new JPanel(new BorderLayout(5, 5));

        String[] colunas = {
            "Código",
            "Descrição",
            "NCM",
            "CFOP",
            "Quantidade",
            "Valor Unitário",
            "Valor Total"
        };

        modeloItens = new DefaultTableModel(colunas, 0) {

            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        tabelaItens = new JTable(modeloItens);

        tabelaItens.setRowHeight(25);
        tabelaItens.setSelectionMode(
            ListSelectionModel.SINGLE_SELECTION
        );

        JScrollPane scroll = new JScrollPane(tabelaItens);

        painel.add(scroll, BorderLayout.CENTER);

        JPanel painelAcoes = new JPanel(new FlowLayout(FlowLayout.LEFT));

        btnAdicionarItem = new JButton("Adicionar Item");
        btnRemoverItem = new JButton("Remover Item");

        painelAcoes.add(btnAdicionarItem);
        painelAcoes.add(btnRemoverItem);

        painel.add(painelAcoes, BorderLayout.SOUTH);

        return painel;
    }



    private JPanel criarPainelNotas() {

        JPanel painel = new JPanel(new BorderLayout(5, 5));

        String[] colunas = {
            "Número",
            "Série",
            "Fornecedor",
            "CNPJ",
            "Data Emissão",
            "Data Entrada",
            "Valor Total",
            "Situação"
        };

        modeloNotas = new DefaultTableModel(colunas, 0) {

            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        tabelaNotas = new JTable(modeloNotas);

        tabelaNotas.setRowHeight(25);
        tabelaNotas.setSelectionMode(
            ListSelectionModel.SINGLE_SELECTION
        );

        painel.add(new JScrollPane(tabelaNotas), BorderLayout.CENTER);

        JPanel painelPesquisa = new JPanel(new FlowLayout(FlowLayout.LEFT));

        txtPesquisa = new JTextField(20);
        btnBuscar = new JButton("Buscar");

        painelPesquisa.add(new JLabel("Pesquisar:"));
        painelPesquisa.add(txtPesquisa);
        painelPesquisa.add(btnBuscar);

        painel.add(painelPesquisa, BorderLayout.NORTH);

        return painel;
    }



    private JPanel criarPainelTotais() {

        JPanel painel = new JPanel(new GridLayout(2, 4, 10, 5));

        painel.setBorder(
            BorderFactory.createTitledBorder("Valores e Tributos")
        );

        txtValorProdutos = criarCampoValor();
        txtFrete = criarCampoValor();
        txtDesconto = criarCampoValor();
        txtValorICMS = criarCampoValor();
        txtValorIPI = criarCampoValor();
        txtValorPIS = criarCampoValor();
        txtValorCOFINS = criarCampoValor();
        txtValorTotal = criarCampoValor();

        adicionarCampo(painel, "Produtos:", txtValorProdutos);
        adicionarCampo(painel, "Frete:", txtFrete);
        adicionarCampo(painel, "Desconto:", txtDesconto);
        adicionarCampo(painel, "ICMS:", txtValorICMS);
        adicionarCampo(painel, "IPI:", txtValorIPI);
        adicionarCampo(painel, "PIS:", txtValorPIS);
        adicionarCampo(painel, "COFINS:", txtValorCOFINS);
        adicionarCampo(painel, "Total da Nota:", txtValorTotal);

        return painel;
    }

    private JTextField criarCampoValor() {

        JTextField campo = new JTextField();
        campo.setHorizontalAlignment(JTextField.RIGHT);

        return campo;
    }



    private JPanel criarPainelBotoes() {

        JPanel painel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 5));

        btnNovo = new JButton("Novo");
        btnSalvar = new JButton("Salvar");
        btnEditar = new JButton("Editar");
        btnExcluir = new JButton("Excluir");
        btnLimpar = new JButton("Limpar");
        btnImportarXML = new JButton("Importar XML");

        painel.add(btnNovo);
        painel.add(btnSalvar);
        painel.add(btnEditar);
        painel.add(btnExcluir);
        painel.add(btnLimpar);
        painel.add(btnImportarXML);

        return painel;
    }


    private void adicionarCampo(
        JPanel painel,
        String texto,
        JComponent componente
    ) {

        JPanel grupo = new JPanel(new BorderLayout(3, 3));

        grupo.add(new JLabel(texto), BorderLayout.NORTH);
        grupo.add(componente, BorderLayout.CENTER);

        painel.add(grupo);
    }



    public void adicionarItem(
        String codigo,
        String descricao,
        String ncm,
        String cfop,
        String quantidade,
        String valorUnitario,
        String valorTotal
    ) {

        modeloItens.addRow(new Object[] {
            codigo,
            descricao,
            ncm,
            cfop,
            quantidade,
            valorUnitario,
            valorTotal
        });
    }

    public void adicionarNota(
        String numero,
        String serie,
        String fornecedor,
        String cnpj,
        String dataEmissao,
        String dataEntrada,
        String valorTotal,
        String situacao
    ) {

        modeloNotas.addRow(new Object[] {
            numero,
            serie,
            fornecedor,
            cnpj,
            dataEmissao,
            dataEntrada,
            valorTotal,
            situacao
        });
    }



    public void limparTabelaItens() {
        modeloItens.setRowCount(0);
    }

    public void limparTabelaNotas() {
        modeloNotas.setRowCount(0);
    }



    private void carregarDadosTeste() {

       
        adicionarItem(
            "001",
            "Notebook",
            "84713012",
            "1102",
            "2",
            "2.500,00",
            "5.000,00"
        );

        adicionarItem(
            "002",
            "Mouse USB",
            "84716053",
            "1102",
            "5",
            "50,00",
            "250,00"
        );

        
        adicionarNota(
            "000125",
            "1",
            "Distribuidora Exemplo LTDA",
            "12.345.678/0001-90",
            "10/11/2026",
            "11/11/2026",
            "5.250,00",
            "Registrada"
        );

        adicionarNota(
            "000126",
            "1",
            "Fornecedor Modelo LTDA",
            "98.765.432/0001-10",
            "15/11/2026",
            "16/11/2026",
            "3.800,00",
            "Pendente"
        );
    }



    public JTextField getTxtNumero() {
        return txtNumero;
    }

    public JTextField getTxtSerie() {
        return txtSerie;
    }

    public JTextField getTxtChaveAcesso() {
        return txtChaveAcesso;
    }

    public JTextField getTxtDataEmissao() {
        return txtDataEmissao;
    }

    public JTextField getTxtDataEntrada() {
        return txtDataEntrada;
    }



    public JTextField getTxtCnpjFornecedor() {
        return txtCnpjFornecedor;
    }

    public JTextField getTxtNomeFornecedor() {
        return txtNomeFornecedor;
    }

    public JTextField getTxtInscricaoEstadual() {
        return txtInscricaoEstadual;
    }



    public JComboBox<String> getCbNaturezaOperacao() {
        return cbNaturezaOperacao;
    }

    public JComboBox<String> getCbTipoDocumento() {
        return cbTipoDocumento;
    }

    public JComboBox<String> getCbStatus() {
        return cbStatus;
    }



    public JTextField getTxtValorProdutos() {
        return txtValorProdutos;
    }

    public JTextField getTxtFrete() {
        return txtFrete;
    }

    public JTextField getTxtDesconto() {
        return txtDesconto;
    }

    public JTextField getTxtValorICMS() {
        return txtValorICMS;
    }

    public JTextField getTxtValorIPI() {
        return txtValorIPI;
    }

    public JTextField getTxtValorPIS() {
        return txtValorPIS;
    }

    public JTextField getTxtValorCOFINS() {
        return txtValorCOFINS;
    }

    public JTextField getTxtValorTotal() {
        return txtValorTotal;
    }

    public JTable getTabelaItens() {
        return tabelaItens;
    }

    public JTable getTabelaNotas() {
        return tabelaNotas;
    }

 

    public JButton getBtnNovo() {
        return btnNovo;
    }

    public JButton getBtnSalvar() {
        return btnSalvar;
    }

    public JButton getBtnEditar() {
        return btnEditar;
    }

    public JButton getBtnExcluir() {
        return btnExcluir;
    }

    public JButton getBtnLimpar() {
        return btnLimpar;
    }

    public JButton getBtnImportarXML() {
        return btnImportarXML;
    }

    public JButton getBtnAdicionarItem() {
        return btnAdicionarItem;
    }

    public JButton getBtnRemoverItem() {
        return btnRemoverItem;
    }

    public JButton getBtnBuscar() {
        return btnBuscar;
    }

    public JTextField getTxtPesquisa() {
        return txtPesquisa;
    }
}
