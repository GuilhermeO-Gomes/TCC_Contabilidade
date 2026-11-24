package view;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class TelaNotaFiscalSaida extends JPanel {

    private static final long serialVersionUID = 1L;


    private JTextField txtNumero;
    private JTextField txtSerie;
    private JTextField txtChaveAcesso;
    private JTextField txtDataEmissao;
    private JTextField txtDataSaida;

   
    private JTextField txtCpfCnpjCliente;
    private JTextField txtNomeCliente;
    private JTextField txtInscricaoEstadual; 
    private JTextField txtMunicipioCliente;

  
    private JComboBox<String> cbNaturezaOperacao;
    private JComboBox<String> cbFinalidade;
    private JComboBox<String> cbTipoSaida;
    private JComboBox<String> cbStatus;

   
    private JTextField txtCodigoServico;
    private JTextField txtDescricaoServico;
    private JTextField txtCodigoMunicipalServico;
    private JTextField txtQuantidadeServico;
    private JTextField txtValorUnitarioServico;
    private JTextField txtValorTotalServico;
    private JTextField txtAliquotaISS;
    private JTextField txtValorISSItem;


    private JTextField txtValorProdutos; 
    private JTextField txtFrete;         
    private JTextField txtDesconto;
    private JTextField txtValorICMS;    
    private JTextField txtValorIPI;     
    private JTextField txtValorPIS;
    private JTextField txtValorCOFINS;
    private JTextField txtValorISS;
    private JTextField txtValorISSRetido;
    private JTextField txtValorCSLL;
    private JTextField txtValorIRRF;
    private JTextField txtValorINSS;
    private JTextField txtOutrasRetencoes;
    private JTextField txtDeducoes;
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
    private JButton btnGerarNFe; 
    private JButton btnVisualizarDANFE; 
    private JButton btnRemoverItem;
    private JButton btnBuscar;
    private JButton btnAdicionarItem;

    private JTextField txtPesquisa;

    public TelaNotaFiscalSaida() {
        montar();
        carregarDadosTeste();
    }

    private void montar() {
        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JPanel painelSuperior = new JPanel(new GridLayout(1, 3, 10, 10));
        painelSuperior.add(criarPainelIdentificacao());
        painelSuperior.add(criarPainelCliente());
        painelSuperior.add(criarPainelOperacao());
        add(painelSuperior, BorderLayout.NORTH);

        JTabbedPane abas = new JTabbedPane();
        abas.addTab("Serviços da Nota", criarPainelItens());
        abas.addTab("Notas de Saída", criarPainelNotas());
        add(abas, BorderLayout.CENTER);

        JPanel painelInferior = new JPanel(new BorderLayout(5, 5));
        painelInferior.add(criarPainelTotais(), BorderLayout.CENTER);
        painelInferior.add(criarPainelBotoes(), BorderLayout.SOUTH);
        add(painelInferior, BorderLayout.SOUTH);
    }

    private JPanel criarPainelIdentificacao() {
        JPanel painel = new JPanel(new GridLayout(0, 2, 5, 8));
        painel.setBorder(BorderFactory.createTitledBorder("Identificação da NFS-e"));

        txtNumero = new JTextField();
        txtSerie = new JTextField();
        txtChaveAcesso = new JTextField();
        txtDataEmissao = new JTextField();
        txtDataSaida = new JTextField();

        adicionarCampo(painel, "Número:", txtNumero);
        adicionarCampo(painel, "Série / RPS:", txtSerie);
        adicionarCampo(painel, "Data de Emissão:", txtDataEmissao);
        adicionarCampo(painel, "Data de Competência:", txtDataSaida);
        adicionarCampo(painel, "Código de Verificação:", txtChaveAcesso);

        return painel;
    }

    private JPanel criarPainelCliente() {
        JPanel painel = new JPanel(new GridLayout(0, 1, 5, 8));
        painel.setBorder(BorderFactory.createTitledBorder("Dados do Tomador do Serviço"));

        txtCpfCnpjCliente = new JTextField();
        txtNomeCliente = new JTextField();
        txtInscricaoEstadual = new JTextField();
        txtMunicipioCliente = new JTextField();

        adicionarCampo(painel, "CPF/CNPJ:", txtCpfCnpjCliente);
        adicionarCampo(painel, "Nome / Razão Social:", txtNomeCliente);
        adicionarCampo(painel, "Inscrição Municipal:", txtInscricaoEstadual);
        adicionarCampo(painel, "Município:", txtMunicipioCliente);

        return painel;
    }

    private JPanel criarPainelOperacao() {
        JPanel painel = new JPanel(new GridLayout(0, 1, 5, 8));
        painel.setBorder(BorderFactory.createTitledBorder("Dados da Operação"));

        cbNaturezaOperacao = new JComboBox<>(new String[] {
            "Selecione...",
            "Prestação de Serviço",
            "Prestação de Serviço - Exterior",
            "Serviço com ISS Retido",
            "Substituição de NFS-e"
        });

        cbFinalidade = new JComboBox<>(new String[] {
            "NFS-e Normal",
            "NFS-e de Substituição"
        });

        cbTipoSaida = new JComboBox<>(new String[] {
            "Emissão Própria",
            "Substituição de Nota"
        });

        cbStatus = new JComboBox<>(new String[] {
            "Pendente",
            "Em processamento",
            "Autorizada",
            "Rejeitada",
            "Cancelada"
        });

        adicionarCampo(painel, "Natureza:", cbNaturezaOperacao);
        adicionarCampo(painel, "Finalidade:", cbFinalidade);
        adicionarCampo(painel, "Tipo de Emissão:", cbTipoSaida);
        adicionarCampo(painel, "Situação:", cbStatus);

        return painel;
    }

    private JPanel criarPainelItens() {
        JPanel painel = new JPanel(new BorderLayout(5, 5));

        JPanel painelDadosServico = new JPanel(new GridLayout(0, 4, 8, 6));
        painelDadosServico.setBorder(
            BorderFactory.createTitledBorder("Dados do Serviço Prestado")
        );

        txtCodigoServico = new JTextField();
        txtDescricaoServico = new JTextField();
        txtCodigoMunicipalServico = new JTextField();
        txtQuantidadeServico = new JTextField();
        txtValorUnitarioServico = criarCampoValor();
        txtValorTotalServico = criarCampoValor();
        txtAliquotaISS = criarCampoValor();
        txtValorISSItem = criarCampoValor();

        adicionarCampo(painelDadosServico, "Código do Serviço:", txtCodigoServico);
        adicionarCampo(painelDadosServico, "Descrição:", txtDescricaoServico);
        adicionarCampo(painelDadosServico, "Código Municipal:", txtCodigoMunicipalServico);
        adicionarCampo(painelDadosServico, "Quantidade:", txtQuantidadeServico);
        adicionarCampo(painelDadosServico, "Valor Unitário (R$):", txtValorUnitarioServico);
        adicionarCampo(painelDadosServico, "Valor Total (R$):", txtValorTotalServico);
        adicionarCampo(painelDadosServico, "Alíquota ISS (%):", txtAliquotaISS);
        adicionarCampo(painelDadosServico, "ISS do Serviço (R$):", txtValorISSItem);

        painel.add(painelDadosServico, BorderLayout.NORTH);

        String[] colunas = {
            "Código Serviço",
            "Descrição",
            "Código Municipal",
            "Quantidade",
            "Valor Unitário",
            "Valor Total",
            "Alíquota ISS",
            "Valor ISS"
        };

        modeloItens = new DefaultTableModel(colunas, 0) {
            private static final long serialVersionUID = 1L;

            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        tabelaItens = new JTable(modeloItens);
        tabelaItens.setRowHeight(25);
        tabelaItens.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        painel.add(new JScrollPane(tabelaItens), BorderLayout.CENTER);

        JPanel painelAcoes = new JPanel(new FlowLayout(FlowLayout.LEFT));
        btnAdicionarItem = new JButton("Adicionar Serviço");
        btnRemoverItem = new JButton("Remover Serviço");

        painelAcoes.add(btnAdicionarItem);
        painelAcoes.add(btnRemoverItem);
        painel.add(painelAcoes, BorderLayout.SOUTH);

        return painel;
    }

    private JPanel criarPainelNotas() {
        JPanel painel = new JPanel(new BorderLayout(5, 5));

        String[] colunas = {
            "Número",
            "Série / RPS",
            "Tomador",
            "CPF/CNPJ",
            "Data Emissão",
            "Competência",
            "Valor Líquido",
            "Situação"
        };

        modeloNotas = new DefaultTableModel(colunas, 0) {
            private static final long serialVersionUID = 1L;

            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        tabelaNotas = new JTable(modeloNotas);
        tabelaNotas.setRowHeight(25);
        tabelaNotas.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        painel.add(new JScrollPane(tabelaNotas), BorderLayout.CENTER);

        JPanel painelPesquisa = new JPanel(new FlowLayout(FlowLayout.LEFT));
        txtPesquisa = new JTextField(20);
        btnBuscar = new JButton("Buscar");

        painelPesquisa.add(new JLabel("Pesquisar por número, tomador ou período:"));
        painelPesquisa.add(txtPesquisa);
        painelPesquisa.add(btnBuscar);

        painel.add(painelPesquisa, BorderLayout.NORTH);
        return painel;
    }

    private JPanel criarPainelTotais() {
        JPanel painel = new JPanel(new GridLayout(3, 4, 10, 5));
        painel.setBorder(BorderFactory.createTitledBorder("Valores e Retenções"));

        txtValorProdutos = criarCampoValor();
        txtFrete = criarCampoValor();
        txtDesconto = criarCampoValor();
        txtValorICMS = criarCampoValor();
        txtValorIPI = criarCampoValor();
        txtValorPIS = criarCampoValor();
        txtValorCOFINS = criarCampoValor();
        txtValorISS = criarCampoValor();
        txtValorISSRetido = criarCampoValor();
        txtValorCSLL = criarCampoValor();
        txtValorIRRF = criarCampoValor();
        txtValorINSS = criarCampoValor();
        txtOutrasRetencoes = criarCampoValor();
        txtDeducoes = criarCampoValor();
        txtValorTotal = criarCampoValor();

        adicionarCampo(painel, "Valor Bruto dos Serviços (R$):", txtValorProdutos);
        adicionarCampo(painel, "Descontos (R$):", txtDesconto);
        adicionarCampo(painel, "Deduções (R$):", txtDeducoes);
        adicionarCampo(painel, "ISS (R$):", txtValorISS);
        adicionarCampo(painel, "ISS Retido (R$):", txtValorISSRetido);
        adicionarCampo(painel, "PIS Retido (R$):", txtValorPIS);
        adicionarCampo(painel, "COFINS Retido (R$):", txtValorCOFINS);
        adicionarCampo(painel, "CSLL Retida (R$):", txtValorCSLL);
        adicionarCampo(painel, "IRRF Retido (R$):", txtValorIRRF);
        adicionarCampo(painel, "INSS Retido (R$):", txtValorINSS);
        adicionarCampo(painel, "Outras Retenções (R$):", txtOutrasRetencoes);
        adicionarCampo(painel, "Valor Líquido da Nota (R$):", txtValorTotal);

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
        btnGerarNFe = new JButton("Gerar NFS-e");
        btnVisualizarDANFE = new JButton("Visualizar NFS-e / PDF");

        painel.add(btnNovo);
        painel.add(btnSalvar);
        painel.add(btnEditar);
        painel.add(btnExcluir);
        painel.add(btnLimpar);
        painel.add(btnGerarNFe);
        painel.add(btnVisualizarDANFE);

        return painel;
    }

    private void adicionarCampo(JPanel painel, String texto, JComponent componente) {
        JPanel grupo = new JPanel(new BorderLayout(3, 3));
        grupo.add(new JLabel(texto), BorderLayout.NORTH);
        grupo.add(componente, BorderLayout.CENTER);
        painel.add(grupo);
    }

   
    public void adicionarServico(
        String codigoServico,
        String descricao,
        String codigoMunicipal,
        String quantidade,
        String valorUnitario,
        String valorTotal,
        String aliquotaISS,
        String valorISS
    ) {
        modeloItens.addRow(new Object[] {
            codigoServico,
            descricao,
            codigoMunicipal,
            quantidade,
            valorUnitario,
            valorTotal,
            aliquotaISS,
            valorISS
        });
    }

   
    @Deprecated
    public void adicionarItem(
        String codigo,
        String descricao,
        String ncm,
        String cfop,
        String quantidade,
        String valorUnitario,
        String valorTotal
    ) {
        adicionarServico(
            codigo,
            descricao,
            cfop,
            quantidade,
            valorUnitario,
            valorTotal,
            "",
            ""
        );
    }

    public void adicionarNota(
        String numero,
        String serie,
        String cliente,
        String cpfCnpj,
        String dataEmissao,
        String dataSaida,
        String valorTotal,
        String situacao
    ) {
        modeloNotas.addRow(new Object[] {
            numero,
            serie,
            cliente,
            cpfCnpj,
            dataEmissao,
            dataSaida,
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
        adicionarServico(
            "0107",
            "Desenvolvimento e manutenção de sistemas",
            "1.07",
            "1",
            "2.500,00",
            "2.500,00",
            "2,00",
            "50,00"
        );

        adicionarServico(
            "0140",
            "Consultoria em tecnologia da informação",
            "1.06",
            "2",
            "750,00",
            "1.500,00",
            "2,00",
            "30,00"
        );

        adicionarNota(
            "000245",
            "RPS 245",
            "Empresa Exemplo LTDA",
            "12.345.678/0001-90",
            "18/11/2026",
            "11/2026",
            "3.920,00",
            "Autorizada"
        );

        adicionarNota(
            "000246",
            "RPS 246",
            "Cliente Modelo LTDA",
            "98.765.432/0001-10",
            "20/11/2026",
            "11/2026",
            "1.450,00",
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

    public JTextField getTxtDataSaida() {
        return txtDataSaida;
    }

   

    public JTextField getTxtCpfCnpjCliente() {
        return txtCpfCnpjCliente;
    }

    public JTextField getTxtNomeCliente() {
        return txtNomeCliente;
    }

    public JTextField getTxtInscricaoEstadual() {
        return txtInscricaoEstadual;
    }

    public JTextField getTxtMunicipioCliente() {
        return txtMunicipioCliente;
    }

   
    public JComboBox<String> getCbNaturezaOperacao() {
        return cbNaturezaOperacao;
    }

    public JComboBox<String> getCbFinalidade() {
        return cbFinalidade;
    }

    public JComboBox<String> getCbTipoSaida() {
        return cbTipoSaida;
    }

    public JComboBox<String> getCbStatus() {
        return cbStatus;
    }


    public JTextField getTxtCodigoServico() {
        return txtCodigoServico;
    }

    public JTextField getTxtDescricaoServico() {
        return txtDescricaoServico;
    }

    public JTextField getTxtCodigoMunicipalServico() {
        return txtCodigoMunicipalServico;
    }

    public JTextField getTxtQuantidadeServico() {
        return txtQuantidadeServico;
    }

    public JTextField getTxtValorUnitarioServico() {
        return txtValorUnitarioServico;
    }

    public JTextField getTxtValorTotalServico() {
        return txtValorTotalServico;
    }

    public JTextField getTxtAliquotaISS() {
        return txtAliquotaISS;
    }

    public JTextField getTxtValorISSItem() {
        return txtValorISSItem;
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

    public JTextField getTxtValorISS() {
        return txtValorISS;
    }

    public JTextField getTxtValorISSRetido() {
        return txtValorISSRetido;
    }

    public JTextField getTxtValorCSLL() {
        return txtValorCSLL;
    }

    public JTextField getTxtValorIRRF() {
        return txtValorIRRF;
    }

    public JTextField getTxtValorINSS() {
        return txtValorINSS;
    }

    public JTextField getTxtOutrasRetencoes() {
        return txtOutrasRetencoes;
    }

    public JTextField getTxtDeducoes() {
        return txtDeducoes;
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

    public JButton getBtnGerarNFe() {
        return btnGerarNFe;
    }

    public JButton getBtnVisualizarDANFE() {
        return btnVisualizarDANFE;
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
