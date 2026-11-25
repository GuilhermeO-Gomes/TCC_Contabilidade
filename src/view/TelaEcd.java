
package view;

import model.EcdGeracao;
import model.EcdSignatario;
import model.EcdValidacao;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class TelaEcd extends JPanel {

    

    private final JTextField txtRazaoSocial = campoLeitura(),
            txtCnpj = campoLeitura(),
            txtInscricaoEstadual = campoLeitura(),
            txtInscricaoMunicipal = campoLeitura(),
            txtEndereco = campoLeitura(),
            txtCidadeUf = campoLeitura(),
            txtCep = campoLeitura(),
            txtTelefone = campoLeitura(),
            txtCodigoMunicipio = new JTextField(10),
            txtNire = new JTextField(15),
            txtDataArquivamento = new JTextField(12),
            txtDataConversao = new JTextField(12),
            txtDataInicio = new JTextField(12),
            txtDataFim = new JTextField(12),
            txtNumeroLivro = new JTextField(15),
            txtNaturezaLivro = new JTextField(25),
            txtVersaoLayout = new JTextField(12),
            txtCodigoPlanoReferencial = new JTextField(15),
            txtCnpjCentralizadora = new JTextField(18),
            txtCnpjScp = new JTextField(18),
            txtStatus = campoLeitura(),
            txtNomeArquivo = campoLeitura(),
            txtHashArquivo = campoLeitura(),
            txtHashEcdSubstituida = campoLeitura(),
            txtNumeroRecibo = campoLeitura(),
            txtGeradoEm = campoLeitura(),
            txtTransmitidoEm = campoLeitura();

    private final JTextArea txtObservacao =
            new JTextArea(3, 35);
    
    private final JComboBox<String> cbEmpresa =
            new JComboBox<>();

    private final JComboBox<EcdGeracao.Finalidade> cbFinalidade =
            new JComboBox<>(EcdGeracao.Finalidade.values());

    private final JComboBox<EcdGeracao> cbEcdSubstituida =
            new JComboBox<>();

    private final JComboBox<TipoLivroOpcao> cbTipoLivro =
            new JComboBox<>(new TipoLivroOpcao[] {
                    new TipoLivroOpcao("G", "Livro diário completo"),
                    new TipoLivroOpcao("R", "Livro diário resumido"),
                    new TipoLivroOpcao("A", "Livro diário auxiliar"),
                    new TipoLivroOpcao("B", "Balancetes diários e balanços"),
                    new TipoLivroOpcao("Z", "Razão auxiliar")
            });
    
    private final JComboBox<String> cbExercicio =
            new JComboBox<>(),            
            cbPossuiNire =
            new JComboBox<>(new String[] {
                    "Não possui NIRE",
                    "Possui NIRE"
            }),
            cbSituacaoEspecial =
            new JComboBox<>(new String[] {
                    "",
                    "Cisão",
                    "Fusão",
                    "Incorporação",
                    "Extinção"
            }),
            cbSituacaoInicioPeriodo =
            new JComboBox<>(new String[] {
                    "Normal",
                    "Abertura",
                    "Resultante de cisão ou fusão",
                    "Início de obrigatoriedade da ECD"
            }),
            cbAuditoriaIndependente =
            new JComboBox<>(new String[] {
                    "Não",
                    "Sim"
            }),
            cbTipoEcd =
            new JComboBox<>(new String[] {
                    "Empresa não participante de SCP",
                    "Empresa participante de SCP",
                    "Escrituração da SCP"
            }),
            cbMoedaFuncional =
            new JComboBox<>(new String[] {
                    "Não",
                    "Sim"
            }),
            cbEscrituracaoConsolidada =
            new JComboBox<>(new String[] {
                    "Não",
                    "Sim"
            }),
            cbModalidadeEscrituracao =
            new JComboBox<>(new String[] {
                    "Centralizada",
                    "Descentralizada"
            }),
            cbMudancaPlanoContas =
            new JComboBox<>(new String[] {
                    "Não",
                    "Sim"
            });


    private final DefaultTableModel modeloSignatarios =
            new DefaultTableModel(
                    new Object[] {
                            "Nome", "CPF", "Tipo", "CRC",
                            "UF CRC", "E-mail", "Assinou"
                    }, 0) {
                public boolean isCellEditable(
                        int row, int column) {
                    return false;
                }
            };

    private final JTable tabelaSignatarios =
            new JTable(modeloSignatarios);

    private final List<EcdSignatario> signatarios =
            new ArrayList<>();


    private final JButton btnAdicionarSignatario =
            new JButton("Adicionar"),
            btnEditarSignatario =
            new JButton("Editar"),
            btnRemoverSignatario =
            new JButton("Remover"),
            btnValidar =
            new JButton("Validar ECD"),
            btnProcessar =
            new JButton("Gerar ECD"),
            btnCancelar =
            new JButton("Cancelar"),
            btnResolverValidacao =
            new JButton("Registrar resolução");

    private Long geracaoId;

    private EcdGeracao.Status statusAtual =
            EcdGeracao.Status.EM_PREPARACAO;

    private boolean somenteLeitura;

    public TelaEcd() {
        setLayout(new BorderLayout(8, 8));
        setBorder(
                BorderFactory.createEmptyBorder(10, 10, 10, 10));

        montar();
        configurarEventosLocais();
        atualizarEstadoFormulario();
    }

    private static JTextField campoLeitura() {
        JTextField campo = new JTextField(25);
        campo.setEditable(false);
        campo.setFocusable(false);
        return campo;
    }

    private void montar() {
        JPanel conteudo = new JPanel();

        conteudo.setLayout(
                new BoxLayout(conteudo, BoxLayout.Y_AXIS));

        conteudo.add(montarEmpresaExercicio());
        conteudo.add(Box.createVerticalStrut(8));

        conteudo.add(montarParametros());
        conteudo.add(Box.createVerticalStrut(8));

        conteudo.add(montarInformacoesComplementares());
        conteudo.add(Box.createVerticalStrut(8));

        conteudo.add(montarSignatarios());
        conteudo.add(Box.createVerticalStrut(8));

        conteudo.add(montarBotoes());

        add(new JScrollPane(conteudo), BorderLayout.CENTER);
    }

    private JPanel painelGrade(String titulo) {
        JPanel painel = new JPanel(new GridBagLayout());

        painel.setBorder(
                BorderFactory.createTitledBorder(titulo));

        return painel;
    }

    private GridBagConstraints restricoes() {
        GridBagConstraints g = new GridBagConstraints();

        g.insets = new Insets(3, 4, 3, 4);
        g.anchor = GridBagConstraints.WEST;
        g.fill = GridBagConstraints.HORIZONTAL;

        return g;
    }

    private void adicionar(
            JPanel painel,
            GridBagConstraints g,
            int linha,
            String rotulo,
            JComponent componente) {

        g.gridy = linha;
        g.gridx = 0;
        g.weightx = 0;

        painel.add(new JLabel(rotulo), g);

        g.gridx = 1;
        g.weightx = 1;

        painel.add(componente, g);
    }


    private JPanel montarEmpresaExercicio() {
        JPanel painel =
                painelGrade("Empresa e exercício social");

        GridBagConstraints g = restricoes();

        adicionar(painel, g, 0,
                "Empresa:", cbEmpresa);

        adicionar(painel, g, 1,
                "Razão social:", txtRazaoSocial);

        adicionar(painel, g, 2,
                "CNPJ:", txtCnpj);

        adicionar(painel, g, 3,
                "Inscrição estadual:", txtInscricaoEstadual);

        adicionar(painel, g, 4,
                "Inscrição municipal:", txtInscricaoMunicipal);

        adicionar(painel, g, 5,
                "Endereço:", txtEndereco);

        adicionar(painel, g, 6,
                "Cidade / UF:", txtCidadeUf);

        adicionar(painel, g, 7,
                "Código do município:", txtCodigoMunicipio);

        adicionar(painel, g, 8,
                "CEP:", txtCep);

        adicionar(painel, g, 9,
                "Telefone:", txtTelefone);

        adicionar(painel, g, 10,
                "Exercício:", cbExercicio);

        adicionar(painel, g, 11,
                "Data inicial:", txtDataInicio);

        adicionar(painel, g, 12,
                "Data final:", txtDataFim);

        adicionar(painel, g, 13,
                "Possui NIRE:", cbPossuiNire);

        adicionar(painel, g, 14,
                "NIRE:", txtNire);

        adicionar(painel, g, 15,
                "Data de arquivamento:", txtDataArquivamento);

        adicionar(painel, g, 16,
                "Data de conversão:", txtDataConversao);

        return painel;
    }


    private JPanel montarParametros() {
        JPanel painel =
                painelGrade("Parâmetros da ECD");

        GridBagConstraints g = restricoes();

        adicionar(painel, g, 0,
                "Finalidade:", cbFinalidade);

        adicionar(painel, g, 1,
                "ECD substituída:", cbEcdSubstituida);

        adicionar(painel, g, 2,
                "Hash da ECD substituída:",
                txtHashEcdSubstituida);

        adicionar(painel, g, 3,
                "Tipo do livro:", cbTipoLivro);

        adicionar(painel, g, 4,
                "Número do livro:", txtNumeroLivro);

        adicionar(painel, g, 5,
                "Natureza do livro:", txtNaturezaLivro);

        adicionar(painel, g, 6,
                "Versão do layout:", txtVersaoLayout);

        adicionar(painel, g, 7,
                "Observação:",
                new JScrollPane(txtObservacao));

        return painel;
    }


    private JPanel montarInformacoesComplementares() {
        JPanel painel = painelGrade(
                "Informações complementares da escrituração");

        GridBagConstraints g = restricoes();

        adicionar(painel, g, 0,
                "Situação especial:", cbSituacaoEspecial);

        adicionar(painel, g, 1,
                "Situação no início do período:",
                cbSituacaoInicioPeriodo);

        adicionar(painel, g, 2,
                "Sujeita à auditoria independente:",
                cbAuditoriaIndependente);

        adicionar(painel, g, 3,
                "Tipo da ECD:", cbTipoEcd);

        adicionar(painel, g, 4,
                "CNPJ da SCP:", txtCnpjScp);

        adicionar(painel, g, 5,
                "Identificação de moeda funcional:",
                cbMoedaFuncional);

        adicionar(painel, g, 6,
                "Escrituração consolidada:",
                cbEscrituracaoConsolidada);

        adicionar(painel, g, 7,
                "Modalidade da escrituração:",
                cbModalidadeEscrituracao);

        adicionar(painel, g, 8,
                "Mudança no plano de contas:",
                cbMudancaPlanoContas);

        adicionar(painel, g, 9,
                "Código do plano referencial:",
                txtCodigoPlanoReferencial);

        adicionar(painel, g, 10,
                "CNPJ da centralizadora:",
                txtCnpjCentralizadora);

        return painel;
    }

    


    private JPanel montarSignatarios() {
        JPanel painel =
                new JPanel(new BorderLayout(5, 5));

        painel.setBorder(
                BorderFactory.createTitledBorder(
                        "Signatários da ECD"));

        tabelaSignatarios.setSelectionMode(
                ListSelectionModel.SINGLE_SELECTION);

        tabelaSignatarios.setAutoCreateRowSorter(true);

        painel.add(
                new JScrollPane(tabelaSignatarios),
                BorderLayout.CENTER);

        JPanel acoes =
                new JPanel(new FlowLayout(FlowLayout.LEFT));

        acoes.add(btnAdicionarSignatario);
        acoes.add(btnEditarSignatario);
        acoes.add(btnRemoverSignatario);

        painel.add(acoes, BorderLayout.SOUTH);

        return painel;
    }


    private JPanel montarBotoes() {
        JPanel painel =
                new JPanel(new FlowLayout(FlowLayout.RIGHT));

        painel.add(btnValidar);
        painel.add(btnCancelar);
        painel.add(btnProcessar);

        return painel;
    }


    private void configurarEventosLocais() {

        
        cbFinalidade.addActionListener(e -> {

            boolean substituta =
                    cbFinalidade.getSelectedItem()
                            == EcdGeracao.Finalidade.SUBSTITUTA;

            cbEcdSubstituida.setEnabled(
                    substituta && !somenteLeitura);

            if (!substituta) {
                cbEcdSubstituida.setSelectedItem(null);
                txtHashEcdSubstituida.setText("");
            }

            atualizarEstadoFormulario();
        });

        cbEcdSubstituida.addActionListener(e -> {

            EcdGeracao selecionada =
                    (EcdGeracao)
                            cbEcdSubstituida.getSelectedItem();

            txtHashEcdSubstituida.setText(
                    selecionada == null
                            ? ""
                            : valor(selecionada.getHashArquivo()));
        });

        cbPossuiNire.addActionListener(
                e -> atualizarCamposCondicionais());

        cbTipoEcd.addActionListener(
                e -> atualizarCamposCondicionais());

        cbModalidadeEscrituracao.addActionListener(
                e -> atualizarCamposCondicionais());

        btnAdicionarSignatario.addActionListener(
                e -> adicionarSignatario());

        btnEditarSignatario.addActionListener(
                e -> editarSignatarioSelecionado());

        btnRemoverSignatario.addActionListener(
                e -> removerSignatarioSelecionado());
    }

    private void atualizarCamposCondicionais() {

        boolean possuiNire =
                cbPossuiNire.getSelectedIndex() == 1;

        txtNire.setEnabled(
                possuiNire && !somenteLeitura);

        boolean ecdScp =
                cbTipoEcd.getSelectedIndex() == 2;

        txtCnpjScp.setEnabled(
                ecdScp && !somenteLeitura);

        boolean descentralizada =
                cbModalidadeEscrituracao.getSelectedIndex() == 1;

        txtCnpjCentralizadora.setEnabled(
                descentralizada && !somenteLeitura);
    }




    private String valor(String texto) {
        return texto == null ? "" : texto.trim();
    }


    private void adicionarSignatario() {

        EcdSignatario novo =
                abrirDialogSignatario(null);

        if (novo != null) {
            signatarios.add(novo);
            atualizarTabelaSignatarios();
        }
    }

    private void editarSignatarioSelecionado() {

        int indice =
                indiceSignatarioSelecionado();

        if (indice < 0) {
            exibirMensagem("Selecione um signatário.");
            return;
        }

        EcdSignatario editado =
                abrirDialogSignatario(
                        signatarios.get(indice));

        if (editado != null) {
            signatarios.set(indice, editado);
            atualizarTabelaSignatarios();
        }
    }

    private void removerSignatarioSelecionado() {

        int indice =
                indiceSignatarioSelecionado();

        if (indice < 0) {
            exibirMensagem("Selecione um signatário.");
            return;
        }

        int resposta = JOptionPane.showConfirmDialog(
                this,
                "Deseja remover o signatário selecionado?",
                "Confirmar remoção",
                JOptionPane.YES_NO_OPTION);

        if (resposta == JOptionPane.YES_OPTION) {
            signatarios.remove(indice);
            atualizarTabelaSignatarios();
        }
    }

    private int indiceSignatarioSelecionado() {

        int linha =
                tabelaSignatarios.getSelectedRow();

        if (linha < 0) {
            return -1;
        }

        return tabelaSignatarios.convertRowIndexToModel(
                linha);
    }

    private EcdSignatario abrirDialogSignatario(
            EcdSignatario original) {

        JTextField nome = new JTextField(25),
        		cpf = new JTextField(16),
        		crc = new JTextField(15),
        		email = new JTextField(25);

        JComboBox<EcdSignatario.Tipo> tipo =
                new JComboBox<>(
                        EcdSignatario.Tipo.values());


        JComboBox<String> ufCrc =
                new JComboBox<>(new String[] {
                        "", "AC", "AL", "AP", "AM", "BA",
                        "CE", "DF", "ES", "GO", "MA", "MT",
                        "MS", "MG", "PA", "PB", "PR", "PE",
                        "PI", "RJ", "RN", "RS", "RO", "RR",
                        "SC", "SP", "SE", "TO"
                });

        

        JCheckBox assinou =
                new JCheckBox("Assinatura realizada");

        if (original != null) {

            nome.setText(valor(original.getNome()));
            cpf.setText(valor(original.getCpf()));

            tipo.setSelectedItem(
                    original.getTipo());

            crc.setText(valor(original.getCrc()));

            ufCrc.setSelectedItem(
                    valor(original.getUfCrc()));

            email.setText(
                    valor(original.getEmail()));

            assinou.setSelected(
                    original.isAssinou());
        }

        JPanel formulario =
                new JPanel(new GridLayout(0, 2, 5, 5));

        formulario.add(new JLabel("Nome:"));
        formulario.add(nome);

        formulario.add(new JLabel("CPF:"));
        formulario.add(cpf);

        formulario.add(new JLabel("Tipo:"));
        formulario.add(tipo);

        formulario.add(new JLabel("CRC:"));
        formulario.add(crc);

        formulario.add(new JLabel("UF do CRC:"));
        formulario.add(ufCrc);

        formulario.add(new JLabel("E-mail:"));
        formulario.add(email);

        formulario.add(new JLabel("Assinatura:"));
        formulario.add(assinou);

        int opcao = JOptionPane.showConfirmDialog(
                this,
                formulario,
                original == null
                        ? "Adicionar signatário"
                        : "Editar signatário",
                JOptionPane.OK_CANCEL_OPTION,
                JOptionPane.PLAIN_MESSAGE);

        if (opcao != JOptionPane.OK_OPTION) {
            return null;
        }

        EcdSignatario s =
                new EcdSignatario();

        if (original != null) {
            s.setId(original.getId());
            s.setEcdId(original.getEcdId());
            s.setAssinadoEm(original.getAssinadoEm());
        }

        s.setNome(nome.getText().trim());
        s.setCpf(cpf.getText().trim());

        s.setTipo(
                (EcdSignatario.Tipo)
                        tipo.getSelectedItem());

        s.setCrc(crc.getText().trim());

        s.setUfCrc(
                valor((String) ufCrc.getSelectedItem()));

        s.setEmail(email.getText().trim());

        s.setAssinou(assinou.isSelected());

        try {
            s.validar();
            return s;

        } catch (IllegalArgumentException ex) {
            exibirErro(ex.getMessage());
            return null;
        }
    }

    private void atualizarTabelaSignatarios() {

        modeloSignatarios.setRowCount(0);

        for (EcdSignatario s : signatarios) {

            modeloSignatarios.addRow(new Object[] {
                    s.getNome(),
                    s.getCpf(),
                    s.getTipo(),
                    s.getCrc(),
                    s.getUfCrc(),
                    s.getEmail(),
                    s.isAssinou() ? "Sim" : "Não"
            });
        }
    }




    public List<EcdSignatario> getSignatarios() {
        return new ArrayList<>(signatarios);
    }

    public void carregarSignatarios(
            List<EcdSignatario> registros) {

        signatarios.clear();

        if (registros != null) {
            signatarios.addAll(registros);
        }

        atualizarTabelaSignatarios();
    }


    public void carregarEcdsSubstituiveis(
            List<EcdGeracao> geracoes) {

        cbEcdSubstituida.removeAllItems();

        for (EcdGeracao geracao : geracoes) {
            cbEcdSubstituida.addItem(geracao);
        }
    }



    

    public void atualizarAcompanhamento(
            EcdGeracao geracao) {

        if (geracao == null) {
            return;
        }

        statusAtual = geracao.getStatus();

        txtStatus.setText(
                statusAtual == null
                        ? ""
                        : statusAtual.name());

        txtNomeArquivo.setText(
                valor(geracao.getNomeArquivo()));

        txtHashArquivo.setText(
                valor(geracao.getHashArquivo()));

        txtNumeroRecibo.setText(
                valor(geracao.getNumeroRecibo()));

        txtGeradoEm.setText(
                geracao.getGeradoEm() == null
                        ? ""
                        : geracao.getGeradoEm().toString());

        txtTransmitidoEm.setText(
                geracao.getTransmitidoEm() == null
                        ? ""
                        : geracao.getTransmitidoEm().toString());

        atualizarEstadoFormulario();
    }


    public void definirSomenteLeitura(
            boolean somenteLeitura) {

        this.somenteLeitura = somenteLeitura;

        atualizarEstadoFormulario();
    }

    private void atualizarEstadoFormulario() {

        boolean editavel =
                !somenteLeitura
                && (statusAtual
                        == EcdGeracao.Status.EM_PREPARACAO
                || statusAtual
                        == EcdGeracao.Status.COM_ERROS);

        cbEmpresa.setEnabled(
                !somenteLeitura
                && geracaoId == null
                && cbEmpresa.getItemCount() > 1);

        cbExercicio.setEnabled(
                editavel && geracaoId == null);

        txtDataInicio.setEditable(editavel);
        txtDataFim.setEditable(editavel);
        txtNumeroLivro.setEditable(editavel);
        txtNaturezaLivro.setEditable(editavel);
        txtVersaoLayout.setEditable(editavel);
        txtObservacao.setEditable(editavel);

        txtCodigoMunicipio.setEditable(editavel);
        txtNire.setEditable(editavel);
        txtDataArquivamento.setEditable(editavel);
        txtDataConversao.setEditable(editavel);

        txtCodigoPlanoReferencial.setEditable(editavel);
        txtCnpjScp.setEditable(editavel);
        txtCnpjCentralizadora.setEditable(editavel);

        cbFinalidade.setEnabled(editavel);
        cbTipoLivro.setEnabled(editavel);
        cbSituacaoEspecial.setEnabled(editavel);
        cbSituacaoInicioPeriodo.setEnabled(editavel);
        cbAuditoriaIndependente.setEnabled(editavel);
        cbTipoEcd.setEnabled(editavel);
        cbMoedaFuncional.setEnabled(editavel);
        cbEscrituracaoConsolidada.setEnabled(editavel);
        cbModalidadeEscrituracao.setEnabled(editavel);
        cbMudancaPlanoContas.setEnabled(editavel);
        cbPossuiNire.setEnabled(editavel);

        cbEcdSubstituida.setEnabled(
                editavel
                && cbFinalidade.getSelectedItem()
                        == EcdGeracao.Finalidade.SUBSTITUTA);

        txtHashEcdSubstituida.setEditable(false);

        atualizarCamposCondicionais();

        btnAdicionarSignatario.setEnabled(editavel);
        btnEditarSignatario.setEnabled(editavel);
        btnRemoverSignatario.setEnabled(editavel);

        btnProcessar.setEnabled(editavel);

        btnValidar.setEnabled(!somenteLeitura);

        btnCancelar.setEnabled(
                geracaoId != null && !somenteLeitura);
    }


    public JButton getBtnProcessar() {
        return btnProcessar;
    }

    public JButton getBtnValidar() {
        return btnValidar;
    }

    public JButton getBtnCancelar() {
        return btnCancelar;
    }

    public JButton getBtnResolverValidacao() {
        return btnResolverValidacao;
    }


    public JComboBox<String> getCbEmpresa() {
        return cbEmpresa;
    }

    public Long getGeracaoId() {
        return geracaoId;
    }


    public JTextField getTxtNire() {
        return txtNire;
    }

    public JTextField getTxtCodigoMunicipio() {
        return txtCodigoMunicipio;
    }

    public JTextField getTxtDataArquivamento() {
        return txtDataArquivamento;
    }

    public JTextField getTxtDataConversao() {
        return txtDataConversao;
    }

    public JTextField getTxtNaturezaLivro() {
        return txtNaturezaLivro;
    }

    public JTextField getTxtCodigoPlanoReferencial() {
        return txtCodigoPlanoReferencial;
    }

    public JTextField getTxtCnpjScp() {
        return txtCnpjScp;
    }

    public JTextField getTxtCnpjCentralizadora() {
        return txtCnpjCentralizadora;
    }

    public JTextField getTxtHashEcdSubstituida() {
        return txtHashEcdSubstituida;
    }

    public JComboBox<String> getCbPossuiNire() {
        return cbPossuiNire;
    }

    public JComboBox<String> getCbSituacaoEspecial() {
        return cbSituacaoEspecial;
    }

    public JComboBox<String> getCbSituacaoInicioPeriodo() {
        return cbSituacaoInicioPeriodo;
    }

    public JComboBox<String> getCbAuditoriaIndependente() {
        return cbAuditoriaIndependente;
    }

    public JComboBox<String> getCbTipoEcd() {
        return cbTipoEcd;
    }

    public JComboBox<String> getCbMoedaFuncional() {
        return cbMoedaFuncional;
    }

    public JComboBox<String> getCbEscrituracaoConsolidada() {
        return cbEscrituracaoConsolidada;
    }

    public JComboBox<String> getCbModalidadeEscrituracao() {
        return cbModalidadeEscrituracao;
    }

    public JComboBox<String> getCbMudancaPlanoContas() {
        return cbMudancaPlanoContas;
    }


    public void exibirMensagem(String mensagem) {

        JOptionPane.showMessageDialog(
                this,
                mensagem);
    }

    public void exibirErro(String mensagem) {

        JOptionPane.showMessageDialog(
                this,
                mensagem,
                "Erro",
                JOptionPane.ERROR_MESSAGE);
    }


    public static final class TipoLivroOpcao {

        private final String codigo;
        private final String descricao;

        public TipoLivroOpcao(
                String codigo,
                String descricao) {

            this.codigo = codigo;
            this.descricao = descricao;
        }

        public String getCodigo() {
            return codigo;
        }

        public String getDescricao() {
            return descricao;
        }

        public String toString() {
            return codigo + " - " + descricao;
        }
    }
}
